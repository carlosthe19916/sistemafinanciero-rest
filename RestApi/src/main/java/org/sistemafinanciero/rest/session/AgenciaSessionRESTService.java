package org.sistemafinanciero.rest.session;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.Trabajador;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.service.nt.AgenciaServiceNT;
import org.sistemafinanciero.service.nt.TrabajadorServiceNT;
import org.sistemafinanciero.service.nt.UsuarioServiceNT;

@RolesAllowed("CAJERO")
@Path("/agencia/session")
public class AgenciaSessionRESTService {

	@EJB
	private UsuarioServiceNT usuarioService;
	@EJB
	private TrabajadorServiceNT trabajadorService;
	@EJB
	private AgenciaServiceNT agenciaService;

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response getAgenciaOfSession(@Context SecurityContext context) {
		Agencia agencia = null;
		try {
			KeycloakPrincipal p = (KeycloakPrincipal) context.getUserPrincipal();
			KeycloakSecurityContext kcSecurityContext = p.getKeycloakSecurityContext();
			String username = kcSecurityContext.getToken().getPreferredUsername();
					
			Trabajador trabajador;
			if (username != null)
				trabajador = trabajadorService.findByUsername(username);
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
			agencia = trabajadorService.getAgencia(trabajador.getIdTrabajador());
		} catch (NonexistentEntityException e) {
			throw new InternalServerErrorException();
		}
		return Response.status(Response.Status.OK).entity(agencia).build();
	}

	@GET
	@Path("/cajas")
	@Produces({ "application/xml", "application/json" })
	public Response getCajasOfSession(@Context SecurityContext context) {
		Agencia agencia = null;
		try {
			KeycloakPrincipal p = (KeycloakPrincipal) context.getUserPrincipal();
			KeycloakSecurityContext kcSecurityContext = p.getKeycloakSecurityContext();
			String username = kcSecurityContext.getToken().getPreferredUsername();
					
			Trabajador trabajador;
			if (username != null)
				trabajador = trabajadorService.findByUsername(username);
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
			agencia = trabajadorService.getAgencia(trabajador.getIdTrabajador());
			Set<Caja> agencias = agenciaService.getCajas(agencia.getIdAgencia());
			return Response.status(Response.Status.OK).entity(agencias).build();
		} catch (NonexistentEntityException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

}
