package org.sistemafinanciero.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.entity.BovedaCaja;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.MonedaDenominacion;
import org.sistemafinanciero.entity.TransaccionBovedaCaja;
import org.sistemafinanciero.entity.TransaccionBovedaCajaDetalle;
import org.sistemafinanciero.entity.dto.VoucherTransaccionBovedaCaja;
import org.sistemafinanciero.entity.dto.VoucherTransaccionCajaCaja;
import org.sistemafinanciero.service.nt.TransaccionInternaServiceNT;

@Named
@Stateless
@Remote(TransaccionInternaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransaccionInternaServiceBeanNT implements TransaccionInternaServiceNT {

	@Inject
	private DAO<Object, TransaccionBovedaCaja> transaccionBovedaCajaDAO;

	@Override
	public VoucherTransaccionBovedaCaja getVoucherTransaccionBovedaCaja(BigInteger idTransaccionBovedaCaja) {
		TransaccionBovedaCaja transaccion = transaccionBovedaCajaDAO.find(idTransaccionBovedaCaja);
		if (transaccion == null)
			return null;

		// recuperando transaccion
		Caja caja = transaccion.getHistorialCaja().getCaja();
		Boveda boveda = transaccion.getHistorialBoveda().getBoveda();
		Moneda moneda = boveda.getMoneda();

		Set<TransaccionBovedaCajaDetalle> detalleTransaccion = transaccion.getTransaccionBovedaCajaDetalls();
		BigDecimal totalTransaccion = BigDecimal.ZERO;
		Set<BovedaCaja> list = caja.getBovedaCajas();
		Agencia agencia = null;
		for (BovedaCaja bovedaCaja : list) {
			agencia = bovedaCaja.getBoveda().getAgencia();
			break;
		}
		for (TransaccionBovedaCajaDetalle det : detalleTransaccion) {
			MonedaDenominacion denominacion = det.getMonedaDenominacion();
			BigDecimal valor = denominacion.getValor();
			BigInteger cantidad = det.getCantidad();
			BigDecimal subtotal = valor.multiply(new BigDecimal(cantidad));
			totalTransaccion = totalTransaccion.add(subtotal);
		}

		Hibernate.initialize(moneda);
		VoucherTransaccionBovedaCaja voucher = new VoucherTransaccionBovedaCaja();

		voucher.setId(transaccion.getIdTransaccionBovedaCaja());
		voucher.setAgenciaAbreviatura(agencia.getAbreviatura());
		voucher.setAgenciaDenominacion(agencia.getDenominacion());
		voucher.setEstadoConfirmacion(transaccion.getEstadoConfirmacion());
		voucher.setEstadoSolicitud(transaccion.getEstadoSolicitud());
		voucher.setFecha(transaccion.getFecha());
		voucher.setHora(transaccion.getHora());
		voucher.setMoneda(moneda);
		voucher.setMonto(totalTransaccion);
		voucher.setObservacion(transaccion.getObservacion());
		voucher.setOrigen(transaccion.getOrigen());
		voucher.setCajaDenominacion(caja.getDenominacion());
		voucher.setCajaAbreviatura(caja.getAbreviatura());
		// voucher.setTrabajador(transaccion.get);

		return voucher;
	}

	@Override
	public VoucherTransaccionCajaCaja getVoucherTransaccionCajaCaja(BigInteger idTransaccionCajaCaja) {
		// TODO Auto-generated method stub
		return null;
	}

}
