package org.sistemafinanciero.util;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Named;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

@Named
@Stateless
public class UsuarioSession {

	@Resource
	private SessionContext context;

	public boolean haveSession() {
		Principal principal = context.getCallerPrincipal();
		String name = principal.getName();
		return name == null;
	}

	public String getUsername() {
		KeycloakPrincipal p = (KeycloakPrincipal) context.getCallerPrincipal();
		KeycloakSecurityContext kcSecurityContext = p.getKeycloakSecurityContext();
		String username = kcSecurityContext.getToken().getPreferredUsername();
		return username;
	}

}
