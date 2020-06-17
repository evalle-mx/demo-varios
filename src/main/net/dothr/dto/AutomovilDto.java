package net.dothr.dto;


public class AutomovilDto extends ComunDto{

	private String idPersona;
	private String perteneceCompania;
	private String marca;
	private String modelo;
	private String gastosPagados;
	private String opcionCompra;
	private String tiempoCambio;
	

	public String getPerteneceCompania() {
		return perteneceCompania;
	}
	public void setPerteneceCompania(String perteneceCompania) {
		this.perteneceCompania = perteneceCompania;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getGastosPagados() {
		return gastosPagados;
	}
	public void setGastosPagados(String gastosPagados) {
		this.gastosPagados = gastosPagados;
	}
	public String getOpcionCompra() {
		return opcionCompra;
	}
	public void setOpcionCompra(String opcionCompra) {
		this.opcionCompra = opcionCompra;
	}
	public String getTiempoCambio() {
		return tiempoCambio;
	}
	public void setTiempoCambio(String tiempoCambio) {
		this.tiempoCambio = tiempoCambio;
	}
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
}
