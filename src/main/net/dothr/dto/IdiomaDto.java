package net.dothr.dto;

public class IdiomaDto  extends ComunDto {

	private String idPersonaIdioma;
	private String idPersona;
	private String idPosicionIdioma;//idPoliticaMValor
	private String idPolitica;
	private String idPoliticaValor;
	private String idPosicion;
	private String idPoliticaMValor;

	
	private String idIdioma;
	private String idDominio;
	private String descripcion;
	private String ponderacion;
	
	//Etiquetas para READ
	private String lbDominio;	//==>idDominio 
	private String lbIdioma;	//==>idIdioma 
	
	//Bitacora
	private String idTipoOperacionBitacora;
	
	
	public IdiomaDto() {
	}
	
	public IdiomaDto(String idEmpresaConf, String idPersona, String idPosicion) {
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.idPosicion=idPosicion;
	}
	
	/* Constructor para Idioma-Persona */
	public IdiomaDto(Long idPersonaIdioma, Long idPersona, 
			Long idIdioma, Long idDominio){
		this.idPersonaIdioma=idPersonaIdioma!=null?String.valueOf(idPersonaIdioma):null;
		this.idPersona=idPersona!=null?String.valueOf(idPersona):null;		
		this.idIdioma=idIdioma!=null?String.valueOf(idIdioma):null;
		this.idDominio=idDominio!=null?String.valueOf(idDominio):null;
	}
	
	/* Constructor para Idioma-Persona con Etiquetas */
	public IdiomaDto(Long idPersonaIdioma, Long idPersona,
			Long idIdioma, String lbIdioma, Long idDominio, String lbDominio){
		this.idPersonaIdioma=idPersonaIdioma!=null?String.valueOf(idPersonaIdioma):null;
		this.idPersona=idPersona!=null?String.valueOf(idPersona):null;
		
		this.idIdioma=idIdioma!=null?String.valueOf(idIdioma):null;
		this.lbIdioma=lbIdioma;
		this.idDominio=idDominio!=null?String.valueOf(idDominio):null;
		this.lbDominio=lbDominio;
	}
	
	public IdiomaDto(
			Long idPoliticaMValor, 
			Long idPosicion,
			Long idPoliticaValor,
			Long idDominio, 
			Long idIdioma, 
			Short ponderacion, 
			Long idPolitica 
			) {
		
		this.idPosicionIdioma=idPoliticaMValor!=null?String.valueOf(idPoliticaMValor):null;
		this.idPosicion=idPosicion!=null?String.valueOf(idPosicion):null;
		this.idPoliticaValor=idPoliticaValor!=null?String.valueOf(idPoliticaValor):null;
		this.idDominio=idDominio!=null?String.valueOf(idDominio) :null;
		this.idIdioma=idIdioma!=null?String.valueOf(idIdioma) :null; 
		this.ponderacion=ponderacion!=null?String.valueOf(ponderacion) :null;
		this.idPolitica=idPolitica!=null?String.valueOf(idPolitica):null;
		
	}
	
	
	
	
	
	public IdiomaDto(String idPersonaIdioma, String idPersona, String idPosicionIdioma, String idPosicion, 
			String idIdioma, String lbIdioma, String idDominio, String lbDominio) {
		this.idPersonaIdioma=idPersonaIdioma;
		this.idPersona=idPersona;
		this.idPosicionIdioma=idPosicionIdioma;
		this.idPosicion=idPosicion;
		
		this.idIdioma=idIdioma;
		this.idDominio=idDominio;
	}
	
	
	
	public String getIdPersonaIdioma() {
		return idPersonaIdioma;
	}
	public void setIdPersonaIdioma(String idPersonaIdioma) {
		this.idPersonaIdioma = idPersonaIdioma;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
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
	public String getLbIdioma() {
		return lbIdioma;
	}
	public void setLbIdioma(String lbIdioma) {
		this.lbIdioma = lbIdioma;
	}
	public String getIdPosicionIdioma() {
		return idPosicionIdioma;
	}
	public void setIdPosicionIdioma(String idPosicionIdioma) {
		this.idPosicionIdioma = idPosicionIdioma;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getIdPolitica() {
		return idPolitica;
	}

	public void setIdPolitica(String idPolitica) {
		this.idPolitica = idPolitica;
	}

	public String getIdPoliticaValor() {
		return idPoliticaValor;
	}

	public void setIdPoliticaValor(String idPoliticaValor) {
		this.idPoliticaValor = idPoliticaValor;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}

	public String getIdPoliticaMValor() {
		return idPoliticaMValor;
	}

	public void setIdPoliticaMValor(String idPoliticaMValor) {
		this.idPoliticaMValor = idPoliticaMValor;
	}

	public String getIdTipoOperacionBitacora() {
		return idTipoOperacionBitacora;
	}

	public void setIdTipoOperacionBitacora(String idTipoOperacionBitacora) {
		this.idTipoOperacionBitacora = idTipoOperacionBitacora;
	}

}
