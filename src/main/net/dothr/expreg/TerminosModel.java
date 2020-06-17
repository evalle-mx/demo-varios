package net.dothr.expreg;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import utily.FileUtily;

/**
 * Genera archivo con unicamente terminos a partir del archivo de texto
 * @author dothr
 *
 */
public class TerminosModel {

	final static String EXP_REG_COMODIN="[:]{1}";
	final static String EXP_REG="[  0-9A-ZÑÁÉÍÓÚÜ'\"&,\\-/.\\(\\)\\[a-zñáéíóúü]+" + EXP_REG_COMODIN;
	
	final static String REPLACE_COMODIN="\n";//", ";
	final static String ADDFIN=" .";
	
	/* >> Creación Archivos terminos: */
	final static String IN_FILE = "/home/dothr/Documents/dothr_otros/modelos/MODELO3/semillas/area18/ExpRegularFile.txt"; 
			//"/home/dothr/workspace/Varios/files/prueba.txt";
	final static String OUT_FILE = "/home/dothr/Documents/TCE/semillas_cvs/Nutch/Terminos/Area-18.txt";
	
	
	/* Comparador */
//	final static String FILE_A = "/home/dothr/Documents/TCE/semillas_cvs/Nutch/Terminos/Area-8.txt";
//	final static String FILE_B = "/home/dothr/Documents/TCE/semillas_cvs/Nutch/Terminos/Area-22.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createTermFile();

	}
	
	/**
	 * Lee un archivo plano (ExpRegularFile) con Término: descripcion, 
	 * y genera listado de solo Terminos en un archivo txt
	 */
	protected static void createTermFile(){
		try {
			String cad=null;
			StringBuilder sb=new StringBuilder();
			
			StringTokenizer st=new StringTokenizer( readFile(IN_FILE, StandardCharsets.UTF_8), "\n" );

			while(st.hasMoreTokens()){
				cad=st.nextToken();
				if(cad.matches(EXP_REG)){
					cad=cad.replaceAll(EXP_REG_COMODIN, "");
					if(cad.replace(" ", "").length()<3){
						System.out.println(">>"+cad+"?");
						//sb.append(cad).append("==>").append(cad.length()).append("\n");
					}else{
						if(cad.endsWith(" ")){
							cad= cad.substring(0,cad.length()-1);
						}
						if(cad.indexOf(".") !=-1){
							System.out.println("++"+cad+"++");
							cad = cad.replace(".", "");
						}
						sb.append(cad).append("\n");
					}
				}
//				else{ 	System.out.println("KO"+" -> "+cad); }
			}
			//filtros
			if(sb.length() > 0){
				sb.delete(sb.lastIndexOf(REPLACE_COMODIN), sb.length());
				sb.append(ADDFIN);				
			}
			FileUtily.writeStringInFile(OUT_FILE, sb.toString(), false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param path
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	private static String readFile(String path, Charset encoding)  throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)), encoding);
	}
		  

}
