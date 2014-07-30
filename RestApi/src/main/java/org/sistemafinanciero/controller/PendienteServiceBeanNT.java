package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.BovedaCaja;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.PendienteCaja;
import org.sistemafinanciero.entity.dto.VoucherPendienteCaja;
import org.sistemafinanciero.service.nt.PendienteServiceNT;


@Named
@Stateless
@Remote(PendienteServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PendienteServiceBeanNT implements PendienteServiceNT {

	@Inject
	private DAO<Object, PendienteCaja> pendienteCajaDAO;
		
	@Override
	public VoucherPendienteCaja getVoucherPendienteCaja(BigInteger idPendienteCaja) {
		VoucherPendienteCaja voucherPendienteCaja = new VoucherPendienteCaja();		
		
		//recuperando pendiente
		PendienteCaja pendientecaja = pendienteCajaDAO.find(idPendienteCaja);
		Caja caja = pendientecaja.getHistorialCaja().getCaja();
		Set<BovedaCaja> list = caja.getBovedaCajas();
		Agencia agencia = null;
		for (BovedaCaja bovedaCaja : list) {
			agencia = bovedaCaja.getBoveda().getAgencia();
			break;
		}
			
		//poniendo los datos del pendiente
		voucherPendienteCaja.setAgenciaDenominacion(agencia.getDenominacion());
		voucherPendienteCaja.setAgenciaAbreviatura(agencia.getAbreviatura());
		voucherPendienteCaja.setCajaDenominacion(caja.getDenominacion());
		voucherPendienteCaja.setCajaAbreviatura(caja.getAbreviatura());
		voucherPendienteCaja.setIdPendienteCaja(idPendienteCaja);	
		voucherPendienteCaja.setMonto(pendientecaja.getMonto());
		voucherPendienteCaja.setObservacion(pendientecaja.getObservacion());
		voucherPendienteCaja.setFecha(pendientecaja.getFecha());
		voucherPendienteCaja.setHora(pendientecaja.getHora());
		voucherPendienteCaja.setTipoPendiente(pendientecaja.getTipoPendiente());
		voucherPendienteCaja.setTrabajador(pendientecaja.getTrabajador());
			
		Moneda moneda = pendientecaja.getMoneda();
		Hibernate.initialize(moneda);
		
		voucherPendienteCaja.setMoneda(moneda);
		
		return voucherPendienteCaja;
	}

	@Override
	public PendienteCaja findById(BigInteger id) {
		PendienteCaja pendiente = pendienteCajaDAO.find(id);
		Moneda moneda = pendiente.getMoneda();
		Hibernate.initialize(moneda);
		return pendiente;
	}

	@Override
	public List<PendienteCaja> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}
}
