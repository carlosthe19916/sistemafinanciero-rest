package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.exception.IllegalResultException;
import org.sistemafinanciero.service.nt.PersonaNaturalServiceNT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Stateless
@Remote(PersonaNaturalServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonaNaturalServiceBeanNT implements PersonaNaturalServiceNT {

	private static Logger LOGGER = LoggerFactory.getLogger(PersonaNaturalServiceBeanNT.class);

	@Inject
	private DAO<Object, PersonaNatural> personaNaturalDAO;

	@Override
	public PersonaNatural findById(BigInteger id) {
		if (id == null)
			return null;
		PersonaNatural persona = personaNaturalDAO.find(id);
		if (persona != null) {
			TipoDocumento documento = persona.getTipoDocumento();
			Hibernate.initialize(documento);
		}
		return persona;
	}

	@Override
	public PersonaNatural find(BigInteger idTipoDocumento, String numeroDocumento) {
		if (idTipoDocumento == null || numeroDocumento == null)
			return null;
		numeroDocumento = numeroDocumento.trim();
		if (numeroDocumento.isEmpty())
			return null;
		PersonaNatural result = null;
		try {
			QueryParameter queryParameter = QueryParameter.with("idTipoDocumento", idTipoDocumento).and("numeroDocumento", numeroDocumento);
			List<PersonaNatural> list = personaNaturalDAO.findByNamedQuery(PersonaNatural.FindByTipoAndNumeroDocumento, queryParameter.parameters());
			if (list.size() > 1)
				throw new IllegalResultException("Se encontró mas de una persona con idDocumento:" + idTipoDocumento + " y numero de documento:" + numeroDocumento);
			else
				for (PersonaNatural personaNatural : list) {
					result = personaNatural;
					TipoDocumento tipoDocumento = result.getTipoDocumento();
					Hibernate.initialize(tipoDocumento);
				}
		} catch (IllegalResultException e) {
			LOGGER.error(e.getMessage(), e.getLocalizedMessage(), e.getCause());
		}
		return result;
	}

	@Override
	public List<PersonaNatural> findAll() {
		return findAll(null, null, null);
	}

	@Override
	public List<PersonaNatural> findAll(Integer offset, Integer limit) {
		return findAll(null, offset, limit);
	}

	@Override
	public List<PersonaNatural> findAll(String filterText) {
		return findAll(filterText, null, null);
	}

	@Override
	public List<PersonaNatural> findAll(String filterText, Integer offset, Integer limit) {
		List<PersonaNatural> result = null;

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

		QueryParameter queryParameter = QueryParameter.with("filterText", '%' + filterText.toUpperCase() + '%');
		result = personaNaturalDAO.findByNamedQuery(PersonaNatural.FindByFilterText, queryParameter.parameters(), offSetInteger, limitInteger);

		if (result != null) {
			for (PersonaNatural personaNatural : result) {
				TipoDocumento tipoDocumento = personaNatural.getTipoDocumento();
				Hibernate.initialize(tipoDocumento);
			}
		}
		return result;
	}

	@Override
	public int count() {
		return personaNaturalDAO.count();
	}

}
