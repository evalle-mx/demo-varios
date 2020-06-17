package net.dothr.dto;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 
 * @author Goyo
 *
 */
public class LocationInfoDto extends ComunDto{
	private String idPersona;
	private String idEmpresa;
	private String idPosicion;
	private String idDomicilio;
	private String idCodigoProceso;
	private String idTipoPrecision;
	private String codigoPostal;
	private String idPais;
	private String idEstado;
	private String idMunicipio;
	private String idCiudad;
	private String idTipoDomicilio;
	private String direccionFacturacion;
	private String googleLatitude;
	private String googleLongitude;
	private String latitud;
	private String longitud;
	private String calle;
	private String numeroExterior;
	private String numeroInterior;
	private String idAsentamiento;
	private String direccionNoCatalogada;
	private String descripcion;
	
	//nuevos para Settlement
	private String stPais;
	private String stEstado;
	private String stMunicipio;
	private String stColonia;
	
	//Bitacora
	private String idTipoOperacionBitacora;
	
	
	public LocationInfoDto(){}
	
	public LocationInfoDto(String idEmpresaConf,String idPersona,String idEmpresa,String idPosicion, boolean parcial){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.idEmpresa=idEmpresa;
		this.idPosicion=idPosicion;
		this.setParcial(parcial);
	}
	
	public LocationInfoDto(Long idDomicilio, BigDecimal googleLatitude, BigDecimal googleLongitude){
		this.idDomicilio=idDomicilio == null ? null:String.valueOf(idDomicilio);
		this.googleLatitude=googleLatitude == null ? null:String.valueOf(googleLatitude);
		this.googleLongitude=googleLongitude == null ? null:String.valueOf(googleLongitude);
	}
	
	
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getCalle() {
		return calle;
	}
	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}
	public String getNumeroExterior() {
		return numeroExterior;
	}
	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}
	public String getNumeroInterior() {
		return numeroInterior;
	}
		
	
	public void setGoogleLatitude(String googleLatitude) {
		this.googleLatitude = googleLatitude;
	}
	public String getGoogleLatitude() {
		return googleLatitude;
	}
	public void setGoogleLongitude(String googleLongitude) {
		this.googleLongitude = googleLongitude;
	}
	public String getGoogleLongitude() {
		return googleLongitude;
	}
	
	public void setDireccionFacturacion(String direccionFacturacion) {
		this.direccionFacturacion = direccionFacturacion;
	}
	public String getDireccionFacturacion() {
		return direccionFacturacion;
	}
	
	public void setDireccionNoCatalogada(String direccionNoCatalogada) {
		this.direccionNoCatalogada = direccionNoCatalogada;
	}
	public String getDireccionNoCatalogada() {
		return direccionNoCatalogada;
	}
	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}
	public String getIdEstado() {
		return idEstado;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getIdPais() {
		return idPais;
	}
	public void setIdCiudad(String idCiudad) {
		this.idCiudad = idCiudad;
	}
	public String getIdCiudad() {
		return idCiudad;
	}
	public void setIdDomicilio(String idDomicilio) {
		this.idDomicilio = idDomicilio;
	}
	public String getIdDomicilio() {
		return idDomicilio;
	}
	public void setIdTipoDomicilio(String idTipoDomicilio) {
		this.idTipoDomicilio = idTipoDomicilio;
	}
	public String getIdTipoDomicilio() {
		return idTipoDomicilio;
	}
	public void setIdAsentamiento(String idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}
	public String getIdAsentamiento() {
		return idAsentamiento;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdCodigoProceso(String idCodigoProceso) {
		this.idCodigoProceso = idCodigoProceso;
	}
	public String getIdCodigoProceso() {
		return idCodigoProceso;
	}
	public void setIdTipoPrecision(String idTipoPrecision) {
		this.idTipoPrecision = idTipoPrecision;
	}
	public String getIdTipoPrecision() {
		return idTipoPrecision;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

	public String getStPais() {
		return stPais;
	}

	public void setStPais(String stPais) {
		this.stPais = stPais;
	}

	public String getStEstado() {
		return stEstado;
	}

	public void setStEstado(String stEstado) {
		this.stEstado = stEstado;
	}

	public String getStMunicipio() {
		return stMunicipio;
	}

	public void setStMunicipio(String stMunicipio) {
		this.stMunicipio = stMunicipio;
	}

	public String getStColonia() {
		return stColonia;
	}

	public void setStColonia(String stColonia) {
		this.stColonia = stColonia;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getIdTipoOperacionBitacora() {
		return idTipoOperacionBitacora;
	}

	public void setIdTipoOperacionBitacora(String idTipoOperacionBitacora) {
		this.idTipoOperacionBitacora = idTipoOperacionBitacora;
	}
}
