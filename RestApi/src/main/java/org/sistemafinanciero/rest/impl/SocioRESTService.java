/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sistemafinanciero.rest.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.CuentaAporte;
import org.sistemafinanciero.entity.CuentaBancaria;
import org.sistemafinanciero.entity.HistorialAportesSP;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.Socio;
import org.sistemafinanciero.entity.SocioView;
import org.sistemafinanciero.entity.Trabajador;
import org.sistemafinanciero.entity.dto.VoucherTransaccionCuentaAporte;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.rest.dto.ApoderadoDTO;
import org.sistemafinanciero.rest.dto.SocioDTO;
import org.sistemafinanciero.service.nt.CajaServiceNT;
import org.sistemafinanciero.service.nt.PersonaNaturalServiceNT;
import org.sistemafinanciero.service.nt.SocioServiceNT;
import org.sistemafinanciero.service.nt.TrabajadorServiceNT;
import org.sistemafinanciero.service.nt.UsuarioServiceNT;
import org.sistemafinanciero.service.ts.SocioServiceTS;

@Path("/socio")
public class SocioRESTService {

	@EJB
	private SocioServiceNT socioServiceNT;
	@EJB
	private SocioServiceTS socioServiceTS;
	
	@EJB
	private UsuarioServiceNT usuarioService;

	@EJB
	private TrabajadorServiceNT trabajadorService;

	@EJB
	private PersonaNaturalServiceNT personaNaturalService;
	
	@EJB
	private CajaServiceNT serviceNT;

	// cuerpo de la respuesta
	private final String ID_RESPONSE = "id";
	private final String MESSAGE_RESPONSE = "message";

