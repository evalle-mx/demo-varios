package com.pronos.sorteo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.pronos.core.CombCommon;
import com.pronos.util.PronosUtily;
import com.pronos.vo.MelateVo;

/**
 * Esta clase está dedicada al procesamiento de la lista de Historicos
 * @author netto
 *
 */
public class HistoricoProcess extends MelateRetro {
	static Logger log4j = Logger.getLogger( HistoricoProcess.class);
	private static final String ROOT = "/home/dothr/workspace/MyProjects/files/melate/";
	private static final String CVS_PATH =ROOT+"Melate-Retro.csv";
	
	private static final String HISTORICO_REPL_PATH =ROOT+"Melate-Retro-Repl.txt";
	private static final String HISTORICO_ORI =ROOT+"Melate-Historico.txt";
	
	private static ArrayList<MelateVo> lsAll = null;
	
	private static BigDecimal prem1er = new BigDecimal("5500000.00");
	private static BigDecimal prem2do = new BigDecimal("42725.83");
	private static BigDecimal prem3er = new BigDecimal("704.35");
	private static BigDecimal prem4to = new BigDecimal("119.78");
	//FIjos
	private static BigDecimal prem5to = new BigDecimal("21.50");
	private static BigDecimal prem6to = new BigDecimal("16.13");
	private static BigDecimal prem7to = new BigDecimal("10.00");
	
	public static void main(String[] args) {
//		createHistoricoReplace();
		premiosHistRep();
	}
	
	/* **************  METODO PARA GENERAR NUEVO LISTADO CON LOS HISTORICOS REEMPLAZANDO ADICIONAL ************** */
	protected static void createHistoricoReplace(){
		log4j.debug("<createHistoricoReplace>");
		lsAll = new ArrayList<MelateVo>();
		
		ArrayList<MelateVo> lsHistorico = PronosUtily.getLsHistoricoMelate(CVS_PATH);
		StringBuilder sbHistRpl = new StringBuilder();
		try{
			Iterator<MelateVo> itHist = lsHistorico.iterator();//lsTmpHistorico.iterator();
			lsAll.addAll(lsHistorico);
			MelateVo tempVo;
			int rdmFx = -1;
			boolean coincidente = true;
			while(itHist.hasNext()){
				MelateVo vo;
				tempVo = itHist.next();
				do{
					rdmFx = PronosUtily.getAleatorio(6);
					vo = new MelateVo(tempVo.toList());
					setReplace(vo, tempVo.getfAd(), rdmFx);
					coincidente= CombCommon.coincideCombinacion(vo, lsAll, 6);
				}while(coincidente);
				sbHistRpl.append(vo.toText()).append("\n");
				lsAll.add(vo);
			}			
			PronosUtily.writeInFile(HISTORICO_REPL_PATH, sbHistRpl.toString(), true);
			log4j.debug("FIN DE PROCESO Historico Replace: " + HISTORICO_REPL_PATH);
		}catch (Exception e){
			log4j.fatal("<createHistoricoReplace> Excepcion ", e);
		}
	}
	
	
	
	
	/**
	 * Metodo unicamente hecho para verificar el resultado del HistoricoReplaced
	 */
	private static void premiosHistRep(){
		StringBuilder sbRp = new StringBuilder("/* **********************************  premiosHistRep()  ********************************** */ \n");
		try{
			ArrayList<MelateVo> lsHistorico = getLsVo(HISTORICO_ORI);
			Iterator<MelateVo> itHistorico = lsHistorico.iterator();
			ArrayList<MelateVo> lsNuevos = getLsVo(HISTORICO_REPL_PATH);
					
			MelateVo histVo;
			BigDecimal total = new BigDecimal(0.0);
			int indiceHis = 1, indiceNw=1;
			int coincidencia = -1;
			while(itHistorico.hasNext()){
				histVo = itHistorico.next();
				MelateVo nwVo;
				indiceNw=1;
				Iterator<MelateVo> itNuevos = lsNuevos.iterator();
				total = new BigDecimal(0);
				sbRp.append("H").append(indiceHis).append(".-").append(histVo.toText()).append(":");
				while(itNuevos.hasNext()){
					nwVo = itNuevos.next();
									
					if(indiceHis!=indiceNw){
						coincidencia = CombCommon.getSimilitud(histVo.toList(), nwVo.toList());
						if(coincidencia==6){	//1er lugar
							log4j.debug("["+indiceNw+"]>"+nwVo.toText()+" ("+coincidencia+"): Ganador 1er Lugar");
							total= total.add(prem1er);
						}
						else if(coincidencia==5){	//2o / 3o
							if( nwVo.toList().contains(histVo.getfAd()) ){
								log4j.debug("["+indiceNw+"]>"+nwVo.toText()+" ("+coincidencia+"+Ad): 2do. Lugar $"+ prem2do);
								total=total.add(prem2do);
							}else{
								log4j.debug("["+indiceNw+"]>"+nwVo.toText()+" ("+coincidencia+"): 3er. Lugar $"+ prem3er);
								total=total.add(prem3er);
							}
						}
						else if(coincidencia==4){	//4to
							log4j.debug("["+indiceNw+"]>"+nwVo.toText()+" ("+coincidencia+"): 4to. Lugar $" + prem4to);
							total=total.add(prem4to);
						}
						else if(coincidencia==3){	//5to lugar
							log4j.debug("["+indiceNw+"]>"+nwVo.toText()+" ("+coincidencia+"): 5to Lugar $" + prem5to);
							total=total.add(prem5to);
						}
						else if(coincidencia==2 && nwVo.toList().contains(histVo.getfAd())){	//6to lugar
							log4j.debug("["+indiceNw+"]>"+nwVo.toText()+" ("+coincidencia+"+Ad): 6to Lugar $" + prem6to);
							total=total.add(prem6to);
						}
						else if(coincidencia==1 && nwVo.toList().contains(histVo.getfAd())){	//7to lugar
							log4j.debug("["+indiceNw+"]>"+nwVo.toText()+" ("+coincidencia+"+Ad): 7to Lugar $" + prem7to);
							total=total.add(prem7to);
						}
						if(coincidencia>4){ //Filtrar las más coincidentes
							sbRp.append("\n H").append(indiceHis).append("/N").append(indiceNw).append(" (").append(nwVo.toText())
							.append(") ==> coincidencia: ").append(coincidencia).append(itNuevos.hasNext()?"":"\n");
						}	
						indiceNw++;						
					}
					else {log4j.debug("Comb Origen, no se compara ");indiceNw++;}
				}
				sbRp.append(" TOTAL; ")
					.append(total)	//.append("\n")
					.append("\n");
				indiceHis++;
			}
			PronosUtily.writeInFile(ROOT+"Rep.premiosHistRep.txt", sbRp.toString(), false);
		}catch(Exception e){
			log4j.fatal("<estadSorteos> fatal:", e);
		}
	}
	
	
	/**
	 * Genera lista de Vo's a partir de un archivo txt
	 * @param Path (Archivo txt de combinaciones)
	 * @return
	 */
	private static ArrayList<MelateVo> getLsVo(String Path){
		ArrayList<MelateVo> lsVo = new ArrayList<MelateVo>();
		Iterator<String> itLineas= PronosUtily.getListLines(Path).iterator();
		String linea;
		while(itLineas.hasNext()){
			linea = itLineas.next();
			if(!linea.startsWith("/*")){
				MelateVo vo = new MelateVo(linea);//setData(linea);
				if(vo!=null){
					lsVo.add(vo);
				}
			}
		}	
		return lsVo;
	}
}
