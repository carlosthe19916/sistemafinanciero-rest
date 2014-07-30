package org.sistemafinanciero.service.nt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.entity.HistorialCaja;
import org.sistemafinanciero.entity.HistorialTransaccionCaja;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.PendienteCaja;
import org.sistemafinanciero.entity.TransaccionBovedaCajaView;
import org.sistemafinanciero.entity.TransaccionCajaCaja;
import org.sistemafinanciero.entity.dto.GenericMonedaDetalle;

@Remote
public interface CajaSessionServiceNT {

	public Map<Boveda, BigDecimal> getDiferenciaSaldoCaja(Set<GenericMonedaDetalle> detalleCaja);
	
	public Set<Moneda> getMonedas();

	public Set<GenericMonedaDetalle> getDetalleCaja();

	public Set<PendienteCaja> getPendientesCaja();

	public Set<HistorialCaja> getHistorialCaja(Date dateDesde, Date dateHasta);

	public List<HistorialTransaccionCaja> getHistorialTransaccion();
	
	public List<HistorialTransaccionCaja> getHistorialTransaccion(String filterText);

	public List<TransaccionBovedaCajaView> getTransaccionesEnviadasBovedaCaja();

	public List<TransaccionBovedaCajaView> getTransaccionesRecibidasBovedaCaja();

	public Set<TransaccionCajaCaja> getTransaccionesEnviadasCajaCaja();

	public Set<TransaccionCajaCaja> getTransaccionesRecibidasCajaCaja();

}
