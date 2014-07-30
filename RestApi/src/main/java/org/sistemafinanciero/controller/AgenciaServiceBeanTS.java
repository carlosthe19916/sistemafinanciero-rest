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
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.ts.AgenciaServiceTS;

@Named
@Stateless
@Remote(AgenciaServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgenciaServiceBeanTS implements AgenciaServiceTS {

	@Inject
	private DAO<Object, Agencia> agenciaDAO;

	@Inject
	private Validator validator;

	@Override
	public BigInteger create(Agencia t) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<Agencia>> violations = validator.validate(t);
		if (violations.isEmpty()) {
			agenciaDAO.create(t);
			return t.getIdAgencia();
		} else {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	@Override
	public void update(BigInteger id, Agencia t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Agencia agencia = agenciaDAO.find(id);
		if (agencia != null) {
			Set<ConstraintViolation<Agencia>> violations = validator.validate(t);
			if (violations.isEmpty()) {
				t.setIdAgencia(id);
				agenciaDAO.update(agencia);
			} else {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
		} else {
			throw new NonexistentEntityException("Agencia no existente, UPDATE no ejecutado");
		}
	}

	@Override
	public void delete(BigInteger id) throws NonexistentEntityException, RollbackFailureException {
		Agencia agencia = agenciaDAO.find(id);
		if (agencia != null) {
			agenciaDAO.delete(agencia);
		} else {
			throw new NonexistentEntityException("Agencia no existente, DELETE no ejecutado");
		}
	}

}
