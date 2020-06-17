package net.dothr.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author DhrDeveloper
 */
public class CurriculumDto extends ComunDto {
	
	private String idPersona;
	private String idExterno;
	private String idPosicion;
	private String idEmpresa;
	private String email;
	private String password;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String anioNacimiento;
	private String mesNacimiento;
	private String diaNacimiento;
	private String idTipoGenero;
	private String idEstadoCivil;
    private String idPeriodoEstadoCivil;
	private String permisoTrabajo;
	private String idPais;
	private String numeroHijos;
	private String idTipoEstatusPadres;
	private String idTipoConvivencia;
	private String idTipoVivienda;
    private String numeroDependientesEconomicos;
    private String cambioDomicilio;
	private String idTipoDispViajar;
	private String idAmbitoGeografico;
	private String disponibilidadHorario;
	private String idTipoJornada;
	private String salarioMin;
	private String salarioMax;
	private String antiguedadDomicilio;
	private String idEstatusInscripcion;
	private String idTipoContrato;
	private String idTipoPersona;
	private String idTipoPrestacion;
	private String fechaModificacion;
	private String fechaCreacion;
	private String diasExperienciaLaboral;
	private String idGradoAcademicoMax;
	private String idEstatusEscolarMax;
	private String role;
	
	//CvS masivo
	private String stPais;
	private String edad;
	private String claveInterna;
	
	private List<LocationInfoDto> localizacion;
	private List<WorkExperienceDto> experienciaLaboral;
	private List<AcademicBackgroundDto> escolaridad;
	
	private List<ContactInfoDto> contacto;
	private List<PersonSkillDto> habilidad;
	private List<PermisoDto> permiso;
	private List<IdiomaDto> idioma;
	private List<CertificacionDto> certificacion;
	private List<FileDto> contenido;
	
	private List<AreaPersonaDto> areaPersona;
	
	private String tokenSocial;
	private String tokenSecret;
	private String alMenosUnaEscOExp;
	
	private String fechaNacimiento;	// ==> +
	private String lbGenero;		// ==> idTipoGenero 
	private String lbEstadoCivil;	// ==> idEstadoCivil 
	private String lbDispViajar;	// ==> idTipoDispViajar
	private String lbGradoMax;		// ==> idGradoAcademicoMax
	private String lbEstatusMax;	// ==> idEstatusEscolarMax
	private String nombreCompleto;	//+
	private String idRol;
	private String vistaInicial;
	private String idRelacionEmpresaPersona;
	private String passwordUpd1;
	private String passwordUpd2;
	private FileDto imgPerfil;
	
	private String escandidato;
	private String bcompensacion;
	
	/* Constructor's */
	public CurriculumDto(){}
	
	public CurriculumDto(Long idPersona, Long role){
		this.idPersona=idPersona.toString();
		this.setRole(role.toString());
	}
	
	public CurriculumDto(Long idPersona, Long role, Long idEmpresa, String vistaInicial){
		this.idPersona=idPersona.toString();
		this.setRole(role.toString());
		this.idEmpresa=idEmpresa.toString();
		this.vistaInicial=vistaInicial;
	}
	
	public CurriculumDto(String passw){
		this.password=passw;
	}
	
	public CurriculumDto(Long idPersona){
		this.idPersona=idPersona.toString();;
	}
	
	public CurriculumDto(String idPersona, String idEstatusInscripcion){
		this.idPersona=idPersona;
		this.idEstatusInscripcion=idEstatusInscripcion;
	}

	public CurriculumDto(String nombre,String apellidoPaterno,String apellidoMaterno){
		this.nombre=nombre;
		this.apellidoPaterno=apellidoPaterno;
		this.apellidoMaterno=apellidoMaterno;
	}
	public CurriculumDto(Long idPersona,Long idEmpresaExterno,String claveInterna,String dummy){
		this.idPersona=idPersona.toString();
		this.idEmpresa=idEmpresa != null ? idEmpresa.toString():null;
		this.claveInterna=claveInterna;
	}
	
