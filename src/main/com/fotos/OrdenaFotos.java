package com.fotos;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

//import org.apache.log4j.Logger;

import utily.DateUtily;
import utily.FileUtily;
/**
 * Class developed for organize and rename Photos taken with celphone and Camera
 * [Clase para organizar y renombrar fotografías tomadas con celular y Cámara]
 * @author dothr
 *
 * Steps: 
 * **Tip: realize this process BEFORE edit photos
 * 1. Copy the Folder with the photos on it, and type the path into fotosPath variable
 * 2. Run csvInfoFotos method (parameter hours2Add for add hours to creation time) 
 * 	to generate <b>fotos.csv</b>
 * 3. Open csv file, re-order by the appropiate column (3rd) and save it
 * 4. Run createCvsRename to create a file wich contains the old and the new names 
 * 5. After check the correct naming, Run renameByList to rename all files in the folder
 */
public class OrdenaFotos {

	protected static String fotosPath = "/home/dothr/Pictures/Camara/2018-03-30.Hueytamalco/"; 
			//"/home/dothr/Pictures/Camara/2016-12-03.Xinantecatl/";
				//"/home/dothr/Documents/TCE/TMP/ordenaFoto/";
	protected static String csvLista = "fotos.csv";
	protected static String csvToRename = "fotos2renom.csv";
	protected static String csvReverse = "reverse.csv";
	protected static String pattern = "YYYYMMdd_HHmmss" ;
	
	//static Logger log4j = Logger.getLogger( OrdenaFotos.class );
			
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		csvInfoFotos(0); // 0->5 [Utilizar repositorio de fotos sin editar]
		//Reordenar por fecha
//		createCvsRename(","); //; | ,   [ejecutarlo apuntando hacia fotos modificadas]
//		renameByList();
		

//		//renameFotosBy("Xinantecatl");
	}

	
