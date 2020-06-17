package com.pronos.test;

import org.apache.log4j.Logger;

public class Prueba {
	
	static Logger log4j = Logger.getLogger( Prueba.class );	
	
	
	public static void main(String[] args) {
//		test();
		testArrayInteger();
	}
	
	
	public static void test(){
		log4j.debug("FUnciona");
		System.out.println("test");
	}
	
	public static void testArrayInteger(){
		Integer[] num = //{0,0,0,0,0,0,0,0}; 
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//new Integer[40];
		Integer[] caidos = {2,6,8,1,2,5,5,5,5,3,2,7};	//[2]=3, [5]=4veces, los demas uno
		
		for(int y=0; y<caidos.length;y++){
			num[(caidos[y]) ]++;
		}
		
		System.out.println("num.length " + num.length);
		for(int x=0;x<num.length;x++){
			System.out.println( ("num["+(x)+"]=")+ num[x]);
		}
	}

}
