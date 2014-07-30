package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.PersonaNatural;

@Remote
public interface PersonaNaturalServiceNT extends AbstractServiceNT<PersonaNatural>{
	
	public PersonaNatural find(BigInteger idTipoDocumento, String numeroDocumento);

	public List<PersonaNatural> findAll();

	public List<PersonaNatural> findAll(Integer offset, Integer limit);

	public List<PersonaNatural> findAll(String filterText);

	public List<PersonaNatural> findAll(String filterText, Integer offset, Integer limit);

}
