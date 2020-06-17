package com.pronos.sorteo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;


import org.apache.log4j.Logger;

import com.pronos.core.CombCommon;
import com.pronos.util.PronosUtily;
import com.pronos.vo.MelateVo;

public class CalculaPremio {
	
	static Logger log4j = Logger.getLogger( CalculaPremio.class);
	
	private static final String TXT_OUT_PATH ="/home/dothr/workspace/MyProjects/files/melate/";
	
	private static final String CVS_PATH ="/home/dothr/workspace/MyProjects/files/melate/Melate-Retro.csv";
	
	private static final String ARCH_COMB_NUEVO =
			"jugados-MR-g01.txt";
//			"MR-g02(AunNOJugado).txt";
//			"MR-g01.txt";
			//"Melate-Historico.txt";	//-> $583.56
//			"Melate-Retro-Nuevos141944_41.txt"; //-> $64.50
//			"Melate-Retro-Nuevos141939_51.txt"; //-> $205.78
//			"Melate-Retro-Nuevos141841_59.txt"; //-> $107.50
//			"Melate-Retro-Nuevos-F.txt"; 	//-> $205.78
//			"Melate-Retro-Nuevos-4.txt";	//-> $205.78
//			"Melate-Retro-Nuevos-3.txt";	//-> $150.50
//			"Melate-Retro-Nuevos-2.txt";	//-> $129.00
//			"Melate-Retro-Nuevos-1.txt"; //-> $64.50
//			"Melate-Retro-NRep.txt"; //-> $43.00
	private static ArrayList<MelateVo> lsNuevos = null;
	
