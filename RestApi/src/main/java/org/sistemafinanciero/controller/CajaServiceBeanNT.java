package org.sistemafinanciero.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.entity.BovedaCaja;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.CuentaAporte;
import org.sistemafinanciero.entity.CuentaBancaria;
import org.sistemafinanciero.entity.DetalleHistorialCaja;
import org.sistemafinanciero.entity.HistorialCaja;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.MonedaDenominacion;
import org.sistemafinanciero.entity.PendienteCaja;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.Socio;
import org.sistemafinanciero.entity.TransaccionBancaria;
import org.sistemafinanciero.entity.TransaccionBovedaCaja;
import org.sistemafinanciero.entity.TransaccionCajaCaja;
import org.sistemafinanciero.entity.TransaccionCompraVenta;
import org.sistemafinanciero.entity.TransaccionCuentaAporte;
import org.sistemafinanciero.entity.VariableSistema;
import org.sistemafinanciero.entity.dto.CajaCierreMoneda;
import org.sistemafinanciero.entity.dto.GenericDetalle;
import org.sistemafinanciero.entity.dto.ResumenOperacionesCaja;
import org.sistemafinanciero.entity.dto.VoucherCompraVenta;
import org.sistemafinanciero.entity.dto.VoucherTransaccionBancaria;
import org.sistemafinanciero.entity.dto.VoucherTransaccionCuentaAporte;
import org.sistemafinanciero.entity.dto.VoucherTransferenciaBancaria;
import org.sistemafinanciero.entity.type.TipoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoPendiente;
import org.sistemafinanciero.entity.type.Tipotransaccionbancaria;
import org.sistemafinanciero.entity.type.Tipotransaccioncompraventa;
import org.sistemafinanciero.entity.type.TransaccionBovedaCajaOrigen;
import org.sistemafinanciero.entity.type.Variable;
import org.sistemafinanciero.service.nt.CajaServiceNT;
import org.sistemafinanciero.service.nt.VariableSistemaServiceNT;