//	/**
//	 * Lee el contenido del directorio para generar una lista de elementos con su fecha de creacion 
//	 * para ordenarla manualmente
//	 */
//	protected static void csvInfoFotos(){
//		List<String> lsFiles = getLsFiles();
//		List<String> lsNwNames = new ArrayList<String>();
//		StringBuilder sbPhoto = new StringBuilder();
//		System.out.println("lsFiles.size: " + lsFiles.size() );
//		Iterator<String> itFile = lsFiles.iterator();
//		String fileName;
//		int total = 0;
//		while(itFile.hasNext()){
//			fileName = itFile.next();
//			if(!fileName.startsWith(csvLista.substring(0,3))){
//				PhotoVo vo = setInfo(fotosPath, fileName);
//				System.out.println(vo.getNombre() +  " ; " +vo.getExtension()+";"+ vo.getCreatTime() + " : "+ vo.getStFCreacion() + "\n");
//				vo.setNewName(checkConsecutivo(vo.getStFCreacion(), lsNwNames));
//				
//				lsNwNames.add(vo.getNewName());
//				
//				sbPhoto.append(vo.getNombre())
//				.append(";").append(vo.getNewName())	//vo.getStFCreacion())
//				.append(";").append(vo.getCreatTime())
//				.append(";").append(vo.getExtension())
//				.append(itFile.hasNext()?"\n":"");
//				total++;
//			}
//		}
//		System.out.println("Se iteraron "+total+" nombres de archivo");
//		FileUtily.writeStringInFile(fotosPath+csvLista, sbPhoto.toString(), false);
//	}
	
	/**
	 * Lee el contenido del directorio para generar una lista de elementos con su fecha de creacion 
	 * para ordenarla manualmente
	 */
	protected static void csvInfoFotos(int hours2Add){
		List<String> lsFiles = getLsFiles();
		List<String> lsNwNames = new ArrayList<String>();
		StringBuilder sbPhoto = new StringBuilder();
		System.out.println("lsFiles.size: " + lsFiles.size() );
		Iterator<String> itFile = lsFiles.iterator();
		String fileName;
		int total = 0;
		while(itFile.hasNext()){
			fileName = itFile.next();
			if(!fileName.startsWith(csvLista.substring(0,3)) 
					&& !fileName.startsWith(csvReverse.substring(0,3))){
				PhotoVo vo = null;
//				if(fileName.startsWith("D")){
//					vo = setInfo(fotosPath, fileName, hours2Add);
//				}else{
//					vo = setInfo(fotosPath, fileName, 0);
//				}
				vo = setInfo(fotosPath, fileName, hours2Add);
				System.out.println(vo.getNombre() +  " ; " +vo.getExtension()+";"+ vo.getCreatTime() + " : "+ vo.getStFCreacion() + "\n");
				vo.setNewName(checkConsecutivo(vo.getStFCreacion(), lsNwNames));
				
				lsNwNames.add(vo.getNewName());
				
				sbPhoto.append(vo.getNombre())
				.append(";").append(vo.getNewName())	//vo.getStFCreacion())
				.append(";").append(vo.getCreatTime())
				.append(";").append(vo.getExtension())
				.append(itFile.hasNext()?"\n":"");
				total++;
			}
		}
		System.out.println("Se iteraron "+total+" nombres de archivo");
		FileUtily.writeStringInFile(fotosPath+csvLista, sbPhoto.toString(), false);
	}
	
	protected static String checkConsecutivo(String nombre, List<String> lsNames){
		boolean repetido = false;
		String newName = "";
		int subIndex = 1;
		newName = nombre;
		do{
			repetido=false;			
			for(int x=0;x<lsNames.size();x++){
				if(newName.equals(lsNames.get(x))){
					System.out.println("Encontro repetido: " + newName );
					repetido=true;
					newName=nombre+"."+subIndex;
					subIndex++;
				}
			}
		}while(repetido);
		return newName;		
	}
	
	/**
	 *  Verifica cuales de los nuevos nombres se encuentran repetidos
	 * 
	 */
	public static void createCvsRename(String separator){
		List<String> lsLine = FileUtily.getLinesFile(fotosPath+csvLista, null);
		System.out.println("lsLine.size "+lsLine.size());
		StringBuilder sbPhoto = new StringBuilder();
		
		Iterator<String> itLine = lsLine.iterator();
		String line;
		int total = 0;
		while(itLine.hasNext()){
			line = itLine.next();
			PhotoVo vo = new PhotoVo(line, separator);
//			if(vo.getNombre().startsWith("D")){//Para solo tomadas con la camara
				sbPhoto.append(vo.getNombre())
				.append(";").append(vo.getNewName()).append("_NK").append(vo.getExtension())
				.append(itLine.hasNext()?"\n":"");
				total++;
//			}
		}
		System.out.println("Se iteraron "+total+" elementos a "+csvToRename);
		FileUtily.writeStringInFile(fotosPath+csvToRename, sbPhoto.toString(), false);
	}
	
