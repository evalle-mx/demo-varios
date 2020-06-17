package net.dothr.dto;

public class VacacionesDto extends ComunDto{

	private String idPersona;
	private String valorDias;
	private String valorPrima;

	
	public String getValorDias() {
		return valorDias;
	}
	public void setValorDias(String valorDias) {
		this.valorDias = valorDias;
	}
	public String getValorPrima() {
		return valorPrima;
	}
	public void setValorPrima(String valorPrima) {
		this.valorPrima = valorPrima;
	}
	
	public String getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}
	
	
	
}
