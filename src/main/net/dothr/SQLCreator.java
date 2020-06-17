package net.dothr;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import utily.FileUtily;

public class SQLCreator {

	private static String SQL_INS_PERMISO = "INSERT INTO public.permiso("+
            " id_permiso, id_permiso_relacionada, contexto, valor, descripcion, id_tipo_permiso)"+
            "\n VALUES \n";
            //"(?, ?, ?, ?, ?, ?);"
	private static String SQL_INS_TipoRELPERM = "INSERT INTO public.tipo_relacion_permiso("+
            " id_tipo_relacion_permiso, id_tipo_relacion, id_permiso, activo) "+
            "\n VALUES \n"; //(?, ?, ?, ?);
            
	
	public static void main(String[] args) {
//		creaInsertsAreaPersona();
//		creaInsPermiso();
		creaTRPermiso();
	}
	
	public static void creaTRPermiso(){
		StringBuilder sql = new StringBuilder(SQL_INS_TipoRELPERM);
		String fileTipoRelPermisoCsv = "/home/dothr/Documents/SVN/dhr_Otros/Data/Postgre/loads/csv/tipo_relacion_permiso.csv";
		List<String> lsLinea = FileUtily.getLinesFile(fileTipoRelPermisoCsv, null);
		Iterator<String> itLinea = lsLinea.iterator();
		String linea;
		List<String> items;
		while(itLinea.hasNext()){
			linea = itLinea.next();
			items = Arrays.asList(linea.split("\\s*;\\s*"));
			
			sql.append("(")
			.append(items.get(0)).append(",") //id_tipo_relacion_permiso
			.append(items.get(1)).append(",")	//id_tipo_relacion
			.append(items.get(2)).append(",")	//id_permiso
			.append(items.get(3).equals("t")?"true":"false")	//activo
			.append( itLinea.hasNext()?"),":");" ).append("\n");	
		}
		System.out.println("Se crea archivo SqL: " + "/home/dothr/Documents/TCE/TRPpermiso.sql" );
		FileUtily.writeStringInFile("/home/dothr/Documents/TCE/TRPpermiso.sql", sql.toString(), false);
	}
	
	public static void creaInsPermiso(){
		StringBuilder sql = new StringBuilder(SQL_INS_PERMISO);
		String filePermisoCsv = "/home/dothr/Documents/SVN/dhr_Otros/Data/Postgre/loads/csv/permiso.csv";
		List<String> lsLinea = FileUtily.getLinesFile(filePermisoCsv, null);
		Iterator<String> itLinea = lsLinea.iterator();
		String linea;
		List<String> items;
		while(itLinea.hasNext()){
			linea = itLinea.next();
			items = Arrays.asList(linea.split("\\s*;\\s*"));
			
			sql.append("(")
			.append(items.get(0)).append(",") //idPermiso
			.append(items.get(1).equals("")?"null":items.get(1)).append(",")	//idPermisoRelac
			.append(items.get(2).equals("")?"null":"'"+items.get(2)+"'").append(",")	//Contexto
			.append(items.get(3).equals("")?"null":"'"+items.get(3)+"'").append(",")	//Valor
			.append(items.get(4).equals("")?"null":"'"+items.get(4)+"'").append(",")	//Descripcion
			.append(items.get(5))	//idTipoPermiso
			.append( itLinea.hasNext()?"),":");" ).append("\n");	
		}
		System.out.println("Se crea archivo SqL: " + "/home/dothr/Documents/TCE/permiso.sql" );
		FileUtily.writeStringInFile("/home/dothr/Documents/TCE/permiso.sql", sql.toString(), false);
	}
	public static void creaInsertsAreaPersona(){
		String fileAreaP = "/home/dothr/Documents/TCE/AP.csv";
		StringBuilder sql = new StringBuilder("INSERT INTO area_persona (id_area_persona, id_area, id_persona) \n VALUES \n");
		List<String> lsLinea = FileUtily.getLinesFile(fileAreaP, null);
		Iterator<String> itLinea = lsLinea.iterator();
		String linea;
		String areaIt;
		int id = 1;
		while(itLinea.hasNext()){
			linea = itLinea.next();
			List<String> items = Arrays.asList(linea.split("\\s*;\\s*"));
			areaIt = items.get(2);
			List<String> areas = Arrays.asList(areaIt.split("\\s*,\\s*"));
			System.out.println(items.get(0)+"> areas: " + areas );	
			for(int x=0; x<areas.size();x++){
				sql.append("(").append(id++).append(",")
				.append(areas.get(x).replace("\"", "")).append(",")
				.append(items.get(1)).append("),\n");
			}			
		}
		
		FileUtily.writeStringInFile("/home/dothr/Documents/TCE/apersona.sql", sql.toString(), false);
		
		//
	}
	
}

