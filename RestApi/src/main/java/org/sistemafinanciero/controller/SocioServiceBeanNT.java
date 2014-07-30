package org.sistemafinanciero.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.joda.time.LocalDate;
import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.Accionista;
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.BovedaCaja;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.CuentaAporte;
import org.sistemafinanciero.entity.CuentaBancaria;
import org.sistemafinanciero.entity.HistorialAportesSP;
import org.sistemafinanciero.entity.HistorialAportesView;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.Socio;
import org.sistemafinanciero.entity.SocioView;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.entity.TransaccionCuentaAporte;
import org.sistemafinanciero.entity.dto.VoucherTransaccionCuentaAporte;
import org.sistemafinanciero.entity.type.EstadoCuentaAporte;
import org.sistemafinanciero.entity.type.EstadoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.exception.IllegalResultException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.nt.PersonaJuridicaServiceNT;
import org.sistemafinanciero.service.nt.PersonaNaturalServiceNT;
import org.sistemafinanciero.service.nt.SocioServiceNT;
import org.sistemafinanciero.util.EntityManagerProducer;
import org.sistemafinanciero.util.ProduceObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Stateless
@Remote(SocioServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SocioServiceBeanNT implements SocioServiceNT {

	private static Logger LOGGER = LoggerFactory.getLogger(SocioServiceBeanNT.class);

	@Inject
	private DAO<Object, Socio> socioDAO;
	@Inject
	private DAO<Object, SocioView> socioViewDAO;
	@Inject
	private DAO<Object, CuentaAporte> cuentaAporteDAO;
	@Inject
	private DAO<Object, CuentaBancaria> cuentaBancariaDAO;
	@Inject
	private DAO<Object, Agencia> agenciaDAO;
	@Inject
	private DAO<Object, PersonaNatural> personaNaturalDAO;
	@Inject
	private DAO<Object, TransaccionCuentaAporte> transaccionCuentaAporteDAO;

	@Inject
	private DAO<Object, HistorialAportesView> historialAportesViewDAO;

	@EJB
	private PersonaNaturalServiceNT personaNaturalService;
	@EJB
	private PersonaJuridicaServiceNT personaJuridicaService;

	@Inject
	private EntityManagerProducer em;

	@Override
	public Socio findById(BigInteger id) {
		return socioDAO.find(id);
	}

	@Override
	public List<Socio> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SocioView> findAllView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SocioView> findAllView(Boolean estadoCuentaAporte, Boolean estadoSocio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SocioView> findAllView(Boolean estadoCuentaAporte, Boolean estadoSocio, Integer offset, Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SocioView> findAllView(String filterText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SocioView> findAllView(String filterText, Boolean estadoCuentaAporte, Boolean estadoSocio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SocioView> findAllView(String filterText, BigInteger offset, BigInteger limit) {
		Boolean estadoCuentaAporte = null;
		Boolean estadoSocio = null;
		return findAllView(filterText, estadoCuentaAporte, estadoSocio, offset, limit);
	}

	@Override
	public List<SocioView> findAllView(String filterText, Boolean estadoCuentaAporte, Boolean estadoSocio, BigInteger offset, BigInteger limit) {
		List<SocioView> result = null;

		if (filterText == null)
			filterText = "";
		if (offset == null) {
			offset = BigInteger.ZERO;
		}
		offset = offset.abs();
		if (limit != null) {
			limit = limit.abs();
		}
		Integer offSetInteger = offset.intValue();
		Integer limitInteger = (limit != null ? limit.intValue() : null);

		// parametros de busqueda de estado socio
		if (estadoCuentaAporte == null)
			estadoCuentaAporte = true;

		List<Boolean> listEstado = new ArrayList<>();
		if (estadoSocio != null) {
			listEstado.add(estadoSocio);
		} else {
			listEstado.add(true);
			listEstado.add(false);
		}

		QueryParameter queryParameter = QueryParameter.with("modeEstado", listEstado).and("filtertext", '%' + filterText.toUpperCase() + '%');
		if (offSetInteger != null) {
			if (estadoCuentaAporte) {
				result = socioViewDAO.findByNamedQuery(SocioView.FindByFilterTextSocioView, queryParameter.parameters(), offSetInteger, limitInteger);
			} else {
				result = socioViewDAO.findByNamedQuery(SocioView.FindByFilterTextSocioViewAllHaveCuentaAporte, queryParameter.parameters(), offSetInteger, limitInteger);
			}
		} else {
			if (estadoCuentaAporte) {
				result = socioViewDAO.findByNamedQuery(SocioView.FindByFilterTextSocioView, queryParameter.parameters());
			} else {
				result = socioViewDAO.findByNamedQuery(SocioView.FindByFilterTextSocioViewAllHaveCuentaAporte, queryParameter.parameters());
			}
		}
		return result;
	}

	@Override
	public Socio find(TipoPersona tipoPersona, BigInteger idTipoDocumento, String numeroDocumento) {
		switch (tipoPersona) {
		case NATURAL:
			QueryParameter queryParameter1 = QueryParameter.with("idtipodocumento", idTipoDocumento).and("numerodocumento", numeroDocumento);
			List<Socio> list1 = socioDAO.findByNamedQuery(Socio.FindByPNTipoAndNumeroDocumento, queryParameter1.parameters());
			if (list1.size() == 1)
				return list1.get(0);
			if (list1.size() > 1) {
				LOGGER.error("Resultado invalido", "Se encontró mas de un socio");
				throw new EJBAccessException("Se encontró mas de un socio activo");
			}
			break;
		case JURIDICA:
			QueryParameter queryParameter2 = QueryParameter.with("idtipodocumento", idTipoDocumento).and("numerodocumento", numeroDocumento);
			List<Socio> list2 = socioDAO.findByNamedQuery(Socio.FindByPJTipoAndNumeroDocumento, queryParameter2.parameters());
			if (list2.size() == 1)
				return list2.get(0);
			if (list2.size() > 1) {
				LOGGER.error("Resultado invalido", "Se encontró mas de un socio");
				throw new EJBAccessException("Se encontró mas de un socio activo");
			}
			break;
		default:
			return null;
		}
		return null;
	}

	@Override
	public Socio find(BigInteger idCuentaBancaria) {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(idCuentaBancaria);
		if (cuentaBancaria == null)
			return null;
		Socio socio = cuentaBancaria.getSocio();
		PersonaNatural personaNatural = socio.getPersonaNatural();
		PersonaJuridica personaJuridica = socio.getPersonaJuridica();
		Hibernate.initialize(socio);
		if (personaNatural != null) {
			TipoDocumento documento = personaNatural.getTipoDocumento();
			Hibernate.initialize(personaNatural);
			Hibernate.initialize(documento);
		}
		if (personaJuridica != null) {
			TipoDocumento documento = personaJuridica.getTipoDocumento();
			Set<Accionista> accionistas = personaJuridica.getAccionistas();
			Hibernate.initialize(personaJuridica);
			Hibernate.initialize(documento);
			Hibernate.initialize(accionistas);
		}
		return socio;
	}

	@Override
	public PersonaNatural getPersonaNatural(BigInteger idSocio) {
		Socio socio = socioDAO.find(idSocio);
		if (socio == null)
			return null;
		PersonaNatural persona = socio.getPersonaNatural();
		if (persona != null) {
			Hibernate.initialize(persona);
			TipoDocumento documento = persona.getTipoDocumento();
			Hibernate.initialize(documento);
		}
		return persona;
	}

	@Override
	public PersonaJuridica getPersonaJuridica(BigInteger idSocio) {
		Socio socio = socioDAO.find(idSocio);
		if (socio == null)
			return null;
		PersonaJuridica persona = socio.getPersonaJuridica();
		if (persona != null) {
			Hibernate.initialize(persona);

			Set<Accionista> accionistas = persona.getAccionistas();
			PersonaNatural personaNatural = persona.getRepresentanteLegal();
			TipoDocumento docRepresentante = personaNatural.getTipoDocumento();
			TipoDocumento tipoDocumento = persona.getTipoDocumento();
			Hibernate.initialize(accionistas);
			Hibernate.initialize(personaNatural);
			Hibernate.initialize(docRepresentante);
			Hibernate.initialize(tipoDocumento);
		}
		return persona;
	}

	@Override
	public PersonaNatural getApoderado(BigInteger idSocio) {
		Socio socio = socioDAO.find(idSocio);
		if (socio == null)
			return null;
		PersonaNatural persona = socio.getApoderado();
		if (persona != null) {
			Hibernate.initialize(persona);
			TipoDocumento documento = persona.getTipoDocumento();
			Hibernate.initialize(documento);
		}
		return persona;
	}

	@Override
	public CuentaAporte getCuentaAporte(BigInteger idSocio) {
		Socio socio = socioDAO.find(idSocio);
		if (socio == null)
			return null;
		CuentaAporte cuentaAporte = socio.getCuentaAporte();
		if (cuentaAporte != null) {
			Hibernate.initialize(cuentaAporte);
			Moneda moneda = cuentaAporte.getMoneda();
			Hibernate.initialize(moneda);
		}
		return cuentaAporte;
	}

	@Override
	public Set<CuentaBancaria> getCuentasBancarias(BigInteger idSocio) {
		Socio socio = socioDAO.find(idSocio);
		if (socio == null)
			return null;
		Set<CuentaBancaria> cuentas = socio.getCuentaBancarias();
		for (CuentaBancaria cuentaBancaria : cuentas) {
			Hibernate.initialize(cuentaBancaria);
			Moneda moneda = cuentaBancaria.getMoneda();
			Hibernate.initialize(moneda);
		}
		return cuentas;
	}

	@Override
	public List<HistorialAportesSP> getHistorialAportes(BigInteger idSocio, Date desde, Date hasta, BigInteger offset, BigInteger limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
