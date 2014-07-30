package org.sistemafinanciero.rest.impl;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.entity.Accionista;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.rest.dto.PersonaJuridicaDTO;
import org.sistemafinanciero.service.nt.PersonaJuridicaServiceNT;
import org.sistemafinanciero.service.ts.PersonaJuridicaServiceTS;

@Path("/personaJuridica")
public class PersonaJuridicaRESTService {

	private Logger log;

	@EJB
	private PersonaJuridicaServiceNT personaJuridicaServiceNT;
	@EJB
	private PersonaJuridicaServiceTS personaJuridicaServiceTS;

	// cuerpo de la respuesta
	private final String ID_RESPONSE = "id";
	private final String MESSAGE_RESPONSE = "message";

	// mensajes
	private final String SUCCESS_MESSAGE = "Success";
	private final String NOT_FOUND_MESSAGE = "Persona no encontrada";
	private final String BAD_REQUEST_MESSAGE = "Datos invalidos";
	private final String CONFLICT_MESSAGE = "Persona ya existente";

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") @DefaultValue("-1") BigInteger id) {
		Response result = null;
		JsonObject model = null;
		if (id != null) {
			PersonaJuridica persona = personaJuridicaServiceNT.findById(id);
			if (persona != null) {
				result = Response.status(Response.Status.OK).entity(persona).build();
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
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public Response findByTipoNumeroDocumento(@QueryParam("idTipoDocumento") @DefaultValue("-1") BigInteger idtipodocumento, @QueryParam("numeroDocumento") @DefaultValue("") String numerodocumento) {
		Response result = null;
		JsonObject model = null;
		if (idtipodocumento == null || numerodocumento == null || numerodocumento.isEmpty() || numerodocumento.trim().isEmpty()) {
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, BAD_REQUEST_MESSAGE).build();
			result = Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
		try {
			PersonaJuridica personaJuridica = personaJuridicaServiceNT.find(idtipodocumento, numerodocumento);
			if (personaJuridica != null) {
				result = Response.status(Response.Status.OK).entity(personaJuridica).build();
			} else {
				model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
				result = Response.status(Response.Status.NOT_FOUND).entity(model).build();
			}
		} catch (EJBException e) {
			log.log(Level.SEVERE, e.getMessage());
			model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		if (offset == null || limit == null) {
			offset = null;
			limit = null;
		}
		if (offset != null && offset.compareTo(0) < 1)
			offset = 0;
		if (limit != null && limit.compareTo(0) < 1)
			limit = 0;
		List<PersonaJuridica> list = personaJuridicaServiceNT.findAll(filterText, offset, limit);

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
		int size = personaJuridicaServiceNT.count();
		Response result = Response.status(Response.Status.OK).entity(size).build();
		return result;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") @DefaultValue("-1") BigInteger id, PersonaJuridicaDTO persona) {
		try {
			PersonaJuridica personaJuridica = new PersonaJuridica();
			personaJuridica.setIdPersonaJuridica(null);
			personaJuridica.setActividadPrincipal(persona.getActividadPrincipal());
			personaJuridica.setCelular(persona.getCelular());
			personaJuridica.setTelefono(persona.getTelefono());
			personaJuridica.setDireccion(persona.getDireccion());
			personaJuridica.setReferencia(persona.getReferencia());
			personaJuridica.setEmail(persona.getEmail());
			personaJuridica.setFechaConstitucion(persona.getFechaConstitucion());
			personaJuridica.setFinLucro(persona.isFinLucro());
			personaJuridica.setNombreComercial(persona.getNombreComercial());
			personaJuridica.setNumeroDocumento(persona.getNumeroDocumento());
			personaJuridica.setRazonSocial(persona.getRazonSocial());
			personaJuridica.setTipoDocumento(persona.getTipoDocumento());
			personaJuridica.setTipoEmpresa(persona.getTipoEmpresa());
			personaJuridica.setUbigeo(persona.getUbigeo());

			PersonaNatural representante = new PersonaNatural();
			representante.setIdPersonaNatural(persona.getIdRepresentanteLegal());
			personaJuridica.setRepresentanteLegal(representante);

			Set<Accionista> accionistasFinal = new HashSet<Accionista>();
			Set<org.sistemafinanciero.rest.dto.PersonaJuridicaDTO.Accionista> accionistas = persona.getAccionistas();
			for (org.sistemafinanciero.rest.dto.PersonaJuridicaDTO.Accionista accionista : accionistas) {
				Accionista accionistaFinal = new Accionista();
				accionistaFinal.setIdAccionista(null);
				PersonaNatural person = new PersonaNatural();
				person.setIdPersonaNatural(accionista.getIdPersona());
				accionistaFinal.setPersonaNatural(person);
				accionistaFinal.setPorcentajeParticipacion(accionista.getPorcentaje());

				accionistasFinal.add(accionistaFinal);
			}
			personaJuridica.setAccionistas(accionistasFinal);

			personaJuridicaServiceTS.update(id, personaJuridica);
			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, SUCCESS_MESSAGE).build();
			return Response.status(Response.Status.OK).entity(model).build();
		} catch (NonexistentEntityException e) {
			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		} catch (RollbackFailureException e) {
			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		} catch (PreexistingEntityException e) {
			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, CONFLICT_MESSAGE).build();
			return Response.status(Response.Status.CONFLICT).entity(model).build();
		}
	}

	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response create(PersonaJuridicaDTO persona) {
		try {
			PersonaJuridica personaJuridica = new PersonaJuridica();
			personaJuridica.setIdPersonaJuridica(null);
			personaJuridica.setActividadPrincipal(persona.getActividadPrincipal());
			personaJuridica.setCelular(persona.getCelular());
			personaJuridica.setTelefono(persona.getTelefono());
			personaJuridica.setDireccion(persona.getDireccion());
			personaJuridica.setReferencia(persona.getReferencia());
			personaJuridica.setEmail(persona.getEmail());
			personaJuridica.setFechaConstitucion(persona.getFechaConstitucion());
			personaJuridica.setFinLucro(persona.isFinLucro());
			personaJuridica.setNombreComercial(persona.getNombreComercial());
			personaJuridica.setNumeroDocumento(persona.getNumeroDocumento());
			personaJuridica.setRazonSocial(persona.getRazonSocial());
			personaJuridica.setTipoDocumento(persona.getTipoDocumento());
			personaJuridica.setTipoEmpresa(persona.getTipoEmpresa());
			personaJuridica.setUbigeo(persona.getUbigeo());

			PersonaNatural representante = new PersonaNatural();
			representante.setIdPersonaNatural(persona.getIdRepresentanteLegal());
			personaJuridica.setRepresentanteLegal(representante);

			Set<Accionista> accionistasFinal = new HashSet<Accionista>();
			Set<org.sistemafinanciero.rest.dto.PersonaJuridicaDTO.Accionista> accionistas = persona.getAccionistas();
			for (org.sistemafinanciero.rest.dto.PersonaJuridicaDTO.Accionista accionista : accionistas) {
				Accionista accionistaFinal = new Accionista();
				accionistaFinal.setIdAccionista(null);
				PersonaNatural person = new PersonaNatural();
				person.setIdPersonaNatural(accionista.getIdPersona());
				accionistaFinal.setPersonaNatural(person);
				accionistaFinal.setPorcentajeParticipacion(accionista.getPorcentaje());

				accionistasFinal.add(accionistaFinal);
			}
			personaJuridica.setAccionistas(accionistasFinal);

			BigInteger idPersona = personaJuridicaServiceTS.create(personaJuridica);

			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, SUCCESS_MESSAGE).add(ID_RESPONSE, idPersona).build();
			return Response.status(Response.Status.OK).entity(model).build();
		} catch (PreexistingEntityException e) {
			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, CONFLICT_MESSAGE).build();
			return Response.status(Response.Status.CONFLICT).entity(model).build();
		} catch (RollbackFailureException e) {
			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, NOT_FOUND_MESSAGE).build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		} catch (EJBException e) {
			JsonObject model = Json.createObjectBuilder().add(MESSAGE_RESPONSE, e.getMessage()).build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(model).build();
		}
	}

}
