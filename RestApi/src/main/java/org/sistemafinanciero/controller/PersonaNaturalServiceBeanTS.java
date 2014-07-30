package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
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
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.nt.PersonaNaturalServiceNT;
import org.sistemafinanciero.service.ts.PersonaNaturalServiceTS;

@Named
@Stateless
@Remote(PersonaNaturalServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonaNaturalServiceBeanTS implements PersonaNaturalServiceTS {

	@Inject
	private DAO<Object, PersonaNatural> personaNaturalDAO;

	@Inject
	private Validator validator;

	@EJB
	private PersonaNaturalServiceNT personaNaturalServiceNT;

	public BigInteger create(PersonaNatural personanatural) throws PreexistingEntityException {
		Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(personanatural);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		TipoDocumento tipoDocumento = personanatural.getTipoDocumento();
		String numeroDocumento = personanatural.getNumeroDocumento();
		PersonaNatural obj = personaNaturalServiceNT.find(tipoDocumento.getIdTipoDocumento(), numeroDocumento);
		if (obj == null)
			personaNaturalDAO.create(personanatural);
		else
			throw new PreexistingEntityException("Persona existente");
		return personanatural.getIdPersonaNatural();
	}

	@Override
	public void update(BigInteger idPersona, PersonaNatural persona) throws NonexistentEntityException, PreexistingEntityException {
		Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(persona);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		PersonaNatural personaNaturalFromDB = personaNaturalDAO.find(idPersona);
		if (personaNaturalFromDB == null)
			throw new NonexistentEntityException("La persona con id " + idPersona + " no fue encontrado");

		TipoDocumento tipoDocumento = persona.getTipoDocumento();
		PersonaNatural p = personaNaturalServiceNT.find(tipoDocumento.getIdTipoDocumento(), persona.getNumeroDocumento());
		if (p != null)
			if (p.getIdPersonaNatural() != idPersona)
				throw new PreexistingEntityException("Tipo y numero de documento ya existente");

		persona.setIdPersonaNatural(idPersona);
		personaNaturalDAO.update(persona);
	}

	@Override
	public void delete(BigInteger idPersonaNatural) throws NonexistentEntityException, RollbackFailureException {
		PersonaNatural personaNatural = personaNaturalDAO.find(idPersonaNatural);
		if (personaNatural != null)
			personaNaturalDAO.delete(personaNatural);
		else
			throw new NonexistentEntityException("Objeto no existente");
	}

}
