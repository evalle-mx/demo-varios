package com.pronos.vo;

import java.util.ArrayList;
import java.util.Date;
//import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.pronos.util.PronosUtily;


public class MelateVo {

	private Integer nProducto;
	private Integer numConcurso;
	private Integer f1=null;
	private Integer f2=null;
	private Integer f3=null;
	private Integer f4=null;
	private Integer f5=null;
	private Integer f6=null;	
	private Integer fAd=null;
	
	private String bolsa;	
	private Date fecha;
	

	public Integer getnProducto() {
		return nProducto;
	}
	public void setnProducto(Integer nProducto) {
		this.nProducto = nProducto;
	}
	public Integer getNumConcurso() {
		return numConcurso;
	}
	public void setNumConcurso(Integer numConcurso) {
		this.numConcurso = numConcurso;
	}
	public Integer getF1() {
		return f1;
	}
	public void setF1(Integer f1) {
		this.f1 = f1;
	}
	public Integer getF2() {
		return f2;
	}
	public void setF2(Integer f2) {
		this.f2 = f2;
	}
	public Integer getF3() {
		return f3;
	}
	public void setF3(Integer f3) {
		this.f3 = f3;
	}
	public Integer getF4() {
		return f4;
	}
	public void setF4(Integer f4) {
		this.f4 = f4;
	}
	public Integer getF5() {
		return f5;
	}
	public void setF5(Integer f5) {
		this.f5 = f5;
	}
	public Integer getF6() {
		return f6;
	}
	public void setF6(Integer f6) {
		this.f6 = f6;
	}
	public Integer getfAd() {
		return fAd;
	}
	public void setfAd(Integer fAd) {
		this.fAd = fAd;
	}
	public String getBolsa() {
		return bolsa;
	}
	public void setBolsa(String bolsa) {
		this.bolsa = bolsa;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public MelateVo() { }	
	
	public MelateVo(String[] datos) {
		//NPRODUCTO,CONCURSO,F1,F2,F3,F4,F5,F6,F7,BOLSA,FECHA
		this.nProducto=Integer.parseInt(datos[0]);
		this.numConcurso=Integer.parseInt(datos[1]);
		
		this.f1=Integer.parseInt(datos[2]);
		this.f2=Integer.parseInt(datos[3]);
		this.f3=Integer.parseInt(datos[4]);
		this.f4=Integer.parseInt(datos[5]);
		this.f5=Integer.parseInt(datos[6]);
		this.f6=Integer.parseInt(datos[7]);
		this.fAd=Integer.parseInt(datos[8]);
		
		this.bolsa = datos[9].replace("\"", "");
		this.fecha = PronosUtily.string2Date(datos[10]);		
	}	
	
	public MelateVo(ArrayList<Integer> fromList) { 
		if(fromList.size()==6){
			this.f1=fromList.get(0);
			this.f2=fromList.get(1);
			this.f3=fromList.get(2);
			this.f4=fromList.get(3);
			this.f5=fromList.get(4);
			this.f6=fromList.get(5);
		}
	}	
	
	public MelateVo(String linea){
		String[] datos = linea.split(",");
		if(PronosUtily.isNumber(datos[0]) && (datos.length==7 || datos.length==6)){
			this.f1=Integer.parseInt(datos[0]);
			this.f2=Integer.parseInt(datos[1]);
			this.f3=Integer.parseInt(datos[2]);
			this.f4=Integer.parseInt(datos[3]);
			this.f5=Integer.parseInt(datos[4]);
			this.f6=Integer.parseInt(datos[5]);
			if(datos.length==7){
//				log4j.debug(" Adicional");
				this.fAd=Integer.parseInt(datos[6]);
			}
		}
	}
	
	public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
	
	public String toText(){
		return ""+this.f1+","+this.f2+","+this.f3+","+this.f4+","+this.f5+","+this.f6;
	}
	
	public ArrayList<Integer> toList(){
		ArrayList<Integer> list = null;
		if(this.f1!=null && this.f6!=null){
			list = new ArrayList<Integer>();
			list.add(f1);
			list.add(f2);
			list.add(f3);
			list.add(f4);
			list.add(f5);
			list.add(f6);
//			if(this.fAd!=null){
//				list.add(fAd);
//			}
		}
		return list;
	}
	
	public Integer[] toArrayInt(){
		if((this.f1!=null && this.f2!=null) && (this.f3!=null && this.f4!=null) && (this.f5!=null && this.f6!=null) ){
			Integer[] array = new Integer[6];
			array[0]=this.f1;
			array[1]=this.f2;
			array[2]=this.f3;
			array[3]=this.f4;
			array[4]=this.f5;
			array[5]=this.f6;
			return array;
		}else{
			return null;
		}
	}
	/**
	 * verifica existencia de duplicados
	 * regresa false si existe un elemento (f) que se repita en los 6 digitos
	 */
	public boolean noDuplicados(){
		boolean res = true;
		if(this.f1==this.f2 || this.f1==this.f3 ||this.f1==this.f4 ||this.f1==this.f5 ||this.f1==this.f6){
			res = false;
		}else if(this.f2==this.f1 || this.f2==this.f3 ||this.f2==this.f4 ||this.f2==this.f5 ||this.f2==this.f6){
			res = false;
		}else if(this.f3==this.f1 || this.f3==this.f2 ||this.f3==this.f4 ||this.f3==this.f5 ||this.f3==this.f6){
			res = false;
		}else if(this.f4==this.f1 || this.f4==this.f2 ||this.f4==this.f3 ||this.f4==this.f5 ||this.f4==this.f6){
			res = false;
		}else if(this.f5==this.f1 || this.f5==this.f2 ||this.f5==this.f3 ||this.f5==this.f4 ||this.f5==this.f6){
			res = false;
		}else if(this.f6==this.f1 || this.f6==this.f2 ||this.f6==this.f3 ||this.f6==this.f4 ||this.f6==this.f5){
			res = false;
		}
		return res;
	}
	
	/**
	 * Ordena los elementos (f's) en orden ascendente
	 */
	public void reOrder(){
		Integer[] arreglo = this.toArrayInt();
		for(int i=0; i<arreglo.length;i++){
			int temp = -1;
			for(int j=0; j<arreglo.length;j++){
				if(arreglo[i]<arreglo[j]){
					temp = arreglo[i];
					arreglo[i] = arreglo[j];
					arreglo[j] = temp;
				}
			}
		}
		this.f1=arreglo[0];
		this.f2=arreglo[1];
		this.f3=arreglo[2];
		this.f4=arreglo[3];
		this.f5=arreglo[4];
		this.f6=arreglo[5];
	}
}

//MelateVo vo = new MelateVo();
//vo.setF1(6);
//vo.setF2(8);
//vo.setF3(7);
//vo.setF4(19);
//vo.setF5(33);
//vo.setF6(36);
//
//System.out.println(vo.toList().size());
//System.out.println(vo.toList());
//System.out.println("no Duplicados? " + vo.noDuplicados());
//vo.reOrder();
//System.out.println(vo.toList());
