package org.sistemafinanciero.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.Beneficiario;
import org.sistemafinanciero.entity.CuentaBancaria;
import org.sistemafinanciero.entity.CuentaBancariaInteresGenera;
import org.sistemafinanciero.entity.CuentaBancariaTasa;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.Socio;
import org.sistemafinanciero.entity.Titular;
import org.sistemafinanciero.entity.type.EstadoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.nt.PersonaNaturalServiceNT;
import org.sistemafinanciero.service.nt.TasaInteresServiceNT;
import org.sistemafinanciero.service.ts.CajaSessionServiceTS;
import org.sistemafinanciero.service.ts.CuentaBancariaServiceTS;
import org.sistemafinanciero.util.ProduceObject;

@Named
@Stateless
@Remote(CuentaBancariaServiceTS.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CuentaBancariaBeanTS implements CuentaBancariaServiceTS {

	@Inject
	private DAO<Object, CuentaBancaria> cuentaBancariaDAO;

	@Inject
	private DAO<Object, PersonaNatural> personaNaturalDAO;

	@Inject
	private DAO<Object, PersonaJuridica> personaJuridicaDAO;

	@Inject
	private DAO<Object, Socio> socioDAO;

	@Inject
	private DAO<Object, Moneda> monedaDAO;

	@Inject
	private DAO<Object, Titular> titularDAO;

	@Inject
	private DAO<Object, Beneficiario> beneficiarioDAO;

	@Inject
	private DAO<Object, CuentaBancariaTasa> cuentaBancariaTasaDAO;

	@Inject
	private DAO<Object, Agencia> agenciaDAO;

	@Inject
	private DAO<Object, CuentaBancariaInteresGenera> cuentaBancariaInteresGeneraDAO;

	@EJB
	private TasaInteresServiceNT tasaInteresService;
	@EJB
	private CajaSessionServiceTS cajaSessionService;
	@EJB
	private PersonaNaturalServiceNT personaNaturalService;

	@Inject
	private Validator validator;

	@Override
	public BigInteger create(CuentaBancaria t) throws PreexistingEntityException, RollbackFailureException {
		return null;
	}

	@Override
	public void update(BigInteger id, CuentaBancaria t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BigInteger id) throws NonexistentEntityException, RollbackFailureException {
		// TODO Auto-generated method stub

	}

	@Override
	public BigInteger create(TipoCuentaBancaria tipoCuentaBancaria, String codigoAgencia, BigInteger idMoneda, BigDecimal tasaInteres, TipoPersona tipoPersona, BigInteger idPersona, Integer periodo, int cantRetirantes, List<BigInteger> titulares, List<Beneficiario> beneficiarios) throws RollbackFailureException {
		PersonaNatural personaNatural = null;
		PersonaJuridica personaJuridica = null;

		switch (tipoPersona) {
		case NATURAL:
			personaNatural = personaNaturalDAO.find(idPersona);
			break;
		case JURIDICA:
			personaJuridica = personaJuridicaDAO.find(idPersona);
			break;
		default:
			break;
		}

		Calendar calendar = Calendar.getInstance();
		Moneda moneda = monedaDAO.find(idMoneda);

		QueryParameter queryParameter = QueryParameter.with("codigo", codigoAgencia);
		List<Agencia> listAgencias = agenciaDAO.findByNamedQuery(Agencia.findByCodigo, queryParameter.parameters());
		if (listAgencias.size() != 1)
			throw new RollbackFailureException("Agencia no encontrada");

		// verificar si existe el socio
		Set<Socio> socios = null;
		Socio socio = null;
		if (personaNatural != null)
			socios = personaNatural.getSocios();
		if (personaJuridica != null)
			socios = personaJuridica.getSocios();
		for (Socio s : socios) {
			if (s.getEstado())
				socio = s;
		}

		// verificar titulares
		Set<Titular> listTitulres = new HashSet<>();
		for (BigInteger id : titulares) {
			PersonaNatural persona = personaNaturalDAO.find(id);
			if (persona == null) {
				throw new RollbackFailureException("Titular no encontrado");
			} else {
				Titular titular = new Titular();
				titular.setCuentaBancaria(null);
				titular.setEstado(true);
				titular.setPersonaNatural(persona);
				titular.setFechaInicio(calendar.getTime());
				titular.setFechaFin(null);
				listTitulres.add(titular);
			}
		}

		// crear socio sin cuenta de aportes si no existe
		if (socio == null) {
			socio = new Socio();
			socio.setApoderado(null);
			socio.setCuentaAporte(null);
			socio.setCuentaBancarias(null);
			socio.setEstado(true);
			socio.setFechaInicio(calendar.getTime());
			socio.setFechaFin(null);
			socio.setPersonaJuridica(personaJuridica);
			socio.setPersonaNatural(personaNatural);
			socioDAO.create(socio);
		}

		// crear cuenta bancaria
		CuentaBancaria cuentaBancaria = new CuentaBancaria();
		cuentaBancaria.setNumeroCuenta(codigoAgencia);
		cuentaBancaria.setBeneficiarios(null);
		cuentaBancaria.setCantidadRetirantes(cantRetirantes);
		cuentaBancaria.setEstado(EstadoCuentaBancaria.ACTIVO);
		cuentaBancaria.setFechaApertura(calendar.getTime());
		cuentaBancaria.setFechaCierre(null);
		if (tipoCuentaBancaria.equals(TipoCuentaBancaria.PLAZO_FIJO)) {
			if (periodo == null)
				throw new RollbackFailureException("Periodo de plazo fijo no definido");
			LocalDate fechaCierre = new LocalDate(calendar.getTime());
			fechaCierre = fechaCierre.plusDays(periodo);
			cuentaBancaria.setFechaCierre(fechaCierre.toDate());
		}
		cuentaBancaria.setMoneda(moneda);
		cuentaBancaria.setSaldo(BigDecimal.ZERO);
		cuentaBancaria.setSocio(socio);
		cuentaBancaria.setTipoCuentaBancaria(tipoCuentaBancaria);
		cuentaBancaria.setTitulars(null);
		cuentaBancariaDAO.create(cuentaBancaria);
		// generar el numero de cuenta de cuenta
		String numeroCuenta = ProduceObject.getNumeroCuenta(cuentaBancaria);
		cuentaBancaria.setNumeroCuenta(numeroCuenta);
		cuentaBancariaDAO.update(cuentaBancaria);

		// crear titulares
		for (Titular titular : listTitulres) {
			titular.setCuentaBancaria(cuentaBancaria);
			titularDAO.create(titular);
		}
		if (beneficiarios != null)
			for (Beneficiario beneficiario : beneficiarios) {
				beneficiario.setCuentaBancaria(cuentaBancaria);
				beneficiarioDAO.create(beneficiario);
			}

		// crear intereses
		if (tasaInteres == null)
			tasaInteres = tasaInteresService.getTasaInteresCuentaAhorro(idMoneda);
		CuentaBancariaTasa cuentaBancariaTasa = new CuentaBancariaTasa();
		cuentaBancariaTasa.setCuentaBancaria(cuentaBancaria);
		cuentaBancariaTasa.setValor(tasaInteres);
		cuentaBancariaTasaDAO.create(cuentaBancariaTasa);

		return cuentaBancaria.getIdCuentaBancaria();
	}

	@Override
	public void congelarCuentaBancaria(BigInteger idCuentaBancaria) throws RollbackFailureException {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(idCuentaBancaria);
		if (cuentaBancaria == null)
			throw new RollbackFailureException("Cuenta bancaria no encontrada");
		if (!cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.ACTIVO))
			throw new RollbackFailureException("La cuenta no esta activa, no se puede congelar");
		cuentaBancaria.setEstado(EstadoCuentaBancaria.CONGELADO);
		cuentaBancariaDAO.update(cuentaBancaria);
	}

	@Override
	public void descongelarCuentaBancaria(BigInteger idCuentaBancaria) throws RollbackFailureException {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(idCuentaBancaria);
		if (cuentaBancaria == null)
			throw new RollbackFailureException("Cuenta bancaria no encontrada");
		if (!cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.CONGELADO))
			throw new RollbackFailureException("La cuenta no esta congelada, no se puede descongelar");
		if (cuentaBancaria.getTipoCuentaBancaria().equals(TipoCuentaBancaria.PLAZO_FIJO)) {
			Date fechaActual = Calendar.getInstance().getTime();
			Date fechaCierre = cuentaBancaria.getFechaCierre();
			if (fechaActual.compareTo(fechaCierre) != 1)
				throw new RollbackFailureException("Cuenta PLAZO_FIJO, no vencio aun, no se puede descongelar");
		}
		cuentaBancaria.setEstado(EstadoCuentaBancaria.ACTIVO);
		cuentaBancariaDAO.update(cuentaBancaria);
	}

	@Override
	public BigInteger[] renovarCuentaPlazoFijo(BigInteger idCuenta, int periodo, BigDecimal tasaInteres) throws RollbackFailureException {
		CuentaBancaria cuentaBancariaOld = cuentaBancariaDAO.find(idCuenta);
		if (cuentaBancariaOld == null)
			throw new RollbackFailureException("Cuenta bancaria no encontrada");
		if (!cuentaBancariaOld.getTipoCuentaBancaria().equals(TipoCuentaBancaria.PLAZO_FIJO))
			throw new RollbackFailureException("Solo las cuentas a plazo fijo pueden ser renovadas");
		if (!cuentaBancariaOld.getEstado().equals(EstadoCuentaBancaria.ACTIVO))
			throw new RollbackFailureException("Cuenta bancaria no activa, no se puede renovar.");

		Calendar calendar = Calendar.getInstance();
		Date fechaActual = calendar.getTime();

		if (fechaActual.compareTo(cuentaBancariaOld.getFechaCierre()) != 1)
			throw new RollbackFailureException("Cuenta aun no vence, no se puede renovar");

		// obtener datos
		Moneda moneda = cuentaBancariaOld.getMoneda();
		Set<Titular> titularesOld = cuentaBancariaOld.getTitulars();
		Set<Beneficiario> beneficiariosOld = cuentaBancariaOld.getBeneficiarios();
		Socio socio = cuentaBancariaOld.getSocio();
		PersonaNatural personaNaturalSocio = socio.getPersonaNatural();
		PersonaJuridica personaJuridicaSocio = socio.getPersonaJuridica();
		if (personaNaturalSocio == null && personaJuridicaSocio == null)
			throw new RollbackFailureException("Socio no tiene una persona asociada");
		if (personaNaturalSocio != null && personaJuridicaSocio != null)
			throw new RollbackFailureException("Socio tiene persona natural y juridica asociada");

		List<BigInteger> listaTitulares = new ArrayList<>();
		List<Beneficiario> listaBeneficiarios = new ArrayList<>();

		for (Titular titularOld : titularesOld) {
			PersonaNatural personaOld = titularOld.getPersonaNatural();
			BigInteger idPersonaOld = personaOld.getIdPersonaNatural();
			listaTitulares.add(idPersonaOld);
		}
		for (Beneficiario beneficiarioOld : beneficiariosOld) {
			Beneficiario beneficiarioNew = new Beneficiario();
			beneficiarioNew.setIdBeneficiario(null);
			beneficiarioNew.setApellidoPaterno(beneficiarioOld.getApellidoPaterno());
			beneficiarioNew.setApellidoMaterno(beneficiarioOld.getApellidoMaterno());
			beneficiarioNew.setNombres(beneficiarioOld.getNombres());
			beneficiarioNew.setNumeroDocumento(beneficiarioOld.getNumeroDocumento());
			beneficiarioNew.setPorcentajeBeneficio(beneficiarioOld.getPorcentajeBeneficio());

			listaBeneficiarios.add(beneficiarioNew);
		}

		// recuperar datos
		String codigoAgencia = ProduceObject.getCodigoAgenciaFromNumeroCuenta(cuentaBancariaOld.getNumeroCuenta());
		BigInteger idMoneda = moneda.getIdMoneda();
		TipoPersona tipoPersona = (personaNaturalSocio != null ? TipoPersona.NATURAL : TipoPersona.JURIDICA);
		BigInteger idPersona = (personaNaturalSocio != null ? personaNaturalSocio.getIdPersonaNatural() : personaJuridicaSocio.getIdPersonaJuridica());
		int cantRetirantes = cuentaBancariaOld.getCantidadRetirantes();

		// capitalizar cuenta
		capitalizarCuenta(cuentaBancariaOld.getIdCuentaBancaria());

		// crear cuenta nueva
		BigInteger idCuentaBancariaNew = create(TipoCuentaBancaria.PLAZO_FIJO, codigoAgencia, idMoneda, tasaInteres, tipoPersona, idPersona, new Integer(periodo), cantRetirantes, listaTitulares, listaBeneficiarios);
		CuentaBancaria cuentaBancariaNew = cuentaBancariaDAO.find(idCuentaBancariaNew);

		// crear transferencia
		String numeroCuentaOld = cuentaBancariaOld.getNumeroCuenta();
		String numeroCuentaNew = cuentaBancariaNew.getNumeroCuenta();
		BigDecimal montoTransferencia = cuentaBancariaOld.getSaldo();
		String referencia = "TRANSFERENCIA POR RENOVACION DE CUENTA";
		BigInteger idTransferencia = cajaSessionService.crearTransferenciaBancaria(numeroCuentaOld, numeroCuentaNew, montoTransferencia, referencia);

		// cancelar cuenta antigua
		BigInteger idCuentaBancariaOld = cuentaBancariaOld.getIdCuentaBancaria();
		cancelarCuentaBancaria(idCuentaBancariaOld);

		return new BigInteger[] { idCuentaBancariaNew, idTransferencia };
	}

	@Override
	public void recalcularCuentaPlazoFijo(BigInteger idCuenta, int periodo, BigDecimal tasaInteres) throws RollbackFailureException {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(idCuenta);
		if (cuentaBancaria == null)
			throw new RollbackFailureException("Cuenta no encontrada");
		if (!cuentaBancaria.getTipoCuentaBancaria().equals(TipoCuentaBancaria.PLAZO_FIJO))
			throw new RollbackFailureException("Solo las cuentas PLAZO_FIJO pueden ser recalculadas.");
		if (cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.INACTIVO))
			throw new RollbackFailureException("Cuenta inactiva, no se puede recalcular");

		LocalDate inicio = new LocalDate(cuentaBancaria.getFechaApertura());
		LocalDate fin = inicio.plusDays(periodo);

		Date fechaCierre = fin.toDate();

		// actualizando cuenta
		cuentaBancaria.setFechaCierre(fechaCierre);

		Set<CuentaBancariaTasa> tasas = cuentaBancaria.getCuentaBancariaTasas();
		CuentaBancariaTasa tasaInteresCuentaBancaria = null;
		for (CuentaBancariaTasa cuentaBancariaTasa : tasas) {
			tasaInteresCuentaBancaria = cuentaBancariaTasa;
		}
		if (tasaInteresCuentaBancaria == null)
			throw new RollbackFailureException("Tasa de interes no encontrada");
		tasaInteresCuentaBancaria.setValor(tasaInteres);

		cuentaBancariaDAO.update(cuentaBancaria);
		cuentaBancariaTasaDAO.update(tasaInteresCuentaBancaria);

		if (fechaCierre.compareTo(Calendar.getInstance().getTime()) == -1) {
			if (!cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.ACTIVO))
				descongelarCuentaBancaria(idCuenta);
		} else {
			if (!cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.CONGELADO))
				congelarCuentaBancaria(idCuenta);
		}
	}

	@Override
	public void capitalizarCuenta(BigInteger idCuentaBancaria) throws RollbackFailureException {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(idCuentaBancaria);
		if (cuentaBancaria == null)
			throw new RollbackFailureException("Cuenta bancaria no encontrada");
		if (!cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.ACTIVO))
			throw new RollbackFailureException("La cuenta no esta activa, no se puede cancelar");

		TipoCuentaBancaria tipoCuentaBancaria = cuentaBancaria.getTipoCuentaBancaria();
		BigDecimal capital = cuentaBancaria.getSaldo();
		BigDecimal tasaInteres = null;

		Set<CuentaBancariaTasa> tasasList = cuentaBancaria.getCuentaBancariaTasas();
		if (tasasList.size() != 1)
			throw new RollbackFailureException("Tasa de interes no encontrada, no se puede capitalizar");
		for (CuentaBancariaTasa tasa : tasasList) {
			tasaInteres = tasa.getValor();
		}

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LocalDate desde = new LocalDate(year, month, 1);
		LocalDate hasta = new LocalDate(year, month, day);
		QueryParameter queryParameter;
		List<CuentaBancariaInteresGenera> interesesGenerados;
		BigDecimal totalInteres;

		switch (tipoCuentaBancaria) {
		case AHORRO:
			queryParameter = QueryParameter.with("idCuentaBancaria", idCuentaBancaria).and("desde", desde.toDate()).and("hasta", hasta.toDate());
			interesesGenerados = cuentaBancariaInteresGeneraDAO.findByNamedQuery(CuentaBancariaInteresGenera.findByIdAndDate, queryParameter.parameters());

			totalInteres = BigDecimal.ZERO;
			for (CuentaBancariaInteresGenera cuentaBancariaInteresGenera : interesesGenerados) {
				BigDecimal interes = cuentaBancariaInteresGenera.getInteresGenerado();
				totalInteres = totalInteres.add(interes);
			}

			cuentaBancaria.setSaldo(totalInteres.add(capital));
			cuentaBancariaDAO.update(cuentaBancaria);
			break;
		case CORRIENTE:
			queryParameter = QueryParameter.with("idCuentaBancaria", idCuentaBancaria).and("desde", desde.toDate()).and("hasta", hasta.toDate());
			interesesGenerados = cuentaBancariaInteresGeneraDAO.findByNamedQuery(CuentaBancariaInteresGenera.findByIdAndDate, queryParameter.parameters());

			totalInteres = BigDecimal.ZERO;
			for (CuentaBancariaInteresGenera cuentaBancariaInteresGenera : interesesGenerados) {
				BigDecimal interes = cuentaBancariaInteresGenera.getInteresGenerado();
				totalInteres = totalInteres.add(interes);
			}

			cuentaBancaria.setSaldo(totalInteres.add(capital));
			cuentaBancariaDAO.update(cuentaBancaria);
			break;
		case PLAZO_FIJO:
			Date fechaApertura = cuentaBancaria.getFechaApertura();
			Date fechaCierre = cuentaBancaria.getFechaCierre();
			LocalDate localDateApertura = new LocalDate(fechaApertura);
			LocalDate localDateCierre = new LocalDate(fechaCierre);
			Days days = Days.daysBetween(localDateApertura, localDateCierre);

			// calcular nuevo saldo
			int periodo = days.getDays();
			BigDecimal interesGenerado = ProduceObject.getInteresPlazoFijo(capital, tasaInteres, periodo);
			cuentaBancaria.setSaldo(interesGenerado.add(capital));

			// crear tupla en interes generado
			CuentaBancariaInteresGenera bancariaInteresGenera = new CuentaBancariaInteresGenera();
			bancariaInteresGenera.setIdCuentaBancariaInteresGen(null);
			bancariaInteresGenera.setCuentaBancaria(cuentaBancaria);
			bancariaInteresGenera.setFecha(Calendar.getInstance().getTime());
			bancariaInteresGenera.setCapital(capital);
			bancariaInteresGenera.setInteresGenerado(interesGenerado);

			cuentaBancariaDAO.update(cuentaBancaria);
			cuentaBancariaInteresGeneraDAO.create(bancariaInteresGenera);
			break;
		default:
			throw new RollbackFailureException("Tipo de cuenta bancaria no identificado");
		}
	}

	@Override
	public void cancelarCuentaBancaria(BigInteger id) throws RollbackFailureException {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(id);
		if (cuentaBancaria == null)
			throw new RollbackFailureException("Cuenta bancaria no encontrada");
		if (!cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.ACTIVO))
			throw new RollbackFailureException("La cuenta no esta activa, no se puede cancelar");
		if (cuentaBancaria.getSaldo().compareTo(BigDecimal.ZERO) != 0)
			throw new RollbackFailureException("Cuenta tiene saldo diferente de cero, no se puede cancelar");

		if (cuentaBancaria.getTipoCuentaBancaria().equals(TipoCuentaBancaria.PLAZO_FIJO)) {
			if (cuentaBancaria.getFechaCierre().compareTo(Calendar.getInstance().getTime()) == 1)
				throw new RollbackFailureException("La cuenta PLAZO_FIJO tiene fecha de cierre aun no vencida");
		} else {
			cuentaBancaria.setFechaCierre(Calendar.getInstance().getTime());
		}
		cuentaBancaria.setEstado(EstadoCuentaBancaria.INACTIVO);
		cuentaBancariaDAO.update(cuentaBancaria);
	}

	@Override
	public BigInteger addBeneficiario(BigInteger id, Beneficiario beneficiario) throws RollbackFailureException {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(id);
		if (cuentaBancaria == null)
			throw new RollbackFailureException("Cuenta bancaria no encotrada");
		if (cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.INACTIVO))
			throw new RollbackFailureException("Cuena INACTIVA, no se puede modificar beneficiarios");
		beneficiario.setIdBeneficiario(null);
		beneficiario.setCuentaBancaria(cuentaBancaria);

		// validar beneficiario
		Set<ConstraintViolation<Beneficiario>> violations = validator.validate(beneficiario);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		beneficiarioDAO.create(beneficiario);
		return beneficiario.getIdBeneficiario();
	}

	@Override
	public BigInteger addTitular(BigInteger idCuenta, Titular titular) throws RollbackFailureException {
		CuentaBancaria cuentaBancaria = cuentaBancariaDAO.find(idCuenta);
		if (cuentaBancaria == null)
			throw new RollbackFailureException("Cuenta bancaria no encotrada");
		if (cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.INACTIVO))
			throw new RollbackFailureException("Cuenta bancaria inactiva, no se puede modificar titulares");

		PersonaNatural personaNatural = titular.getPersonaNatural();
		personaNatural = personaNaturalService.find(personaNatural.getTipoDocumento().getIdTipoDocumento(), personaNatural.getNumeroDocumento());

		Set<Titular> titulresDB = cuentaBancaria.getTitulars();
		for (Titular titDB : titulresDB) {
			if (titDB.getPersonaNatural().equals(personaNatural))
				if (titDB.getEstado())
					throw new RollbackFailureException("Titular ya existente");
		}

		if (personaNatural == null)
			throw new RollbackFailureException("Persona para titular no encontrado");

		titular.setPersonaNatural(personaNatural);
		titular.setIdTitular(null);
		titular.setCuentaBancaria(cuentaBancaria);
		titular.setEstado(true);
		titular.setFechaFin(null);
		titular.setFechaInicio(Calendar.getInstance().getTime());

		// validar beneficiario
		Set<ConstraintViolation<Titular>> violations = validator.validate(titular);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}

		titularDAO.create(titular);
		return titular.getIdTitular();
	}

	@Override
	public void updateBeneficiario(BigInteger idBeneficiario, Beneficiario beneficiario) throws NonexistentEntityException, RollbackFailureException {
		Beneficiario beneficiarioDB = beneficiarioDAO.find(idBeneficiario);
		if (beneficiarioDB == null)
			throw new NonexistentEntityException("Beneficiario no encontrado");
		CuentaBancaria cuentaBancaria = beneficiarioDB.getCuentaBancaria();
		if (cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.INACTIVO))
			throw new NonexistentEntityException("Cuenta INACTIVA, no se puede modificar los beneficiarios");

		beneficiario.setIdBeneficiario(idBeneficiario);
		beneficiarioDAO.update(beneficiario);
	}
	
	@Override
	public void deleteBeneficiario(BigInteger idBeneficiario) throws NonexistentEntityException, RollbackFailureException{
		Beneficiario beneficiario = beneficiarioDAO.find(idBeneficiario);
		if(beneficiario == null)
			throw new NonexistentEntityException("Beneficiario no encontrado");
		CuentaBancaria cuentaBancaria = beneficiario.getCuentaBancaria();
		if(cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.INACTIVO))
			throw new RollbackFailureException("Cuenta INACTIVA, no se puede modificar los beneficiarios");
		beneficiarioDAO.delete(beneficiario);
	}

	@Override
	public void updateTitular(BigInteger idTitular, Titular titular) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTitular(BigInteger idTitular) throws NonexistentEntityException, RollbackFailureException {
		Titular titular = titularDAO.find(idTitular);
		if(titular == null)
			throw new NonexistentEntityException("Titular no existente");
		
		//verificando que no se elimine el titular principal
		PersonaNatural personaTitular = titular.getPersonaNatural();
		
		CuentaBancaria cuentaBancaria = titular.getCuentaBancaria();
		Socio socio = cuentaBancaria.getSocio();
		PersonaNatural personaNatural = socio.getPersonaNatural();
		PersonaJuridica personaJuridica = socio.getPersonaJuridica();
		
		if(cuentaBancaria.getEstado().equals(EstadoCuentaBancaria.INACTIVO))
			throw new RollbackFailureException("Cuenta INACTIVA, no se pueden modificar titulares");
		if(personaNatural != null){
			if(personaTitular.equals(personaNatural))
				throw new NonexistentEntityException("No se puede eliminar el titular principal");
		}
		if(personaJuridica != null)
			if(personaTitular.equals(personaJuridica.getRepresentanteLegal()))
				throw new NonexistentEntityException("No se puede eliminar el titular principal");
		
		titularDAO.delete(titular);
	}

}
