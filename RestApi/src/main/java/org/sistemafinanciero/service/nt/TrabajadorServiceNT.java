package org.sistemafinanciero.service.nt;

import java.math.BigInteger;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.Trabajador;
import org.sistemafinanciero.exception.NonexistentEntityException;

@Remote
public interface TrabajadorServiceNT extends AbstractServiceNT<Trabajador> {

	public Trabajador findByUsername(String username);

	public Caja findByTrabajador(BigInteger idTrabajador) throws NonexistentEntityException;

	public Agencia getAgencia(BigInteger idTrabajador) throws NonexistentEntityException;

}
