package net.dothr.dto;

public class SueldoDto  extends ComunDto{

	private String idPersona;
	private String idPeriodicidadSueldo;
	private String lbPeriodicidadSueldo;
	private String cantidad;
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPeriodicidadSueldo() {
		return idPeriodicidadSueldo;
	}
	public void setIdPeriodicidadSueldo(String idPeriodicidadSueldo) {
		this.idPeriodicidadSueldo = idPeriodicidadSueldo;
	}
	public String getLbPeriodicidadSueldo() {
		return lbPeriodicidadSueldo;
	}
	public void setLbPeriodicidadSueldo(String lbPeriodicidadSueldo) {
		this.lbPeriodicidadSueldo = lbPeriodicidadSueldo;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

}
