package com.itunes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utily.JsonUtily;
import utily.StringUtily;

public class ItunesFormater {
	
	public static String locFormat(String location){
		String nwLoc = StringUtily.replaceUnicode(location);
		return nwLoc.replace("file://localhost", "").replace("/X:/", "").replace("/E:/", "");
	}
	
	/**
	 * Realiza un formateo para identar una Cadena Json
	 * @param jsonString
	 * @return
	 */
	public static String formtJsonByGoogle(StringBuilder sbJsonString){		
		return JsonUtily.formatJson(sbJsonString.toString());
	}
	
	/**
	 * Da formato al Objeto JSON en el orden esperado
	 * @param json
	 * @param tab
	 * @return
	 * @throws JSONException
	 */
	public static StringBuilder formtPlayList(JSONObject json) throws JSONException {
		return formtPlayList(json, "");
	}
	/**
	 * Da formato al Objeto JSON en el orden esperado, con el separador tab entre elementos
	 * @param json
	 * @param tab
	 * @return
	 * @throws JSONException
	 */
	public static StringBuilder formtPlayList(JSONObject json, String tab) throws JSONException {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if(json.has("Name")){ sb.append(tab).append("\"Name\":\"").append( json.getString("Name") ).append("\""); }	//.append("\n")
		if(json.has("Playlist ID")){ sb.append(",").append(tab).append("\"Playlist ID\":\"").append( json.getString("Playlist ID") ).append("\""); }	//.append("\n")
		if(json.has("Playlist Persistent ID")){ sb.append(",").append(tab).append("\"Playlist Persistent ID\":\"").append( json.getString("Playlist Persistent ID") ).append("\""); }//.append("\n")
		if(json.has("Parent Persistent ID")){ sb.append(",").append(tab).append("\"Parent Persistent ID\":\"").append( json.getString("Parent Persistent ID") ).append("\""); }//.append("\n")
		if(json.has("parentName")){ sb.append(",").append(tab).append("\"parentName\":\"").append( json.getString("parentName") ).append("\""); }//.append("\n")
		
		if(json.has("Distinguished Kind")){ sb.append(",").append(tab).append("\"Distinguished Kind\":\"").append( json.getString("Distinguished Kind") ).append("\""); }//.append("\n")
		if(json.has("Movies")){ sb.append(",").append(tab).append("\"Movies\":\"").append( json.getString("Movies") ).append("\""); }//.append("\n")
		if(json.has("TV Shows")){ sb.append(",").append(tab).append("\"TV Shows\":\"").append( json.getString("TV Shows") ).append("\""); }//.append("\n")
		if(json.has("Podcasts")){ sb.append(",").append(tab).append("\"Podcasts\":\"").append( json.getString("Podcasts") ).append("\""); }//.append("\n")
		if(json.has("iTunesU")){ sb.append(",").append(tab).append("\"iTunesU\":\"").append( json.getString("iTunesU") ).append("\""); }//.append("\n")
		if(json.has("Audiobooks")){ sb.append(",").append(tab).append("\"Audiobooks\":\"").append( json.getString("Audiobooks") ).append("\""); }//.append("\n")
		if(json.has("Books")){ sb.append(",").append(tab).append("\"Books\":\"").append( json.getString("Books") ).append("\""); }//.append("\n")
		if(json.has("Folder")){ sb.append(",").append(tab).append("\"Folder\":\"").append( json.getString("Folder") ).append("\""); }//.append("\n")
		
		// Listas autogeneradas por Itunes:
		if(json.has("Smart Info")){ sb.append(",").append(tab).append("\"Smart Info\":\"").append( json.getString("Smart Info") ).append("\""); }//.append("\n")
		if(json.has("Smart Criteria")){ sb.append(",").append(tab).append("\"Smart Criteria\":\"").append( json.getString("Smart Criteria") ).append("\""); }//.append("\n")
		
		if(json.has("All Items")){ sb.append(",").append(tab).append("\"All Items\":\"").append( json.getString("All Items") ).append("\""); }//.append("\n")
		if(json.has("Playlist Items")){ sb.append(",").append(tab).append("\"Playlist Items\":").append( json.get("Playlist Items").toString() ); }
		sb.append(" }");
//		System.out.println(sb.toString());
		return sb;
	}
	
	/**
	 * Da formato al Arreglo de Salida, a manera de que sea un JSON por linea
	 * @param jsListArray
	 * @return
	 * @throws JSONException
	 */
	public static StringBuilder formtPlayList(JSONArray jsListArray) throws JSONException{
		StringBuilder sb = new StringBuilder();
		JSONObject json = null;
		sb.append("[\n");
		for(int x=0; x<jsListArray.length();x++){
			
			json = jsListArray.getJSONObject(x);
			sb.append("  ").append(formtPlayList(json,"  "));
			sb.append(x==jsListArray.length()-1?"\n":",\n"); 
		}
		sb.append("]");
		
		return sb;
	}
	public static StringBuilder formtSimpleJsonBr(JSONArray jsArray) {
		StringBuilder sb = new StringBuilder();
		sb.append("[\n");
		try{
//			JSONObject json = null;
			for(int x=0; x<jsArray.length();x++){
				sb.append(jsArray.getJSONObject(x).toString());
				sb.append(x==jsArray.length()-1?"\n":",\n");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		sb.append("]");
		return sb;
	}
}
