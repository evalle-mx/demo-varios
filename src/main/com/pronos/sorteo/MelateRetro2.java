package com.pronos.sorteo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.pronos.core.CombCommon;
import com.pronos.util.PronosUtily;
import com.pronos.vo.MelateVo;

/**
 * Esta clase genera nuevas combinaciones a partir de una lista de NO GANADORES
 * y seleccionandolas de manera aleatoria
 * (Pasa por filtros de coincidencia para generar un archivo de Depurados y sus segmentos)
 * @author netto
 *
 */
public class MelateRetro2 {

	static Logger log4j = Logger.getLogger( MelateRetro2.class);
	private static final String ROOT_DIR = "/home/dothr/workspace/MyProjects/files/melate/";
	private static final String TXT_DEPURADO_PATH =ROOT_DIR+"Melate-Retro-Todas-Depurado.txt";
	private static final String TXT_OUT_PATH =ROOT_DIR+"MRetro-Nuevos.Dep";
	
	private static ArrayList<MelateVo> lsNuevos = null;
	private static List<String> lsAll = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*String DepFile = "segmentado/Depurado.1.txt";
		randomCombs(20, 2, DepFile);*/
//		generaUnicas();
		//9,17,21,26,35,37 | 
//		System.out.println(coincidenciaUltimos(new MelateVo("8,19,21,26,31,39")));
		
