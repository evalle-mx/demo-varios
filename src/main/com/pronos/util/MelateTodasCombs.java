package com.pronos.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.pronos.core.CombCommon;
import com.pronos.vo.MelateVo;

public class MelateTodasCombs {
	
	static Logger log4j = Logger.getLogger( MelateTodasCombs.class);
	private static final int numMax = 39;
	private static final int maxConsecutivos = 3;
	private static ArrayList<MelateVo> lsHistorico = null;
//	private static ArrayList<MelateVo> lsAll = null;

	private static final String HISTORY_PATH ="/home/dothr/workspace/MyProjects/files/melate/Melate-Retro.csv";
	private static final String ALL_COMBS_PATH ="/home/dothr/workspace/MyProjects/files/melate/Melate-Retro-Todas-Depurado.txt";//TODO cambiar a ruta en proyecto
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		generaTodasDepuradas();
//		testgeneraTodasRestantes();
	}
	
	/**
	 * Genera un archivo con todas las combinaciones posibles entre los numeros
	 */
	protected static void generaTodasPosibles(){
		log4j.debug("<generaTodasPosibles>");
		try {
			PronosUtily.writeInFile(ALL_COMBS_PATH, "/* Todas las combinaciones posibles (1-"+numMax+") */ \n", true);
			
			int nMax = numMax+1;
			for ( int f1 = 1; f1 < nMax; f1++ ) {
		        for ( int f2 = f1 + 1; f2 < nMax; f2++ ) {
		            for ( int f3 = f2 + 1; f3 < nMax; f3++ ) {
		                for (int f4 = f3 + 1; f4 < nMax; f4++) {
		                    for (int f5 = f4 + 1; f5 < nMax; f5++) {
		                    	for (int f6 = f5 + 1; f6 < nMax; f6++) {
		                    		//System.out.println((f1+1)+","+(f2+1)+","+(f3+1)+","+(f4+1)+","+(f5+1));
			                        PronosUtily.writeInFile(ALL_COMBS_PATH, (f1+1)+","+(f2+1)+","+(f3+1)+","+(f4+1)+","+(f5+1)+","+(f6+1)+"\n", true);
		                    	}
		                    }
		                }
		            }
		        }
		    }
		} catch (Exception e) {
			log4j.debug("<generaTodasPosibles>Excepcion al iterar: ", e);
		}
		log4j.debug("<generaTodasPosibles> FIN DE PROCESO");
	}

	/**
	 * Genera un archivo con todas las combinaciones 
	 *  posibles entre los numeros, eliminando aquellas que:
	 *  <ul>
	 *  <li>Ya han sido ganadoras del sorteo</li>
	 *  <li>Son consecutivos los seis numeros (Muy improbable)</li>
	 *  <li>Tienen un numero de consecutivos mayor a maxConsecutivos (3) (poco Probable)</li>
	 *  </ul>
	 */
	protected static void generaTodasDepuradas(){
		log4j.debug("<generaTodasPosibles>");
		try {
			lsHistorico = PronosUtily.getLsHistoricoMelate(HISTORY_PATH);
			PronosUtily.writeInFile(ALL_COMBS_PATH, "/* Todas las combinaciones Depuradas (1-"+numMax+") */ \n", true);
			
			int nMax = numMax+1;
			MelateVo vo;
			for ( int f1 = 1; f1 < nMax; f1++ ) {
		        for ( int f2 = f1 + 1; f2 < nMax; f2++ ) {
		            for ( int f3 = f2 + 1; f3 < nMax; f3++ ) {
		                for (int f4 = f3 + 1; f4 < nMax; f4++) {
		                    for (int f5 = f4 + 1; f5 < nMax; f5++) {
		                    	 for (int f6 = f5 + 1; f6 < nMax; f6++) {
		                    		 if(PronosUtily.consecPerfecto(f1, f2, f3, f4, f5, f6)){
		                    			System.out.println("consecutivo perfecto se omite: " +
		                    					(f1)+","+(f2)+","+(f3)+","+(f4)+","+(f5)+","+(f6));
		                    		 }else if(PronosUtily.consecParcial(f1, f2, f3, f4, f5, f6)>maxConsecutivos){
		                    			 System.out.println("consecutivo Parcial mayor a "+maxConsecutivos+" se omite: " +
			                    					(f1)+","+(f2)+","+(f3)+","+(f4)+","+(f5)+","+(f6));
		                    		 }else{
		                    			 vo = new MelateVo();
					                    	//System.out.println((f1+1)+","+(f2+1)+","+(f3+1)+","+(f4+1)+","+(f5+1));
					                    	vo.setF1(f1);
					                		vo.setF2(f2);
					                		vo.setF3(f3);
					                		vo.setF4(f4);
					                		vo.setF5(f5);
					                		vo.setF6(f6);
					                		if(CombCommon.coincideCombinacion(vo, lsHistorico, 6)){
					                			System.out.println( "Se omite pues ya ha sido ganador: "
					                					+(f1)+","+(f2)+","+(f3)+","+(f4)+","+(f5)+","+(f6));
					                			
					                		}else{					                			
					                			PronosUtily.writeInFile(ALL_COMBS_PATH, (f1)+","+(f2)+","+(f3)+","+(f4)+","+(f5)+","+(f6)+"\n", true);
					                		}
		                    		 }
		                    	 }
		                    }
		                }
		            }
		        }
		    }
		} catch (Exception e) {
			log4j.debug("<generaTodasPosibles>Excepcion al iterar: ", e);
		}
		log4j.debug("<generaTodasPosibles> FIN DE PROCESO");
	}
	
	/**
	 * Genera un archivo con todas las combinaciones posibles entre los numeros
	 */
	protected static void testgeneraTodasRestantes(){
		log4j.debug("<generaTodasPosibles>");
		try {
			lsHistorico = PronosUtily.getLsHistoricoMelate(HISTORY_PATH);
			PronosUtily.writeInFile(ALL_COMBS_PATH, "/* Todas las combinaciones posibles (1-"+numMax+") */ \n", true);
			
			int nMax = numMax+1;
			for ( int f1 = 1; f1 < nMax; f1++ ) {
		        for ( int f2 = f1 + 1; f2 < nMax; f2++ ) {
		            for ( int f3 = f2 + 1; f3 < nMax; f3++ ) {
		                
		            	if(testConsecPerfecto(f1,f2,f3)){
		            		System.out.println("consecutivo perfecto se omite");
		            		System.out.println((f1)+","+(f2)+","+(f3));
		            	}
		            	else{
//		            		System.out.println((f1+1)+","+(f2+1)+","+(f3+1));
                			PronosUtily.writeInFile(ALL_COMBS_PATH, (f1)+","+(f2)+","+(f3)+"\n", true);
		            	}
		            }
		        }
		    }
		} catch (Exception e) {
			log4j.debug("<generaTodasPosibles>Excepcion al iterar: ", e);
		}
		log4j.debug("<generaTodasPosibles> FIN DE PROCESO");
	}
	
	private static boolean testConsecPerfecto(int f1, int f2, int f3){
		//if(f3==f2+1 && f2==f1+1){
		if(f3==f2+1){
			if(f2==f1+1){
				return true;
			}
		}
		return false;
	}
	
}