//	/**
//	 * renombra los archivos dependiendo un archivo csv con nombre y nuevo nombre (Anterior)
//	 */
//	protected static void reverse(){
//		//csvReverse
//		StringBuilder sbRen = new StringBuilder("Original;nuevo\n");
//		List<String> lsLine = FileUtily.getLinesFile(fotosPath+csvReverse, null);
//		System.out.println("Se renombran " + lsLine.size() +" archivos");
//		Iterator<String> itLine = lsLine.iterator();
//		File fileTmp;
//		String line, name, newName;
//		while(itLine.hasNext()){
//			line = itLine.next();
//			name = line.substring(0,line.indexOf(","));
//			newName = line.substring(line.indexOf(",")+1,line.length());
//			System.out.println("name: "+name);
//			System.out.println("newName: "+newName);
//			try{
//				fileTmp= new File(fotosPath+name);
//				fileTmp.renameTo(new File(fotosPath, newName));
//				sbRen.append(name).append(";").append(newName).append("\n");
//			}catch (Exception e){
//				e.printStackTrace();
//				sbRen.append(name).append(";").append(newName).append(";ERROR").append("\n");
//			}
//			
//		}
//		FileUtily.writeStringInFile(fotosPath+"renamedRev.csv", sbRen.toString(), false);
//	}
	
	/**
	 * Renombra los archivos dependiendo la lista [Nombre, nombreNuevo] 
	 * en un archivo plano
	 */
	protected static void renameByList(){
		System.out.println(fotosPath+csvToRename);
		StringBuilder sbRen = new StringBuilder("Original;nuevo\n");
		List<String> lsLine = FileUtily.getLinesFile(fotosPath+csvToRename, null);
		int nArchs = lsLine.size(), correctos = 0;
		System.out.println("Se renombran " + nArchs +" archivos");
		Iterator<String> itLine = lsLine.iterator();
		File fileTmp;
		String line, name, newName, ext;
		List<String> items;
		while(itLine.hasNext()){
			line = itLine.next();
			//System.out.println(" "+line);
			items = Arrays.asList(line.split("\\s*;\\s*"));
			if(items.size()==2){
				name = items.get(0);
				newName = items.get(1);
				System.out.println(name + " > \t"+newName);
				try{
					fileTmp= new File(fotosPath+name);
					fileTmp.renameTo(new File(fotosPath, newName));
					sbRen.append(name).append(";").append(newName).append("\n");
					correctos++;
				}catch (Exception e){
					e.printStackTrace();
					sbRen.append(name).append(";").append(newName).append(";ERROR").append("\n");
				}
			}else{
				System.out.println("# Columnas incorrecto: "+items.size() + ", verifique separador: ;");
			}
		}
		System.out.println("\n nArchs: "+nArchs + ", Correctos: " + correctos +" ["+fotosPath+
				"]\nCon error: "+ (nArchs-correctos));
		FileUtily.writeStringInFile(fotosPath+"renamed.csv", sbRen.toString(), false);
	}

	
	
	
//	/**
//	 * Obtiene información de un archivo a partir de su ruta y nombre, 
//	 * devuelve un VO conteniendo la informacion requerida
//	 * @param path
//	 * @param name
//	 * @return
//	 */
//	private static PhotoVo setInfo(String path, String name){
//		PhotoVo vo = new PhotoVo(name);
//		
//		vo.setExtension(getExtension(name));
//		Path file = Paths.get(path+name);
//		try{
//			BasicFileAttributes attr =
//	                Files.readAttributes(file, BasicFileAttributes.class);
//
//			vo.setCreatTime(attr.creationTime().toMillis());
//			vo.setStFCreacion( DateUtily.longDate2String(attr.creationTime().toMillis(), pattern) );//TODO problema con el patron +12
//			vo.setStFechaMod( DateUtily.longDate2String(attr.lastModifiedTime().toMillis(), pattern ) );
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//        
//		return vo;
//	}
	
	/**
	 * Obtiene información de un archivo a partir de su ruta y nombre, 
	 * Agrega las horas determinadas
	 * devuelve un VO conteniendo la informacion requerida
	 * @param path
	 * @param name
	 * @param addHours
	 * @return
	 */
	private static PhotoVo setInfo(String path, String name, int addHours){
		PhotoVo vo = new PhotoVo(name);
		
		vo.setExtension(getExtension(name));
		Path file = Paths.get(path+name);
		try{
			BasicFileAttributes attr =
	                Files.readAttributes(file, BasicFileAttributes.class);

			vo.setCreatTime(attr. creationTime().toMillis());
			vo.setFechaCreacion(new java.util.Date(attr.creationTime().toMillis()));
			vo.setStFCreacion( DateUtily.addTime(vo.getFechaCreacion(), 0, 0, 0, addHours, 0, 0, pattern) );
			//vo.setStFechaMod( DateUtily.longDate2String(attr.lastModifiedTime().toMillis(), pattern ) );
		}catch (Exception e){
			e.printStackTrace();
		}
        
		return vo;
	}
	
	
	/**
	 * Lista de archivos en el directorio
	 * @return
	 */
	protected static List<String> getLsFiles(){
		
		return FileUtily.listFilesInDir(fotosPath);
	}
	
	protected static String getExtension(String name){
		return name.substring(name.lastIndexOf("."), name.length());
	}

}
