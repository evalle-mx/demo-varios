package com;

import java.util.Random;

public class Test {

	
	public static void main(String[] args) {
//		restador();
//		contador();
		muestra();
	}
	
	public static void muestra(){
		String oldName = "prueba.txt";
		
		oldName = oldName.substring(0, oldName.lastIndexOf("."));
		System.out.println(oldName);
	}
	
	public static void contador(){
		int max = 86;
		int base = 10;
		for(int x=0; x<max; x++){
			System.out.println(base+x);
		}
	}
	
	public static void restador(){
		double ini = 55.62;
		double rest = 0.43;
		do{
			ini = ini-rest;
			System.out.println(ini);
		}while(ini>0.0);
	}	 
	  
}