	private static BigDecimal prem1er = new BigDecimal("5500000.00"); //6 Nat
	private static BigDecimal prem2do = new BigDecimal("42725.83");	//5 Nat+Ad
	private static BigDecimal prem3er = new BigDecimal("704.35"); //5 Nat
	private static BigDecimal prem4to = new BigDecimal("119.78");  //4 Nat
	//FIjos
	private static BigDecimal prem5to = new BigDecimal("21.50");	//3 Nat
	private static BigDecimal prem6to = new BigDecimal("16.13");	//2 Nat+Ad
	private static BigDecimal prem7to = new BigDecimal("10.00"); 	//1Nat+Ad
	
	
	public static void main(String[] args) {
		lsNuevos = setLsNuevos(TXT_OUT_PATH+ARCH_COMB_NUEVO);

		//*
		BigDecimal total = calcPremMelate("1,3,10,11,29,39", lsNuevos);
		System.out.println("Total: " + total ); //*/
		/* 
		ArrayList<String> lsCombs = new ArrayList<String>();
		lsCombs.add("16,18,22,25,32,37");
		lsCombs.add("3,15,26,27,35,37");
		lsCombs.add("1,3,10,11,29,39,26");
		lsCombs.add("21,22,26,30,35,36,16");
		lsCombs.add("2,10,24,30,38,39,7");
		lsCombs.add("7,22,24,25,28,29,2");
		lsCombs.add("2,6,15,20,22,30,23");
		lsCombs.add("8,12,19,30,31,39,25");
		lsCombs.add("7,13,17,23,28,31,30");
		lsCombs.add("8,15,23,24,25,32,34");
		lsCombs.add("1,7,14,17,33,35,34");
		lsCombs.add("4,6,9,16,35,36,38");
		lsCombs.add("1,12,13,26,38,39,20");

		
		Iterator<String> itCombs = lsCombs.iterator();
		StringBuilder sb = new StringBuilder();
		int sorteo = 688;
		String combinacion;
		while(itCombs.hasNext()){
			combinacion = itCombs.next();
			sb.append(sorteo).append(";\"").append(combinacion).append("\";$")
			.append(calcPremMelate(combinacion, lsNuevos)).append("\n");
			sorteo--;
		}
		System.out.println("\n"+sb);// */
		
//		premiosHistRep();
	}
	
	
	/**
	 * Evalua el archivo de Combinaciones generado y precalcula los premios obtenidos
	 * @param ganadorVo
	 */
	public static BigDecimal calcPremMelate(String combGanadora, ArrayList<MelateVo> lsCombs){
		BigDecimal total = new BigDecimal("0");
		MelateVo ganadorVo = setData(combGanadora);
//		ganadorVo.setfAd(adicional);
		setLsNuevos(TXT_OUT_PATH+ARCH_COMB_NUEVO);
		Iterator<MelateVo> itNuevos = lsCombs.iterator();
		int coincidencia = 0, indice =2;
		MelateVo voTmp;
		while(itNuevos.hasNext()){
			voTmp = itNuevos.next();
			coincidencia = CombCommon.getSimilitud(ganadorVo.toList(), voTmp.toList());
			if(coincidencia==6){	//1er lugar
				log4j.debug("["+indice+"]>"+voTmp.toText()+" ("+coincidencia+"): Ganador 1er Lugar");
				total= total.add(prem1er);
			}
			else if(coincidencia==5){	//2o / 3o
				if( voTmp.toList().contains(ganadorVo.getfAd()) ){
					log4j.debug("["+indice+"]>"+voTmp.toText()+" ("+coincidencia+"+Ad): 2do. Lugar $"+ prem2do);
					total=total.add(prem2do);
				}else{
					log4j.debug("["+indice+"]>"+voTmp.toText()+" ("+coincidencia+"): 3er. Lugar $"+ prem3er);
					total=total.add(prem3er);
				}
			}
			else if(coincidencia==4){	//4to
				log4j.debug("["+indice+"]>"+voTmp.toText()+" ("+coincidencia+"): 4to. Lugar $" + prem4to);
				total=total.add(prem4to);
			}
			else if(coincidencia==3){	//5to lugar
				log4j.debug("["+indice+"]>"+voTmp.toText()+" ("+coincidencia+"): 5to Lugar $" + prem5to);
				total=total.add(prem5to);
			}
			else if(coincidencia==2 && voTmp.toList().contains(ganadorVo.getfAd())){	//6to lugar
				log4j.debug("["+indice+"]>"+voTmp.toText()+" ("+coincidencia+"+Ad): 6to Lugar $" + prem6to);
				total=total.add(prem6to);
			}
			else if(coincidencia==1 && voTmp.toList().contains(ganadorVo.getfAd())){	//7to lugar
				log4j.debug("["+indice+"]>"+voTmp.toText()+" ("+coincidencia+"+Ad): 7to Lugar $" + prem7to);
				total=total.add(prem7to);
			}
			indice++;
		}
		log4j.debug("\nArchivo: " + ARCH_COMB_NUEVO +"\n Procesadas "+lsCombs.size()+" combinaciones \n Total obtenido en iteracion: $" + total);
		return total;
	}
	
		
	/**
	 * Estadistica de premiacion a partir de cada combinación de la lista de Nuevos generada
	 */
	public static void estadPremioSorteos(){
		StringBuilder sbRp = new StringBuilder("/* **********************************  estadSorteos()  ********************************** */ \n\n");
		try{
			ArrayList<MelateVo> lsHistorico = PronosUtily.getLsHistoricoMelate(CVS_PATH);
			Iterator<MelateVo> itHistorico = lsHistorico.iterator();
			
			MelateVo histVo;
			BigDecimal total, lmGan = new BigDecimal("1000"), ganMax = new BigDecimal("0");
			String hayGanancia;
			while(itHistorico.hasNext()){
				histVo = itHistorico.next();
				hayGanancia = "";
				total = calcPremMelate(histVo.toText(), lsNuevos);
				if(total.compareTo(lmGan)!=-1){
					hayGanancia = ">>";
				}
				if(total.compareTo(ganMax)==1){
					ganMax=total;
				}
				sbRp.append(hayGanancia).append("Sorteo: ").append(addSpace(histVo.getNumConcurso())).append(" Premio: $").append(total).append("\n");
				/*Csv version*/
//				sbRp.append(addSpace(histVo.getNumConcurso())).append(";").append(total).append("\n");
			}
			sbRp.append("\n Ganancia Maxima: $").append(ganMax);
			PronosUtily.writeInFile(TXT_OUT_PATH+"Rep.estadSorteos.txt", sbRp.toString(), false);
		}catch(Exception e){
			log4j.fatal("<estadSorteos> fatal:", e);
		}
		
	}
	
