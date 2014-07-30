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
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.Accionista;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.exception.IllegalResultException;
import org.sistemafinanciero.service.nt.PersonaJuridicaServiceNT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Stateless
@Remote(PersonaJuridicaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonaJuridicaServiceBeanNT implements PersonaJuridicaServiceNT {

	private static Logger LOGGER = LoggerFactory.getLogger(PersonaJuridicaServiceBeanNT.class);

	@Inject
	private DAO<Object, PersonaJuridica> personaJuridicaDAO;

	@Override
	public PersonaJuridica findById(BigInteger id) {
		if (id == null)
			return null;
		PersonaJuridica persona = personaJuridicaDAO.find(id);
		if (persona != null) {
			PersonaNatural representante = persona.getRepresentanteLegal();
			TipoDocumento documentoRepresentante = representante.getTipoDocumento();
			TipoDocumento documento = persona.getTipoDocumento();
			Set<Accionista> accionistas = persona.getAccionistas();
			Hibernate.initialize(representante);
			Hibernate.initialize(documentoRepresentante);
			Hibernate.initialize(documento);
			for (Accionista accionista : accionistas) {
				PersonaNatural person = accionista.getPersonaNatural();
				TipoDocumento docPerson = person.getTipoDocumento();
				Hibernate.initialize(accionista);
				Hibernate.initialize(person);
				Hibernate.initialize(docPerson);
			}
		}
		return persona;
	}

	@Override
	public PersonaJuridica find(BigInteger idTipodocumento, String numerodocumento) {
		if (idTipodocumento == null || numerodocumento == null)
			return null;
		if (numerodocumento.isEmpty() || numerodocumento.trim().isEmpty())
			return null;
		PersonaJuridica result = null;
		try {
			QueryParameter queryParameter = QueryParameter.with("idtipodocumento", idTipodocumento).and("numerodocumento", numerodocumento);
			List<PersonaJuridica> list = personaJuridicaDAO.findByNamedQuery(PersonaJuridica.FindByTipoAndNumeroDocumento, queryParameter.parameters());
			if (list.size() > 1)
				throw new IllegalResultException("Se encontró mas de una persona con idDocumento:" + idTipodocumento + " y numero de documento:" + numerodocumento);
			else
				for (PersonaJuridica personaJuridica : list) {
					result = personaJuridica;
					TipoDocumento tipoDocumento = result.getTipoDocumento();
					PersonaNatural representante = result.getRepresentanteLegal();
					TipoDocumento documentoRepre = representante.getTipoDocumento();
					Set<Accionista> accionistas = result.getAccionistas();
					Hibernate.initialize(representante);
					Hibernate.initialize(accionistas);
					Hibernate.initialize(tipoDocumento);
					Hibernate.initialize(documentoRepre);
				}
		} catch (IllegalResultException e) {
			LOGGER.error(e.getMessage(), e.getLocalizedMessage(), e.getCause());
		}
		return result;
	}

	@Override
	public List<PersonaJuridica> findAll() {
		return findAll(null, null, null);
	}

	@Override
	public List<PersonaJuridica> findAll(Integer offset, Integer limit) {
		return findAll(null, offset, limit);
	}

	@Override
	public List<PersonaJuridica> findAll(String filterText) {
		return findAll(filterText, null, null);
	}

	@Override
	public List<PersonaJuridica> findAll(String filterText, Integer offset, Integer limit) {
		List<PersonaJuridica> result = null;

		if (filterText == null)
			filterText = "";
		if (offset == null) {
			offset = 0;
		}
		offset = Math.abs(offset);
		if (limit != null) {
			limit = Math.abs(limit);
		}
		Integer offSetInteger = offset.intValue();
		Integer limitInteger = (limit != null ? limit.intValue() : null);

		QueryParameter queryParameter = QueryParameter.with("filtertext", '%' + filterText.toUpperCase() + '%');
		result = personaJuridicaDAO.findByNamedQuery(PersonaJuridica.FindByFilterText, queryParameter.parameters(), offSetInteger, limitInteger);
		if (result != null) {
			for (PersonaJuridica personaJuridica : result) {
				Set<Accionista> accionistas = personaJuridica.getAccionistas();
				PersonaNatural representante = personaJuridica.getRepresentanteLegal();
				TipoDocumento tipoDocumento = personaJuridica.getTipoDocumento();
				Hibernate.initialize(representante);
				Hibernate.initialize(tipoDocumento);
				for (Accionista accionista : accionistas) {
					PersonaNatural p = accionista.getPersonaNatural();
					TipoDocumento doc = p.getTipoDocumento();
					Hibernate.initialize(accionista);
					Hibernate.initialize(p);
					Hibernate.initialize(doc);
				}
			}
		}
		return result;
	}

	@Override
	public int count() {
		return personaJuridicaDAO.count();
	}

}