@Named
@Stateless
@Remote(CajaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaServiceBeanNT implements CajaServiceNT {

	@Inject
	private DAO<Object, Caja> cajaDAO;

	@Inject
	private DAO<Object, HistorialCaja> historialCajaDAO;

	@Inject
	private DAO<Object, TransaccionCuentaAporte> transaccionCuentaAporteDAO;

	@Inject
	private DAO<Object, TransaccionBancaria> transaccionBancariaDAO;

	@Inject
	private DAO<Object, TransaccionCompraVenta> transaccionCopraVentaDAO;

	@EJB
	private VariableSistemaServiceNT variableSistemaService;

	@Override
	public Caja findById(BigInteger id) {
		return cajaDAO.find(id);
	}

	@Override
	public List<Caja> findAll() {
		return cajaDAO.findAll();
	}

	@Override
	public int count() {
		return cajaDAO.count();
	}

	@Override
	public Set<Boveda> getBovedas(BigInteger idCaja) {
		Set<Boveda> result = null;
		Caja caja = cajaDAO.find(idCaja);
		if (caja != null) {
			result = new HashSet<Boveda>();
			Set<BovedaCaja> bovedaCajas = caja.getBovedaCajas();
			for (BovedaCaja bovedaCaja : bovedaCajas) {
				Boveda boveda = bovedaCaja.getBoveda();
				Moneda moneda = boveda.getMoneda();
				Hibernate.initialize(boveda);
				Hibernate.initialize(moneda);
				result.add(boveda);
			}
		}
		return result;
	}

	@Override
	public Set<CajaCierreMoneda> getVoucherCierreCaja(BigInteger idHistorial) {
		Set<CajaCierreMoneda> result;

		Agencia agencia = null;
		HistorialCaja historialCaja = historialCajaDAO.find(idHistorial);
		Caja caja = historialCaja.getCaja();

		// recuperando agencia
		Set<BovedaCaja> bovedaCajas = caja.getBovedaCajas();
		for (BovedaCaja bovedaCaja : bovedaCajas) {
			agencia = bovedaCaja.getBoveda().getAgencia();
			break;
		}
		if (agencia == null)
			return null;

		// recuperando el historial del dia anterior
		HistorialCaja historialAyer = null;
		QueryParameter queryParameter;
		queryParameter = QueryParameter.with("idcaja", caja.getIdCaja()).and("fecha", historialCaja.getFechaApertura());
		List<HistorialCaja> list2 = historialCajaDAO.findByNamedQuery(HistorialCaja.findByHistorialDateRangePenultimo, queryParameter.parameters(), 2);
		for (HistorialCaja hist : list2) {
			if (!historialCaja.equals(hist))
				historialAyer = hist;
		}

		// recuperando las monedas de la trasaccion
		Set<DetalleHistorialCaja> detalleHistorial = historialCaja.getDetalleHistorialCajas();
		Set<Moneda> monedasTransaccion = new HashSet<Moneda>();
		for (DetalleHistorialCaja detHistcaja : detalleHistorial) {
			Moneda moneda = detHistcaja.getMonedaDenominacion().getMoneda();
			if (!monedasTransaccion.contains(moneda)) {
				monedasTransaccion.add(moneda);
			}
		}

		// poniendo los datos por moneda
		result = new HashSet<CajaCierreMoneda>();
		for (Moneda moneda : monedasTransaccion) {
			CajaCierreMoneda cajaCierreMoneda = new CajaCierreMoneda();
			cajaCierreMoneda.setAgencia(agencia.getDenominacion());
			cajaCierreMoneda.setCaja(caja.getDenominacion());
			cajaCierreMoneda.setFechaApertura(historialCaja.getFechaApertura());
			cajaCierreMoneda.setFechaCierre(historialCaja.getFechaCierre());
			cajaCierreMoneda.setHoraApertura(historialCaja.getHoraApertura());
			cajaCierreMoneda.setHoraCierre(historialCaja.getHoraCierre());
			cajaCierreMoneda.setMoneda(moneda);
			cajaCierreMoneda.setTrabajador(historialCaja.getTrabajador());

			BigDecimal saldoAyer = BigDecimal.ZERO;
			BigDecimal entradas = BigDecimal.ZERO;
			BigDecimal salidas = BigDecimal.ZERO;
			BigDecimal porDevolver = BigDecimal.ZERO;
			BigDecimal sobrante = BigDecimal.ZERO;
			BigDecimal faltante = BigDecimal.ZERO;

			cajaCierreMoneda.setSaldoAyer(saldoAyer);
			cajaCierreMoneda.setEntradas(entradas);
			cajaCierreMoneda.setSalidas(salidas);
			cajaCierreMoneda.setPorDevolver(porDevolver);
			cajaCierreMoneda.setSobrante(sobrante);
			cajaCierreMoneda.setFaltante(faltante);

			/*********** Añadiendo el detalle de una moneda ***************/
			result.add(cajaCierreMoneda);

			// poniendo el detalle
			Set<GenericDetalle> detalle = new TreeSet<GenericDetalle>();
			cajaCierreMoneda.setDetalle(detalle);
			for (DetalleHistorialCaja detHistcaja : detalleHistorial) {
				MonedaDenominacion denominacion = detHistcaja.getMonedaDenominacion();
				Moneda moneda2 = denominacion.getMoneda();
				BigInteger cantidad = detHistcaja.getCantidad();
				if (moneda.equals(moneda2)) {
					if (cantidad.compareTo(BigInteger.ZERO) > 0) {
						detalle.add(new GenericDetalle(denominacion.getValor(), detHistcaja.getCantidad()));
					}
				}
			}

			// recuperando saldo del dia anterior
			if (historialAyer == null) {
				saldoAyer = BigDecimal.ZERO;
			} else {
				for (DetalleHistorialCaja detHistCaja : historialAyer.getDetalleHistorialCajas()) {
					MonedaDenominacion denominacion = detHistCaja.getMonedaDenominacion();
					Moneda moneda2 = denominacion.getMoneda();
					if (moneda.equals(moneda2)) {
						BigDecimal subTotal = denominacion.getValor().multiply(new BigDecimal(detHistCaja.getCantidad()));
						saldoAyer = saldoAyer.add(subTotal);
					}
				}
			}

			// recuperando las operaciones del dia
			Set<TransaccionBancaria> transBancarias = historialCaja.getTransaccionBancarias();
			Set<TransaccionCompraVenta> transComVent = historialCaja.getTransaccionCompraVentas();
			Set<TransaccionCuentaAporte> transCtaAport = historialCaja.getTransaccionCuentaAportes();
			for (TransaccionBancaria transBanc : transBancarias) {
				Moneda moneda2 = transBanc.getCuentaBancaria().getMoneda();
				if (moneda.equals(moneda2)) {
					if (transBanc.getMonto().compareTo(BigDecimal.ZERO) >= 0)
						entradas = entradas.add(transBanc.getMonto());
					else
						salidas = salidas.add(transBanc.getMonto());
				}
			}
			for (TransaccionCompraVenta transCompVent : transComVent) {
				Moneda monedaRecibida = transCompVent.getMonedaRecibida();
				Moneda monedaEntregada = transCompVent.getMonedaEntregada();
				if (moneda.equals(monedaRecibida)) {
					entradas = entradas.add(transCompVent.getMontoRecibido());
				}
				if (moneda.equals(monedaEntregada)) {
					salidas = salidas.add(transCompVent.getMontoEntregado());
				}
			}
			for (TransaccionCuentaAporte transAport : transCtaAport) {
				Moneda moneda2 = transAport.getCuentaAporte().getMoneda();
				if (moneda.equals(moneda2)) {
					if (transAport.getMonto().compareTo(BigDecimal.ZERO) >= 0)
						entradas = entradas.add(transAport.getMonto());
					else
						salidas = salidas.add(transAport.getMonto());
				}
			}

			// recuperando faltantes y sobrantes
			Set<PendienteCaja> listPendientes = historialCaja.getPendienteCajas();
			for (PendienteCaja pendiente : listPendientes) {
				Moneda moneda2 = pendiente.getMoneda();
				if (moneda.equals(moneda2)) {
					if (pendiente.getMonto().compareTo(BigDecimal.ZERO) >= 0)
						sobrante = sobrante.add(pendiente.getMonto());
					else
						faltante = faltante.add(pendiente.getMonto());
				}
			}
		}

		return result;
	}

	@Override
	public ResumenOperacionesCaja getResumenOperacionesCaja(BigInteger idHistorial) {
		Agencia agencia = null;
		HistorialCaja historialCaja = historialCajaDAO.find(idHistorial);
		Caja caja = historialCaja.getCaja();
		// recuperando agencia
		Set<BovedaCaja> bovedaCajas = caja.getBovedaCajas();
		for (BovedaCaja bovedaCaja : bovedaCajas) {
			agencia = bovedaCaja.getBoveda().getAgencia();
			break;
		}
		if (agencia == null)
			return null;

		ResumenOperacionesCaja result = new ResumenOperacionesCaja();

		int depositosAhorro = 0;
		int retirosAhorro = 0;
		int depositosCorriente = 0;
		int retirosCorriente = 0;
		int depositosPlazoFijo = 0;
		int retirosPlazoFijo = 0;
		int depositosAporte = 0;
		int retirosAporte = 0;

		int compra = 0;
		int venta = 0;

		int depositosMayorCuantia = 0;
		int retirosMayorCuantia = 0;
		int compraVentaMayorCuantia = 0;

		int transCajaCajaRecibido = 0;
		int transCajaCajaEnviado = 0;
		int transBovedaCajaRecibido = 0;
		int transBovedaCajaEnviado = 0;

		int pendienteFaltante = 0;
		int pendienteSobrante = 0;

		// recuperando las operaciones del dia
		Set<TransaccionBancaria> transBancarias = historialCaja.getTransaccionBancarias();
		Set<TransaccionCompraVenta> transComVent = historialCaja.getTransaccionCompraVentas();
		Set<TransaccionCuentaAporte> transCtaAport = historialCaja.getTransaccionCuentaAportes();

		Set<TransaccionCajaCaja> transCajaCajaEnviados = historialCaja.getTransaccionCajaCajasForIdCajaHistorialOrigen();
		Set<TransaccionCajaCaja> transCajaCajaRecibidos = historialCaja.getTransaccionCajaCajasForIdCajaHistorialDestino();
		Set<TransaccionBovedaCaja> transBovedaCaja = historialCaja.getTransaccionBovedaCajas();

		Set<PendienteCaja> transPendiente = historialCaja.getPendienteCajas();

		VariableSistema varSoles = variableSistemaService.getVariable(Variable.TRANSACCION_MAYOR_CUANTIA_NUEVOS_SOLES);
		VariableSistema varDolares = variableSistemaService.getVariable(Variable.TRANSACCION_MAYOR_CUANTIA_DOLARES);
		VariableSistema varEuros = variableSistemaService.getVariable(Variable.TRANSACCION_MAYOR_CUANTIA_EUROS);

		for (TransaccionBancaria transBanc : transBancarias) {
			TipoCuentaBancaria tipoCuenta = transBanc.getCuentaBancaria().getTipoCuentaBancaria();
			if (tipoCuenta.equals(TipoCuentaBancaria.AHORRO)) {
				if (transBanc.getTipoTransaccion().equals(Tipotransaccionbancaria.DEPOSITO))
					depositosAhorro++;
				else
					retirosAhorro++;
			}
			if (tipoCuenta.equals(TipoCuentaBancaria.CORRIENTE)) {
				if (transBanc.getTipoTransaccion().equals(Tipotransaccionbancaria.DEPOSITO))
					depositosCorriente++;
				else
					retirosCorriente++;
			}
			if (tipoCuenta.equals(TipoCuentaBancaria.PLAZO_FIJO)) {
				if (transBanc.getTipoTransaccion().equals(Tipotransaccionbancaria.DEPOSITO))
					depositosPlazoFijo++;
				else
					retirosPlazoFijo++;
			}

			// mayor cuantia
			if (transBanc.getCuentaBancaria().getMoneda().getIdMoneda().equals(new BigInteger("1"))) {
				if (transBanc.getMonto().abs().compareTo(varSoles.getValor()) >= 0)
					if (transBanc.getTipoTransaccion().equals(Tipotransaccionbancaria.DEPOSITO))
						depositosMayorCuantia++;
					else
						retirosMayorCuantia++;
			}
			if (transBanc.getCuentaBancaria().getMoneda().getIdMoneda().equals(new BigInteger("2"))) {
				if (transBanc.getMonto().abs().compareTo(varDolares.getValor()) >= 0)
					if (transBanc.getTipoTransaccion().equals(Tipotransaccionbancaria.DEPOSITO))
						depositosMayorCuantia++;
					else
						retirosMayorCuantia++;
			}
			if (transBanc.getCuentaBancaria().getMoneda().getIdMoneda().equals(new BigInteger("3"))) {
				if (transBanc.getMonto().abs().compareTo(varEuros.getValor()) >= 0)
					if (transBanc.getTipoTransaccion().equals(Tipotransaccionbancaria.DEPOSITO))
						depositosMayorCuantia++;
					else
						retirosMayorCuantia++;
			}

		}
		for (TransaccionCompraVenta transCompraVenta : transComVent) {
			if (transCompraVenta.equals(Tipotransaccioncompraventa.COMPRA))
				compra++;
			else
				venta++;
		}
		for (TransaccionCuentaAporte trans : transCtaAport) {
			if (trans.getTipoTransaccion().equals(Tipotransaccionbancaria.DEPOSITO))
				depositosAporte++;
			else
				retirosAporte++;
		}

		transCajaCajaEnviado = transCajaCajaEnviados.size();
		transCajaCajaRecibido = transCajaCajaRecibidos.size();

		for (TransaccionBovedaCaja transBovCaj : transBovedaCaja) {
			if (transBovCaj.getEstadoSolicitud() && transBovCaj.getEstadoConfirmacion()) {
				if (transBovCaj.getOrigen().equals(TransaccionBovedaCajaOrigen.CAJA))
					transBovedaCajaEnviado++;
				else
					transBovedaCajaRecibido++;
			}
		}

		for (PendienteCaja pendiente : transPendiente) {
			if (pendiente.getTipoPendiente().equals(TipoPendiente.FALTANTE))
				pendienteFaltante++;
			else
				pendienteSobrante++;
		}

		result.setAgencia(agencia.getDenominacion());
		result.setCaja(caja.getDenominacion());
		result.setFechaApertura(historialCaja.getFechaApertura());
		result.setHoraApertura(historialCaja.getHoraApertura());
		result.setFechaCierre(historialCaja.getFechaCierre());
		result.setHoraCierre(historialCaja.getHoraCierre());
		result.setTrabajador(historialCaja.getTrabajador());

		result.setDepositosAhorro(depositosAhorro);
		result.setRetirosAhorro(retirosAhorro);
		result.setDepositosCorriente(depositosCorriente);
		result.setRetirosCorriente(retirosCorriente);
		result.setDepositosPlazoFijo(depositosPlazoFijo);
		result.setRetirosPlazoFijo(retirosPlazoFijo);
		result.setDepositosAporte(depositosAporte);
		result.setRetirosAporte(retirosAporte);

		result.setCompra(compra);
		result.setVenta(venta);

		result.setDepositoMayorCuantia(depositosMayorCuantia);
		result.setRetiroMayorCuantia(retirosMayorCuantia);
		result.setCompraVentaMayorCuantia(compraVentaMayorCuantia);

		result.setEnviadoCajaCaja(transCajaCajaEnviado);
		result.setRecibidoCajaCaja(transCajaCajaRecibido);
		result.setEnviadoBovedaCaja(transBovedaCajaEnviado);
		result.setRecibidoBovedaCaja(transBovedaCajaRecibido);

		result.setPendienteFaltante(pendienteFaltante);
		result.setPendienteSobrante(pendienteSobrante);

		return result;
	}

	@Override
	public VoucherTransaccionCuentaAporte getVoucherCuentaAporte(BigInteger idTransaccion) {
		VoucherTransaccionCuentaAporte voucherTransaccion = new VoucherTransaccionCuentaAporte();

		// recuperando transaccion
		TransaccionCuentaAporte transaccionCuentaAporte = transaccionCuentaAporteDAO.find(idTransaccion);
		CuentaAporte cuentaAporte = transaccionCuentaAporte.getCuentaAporte();
		Socio socio = new Socio();
		Set<Socio> socios = cuentaAporte.getSocios();
		if (socios.size() == 1) {
			for (Socio socioBuscado : socios) {
				socio = socioBuscado;
			}
		}
		Caja caja = transaccionCuentaAporte.getHistorialCaja().getCaja();
		Set<BovedaCaja> list = caja.getBovedaCajas();
		Agencia agencia = null;
		for (BovedaCaja bovedaCaja : list) {
			agencia = bovedaCaja.getBoveda().getAgencia();
			break;
		}

		// Poniendo datos de transaccion
		Moneda moneda = transaccionCuentaAporte.getCuentaAporte().getMoneda();
		Hibernate.initialize(moneda);
		voucherTransaccion.setMoneda(moneda);

		voucherTransaccion.setIdTransaccion(transaccionCuentaAporte.getIdTransaccionCuentaAporte());
		voucherTransaccion.setFecha(transaccionCuentaAporte.getFecha());
		voucherTransaccion.setHora(transaccionCuentaAporte.getHora());
		voucherTransaccion.setNumeroOperacion(transaccionCuentaAporte.getNumeroOperacion());
		voucherTransaccion.setMonto(transaccionCuentaAporte.getMonto());
		voucherTransaccion.setReferencia(transaccionCuentaAporte.getReferencia());
		voucherTransaccion.setTipoTransaccion(transaccionCuentaAporte.getTipoTransaccion());

		// Poniendo datos de cuenta bancaria
		voucherTransaccion.setNumeroCuenta(cuentaAporte.getNumeroCuenta());
		voucherTransaccion.setSaldoDisponible(cuentaAporte.getSaldo());
		voucherTransaccion.setObservacion(transaccionCuentaAporte.getObservacion());

		// Poniendo datos de agencia
		voucherTransaccion.setAgenciaDenominacion(agencia.getDenominacion());
		voucherTransaccion.setAgenciaAbreviatura(agencia.getAbreviatura());

		// Poniendo datos de caja
		voucherTransaccion.setCajaDenominacion(caja.getDenominacion());
		voucherTransaccion.setCajaAbreviatura(caja.getAbreviatura());

		// Poniendo datos del socio
		PersonaNatural personaNatural = socio.getPersonaNatural();
		PersonaJuridica personaJuridica = socio.getPersonaJuridica();
		if (personaJuridica == null) {
			voucherTransaccion.setIdSocio(socio.getIdSocio());
			voucherTransaccion.setTipoDocumento(socio.getPersonaNatural().getTipoDocumento()); //
			voucherTransaccion.setNumeroDocumento(socio.getPersonaNatural().getNumeroDocumento());
			voucherTransaccion.setSocio(personaNatural.getApellidoPaterno() + " " + personaNatural.getApellidoMaterno() + ", " + personaNatural.getNombres());
		}
		if (personaNatural == null) {
			voucherTransaccion.setIdSocio(socio.getIdSocio());
			voucherTransaccion.setTipoDocumento(socio.getPersonaJuridica().getTipoDocumento()); //
			voucherTransaccion.setNumeroDocumento(socio.getPersonaJuridica().getNumeroDocumento());
			voucherTransaccion.setSocio(personaJuridica.getRazonSocial());
		}
		return voucherTransaccion;
	}

	@Override
	public VoucherTransaccionBancaria getVoucherTransaccionBancaria(BigInteger idTransaccionBancaria) {
		VoucherTransaccionBancaria voucherTransaccion = new VoucherTransaccionBancaria();

		// recuperando transaccion
		TransaccionBancaria transaccionBancaria = transaccionBancariaDAO.find(idTransaccionBancaria);
		CuentaBancaria cuentaBancaria = transaccionBancaria.getCuentaBancaria();
		Socio socio = cuentaBancaria.getSocio();
		Caja caja = transaccionBancaria.getHistorialCaja().getCaja();
		Set<BovedaCaja> list = caja.getBovedaCajas();
		Agencia agencia = null;
		for (BovedaCaja bovedaCaja : list) {
			agencia = bovedaCaja.getBoveda().getAgencia();
			break;
		}

		// Poniendo datos de transaccion
		voucherTransaccion.setIdTransaccionBancaria(transaccionBancaria.getIdTransaccionBancaria());
		Moneda moneda = transaccionBancaria.getMoneda();
		Hibernate.initialize(moneda);
		voucherTransaccion.setMoneda(moneda);

		voucherTransaccion.setFecha(transaccionBancaria.getFecha());
		voucherTransaccion.setHora(transaccionBancaria.getHora());
		voucherTransaccion.setNumeroOperacion(transaccionBancaria.getNumeroOperacion());
		voucherTransaccion.setMonto(transaccionBancaria.getMonto());
		voucherTransaccion.setReferencia(transaccionBancaria.getReferencia());
		voucherTransaccion.setTipoTransaccion(transaccionBancaria.getTipoTransaccion());
		voucherTransaccion.setObservacion(transaccionBancaria.getObservacion());

		// Poniendo datos de cuenta bancaria
		voucherTransaccion.setTipoCuentaBancaria(cuentaBancaria.getTipoCuentaBancaria());
		voucherTransaccion.setNumeroCuenta(cuentaBancaria.getNumeroCuenta());
		voucherTransaccion.setSaldoDisponible(cuentaBancaria.getSaldo());

		// Poniendo datos de agencia
		voucherTransaccion.setAgenciaDenominacion(agencia.getDenominacion());
		voucherTransaccion.setAgenciaAbreviatura(agencia.getAbreviatura());

		// Poniendo datos de caja
		voucherTransaccion.setCajaDenominacion(caja.getDenominacion());
		voucherTransaccion.setCajaAbreviatura(caja.getAbreviatura());

		// Poniendo datos del socio
		PersonaNatural personaNatural = socio.getPersonaNatural();
		PersonaJuridica personaJuridica = socio.getPersonaJuridica();
		if (personaJuridica == null) {
			voucherTransaccion.setIdSocio(socio.getIdSocio());
			voucherTransaccion.setTipoDocumento(socio.getPersonaNatural().getTipoDocumento()); //
			voucherTransaccion.setNumeroDocumento(socio.getPersonaNatural().getNumeroDocumento());
			voucherTransaccion.setSocio(personaNatural.getApellidoPaterno() + " " + personaNatural.getApellidoMaterno() + ", " + personaNatural.getNombres());
		}
		if (personaNatural == null) {
			voucherTransaccion.setIdSocio(socio.getIdSocio());
			voucherTransaccion.setTipoDocumento(socio.getPersonaJuridica().getTipoDocumento()); //
			voucherTransaccion.setNumeroDocumento(socio.getPersonaJuridica().getNumeroDocumento());
			voucherTransaccion.setSocio(personaJuridica.getRazonSocial());
		}
		return voucherTransaccion;
	}

	@Override
	public VoucherTransferenciaBancaria getVoucherTransferenciaBancaria(BigInteger idTransferencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoucherCompraVenta getVoucherCompraVenta(BigInteger idTransaccionCompraVenta) {
		VoucherCompraVenta voucherCompraVenta = new VoucherCompraVenta();

		// recuperando transaccion
		TransaccionCompraVenta compraVenta = transaccionCopraVentaDAO.find(idTransaccionCompraVenta);

		Caja caja = compraVenta.getHistorialCaja().getCaja();
		Set<BovedaCaja> list = caja.getBovedaCajas();
		Agencia agencia = null;
		for (BovedaCaja bovedaCaja : list) {
			agencia = bovedaCaja.getBoveda().getAgencia();
			break;
		}

		// Poniendo datos de transaccion
		Moneda monedaEntregada = compraVenta.getMonedaEntregada();
		Moneda monedaRecibida = compraVenta.getMonedaRecibida();
		Hibernate.initialize(monedaEntregada);
		Hibernate.initialize(monedaRecibida);

		voucherCompraVenta.setIdCompraVenta(compraVenta.getIdTransaccionCompraVenta());
		voucherCompraVenta.setFecha(compraVenta.getFecha());
		voucherCompraVenta.setHora(compraVenta.getHora());
		voucherCompraVenta.setMonedaEntregada(monedaEntregada);
		voucherCompraVenta.setMonedaRecibida(monedaRecibida);
		voucherCompraVenta.setMontoRecibido(compraVenta.getMontoRecibido());
		voucherCompraVenta.setMontoEntregado(compraVenta.getMontoEntregado());
		voucherCompraVenta.setEstado(compraVenta.getEstado());

		voucherCompraVenta.setNumeroOperacion(compraVenta.getNumeroOperacion());
		voucherCompraVenta.setObservacion(compraVenta.getObservacion());
		voucherCompraVenta.setReferencia(compraVenta.getCliente());
		voucherCompraVenta.setTipoCambio(compraVenta.getTipoCambio());
		voucherCompraVenta.setTipoTransaccion(compraVenta.getTipoTransaccion());

		// Poniendo datos de agencia
		voucherCompraVenta.setAgenciaDenominacion(agencia.getDenominacion());
		voucherCompraVenta.setAgenciaAbreviatura(agencia.getAbreviatura());

		// Poniendo datos de caja
		voucherCompraVenta.setCajaDenominacion(caja.getDenominacion());
		voucherCompraVenta.setCajaAbreviatura(caja.getAbreviatura());

		return voucherCompraVenta;
	}

}
