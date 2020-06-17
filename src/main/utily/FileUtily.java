package utily;
/**
 * Clase con metodos estaticos que proporcionan utilidades para generacion, manipulacion o transformacion
 * de archivos
 * @author EAVV
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//import test.stringt.StrUtily;

public class FileUtily {
	
	private static String defaultDir = "D:/SalidaJava/";
	private static String defCharset = "UTF-8";	// UTF-8 | ISO-8859-1
	
	/**
	 * Muestra una lista del contenido dentro de un directorio (archivos y directorios)
	 * @param dirPath
	 */
	public static void listContentOFDir(String dirPath){
		
		File dir = new File(dirPath);
		String[] contenido = dir.list();
		
		if(contenido!=null){
			for(int x=0;x<contenido.length;x++){				
				
				System.out.println(contenido[x]);
			}
		}else{
			System.out.println("El directorio esta vacio");
		}
	}
	
	/**
	 * Muestra una lista de directorios contenidos dentro de un directorio
	 * @param dirPath
	 */
//	public static void listDirsInDir(String dirPath){
	public static List<String> listDirsInDir(String dirPath){	
		List<String> lsDirs = null;
		File dir = new File(dirPath);
		FileFilter fileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isDirectory();
	        }
	    };
	    
	    File[] dirs = dir.listFiles(fileFilter);
		
		if(dirs!=null){
			lsDirs = new ArrayList<String>();
			for(int x=0;x<dirs.length;x++){				
				lsDirs.add(dirs[x].getName());
				System.out.println(dirs[x].getName());
			}
		}else{
			System.out.println("El directorio esta vacio");
		}
		return lsDirs;
		
	}
	/**
	 * Muestra una lista de archivos contenidos dentro de un directorio
	 * @param dirPath
	 */
