package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Departamento;
import org.sistemafinanciero.entity.Distrito;
import org.sistemafinanciero.entity.Pais;
import org.sistemafinanciero.entity.Provincia;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.entity.type.TipoPersona;

@Remote
public interface MaestroServiceNT {

	public List<TipoDocumento> getTipoDocumento(TipoPersona tipopersona);

	public List<Pais> getPaises();

	public Pais findPaisByAbreviatura(String abrevitura);

	public Pais findPaisByCodigo(String codigo);

	public List<Departamento> getDepartamentos();

	public List<Provincia> getProvincias(BigInteger idDepartamento);

	public List<Distrito> getDistritos(BigInteger idProvincia);

	public List<Provincia> getProvincias(String codigoDepartamento);

	public List<Distrito> getDistritos(String codigoDepartamento, String codigoProvincia);

}
