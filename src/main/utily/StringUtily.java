package utily;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class StringUtily {
	
	/**
	 * Separa una cadena en subCadenas y las guarda en una Lista
	 * @param input
	 * @param sep
	 * @return
	 */
	public static List<String> getTokens(String input, String sep) {
		List<String> items = Arrays.asList(input.split("\\s*"+sep+"\\s*"));
		return items;
	}
	
	/**
     * Remueve los espacios en blanco al principio y final de una cadena, 
     * sin afectar los que se encuentran entre caracteres 
     * @param tabulada
     * @return
     */
    public static String removeTabs(String tabulada){
    	String subCadena = tabulada;
		boolean val = true;
		do{
			if(subCadena!=null && subCadena.length()>0){
				String prim = subCadena.substring(0,1);
				if(prim.equals(" ")){
					subCadena = subCadena.substring(1,subCadena.length());
				}else{
					//val = false;
					if(subCadena.length()>0){
						String last = subCadena.substring(subCadena.length()-1,subCadena.length());
						if(last.equals(" ")){
							subCadena = subCadena.substring(0,subCadena.length()-1);
						}else{
							val = false;
						}
					}
				}
			}else{
				if(subCadena==null){
					subCadena = "";
				}
				val = false;
			}
			
		}while(val);
		return subCadena;
    }
	/**
	 * Reemplaza los hexadecimales por los caracteres especiales originales 
	 * @param trackPath
	 * @return
	 */
	public static String replaceUnicode(String textHexa){
		
		return textHexa
				.replace("%20", " ")
				.replace("%C3%A1", "á").replace("%C3%81", "Á")
				.replace("%C3%A9", "é").replace("%C3%89", "É")
				.replace("%C3%AD", "í").replace("%C3%8D", "Í")
				.replace("%C3%B3", "ó").replace("%C3%93", "Ó")
				.replace("%C3%BA", "ú").replace("%C3%9A", "Ú")
				.replace("%C3%BC", "ü").replace("%C3%9C", "Ü")
				.replace("%C3%B1", "ñ").replace("%C3%91", "Ñ")
				.replace("%C2%B4", "´").replace("%60", "`")
				.replace("%E2%80%93", "–").replace("%C2%BF","¿")				
				.replace("%C3%88", "È").replace("%C3%9F", "ß")
				.replace("%C3%A0", "à").replace("%C3%A4", "ä")
				.replace("%C3%AC", "ì").replace("%C3%B2", "ò") 
				.replace("%C3%B4", "ô").replace("%C3%B6", "ö")
				.replace("%5B", "[").replace("%5D", "]")
				;
	}
	
	/**
	 * Elimina los caracteres en arreglo parametro
	 * @param texto
	 * @param tokens [] = {"%","&"};
	 * @return
	 */
	public static String cleanFreeText(String texto, String[] tokens){
		if(texto!=null){
			for(String token : tokens){
				texto = texto.replace(token, "");
	    	}
	    	return texto;
		}
		return null;
	}
	
	/**
	 * Se reemplazan tildes y acentos específicos de Vocales
	 * @param token
	 * @return el token modificado
	 */
	public static String replaceAccentMarks(String token){ //replaceEspecialChars
		return token.replaceAll("[àáâãäå]","a").
					replaceAll("[èéêë]","e").
					replaceAll("[ìíîï]","i").
					replaceAll("[òóôõö]","o").
					replaceAll("[ùúûü]","u");
	}
	
	/**
	 * Reemplaza las tildes y acentos usando libreria java.text.Normalizer
	 * @param texto
	 * @return
	 */
	public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }
	
	/**
	 * Reemplaza los caracteres especiales por un correspondiente definido
	 * @param input
	 * @return
	 */
	public static String replaceOrdered(String input) {
		/* http://www.v3rgu1.com/blog/231/2010/programacion/eliminar-acentos-y-caracteres-especiales-en-java/ */
	    // Cadena de caracteres original a sustituir.
	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    if(original.length()!=ascii.length()){
	    	System.err.println("Las cadenas de caracteres deben ser iguales");
	    	return null;
	    }
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	        // Reemplazamos los caracteres especiales.
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }//for i
	    return output;
	}
	
	/* ************************************************************** */
	/* ******************* TEST METHODS ***************************** */
	/* ************************************************************** */
	/**
	 * Muestra funcionamiento de .replaceAll con Expresiones regulares
	 */
	private static void testReplaceAll(){
		String text = "This 1st- texñt ! has \\ /allot # of % special % characters";
		text = text.replaceAll("[^a-zA-Z0-9]", "_");
		System.out.println(text);

		String text2 = "This#is&bold(2";
		text2 = text2.replaceAll("[^a-zA-Z0-9\\s+]", "");
		System.out.println(text2);
		
		String text3 = "Téxto ën Español";
		text3 = text3.replaceAll("[^a-zA-Z0-9\\s+]", "");
		System.out.println(text3);
	}
	
	private static void testCleanFreeText(){
		String[] tokens = {"%","&","#","\"", "$","!","?","¿","|", "="};
		String text = "c%ad&en#a co$n ca!rac???té¿res";
		System.out.println(cleanFreeText(text, tokens));		
	}
	
	public static void testAccentMarks(){
		String cadena = "Mónica argüello Pérez";
		System.out.println(replaceAccentMarks(cadena));
	}
	
	public static void testCleanString(){
		String cadena = "Mónica # argüello Pérez";
		System.out.println(cleanString(cadena));
	}
	
	public static void testRepOrdered(){
		String cadena = "Mañana será un dïa mejor";
		System.out.println(replaceOrdered(cadena));
	}

	public static void main(String[] args) {
//		testReplaceAll();
//		testCleanFreeText();
//		testAccentMarks();
//		testCleanString();
		testRepOrdered();
	}
	
	/**
	 * Lee un archivo planto, generando un Builder substituyendo Tabuladores por un espacio en blanco
	 * utilizando el charset Definido
	 * @param fullPath
	 * @param stCharset
	 * @return
	 */
	public static StringBuilder getBuilderNoTabsFile( String fullPath, String stCharset) throws IOException{
		StringBuilder sb = new StringBuilder();
		if(stCharset==null || stCharset.trim().equals("")){
			stCharset="UTF8";
		}
		//log4j.debug("**************  USANDO FileInputStream con "+stCharset+" *****************");
	        BufferedReader infile = new BufferedReader(
	        		 new InputStreamReader(
	                         new FileInputStream(fullPath), stCharset));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
	        	String unTabedLine = removeTabs(strLine).concat(" ");
	            sb.append(unTabedLine).append("\n");
	        }
	        infile.close();
	    return sb;
	}
	
	/*  ************** AFORE BANAMEX *************** */
	public static List<String> cadenaARenglones(String cadena, int longMaxima){
		 List<String> renglones = null;
		 if(cadena!=null){
			 renglones = new ArrayList<String>();
			 while(cadena.indexOf("  ")!=-1){//esta parte elimina tabuladores y dobles espacios en blanco
				 cadena = cadena.replace("  ", " ");
			 }
			if(cadena.length()<=longMaxima){
				renglones.add(cadena);
			}else{
				String subCadenaUno;
				String subCadenaRest = cadena;
				boolean finCadena = false;
				while(!finCadena){
					if(subCadenaRest.length()<longMaxima){
						//subCadenaUno = subCadenaRest.substring(0, subCadenaRest.length());						
						finCadena = true;
						renglones.add(subCadenaRest);
					}else{
						subCadenaUno = subCadenaRest.substring(0, longMaxima);
						System.out.println(subCadenaUno.concat("|") );
						int ultimoBLank = subCadenaUno.lastIndexOf(" ");
						//System.out.println("ultimo espacio en blanco en: " + ultimoBLank);
						subCadenaUno = subCadenaRest.substring(0, ultimoBLank);
						
						subCadenaRest = subCadenaRest.substring(ultimoBLank+1, subCadenaRest.length());
						
						System.out.println(subCadenaUno.concat("|").concat(subCadenaRest) );
						renglones.add(subCadenaUno);
					}
					
					
				}
				
				
				
			}
		 }
		 return renglones;
	 }
	
	/**addTokenL (Left) agrega Caracteres a la Izquierda de la cadena hasta la longitud determinada
	 * @param stToken
	 * @param sCadena
	 * @param mxlength
	 * @return String
	 */
	public static String addTokenL(String stToken, String sCadena, int iMaxLength){
		int longCad = sCadena.length();
		int faltantes = iMaxLength - longCad;
		for(int i=0; i< faltantes; i++){
			//sCadena = stToken+sCadena;
			sCadena = stToken.concat(sCadena);
		}
		return sCadena;
	}
}
