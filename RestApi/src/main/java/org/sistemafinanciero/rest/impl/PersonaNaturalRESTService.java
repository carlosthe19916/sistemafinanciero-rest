package org.sistemafinanciero.rest.impl;

import java.io.File;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.util.NotImplementedException;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.rest.Jsend;
import org.sistemafinanciero.rest.PersonaNaturalREST;
import org.sistemafinanciero.service.nt.PersonaNaturalServiceNT;
import org.sistemafinanciero.service.ts.PersonaNaturalServiceTS;

public class PersonaNaturalRESTService implements PersonaNaturalREST {

	@Inject
	private Validator validator;

	@EJB
	private PersonaNaturalServiceNT personaNaturalServiceNT;

	@EJB
	private PersonaNaturalServiceTS personaNaturalServiceTS;

	private final String UPLOADED_FIRMA_PATH = "d:\\firmas\\";
	private final String UPLOADED_FOTO_PATH = "d:\\fotos\\";

	@Override
	public Response findById(BigInteger id) {
		PersonaNatural personaNatural = personaNaturalServiceNT.findById(id);
		Response response = Response.status(Response.Status.OK).entity(personaNatural).build();
		return response;
	}

	@Override
	public Response findByTipoNumeroDocumento(BigInteger idTipoDocumento, String numeroDocumento) {
		PersonaNatural personaNatural = personaNaturalServiceNT.find(idTipoDocumento, numeroDocumento);
		Response response = Response.status(Response.Status.OK).entity(personaNatural).build();
		return response;
	}

	@Override
	public Response listAll(String filterText, Integer offset, Integer limit) {
		List<PersonaNatural> list = personaNaturalServiceNT.findAll(filterText, offset, limit);
		Response response = Response.status(Response.Status.OK).entity(list).build();
		return response;
	}

	@Override
	public Response countAll() {
		int count = personaNaturalServiceNT.count();
		Response response = Response.status(Response.Status.OK).entity(count).build();
		return response;
	}

	@Override
	public Response update(BigInteger id, PersonaNatural personaNatural) {
		Response result;
		try {
			Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(personaNatural);
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
			personaNaturalServiceTS.update(id, personaNatural);
			result = Response.status(Response.Status.NO_CONTENT).build();
		} catch (ConstraintViolationException e) {
			Jsend jsend = Jsend.getErrorJSend();
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				jsend.addMessage(violation.getPropertyPath().toString() + " " + violation.getMessage());
			}
			result = Response.status(Response.Status.BAD_REQUEST).entity(jsend).build();
		} catch (NonexistentEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			result = Response.status(Response.Status.NOT_FOUND).entity(jsend).build();
		} catch (PreexistingEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			result = Response.status(Response.Status.CONFLICT).entity(jsend).build();
		} catch (RollbackFailureException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		} catch (EJBException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			result = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		}
		return result;
	}

	@Override
	public Response create(PersonaNatural personaNatural) {
		Response response;
		try {
			Set<ConstraintViolation<PersonaNatural>> violations = validator.validate(personaNatural);
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
			}
			BigInteger idPersona = personaNaturalServiceTS.create(personaNatural);			
			response = Response.status(Response.Status.CREATED).build();
		} catch (ConstraintViolationException e) {
			Jsend jsend = Jsend.getErrorJSend();
			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				jsend.addMessage(violation.getPropertyPath().toString() + " " + violation.getMessage());
			}
			response = Response.status(Response.Status.BAD_REQUEST).entity(jsend).build();
		} catch (PreexistingEntityException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.CONFLICT).entity(jsend).build();
		} catch (RollbackFailureException e) {
			Jsend jsend = Jsend.getErrorJSend(e.getMessage());
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsend).build();
		}
		return response;
	}

	@Override
	public Response delete(BigInteger id) {
		return null;
	}

	@Override
	public Response getFirma(String id, int flowChunkNumber, int flowChunkSize, int flowCurrentChunkSize, String flowFilename, String flowIdentifier, String flowRelativePath, int flowTotalChunks, int flowTotalSize) {
		if (flowFilename != null)
			return Response.status(Response.Status.NOT_FOUND).build();
		File file = new File(this.UPLOADED_FIRMA_PATH + id);
		if (!file.exists())
			file = new File(this.UPLOADED_FIRMA_PATH + "default.gif");
		ResponseBuilder response = Response.status(Response.Status.OK).entity((Object) file);
		response.header("Content-Disposition", "attachment; filename=image" + id + ".png");
		return response.build();
	}

	@Override
	public Response getFoto(String id, int flowChunkNumber, int flowChunkSize, int flowCurrentChunkSize, String flowFilename, String flowIdentifier, String flowRelativePath, int flowTotalChunks, int flowTotalSize) {
		if (flowFilename != null)
			return Response.status(Response.Status.NOT_FOUND).build();
		File file = new File(this.UPLOADED_FOTO_PATH + id);
		if (!file.exists())
			file = new File(this.UPLOADED_FOTO_PATH + "default.gif");
		ResponseBuilder response = Response.status(Response.Status.OK).entity((Object) file);
		response.header("Content-Disposition", "attachment; filename=image" + id + ".png");
		return response.build();
	}

	@Override
	public Response uploadFirma(BigInteger id, MultipartFormDataInput input) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@Override
	public Response uploadFoto(BigInteger id, MultipartFormDataInput input) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