		comparaNuevos();
		
	}
	
	/**
	 * obtiene combinaciones aleatorias que tengan similitud menor a la solicitada
	 * @param nCombs
	 */
	protected static void randomCombs(int nCombs, int mxSimilitud, String depuradoFileN){
		lsAll = PronosUtily.getListLines(ROOT_DIR+depuradoFileN);
		System.out.println("Total de No Ganadores: " + lsAll.size() );
		boolean coincide = false;
		String comb, fileName="";
		MelateVo vo = null;
		lsNuevos = new ArrayList<MelateVo>();
		StringBuilder sbCombs = new StringBuilder("/* Generadas (Segmento:")
		.append(depuradoFileN).append(" ")
		.append(PronosUtily.todayFormated("YY/MM/dd HH:mm:ss"))
			.append("  */ \n");
		int x=0, iRemove = 0, randm=0;		
		try{
			do{ //System.out.println("X= " + x );
				randm = PronosUtily.getAleatorio( lsAll.size()-1 );
				comb = lsAll.get(randm);
				vo = new MelateVo(comb);
				//Se compara contra ultimos jugados
				coincide = coincidenciaUltimos(vo);
				
				
				if(!coincide){ //Si no coincide se compara contra la lista actual
					coincide = CombCommon.coincideCombinacion(vo, lsNuevos, mxSimilitud);					
				}
				lsAll.remove(randm);
				iRemove++;
				
				if(coincide){
					if(iRemove==100 || lsAll.size()<=2){
						System.out.println("Removiendo "+randm+ ", lsAll.size: " + lsAll.size() );
						iRemove=0;
					}
				}else{
					try{
						lsNuevos.add(vo);
						log4j.debug("["+randm+"] Vo: "+vo.toList()+ " se agrega, lsNuevos.size: " + lsNuevos.size() + ", lsAll.size: "+lsAll.size());
						sbCombs.append(vo.toText()).append("\n");
						fileName = PronosUtily.todayFormated("ddHHmm")+".txt";
						log4j.debug("Escribiendo: " + fileName );
						PronosUtily.writeInFile(TXT_OUT_PATH+fileName, sbCombs.toString(), false);
						
						x++;/* Si se encontro una nueva, se agrega al archivo y se aumenta X */
					}catch (Exception e){
						e.printStackTrace();
						log4j.debug(
								"rdm: "+randm +"\n"+
								"lsAll.size: "+lsAll.size() +"\n"
								);
						log4j.fatal("Error al procesar archivo de salida ", e);
					}
				}
			}while(x<nCombs && lsAll.size()>10);
			
			if(lsNuevos.size()<nCombs-1){
				log4j.debug("No se pudieron generar el numero de Combinaciones solicitado, redusca filtros \n"+
						"lsNuevos: " + lsNuevos.size()+", mxSimilitud: "+mxSimilitud + ", DepuradoBase: " + depuradoFileN );
			}
//			PronosUtily.writeInFile(TXT_OUT_PATH+PronosUtily.todayFormated("ddHHmm_ss")+".txt", sbCombs.toString(), false);
			log4j.debug("Fin de Proceso sin errores, ultimo archivo generado: \n"+TXT_OUT_PATH+fileName);
		}catch (Exception e){
			log4j.fatal("Error en el procedimiento principal ", e );
		}
		
		
	}
	
	/**
	 * Regresa true si coincide (Incrementalmente) con las ultimas combinaciones (9)
	 * [1,1,1,1,2,2,2,3,3,.....]
	 * @param vo
	 * @return
	 */
	private static boolean coincidenciaUltimos(MelateVo vo){
		int mxSim = 1, simObt=0;
		boolean coinc = false;
		List<MelateVo> lsUlts= getUltimas();
		MelateVo voTmp;
		for(int x=0; x<lsUlts.size();x++){
			voTmp = lsUlts.get(x);//itUltimos.next();
			simObt = CombCommon.getSimilitud(vo.toList(), voTmp.toList());
			if(simObt == mxSim){
				//System.out.println(voTmp.toText() +" [mxSim: "+mxSim+"]");
				coinc = true;
			}
			//System.out.println((simObt == mxSim?">>":"")+ "X: "+x+" x%3= " + (x%3) + " voTmp: " + voTmp.toText()+ " mxSim: " + mxSim+" simObt: "+ simObt);
			if(x!=0 && (x%3)==0 && mxSim<6){
				mxSim ++;
			}
			//System.out.println("mxSim: " + mxSim);
			simObt=0;
		}
		
		return coinc;
	}
	
	/**
	 * Genera un archivo donde se encuentran aquellas combinaciones que no tienen ningun elemento en común
	 * (Coincidencia maxima = 1)
	 */
	protected static void generaUnicas(){
		int sim = 0;
		String fileName = PronosUtily.todayFormated("ddHHmm")+".txt";
		lsAll = PronosUtily.getListLines(TXT_DEPURADO_PATH);
		Iterator<String> itA = lsAll.iterator();
		Iterator<String> itB;
		lsNuevos = new ArrayList<MelateVo>();
		
		String combA, combB;
		StringBuilder sbOut = new StringBuilder();
		String primerElem = "";
		MelateVo voA, voB;
		
		int similitud = 0, evaluada = 0;
		
		while(itA.hasNext()){
			combA = itA.next();		
			System.out.println("primerElem: "+ primerElem );
			if(!combA.startsWith("/*") &&	//Elemento comentario 
					!primerElem.equals(combA.substring(0, combA.indexOf(",")))){ //El primero ya determina igualdad con los siguientes 33 (39-6) 
				primerElem = combA.substring(0, combA.indexOf(","));
				voA = new MelateVo(combA);
				if(!CombCommon.coincideCombinacion(voA, lsNuevos, 1)){
					log4j.debug("voA: " + voA.toText() );
					itB = lsAll.iterator();
					while(itB.hasNext()){
						try{
							combB = itB.next();
//							System.out.println("voA: " + voA.toText() + " combB: " + combB);					
							if(!combB.startsWith("/*") && !combA.equals(combB)){//Si no es Comentario ni es la misma
								voB = new MelateVo(combB);
//								System.out.println("voA: " + voA.toText() + " voB: " + voB.toText());
								similitud = CombCommon.getSimilitud(voA.toList(), voB.toList());
//								System.out.println("Similitud: " + similitud );
								if(similitud==sim){
									if(!CombCommon.coincideCombinacion(voB, lsNuevos, 1)){
										log4j.debug(" se agrega: " +  voB.toText());
										lsNuevos.add(voB);
										PronosUtily.writeInFile(TXT_OUT_PATH+fileName, voB.toText()+"\n", true);
									}
								}
							}
							evaluada++;
						}catch (Exception e){
							e.printStackTrace();
						}
					}
//					System.out.println("# evaluadas: "+evaluada);
					log4j.debug("# evaluadas: "+ evaluada + " lsNuevos.size: " + lsNuevos.size() );
					evaluada = 0;
				}
			}
		}
	}
	
	/**
	 * Obtiene la lista de últimos ganadores (Descendiente)
	 * @return
	 */
	private static List<MelateVo> getUltimas(){
		List<MelateVo> lsCombs = new ArrayList<MelateVo>();
		lsCombs.add(new MelateVo("1,2,6,13,29,32"));
		lsCombs.add(new MelateVo("16,17,18,23,30,34"));
		lsCombs.add(new MelateVo("11,18,22,28,30,38"));
		lsCombs.add(new MelateVo("1,3,10,11,29,39"));
		lsCombs.add(new MelateVo("21,22,26,30,35,36"));
		lsCombs.add(new MelateVo("2,10,24,30,38,39"));
		lsCombs.add(new MelateVo("7,22,24,25,28,29"));
		lsCombs.add(new MelateVo("2,6,15,20,22,30"));
		lsCombs.add(new MelateVo("8,12,19,30,31,39"));
		lsCombs.add(new MelateVo("7,13,17,23,28,31"));
		lsCombs.add(new MelateVo("8,15,23,24,25,32"));
		
		return lsCombs;
	}
	
	/**
	 * Metodo que compara las combinaciones del archivo de nuevas para detectar 
	 * aquellas que sobrepasan el factor de Coincidencia
	 */
	protected static void comparaNuevos(){
		int factCoinciden = 4;	//Filtra en pantalla los que coinciden 4 numeros
		int minIncidencia = 2;	//Filtra en pantalla los de la incidencia o superior
		StringBuilder sbMatriz = new StringBuilder();
		int total=0, indice = 1;
		List<MelateVo> lsCombs =
				PronosUtily.getVoFromCsv("/home/dothr/workspace/MyProjects/files/melate/MR-g02(AunNOJugado).txt");
		
		//Encabezado de archivo
		sbMatriz.append("comb,");
		for(int x=0;x<lsCombs.size();x++){
			sbMatriz.append(x+1).append(",");
		}
		sbMatriz.append("\n");	
		
		Iterator<MelateVo> itCombA = lsCombs.iterator();
		MelateVo voA, voB;
		while(itCombA.hasNext()){
			int similitud = 0, incidenciasSim = 0;
			voA = itCombA.next();
			Iterator<MelateVo> itCombB = lsCombs.iterator();
			sbMatriz.append(indice).append(",");
			while(itCombB.hasNext()){
				voB = itCombB.next();
				similitud = CombCommon.getSimilitud(voA.toList(), voB.toList()) ;
				sbMatriz.append(PronosUtily.addStringd(" ", similitud!=6?String.valueOf(similitud):" ", 3)).append(",");
				
				if(similitud==factCoinciden	//similitud>=factCoinciden
						&& similitud<6){
					incidenciasSim++;
//					System.out.println("coincidencia: " +voA.toText()+"/"+voB.toText() 
//							+ " = " + CombCommon.getSimilitud(voA.toList(), voB.toList()) );
//					total++;
				}
			}
			if(incidenciasSim>=minIncidencia){
				System.out.println("["+indice+"] Total de incidencias de similitud "+factCoinciden+ ": "+incidenciasSim +" ["+voA.toText()+"]");
			}
			
			sbMatriz.append("\n");
			indice++;
			incidenciasSim=0;
		}
		try {
			PronosUtily.writeInFile(ROOT_DIR+"MatrizIncidencia.csv", sbMatriz.toString(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}