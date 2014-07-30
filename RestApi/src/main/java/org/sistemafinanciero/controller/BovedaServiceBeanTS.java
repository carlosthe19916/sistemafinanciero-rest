package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.ts.BovedaServiceTS;

@Named
@Stateless
@Remote(BovedaServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaServiceBeanTS implements BovedaServiceTS {

	@Inject
	private DAO<Object, Boveda> bovedaDAO;

	@Inject
	private Validator validator;

	@Override
	public BigInteger create(Boveda t) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<Boveda>> violations = validator.validate(t);
		if (violations.isEmpty()) {
			bovedaDAO.create(t);
			return t.getIdBoveda();
		} else {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	@Override
	public void update(BigInteger id, Boveda t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Boveda boveda = bovedaDAO.find(id);
		if (boveda != null) {
			Set<ConstraintViolation<Boveda>> violations = validator.validate(t);
			if (violations.isEmpty()) {
				t.setIdBoveda(id);
				bovedaDAO.update(boveda);
			} else {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
		} else {
			throw new NonexistentEntityException("Boveda no existente, UPDATE no ejecutado");
		}
	}

	@Override
	public void delete(BigInteger id) throws NonexistentEntityException, RollbackFailureException {
		Boveda boveda = bovedaDAO.find(id);
		if (boveda != null) {
			bovedaDAO.delete(boveda);
		} else {
			throw new NonexistentEntityException("Boveda no existente, DELETE no ejecutado");
		}
	}

}
