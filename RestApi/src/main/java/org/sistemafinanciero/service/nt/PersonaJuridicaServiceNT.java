package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.PersonaJuridica;

@Remote
public interface PersonaJuridicaServiceNT extends AbstractServiceNT<PersonaJuridica> {

	public PersonaJuridica find(BigInteger idTipodocumento, String numerodocumento);

	public List<PersonaJuridica> findAll();

	public List<PersonaJuridica> findAll(Integer offset, Integer limit);

	public List<PersonaJuridica> findAll(String filterText);

	public List<PersonaJuridica> findAll(String filterText, Integer offset, Integer limit);
}
