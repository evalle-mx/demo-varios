package com.itunes;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utily.FileUtily;

/**
 * Procesa archivos XML modificados para extraer información de Tracks o de LIsta de canciones
 * @author dothr
 *
 */
public class ConvertXml {
	
	private static final String JSONLIST_DIR = ConstantesItunes.JSONLIST_DIR;	
	protected static final String XmlPlayList = ConstantesItunes.XmlPlayList;
	protected static final String xmlTracks = ConstantesItunes.xmlTracks;	
	private static final String TRACKS_FILE = ConstantesItunes.TRACKS_FILE_NAME;

	
	public static void main(String[] args) {
		crawlItunesXML_ANT(xmlTracks);	//	XmlPlayList | xmlTracks
	
	}
	
	
	/**
	 * Procesa los archivos Modificados
	 * @param xmlFile
	 */
	public static void crawlItunesXML_ANT(String xmlFile){
		try {
			File fXmlFile = new File(ConstantesItunes.ITUNES_DIR+xmlFile);	//"/home/dothr/Music/ITUNES-/pList.test.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName()); //plist
			NodeList pNodes = doc.getDocumentElement().getChildNodes();	//Todos los segmentos dict principal
			
			for(int a = 0; a < pNodes.getLength(); a++) {
				Node dNode = pNodes.item(a);
			    if(dNode.getNodeType() == Node.ELEMENT_NODE && dNode.getNodeName().equals("dict")) {
			    	
			    		
			    		processNode(dNode);
			    	
			    	
			    }
			    //#text y otros no procesados:
			    else {
			    	if(dNode.getNodeType() == Node.ELEMENT_NODE){
			    		System.out.println("se omite nodo: " + dNode.getNodeName());
			    		}
			    }
			}//FIn de iteracion de nodos
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Lee el archivo modificado de PlayList de Itunes para generar un JSONArray con las listas
	 */
	public static void processNode(Node dNode) throws Exception {
			/* Nodo para Lista de canciones (nombreLista, y arreglo de trackId's)*/
			NodeList dDictPlst = null;
			NodeList dDictTracks = null;
			
			NodeList dNodes = dNode.getChildNodes();
			boolean isKey = true;
			String key = "";
			Node dNodeItem;
			for(int b =0;b<dNodes.getLength();b++){			    		
				dNodeItem = dNodes.item(b);			    		
			    if(dNodeItem.getNodeType() == Node.ELEMENT_NODE){
			    System.out.println("b: " + b + ", name: "+dNodeItem.getNodeName() +" ("+dNodeItem.getTextContent()+")");
			    	if(isKey){ //Playlists
			    		key=dNodeItem.getTextContent();
			    	}
			    	else if(dNodeItem.getNodeName().equals("array") && key.equals("Playlists")){ //Valor (array)
			    		System.out.println("<processNodePList> item a procesar : " + key);
			    		dDictPlst = dNodeItem.getChildNodes();
			    	}
			    	else if(dNodeItem.getNodeName().equals("dict") && key.equals("Tracks")){ //Valor (array)
			    		System.out.println("<processNodePList> item a procesar : " + key);
			    		dDictTracks = dNodeItem.getChildNodes();
			    	}
				   	isKey=!isKey;
			    }
			} //Fin de for dNodes
			/* ************************************ */
			
			/* PROCESO DE INFORMACION de CANCIONES (Tracks) */
			if(dDictTracks!=null){ 		//xmlFile.equals(xmlTracks)
				StringBuilder procTracks = pDictTracksToJSON(dDictTracks); //Segmento dict.Playlists(array)
				System.out.println( "<processNodePList> REporte:" );
				System.out.println( "<processNodePList> \n" + procTracks.toString() );
			}
			/* PROCESO DE LISTA DE CANCIONES (PlayList) */
			if(dDictPlst!=null){	// && xmlFile.equals(XmlPlayList)
				StringBuilder procList = pDictPlistToJSON(dDictPlst); //Segmento dict.Playlists(array)
				System.out.println( "<processNodePList> REporte:" );
				System.out.println( "<processNodePList> \n" + procList.toString() );
			}
	}
	
	/**
	 * Metodo encargado de crear el archivo de lista de canciones
	 * @param pListDictNodes
	 * @return
	 */
	protected static StringBuilder pDictTracksToJSON(NodeList pListDictNodes){
		System.out.println("<pDictTracksToJSON> ");
		StringBuilder process = new StringBuilder();
		boolean isKey = true;
		JSONArray jsTracks = new JSONArray();
		String key = "";
		Node dictNode;
		for(int a = 0; a < pListDictNodes.getLength(); a++) {
		    dictNode = pListDictNodes.item(a);
		    
		    JSONObject jsonInfo;
//		    NodeList infoList;
		    if(dictNode.getNodeType() == Node.ELEMENT_NODE) { //Solo debe tener dos elementos key y dict (con info de archivo)
		    	if(isKey){ //Track ID > 1247
		    		key=dictNode.getTextContent();
		    	}
		    	else if(dictNode.getNodeName().equals("dict")){ //Valor (array)
		    		System.out.println("<pDictTracksToJSON> id_Track a procesar : " + key);		    		
//		    		infoList = dictNode.getChildNodes();
		    		jsonInfo = simpleKeyValueToJSON(dictNode.getChildNodes());
		    		//nombre = jsInfo.getString("Name")
		    		jsTracks.put(jsonInfo);
		    	}
		    	isKey=!isKey;
		    }
		}//Fin de for
		process.append("Se procesaron ").append(jsTracks.length()).append(" Canciones \n");
		
		//Escribir JSON de canciones
		try {		    		
			process.append("Escribiendo DB de canciones: ").append(TRACKS_FILE).append("\n");
			FileUtily.writeStringInFile(JSONLIST_DIR+TRACKS_FILE, ItunesFormater.formtSimpleJsonBr(jsTracks).toString(), false);
			process.append(TRACKS_FILE).append("==> OK\n");
		} catch (Exception e) {
			process.append("Error al escribir archivo ").append(JSONLIST_DIR+TRACKS_FILE).append(":\n ").append(e.getMessage()).append("\n");
			e.printStackTrace();
		}
		
		return process;
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
	    				jsonTrackInfo.put(key, dictNode.getTextContent());
	    			}
			    	isKey = !isKey;
			    }
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return jsonTrackInfo;
	}
	
