package org.sistemafinanciero.rest.session;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.sistemafinanciero.entity.TransaccionCajaCaja;
import org.sistemafinanciero.service.nt.BovedaServiceNT;
import org.sistemafinanciero.service.nt.CajaServiceNT;
import org.sistemafinanciero.service.nt.CajaSessionServiceNT;
import org.sistemafinanciero.service.nt.PendienteServiceNT;
import org.sistemafinanciero.service.nt.TrabajadorServiceNT;
import org.sistemafinanciero.service.nt.UsuarioServiceNT;

@RolesAllowed("CAJERO")
@Path("/caja/session/transaccioncajacaja")
public class TransaccionCajaCajaSessionRESTService {

	@EJB
	private BovedaServiceNT bovedaService;
	@EJB
	private CajaServiceNT cajaService;
	@EJB
	private CajaSessionServiceNT cajaSessionService;
	@EJB
	private UsuarioServiceNT usuarioService;
	@EJB
	private TrabajadorServiceNT trabajadorService;
	@EJB
	private PendienteServiceNT pendienteService;

	@GET
	@Path("/enviados")
	@Produces({ "application/xml", "application/json" })
	public Response getTransaccionesCajaCajaOfCajaEnviados(@Context SecurityContext context) {
		Set<TransaccionCajaCaja> result = cajaSessionService.getTransaccionesEnviadasCajaCaja();
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@GET
	@Path("/recibidos")
	@Produces({ "application/xml", "application/json" })
	public Response getTransaccionesCajaCajaOfCajaRecibidos(@Context SecurityContext context) {
		Set<TransaccionCajaCaja> result = cajaSessionService.getTransaccionesRecibidasCajaCaja();
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response createTransaccionCajaCaja(@Context SecurityContext context) {
		return null;
	}
}
