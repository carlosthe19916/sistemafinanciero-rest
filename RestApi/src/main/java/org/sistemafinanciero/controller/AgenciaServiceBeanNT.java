package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.HashSet;
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
import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.entity.BovedaCaja;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.service.nt.AgenciaServiceNT;

@Named
@Stateless
@Remote(AgenciaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgenciaServiceBeanNT implements AgenciaServiceNT {

	@Inject
	private DAO<Object, Agencia> agenciaDAO;

	@Override
	public Agencia findById(BigInteger id) {
		return agenciaDAO.find(id);
	}

	@Override
	public List<Agencia> findAll() {
		return agenciaDAO.findAll();
	}

	@Override
	public int count() {
		return agenciaDAO.count();
	}

	@Override
	public Set<Caja> getCajas(BigInteger idAgencia) {
		Agencia agencia = agenciaDAO.find(idAgencia);
		if (agencia != null) {
			Set<Caja> result = new HashSet<Caja>();
			Set<Boveda> bovedas = agencia.getBovedas();
			for (Boveda boveda : bovedas) {
				Set<BovedaCaja> bovedasCasjas = boveda.getBovedaCajas();
				for (BovedaCaja bovedaCaja : bovedasCasjas) {
					Caja caja = bovedaCaja.getCaja();
					Hibernate.initialize(caja);
					result.add(caja);
				}
			}
			return result;
		} else {
			return null;
		}
	}

}