	// mensajes
	private final String SUCCESS_MESSAGE = "Success";
	private final String NOT_FOUND_MESSAGE = "Socio no encontrado";
	private final String BAD_REQUEST_MESSAGE = "Datos invalidos";
	private final String CONFLICT_MESSAGE = "Socio ya existente";

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") @DefaultValue("null") BigInteger id) {
		Response result = null;
		JsonObject model = null;
		if (id != null) {
			Socio socio = socioServiceNT.findById(id);
			if (socio != null) {
				result = Response.status(Response.Status.OK).entity(socio).build();
			} else {
				model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
				result = Response.status(Response.Status.NOT_FOUND).entity(model).build();
			}
		} else {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll(@QueryParam("filterText") @DefaultValue("") String filterText, @QueryParam("cuentaAporte") Boolean estadoCuentaAporte, @QueryParam("estadoSocio") Boolean estadoSocio, @QueryParam("offset") BigInteger offset, @QueryParam("limit") BigInteger limit) {

		if (offset != null && offset.compareTo(BigInteger.ZERO) < 1)
			offset = BigInteger.ZERO;
		if (limit != null && limit.compareTo(BigInteger.ZERO) < 1)
			limit = BigInteger.ZERO;

		List<SocioView> list = socioServiceNT.findAllView(filterText, estadoCuentaAporte, estadoSocio, offset, limit);
		Response result = null;
		JsonObject model = null;
		if (list != null) {
			result = Response.status(Response.Status.OK).entity(list).build();
		} else {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			result = Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}
		return result;

	}

	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response countAll(@QueryParam("filterText") String filterText) {
		int size = socioServiceNT.count();
		Response result = Response.status(Response.Status.OK).entity(size).build();
		return result;
	}

	@GET
	@Path("/{id}/cuentaAporte")
	@Produces({ "application/xml", "application/json" })
	public Response getCuentaAporte(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;

		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		CuentaAporte cuentaAporte = socioServiceNT.getCuentaAporte(id);
		if (cuentaAporte != null) {
			return Response.status(Response.Status.OK).entity(cuentaAporte).build();
		} else {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			result = Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}
		return result;
	}

	@PUT
	@Path("/{id}/cuentaAporte/congelar")
	@Produces({ "application/xml", "application/json" })
	public Response congelarCuentaAporte(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;

		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			socioServiceTS.congelarCuentaAporte(id);
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, SUCCESS_MESSAGE).build();
			result = Response.status(Response.Status.OK).entity(model).build();
		} catch (RollbackFailureException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (EJBException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
		return result;
	}

	@PUT
	@Path("/{id}/cuentaAporte/descongelar")
	@Produces({ "application/xml", "application/json" })
	public Response descongelarCuentaAporte(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;

		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			socioServiceTS.descongelarCuentaAporte(id);
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, SUCCESS_MESSAGE).build();
			result = Response.status(Response.Status.OK).entity(model).build();
		} catch (RollbackFailureException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (EJBException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
		return result;
	}

	@GET
	@Path("/{id}/personaNatural")
	@Produces({ "application/xml", "application/json" })
	public Response getPersonaNatural(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;

		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		PersonaNatural persona = socioServiceNT.getPersonaNatural(id);
		if (persona != null) {
			return Response.status(Response.Status.OK).entity(persona).build();
		} else {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			result = Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}
		return result;
	}

	@GET
	@Path("/{id}/personaJuridica")
	@Produces({ "application/xml", "application/json" })
	public Response getPersonaJuridica(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;

		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		PersonaJuridica persona = socioServiceNT.getPersonaJuridica(id);
		if (persona != null) {
			return Response.status(Response.Status.OK).entity(persona).build();
		} else {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			result = Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}
		return result;
	}

	@GET
	@Path("/{id}/apoderado")
	@Produces({ "application/xml", "application/json" })
	public Response getApoderado(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;

		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		PersonaNatural persona = socioServiceNT.getApoderado(id);
		if (persona != null) {
			return Response.status(Response.Status.OK).entity(persona).build();
		} else {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			result = Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}
		return result;
	}

	@PUT
	@Path("/{id}/apoderado")
	@Produces({ "application/xml", "application/json" })
	public Response cambiarApoderado(@PathParam("id") BigInteger idSocio, ApoderadoDTO apoderado) {
		Response result = null;
		JsonObject model = null;

		if (idSocio == null || apoderado == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		BigInteger idTipoDocumento = apoderado.getIdTipoDocumento();
		String numeroDocumento = apoderado.getNumeroDocumento();

		PersonaNatural apoderadoPN = personaNaturalService.find(idTipoDocumento, numeroDocumento);
		if (apoderadoPN == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			socioServiceTS.cambiarApoderado(idSocio, apoderadoPN.getIdPersonaNatural());
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, SUCCESS_MESSAGE).build();
			result = Response.status(Response.Status.OK).entity(model).build();
		} catch (RollbackFailureException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (EJBException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
		return result;
	}

	@DELETE
	@Path("/{id}/apoderado")
	@Produces({ "application/xml", "application/json" })
	public Response eliminarApoderado(@PathParam("id") BigInteger idSocio) {
		Response result = null;
		JsonObject model = null;

		if (idSocio == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			socioServiceTS.eliminarApoderado(idSocio);
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, SUCCESS_MESSAGE).build();
			result = Response.status(Response.Status.OK).entity(model).build();
		} catch (RollbackFailureException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (EJBException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
		return result;
	}

	@GET
	@Path("/{id}/cuentaBancaria")
	@Produces({ "application/xml", "application/json" })
	public Response getCuentasBancarias(@PathParam("id") BigInteger id) {
		if (id == null)
			return Response.status(Response.Status.BAD_REQUEST).entity("id no valido").build();
		Socio socio = socioServiceNT.findById(id);
		if (socio == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
		} else {
			Set<CuentaBancaria> cuentas = socioServiceNT.getCuentasBancarias(socio.getIdSocio());
			if (cuentas == null)
				return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
			else
				return Response.status(Response.Status.OK).entity(cuentas).build();
		}
	}

	@GET
	@Path("/{id}/historialAportes")
	@Produces({ "application/xml", "application/json" })
	public Response getAportesHistorial(@PathParam("id") BigInteger idSocio, @QueryParam("desde") Long desde, @QueryParam("hasta") Long hasta) {
		if (idSocio == null)
			return Response.status(Response.Status.BAD_REQUEST).entity("id no valido").build();
		Date dateDesde = (desde != null ? new Date(desde) : null);
		Date dateHasta = (desde != null ? new Date(hasta) : null);
		if (desde == null || hasta == null) {
			desde = null;
			hasta = null;
		}
		List<HistorialAportesSP> result = socioServiceNT.getHistorialAportes(idSocio, dateDesde, dateHasta, null, null);
		if (result == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Socio no encontrado").build();
		} else {
			return Response.status(Response.Status.OK).entity(result).build();
		}
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response createSocio(SocioDTO socioDTO, @Context SecurityContext context) {
		try {
			// agencia			
			KeycloakPrincipal p = (KeycloakPrincipal) context.getUserPrincipal();
			KeycloakSecurityContext kcSecurityContext = p.getKeycloakSecurityContext();
			String username = kcSecurityContext.getToken().getPreferredUsername();
					
			Trabajador trabajador;
			if (username != null)
				trabajador = trabajadorService.findByUsername(username);
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
			Agencia agencia = trabajadorService.getAgencia(trabajador.getIdTrabajador());
			if (agencia == null)
				return Response.status(Response.Status.NOT_FOUND).entity("Agencia no encontrado").build();

			// socio
			TipoPersona tipoPersona = socioDTO.getTipoPersona();
			BigInteger idTipoDocumentoSocio = socioDTO.getIdTipoDocumentoSocio();
			String numeroDocumentoSocio = socioDTO.getNumeroDocumentoSocio();
			BigInteger idTipoDocumentoApoderado = socioDTO.getIdTipoDocumentoApoderado();
			String numeroDocumentoApoderado = socioDTO.getNumeroDocumentoApoderado();

			if (tipoPersona == null || idTipoDocumentoSocio == null || numeroDocumentoSocio == null) {
				JsonObject model = Json.createObjectBuilder().add("message", "datos no validos").build();
				return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
			}

			// transaccion
			BigInteger idSocio = socioServiceTS.create(agencia.getIdAgencia(), tipoPersona, idTipoDocumentoSocio, numeroDocumentoSocio, idTipoDocumentoApoderado, numeroDocumentoApoderado);
			JsonObject model = Json.createObjectBuilder().add("message", "Socio creado").add("id", idSocio).build();
			return Response.status(Response.Status.OK).entity(model).build();
		} catch (NonexistentEntityException e) {
			JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
			return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (RollbackFailureException e) {
			JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
			return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (EJBException e) {
			JsonObject model = Json.createObjectBuilder().add("message", "Error interno, intentelo nuevamente").build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response deleteSocio(@PathParam("id") BigInteger id) {
		Response result = null;
		JsonObject model = null;
		if (id == null) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			socioServiceTS.inactivarSocio(id);
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, SUCCESS_MESSAGE).build();
			result = Response.status(Response.Status.OK).entity(model).build();
		} catch (RollbackFailureException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		} catch (EJBException e) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
		return result;
	}

	@GET
	@Path("{id}/voucherCancelacionCuentaAporte")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response getVoucherCuentaBancaria(@PathParam("id") BigInteger idTransaccion) {
		VoucherTransaccionCuentaAporte voucherTransaccionBancaria = serviceNT.getVoucherCuentaAporte(idTransaccion);
		return Response.status(Response.Status.OK).entity(voucherTransaccionBancaria).build();
	}

	@GET
	@Path("{id}/voucherCuentaAporte")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response getVoucherCuentaAporte(@PathParam("id") BigInteger idTransaccion) {
		VoucherTransaccionCuentaAporte voucherTransaccionCuentaAporte = serviceNT.getVoucherCuentaAporte(idTransaccion);
		return Response.status(Response.Status.OK).entity(voucherTransaccionCuentaAporte).build();
	}
}
