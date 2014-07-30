package org.sistemafinanciero.service.ts;

import java.math.BigInteger;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Socio;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.exception.RollbackFailureException;

@Remote
public interface SocioServiceTS extends AbstractServiceTS<Socio> {

	public BigInteger create(BigInteger idAgencia, TipoPersona tipoPersona, BigInteger idDocSocio, String numDocSocio, BigInteger idDocApoderado, String numDocApoderado) throws RollbackFailureException;

	public void congelarCuentaAporte(BigInteger idSocio) throws RollbackFailureException;

	public void descongelarCuentaAporte(BigInteger idSocio) throws RollbackFailureException;

	public void inactivarSocio(BigInteger idSocio) throws RollbackFailureException;

	public void cambiarApoderado(BigInteger idSocio, BigInteger idPersonaNatural) throws RollbackFailureException;

	public void eliminarApoderado(BigInteger idSocio) throws RollbackFailureException;

}
