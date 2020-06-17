package net.dothr.dto;

public class SeguroDto extends ComunDto{
	
	private String idSeguro;
	private String idPersona;
	private String idTipoSeguro;
	private String lbTipoSeguro;
	private String familiar;
	
	public String getIdSeguro() {
		return idSeguro;
	}
	public void setIdSeguro(String idSeguro) {
		this.idSeguro = idSeguro;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdTipoSeguro() {
		return idTipoSeguro;
	}
	public void setIdTipoSeguro(String idTipoSeguro) {
		this.idTipoSeguro = idTipoSeguro;
	}
	public String getLbTipoSeguro() {
		return lbTipoSeguro;
	}
	public void setLbTipoSeguro(String lbTipoSeguro) {
		this.lbTipoSeguro = lbTipoSeguro;
	}
	public String getFamiliar() {
		return familiar;
	}
	public void setFamiliar(String familiar) {
		this.familiar = familiar;
	}
}
