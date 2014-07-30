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
package org.sistemafinanciero.rest.session;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

@RolesAllowed("CAJERO")
@Path("/usuario/session")
public class UsuarioSessionRESTService {

	@RolesAllowed("CAJERO")
	@GET
	public Response getUsernameOfSession(@Context SecurityContext context) {		
		KeycloakPrincipal p = (KeycloakPrincipal) context.getUserPrincipal();
		KeycloakSecurityContext kcSecurityContext = p.getKeycloakSecurityContext();                
		String username = kcSecurityContext.getToken().getPreferredUsername();	
		return Response.status(Response.Status.OK).entity(username).build();
	}

}