	/**
	 * Constructor para Busqueda de persona
	 * @param idPersona
	 * @param idEstatusInscripcion
	 * @param email
	 * @param nombre
	 * @param apellidoPaterno
	 * @param apellidoMaterno
	 * @param idRol
	 */
	public CurriculumDto(Long idPersona, Long idEstatusInscripcion, String email, String nombre, 
			String apellidoPaterno, String apellidoMaterno, Long idRol, Long idRelacionEmpresaPersona){
		System.out.println("\n\n\n?????????????????????  \n constructor Busqueda \n ¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿ \n\n");
		this.idPersona=idPersona==null? null: String.valueOf(idPersona);
		this.email=email;
		this.idEstatusInscripcion=idEstatusInscripcion == null ? null:String.valueOf(idEstatusInscripcion);
		this.nombre=nombre;
		this.apellidoPaterno=apellidoPaterno;
		this.apellidoMaterno=apellidoMaterno;
		this.idRol=idRol==null?null:String.valueOf(idRol);
		this.idRelacionEmpresaPersona=idRelacionEmpresaPersona==null?null:String.valueOf(idRelacionEmpresaPersona);
	}
	
	public CurriculumDto(Long idPersona,Long idTipoGenero, Boolean permisoTrabajo, Long idPais,
						Boolean cambioDomicilio,Long idTipoDispViajar,Long idAmbitoGeografico,
						Boolean disponibilidadHorario, Long idTipoJornada, Long idTipoContrato,
						Long idTipoPrestacion){
		this.idPersona=idPersona == null ? null:String.valueOf(idPersona);
		this.idTipoGenero=idTipoGenero == null ? null:String.valueOf(idTipoGenero);
		this.permisoTrabajo=permisoTrabajo == null ? null:(String.valueOf(permisoTrabajo ? "1":"0"));
		this.idPais=idPais == null ? null:String.valueOf(idPais);
		this.cambioDomicilio=cambioDomicilio == null ? null:(String.valueOf(cambioDomicilio ? "1":"0"));
		this.idTipoDispViajar=idTipoDispViajar == null ? null:String.valueOf(idTipoDispViajar);
		this.idAmbitoGeografico=idAmbitoGeografico == null ? null:String.valueOf(idAmbitoGeografico);
		this.disponibilidadHorario=disponibilidadHorario == null ? null:(String.valueOf(disponibilidadHorario ? "1":"0"));
		this.idTipoJornada=idTipoJornada == null ? null:String.valueOf(idTipoJornada);
		this.idTipoContrato=idTipoContrato == null ? null:String.valueOf(idTipoContrato);
		this.idTipoPrestacion=idTipoPrestacion == null ? null:String.valueOf(idTipoPrestacion);
	}
	
	public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

