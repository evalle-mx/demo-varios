package com.fotos;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PhotoVo {

	private String nombre;
	private String newName;
	private String path;
	private Long creatTime;
	private Long modifTime;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private String stFCreacion;
	private String stFechaMod;
	private String extension;
	
	public PhotoVo() {	
	}
	
	public PhotoVo(String cvsLine, String sep) {
		List<String> items = Arrays.asList(cvsLine.split("\\s*"+sep+"\\s*"));
		if(items.size()==4){
			this.nombre=items.get(0);
			this.newName=items.get(1);
			this.creatTime=Long.parseLong(items.get(2));
			this.extension=items.get(3);
		}
	}
	
	public PhotoVo(String nombre) {
		this.nombre=nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Long creatTime) {
		this.creatTime = creatTime;
	}

	public Long getModifTime() {
		return modifTime;
	}

	public void setModifTime(Long modifTime) {
		this.modifTime = modifTime;
	}

	public String getStFCreacion() {
		return stFCreacion;
	}

	public void setStFCreacion(String stFCreacion) {
		this.stFCreacion = stFCreacion;
	}

	public String getStFechaMod() {
		return stFechaMod;
	}

	public void setStFechaMod(String stFechaMod) {
		this.stFechaMod = stFechaMod;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
