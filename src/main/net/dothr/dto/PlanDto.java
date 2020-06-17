package net.dothr.dto;

public class PlanDto extends ComunDto{
	
	private String idPlan;
	private String idPersona;
	private String idTipoPlan;
	private String lbTipoPlan;
	private String descripcion;
	
	public String getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(String idPlan) {
		this.idPlan = idPlan;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdTipoPlan() {
		return idTipoPlan;
	}
	public void setIdTipoPlan(String idTipoPlan) {
		this.idTipoPlan = idTipoPlan;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLbTipoPlan() {
		return lbTipoPlan;
	}
	public void setLbTipoPlan(String lbTipoPlan) {
		this.lbTipoPlan = lbTipoPlan;
	}
	
	
}
