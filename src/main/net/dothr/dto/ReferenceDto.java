package net.dothr.dto;



public class ReferenceDto  extends ComunDto {
	private String idReferencia;
	private String nombre;
	private String empresa;
	private String puesto;
	private String email;
	private String telParticular;
	private String telCelular;
	private String idPersona;
	
	public ReferenceDto(){}
		
	public ReferenceDto(String idEmpresaConf,String idPersona){
		this.setIdEmpresaConf(idEmpresaConf);
		this.setIdPersona(idPersona);
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setIdReferencia(String idReferencia) {
		this.idReferencia = idReferencia;
	}
	public String getIdReferencia() {
		return idReferencia;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getTelParticular() {
		return telParticular;
	}

	public void setTelParticular(String telParticular) {
		this.telParticular = telParticular;
	}

	public String getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	
	
}
