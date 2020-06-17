package com.hipot;

public class DatosInicialesDto {

	private Double prestamo;
	private Double cuotaMen;
	private Double segDanos;
	private Double segCasaI;
	private Double factorVida;
	private Integer nPagoIni;
	private Integer nPagoFin;
	private Integer nAnios;
	private Integer renglon;
	private Integer anioIni;
	private Double tasa;
	
	private int tipoAdelanto =0;
	
	public DatosInicialesDto() {
		// TODO Auto-generated constructor stub
	}
	
	public DatosInicialesDto(int fase) {
		if(fase ==0){
			/* -->ORIGINAL: 1, 240, 519792.00, 0, 1 */
			this.setnPagoIni(0); this.setnPagoFin(240);this.setPrestamo(519792.00);this.setRenglon(0);this.setAnioIni(1);
			this.setCuotaMen(5197.92);//Varia, calcular con interes+Capital del documento
			//this.setnAnios(20);
			this.setSegDanos(123.15);//Varia, copiar el de documento
			this.setSegCasaI(243.47);	//Varia
			this.setFactorVida(.000233);	//Varia (Cambiar hasta obtener las de documento)
			this.setTasa(11.04688379);
		}
		else if(fase ==1){ /* Reducción de cuota: Solo se recalcula saldo a plazo original */
			this.setnPagoIni(38); this.setnPagoFin(240);this.setPrestamo(441588.68);this.setRenglon(2);this.setAnioIni(3);
			this.setCuotaMen(4673.79);//Varia, calcular con interes+Capital del documento
			this.setSegDanos(127.14);//Varia, copiar el de documento
			this.setSegCasaI(243.47);	//Varia
			this.setFactorVida(.000258659);	//Varia (Cambiar hasta obtener las de documento)
			this.setTasa(11.04688379);
		}
		else { /* Reducción a plazo: */
			this.setnPagoIni(88); 
//			this.setnPagoFin(240);//Se va a obtener hasta pagar el prestamo
			this.setPrestamo(78268.78);
			this.setRenglon(4);
			this.setAnioIni(5);
			this.setCuotaMen(1493.26);//Varia, calcular con interes+Capital del documento
			this.setSegDanos(127.14);//Varia, copiar el de documento
			this.setSegCasaI(250.77);	//Varia
			this.setFactorVida(.00035502);	//Varia (Cambiar hasta obtener las de documento)
			this.setTasa(11.02268879);	//11.04688379
		}
	}
	
	public Double getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Double prestamo) {
		this.prestamo = prestamo;
	}
	public Double getCuotaMen() {
		return cuotaMen;
	}
	public void setCuotaMen(Double cuotaMen) {
		this.cuotaMen = cuotaMen;
	}
	public Double getSegDanos() {
		return segDanos;
	}
	public void setSegDanos(Double segDanos) {
		this.segDanos = segDanos;
	}
	public Double getSegCasaI() {
		return segCasaI;
	}
	public void setSegCasaI(Double segCasaI) {
		this.segCasaI = segCasaI;
	}
	public Double getFactorVida() {
		return factorVida;
	}
	public void setFactorVida(Double factorVida) {
		this.factorVida = factorVida;
	}
	public Integer getnPagoIni() {
		return nPagoIni;
	}
	public void setnPagoIni(Integer nPagoIni) {
		this.nPagoIni = nPagoIni;
	}
	public Integer getnPagoFin() {
		return nPagoFin;
	}
	public void setnPagoFin(Integer nPagoFin) {
		this.nPagoFin = nPagoFin;
	}
	public Integer getnAnios() {
		return nAnios;
	}
	public void setnAnios(Integer nAnios) {
		this.nAnios = nAnios;
	}
	public Integer getRenglon() {
		return renglon;
	}
	public void setRenglon(Integer renglon) {
		this.renglon = renglon;
	}
	public Integer getAnioIni() {
		return anioIni;
	}
	public void setAnioIni(Integer anioIni) {
		this.anioIni = anioIni;
	}
	public int getTipoAdelanto() {
		return tipoAdelanto;
	}
	public void setTipoAdelanto(int tipoAdelanto) {
		this.tipoAdelanto = tipoAdelanto;
	}

	public Double getTasa() {
		return tasa;
	}

	public void setTasa(Double tasa) {
		this.tasa = tasa;
	}
}
