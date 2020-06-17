package com.itunes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONObject;

import utily.FileUtily;
import utily.StringUtily;

public class JsonToSql {
	
	private static final String JSONLIST_DIR = ConstantesItunes.JSONLIST_DIR;
	private static final String TRACKS_FILE = ConstantesItunes.TRACKS_FILE_NAME;
	
	
	public static void main(String[] args) {
		createSQLTracks();
	}
	/**
	 * Genera el archivo de INSERTS en SQL a partir del archivo TRACKS_FILE (tracksDB.json)
	 */
	public static void createSQLTracks(){
		System.out.println("<createSQLTracks>");
		String sqlFileName = "INSERT_TRACKS.sql";
		JSONObject json;
		String stJson;
		long nLines = 0, idDB =1;
		String CreateSQL = ";\n \n/*\n CREATE TABLE track_item (\n id_track_item bigint NOT NULL,\n itunes_id_track varchar(8),\n file_path varchar(300),\n descripcion varchar(150),"+
	"\n track_name varchar(150),\n track_artist varchar(150),\n track_number smallint,\n track_year smallint,\n disc_name varchar(150),\n disc_number smallint,\n album varchar(150)," +
	"\n id_genero bigint,\n json varchar(1000)\n ) ; \n " +
	"\n CREATE TABLE genero (id_genero bigint NOT NULL, descripcion varchar(50) ); INSERT INTO genero (id_genero, descripcion) VALUES (1,'Otro'), (2,'SoundTrack (OST)''), (3,'Rock'), (4,'Pop'), (5,'Electrónico');*/";

		try 
	    {
			File file = new File(JSONLIST_DIR+sqlFileName);
			if(file.exists()){
				file.delete();
			}
	        BufferedReader infile = new BufferedReader(new FileReader(JSONLIST_DIR+TRACKS_FILE));
	        String strLine;
	        
	        String sbSql ="INSERT INTO track_item (id_track_item, itunes_id_track, file_path, descripcion"+
	        	", track_name, track_artist, track_number, track_year, disc_name, disc_number, album, id_genero, json) VALUES\n";
	        FileUtily.writeStringInFile(JSONLIST_DIR+sqlFileName, sbSql, true);
	        boolean firstElem = true;
	        String coma = "";
	        while ((strLine = infile.readLine()) != null) 
	        {
	            System.out.println(strLine);
	            nLines++;
	            if(!strLine.startsWith("[") && !strLine.startsWith("]")){
	            	
	            	if(strLine.substring(strLine.length()-1,strLine.length()).equals(",")){
	            		stJson = strLine.substring(0, strLine.length()-1);
	            	}else{
	            		stJson = strLine;
	            	}
	            	json = new JSONObject(stJson);	            	
	            	FileUtily.writeStringInFile(JSONLIST_DIR+sqlFileName, coma+json2SQLInsert(json, idDB), true);
	            	if(firstElem){
	            		coma = ",\n";
	            		firstElem=false;
	            	}
	            	idDB++;
	            }
	        }
	        infile.close();	 
	        FileUtily.writeStringInFile(JSONLIST_DIR+sqlFileName, CreateSQL, true);
	    } 
	    catch (Exception e) 
	    {	//System.err.println("IOException "+e);
	    	System.out.println("nLines: " + nLines );
	    	e.printStackTrace();
	    }
	    if(nLines!=0)
	    	System.out.println("<Fin>Numero de Lineas Leidas: " + nLines + "</fin>");
	    else
	    	System.out.println("Archivo Vacio: " + nLines + " lineas");
	}
	
	/**
	 * Genera la instruccion Insert por cada elemento
	 * @param json
	 * @param idDb
	 * @return
	 */
	private static String json2SQLInsert(JSONObject json, Long idDb){
		StringBuilder sbSql = new StringBuilder("(");
		//Values:
		try{
			sbSql.append(idDb)
			.append(", ").append( txtData(json, "Track ID") ) //itunes_id_track ")
			.append(", ").append( txtData(json, "Location") ) //file_path varchar ")
			.append(", ").append( txtData(json, "dEsCriPcIon") ) //descripcion ")
			.append(", ").append( txtData(json, "Name") ) //track_name ")
			.append(", ").append( txtData(json, "Artist") ) //track_artist ")
			.append(", ").append( json.has("Track Number")?json.getString("Track Number"):"null" ) //track_number ")*
			.append(", ").append( json.has("Year")?json.getString("Year"):"null" ) //track_year ")*
			.append(", ").append( txtData(json, "Album Artist") ) //disc_name ")
			.append(", ").append( json.has("Disc Number")?json.getString("Disc Number"):"null" ) //disc_number ") *
			.append(", ").append( txtData(json, "Album") ) //album ")
			.append(", ").append( "1" ) //id_genero ")*
			.append(", '").append( json.toString().replace("'", "''")).append("' "); //json  ");
		}catch (Exception e){
			e.printStackTrace();
		}
		
		sbSql.append(")");
		return sbSql.toString();
	}
	
	/**
	 * Adecua el formato para insertar varchar SQL
	 * @param json
	 * @param param
	 * @return
	 */
	private static String txtData(JSONObject json, String param){
		String rep = "null";
		try{
			if(json.has(param)){
//				System.out.println("json.getString(param): " + json.getString(param));
//				System.out.println("Replaced? json.getString(param): " + json.getString(param).replace("'", "\\'"));
				rep = "'"+(json.getString(param).replace("'", "''") )+"'";
				return StringUtily.replaceUnicode(rep);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rep;
	}
	
	

}
