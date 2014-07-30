package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.CuentaAporte;
import org.sistemafinanciero.entity.CuentaBancaria;
import org.sistemafinanciero.entity.HistorialAportesSP;
import org.sistemafinanciero.entity.PersonaJuridica;
import org.sistemafinanciero.entity.PersonaNatural;
import org.sistemafinanciero.entity.Socio;
import org.sistemafinanciero.entity.SocioView;
import org.sistemafinanciero.entity.type.TipoPersona;

@Remote
public interface SocioServiceNT extends AbstractServiceNT<Socio> {

	public List<SocioView> findAllView();

	public List<SocioView> findAllView(Boolean estadoCuentaAporte, Boolean estadoSocio);

	public List<SocioView> findAllView(Boolean estadoCuentaAporte, Boolean estadoSocio, Integer offset, Integer limit);

	public List<SocioView> findAllView(String filterText);

	public List<SocioView> findAllView(String filterText, Boolean estadoCuentaAporte, Boolean estadoSocio);

	public List<SocioView> findAllView(String filterText, BigInteger offset, BigInteger limit);

	public List<SocioView> findAllView(String filterText, Boolean estadoCuentaAporte, Boolean estadoSocio, BigInteger offset, BigInteger limit);

	public Socio find(TipoPersona tipoPersona, BigInteger idTipoDocumento, String numeroDocumento);

	public Socio find(BigInteger idCuentaBancaria);

	public PersonaNatural getPersonaNatural(BigInteger idSocio);

	public PersonaJuridica getPersonaJuridica(BigInteger idSocio);

	public PersonaNatural getApoderado(BigInteger idSocio);

	public CuentaAporte getCuentaAporte(BigInteger idSocio);

	public Set<CuentaBancaria> getCuentasBancarias(BigInteger idSocio);

	public List<HistorialAportesSP> getHistorialAportes(BigInteger idSocio, Date desde, Date hasta, BigInteger offset, BigInteger limit);

}
