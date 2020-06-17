package net.dothr.dto;

import java.util.List;


public class WorkExperienceDto extends ComunDto implements Cloneable{
	private String idExperienciaLaboral;
	private String idPersona;
	private String nombreEmpresa;
	private String idPais;
	private String idNivelJerarquico;
	private String idTipoContrato;
	private String idTipoPrestacion;
	private String genteACargo;
	private String anioInicio;
	private String mesInicio;
	private String diaInicio;
	private String anioFin;
	private String mesFin;
	private String diaFin;
    private String trabajoActual;
    private String motivoSeparacion;
    private String referencias;
    private String complementoDireccion;
    private String puesto;
    private String idTipoJornada;
    private String idTextoNgram;
    private String texto;
    private String textoFiltrado;
    private List<ReferenceDto> referencia;
    
    //Etiquetas para Persona [Candidato.Read]
    private String lbPais;	// ==> idPais
    private String lbNivelJerarquico;	// ==> idNivelJerarquico
    private String fechaInicio;	// ==>+
    private String fechaFin;	// ==>+
    private String lbTipoJornada;	// ==>idTipoJornada
	
    public WorkExperienceDto(){}
	
    public WorkExperienceDto(String idEmpresaConf,String idPersona, boolean parcial){
		this.setIdEmpresaConf(idEmpresaConf);
		this.idPersona=idPersona;
		this.setParcial(parcial);
	}
    
	public WorkExperienceDto(Long idExperienciaLaboral,Long idNivelJerarquico, Long idTipoContrato,
							Long idTipoPrestacion,Boolean genteACargo,Short anioInicio,
							Byte mesInicio,Short anioFin,Byte mesFin,Boolean trabajoActual,
							String motivoSeparacion,String complementoDireccion,String puesto,
							String texto) {
		this.idExperienciaLaboral =idExperienciaLaboral == null ? null:String.valueOf(idExperienciaLaboral);
		this.idNivelJerarquico = idNivelJerarquico == null ? null:String.valueOf(idNivelJerarquico);
		this.idTipoContrato = idTipoContrato == null ? null:String.valueOf(idTipoContrato);
		this.idTipoPrestacion = idTipoPrestacion == null ? null:String.valueOf(idTipoPrestacion);
		this.genteACargo = genteACargo == null ? null:(String.valueOf(genteACargo ? "1":"0"));
		this.anioInicio = anioInicio == null ? null:String.valueOf(anioInicio);
		this.mesInicio = mesInicio == null ? null:String.valueOf(mesInicio);
		this.anioFin = anioFin == null ? null:String.valueOf(anioFin);
		this.mesFin = mesFin == null ? null:String.valueOf(mesFin);
		this.trabajoActual = trabajoActual == null ? null:(String.valueOf(trabajoActual ? "1":"0"));
		this.motivoSeparacion = motivoSeparacion;
		this.complementoDireccion = complementoDireccion;
		this.puesto = puesto;
		this.texto = texto;
	}
    
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	
	
	public String getMotivoSeparacion() {
		return motivoSeparacion;
	}
	public void setMotivoSeparacion(String motivoSeparacion) {
		this.motivoSeparacion = motivoSeparacion;
	}
	
	public void setGenteACargo(String genteACargo) {
		this.genteACargo = genteACargo;
	}
	public String getGenteACargo() {
		return genteACargo;
	}


	public void setComplementoDireccion(String complementoDireccion) {
		this.complementoDireccion = complementoDireccion;
	}

	public String getComplementoDireccion() {
		return complementoDireccion;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getPuesto() {
		return puesto;
	}
	public void setIdExperienciaLaboral(String idExperienciaLaboral) {
		this.idExperienciaLaboral = idExperienciaLaboral;
	}
	public String getIdExperienciaLaboral() {
		return idExperienciaLaboral;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	public String getIdPersona() {
		return idPersona;
	}
	
	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}
	public String getIdPais() {
		return idPais;
	}
		
	public void setIdTipoContrato(String idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}
	public String getIdTipoContrato() {
		return idTipoContrato;
	}
	public void setIdNivelJerarquico(String idNivelJerarquico) {
		this.idNivelJerarquico = idNivelJerarquico;
	}
	public String getIdNivelJerarquico() {
		return idNivelJerarquico;
	}
	public void setIdTipoPrestacion(String idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}
	public String getIdTipoPrestacion() {
		return idTipoPrestacion;
	}
	
	public void setTrabajoActual(String trabajoActual) {
		this.trabajoActual = trabajoActual;
	}
	public String getTrabajoActual() {
		return trabajoActual;
	}
	public void setReferencias(String referencias) {
		this.referencias = referencias;
	}
	public String getReferencias() {
		return referencias;
	}
	public void setAnioInicio(String anioInicio) {
		this.anioInicio = anioInicio;
	}
	public String getAnioInicio() {
		return anioInicio;
	}
	public void setMesInicio(String mesInicio) {
		this.mesInicio = mesInicio;
	}
	public String getMesInicio() {
		return mesInicio;
	}
	public void setDiaInicio(String diaInicio) {
		this.diaInicio = diaInicio;
	}
	public String getDiaInicio() {
		return diaInicio;
	}
	public void setAnioFin(String anioFin) {
		this.anioFin = anioFin;
	}
	public String getAnioFin() {
		return anioFin;
	}
	public void setMesFin(String mesFin) {
		this.mesFin = mesFin;
	}
	public String getMesFin() {
		return mesFin;
	}
	public void setDiaFin(String diaFin) {
		this.diaFin = diaFin;
	}
	public String getDiaFin() {
		return diaFin;
	}
	public void setIdTipoJornada(String idTipoJornada) {
		this.idTipoJornada = idTipoJornada;
	}
	public String getIdTipoJornada() {
		return idTipoJornada;
	}

	public void setReferencia(List<ReferenceDto> referencia) {
		this.referencia = referencia;
	}

	public List<ReferenceDto> getReferencia() {
		return referencia;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public void setIdTextoNgram(String idTextoNgram) {
		this.idTextoNgram = idTextoNgram;
	}

	public String getIdTextoNgram() {
		return idTextoNgram;
	}

	public String getTextoFiltrado() {
		return textoFiltrado;
	}

	public void setTextoFiltrado(String textoFiltrado) {
		this.textoFiltrado = textoFiltrado;
	}

	public String getLbPais() {
		return lbPais;
	}

	public void setLbPais(String lbPais) {
		this.lbPais = lbPais;
	}

	public String getLbNivelJerarquico() {
		return lbNivelJerarquico;
	}

	public void setLbNivelJerarquico(String lbNivelJerarquico) {
		this.lbNivelJerarquico = lbNivelJerarquico;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getLbTipoJornada() {
		return lbTipoJornada;
	}

	public void setLbTipoJornada(String lbTipoJornada) {
		this.lbTipoJornada = lbTipoJornada;
	}
	
	 public Object clone(){
	        Object obj=null;
	        try{
	            obj=super.clone();
	        }catch(CloneNotSupportedException ex){
	            System.out.println(" no se puede duplicar WorkExperienceDto");
	        }
	        return obj;
	    }	
}
