package com.pronos.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


import org.apache.log4j.Logger;

import com.pronos.vo.MelateVo;
import com.pronos.vo.NumeroSorteoVo;

public class PronosUtily {

	private static Logger log4j = Logger.getLogger(PronosUtily.class);
	
	/**
	 * Obtiene el numero de consecutivos
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param f4
	 * @param f5
	 * @param f6
	 * @return
	 */
	public static int consecParcial(int f1, int f2, int f3, int f4, int f5, int f6){
		int consecutivo = 1;
		if(f6==f5+1){
			consecutivo++;
		}
		if(f5==f4+1){
			consecutivo++;
		}
		if(f4==f3+1){
			consecutivo++;
		}
		if(f3==f2+1){
			consecutivo++;
		}
		if(f2==f1+1){
			consecutivo++;
		}
		return consecutivo;
	}
	/**
	 * Recibe 6 factores, si son consecutivos [fZ=fY+1,fY= fX+1] regresa true
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param f4
	 * @param f5
	 * @param f6
	 * @return
	 */
	public static boolean consecPerfecto(int f1, int f2, int f3, int f4, int f5, int f6){
		//if(f3==f2+1 && f2==f1+1){
		if(f6==f5+1){
			if(f5==f4+1){
				if(f4==f3+1){
					if(f3==f2+1){
						if(f2==f1+1){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * Regresa la lista de numerales 
	 * @param lsMelate
	 * @return
	 */
	public static ArrayList<NumeroSorteoVo> getFrecuenciaMRetro(List<MelateVo> lsMelate){
		Integer[] num = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//new Integer[40];
		Integer[] sorteo;
		Iterator<MelateVo> itMel = lsMelate.iterator();
		while(itMel.hasNext()){
			sorteo = itMel.next().toArrayInt();
			for(int y=0; y<sorteo.length;y++){
				num[(sorteo[y]) ]++;
			}
		}
//		System.out.println("num.length " + num.length);
		ArrayList<NumeroSorteoVo> lsNumSorteo = new ArrayList<NumeroSorteoVo>();
		for(int x=0;x<num.length;x++){
//			System.out.println( ("num["+(x)+"]=")+ num[x]);
			if(x!=0){
				lsNumSorteo.add(new NumeroSorteoVo(x, num[x]));
			}
		}
//		System.out.println("\nOrdenando la lista ("+lsNumSorteo.size()+") de Numeros en sorteo:\n");
		Collections.sort(lsNumSorteo);
		return lsNumSorteo;
	}
	
	/**
	 * Obtiene la lista de Objetos Melate(Combinacion) desde el archivo CVS de historico
	 * (Contiene numero de sorteo, bolsa y fecha)
	 * @param CVS_PATH
	 * @return
	 */
	public static ArrayList<MelateVo> getLsHistoricoMelate(String CVS_PATH){
		ArrayList<MelateVo> listHistorico = new ArrayList<MelateVo>();
		Iterator<String> itLineas= PronosUtily.getListLines(CVS_PATH).iterator();
		String linea, bolsa, nwLinea;
		while(itLineas.hasNext()){
			linea = itLineas.next();
			if(!linea.startsWith("N")){
//				log4j.debug("linea >" + linea + "<");
				//FIX para numeral en linea:
				bolsa = linea.substring(linea.indexOf("$"),linea.indexOf(".00"));//log4j.debug("bolsa?"+bolsa);
				nwLinea = linea.substring(0,linea.indexOf("$"))+bolsa.replace(",", "")+linea.substring(linea.indexOf(".00"), linea.length());	//log4j.debug("nwLinea: " + nwLinea );
				
				String[] datos = nwLinea.split(",");
//				log4j.debug("datos.length "+datos.length);
				
				if(datos.length==11 && PronosUtily.isNumber(datos[0])){
					MelateVo vo = new MelateVo(datos);
					listHistorico.add(vo);
				}
			}
		}		
		return listHistorico;
	}
	
	/**
	 * Obtiene las combinacionesVo de un archivo plano 
	 * @param cvsPath
	 * @return
	 */
	public static ArrayList<MelateVo> getVoFromCsv(String cvsPath){
		ArrayList<MelateVo> listVo = new ArrayList<MelateVo>();
		Iterator<String> itLineas= PronosUtily.getListLines(cvsPath).iterator();
		String linea;
		while(itLineas.hasNext()){
			linea = itLineas.next();
			if(!linea.startsWith("N") && !linea.startsWith("/*") && linea.length()>9){
				listVo.add(new MelateVo(linea));
			}
		}
		return listVo;
	}
	
	
	/* ****************************************************************
	 * ************************** Simples *****************************
	 * ****************************************************************
	 */
	/**
	 * Agrega la cadena stAdd a la izquierda de la cadena sCad, hasta que tenga la longitud mxLength.<br> <br>
	 * i.e. addString("0","1",4) = "0001";
	 * @param stAdd
	 * @param sCad
	 * @param mxlength
	 * @return String
	 */
	public static String addStringd(String stAdd, String sCad, int mxlength){
		int longCad = sCad.length();
		int faltantes = mxlength - longCad;
		for(int i=0; i< faltantes; i++){
			sCad = stAdd+sCad;
		}
		return sCad;
	}
	
	/**
	 * Genera un numero entero aleatorio menor o igual al maximo definido
	 * @param maximo
	 * @return
	 */
	public static int getAleatorio(int maximo){
		int numeroAleatorio = (int) (Math.random()*maximo+1);
		return numeroAleatorio;
	}
	
	/**
	 * Genera un numero aleatorio con la longitud determinada
	 * @param longitud
	 * @return
	 */
	public static Long getAleatorio(int maximo, int longitud){
		String numero = "";
		for(int i=0;i<longitud;i++){
			numero+=(getAleatorio(maximo)-1);
		}
		return Long.parseLong(numero);
	}
	/* ****************************************************************
	 * ******************** Manipulacion de Archivo *******************
	 * ****************************************************************
	 */
	
	/**
	 * Obtiene un Objeto List conteniendo las lineas leidas de un archivo (CSV)
	 * @param filePath Ruta del archivo a leer
	 * @return List de Cadenas
	 */
	public static List<String> getListLines(String filePath) {
		List<String> lineas = null;
		try 
	    {
	        BufferedReader infile = new BufferedReader(new FileReader(filePath));
	        String str;
	        int nLines = 0;
	        lineas = new ArrayList<String>();
	        while ((str = infile.readLine()) != null) 
	        {
	            lineas.add(str);
	            nLines++;
	        }
	        infile.close();
	        log4j.debug("numero de Lineas Leidas : "+nLines);
	    } 
	    catch (IOException e) {
	    	log4j.fatal("[IOException] Error al leer archivo ".concat(filePath), e);
//	    	throw e;
	    }
		return lineas;
	}
	
	/**
	 * Escribe una cadena de texto en un archivo
	 * @param filePath ruta, nombre y extension  del archivo
	 * @param texto Cadena a agregar
	 * @param append true: Permite agregar mas lineas al archivo, false: Escribe nuevo archivo cada vez 
	 */
	public static void writeInFile(String filePath, String texto, boolean append ) throws Exception{
		BufferedWriter bufferedWriter;
		if(null == texto){
			return;
		}
		try {
//			log4j.debug( ( append?"Agregando":"Escribiendo").concat(" lineas al archivo " + filePath) );
			bufferedWriter = new BufferedWriter(new FileWriter(filePath, append));
//			bufferedWriter.newLine();
			bufferedWriter.write(texto);
			bufferedWriter.close();
		} catch (IOException e) {
			log4j.fatal("[IOException] Error de escritura en archivo "+ filePath, e);
			throw e;
		}
	}
	
	/* ************************************************************ */
	/* ********************    Utilerias de fecha   *************** */
	/* ************************************************************ */
	/**
	 * Devuelve la fecha actual en formato definido
	 * @param pattern formato de respuesta
	 * @return
	 */
	public static String todayFormated(String pattern){
		 Date d = new Date(); // fecha actual del sistema	
		 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		 return sdf.format(d);
	}
	
	/**
	 * Convierte un string a fecha
	 * @param String
	 * @returns Date
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static Date string2Date(String dateString) {
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fecha;
	}
	
	public static boolean isNumber(String stNumber){
		try{
			Integer.parseInt(stNumber);
		} catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public static double porcentaje(double x, double y){
//		DecimalFormat df = new DecimalFormat("####0.00"); df.format(value);
		return ((x/y)*100);
	}
	
	public static String toCurrency(Number number){
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String currency = format.format(number);
//        System.out.println("Currency Here : " + currency);

        // Format currency for Germany locale in German locale,
        // the decimal point symbol is a dot and currency symbol
        // is €.
//        format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
//        currency = format.format(number);
        return currency;
	}
	
	
	public static void main(String[] args) {
//		System.out.println(string2Date("27/8/2016"));
//		getLsHistoricoMelate("/home/dothr/Downloads/Melate-Retro.csv");
		
//		for(int x=0;x<20;x++){
//			System.out.println(getAleatorio(6));
//		}
//		getAleatorio(6);
		
		//System.out.println(consecParcial(1, 2, 5, 7, 8, 19)); //3 ok, 4 No probable
		
//		List<MelateVo> lsVo = getVoFromCsv("/home/dothr/workspace/MyProjects/files/melate/MR-g02(AunNOJugado).txt");
//		for(int x=0;x<lsVo.size();x++){
//			System.out.println(lsVo.get(x).toText());
//		}
//		System.out.println("lsVo.size " + lsVo.size() );
		System.out.println(porcentaje(30, 100));
	}
}
