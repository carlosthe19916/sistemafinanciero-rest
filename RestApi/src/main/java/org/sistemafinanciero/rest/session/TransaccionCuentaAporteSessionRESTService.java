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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.rest.dto.TransaccionCuentaAporteDTO;
import org.sistemafinanciero.service.nt.CajaSessionServiceNT;
import org.sistemafinanciero.service.ts.CajaSessionServiceTS;

@RolesAllowed("CAJERO")
@Path("/caja/session/transaccionCuentaAporte")
public class TransaccionCuentaAporteSessionRESTService {

	@EJB
	private CajaSessionServiceNT cajaSessionServiceNT;
	@EJB
	private CajaSessionServiceTS cajaSessionServiceTS;

	// cuerpo de la respuesta
	private final String ID_RESPONSE = "id";
	private final String MESSAGE_RESPONSE = "message";

	// mensajes
	private final String SUCCESS_MESSAGE = "Success";
	private final String NOT_FOUND_MESSAGE = "Socio no encontrado";
	private final String BAD_REQUEST_MESSAGE = "Datos invalidos";
	private final String CONFLICT_MESSAGE = "Socio ya existente";

	@RolesAllowed("CAJERO")
	@POST
	@Path("/deposito")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response crearAporte(TransaccionCuentaAporteDTO transaccion) {
		Response.ResponseBuilder builder = null;
		try {
			BigInteger idSocio = transaccion.getIdSocio();
			BigDecimal monto = transaccion.getMonto();
			int mes = transaccion.getMes();
			int anio = transaccion.getAnio();
			String referencia = transaccion.getReferencia();

			BigInteger idTransaccion = null;
			if (monto.compareTo(BigDecimal.ZERO) >= 0) {
				idTransaccion = cajaSessionServiceTS.crearAporte(idSocio, monto, mes, anio, referencia);
			} else {
				JsonObject model = Json.createObjectBuilder().add("message", "Monto no valido").build();
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

	@RolesAllowed("CAJERO")
	@POST
	@Path("/retiro/{id}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response crearRetiroCuentaAporte(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;

		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			BigInteger idTransaccion = cajaSessionServiceTS.cancelarSocioConRetiro(id);
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, "Socio inactivado").add(ID_RESPONSE, idTransaccion).build();
			result = Response.status(Response.Status.OK).entity(model).build();
		} catch (RollbackFailureException e) {
			model = Json.createObjectBuilder().add("message", e.getMessage()).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (EJBException e) {
			model = Json.createObjectBuilder().add("message", e.getMessage()).build();
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
		return result;

	}

}
