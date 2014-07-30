package org.sistemafinanciero.util;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.Caja;

/**
 * 
 * @author adam-bien.com
 */
public class Guard {

	@Resource
	private SessionContext context;

	@Inject
	private UsuarioSession usuarioSession;

	@Inject
	private DAO<Object, Caja> cajaDAO;

	@AroundInvoke
	public Object validatePermissions(InvocationContext ic) throws Exception {
		Method method = ic.getMethod();

		Caja caja = null;
		String username = usuarioSession.getUsername();
		QueryParameter queryParameter = QueryParameter.with("usuario", username);
		List<Caja> list = cajaDAO.findByNamedQuery(Caja.findByUsername, queryParameter.parameters());
		if (list.size() <= 1) {
			for (Caja c : list) {
				caja = c;
			}
		} else {
			throw new SecurityException("se encontro mas de un usuario para la caja seleccionada");
		}

		if (!isAllowed(method, caja)) {
			throw new SecurityException("Caja no tiene permitido hacer esta operacion, verifique su estado ABIERTO/CERRADO");
		}
		return ic.proceed();
	}

	boolean isAllowed(Method method, Caja caja) {
		AllowedTo annotation = method.getAnnotation(AllowedTo.class);
		if (annotation == null) {
			return true;
		}
		Permission[] permissions = annotation.value();
		for (Permission permission : permissions) {
			if (permission.equals(Permission.ABIERTO)) {
				return caja.getAbierto() == true;
			}
			if (permission.equals(Permission.CERRADO)) {
				return caja.getAbierto() == false;
			}
		}
		return false;
	}
}