	/* Getters & setters */
	
	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}

	public String getIdPosicion() {
		return idPosicion;
	}

	public void setIdPosicion(String idPosicion) {
		this.idPosicion = idPosicion;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(String anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public String getMesNacimiento() {
		return mesNacimiento;
	}

	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public String getDiaNacimiento() {
		return diaNacimiento;
	}

	public void setDiaNacimiento(String diaNacimiento) {
		this.diaNacimiento = diaNacimiento;
	}

	public String getIdTipoGenero() {
		return idTipoGenero;
	}

	public void setIdTipoGenero(String idTipoGenero) {
		this.idTipoGenero = idTipoGenero;
	}

	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}

	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	public String getIdPeriodoEstadoCivil() {
		return idPeriodoEstadoCivil;
	}

	public void setIdPeriodoEstadoCivil(String idPeriodoEstadoCivil) {
		this.idPeriodoEstadoCivil = idPeriodoEstadoCivil;
	}

	public String getPermisoTrabajo() {
		return permisoTrabajo;
	}

	public void setPermisoTrabajo(String permisoTrabajo) {
		this.permisoTrabajo = permisoTrabajo;
	}

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getNumeroHijos() {
		return numeroHijos;
	}

	public void setNumeroHijos(String numeroHijos) {
		this.numeroHijos = numeroHijos;
	}

	public String getIdTipoEstatusPadres() {
		return idTipoEstatusPadres;
	}

	public void setIdTipoEstatusPadres(String idTipoEstatusPadres) {
		this.idTipoEstatusPadres = idTipoEstatusPadres;
	}

	public String getIdTipoConvivencia() {
		return idTipoConvivencia;
	}

	public void setIdTipoConvivencia(String idTipoConvivencia) {
		this.idTipoConvivencia = idTipoConvivencia;
	}

	public String getIdTipoVivienda() {
		return idTipoVivienda;
	}

	public void setIdTipoVivienda(String idTipoVivienda) {
		this.idTipoVivienda = idTipoVivienda;
	}

	public String getNumeroDependientesEconomicos() {
		return numeroDependientesEconomicos;
	}

	public void setNumeroDependientesEconomicos(String numeroDependientesEconomicos) {
		this.numeroDependientesEconomicos = numeroDependientesEconomicos;
	}

	public String getCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getIdTipoDispViajar() {
		return idTipoDispViajar;
	}

	public void setIdTipoDispViajar(String idTipoDispViajar) {
		this.idTipoDispViajar = idTipoDispViajar;
	}

	public String getIdAmbitoGeografico() {
		return idAmbitoGeografico;
	}

	public void setIdAmbitoGeografico(String idAmbitoGeografico) {
		this.idAmbitoGeografico = idAmbitoGeografico;
	}

	public String getDisponibilidadHorario() {
		return disponibilidadHorario;
	}

	public void setDisponibilidadHorario(String disponibilidadHorario) {
		this.disponibilidadHorario = disponibilidadHorario;
	}

	public String getIdTipoJornada() {
		return idTipoJornada;
	}

	public void setIdTipoJornada(String idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
	}

	public String getSalarioMin() {
		return salarioMin;
	}

	public void setSalarioMin(String salarioMin) {
		this.salarioMin = salarioMin;
	}

	public String getSalarioMax() {
		return salarioMax;
	}

	public void setSalarioMax(String salarioMax) {
		this.salarioMax = salarioMax;
	}

	public String getAntiguedadDomicilio() {
		return antiguedadDomicilio;
	}

	public void setAntiguedadDomicilio(String antiguedadDomicilio) {
		this.antiguedadDomicilio = antiguedadDomicilio;
	}

	public String getIdEstatusInscripcion() {
		return idEstatusInscripcion;
	}

	public void setIdEstatusInscripcion(String idEstatusInscripcion) {
		this.idEstatusInscripcion = idEstatusInscripcion;
	}

	public String getIdTipoContrato() {
		return idTipoContrato;
	}

	public void setIdTipoContrato(String idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	public String getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(String idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public String getIdTipoPrestacion() {
		return idTipoPrestacion;
	}

	public void setIdTipoPrestacion(String idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getDiasExperienciaLaboral() {
		return diasExperienciaLaboral;
	}

	public void setDiasExperienciaLaboral(String diasExperienciaLaboral) {
		this.diasExperienciaLaboral = diasExperienciaLaboral;
	}

	public String getIdGradoAcademicoMax() {
		return idGradoAcademicoMax;
	}

	public void setIdGradoAcademicoMax(String idGradoAcademicoMax) {
		this.idGradoAcademicoMax = idGradoAcademicoMax;
	}

	public String getIdEstatusEscolarMax() {
		return idEstatusEscolarMax;
	}

	public void setIdEstatusEscolarMax(String idEstatusEscolarMax) {
		this.idEstatusEscolarMax = idEstatusEscolarMax;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStPais() {
		return stPais;
	}

	public void setStPais(String stPais) {
		this.stPais = stPais;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getClaveInterna() {
		return claveInterna;
	}

	public void setClaveInterna(String claveInterna) {
		this.claveInterna = claveInterna;
	}

	public List<LocationInfoDto> getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(List<LocationInfoDto> localizacion) {
		this.localizacion = localizacion;
	}

	public List<WorkExperienceDto> getExperienciaLaboral() {
		return experienciaLaboral;
	}

	public void setExperienciaLaboral(List<WorkExperienceDto> experienciaLaboral) {
		this.experienciaLaboral = experienciaLaboral;
	}

	public List<AcademicBackgroundDto> getEscolaridad() {
		return escolaridad;
	}

	public void setEscolaridad(List<AcademicBackgroundDto> escolaridad) {
		this.escolaridad = escolaridad;
	}

	public List<ContactInfoDto> getContacto() {
		return contacto;
	}

	public void setContacto(List<ContactInfoDto> contacto) {
		this.contacto = contacto;
	}

	public List<PersonSkillDto> getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(List<PersonSkillDto> habilidad) {
		this.habilidad = habilidad;
	}

	public List<PermisoDto> getPermiso() {
		return permiso;
	}

	public void setPermiso(List<PermisoDto> permiso) {
		this.permiso = permiso;
	}

	public String getTokenSocial() {
		return tokenSocial;
	}

	public void setTokenSocial(String tokenSocial) {
		this.tokenSocial = tokenSocial;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public String getAlMenosUnaEscOExp() {
		return alMenosUnaEscOExp;
	}

	public void setAlMenosUnaEscOExp(String alMenosUnaEscOExp) {
		this.alMenosUnaEscOExp = alMenosUnaEscOExp;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getLbGenero() {
		return lbGenero;
	}

	public void setLbGenero(String lbGenero) {
		this.lbGenero = lbGenero;
	}

	public String getLbEstadoCivil() {
		return lbEstadoCivil;
	}

	public void setLbEstadoCivil(String lbEstadoCivil) {
		this.lbEstadoCivil = lbEstadoCivil;
	}

	public String getLbDispViajar() {
		return lbDispViajar;
	}

	public void setLbDispViajar(String lbDispViajar) {
		this.lbDispViajar = lbDispViajar;
	}

	public String getLbGradoMax() {
		return lbGradoMax;
	}

	public void setLbGradoMax(String lbGradoMax) {
		this.lbGradoMax = lbGradoMax;
	}

	public String getLbEstatusMax() {
		return lbEstatusMax;
	}

	public void setLbEstatusMax(String lbEstatusMax) {
		this.lbEstatusMax = lbEstatusMax;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getIdRol() {
		return idRol;
	}

	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	public String getIdRelacionEmpresaPersona() {
		return idRelacionEmpresaPersona;
	}

	public void setIdRelacionEmpresaPersona(String idRelacionEmpresaPersona) {
		this.idRelacionEmpresaPersona = idRelacionEmpresaPersona;
	}

	public String getPasswordUpd1() {
		return passwordUpd1;
	}

	public void setPasswordUpd1(String passwordUpd1) {
		this.passwordUpd1 = passwordUpd1;
	}

	public String getPasswordUpd2() {
		return passwordUpd2;
	}

	public void setPasswordUpd2(String passwordUpd2) {
		this.passwordUpd2 = passwordUpd2;
	}

	public List<IdiomaDto> getIdioma() {
		return idioma;
	}

	public void setIdioma(List<IdiomaDto> idioma) {
		this.idioma = idioma;
	}

	public List<FileDto> getContenido() {
		return contenido;
	}

	public void setContenido(List<FileDto> contenido) {
		this.contenido = contenido;
	}

	public FileDto getImgPerfil() {
		return imgPerfil;
	}

	public void setImgPerfil(FileDto imgPerfil) {
		this.imgPerfil = imgPerfil;
	}

	public List<CertificacionDto> getCertificacion() {
		return certificacion;
	}

	public void setCertificacion(List<CertificacionDto> certificacion) {
		this.certificacion = certificacion;
	}

	public List<AreaPersonaDto> getAreaPersona() {
		return areaPersona;
	}

	public void setAreaPersona(List<AreaPersonaDto> areaPersona) {
		this.areaPersona = areaPersona;
	}

	public String getEscandidato() {
		return escandidato;
	}

	public void setEscandidato(String escandidato) {
		this.escandidato = escandidato;
	}

	public String getVistaInicial() {
		return vistaInicial;
	}

	public void setVistaInicial(String vistaInicial) {
		this.vistaInicial = vistaInicial;
	}

	public String getBcompensacion() {
		return bcompensacion;
	}
	public void setBcompensacion(String bcompensacion) { //1 | 0
		this.bcompensacion = bcompensacion;
	}
}
