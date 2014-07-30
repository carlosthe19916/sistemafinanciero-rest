package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
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
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.Beneficiario;
import org.sistemafinanciero.entity.CuentaBancaria;
import org.sistemafinanciero.entity.CuentaBancariaView;
import org.sistemafinanciero.entity.EstadocuentaBancariaView;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.entity.Titular;
import org.sistemafinanciero.entity.type.EstadoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.service.nt.CuentaBancariaServiceNT;
import org.sistemafinanciero.service.nt.PersonaNaturalServiceNT;
import org.sistemafinanciero.service.nt.TasaInteresServiceNT;
import org.sistemafinanciero.util.ProduceObject;

@Named
@Stateless
@Remote(CuentaBancariaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CuentaBancariaBeanNT implements CuentaBancariaServiceNT {

	@Inject
	private DAO<Object, CuentaBancaria> cuentaBancariaDAO;

	@Inject
	private DAO<Object, CuentaBancariaView> cuentaBancariaViewDAO;

	@Inject
	private DAO<Object, Agencia> agenciaDAO;

	@Inject
	private DAO<Object, Titular> titularDAO;

	@Inject
	private DAO<Object, EstadocuentaBancariaView> estadocuentaBancariaViewDAO;

	@EJB
	private TasaInteresServiceNT tasaInteresService;

	@EJB
	private PersonaNaturalServiceNT personaNaturalService;

	@Override
	public Agencia getAgencia(BigInteger idCuentaBancaria) {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(idCuentaBancaria);
		if (cuentaBancaria == null)
			return null;
		String numeroCuenta = cuentaBancaria.getNumeroCuenta();
		String codigoAgencia = ProduceObject.getCodigoAgenciaFromNumeroCuenta(numeroCuenta);
		QueryParameter queryParameter = QueryParameter.with("codigo", codigoAgencia);
		List<Agencia> list = agenciaDAO.findByNamedQuery(Agencia.findByCodigo, queryParameter.parameters());
		if (list.size() != 1)
			return null;
		return list.get(0);
	}

	@Override
	public CuentaBancariaView findView(BigInteger idCuentaBancaria) {
		CuentaBancariaView cuentaBancariaView = cuentaBancariaViewDAO.find(idCuentaBancaria);
		Moneda moneda = cuentaBancariaView.getMoneda();
		Hibernate.initialize(moneda);
		return cuentaBancariaView;
	}

	@Override
	public CuentaBancariaView find(String numeroCuenta) {
		QueryParameter queryParameter = QueryParameter.with("numeroCuenta", numeroCuenta);
		List<CuentaBancariaView> list = cuentaBancariaViewDAO.findByNamedQuery(CuentaBancariaView.findByNumeroCuenta, queryParameter.parameters());
		if (list.size() > 1)
			throw new EJBException("Mas de una cuenta con el numero de cuenta");
		else
			for (CuentaBancariaView cuentaBancaria : list) {
				Moneda moneda = cuentaBancaria.getMoneda();
				Hibernate.initialize(moneda);
				return cuentaBancaria;
			}
		return null;
	}

	@Override
	public List<CuentaBancariaView> findAllView() {
		TipoCuentaBancaria[] tipoCuenta = EnumSet.allOf(TipoCuentaBancaria.class).toArray(new TipoCuentaBancaria[0]);
		TipoPersona[] persona = EnumSet.allOf(TipoPersona.class).toArray(new TipoPersona[0]);
		EstadoCuentaBancaria[] estadoCuenta = EnumSet.allOf(EstadoCuentaBancaria.class).toArray(new EstadoCuentaBancaria[0]);
		return findAllView(tipoCuenta, persona, estadoCuenta, null, null);
	}

	@Override
	public List<CuentaBancariaView> findAllView(TipoCuentaBancaria[] tipoCuenta, TipoPersona[] persona, EstadoCuentaBancaria[] estadoCuenta) {
		return findAllView(tipoCuenta, persona, estadoCuenta, null, null);
	}

	@Override
	public List<CuentaBancariaView> findAllView(TipoCuentaBancaria[] tipoCuenta, TipoPersona[] persona, EstadoCuentaBancaria[] estadoCuenta, Integer offset, Integer limit) {
		return findAllView(null, tipoCuenta, persona, estadoCuenta, offset, limit);
	}

	@Override
	public List<CuentaBancariaView> findAllView(String filterText) {
		TipoCuentaBancaria[] tipoCuenta = EnumSet.allOf(TipoCuentaBancaria.class).toArray(new TipoCuentaBancaria[0]);
		TipoPersona[] persona = EnumSet.allOf(TipoPersona.class).toArray(new TipoPersona[0]);
		EstadoCuentaBancaria[] estadoCuenta = EnumSet.allOf(EstadoCuentaBancaria.class).toArray(new EstadoCuentaBancaria[0]);
		return findAllView(filterText, tipoCuenta, persona, estadoCuenta, null, null);
	}

	@Override
	public List<CuentaBancariaView> findAllView(String filterText, TipoCuentaBancaria[] tipoCuenta, TipoPersona[] persona, EstadoCuentaBancaria[] estadoCuenta) {
		return findAllView(filterText, tipoCuenta, persona, estadoCuenta, null, null);
	}

	@Override
	public List<CuentaBancariaView> findAllView(String filterText, TipoCuentaBancaria[] tipoCuenta, TipoPersona[] persona, EstadoCuentaBancaria[] estadoCuenta, Integer offset, Integer limit) {
		List<CuentaBancariaView> result = null;

		if (filterText == null)
			filterText = "";
		if (tipoCuenta == null)
			return new ArrayList<>();
		if (tipoCuenta.length == 0)
			return new ArrayList<>();
		if (persona == null)
			return new ArrayList<>();
		if (persona.length == 0)
			return new ArrayList<>();
		if (estadoCuenta == null)
			return new ArrayList<>();
		if (estadoCuenta.length == 0)
			return new ArrayList<>();

		ArrayList<TipoCuentaBancaria> uno = new ArrayList<>(Arrays.asList(tipoCuenta));
		ArrayList<TipoPersona> dos = new ArrayList<>(Arrays.asList(persona));
		ArrayList<EstadoCuentaBancaria> tres = new ArrayList<>(Arrays.asList(estadoCuenta));
		QueryParameter queryParameter = QueryParameter.with("filtertext", '%' + filterText.toUpperCase() + '%').and("tipoCuenta", uno).and("tipoPersona", dos).and("tipoEstadoCuenta", tres);

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

		result = cuentaBancariaViewDAO.findByNamedQuery(CuentaBancariaView.FindByFilterTextCuentaBancariaView, queryParameter.parameters(), offSetInteger, limitInteger);
		if (result != null)
			for (CuentaBancariaView cuentaBancariaView : result) {
				Moneda moneda = cuentaBancariaView.getMoneda();
				Hibernate.initialize(moneda);
			}
		return result;
	}

	@Override
	public Set<Titular> getTitulares(BigInteger idCuentaBancaria, boolean mode) {
		CuentaBancaria cuenta = cuentaBancariaDAO.find(idCuentaBancaria);
		if (cuenta == null)
			return null;
		Set<Titular> result = cuenta.getTitulars();
		for (Titular titular : result) {
			PersonaNatural persona = titular.getPersonaNatural();
			TipoDocumento documento = persona.getTipoDocumento();
			Hibernate.initialize(titular);
			Hibernate.initialize(persona);
			Hibernate.initialize(documento);
		}
		return result;
	}

	@Override
	public Set<Beneficiario> getBeneficiarios(BigInteger idCuentaBancaria) {
		CuentaBancaria cuenta = cuentaBancariaDAO.find(idCuentaBancaria);
		if (cuenta == null)
			return null;
		Set<Beneficiario> result = cuenta.getBeneficiarios();
		Hibernate.initialize(result);
		return result;
	}

	@Override
	public List<EstadocuentaBancariaView> getEstadoCuenta(BigInteger idCuenta, Date dateDesde, Date dateHasta) {
		CuentaBancaria cuenta = cuentaBancariaDAO.find(idCuenta);
		if (cuenta == null)
			return null;

		Date desdeQuery = null;
		Date hastaQuery = null;

		if (dateDesde == null || dateHasta == null) {
			Calendar calendar = Calendar.getInstance();
			LocalDate localDateHasta = new LocalDate(calendar.getTime());
			LocalDate localDateDesde = localDateHasta.minusDays(30);

			desdeQuery = localDateDesde.toDateTimeAtStartOfDay().toDate();
			hastaQuery = localDateHasta.toDateTimeAtStartOfDay().toDate();
		} else {
			desdeQuery = dateDesde;
			hastaQuery = dateHasta;
		}

		QueryParameter queryParameter = QueryParameter.with("numeroCuenta", cuenta.getNumeroCuenta()).and("desde", desdeQuery).and("hasta", hastaQuery);
		List<EstadocuentaBancariaView> list = estadocuentaBancariaViewDAO.findByNamedQuery(EstadocuentaBancariaView.findByNumeroCuentaAndDesdeHasta, queryParameter.parameters());

		return list;
	}

	@Override
	public CuentaBancaria findById(BigInteger id) {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(id);
		if (cuentaBancaria != null) {
			Moneda moneda = cuentaBancaria.getMoneda();
			Hibernate.initialize(moneda);
		}
		return cuentaBancaria;
	}

	@Override
	public List<CuentaBancaria> findAll() {
		List<CuentaBancaria> list = cuentaBancariaDAO.findAll();
		for (CuentaBancaria cuentaBancaria : list) {
			Moneda moneda = cuentaBancaria.getMoneda();
			Hibernate.initialize(moneda);
		}
		return list;
	}

	@Override
	public Titular findTitularById(BigInteger id) {
		Titular titular = titularDAO.find(id);
		if (titular != null) {
			PersonaNatural personaNatural = titular.getPersonaNatural();
			TipoDocumento documento = personaNatural.getTipoDocumento();
			Hibernate.initialize(personaNatural);
			Hibernate.initialize(documento);
		}
		return titular;
	}

	@Override
	public Beneficiario findBeneficiarioById(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		return cuentaBancariaDAO.count();
	}

}
