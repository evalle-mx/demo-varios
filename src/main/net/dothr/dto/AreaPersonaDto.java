package net.dothr.dto;

public class AreaPersonaDto extends ComunDto {

	private String idAreaPersona;
	private String idArea;
	private String idPersona;
	private String lbArea;
//	private String principal;
	private Boolean principal;
	private Boolean confirmada;
	private Boolean personal;
	
	private String idAreaPerfil;
	private String idPosicion;
	
	private Boolean toDelete;
	
	
	
	public AreaPersonaDto() {
	}
	
	public AreaPersonaDto(Long iAreaPersona, Long iArea, Long iPersona) {
		this.idAreaPersona=iAreaPersona!=null?String.valueOf(iAreaPersona):null;
		this.idArea=iArea!=null?String.valueOf(iArea):null;
		this.idPersona=iPersona!=null?String.valueOf(iPersona):null;
	}
	
	public AreaPersonaDto(Long idAreaPersona, Long idArea, String descripcionArea,
			Boolean principal, Boolean confirmada,  Boolean personal) {
		this.idAreaPersona=idAreaPersona!=null?String.valueOf(idAreaPersona):null;
		this.idArea=idArea!=null?String.valueOf(idArea):null;
		this.lbArea=descripcionArea;
		this.principal=principal;
		this.confirmada=confirmada;
		this.personal=personal;
	}
	
	public String getIdAreaPersona() {
		return idAreaPersona;
	}
	public void setIdAreaPersona(String idAreaPersona) {
		this.idAreaPersona = idAreaPersona;
	}
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getLbArea() {
		return lbArea;
	}

	public void setLbArea(String lbArea) {
		this.lbArea = lbArea;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public Boolean getConfirmada() {
		return confirmada;
	}

	public void setConfirmada(Boolean confirmada) {
		this.confirmada = confirmada;
	}

	public Boolean getPersonal() {
		return personal;
	}

	public void setPersonal(Boolean personal) {
		this.personal = personal;
	}

	public Boolean getToDelete() {
		return toDelete;
	}

	public void setToDelete(Boolean toDelete) {
		this.toDelete = toDelete;
	}

	public String getIdAreaPerfil() {
		return idAreaPerfil;
	}

	public void setIdAreaPerfil(String idAreaPerfil) {
		this.idAreaPerfil = idAreaPerfil;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
}