//	public static void listFilesInDir(String dirPath){
	public static List<String> listFilesInDir(String dirPath){
		List<String> lsFiles = null;
		File dir = new File(dirPath);
		FileFilter fileFilter = new FileFilter() {
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    };
	    
	    File[] files = dir.listFiles(fileFilter);
		
		if(files!=null){
			lsFiles = new ArrayList<String>();
			for(int x=0;x<files.length;x++){				
				lsFiles.add(x, files[x].getName());
				//System.out.println(files[x].getName());
			}
		}else{
			System.out.println("El directorio esta vacio");
		}
		return lsFiles;
	}
	

	/**
	 * Metodo que crea un archivo de prueba
	 */
	public static void createTestFile () {
		String fullPath = defaultDir + "salida.txt";		
		String sCadena = "----ESTA ES LA CADENA A IMPRIMIR --- ";
		int inumber = Integer.parseInt("125");//NUMERO A IMPRIMIR EN EL ARCHIVO		
		
		try{
		      FileOutputStream fos = new FileOutputStream(fullPath);
		      PrintWriter pw = new PrintWriter(fos);
		      System.out.println("*");		      
		      pw.println(sCadena);
		      System.out.println("**");
		      pw.println(inumber);
		      System.out.println("***");
		      pw.flush();
		      System.out.println("****");
		      pw.close();
		      System.out.println("****");
		      fos.close();
		      System.out.println("******");
		    }catch (FileNotFoundException e){
		    	//System.out.println("FileNotFoundException: " + e);
		    	e.printStackTrace();
		    }catch (IOException e2){ 
		    	 //System.out.println("IOException: " + e2);
		    	 e2.printStackTrace();
		     }
	 }
	
	/**
	 * Despliega informacion del fichero
	 * @param fullPath
	 */
	public static void printFileInfo(String fullPath){
		File f = new File(fullPath);
		//String fileInfo = " NO EXISTE";
		StringBuffer fileInfo = new StringBuffer(" NO EXISTE");
		if (f.exists()){
			if (f.isFile()) {
				fileInfo = new StringBuffer("Es un archivo\n");
//				fileInfo = "Es un archivo\n"  + 
//					"Ruta absoluta: \t\t\t| "		+ f.getAbsolutePath()+
//				"\n Puede leer: \t\t\t| "	 		+ f.canRead()+
//				"\n Puede escribir:\t\t| " 		+ f.canWrite()+
//				"\n Nombre del fichero:\t\t| " 	+ f.getName()+
//				"\n Padre del fichero:\t\t| " 	+ f.getParent()+
//				"\n Ruta del fichero:\t\t| " 	+ f.getPath()+
//				"\n Longitud:\t\t\t| " 			+ f.length()+
//				"\n Ultima modificaci�n (long):\t| " + f.lastModified();
				
				fileInfo.append("Ruta absoluta: \t\t\t| ").append(f.getAbsolutePath());
				fileInfo.append("\n Puede leer: \t\t\t| ").append(f.canRead());
				fileInfo.append("\n Puede escribir:\t\t| ").append(f.canWrite());
				fileInfo.append("\n Nombre del fichero:\t\t| ").append(f.getName());
				fileInfo.append("\n Padre del fichero:\t\t| ").append(f.getParent());
				fileInfo.append("\n Ruta del fichero:\t\t| ").append(f.getPath());
				fileInfo.append("\n Longitud:\t\t\t| ").append(f.length());
				fileInfo.append("\n Ultima modificaci�n (long):\t| ").append(f.lastModified());
			}else if (f.isDirectory()) 
					fileInfo = new StringBuffer("Es un directorio");
				else
					fileInfo = new StringBuffer("\t f. ES indefinido");
		}
		else
			fileInfo = new StringBuffer("\t file: "+fullPath+" NO EXISTE");		
		System.out.println(fileInfo.toString());
	}
	
	/**
	 * Lee y Despliega en pantalla linea por linea de un archivo
	 * @param fullPath
	 */
	public static void readFile( String fullPath){
		int nLines = 0;
		try 
	    {
	        BufferedReader infile = new BufferedReader(new FileReader(fullPath));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
	            System.out.println(strLine);    
	            nLines++;
	        }
	        infile.close();	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
	    if(nLines!=0)
	    	System.out.println("<Fin>Numero de Lineas Leidas: " + nLines + "</fin>");
	    else
	    	System.out.println("Archivo Vacio: " + nLines + " lineas");	    
	}
	
	/**
	 * Duplica el archivo solicitado en la misma ruta
	 * @param fullPath
	 */
	public static boolean duplicate(String fullPath) {       
        String path = "", fileName = "", fileExt = "";
        String copyName = "Copy";
        
        int lastRoot = fullPath.lastIndexOf("/");
        int lastExt  = fullPath.lastIndexOf(".");
        
        if(lastRoot!= -1){
        	path = fullPath.substring(0, lastRoot+1);
        	if(lastExt!= -1){
            	fileName = fullPath.substring(lastRoot+1, fullPath.length()-4);
            	fileExt = "."+fullPath.substring(fullPath.lastIndexOf(".")+1, fullPath.length());
            }else{
            	fileName = fullPath.substring(lastRoot+1, fullPath.length());
            }
        }else{
        	if(lastExt!= -1){
            	fileName = fullPath.substring(0, fullPath.length()-4);
            	fileExt = "."+fullPath.substring(fullPath.lastIndexOf(".")+1, fullPath.length());
            }else{
            	fileName = fullPath;
            }
        }        
        return duplicate(path, fileName, path, fileName+copyName, fileExt);
   }
	/**
	 * Duplica un archivo
	 * @param fullPathIn
	 * @param fullPathOut
	 * @return
	 */
	public static boolean duplicate(String fullPathIn, String fullPathOut) {
	       
		String dirIn = "", fileNameIn = "";
		String dirOut = "", fileNameOut = "";
        String fileExt = "";
        String fileExt2 = ""; //para validar si son diferentes, se toma por default primero
        
        //  validacion para entrada
        int lastRootIn = fullPathIn.lastIndexOf("/");
        int lastExtIn  = fullPathIn.lastIndexOf(".");
        
        if(lastRootIn!= -1){
        	dirIn = fullPathIn.substring(0, lastRootIn+1);
        	if(lastExtIn!= -1){
        		fileNameIn = fullPathIn.substring(lastRootIn+1, fullPathIn.length()-4);
            	fileExt = "."+fullPathIn.substring(fullPathIn.lastIndexOf(".")+1, fullPathIn.length());
            }else{
            	fileNameIn = fullPathIn.substring(lastRootIn+1, fullPathIn.length());
            }
        }else{
        	if(lastExtIn!= -1){
        		fileNameIn = fullPathIn.substring(0, fullPathIn.length()-4);
            	fileExt = "."+fullPathIn.substring(fullPathIn.lastIndexOf(".")+1, fullPathIn.length());
            }else{
            	fileNameIn = fullPathIn;
            }
        }        
        //  Validacion para salida
        
        int lastRootOut = fullPathOut.lastIndexOf("/");
        int lastExtOut  = fullPathOut.lastIndexOf(".");
        
        if(lastRootOut!= -1){
        	dirOut = fullPathOut.substring(0, lastRootOut+1);
        	if(lastExtOut!= -1){
        		fileNameOut = fullPathOut.substring(lastRootOut+1, fullPathOut.length()-4);
        		fileExt2 = "."+fullPathOut.substring(fullPathOut.lastIndexOf(".")+1, fullPathOut.length());
            }else{
            	fileNameOut = fullPathOut.substring(lastRootOut+1, fullPathOut.length());
            }
        }else{
        	if(lastExtOut!= -1){
        		fileNameOut = fullPathOut.substring(0, fullPathOut.length()-4);
        		fileExt2 = "."+fullPathOut.substring(fullPathOut.lastIndexOf(".")+1, fullPathOut.length());
            }else{
            	fileNameOut = fullPathOut;
            }
        }
        
        if( !(fileExt2.equalsIgnoreCase(fileExt)) ){
        	System.out.println("las extensiones son diferentes");
        }
        
        return duplicate(dirIn, fileNameIn, dirOut, fileNameOut, fileExt);
   }
	/**
	 * Duplica archivo de entrada y genera una copia determinada por parametros
	 * @param dirIn, directorio Original
	 * @param fileName, nombre del archivo de lectura (sin extension)
	 * @param dirOut, directorio de salida
	 * @param fileOutName, nombre del archivo copia (sin extension)
	 * @param stExt, extension del archivo (txt, "")
	 * @return
	 */
	private static boolean duplicate(String dirIn, String fileName, 
										String dirOut, String fileOutName, String stExt) {
		
		FileReader entrada = null;
	    FileWriter salida  = null;
	    boolean resultado = false;
	    
		try {        	
            entrada = new FileReader(dirIn+fileName+stExt);
            salida  = new FileWriter(dirOut + fileOutName + stExt);
            int cAscii;
            while((cAscii=entrada.read())!=-1){
            	//System.out.println(cAscii);
                salida.write(cAscii);
            }
            resultado = true;
		}catch (IOException ex) {
       		//System.err.println("IOEXCEPTION "+ex);
            ex.printStackTrace();
		}finally{	//cerrar los flujos de datos
            if(entrada!=null){
                try{
                    entrada.close();
                }catch(IOException ex){
                	ex.printStackTrace();
                }
            }
            if(salida!=null){
                try{
                    salida.close();
                }catch(IOException ex){
                	ex.printStackTrace();
                }
            }           
       }
		return resultado;
	}
	
	
	/**
	 * Metodo que duplica un archivo de entrada en una salida determinada, 
	 * utilizando FileInputStream y FileOutputStream
	 * @param inFilePath ruta del archivo con extension
	 * @param outFilePath ruta de salida y nombre de archivo con extension
	 */
	public void duplicateStreams(String inFilePath, String outFilePath){
		
		try{
		 	File inputFile = new File(inFilePath);
	        File outputFile = new File(outFilePath);

	        FileInputStream in = new FileInputStream(inputFile);
	        FileOutputStream out = new FileOutputStream(outputFile);
	        int c;

	        while ((c = in.read()) != -1)
	           out.write(c);
	        in.close();
	        out.close();
		}catch (IOException e) {
			e.printStackTrace();			
		}
	}
	/**
	 * Metodo que duplica un archivo de entrada en una salida determinada, 
	 * utilizando BufferedReader y BufferedWriter
	 * @param inFilePath
	 * @param outFilePath
	 */
	public void duplicateBuffereds(String inFilePath, String outFilePath){
		try{
			
			BufferedReader buffFileReader = new BufferedReader(new FileReader(inFilePath));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFilePath));
			
			String fileLine;
			 while ((fileLine = buffFileReader.readLine()) != null ){
				 System.out.println(fileLine);
				 bufferedWriter.write(fileLine);
				 bufferedWriter.newLine();
			 }
			 bufferedWriter.flush();
			 buffFileReader.close();
			 bufferedWriter.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * Mueve el archivo a nuevo directorio
	 * @param pathToMove
	 * @param file
	 * @return
	 */
	public static boolean moveFile(String pathToMove, File file){
		boolean moved = false;
		try{
			//File file = new File("filename");
		    
		    // Destination directory
		    File dir = new File(pathToMove);
		    
		    // Move file to new directory
		    moved = file.renameTo(new File(dir, file.getName()));
//		    if (!moved) {
//		        // File was not successfully moved
//		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return moved;
	}
	/**
	 * Renombra el archivo 
	 * @param pathToMove
	 * @param file
	 * @return
	 */
	public static boolean renameFile(String pathFile, String fromFileName, String toFileName){
		boolean renamed = false;
		try{
			File file = new File(pathFile+fromFileName);
		    
		    // Move file to new directory
			renamed= file.renameTo(new File(pathFile, toFileName));
//		    if (!moved) {
//		        // File was not successfully moved
//		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return renamed;
	}
	/**
	 * Mueve archivo a nuevo directorio, si existe lo elimina y lo vuelve a escribir
	 * @param pathToMove
	 * @param file
	 * @return
	 */
	protected static boolean moveFileForced(String pathToMove, File file){
		boolean moved = false;
		try{
			File f = new File(pathToMove+"/"+file.getName());
			File dir = new File(pathToMove);
			if(f.exists()){
				if(f.delete()){
					moved = file.renameTo(new File(dir, file.getName()));
				}
			}else{
				moved = file.renameTo(new File(dir, file.getName()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return moved;
	}
	/**
	 * Elimina un archivo, regresa booleano con resultado
	 * @param fullPath
	 * @return
	 */
	public static boolean delFile( String fullPath ){
		boolean deleted = false;
		try {
			File file = new File(fullPath);
			 deleted = file.delete();			 
		}catch (Exception e) 
	    {       e.printStackTrace();	    }
		if(!deleted){
		 	System.out.println("No se pudo eliminar el archivo " + fullPath);
		 	}
		return deleted;
	}
	
	
	/**
	 * Crea un archivo a partir de un StringBuffer y la ruta de salida
	 * @param stBuffer
	 * @param fullOutPath
	 * @return
	 */
	public static boolean createFileFromStBff(StringBuffer stBuffer, String fullOutPath){
		boolean created = false;
		BufferedWriter outfile =  null;
		try 
	    {
	        outfile = 
	          new BufferedWriter(new FileWriter(fullOutPath));
	        outfile.write(stBuffer.toString());
	        outfile.close();
	        created = true;
	    }catch (Exception e) 
	    {        	e.printStackTrace();	    }
	    finally{
	    	if(outfile!=null){
	    		try{
	    			outfile.close();
	    		}catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
		return created;
	}
	
	/**
	 * Almacenan los datos en buffers mientras leen o escriben, 
	 * por lo tanto reduciendo as� el n�mero de accesos requeridos a la fuente de datos original. 
	 * Los streams con buffer normalmente son m�s eficientes que los que no lo utilizan. 
	 * @param fileDir
	 * @param fileName
	 * @return BufferedReader
	 */
	public static BufferedReader getBufferedReader(String fileDir , String fileName){
		BufferedReader buffFileReader = null;
		if(fileDir == null){
			fileDir = defaultDir;
		}
		try{
			buffFileReader = new BufferedReader(new FileReader(fileDir + fileName));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return buffFileReader;
	}
	
	/**
	 * Agrega texto a un archivo
	 * @param fileDir
	 * @param fileName
	 * @param texto
	 * @param append
	 */
	public static void writeStringInFile(String fileDir, String fileName, String texto,
			boolean append){
		if(fileDir == null){
			fileDir = defaultDir;
		}
		if(null == fileName){
			return;
		}
		
		writeStringInFile(fileDir + fileName, texto,append);
	}
	
	/**
	 * Lee un archivo planto, generando un Builder
	 * @param fullPath
	 * @return
	 */
	public static StringBuilder getBuilderFile(String fullPath, String stCharset){
		StringBuilder sb = new StringBuilder();
		if(stCharset==null){
			stCharset=defCharset;
		}
		try 
	    {
			BufferedReader infile = new BufferedReader(
	        		 new InputStreamReader(
	                         new FileInputStream(fullPath), stCharset));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
	            sb.append(strLine).append("\n");
//	            nLines++;
	        }
	        infile.close();	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
	    return sb;
	}
	/**
	 * Lee un archivo planto, generando un Builder substituyendo Tabuladores por un espacio en blanco
	 * @param fullPath
	 * @return
	 */
	public static StringBuilder getBuilderNoTabsFile(String fullPath, String stCharset){
		StringBuilder sb = new StringBuilder();
		if(stCharset==null){
			stCharset=defCharset;
		}
		try 
	    {
			BufferedReader infile = new BufferedReader(
	        		 new InputStreamReader(
	                         new FileInputStream(fullPath), stCharset));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
	        	String unTabedLine = StringUtily.removeTabs(strLine).concat(" ");
	            sb.append(unTabedLine);
//	            nLines++;
	        }
	        infile.close();	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
	    return sb;
	}
	
	public static List<String> getLinesFile(String fullPath){
		return getLinesFile(fullPath, defCharset);
	}
	/**
	 * Lee un archivo planto, generando una Lista de lineas
	 * @param fullPath
	 * @return
	 */
	public static List<String> getLinesFile(String fullPath, String stCharset){
		if(stCharset==null){
			stCharset=defCharset;
		}
		List<String> lsLine = new ArrayList<String>();
		try 
	    {
			BufferedReader infile = new BufferedReader(
	        		 new InputStreamReader(
	                         new FileInputStream(fullPath), stCharset));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
//	        	System.out.println(strLine);
	        	lsLine.add(strLine);
	        }
	        infile.close();	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
	    return lsLine;
	}
	
	
	/**
	 * Escribe una cadena de texto en un archivo
	 * @param filePath ruta, nombre y extension  del archivo
	 * @param texto Cadena a agregar
	 * @param append agrega o reemplaza (true/false)
	 */
	public static void writeStringInFile(String filePath, String texto, boolean append ){
		
		BufferedWriter bufferedWriter;
		if(null == texto){
			return;
		}
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filePath, append));
//			bufferedWriter.newLine();	//Agrega un salto de linea
			bufferedWriter.write(texto);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Genera y obtiene un archivo desde StringBuffer
	 * @param stBuff
	 * @param stFileName
	 * @return
	 */
	public static File getFileFromStBff(StringBuffer stBuff, String stFileName) {
		File retFile = null;
		 try 
		    {
		        BufferedWriter outfile = 
		          new BufferedWriter(new FileWriter(stFileName ));//"/LocalFTP/sql/" + stFileName ));
		        outfile.write(stBuff.toString());
		        outfile.close();		        
		        retFile = new File(stFileName);//stDownlFtpFiles + fileName);
		    }
		 catch (Exception e) 
		    {	e.printStackTrace();	}
		 return retFile;
	}
	
	/**
	 * Obtiene un arreglo con todos los apuntadores de Archivo
	 * @param dirPath
	 * @return
	 */
	public static File[] getFilesInDir(String dirPath) {
		File[] files = null;
		try{
			File dir = new File(dirPath);
			FileFilter fileFilter = new FileFilter() {
		        public boolean accept(File file) {
		            return file.isFile();
		        }
		    };
		    files = dir.listFiles(fileFilter);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	    
		return files;
	}
	
	/**
	 * Agrega una linea a un archivo por medio del PrintStream (prueba)
	 * @param fileDir
	 * @param fileName
	 */
	public static void appendPrintStream(String fileDir, String fileName, String texto){
			java.io.PrintStream ps;			
			try {
				ps = new java.io.PrintStream( new FileOutputStream(fileDir
						+ fileName,true));
				ps.println(texto);
				ps.close();
			} catch (FileNotFoundException e) {			
				e.printStackTrace();
			}
	}
	
	/**
	 * Lee un archivo plano, y lo formatea para mostrarse como contenido en un StringBuilder 
	 * @param dir
	 * @param inFileName
	 */
	public static void printAsStBuffer(String dir, String inFileName){
		int nLines = 0;
		String prefx = ".append(\"";
		String postfx = " \")";
		
		try 
	    {
	        BufferedReader infile = new BufferedReader(new FileReader(dir + inFileName));
	        String strLine;	        
			System.out.println("StringBuilder sqlB = new StringBuilder(\"\");");			
			System.out.print("sqlB");
	        while ((strLine = infile.readLine()) != null) 
	        {
	            System.out.println(prefx + strLine + postfx);	            
	            nLines++;
	        }
	        System.out.println("; \n sql = sqlBuffer.toString();  ");	        
	        infile.close();
	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
		
		
	    if(nLines!=0)
	    	System.out.println("<Fin>Numero de Lineas Leidas: " + nLines + "</fin>");
	    else
	    	System.out.println("Archivo Vacio: " + nLines + " lineas");
	}
	
	
	/**
	 * Convierte un archivo con sql en un fragmento Java que contiene el texto en un StringBuilder
	 * @param dir
	 * @param inFileName
	 * @param outFileName
	 */
	public static void expAsJavaStBuilder(String dir, String inFileName, String outFileName){
		int nLines = 0;
		String prefx = "\t\t.append(\"";
		String postfx = " \")";
		String oldSt = "aforesis.";
		String newSt = " \" + prefijoTablas + \"";
		
		try 
	    {
	        BufferedReader infile = new BufferedReader(new FileReader(dir + inFileName));
	        String strLine;
	        StringBuilder strBuilder = new StringBuilder("StringBuilder sqlB = new StringBuilder(\"\");"+ "\n");
			System.out.println("StringBuilder sqlB = new StringBuilder(\"\");");
			
			System.out.print("sqlB");
	        while ((strLine = infile.readLine()) != null) 
	        {
	        	strLine = strLine.replace(oldSt, newSt);
	        	if(nLines==0){
	        		strBuilder.append("\tsqlB.append(\"").append(strLine + postfx+ "\n");
	        	}else{	        		
		            System.out.print(prefx + strLine + postfx+ "\n");
		            strBuilder.append(prefx + strLine + postfx + "\n" );
	        	}
	        	
	            nLines++;
	        }
	        System.out.println("; \n sql = sqlBuffer.toString();  ");
	        strBuilder.append("; \n sql = sqlBuffer.toString();  "+ "\n");
	        infile.close();	        
	        
	        
	        BufferedWriter outfile =  null;
			try 
		    {
		        outfile = 
		          new BufferedWriter(new FileWriter(dir + outFileName));
		        outfile.write(strBuilder.toString());
		        outfile.close();
		       // created = true;
		    }catch (Exception e) 
		    {        	e.printStackTrace();	    }
		    finally{
		    	if(outfile!=null){
		    		try{
		    			outfile.close();
		    		}catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    }
	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
		
		
	    if(nLines!=0)
	    	System.out.println("<Fin>Numero de Lineas Leidas: " + nLines + "</fin>");
	    else
	    	System.out.println("Archivo Vacio: " + nLines + " lineas");
		
		
	}
	/**
	 * Metodo que consiste en dos partes:
	 * 1. Obtiene archivo y lo transforma en arreglo de byte's
	 * 2. a partir del byte[], genera un nuevo archivo
	 * *probado para txt, pdf, zip
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void file2Bytes(String fileName)throws FileNotFoundException, IOException {
        File file = new File(defaultDir+ fileName);
        
        // Get the size of the file
        long length = file.length();
        System.out.println("DEBUG: Length of " + file + " is " + length + "\n");
 
        @SuppressWarnings("resource")
		InputStream fis = new FileInputStream(file);
        System.out.println(file.exists() + "!!");        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            throw ex;
        }
        byte[] bytes = bos.toByteArray();
 
        
        //*** 2a PARTE: CREAR UN ARCHIVO A PARTIR DE LOS BYTE's
        //below is the different part
        File someFile = new File(defaultDir+"CopyOF"+ fileName);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
	
	
	
	public static void main(String[] args) {
		//createTestFile();
		//printFileInfo(defaultDir+ "salida.txt");
		//duplicate(defaultDir+ "File");
//		readFile(defaultDir+ "File");
		//delFile(defaultDir+"salida_copy.txt");	
		
//		String unScaped = "\"S.D. INDEVAL, S.A. DE C.V.\"";		
//		String scaped = unScaped.replace("\"", "'");
//		System.out.println(scaped);
//		
//		String comeado = "abc,123,ABC,987";
//		scaped = comeado.replace(",", "");
//		System.out.println(scaped);
		
//		printAsStBuffer(defaultDir, "query2Java.txt");
//		expAsJavaStBuilder(defaultDir, "query2Java.txt", "javaSQL.txt");
		
		/*//pruebas para listar archivos y/o subdirectorios
		System.out.println("contenido:  ");
		listContentOFDir(defaultDir);
		
		System.out.println(".................\n directorios:  ");
		listDirsInDir(defaultDir);
		System.out.println(".................\n Archivos:  ");
		listFilesInDir(defaultDir);		//*/
		
		/*
		File[] archivos = getFilesInDir("C:/Importacion/Indeval");
		System.out.println("# archivos: " + archivos.length );
		for(int i=0;i<archivos.length;i++){
			System.out.print("moviendo " + archivos[i].getName());
			boolean moved = moveFile("C:/Importacion/Historico", archivos[i]);
			System.out.println("\t " + moved);
		} //*/
		
		lineasACatalogo(2);
		
	}
	
	private static void lineasACatalogo(int tipo){
		String txtPaises = "/home/dothr/Documents/TCE/ListaPaises.txt";
		int nLines = 0, indice = 1;
		try 
	    {
	        BufferedReader infile = new BufferedReader(new FileReader(txtPaises));
	        String strLine;
	        if(tipo == 1){ //Json => Paises
	        	System.out.println("[\n    {\n        \"Pais\": {\n");
		        while ((strLine = infile.readLine()) != null) 
		        {
		            System.out.println("            \""+strLine+"\": "+indice+",");
		            indice++;
		            nLines++;
		        }
		        System.out.println("        },");
	        }else{
	        	//Insert into PAIS (ID_PAIS,NOMBRE,ESTATUS_REGISTRO) values (1,'México',true);
	        	System.out.println("/* table Insert PAIS */");
		        while ((strLine = infile.readLine()) != null) 
		        {
		            System.out.println(" Insert into PAIS (ID_PAIS,NOMBRE,ESTATUS_REGISTRO) values ("+ indice +",'"+strLine+"',true);");
		            indice++;
		            nLines++;
		        }
	        }
	        
	        infile.close();	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
	    if(nLines!=0)
	    	System.out.println("<Fin>Numero de Lineas Leidas: " + nLines + "</fin>");
	    else
	    	System.out.println("Archivo Vacio: " + nLines + " lineas");
		
		
	}
	
	
	
}
