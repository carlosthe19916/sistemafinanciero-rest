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
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.ts.CajaServiceTS;

@Named
@Stateless
@Remote(CajaServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiceBeanTS implements CajaServiceTS {

	@Inject
	private DAO<Object, Caja> cajaDAO;

	@Inject
	private Validator validator;

	@Override
	public BigInteger create(Caja t) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<Caja>> violations = validator.validate(t);
		if (violations.isEmpty()) {
			cajaDAO.create(t);
			return t.getIdCaja();
		} else {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	@Override
	public void update(BigInteger id, Caja t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Caja caja = cajaDAO.find(id);
		if (caja != null) {
			Set<ConstraintViolation<Caja>> violations = validator.validate(t);
			if (violations.isEmpty()) {
				t.setIdCaja(id);
				cajaDAO.update(caja);
			} else {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
		} else {
			throw new NonexistentEntityException("caja no existente, UPDATE no ejecutado");
		}
	}

	@Override
	public void delete(BigInteger id) throws NonexistentEntityException, RollbackFailureException {
		Caja caja = cajaDAO.find(id);
		if (caja != null) {
			cajaDAO.delete(caja);
		} else {
			throw new NonexistentEntityException("caja no existente, DELETE no ejecutado");
		}
	}

}
