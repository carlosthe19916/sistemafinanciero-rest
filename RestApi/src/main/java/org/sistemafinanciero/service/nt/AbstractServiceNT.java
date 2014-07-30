package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.List;

public interface AbstractServiceNT<T> {

	public T findById(BigInteger id);

	public List<T> findAll();

	public int count();

}
