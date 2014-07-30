package org.sistemafinanciero.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

import org.hibernate.Hibernate;
import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.entity.BovedaCaja;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.DetalleHistorialCaja;
import org.sistemafinanciero.entity.HistorialCaja;
import org.sistemafinanciero.entity.HistorialTransaccionCaja;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.MonedaDenominacion;
import org.sistemafinanciero.entity.PendienteCaja;
import org.sistemafinanciero.entity.TransaccionBovedaCajaView;
import org.sistemafinanciero.entity.TransaccionCajaCaja;
import org.sistemafinanciero.entity.dto.GenericDetalle;
import org.sistemafinanciero.entity.dto.GenericMonedaDetalle;
import org.sistemafinanciero.entity.type.TransaccionBovedaCajaOrigen;
import org.sistemafinanciero.service.nt.CajaSessionServiceNT;
import org.sistemafinanciero.service.nt.MonedaServiceNT;
import org.sistemafinanciero.util.Guard;
import org.sistemafinanciero.util.UsuarioSession;

@Stateless
@Named
@Interceptors(Guard.class)
@Remote(CajaSessionServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaSessionServiceBeanNT implements CajaSessionServiceNT {

	@Inject
	private UsuarioSession usuarioSession;

	@Inject
	private DAO<Object, Caja> cajaDAO;

	@Inject
	private DAO<Object, HistorialCaja> historialCajaDAO;

	@Inject
	private DAO<Object, HistorialTransaccionCaja> historialTransaccionCajaDAO;

	@Inject
	private DAO<Object, TransaccionBovedaCajaView> transaccionBovedaCajaViewDAO;

	@EJB
	private MonedaServiceNT monedaService;

	private Caja getCaja() {
		String username = usuarioSession.getUsername();
		QueryParameter queryParameter = QueryParameter.with("usuario", username);
		List<Caja> list = cajaDAO.findByNamedQuery(Caja.findByUsername, queryParameter.parameters());
		if (list.size() <= 1) {
			Caja caja = null;
			for (Caja c : list) {
				caja = c;
			}
			return caja;
		} else {
			System.out.println("Error: mas de un usuario registrado");
			return null;
		}
	}

	private HistorialCaja getHistorialActivo() {
		Caja caja = getCaja();
		HistorialCaja cajaHistorial = null;
		QueryParameter queryParameter = QueryParameter.with("idcaja", caja.getIdCaja());
		List<HistorialCaja> list = historialCajaDAO.findByNamedQuery(HistorialCaja.findByHistorialActivo, queryParameter.parameters());
		for (HistorialCaja c : list) {
			cajaHistorial = c;
			break;
		}
		return cajaHistorial;
	}

	@Override
	public Map<Boveda, BigDecimal> getDiferenciaSaldoCaja(Set<GenericMonedaDetalle> detalleCaja) {
		Map<Boveda, BigDecimal> result = new HashMap<Boveda, BigDecimal>();
		Caja caja = getCaja();
		Set<BovedaCaja> bovedas = caja.getBovedaCajas();
		for (BovedaCaja bovedaCaja : bovedas) {
			Moneda moneda = bovedaCaja.getBoveda().getMoneda();
			for (GenericMonedaDetalle detalle : detalleCaja) {
				if (moneda.equals(detalle.getMoneda())) {
					if (bovedaCaja.getSaldo().compareTo(detalle.getTotal()) != 0) {
						Boveda boveda = bovedaCaja.getBoveda();
						Hibernate.initialize(boveda);
						BigDecimal diferencia = bovedaCaja.getSaldo().subtract(detalle.getTotal());
						result.put(boveda, diferencia);
					}
					break;
				}
			}
		}
		return result;
	}

	@Override
	public Set<Moneda> getMonedas() {
		Caja caja = getCaja();
		Set<BovedaCaja> bovedaCajas = caja.getBovedaCajas();
		Set<Moneda> result = new HashSet<>();
		for (BovedaCaja bovedaCaja : bovedaCajas) {
			Boveda boveda = bovedaCaja.getBoveda();
			Moneda moneda = boveda.getMoneda();
			Hibernate.initialize(moneda);
			result.add(moneda);
		}
		return result;
	}

	@Override
	public Set<GenericMonedaDetalle> getDetalleCaja() {
		Set<GenericMonedaDetalle> result = null;
		Caja caja = getCaja();
		if (caja == null)
			return null;
		// recuperando el historial activo
		HistorialCaja cajaHistorial = getHistorialActivo();

		// recorrer por todas las bovedas
		Set<BovedaCaja> bovedas = caja.getBovedaCajas();
		result = new HashSet<GenericMonedaDetalle>();
		for (BovedaCaja bovedaCaja : bovedas) {
			Boveda boveda = bovedaCaja.getBoveda();
			Moneda moneda = boveda.getMoneda();
			Hibernate.initialize(moneda);
			GenericMonedaDetalle genericMonedaDetalle = new GenericMonedaDetalle(moneda);
			// recorrer todas las denominaciones existentes en la base de datos
			List<MonedaDenominacion> denominacionesExistentes = monedaService.getDenominaciones(moneda.getIdMoneda());
			for (MonedaDenominacion m : denominacionesExistentes) {
				GenericDetalle detalle = new GenericDetalle(m.getValor(), BigInteger.ZERO);
				genericMonedaDetalle.addElementDetalleReplacingIfExist(detalle);
				genericMonedaDetalle.setMoneda(m.getMoneda());
			}
			// si tiene historiales activos reemplazar por cantidades
			if (cajaHistorial != null) {
				for (DetalleHistorialCaja d : cajaHistorial.getDetalleHistorialCajas()) {
					Moneda monedaHistorial = d.getMonedaDenominacion().getMoneda();
					if (monedaHistorial.equals(moneda)) {
						GenericDetalle detalle = new GenericDetalle(d.getMonedaDenominacion().getValor(), d.getCantidad());
						genericMonedaDetalle.addElementDetalleReplacingIfExist(detalle);
					}
				}
				;
			}
			result.add(genericMonedaDetalle);
		}
		return result;
	}

	@Override
	public Set<PendienteCaja> getPendientesCaja() {
		HistorialCaja historial = getHistorialActivo();
		Set<PendienteCaja> result = historial.getPendienteCajas();
		for (PendienteCaja pendienteCaja : result) {
			Moneda moneda = pendienteCaja.getMoneda();
			Hibernate.initialize(pendienteCaja);
			Hibernate.initialize(moneda);
		}
		return result;
	}

	@Override
	public Set<HistorialCaja> getHistorialCaja(Date dateDesde, Date dateHasta) {
		Caja caja = getCaja();
		QueryParameter queryParameter = QueryParameter.with("idcaja", caja.getIdCaja()).and("desde", dateDesde).and("hasta", dateHasta);
		List<HistorialCaja> list = historialCajaDAO.findByNamedQuery(HistorialCaja.findByHistorialDateRange, queryParameter.parameters());
		return new HashSet<HistorialCaja>(list);
	}

	@Override
	public List<HistorialTransaccionCaja> getHistorialTransaccion() {
		HistorialCaja historial = this.getHistorialActivo();
		QueryParameter queryParameter = QueryParameter.with("idHistorialCaja", historial.getIdHistorialCaja());
		List<HistorialTransaccionCaja> list = historialTransaccionCajaDAO.findByNamedQuery(HistorialTransaccionCaja.findByHistorialCaja, queryParameter.parameters());
		return list;
	}

	@Override
	public List<TransaccionBovedaCajaView> getTransaccionesEnviadasBovedaCaja() {
		HistorialCaja historial = getHistorialActivo();
		QueryParameter queryParameter = QueryParameter.with("idHistorialCaja", historial.getIdHistorialCaja()).and("origen", TransaccionBovedaCajaOrigen.CAJA);
		List<TransaccionBovedaCajaView> list = transaccionBovedaCajaViewDAO.findByNamedQuery(TransaccionBovedaCajaView.findByHistorialCajaEnviados, queryParameter.parameters());

		return list;
	}

	@Override
	public List<TransaccionBovedaCajaView> getTransaccionesRecibidasBovedaCaja() {
		HistorialCaja historial = getHistorialActivo();
		QueryParameter queryParameter = QueryParameter.with("idHistorialCaja", historial.getIdHistorialCaja()).and("origen", TransaccionBovedaCajaOrigen.BOVEDA);
		List<TransaccionBovedaCajaView> list = transaccionBovedaCajaViewDAO.findByNamedQuery(TransaccionBovedaCajaView.findByHistorialCajaRecibidos, queryParameter.parameters());
		return list;
	}

	@Override
	public Set<TransaccionCajaCaja> getTransaccionesEnviadasCajaCaja() {
		HistorialCaja historial = getHistorialActivo();
		Set<TransaccionCajaCaja> enviados = historial.getTransaccionCajaCajasForIdCajaHistorialOrigen();
		Hibernate.initialize(enviados);
		return enviados;
	}

	@Override
	public Set<TransaccionCajaCaja> getTransaccionesRecibidasCajaCaja() {
		HistorialCaja historial = getHistorialActivo();
		Set<TransaccionCajaCaja> recibidos = historial.getTransaccionCajaCajasForIdCajaHistorialDestino();
		Hibernate.initialize(recibidos);
		return recibidos;
	}

	@Override
	public List<HistorialTransaccionCaja> getHistorialTransaccion(String filterText) {
		List<HistorialTransaccionCaja> list = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("filterText", '%' + filterText + '%');
		list = historialTransaccionCajaDAO.findByNamedQuery(HistorialTransaccionCaja.findByTransaccion, parameters);
		return list;
	}

}