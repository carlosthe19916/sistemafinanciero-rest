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

import javax.ejb.EJB;
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
import javax.ws.rs.core.Response;

import org.sistemafinanciero.entity.Beneficiario;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;
import org.sistemafinanciero.service.nt.CuentaBancariaServiceNT;
import org.sistemafinanciero.service.ts.CuentaBancariaServiceTS;

@Path("/beneficiario")
public class BeneficiarioRESTService {

	@EJB
	private CuentaBancariaServiceNT cuentaBancariaServiceNT;

	@EJB
	private CuentaBancariaServiceTS cuentaBancariaServiceTS;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findBeneficiario(@PathParam("id") BigInteger id) {
		if (id != null) {
			Beneficiario beneficiario = cuentaBancariaServiceNT.findBeneficiarioById(id);
			if (beneficiario != null) {
				return Response.status(Response.Status.OK).entity(beneficiario).build();
			} else {
				JsonObject model = Json.createObjectBuilder().add("message", "Beneficiario no encontrado").build();
				return Response.status(Response.Status.NOT_FOUND).entity(model).build();
			}
		} else {
			JsonObject model = Json.createObjectBuilder().add("message", "solicitud invalida").build();
			return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response updateBeneficiario(@PathParam("id") BigInteger id, Beneficiario beneficiario) {
		BigInteger idbeneficiario = beneficiario.getIdBeneficiario();
		if (idbeneficiario != null) {
			try {
				Beneficiario beneficiarioDB = cuentaBancariaServiceNT.findBeneficiarioById(idbeneficiario);
				beneficiario.setCuentaBancaria(beneficiarioDB.getCuentaBancaria());
				cuentaBancariaServiceTS.updateBeneficiario(idbeneficiario, beneficiario);
				JsonObject model = Json.createObjectBuilder().add("message", "Beneficiario actualizado").build();
				return Response.status(Response.Status.OK).entity(model).build();
			} catch (NonexistentEntityException e) {
				JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
				return Response.status(Response.Status.NOT_FOUND).entity(model).build();
			} catch (PreexistingEntityException e) {
				JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
				return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
			} catch (RollbackFailureException e) {
				JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
				return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
			}
		} else {
			JsonObject model = Json.createObjectBuilder().add("message", "solicitud invalida").build();
			return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}
	}

	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response createBeneficiario() {
		return null;
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response deteleBeneficiario(@PathParam("id") BigInteger id) {
		if (id != null) {
			try {
				cuentaBancariaServiceTS.deleteBeneficiario(id);
				JsonObject model = Json.createObjectBuilder().add("message", "Beneficiario eliminado").build();
				return Response.status(Response.Status.OK).entity(model).build();
			} catch (NonexistentEntityException e) {
				JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
				return Response.status(Response.Status.NOT_FOUND).entity(model).build();
			} catch (RollbackFailureException e) {
				JsonObject model = Json.createObjectBuilder().add("message", e.getMessage()).build();
				return Response.status(Response.Status.NOT_FOUND).entity(model).build();
			}
		} else {
			JsonObject model = Json.createObjectBuilder().add("message", "solicitud invalida").build();
			return Response.status(Response.Status.BAD_REQUEST).entity(model).build();
		}

	}
}
