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
package org.sistemafinanciero.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public interface MaestroREST {

	@GET
	@Path("/tipoPersonas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTipoPersonas();

	@GET
	@Path("/estadosCiviles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadosCiviles();

	@GET
	@Path("/sexos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSexos();

	@GET
	@Path("/tipoDocumentos/personas/naturales")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTipoDocumentoPersonaNatural();

	@GET
	@Path("/tipoDocumentos/personas/juridicas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTipoDocumentoPersonaJuridica();

	@GET
	@Path("/tiposEmpresa")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTiposEmpresa();

	@GET
	@Path("/paises")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPaises();

	@GET
	@Path("/paises/abreviatura/{abreviatura}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPaisByAbreviatura(@PathParam("abreviatura") String abreviatura);

	@GET
	@Path("/paises/codigo/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPaisByCodigo(@PathParam("codigo") String codigo);

	@GET
	@Path("/ubigeo/departamentos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDepartamentos();

	@GET
	@Path("/ubigeo/provincias")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProvinciasByCodigo(@QueryParam("codigoDepartamento") String codigoDepartamento);

	@GET
	@Path("/ubigeo/distritos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDistritosByCodigo(@QueryParam("codigoDepartamento") String codigoDepartamento, @QueryParam("codigoProvincia") String codigoProvincia);

}
