package org.sistemafinanciero.service.nt;

import javax.ejb.Remote;

@Remote
public interface UsuarioServiceNT {

	public boolean authenticateAsAdministrator(String username, String password);

}