	private static String addSpace(Integer numConc){
		if(numConc<100 && numConc>9){
			return " "+numConc;
		}else if(numConc<10){
			return "  "+numConc;
		}else{
			return ""+numConc;
		}
	}
	
	/**
	 * Estadistica de similitud entre las combinaciones de un mismo archivo
	 */
	public static void comparaSiMismo(ArrayList<MelateVo> lsCombs){
		setLsNuevos(TXT_OUT_PATH+ARCH_COMB_NUEVO);
		StringBuilder sbRep = new StringBuilder("*********** comparaSiMismo: ************** \n");
		try{
			sbRep.append("********* Archivo Combinaciones: ").append(TXT_OUT_PATH).append(ARCH_COMB_NUEVO).append("************ \n\n");
			Iterator<MelateVo> itNuevosA = lsCombs.iterator();
			Iterator<MelateVo> itNuevosB;
			MelateVo voTmpA, voTmpB;
			int coincid = 0, indxA=2, indxB=2;
			while(itNuevosA.hasNext()){
				voTmpA = itNuevosA.next();
				log4j.debug("Evaluando "+indxA + "["+voTmpA.toText()+"]");
				sbRep.append(indxA).append(".[").append(voTmpA.toText()).append("]:\n");				
				itNuevosB = lsCombs.iterator();
				indxB=2;
				while(itNuevosB.hasNext()){					
					voTmpB = itNuevosB.next();
					log4j.debug(indxA + " vs: "+indxB);
					coincid = CombCommon.getSimilitud(voTmpA.toList(), voTmpB.toList());
					log4j.debug("coincidencia con "+indxB+" {"+voTmpB.toText()+"} = "+coincid);					
					if(coincid>3 && coincid!=6){	//Solo si tiene mas de 50% de coincidencia y no es el mismo
						sbRep.append("\t-").append(indxB).append("-(").append(coincid).append(") ").append(voTmpB.toText()).append("\n");
					}
					indxB++;
				}
				indxA++;
			}
			PronosUtily.writeInFile(TXT_OUT_PATH+"Rep.comparaSiMismo.txt", sbRep.toString(), false);
			log4j.debug(sbRep);
		}catch (Exception e){
			log4j.fatal("Error: ", e);
		}	
	}
	
	/**
	 * Carga la lista de Combinaciones en la lista Global
	 */
	protected static ArrayList<MelateVo> setLsNuevos(String stPath){
		ArrayList<MelateVo> lsVo = new ArrayList<MelateVo>();
		Iterator<String> itLineas= PronosUtily.getListLines(stPath).iterator();
		String linea;
		while(itLineas.hasNext()){
			linea = itLineas.next();
			if(!linea.startsWith("/*")){
				MelateVo vo = setData(linea);
				if(vo!=null){
					lsVo.add(vo);
				}
			}
		}	
		return lsVo;
	}
	
	private static MelateVo setData(String linea){
		MelateVo vo =null;
		String[] datos = linea.split(",");
		if(PronosUtily.isNumber(datos[0]) && (datos.length==7 || datos.length==6)){
			vo = new MelateVo();
			vo.setF1(Integer.parseInt(datos[0]) );
			vo.setF2(Integer.parseInt(datos[1]) );
			vo.setF3(Integer.parseInt(datos[2]) );
			vo.setF4(Integer.parseInt(datos[3]) );
			vo.setF5(Integer.parseInt(datos[4]) );
			vo.setF6(Integer.parseInt(datos[5]) );
			if(datos.length==7){
				log4j.debug(" Adicional");
				vo.setfAd(Integer.parseInt(datos[6]));
			}
		}
		
		return vo;
	}

}
