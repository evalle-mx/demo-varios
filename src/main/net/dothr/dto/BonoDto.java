package net.dothr.dto;



public class BonoDto extends ComunDto{

	private String idBono;
	private String idPersona;
	private String idPeriodicidadBono;
	private String lbPerioricidadBono;
	private String valorCantidad;
	private String porcentajeCantidad;
	private String tipoBono;
	
	public String getIdBono() {
		return idBono;
	}
	public void setIdBono(String idBono) {
		this.idBono = idBono;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPeriodicidadBono() {
		return idPeriodicidadBono;
	}
	public void setIdPeriodicidadBono(String idPeriodicidadBono) {
		this.idPeriodicidadBono = idPeriodicidadBono;
	}
	public String getLbPerioricidadBono() {
		return lbPerioricidadBono;
	}
	public void setLbPerioricidadBono(String lbPerioricidadBono) {
		this.lbPerioricidadBono = lbPerioricidadBono;
	}
	public String getValorCantidad() {
		return valorCantidad;
	}
	public void setValorCantidad(String valorCantidad) {
		this.valorCantidad = valorCantidad;
	}
	public String getPorcentajeCantidad() {
		return porcentajeCantidad;
	}
	public void setPorcentajeCantidad(String porcentajeCantidad) {
		this.porcentajeCantidad = porcentajeCantidad;
	}
	public String getTipoBono() {
		return tipoBono;
	}
	public void setTipoBono(String tipoBono) {
		this.tipoBono = tipoBono;
	}
}
