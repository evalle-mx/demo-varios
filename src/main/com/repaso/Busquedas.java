package com.repaso;

import javax.swing.JOptionPane;

/**
 * Muestra ejemplos de Busquedas
 * (algunos usando JavaSwing )
 * @author dothr
 *
 */
public class Busquedas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int arreglo[] = {4,1,5,2,3};
		
		busquedaSecuencial(arreglo);

	}
	
	/**
	 * Busca un numero dentro del arreglo
	 * <ul>
	 * <li>SI se encuentra: muestra posición</li>
	 * <li>No se encuentra: Manda aviso</li>
	 * </ul>
	 */
	public static void busquedaSecuencial(int arreglo[]){
		
		int dato;
		boolean band= false;
		
		dato = Integer.parseInt( JOptionPane.showInputDialog("Digite numero a buscar") );
		
		//Busqueda secuencial
		int i=0;
		while(i<arreglo.length && !band){
			if(dato == arreglo[i]){
				band=true;
				System.out.println("El numero se encontro en "+i);
			}
			i++;
		}
		if(!band){
			System.out.println("No se encontró el elemento en el arreglo: "+arreglo);
			JOptionPane.showMessageDialog(null, "No se encontro el Número");
		}
		else{
			JOptionPane.showMessageDialog(null, "Se encontro el Número (en arreglo["+(i-1)+"])");
		}
		
	}

}
