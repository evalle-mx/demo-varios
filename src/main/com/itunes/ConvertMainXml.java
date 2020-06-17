package com.itunes;

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utily.FileUtily;
import utily.StringUtily;

/**
 * Procesa un archivo XML de Itunes para extraer un archivo de canciones por ID (tracksDB.json) y N listas de canciones
 * en un archivo para cada una (ListaN.json)
 * @author dothr
 *
 */
public class ConvertMainXml {

	private static final String JSONLIST_DIR = ConstantesItunes.JSONLIST_DIR;//"/home/dothr/Music/ITUNES-/JSONlists/";
	// Archivo Completo Original
	private static final String TRACKS_FILE = ConstantesItunes.TRACKS_FILE_NAME;	//"tracksDB.json";
	
	
	public static void main(String[] args) {
		crawlItunesXML(ConstantesItunes.XmlItunesLibrary);
		
//		iterateTracksDB();
	}
	
	/*
	 * Archivo original tiene la siguiente estructura: 
	 * 
	 * <dict>
	 * 		...
	 * 		<key>Tracks</key>
	 * 		<dict>
	 * 			<key>1583</key>
				<dict>
					<key>Track ID</key><integer>1583</integer>
					...
					<key>Location</key><string>file://localhost/E:/Musica/Va.mp3</string>
					...
				</dict>
			</dict>
			<key>Playlists</key>
			<array>
			</array>
				<dict>
				<key>Name</key><string>Biblioteca</string>
	 * 
	 * dict1 = Tracks, dict2=
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * Procesa el archivo completo de Libreria de Itunes para generar Listas Musicales y un archivo de listado de canciones por ID
	 * @param xmlFile
	 */
	public static void crawlItunesXML(String xmlFile){
		System.out.println("xmlFile: " + xmlFile );
		try {
			File fXmlFile = new File(ConstantesItunes.ITUNES_DIR+xmlFile);	//"/home/dothr/Music/ITUNES-/pList.test.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName()); //plist
			NodeList pMainNodes = doc.getDocumentElement().getChildNodes();	//Todos los segmentos dict principal
			
			Node dNodeTracks=null, dNodePlayLists=null;
			
			for(int a = 0; a < pMainNodes.getLength(); a++) {
				Node dNode = pMainNodes.item(a);
				NodeList dDictElements = null;
				boolean isKey = true;
				String key = "";
				Node dNodeItem;
			    if(dNode.getNodeType() == Node.ELEMENT_NODE && dNode.getNodeName().equals("dict")) {
			    	dDictElements = dNode.getChildNodes();	//<key>Major Version</key><integer>1</integer> ...
			    	
			    	for(int b = 0; b < dDictElements.getLength(); b++) { 
			    		/* Examina los elementos del primer Nodo (dict) para obtener key/obj hasta llegar a Tracks y Playlists */ 
			    		dNodeItem = dDictElements.item(b);			    		
					    if(dNodeItem.getNodeType() == Node.ELEMENT_NODE){
//					    System.out.println("b: " + b + ", name: "+dNodeItem.getNodeName());
					    	if(isKey){ //Playlists
					    		key=dNodeItem.getTextContent();
					    	}
					    	else if(dNodeItem.getNodeName().equals("array") && key.equals("Playlists")){ //Valor (array)
					    		System.out.println("<processNodePList> item a dNodePlayLists : " + key);
					    		dNodePlayLists = dNodeItem;
					    	}
					    	else if(dNodeItem.getNodeName().equals("dict") && key.equals("Tracks")){ //Valor (array)
					    		System.out.println("<processNodePList> item a dNodeTracks : " + key);
					    		dNodeTracks = dNodeItem;
					    	}
						   	isKey=!isKey;
					    }
			    	}
			    }
			    //#text y otros no procesados:
			    else {
			    	if(dNode.getNodeType() == Node.ELEMENT_NODE){
			    		System.out.println("se omite nodo: " + dNode.getNodeName());
			    		}
			    }
			}//FIn de for de nodos
			
			/* SEGMENTO DE TRACKS (Canciones, info ) ==> TracksDB.json */
			if(dNodeTracks!=null){
				JSONArray jsTrackArray = getTrackArrayFromDict(dNodeTracks);
				
//				process.append("Escribiendo DB de canciones: ").append(TRACKS_FILE).append("\n");
				FileUtily.writeStringInFile(JSONLIST_DIR+TRACKS_FILE, ItunesFormater.formtSimpleJsonBr(jsTrackArray).toString(), false);
//				process.append(TRACKS_FILE).append("==> OK\n");
			}
			
			/* SEGMENTO DE PLAYLIST (Lista de canciones ) ==> Lista1.json, Lista2.json, ... ListaN.json */
			if(dNodePlayLists!=null){
//				System.out.println("dNodePlayLists.length: " + dNodePlayLists.getChildNodes().getLength());
				StringBuilder report = exportlistsArrayFromDict(dNodePlayLists);
				System.out.println("report: \n" + report );
			}
			
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Convierte el Nodo de PlayLists en diferentes archivos Json con información de cada Lista
	 * @param dNodePlayLists
	 * @return
	 */
	protected static StringBuilder exportlistsArrayFromDict(Node dNodePlayLists){
		System.out.println("<exportlistsArrayFromDict> ");
		StringBuilder process = new StringBuilder(), sbJson;
		String fileJsonName = "";
		System.out.println("dNodePlayLists.length: " + dNodePlayLists.getChildNodes().getLength());
		
		//1 obtener elementos
		NodeList pListDictNodes = dNodePlayLists.getChildNodes();
		Node listElement;
		boolean isKey = true;
		String key = "";
		int nListas = 0, nFolders=0;
		
		for(int a = 0; a < pListDictNodes.getLength(); a++) {
		    listElement = pListDictNodes.item(a);
		    JSONObject jsonPlist;
		    if(listElement.getNodeType() == Node.ELEMENT_NODE && listElement.getNodeName().equals("dict")) { //Cada elemento dict==PlayList
		    	jsonPlist = new JSONObject();
		    	//System.out.println("\n # de elementos de " + dictNode.getNodeName() + ": " + dictNode.getChildNodes().getLength() );
		    	NodeList dictChNodes = listElement.getChildNodes();
		    	try{
		    		for(int b =0;b<dictChNodes.getLength();b++){
			    		Node dictElement = dictChNodes.item(b);
			    		if(dictElement.getNodeType() == Node.ELEMENT_NODE){		    			
			    			if(isKey){ //llave
			    				key=dictElement.getTextContent();
			    			}
			    			else{ //Valor (nombre / id)
			    				try{
				    				if(dictElement.getNodeName().equals("string") || dictElement.getNodeName().equals("integer")){
//				    					System.out.println("{\""+ key+"\":\""+dictElement.getTextContent()+"\"}");
				    					jsonPlist.put(key, dictElement.getTextContent());
				    				}
				    				/* LISTA DE CANCIONES */
				    				else if(dictElement.getNodeName().equals("array") ) {
//				    					System.out.println("{\""+ key+"\":"+dictArrayToJSON(dictElement)+"}");
				    					jsonPlist.put(key, dictArrayToJSON(dictElement));
				    				}
				    				/* Todos los elementos //All items? TODO remover, innecesario al final */
				    				else if(dictElement.getNodeName().equals("true") || dictElement.getNodeName().equals("false") ) {
				    					//System.out.println("{\""+ key+"\":\""+ (dictElement.getNodeName().equals("true")?"true":"false")+ "\"}");
				    					jsonPlist.put(key, (dictElement.getNodeName().equals("true")?"true":"false"));
				    				}
				    				else if(dictElement.getNodeName().equals("data")) {
				    					//System.out.println("{\""+ key+"\":\""+ (dictElement.getNodeName().equals("true")?"true":"false")+ "\"}");
				    					jsonPlist.put(key, dictElement.getTextContent());
				    				}
			    				}catch (Exception e){
			    					e.printStackTrace();
			    				}
			    			}
			    			isKey = !isKey;
			    		}
			    	}
		    	}catch (Exception e){
		    		e.printStackTrace();
		    	}
		    	//* Ya procesados el Json, se escribe en el FileSystem (Un archivo .json por cada Lista)
		    	try {
		    		boolean esFolder = jsonPlist.has("Folder")&&jsonPlist.getString("Folder").toLowerCase().equals("true") ?true:false;
		    		
		    		System.out.println("Escribiendo " +(esFolder?"CarpetaLista: ":"lista : ") + jsonPlist.getString("Name") + ", ID: " + jsonPlist.getString("Playlist ID"));
		    		if(jsonPlist.has("Distinguished Kind") && jsonPlist.has("Playlist ID") && jsonPlist.getString("Playlist ID").equals("13063")){
	    				System.out.println("Distinguished Kind Error??");
	    			}
					process.append("Escribiendo ").append(esFolder?"Lista-Carpeta :":"lista :")
						.append(jsonPlist.getString("Name")).append(", ID: ").append(jsonPlist.getString("Playlist ID")).append(":\n");
					fileJsonName = (esFolder?"Folder.":"")+jsonPlist.getString("Playlist ID")+"."+jsonPlist.getString("Name").replace(" ", "_")+".json";
					sbJson = ItunesFormater.formtPlayList(jsonPlist);
					FileUtily.writeStringInFile(JSONLIST_DIR+fileJsonName, ItunesFormater.formtJsonByGoogle(sbJson), false);
					process.append(fileJsonName).append("==> OK\n");
					if(esFolder){
						nFolders++;
					}else{
						nListas++;
					}
					
				} catch (Exception e) {
					process.append("Error en iteración ").append(a).append(": ").append(e.getMessage()).append("\n");
					e.printStackTrace();
				}
		    }//if de elemento <dict>
		}// Fin de for pListDictNodes
		process.append(nListas).append(" Listas, ").append(nFolders).append(" Carpetas generadas /Escritas ");
		
		return process;
	}
	
	/**
	 * Convierte el segmento (Array) de Tracks dentro del PlayList en JSONArray (Solo Track_ID)
	 * @param arrTracksNode
	 * @return
	 */
	private static JSONArray dictArrayToJSON(Node arrTracksNode){
		JSONArray jsTracks = new JSONArray();
		//1 obtener elementos
		NodeList arrayNodes = arrTracksNode.getChildNodes(), dictNodes;
		Node dictElement, trackElement;
		boolean isKey = true;
    	String key = "";
    	
		for(int x =0; x<arrayNodes.getLength();x++){
			dictElement = arrayNodes.item(x);
			//2 Solo los elementos dict (<dict><key>T_ID</key><integer>id<integer></dict>			
			if(dictElement.getNodeType() == Node.ELEMENT_NODE && dictElement.getNodeName().equals("dict")) {
				dictNodes = dictElement.getChildNodes();
				JSONObject jsonTrack;
				try{
					for(int y=0; y<dictNodes.getLength();y++){
						trackElement = dictNodes.item(y);
						if(trackElement.getNodeType() == Node.ELEMENT_NODE){
							if(isKey){ //Track ID
			    				key=trackElement.getTextContent(); //Track ID
			    			}
			    			else{ //Valor (id)
			    				jsonTrack = new JSONObject();
			    				jsonTrack.put(key, trackElement.getTextContent());
			    				jsTracks.put(jsonTrack);
			    			}
							isKey = !isKey;
						}
					}
					
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return jsTracks;
	}
	
	/**
	 * Transforma el segmento de Tracks-xml en un arreglo de JSON 
	 * @param dNodeTracks
	 * @return
	 */
	private static JSONArray getTrackArrayFromDict(Node dNodeTracks) {
		System.out.println("<getTrackArrayFromDict> ");
		System.out.println("dNodeTracks.length: " + dNodeTracks.getChildNodes().getLength());
		JSONArray jsTracks = new JSONArray();
		//1 obtener elementos
		NodeList dictNodes = dNodeTracks.getChildNodes();
		Node trackElement;
		boolean isKey = true;
    	String key = "";

		try{
			for(int a = 0; a < dictNodes.getLength(); a++) {
				trackElement = dictNodes.item(a);
			    
			    JSONObject jsonInfo;
//			    NodeList infoList;
			    if(trackElement.getNodeType() == Node.ELEMENT_NODE) { //Solo debe tener dos elementos key y dict (con info de archivo)
			    	if(isKey){ //Track ID > 1247
			    		key=trackElement.getTextContent();
			    	}
			    	else if(trackElement.getNodeName().equals("dict")){ //Valor (array)
			    		System.out.println("<pDictTracksToJSON> id_Track a procesar : " + key);		
			    		jsonInfo = simpleKeyValueToJSON(trackElement.getChildNodes());
			    		jsTracks.put(jsonInfo);
			    	}
			    	isKey=!isKey;
			    }
			}//Fin de for			
		}catch (Exception e){
			e.printStackTrace();
		}
		return jsTracks;
	}
	
	
	/**
	 * Genera un objeto JSON a partir de una serie de parejas Key/Object en un NodeList <br>
	 * 
	 * &#60;key&#62;??&#60;/key&#62;<br>&#60;Object&#62;??&#60;/Object&#62;
	 * @param infoList
	 * @return
	 */
	private static JSONObject simpleKeyValueToJSON(NodeList infoList){
		JSONObject jsonTrackInfo = new JSONObject();
		try{
			boolean isKey = true;
	    	String key = "";
			for(int a = 0; a < infoList.getLength(); a++) { //Solo debe tener dos elementos cada subNodo
			    Node dictNode = infoList.item(a);
			    if(dictNode.getNodeType() == Node.ELEMENT_NODE) {
			    	if(isKey && dictNode.getNodeName().equals("key")){ //llave
	    				key=dictNode.getTextContent();
	    			}
	    			else if(dictNode.getNodeName().equals("string") || dictNode.getNodeName().equals("integer")){
	    				//FIX para eliminar de entrada HExadecimales [StringUtily.replaceUnicode]
	    				jsonTrackInfo.put(key, StringUtily.replaceUnicode(dictNode.getTextContent()));
	    			}
			    	isKey = !isKey;
			    }
			}
			//FIX para modificar ruta absoluta por relativa y quitar caracteres Inválidos
			if(jsonTrackInfo.has("Location")){
				jsonTrackInfo.put("Location", ItunesFormater.locFormat(jsonTrackInfo.getString("Location")));
			}
			if(jsonTrackInfo.has("Name")){
				jsonTrackInfo.put("Name", StringUtily.replaceUnicode(jsonTrackInfo.getString("Name")) );
			}
			/* FIX para remover Parametros: Comments=NO necesarios,Sample Rate=NOnecesario,File Folder Count=[-1],Track Type=[File],Artwork Count=[-1] */
			jsonTrackInfo.remove("Comments");
			jsonTrackInfo.remove("Sample Rate");
			jsonTrackInfo.remove("File Folder Count");
			jsonTrackInfo.remove("Track Type");
			jsonTrackInfo.remove("Artwork Count");
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		return jsonTrackInfo;
	}	
	
	
	/**
	 * Lee el archivo de TracksDB.json e itera los elementos para algún requerimiento
	 */
	public static void iterateTracksDB(){
		System.out.println("<iterateTracksDB> ");
		try{
			JSONArray jsTracksDB;
			String stTracksDB = FileUtily.getBuilderNoTabsFile(JSONLIST_DIR+TRACKS_FILE, ConstantesItunes.PCHARSET).toString();
			
			jsTracksDB = new JSONArray(stTracksDB);
			
			if(jsTracksDB!=null && jsTracksDB.length()>0){
				System.out.println("<iterateTracksDB> Se iteran "+jsTracksDB.length()+" Elementos ");
				
				/*El HashSet no Permite DUplicados */
				java.util.HashSet<String> genero = new java.util.HashSet<String>(); 
				
				for(int x=0; x<jsTracksDB.length();x++){
					
					if(jsTracksDB.getJSONObject(x).has("Genre")){ //Print <parametro>
//						System.out.println(jsTracksDB.getJSONObject(x).getString("Track Type").equals("File")?"":"NNNNN");
//						System.out.println(jsTracksDB.getJSONObject(x).getString("Artwork Count"));
						
						genero.add(jsTracksDB.getJSONObject(x).getString("Genre")); 
					}
//					System.out.println(utily.JsonUtily.formatJson(jsTracksDB.getJSONObject(x).toString()));
				}
				for( Iterator<String> itGen = genero.iterator(); itGen.hasNext(); ) { 
					System.out.println(itGen.next()); 
					} 
				
			}else{
				System.out.println("<iterateTracksDB> No hay Elementos (json) que Iterar ");
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("<iterateTracksDB> FIN.");
	}
		
}
