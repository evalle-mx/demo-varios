package com.repaso;

import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Muestra ejemplos sobre Ordenamientos
 * main utiliza consola de entrada
 * @author dothr
 *
 */
public class Ordenamientos {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//una vez ingresado, se manda al metodo de ordenamiento
	
		int arreglo[] = inputArray();
//		ordenamientoInsercion(arreglo);
		ordenamientoBurbuja(arreglo);

	}
	
	/**
	 * Ordena por el metodo de la burbuja
	 * @param arreglo
	 */
	public static void ordenamientoBurbuja(int arreglo[]){
		String outPut = "";
		for(int x=0;x<arreglo.length;x++){
			outPut+=arreglo[x] + " ";
		}
		System.out.println("Arreglo entrada: "+outPut + "\n");
		int aux = -1;
		
		for(int i=0; i<arreglo.length-1;i++){
			for(int j=0;j<arreglo.length-1;j++){
				// si actual > siguiente
				if(arreglo[j]>arreglo[j+1]){
					aux = arreglo[j];
					arreglo[j]=arreglo[j+1];
					arreglo[j+1]=aux;
				}
			}
		}
		outPut = "";
		for(int x=0;x<arreglo.length;x++){
			outPut+=arreglo[x] + " ";
		}
		System.out.println("Ordenado Asc: "+outPut);
	}
	
	/**
	 * Maneja un condicional contra el número a la izquierda
	 * numIz > numAct => Intercambio
	 * <====Ordenado== | == No Ordenado ==>
	 * @param arreglo
	 */
	public static void ordenamientoInsercion(int arreglo[]){
		String outPut = "";
		for(int x=0;x<arreglo.length;x++){
			outPut+=arreglo[x] + " ";
		}
		System.out.println("Arreglo entrada: "+outPut + "\n");
		int iPos = -1, aux=-1;
		
		for(int i=0;i<arreglo.length;i++){
			iPos = i;//posicion
			aux = arreglo[i];//valor a mover
			while(iPos>0 && (arreglo[iPos-1]>aux)){
				arreglo[iPos]=arreglo[iPos-1];
				iPos--;
			}
			arreglo[iPos]=aux;
		}
		//una vez arreglado (ascendentemente)
		outPut = "";
		for(int x=0;x<arreglo.length;x++){
			outPut+=arreglo[x] + " ";
		}
		System.out.println("Arreglo ordenado Asc: " + outPut);
		//descendente, solo se invierte el bucle
		outPut = "";
		for(int y=arreglo.length-1;y>=0;y--){
			outPut+=arreglo[y] + " ";
		}
		System.out.println("Arreglo ordenado Desc: " + outPut);
	}
	
	
	public static int[] inputArray(){
		//int arreglo[] = {5,3,4,1,2};
		//int arreglo[], nElementos; //En el main si se pueden declarar sin inicializar
		Scanner entrada = new Scanner(System.in);
		
		int nElementos = Integer.parseInt(JOptionPane.showInputDialog("Digite candidad de elementos"));
		int arreglo[] = new int[nElementos];
		
		for(int i=0; i<nElementos;i++){
			System.out.println( " Digite numero "+(i+1) + ": ");
			arreglo[i]=entrada.nextInt();			
		}
		entrada.close();
		return arreglo;
	}

}
