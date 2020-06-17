package net.dothr.dto;

public class PermisoDto extends ComunDto{

	private Long idPermiso;
	private Long idPermisoRelacionada;
	private String contexto;
	private String valor;
	private String idTipoPermiso;
	
	/* para servicio de Permisos y Roles */
	private String idRol;
	private String idRolPermiso;
//	private String activo;
	private String activo;
	private Boolean bActivo;
	private String descripcion;
	
	

	public PermisoDto(){}
	
	public PermisoDto(Long idPermiso,Boolean activo){
		this.idPermiso=idPermiso;
		this.bActivo=activo;

	}
	
	public PermisoDto(String contexto, String valor,Byte idTipoPermiso){
		this.contexto=contexto;
		this.valor=valor;
		this.setIdTipoPermiso(idTipoPermiso.toString());
	}
	
	public PermisoDto(Long idPermiso, Long idPermisoRelacionada, String contexto, String valor,Byte idTipoPermiso){
		this.idPermiso=idPermiso;
		this.idPermisoRelacionada=idPermisoRelacionada;
		this.contexto=contexto;
		this.valor=valor;
		this.setIdTipoPermiso(idTipoPermiso.toString());
	}

	public PermisoDto(Long idPermiso, Long idPermisoRelacionada, Byte idTipoPermiso, String contexto, String valor, String descripcion){
		this.idPermiso=idPermiso;
		this.idPermisoRelacionada=idPermisoRelacionada;
		this.contexto=contexto;
		this.valor=valor;
		this.descripcion=descripcion;
		this.setIdTipoPermiso(idTipoPermiso.toString());
	}
	
	public PermisoDto(Long idPermiso, Long idPermisoRelacionada, Long idRolPermiso, String contexto,String valor,Byte idTipoPermiso,
			Long idRol, Boolean activo){
		this.idPermiso=idPermiso;
		this.idPermisoRelacionada=idPermisoRelacionada;
		this.setIdRolPermiso(idRolPermiso!=null?String.valueOf(idRolPermiso):null);
		this.contexto=contexto;
		this.valor=valor;
		this.setIdTipoPermiso(idTipoPermiso.toString());
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.activo=activo!=null?String.valueOf(activo):"false";
	}
	
	public PermisoDto(Long idPermiso, Long idPermisoRelacionada, Long idRolPermiso, String contexto,String valor, Byte idTipoPermiso,
			Long idRol, Boolean activo, String descripcion){
		this.idPermiso=idPermiso;
		this.idPermisoRelacionada=idPermisoRelacionada;
		this.setIdRolPermiso(idRolPermiso!=null?String.valueOf(idRolPermiso):null);
		this.contexto=contexto;
		this.valor=valor;
		this.setIdTipoPermiso(idTipoPermiso.toString());
		this.idRol=idRol!=null?String.valueOf(idRol):null;
		this.activo=activo!=null?String.valueOf(activo):"false";
		this.descripcion=descripcion;
	}
	
	public String getContexto() {
		return contexto;
	}
	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getIdTipoPermiso() {
		return idTipoPermiso;
	}

	public void setIdTipoPermiso(String idTipoPermiso) {
		this.idTipoPermiso = idTipoPermiso;
	}

	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
	}

	public Long getIdPermisoRelacionada() {
		return idPermisoRelacionada;
	}

	public void setIdPermisoRelacionada(Long idPermisoRelacionada) {
		this.idPermisoRelacionada = idPermisoRelacionada;
	}

	public String getIdRol() {
		return idRol;
	}

	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

//	public String getActivo() {
//		return activo;
//	}
//
//	public void setActivo(String activo) {
//		this.activo = activo;
//	}


	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getIdRolPermiso() {
		return idRolPermiso;
	}

	public void setIdRolPermiso(String idRolPermiso) {
		this.idRolPermiso = idRolPermiso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getbActivo() {
		return bActivo;
	}

	public void setbActivo(Boolean bActivo) {
		this.bActivo = bActivo;
	}
	
	
}
