package com.fotos;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import utily.FileUtily;
import utily.JsonUtily;

public class EnlistaFotos {

	
	private static final String shFileName = "shRename.sh";
	private static final String jsonMemeWS_Name = "twitterMemeWeb.json";
	
	private static final String listaMeme = "/home/dothr/Documents/Personal/Pictures/lista.txt"; //ls -p | grep -v / > lista.txt
	private static final String directorioMeme = "/home/dothr/Documents/Personal/Pictures/TwitMeme/";
	private static final String jsonMEME_Docs = "/home/dothr/Documents/Personal/Pictures/twitterMeme.json";
	
	private static final String directorioWeb = "/home/dothr/app/webServer/angularDemos/twitter/";
	private static final String jsonRenameWebServer = directorioWeb+"tRename.json";
			//"/home/dothr/app/webServer/test/twitter/tRename.json";
	private static final String jsonMEME_WebServer = directorioWeb+jsonMemeWS_Name;
	private static final String pathSHDoc_Out = "/home/dothr/Documents/Personal/Pictures/TwitMeme/";
	private static final String pathSHWeb_Out = directorioWeb+"img/";
	
	
	public static void main(String[] args) {
		try {
			//0.Copiar imagenes a webServer /home/dothr/app/webServer/test/twitter/img/
			
			
			//1. Se modifican con el cliente en webServer y se guarda en tRename.json
			genRenombraArchivos();
			//2.Ejecutar ambos .sh
			//2b. Para eliminar archivos repetidos, lista con rm $HOME/Documents/Personal/Pictures/TwitMeme/
			
			//3.Ejecutar los procesos:
//			if(generaTxtLista()){ if(generaJsonMeme()){ compareDetalles();}}
			//regresar a 1
			
			/*
			generaTxtLista();
			generaJsonMeme();
			compareDetalles();//*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean generaTxtLista() throws Exception {
		List<String> lsArchivos = FileUtily.listFilesInDir(directorioMeme);
		Iterator<String> itArchivo = lsArchivos.iterator();
		StringBuilder sb = new StringBuilder();
		String fileName;
		while(itArchivo.hasNext()){
			fileName = itArchivo.next();
			if(!fileName.startsWith(".") && !fileName.startsWith(shFileName)){
				sb.append(fileName).append("\n");
				//System.out.println(fileName);
			}
		}
		FileUtily.writeStringInFile(listaMeme, sb.toString(), false);
		System.out.println("Se escribe archivo: "+ listaMeme);
		return true;
	}
	
	/**
	 * Genera el Archivo de Json para webServer a partir de la lista.txt
	 * @throws Exception
	 */
	public static boolean generaJsonMeme() throws Exception {
		System.out.println("<generaJson>");
		
		
		String urlRoot = "img/";
		
		List<String> lsLine = FileUtily.getLinesFile(listaMeme, null);
		Iterator<String> itLine = lsLine.iterator();
		
		JSONArray jsArr = new JSONArray();
		JSONObject json;
		
		String linea;
		while(itLine.hasNext()){
			linea = itLine.next();
			json = new JSONObject();
			System.out.println(linea);
			json.put("imageUrl", urlRoot+linea);
			json.put("fileName", linea);
			json.put("tema", "");
			json.put("cuenta", "");
			json.put("snippet", linea);
			jsArr.put(json);
		}
		
		
		FileUtily.writeStringInFile(jsonMEME_Docs, jsArr.toString(), false);
		System.out.println("Se escribe archivo: "+ jsonMEME_Docs);
		return true;
	}
	

	/**
	 * Inserta tema y descripcion  cuando hay un rename
	 * @throws Exception
	 */
	public static void compareDetalles() throws Exception {
		System.out.println("Compara Detalles entre \n"+jsonMEME_Docs + "\n y \n"+jsonRenameWebServer+"\n");
		JSONArray jsArrDoc, jsArrWeb;
		JSONObject json, jsonTmp;
		jsArrDoc = new JSONArray(FileUtily.getBuilderNoTabsFile(jsonMEME_Docs, null).toString());
		jsArrWeb = new JSONArray(FileUtily.getBuilderNoTabsFile(jsonRenameWebServer, null).toString());
		
		String docFileName, webFileName; //fileName/newFileName
		System.out.println("Se iteran "+jsArrDoc.length()+ " elementos");
		boolean encontrado = false;

		int x=0, iWeb=0;
		
		for(x=0; x<jsArrDoc.length(); x++){
			json = jsArrDoc.getJSONObject(x);
			iWeb =0;
			encontrado = false;
			docFileName = json.getString("fileName");
			//1. recorre los elementos que ya existen (Doc.fileName == Web.fileName)
			do{
//				System.out.println("iWeb "+ iWeb);
				jsonTmp = jsArrWeb.getJSONObject(iWeb);
				webFileName = jsonTmp.getString("fileName");
				if(docFileName.equals(webFileName)){
					if(jsonTmp.has("tema")){
						json.put("tema", jsonTmp.getString("tema"));
					}
					if(jsonTmp.has("cuenta")){
						json.put("cuenta", jsonTmp.getString("cuenta"));
					}
					if(jsonTmp.has("snippet")){
						json.put("snippet", jsonTmp.getString("snippet"));
					}
					encontrado = true;
				}
				iWeb++;
			}while(!encontrado && iWeb<jsArrWeb.length());
//			System.out.println("no se encontro con el mismo nombre, se procede a nuevo Nombre");
			if(!encontrado){
				iWeb =0;
				do{
					jsonTmp = jsArrWeb.getJSONObject(iWeb);
					if(jsonTmp.has("newFileName")){

						webFileName = jsonTmp.getString("newFileName");
						//quitar extension del docFileName
						if(docFileName.startsWith(webFileName)){
							System.out.println("Se encontro info para archivo modificado: "+webFileName);
							if(jsonTmp.has("tema")){
								json.put("tema", jsonTmp.getString("tema"));
							}
							if(jsonTmp.has("cuenta")){
								json.put("cuenta", jsonTmp.getString("cuenta"));
							}
							if(jsonTmp.has("snippet")){
								json.put("snippet", jsonTmp.getString("snippet"));
							}
							encontrado = true;
						}
					}
					iWeb++;
				}while(!encontrado && iWeb<jsArrWeb.length());
			}
			
		}
		
		//Reescribir el archivo de json
		//FileUtily.writeStringInFile(jsonMemeDocs, jsArrDoc.toString(), false);
		FileUtily.writeStringInFile(jsonMEME_Docs, 
				JsonUtily.formatJson(jsArrDoc.toString()), false);
		System.out.println("Se reescribe archivo: "+ jsonMEME_Docs +"\n [Copiar a webServer] ");
		if(FileUtily.renameFile(directorioWeb, jsonMemeWS_Name, jsonMemeWS_Name.replace(".json", ".old.json"))){
			System.out.println("Se genera nuevo archivo: "+ jsonMemeWS_Name +"\n [y se renombra anterior] ");
			FileUtily.writeStringInFile(jsonMEME_WebServer, 
					JsonUtily.formatJson(jsArrDoc.toString()), false);
		}
		
		
	}
	
	
	public static void genRenombraArchivos() throws Exception {	
		
		String stJsonEntrada, oldName, newName, extension;
		StringBuilder sb = new StringBuilder();
		
		stJsonEntrada = FileUtily.getBuilderNoTabsFile(jsonRenameWebServer, null).toString();
		//System.out.println("stJsonEntrada: "+stJsonEntrada);
		JSONArray jsArrTwMeme = new JSONArray(stJsonEntrada);
		JSONObject json;
		
		System.out.println("jsArrTwMeme.length "+jsArrTwMeme.length());
		int cambiados = 0;
		for(int x =0; x<jsArrTwMeme.length();x++){
			json = jsArrTwMeme.getJSONObject(x);
			
			if(json.has("newFileName") && 
					(json.has("oldFileName") || json.has("fileName"))){
				oldName = json.has("oldFileName")?
						json.getString("oldFileName"):json.getString("fileName");
				newName = json.getString("newFileName");
				extension = oldName.substring(oldName.lastIndexOf("."), oldName.length());
				
				//Quitandoles la extension:
				oldName = oldName.substring(0, oldName.lastIndexOf("."));
				newName = newName.substring(0, newName.lastIndexOf("."));
				
				if(!oldName.equals(newName)){
					sb.append("mv ").append(oldName).append(extension).append(" ")
					.append(newName).append(extension).append("\n");
					cambiados++;
				}
			}
		}
		if(cambiados>0){
			System.out.println("se renombran "+cambiados+" archivos\n (Si solo se cambio tema,desc,cuenta, el proceso de compare lo añade)");
			FileUtily.writeStringInFile(pathSHDoc_Out+shFileName, sb.toString(), false);
			FileUtily.writeStringInFile(pathSHWeb_Out+shFileName, sb.toString(), false);
			System.out.println("Se escriben archivos SH [Se hace ejecutable y ejecuta ./"+shFileName+"]:\n\n"+
			"chmod +x "+pathSHDoc_Out+shFileName +"\nchmod +x "+pathSHWeb_Out+shFileName );
			
			System.out.println("cd "+pathSHDoc_Out+ "\n./"+shFileName
					+"\n\n cd "+pathSHWeb_Out +"\n./"+shFileName);
			
		}else{
			System.out.println("NO hay nombres de archivo a modificar.\n tema|desc|cuenta se modifican en proceso compareDetalles");
		}
		
		
	}
}
