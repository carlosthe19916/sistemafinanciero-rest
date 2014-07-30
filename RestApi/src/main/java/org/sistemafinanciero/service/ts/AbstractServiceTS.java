package org.sistemafinanciero.service.ts;

import java.math.BigInteger;

import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;

public interface AbstractServiceTS<T> {

	public BigInteger create(T t) throws PreexistingEntityException, RollbackFailureException;

	public void update(BigInteger id, T t) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException;

	public void delete(BigInteger id) throws NonexistentEntityException, RollbackFailureException;

}
