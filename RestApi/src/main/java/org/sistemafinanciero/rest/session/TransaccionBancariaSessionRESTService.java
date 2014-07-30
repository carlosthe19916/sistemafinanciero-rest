package org.sistemafinanciero.rest.session;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.HistorialTransaccionCaja;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.rest.dto.TransaccionBancariaDTO;
import org.sistemafinanciero.service.nt.CajaSessionServiceNT;
import org.sistemafinanciero.service.ts.CajaSessionServiceTS;

@RolesAllowed("CAJERO")
@Path("/caja/session/transaccionBancaria")
public class TransaccionBancariaSessionRESTService {

	@EJB
	private CajaSessionServiceNT cajaSessionServiceNT;
	@EJB
	private CajaSessionServiceTS cajaSessionServiceTS;

	@RolesAllowed("CAJERO")
	@GET
	@Produces({ "application/xml", "application/json" })
	public Response getTransaccionBancaria(@Context SecurityContext context) {
		return null;
	}

	@RolesAllowed("CAJERO")
	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response crearTransaccion(TransaccionBancariaDTO transaccion) {
		Response.ResponseBuilder builder = null;
		try {
			String numeroCuenta = transaccion.getNumeroCuenta();
			BigDecimal monto = transaccion.getMonto();
			String referencia = transaccion.getReferencia();

			BigInteger idTransaccion = null;
			if (monto.compareTo(BigDecimal.ZERO) >= 0) {
				idTransaccion = cajaSessionServiceTS.crearDepositoBancario(numeroCuenta, monto, referencia);
			} else {
				idTransaccion = cajaSessionServiceTS.crearRetiroBancario(numeroCuenta, monto, referencia);
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
	@Path("{idTransaccion}/extornar")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response extornarTransaccion(@PathParam("idTransaccion") BigInteger idTransaccion) {
		Response.ResponseBuilder builder = null;
		try {
			cajaSessionServiceTS.extornarTransaccion(idTransaccion);
			JsonObject model = Json.createObjectBuilder().add("message", "Transacci&oacute;n Extornada").add("idTransaccion", idTransaccion).build();
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

	@GET
	@Path("/view")
	@Produces({ "application/xml", "application/json" })
	public Response findAllViewByFilterText(@QueryParam("filterText") String filterText) {
		List<HistorialTransaccionCaja> list = null;
		list = cajaSessionServiceNT.getHistorialTransaccion(filterText);
		return Response.status(Response.Status.OK).entity(list).build();
	}

	@GET
	@Path("/view/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByFilterText() {
		int size = cajaSessionServiceNT.count();
		return Response.status(Response.Status.OK).entity(size).build();
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
