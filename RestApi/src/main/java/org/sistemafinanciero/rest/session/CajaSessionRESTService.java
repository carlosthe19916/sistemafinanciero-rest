package org.sistemafinanciero.rest.session;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.Hibernate;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.HistorialCaja;
import org.sistemafinanciero.entity.HistorialTransaccionCaja;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.Trabajador;
import org.sistemafinanciero.entity.dto.GenericMonedaDetalle;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.nt.CajaServiceNT;
import org.sistemafinanciero.service.nt.CajaSessionServiceNT;
import org.sistemafinanciero.service.nt.TrabajadorServiceNT;
import org.sistemafinanciero.service.ts.CajaSessionServiceTS;

@RolesAllowed("CAJERO")
@Path("/caja/session")
public class CajaSessionRESTService {

	@EJB
	private CajaSessionServiceTS cajaSessionServiceTS;
	@EJB
	private CajaSessionServiceNT cajaSessionServiceNT;
	@EJB
	private TrabajadorServiceNT trabajadorServiceNT;
	@EJB
	private CajaServiceNT cajaService;
	
	
	@RolesAllowed("CAJERO")
	@GET
	@Produces({ "application/xml", "application/json" })
	public Response getCaja(@Context SecurityContext context) {
		Caja caja = null;
		try {
			KeycloakPrincipal p = (KeycloakPrincipal) context.getUserPrincipal();
			KeycloakSecurityContext kcSecurityContext = p.getKeycloakSecurityContext();
			String username = kcSecurityContext.getToken().getPreferredUsername();
					
			Trabajador trabajador;
			if (username != null)
				trabajador = trabajadorServiceNT.findByUsername(username);
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
			if (trabajador != null)
				caja = trabajadorServiceNT.findByTrabajador(trabajador.getIdTrabajador());
			else
				return Response.status(Response.Status.NOT_FOUND).entity("El usuario no tiene cajas asignadas").build();
		} catch (NonexistentEntityException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).entity(caja).build();
	}

	
	@RolesAllowed("CAJERO")
	@GET
	@Path("/bovedas")
	@Produces({ "application/xml", "application/json" })
	public Response getBovedasOfCaja(@Context SecurityContext context) {
		Caja caja = null;
		try {
			KeycloakPrincipal p = (KeycloakPrincipal) context.getUserPrincipal();
			KeycloakSecurityContext kcSecurityContext = p.getKeycloakSecurityContext();
			String username = kcSecurityContext.getToken().getPreferredUsername();
					
			Trabajador trabajador;
			if (username != null)
				trabajador = trabajadorServiceNT.findByUsername(username);
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
			if (trabajador != null)
				caja = trabajadorServiceNT.findByTrabajador(trabajador.getIdTrabajador());
			else
				return Response.status(Response.Status.NOT_FOUND).entity("El usuario no tiene cajas asignadas").build();
			Set<Boveda> bovedas = cajaService.getBovedas(caja.getIdCaja());
			Hibernate.initialize(bovedas);
			return Response.status(Response.Status.OK).entity(bovedas).build();
		} catch (NonexistentEntityException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@RolesAllowed("CAJERO")
	@GET
	@Path("/monedas")
	@Produces({ "application/xml", "application/json" })
	public Response getMonedasOfCaja(@Context SecurityContext context) {
		Set<Moneda> monedas = cajaSessionServiceNT.getMonedas();
		return Response.status(Response.Status.OK).entity(monedas).build();
	}

	@RolesAllowed("CAJERO")
	@GET
	@Path("/detalle")
	@Produces({ "application/xml", "application/json" })
	public Response getDetalleOfCaja() {
		Set<GenericMonedaDetalle> result = cajaSessionServiceNT.getDetalleCaja();
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@RolesAllowed("CAJERO")
	@GET
	@Path("/historial")
	@Produces({ "application/xml", "application/json" })
	public Response getHistorialOfCaja(@QueryParam("desde") Long desde, @QueryParam("hasta") Long hasta) {
		Date dateDesde = (desde != null ? new Date(desde) : null);
		Date dateHasta = (desde != null ? new Date(hasta) : null);
		if (dateDesde == null || dateHasta == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		Set<HistorialCaja> list = cajaSessionServiceNT.getHistorialCaja(dateDesde, dateHasta);
		return Response.status(Response.Status.OK).entity(list).build();
	}

	@RolesAllowed("CAJERO")
	@GET
	@Path("/historialTransaccion")
	@Produces({ "application/xml", "application/json" })
	public Response getHistorialTransaccionCaja() {
		List<HistorialTransaccionCaja> list = cajaSessionServiceNT.getHistorialTransaccion();
		return Response.status(Response.Status.OK).entity(list).build();
	}

	@RolesAllowed("CAJERO")
	@POST
	@Path("/abrir")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response abrirCaja(@Context SecurityContext context) {
		Response.ResponseBuilder builder = null;
		try {
			BigInteger idHistorialCaja = cajaSessionServiceTS.abrirCaja();
			JsonObject model = Json.createObjectBuilder().add("message", "Caja abierta").add("id", idHistorialCaja).build();
			builder = Response.status(Response.Status.OK).entity(model);
		} catch (RollbackFailureException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		} catch (EJBException e) {
			builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage());
		}
		return builder.build();
	}

	@RolesAllowed("CAJERO")
	@POST
	@Path("/cerrar")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response cerrarCaja(@Context SecurityContext context, Set<GenericMonedaDetalle> detalleCaja) {
		try {
			Map<Boveda, BigDecimal> diferencia = cajaSessionServiceNT.getDiferenciaSaldoCaja(detalleCaja);
			if (diferencia.size() > 0) {
				JsonArrayBuilder result = Json.createArrayBuilder();
				for (Boveda boveda : diferencia.keySet()) {
					BigDecimal dif = diferencia.get(boveda);
					JsonObject obj = Json.createObjectBuilder().add("idboveda", boveda.getIdBoveda()).add("boveda", boveda.getDenominacion()).add("monto", dif).build();
					result.add(obj);
				}
				return Response.status(Response.Status.BAD_REQUEST).entity(result.build()).build();
			}
			BigInteger idHistorialCaja = cajaSessionServiceTS.cerrarCaja(new HashSet<GenericMonedaDetalle>(detalleCaja));
			JsonObject model = Json.createObjectBuilder().add("message", "Caja cerrada").add("id", idHistorialCaja).build();
			return Response.status(Response.Status.OK).entity(model).build();
		} catch (RollbackFailureException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (EJBException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/cuentaBancaria/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response deleteCuentaBancaria(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;
		if (id == null) {
			model = Json.createObjectBuilder().add("message", "Id no valido").build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			BigInteger idTransaccion = cajaSessionServiceTS.cancelarCuentaBancariaConRetiro(id);
			model = Json.createObjectBuilder().add("id", idTransaccion).build();
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
