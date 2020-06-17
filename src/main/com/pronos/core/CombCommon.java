package com.pronos.core;

import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;

import com.pronos.vo.MelateVo;

public class CombCommon {

	private static final int melateElementos = 6; //Cantidad de numeros a elegir
	static Logger log4j = Logger.getLogger( CombCommon.class );
	
	/**
	 * Genera una combinacion aleatoria
	 * @param nElementos # de elementos de la combinacion
	 * @param numMax Rango de numeros a elegir ejemplo: 1-39
	 * @return
	 * (depende de las funciones unicInArray, y ordenaArrayInt)
	 */
	public static String genCombinacion(int nElementos, int numMax){
		String combinacion = "";
		
		int arrComb[] = new int[nElementos];//Se crea el arreglo con el tama�o de combinacion (6)
		int i=0; //posicion en el arreglo
		while(i<arrComb.length ){
			//genera un numero aleatorio
			int numeroAleatorio = (int) (Math.random()*numMax+1);
			
			if(unicInArray(arrComb, numeroAleatorio, i)){
				//si es unico en el arreglo, se guarda y se incrementa posicion
				arrComb[i] = numeroAleatorio;
				i++;
			}
		}	
		//una vez generados los nElementos, se ordenan
		arrComb = ordenaArrayInt(arrComb);		
		//Ya ordenados se concatenan separados por coma
		for(int j=0; j<arrComb.length; j++){
			if(j<arrComb.length-1){
				combinacion+=arrComb[j]+",";
			}
			else{
				combinacion+=arrComb[j];
			}			
		}		
		return combinacion;
	}
	
	
	/**
	 * Valida si el entero ya existe en el arreglo de enteros
	 * @param arrComb
	 * @param value
	 * @param index
	 * @return
	 */
	private static boolean unicInArray(int[] arrComb, int value, int index){
		for(int i=0; i<=index; i++){
			if(arrComb[i] == value){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Ordena un arreglo Ascendentemente
	 * (por metodo de burbuja)
	 * @param arrComb
	 * @return
	 */
	public static int[] ordenaArrayInt( int[] arreglo){
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
		return arreglo;
	}
	
	
	
	public static MelateVo genMelateVo(int numMax){
		MelateVo vo = new MelateVo();
		int arrComb[] = new int[melateElementos];//Se crea el arreglo con el tamano de combinacion (6)
		int i=0; //posicion en el arreglo
		while(i<arrComb.length ){
			//genera un numero aleatorio
			int numeroAleatorio = (int) (Math.random()*numMax+1);
			
			if(unicInArray(arrComb, numeroAleatorio, i)){
				//si es unico en el arreglo, se guarda y se incrementa posicion
				arrComb[i] = numeroAleatorio;
				i++;
			}
		}	
		//una vez generados los nElementos, se ordenan
		arrComb = ordenaArrayInt(arrComb);
		
		vo.setF1(arrComb[0]);
		vo.setF2(arrComb[1]);
		vo.setF3(arrComb[2]);
		vo.setF4(arrComb[3]);
		vo.setF5(arrComb[4]);
		vo.setF6(arrComb[5]);
		
		vo.setNumConcurso( new Integer(-1));
		
		return vo;
	}
	
	/**
	 * Obtiene similitud (coincidencia/igualdad de numeros) 
	 * entre dos listas de elementos Númericos
	 * @param listComb1
	 * @param listComb2
	 * @return
	 */
	public static int getSimilitud(List<Integer> listComb1, List<Integer> listComb2){
		int similitud = 0;
		Integer elemUno, elemDos;
		if(listComb1.size() == listComb2.size()){
			for(int i=0;i<listComb1.size();i++){
				elemUno = listComb1.get(i);
				for(int j=0;j<listComb2.size();j++){
					elemDos = listComb2.get(j);
					if(elemUno.equals(elemDos)){
						similitud++;
					}
				}
			}
		}
		else{
			String error ="[X] las longitudes (length) de combinaciones son diferentes " +listComb1.size() + " / " + listComb2.size();
//			log4j.error(error);
			System.err.println(error);
			similitud = -1;
		}
		
		return similitud;
	}
	
	
	/**
	 * Compara una combinacion contra una lista de combinaciones
	 * si este es igual o supera el factor de similitud maxima
	 * regresa la bandera en True
	 * @param combVo
	 * @param lsComparar
	 * @param factMaximo
	 * @return
	 */
	public static boolean coincideCombinacion(MelateVo combVo, List<MelateVo> lsComparar, int factMaximo){
		combVo.reOrder();
		boolean res = false;	//por default no existe similar en el listado
		MelateVo lsVo;
		Iterator<MelateVo> itHistorico = lsComparar.iterator();
		int similitud=0, indice =1; 
		while(itHistorico.hasNext()){
			lsVo = itHistorico.next();
			similitud=CombCommon.getSimilitud(combVo.toList(), lsVo.toList());
			if(similitud>=factMaximo){
				res=true;
//				log4j.debug(combVo.toText() + " / " + lsVo.toText() );
//				log4j.debug("Tiene similitud de " + similitud +" con combinación " + indice + " [linea:"+(indice+1)+"]");
			}
			indice++;
		}
		return res; 
	}
	
	
	/**
	 * Muestra en pantalla las posibles combinaciones entre 1 y nMax
	 * En un arreglo de CINCO elementos (f's)
	 * @param nMax
	 */
	protected static void printAll(long nMax){
		for ( int f1 = 0; f1 < nMax; f1++ ) {
	        for ( int f2 = f1 + 1; f2 < nMax; f2++ ) {
	            for ( int f3 = f2 + 1; f3 < nMax; f3++ ) {
	                for (int f4 = f3 + 1; f4 < nMax; f4++) {
	                    for (int f5 = f4 + 1; f5 < nMax; f5++) {
	                        System.out.println((f1+1)+","+(f2+1)+","+(f3+1)+","+(f4+1)+","+(f5+1));
	                    }
	                }
	            }
	        }
	    }
	}
	
	/* ****************************************************************** */
	/* *******************  M A I N Method ****************************** */
	/* ****************************************************************** */
	
	public static void main(String[] args) {
		printAll(18);
	}
}
