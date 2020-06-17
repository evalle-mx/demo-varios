package com.pronos.util;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class Segmentador {

	static Logger log4j = Logger.getLogger( Segmentador.class);
	private static final String DIR_ROOT = "/home/dothr/workspace/MyProjects/files/melate/";
	private static final String TXT_DEPURADO_PATH = DIR_ROOT+
			"Melate-Retro-Todas-Depurado.txt"; //3,198,806 (3,199,268 c/28's) (Se redujeron 62,174 [61,712] comb improbables)
			//"Melate-Retro-Todas-Depurado.a.txt"; //3,260,980 (3,261,895 con los empezados en 28,...)
	private static List<String> lsAll = null;
	private static long tamSegmento = 319881; 	// 326190 | 319881
	private static final String segName = "segmentado/Depurado.X.txt";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		showDepuradoSize();
		SegmentaFile();
	}
	
	/**
	 * Muestra el numero de combinaciones total del archivo depurado Original
	 */
	private static void showDepuradoSize(){
		lsAll = PronosUtily.getListLines(TXT_DEPURADO_PATH);
		
		System.out.println("lsAll.size: " + lsAll.size());
		
		//3,199,268 ?? (Se redujeron 61,712 comb improbables)
	}
	
	/**
	 * Segmenta el archivo de combinaciones en otros archivos de menor tamaño
	 */
	public static void SegmentaFile(){
		lsAll = PronosUtily.getListLines(TXT_DEPURADO_PATH);
		log4j.debug("lsAll.size: " + lsAll.size() );
		StringBuilder sbSegmento = new StringBuilder();
		
		Iterator<String> itAll = lsAll.iterator();
		String comb;
		long contaComb = 0, fileCont=1, iContI=0, iContF=0, contadorTotal=0;;
		String filename = segName.replace("X", String.valueOf(fileCont));
		while(itAll.hasNext()){
			comb = itAll.next();
			iContF++;
			contadorTotal++;
			try{
				if(!comb.startsWith("/*")){
					sbSegmento.append(comb).append("\n");
					contaComb++;
					if(contaComb==tamSegmento){
						filename = segName.replace("X", String.valueOf(fileCont++));
						log4j.debug("Escribiendo segmento: "+filename  +" (De "+ iContI +" a " + iContF);
						iContI= iContF;
						PronosUtily.writeInFile(DIR_ROOT+filename, sbSegmento.toString(), false);
						sbSegmento = new StringBuilder();
						contaComb=0;
					}
				}
			}catch (Exception e){
				log4j.fatal("Error al procesar elemento "+contaComb+", del archivo "+filename, e);
			}
		}
		filename = segName.replace("X", String.valueOf(fileCont++));
		log4j.debug("Escribiendo segmento (FINAL): "+filename  +" (De "+ iContI +" a " + iContF);
		iContI= iContF;
		try {
			PronosUtily.writeInFile(DIR_ROOT+filename, sbSegmento.toString(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log4j.debug("PRocesadas: " + contadorTotal);
	}

}