	/**
	 * Recibe el arreglo dentro del segmento de Listas, dict.key=Playlist: <br>
	 * &#60;dict&#62; <br> &#32;&#32;&#60;key&#62;Playlists&#60;/key&#62;
	 * <br>&nbsp;&nbsp;&nbsp;&nbsp;&#60;array&#62;
	 * @param pListDictNodes
	 * @return
	 */
	protected static StringBuilder pDictPlistToJSON(NodeList pListDictNodes){
		StringBuilder process = new StringBuilder();
		String fileJsonName = "";
		int nListas = 0;
		StringBuilder sbTmp = null;
		for(int a = 0; a < pListDictNodes.getLength(); a++) {
		    Node dictNode = pListDictNodes.item(a);
		    JSONObject jsonPlist;
		    if(dictNode.getNodeType() == Node.ELEMENT_NODE && dictNode.getNodeName().equals("dict")) { //Cada elemento dict==PlayList
		    	jsonPlist = new JSONObject();
		    	//System.out.println("\n # de elementos de " + dictNode.getNodeName() + ": " + dictNode.getChildNodes().getLength() );
		    	NodeList dictChNodes = dictNode.getChildNodes();
		    	boolean isKey = true;
		    	String key = "";
		    	
		    	for(int b =0;b<dictChNodes.getLength();b++){
		    		Node dictElement = dictChNodes.item(b);
		    		if(dictElement.getNodeType() == Node.ELEMENT_NODE){ //TODO DISCRIMINAR CUANDO SEAN favoritos, más_recientes, etc
		    			
		    			if(isKey){ //llave
		    				key=dictElement.getTextContent();
		    			}
		    			else{ //Valor (nombre / id)
		    				try{
			    				if(dictElement.getNodeName().equals("string") || dictElement.getNodeName().equals("integer")){
//			    					System.out.println("{\""+ key+"\":\""+dictElement.getTextContent()+"\"}");
			    					jsonPlist.put(key, dictElement.getTextContent());
			    				}
			    				/* LISTA DE CANCIONES */
			    				else if(dictElement.getNodeName().equals("array") ) {
//			    					System.out.println("{\""+ key+"\":"+dictArrayToJSON(dictElement)+"}");
			    					jsonPlist.put(key, dictArrayToJSON(dictElement));
			    				}
			    				/* Todos los elementos //All items? TODO remover, innecesario al final */
			    				else if(dictElement.getNodeName().equals("true") || dictElement.getNodeName().equals("false") ) {
			    					//System.out.println("{\""+ key+"\":\""+ (dictElement.getNodeName().equals("true")?"true":"false")+ "\"}");
			    					jsonPlist.put(key, (dictElement.getNodeName().equals("true")?"true":"false"));
			    				}
		    				}catch (Exception e){
		    					e.printStackTrace();
		    				}
		    			}
		    			isKey = !isKey;
		    		}
		    	}
		    	//jsPlayLists.put(jsonPlist);
		    	try {		    		
					process.append("Escribiendo lista: " + jsonPlist.getString("Name") + ", ID: " + jsonPlist.getString("Playlist ID")).append(":\n");
					fileJsonName = jsonPlist.getString("Playlist ID")+"."+jsonPlist.getString("Name").replace(" ", "_")+".json";
					sbTmp = ItunesFormater.formtPlayList(jsonPlist);
					FileUtily.writeStringInFile(JSONLIST_DIR+fileJsonName, ItunesFormater.formtJsonByGoogle(sbTmp), false);
					process.append(fileJsonName).append("==> OK\n");
					nListas++;
				} catch (Exception e) {
					process.append("Error en iteración ").append(a).append(": ").append(e.getMessage()).append("\n");
					e.printStackTrace();
				}
		    }
		}
		process.append(nListas).append(" Listas Generadas");
		//return jsPlayLists;
		return process;
	}
	
	
	/**
	 * Convierte el segmento (Array) de Tracks en PlayList en JSONArray
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
		
		//TODO ITERAR los json y comparar con la base de JSON-Tracks (BD de canciones.mp3)
		
		return jsTracks;
	}
}




//public static void processNode(Node dNode) throws Exception {
//	NodeList dNodeTags = dNode.getChildNodes();
//	Node playListNode = null;
//	boolean isKey = true;
//	String key = "";
//	Node dNodeItem;
//	for(int b =0;b<dNodeTags.getLength();b++){
//		dNodeItem = dNodeTags.item(b);	    		
//	    if(dNodeItem.getNodeType() == Node.ELEMENT_NODE){ //Para omitir #text
//	    	System.out.println("b: " + b + ", name: "+dNodeItem.getNodeName());
//	    	if(isKey){ //Playlists
//	    		key=dNodeItem.getTextContent();
//	    	}
//	    	else if(dNodeItem.getNodeName().equals("array") ){ //Valor (array)
//	    		
//	    	}
//	    }
//	}
//}
