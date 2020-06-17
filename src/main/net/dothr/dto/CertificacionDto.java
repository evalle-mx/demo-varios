package net.dothr.dto;

public class CertificacionDto extends ComunDto {
	
	private String idCertificacion;
	// Persona 
	private String idPersona;
	private String idGrado;
	private String lbGrado;
	private String tituloCert;
	private String institucion;
	private String anio;
	private Boolean terminado;
	
	// Posicion
	private String idPosicion;
	private String idPerfil;
	private String idPoliticaValor;
	private String idPoliticaMValor;
	
	//Bitacora
	private String idTipoOperacionBitacora;
	private String idPolitica;
	
	
	public CertificacionDto() {}
	
	/* Constructores para Persona */
	public CertificacionDto(String idEmpresaConf, String idPersona){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
	}
	public CertificacionDto(Long idCertificacion, Long idPersona, Long idGrado, String lbGrado, String tituloCert, String institucion) {
		this.idCertificacion= idCertificacion!=null?String.valueOf(idCertificacion):null;
		this.idPersona= idPersona!=null?String.valueOf(idPersona):null;
		this.idGrado= idGrado!=null?String.valueOf(idGrado):null;
		this.lbGrado= lbGrado;
		this.tituloCert= tituloCert;
		this.institucion= institucion;
	}
	public CertificacionDto(Long idCertificacion, Long idPersona, Long idGrado, 
			String lbGrado, String tituloCert, String institucion, Short anio) {
		this.idCertificacion= idCertificacion!=null?String.valueOf(idCertificacion):null;
		this.idPersona= idPersona!=null?String.valueOf(idPersona):null;
		this.idGrado= idGrado!=null?String.valueOf(idGrado):null;
		this.lbGrado= lbGrado;
		this.tituloCert= tituloCert;
		this.institucion= institucion;
		this.anio= anio!=null?String.valueOf(anio):null;
	}
	/* Constructores para Posicion */
	public CertificacionDto(String idEmpresaConf, String idPosicion, String idPoliticaValor){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPosicion=idPosicion;
		this.idPoliticaValor=idPoliticaValor;
	}
	public CertificacionDto(Long idCertificacion, Long idPosicion, Long idPoliticaValor, Long idGrado, 
			String lbGrado, String tituloCert) {
		this.idCertificacion= idCertificacion!=null?String.valueOf(idCertificacion):null;
		this.idPosicion= idPosicion!=null?String.valueOf(idPosicion):null;
		this.idPerfil=idPerfil!=null?String.valueOf(idPerfil):null;
		this.idGrado= idGrado!=null?String.valueOf(idGrado):null;
		this.lbGrado= lbGrado;
		this.tituloCert= tituloCert;
	}
	public String getIdCertificacion() {
		return idCertificacion;
	}
	public void setIdCertificacion(String idCertificacion) {
		this.idCertificacion = idCertificacion;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdGrado() {
		return idGrado;
	}
	public void setIdGrado(String idGrado) {
		this.idGrado = idGrado;
	}
	public String getLbGrado() {
		return lbGrado;
	}
	public void setLbGrado(String lbGrado) {
		this.lbGrado = lbGrado;
	}
	public String getTituloCert() {
		return tituloCert;
	}
	public void setTituloCert(String tituloCert) {
		this.tituloCert = tituloCert;
	}
	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public Boolean getTerminado() {
		return terminado;
	}
	public void setTerminado(Boolean terminado) {
		this.terminado = terminado;
	}
	public String getIdPosicion() {
		return idPosicion;
	}
	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}
	public String getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getIdPoliticaValor() {
		return idPoliticaValor;
	}

	public void setIdPoliticaValor(String idPoliticaValor) {
		this.idPoliticaValor = idPoliticaValor;
	}

	public String getIdTipoOperacionBitacora() {
		return idTipoOperacionBitacora;
	}

	public void setIdTipoOperacionBitacora(String idTipoOperacionBitacora) {
		this.idTipoOperacionBitacora = idTipoOperacionBitacora;
	}

	public String getIdPolitica() {
		return idPolitica;
	}

	public void setIdPolitica(String idPolitica) {
		this.idPolitica = idPolitica;
	}

	public String getIdPoliticaMValor() {
		return idPoliticaMValor;
	}

	public void setIdPoliticaMValor(String idPoliticaMValor) {
		this.idPoliticaMValor = idPoliticaMValor;
	}
}
