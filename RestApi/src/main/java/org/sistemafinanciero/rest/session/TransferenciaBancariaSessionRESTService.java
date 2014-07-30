package org.sistemafinanciero.rest.session;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.rest.dto.TranferenciaBancariaDTO;
import org.sistemafinanciero.service.nt.CajaSessionServiceNT;
import org.sistemafinanciero.service.ts.CajaSessionServiceTS;

@RolesAllowed("CAJERO")
@Path("/caja/session/transferenciaBancaria")
public class TransferenciaBancariaSessionRESTService {

	@EJB
	private CajaSessionServiceNT cajaSessionServiceNT;
	@EJB
	private CajaSessionServiceTS cajaSessionServiceTS;

	@RolesAllowed("CAJERO")
	@GET
	@Produces({ "application/xml", "application/json" })
	public Response getTransferenciasBancaria(@Context SecurityContext context) {
		return null;
	}

	@RolesAllowed("CAJERO")
	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response crearTransferencia(TranferenciaBancariaDTO transaccion) {
		Response.ResponseBuilder builder = null;
		try {
			String numeroCuentaOrigen = transaccion.getNumeroCuentaOrigen();
			String numeroCuentaDestino = transaccion.getNumeroCuentaDestino();
			BigDecimal monto = transaccion.getMonto();
			String referencia = transaccion.getReferencia();

			BigInteger idTransaccion = null;
			if (monto.compareTo(BigDecimal.ZERO) >= 0) {
				idTransaccion = cajaSessionServiceTS.crearTransferenciaBancaria(numeroCuentaOrigen, numeroCuentaDestino, monto, referencia);
			} else {
				JsonObject model = Json.createObjectBuilder().add("message", "Solicitud invalida").build();
				builder = Response.status(Response.Status.BAD_REQUEST).entity(model);
			}

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

	@PUT
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response updateCaja(Caja caja) {
		return null;
	}

	@DELETE
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response deleteCaja() {
		return null;
	}
}
