package com.pronos.vo;

public class NumeroSorteoVo implements Comparable<Object> {
	
	private Integer numero;
	private Integer frecuencia;
	
	public NumeroSorteoVo() {
	}
	
	public NumeroSorteoVo(Integer numero, Integer frecuencia) {
		this.numero=numero;
		this.frecuencia=frecuencia;
	}
	
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public Integer getFrecuencia() {
		return frecuencia;
	}
	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}
	
	@Override
	public int compareTo(Object o) {
		NumeroSorteoVo numSor = (NumeroSorteoVo)o;
		
		if(this.frecuencia.compareTo(numSor.frecuencia) == 0){ 
			return this.numero.compareTo(numSor.numero);
		}else{
			return this.frecuencia.compareTo(numSor.frecuencia);
		}
	}
	
	@Override
	public String toString(){
		return "["+this.numero+"]=\t"+this.frecuencia;
	}
}
