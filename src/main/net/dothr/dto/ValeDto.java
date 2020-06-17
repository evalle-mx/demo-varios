package net.dothr.dto;

public class ValeDto extends ComunDto{

	private String idVale;
	private String idPersona;
	private String idTipoVale;
	private String lbTipoVale;
	private String cantidad;
	private String activo;
	
	public String getIdVale() {
		return idVale;
	}
	public void setIdVale(String idVale) {
		this.idVale = idVale;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdTipoVale() {
		return idTipoVale;
	}
	public String getLbTipoVale() {
		return lbTipoVale;
	}
	public void setLbTipoVale(String lbTipoVale) {
		this.lbTipoVale = lbTipoVale;
	}
	public void setIdTipoVale(String idTipoVale) {
		this.idTipoVale = idTipoVale;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
}
