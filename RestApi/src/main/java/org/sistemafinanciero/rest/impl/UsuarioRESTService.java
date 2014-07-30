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

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.service.nt.UsuarioServiceNT;

@Path("/usuario")
public class UsuarioRESTService {

	@EJB
	private UsuarioServiceNT usuarioService;

	@GET
	@Path("/authenticate/administrator")
	public Response authenticateAsAdministrator(@QueryParam("username") String username, @QueryParam("password") String password) {
		boolean result = usuarioService.authenticateAsAdministrator(username, password);
		if (result) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}
}
