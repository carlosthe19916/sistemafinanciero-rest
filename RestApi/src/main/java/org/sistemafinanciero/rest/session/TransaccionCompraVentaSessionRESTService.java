package org.sistemafinanciero.rest.session;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.entity.type.Tipotransaccioncompraventa;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.rest.dto.TransaccionCompraVentaDTO;
import org.sistemafinanciero.service.nt.CajaSessionServiceNT;
import org.sistemafinanciero.service.ts.CajaSessionServiceTS;

@RolesAllowed("CAJERO")
@Path("/caja/session/transaccionCompraVenta")
public class TransaccionCompraVentaSessionRESTService {
	
	@EJB
	private CajaSessionServiceNT cajaSessionServiceNT;
	@EJB
	private CajaSessionServiceTS cajaSessionServiceTS;
	
	@RolesAllowed("CAJERO")
	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response crearTransaccion(TransaccionCompraVentaDTO transaccion) {
		Response.ResponseBuilder builder = null;		
		try {			
			Tipotransaccioncompraventa tipoTransaccion = transaccion.getTipoOperacion();
			BigInteger idMonedaRecibido = transaccion.getIdMonedaRecibida();
			BigInteger idMonedaEntregado = transaccion.getIdMonedaEntregada();
			BigDecimal montoRecibido = transaccion.getMontoRecibido();
			BigDecimal montoEntregado = transaccion.getMontoEntregado();
			BigDecimal tasaCambio = transaccion.getTasaCambio();
			String referencia = transaccion.getReferencia();
						
			BigInteger idTransaccion = cajaSessionServiceTS.crearCompraVenta(tipoTransaccion, idMonedaRecibido, 
					idMonedaEntregado, montoRecibido, montoEntregado, 
					tasaCambio, referencia);
			
			JsonObject model = Json.createObjectBuilder().add("message", "Transaccion creada").add("id", idTransaccion).build();
			builder = Response.status(Response.Status.OK).entity(model);			
		} catch (RollbackFailureException e) {
			JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
			builder = Response.status(Response.Status.BAD_REQUEST).entity(model);
		} catch (EJBException e) {
			JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
			builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model);
		}
		return builder.build();
	}	

}
