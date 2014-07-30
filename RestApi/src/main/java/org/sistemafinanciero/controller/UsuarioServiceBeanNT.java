package org.sistemafinanciero.controller;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.type.RolType;
import org.sistemafinanciero.exception.IllegalResultException;
import org.sistemafinanciero.service.nt.UsuarioServiceNT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Stateless
@Remote(UsuarioServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UsuarioServiceBeanNT implements UsuarioServiceNT {

	private static Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceNT.class);

	@Override
	public boolean authenticateAsAdministrator(String username, String password) {
		/*QueryParameter queryParameter = QueryParameter.with("username", username).and("password", password).and("rol", RolType.ADMINISTRADOR.toString());
		List<Usuario> list = usuarioDAO.findByNamedQuery(Usuario.findByUsernameAndPasswordAndRol, queryParameter.parameters());
		if (list.size() == 1)
			return true;
		else if (list.size() == 0)
			return false;
		else
			try {
				throw new IllegalResultException("Mas de un usuario encontrado");
			} catch (IllegalResultException e) {
				LOGGER.error(e.getMessage(), e.getCause(), e.getLocalizedMessage());
				return false;
			}*/
		return false;
	}

}
