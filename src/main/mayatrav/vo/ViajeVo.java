package mayatrav.vo;

import java.util.List;

public class ViajeVo {
	
	private String name="";
	private String active="";
	private String homeImg="";
	private String menuImg="";
	private String tabName="";
	private String titulo="";
	private String fecha="";
	private String duracion="";
	private String costo="";
	private String descripcion="";
	private String condiciones="";
	private String salida="";
	private String regreso="";
	private String linkEvento="";
	
	private List<String> incluye;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHomeImg() {
		return homeImg;
	}
	public void setHomeImg(String homeImg) {
		this.homeImg = homeImg;
	}
	public String getMenuImg() {
		return menuImg;
	}
	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getCosto() {
		return costo;
	}
	public void setCosto(String costo) {
		this.costo = costo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCondiciones() {
		return condiciones;
	}
	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}
	public String getSalida() {
		return salida;
	}
	public void setSalida(String salida) {
		this.salida = salida;
	}
	public String getRegreso() {
		return regreso;
	}
	public void setRegreso(String regreso) {
		this.regreso = regreso;
	}
	public String getLinkEvento() {
		return linkEvento;
	}
	public void setLinkEvento(String linkEvento) {
		this.linkEvento = linkEvento;
	}
	public List<String> getIncluye() {
		return incluye;
	}
	public void setIncluye(List<String> incluye) {
		this.incluye = incluye;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
}
