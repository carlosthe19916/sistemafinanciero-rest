package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
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
import org.sistemafinanciero.entity.Accionista;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.nt.PersonaJuridicaServiceNT;
import org.sistemafinanciero.service.ts.PersonaJuridicaServiceTS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Stateless
@Remote(PersonaJuridicaServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonaJuridicaServiceBeanTS implements PersonaJuridicaServiceTS {

	private static Logger LOGGER = LoggerFactory.getLogger(PersonaJuridicaServiceBeanTS.class);

	@Inject
	private DAO<Object, PersonaJuridica> personaJuridicaDAO;

	@Inject
	private DAO<Object, PersonaNatural> personaNaturalDAO;

	@Inject
	private DAO<Object, Accionista> accionistaDAO;

	@Inject
	private Validator validator;

	@EJB
	private PersonaJuridicaServiceNT personaJuridicaServiceNT;

	@Override
	public BigInteger create(PersonaJuridica personaJuridica) throws PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<PersonaJuridica>> violations = validator.validate(personaJuridica);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		TipoDocumento tipoDocumento = personaJuridica.getTipoDocumento();
		String numeroDocumento = personaJuridica.getNumeroDocumento();
		Set<Accionista> accionistas = personaJuridica.getAccionistas();

		Object obj = personaJuridicaServiceNT.find(tipoDocumento.getIdTipoDocumento(), numeroDocumento);
		if (obj == null)
			personaJuridicaDAO.create(personaJuridica);
		else
			throw new PreexistingEntityException("La persona con el Tipo y Numero de documento ya existe");

		// crear accionistas
		for (Accionista accionista : accionistas) {
			PersonaNatural personaNatural = accionista.getPersonaNatural();
			if (personaNatural != null) {
				BigInteger idPersona = personaNatural.getIdPersonaNatural();
				if (idPersona != null) {
					PersonaNatural persona = personaNaturalDAO.find(idPersona);
					if (persona != null) {
						accionista.setPersonaJuridica(personaJuridica);
						accionistaDAO.create(accionista);
					} else {
						throw new EJBException("Accionista no encontrado");
					}
				}
			}
		}
		return personaJuridica.getIdPersonaJuridica();
	}

	@Override
	public void update(BigInteger idPersonaJuridica, PersonaJuridica personaJuridica) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		Set<ConstraintViolation<PersonaJuridica>> violations = validator.validate(personaJuridica);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		TipoDocumento tipoDocumento = personaJuridica.getTipoDocumento();
		String numeroDocumento = personaJuridica.getNumeroDocumento();
		Set<Accionista> accionistas = personaJuridica.getAccionistas();

		PersonaJuridica personaDB = personaJuridicaServiceNT.find(tipoDocumento.getIdTipoDocumento(), numeroDocumento);
		PersonaJuridica personaById = personaJuridicaDAO.find(idPersonaJuridica);
		if (personaById == null)
			throw new PreexistingEntityException("Persona juridica no encontrada");

		personaJuridica.setIdPersonaJuridica(idPersonaJuridica);
		if (personaDB != null) {
			if (personaById.getIdPersonaJuridica().equals(personaDB.getIdPersonaJuridica())) {
				personaJuridicaDAO.update(personaJuridica);
			} else {
				throw new PreexistingEntityException("El document ya existe");
			}
		} else {
			personaJuridicaDAO.update(personaJuridica);
		}

		/*
		 * //crear accionistas for (Accionista accionista : accionistas) {
		 * PersonaNatural personaNatural = accionista.getPersonaNatural();
		 * if(personaNatural != null){ BigInteger idPersona =
		 * personaNatural.getIdPersonaNatural(); if(idPersona != null){
		 * PersonaNatural persona = personaNaturalDAO.find(idPersona);
		 * if(persona != null){ accionista.setPersonaJuridica(personaJuridica);
		 * accionistaDAO.create(accionista); } else { throw new
		 * RollbackFailureException("Accionista no encontrado"); } } } }
		 */
	}

	@Override
	public void delete(BigInteger id) throws NonexistentEntityException, RollbackFailureException {
		// TODO Auto-generated method stub

	}

}
