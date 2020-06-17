package net.dothr.dto;

public class PersonSkillDto extends ComunDto {
	private String idPersona;
	private String idHabilidad;
	private String idDominio;
	private String descripcion;
	//Etiquetas para Persona [Candidato.Read]
	private String lbDominio;	//==>idDominio
	
	public PersonSkillDto(){}
	
	public PersonSkillDto(String idEmpresaConf,String idPersona){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
	}
	
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
	
	public void setIdHabilidad(String idHabilidad) {
		this.idHabilidad = idHabilidad;
	}
	public String getIdHabilidad() {
		return idHabilidad;
	}
	
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}
	public String getIdDominio() {
		return idDominio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLbDominio() {
		return lbDominio;
	}

	public void setLbDominio(String lbDominio) {
		this.lbDominio = lbDominio;
	}
	
}
