package net.dothr;
/*
 * Clase desarrollada para convertir datos de Xml utilizados en el mapa DotHR en 
 * archivos .json para la versión más ligera de combos
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import utily.FileUtily;

import java.io.File;
/**
 * Clase desarrollada para convertir datos de Xml utilizados en el mapa DotHR en 
 * archivos .json para la versión más ligera de combos
 * @author Netto
 *
 */
public class Xml2Json {
	
	private static final String xmlsPath = "/home/dothr/app/webServer/resources/dothr/xml/";
	private static final String jsonsPath = "/home/dothr/app/webServer/resources/dothr/json/";
	
	private static final String xmlSample = "/home/dothr/workspace/MyProjects/files/test/staff.xml";
	

	
	public static void main(String[] args) {
//		myKong();
		migrateXml();
	}
	

	/**
	 * Procesa los tres diferentes tipos de XML utilizados en la 
	 * ubicación de mapa (Generados por scripts de Oracle)
	 */
	public static void migrateXml(){
		//1. Convierte archivo unico estados
		String jsonSt = jsonFromXml("states_1.xml", "idEstado");
		FileUtily.writeStringInFile(jsonsPath+"estados.json", jsonSt, false);
		
		//2. Convierte archivos Municipio's (archivo por estado)
		String xmlMunic = "municipalities_";
		for(int x=1; x<=32; x++){
			jsonSt = jsonFromXml(xmlMunic+x+".xml", "idMunicipio");
			FileUtily.writeStringInFile(jsonsPath+"municipios_"+x+".json", jsonSt, false);
		}
		//3. Convierte archivos Asentamiento's (archivo por Municipio) 
		String xmlSettl = "settlement_";
		for(int x=1; x<=2457; x++){
			jsonSt = jsonFromXml(xmlSettl+x+".xml", "idAsentamiento");
			FileUtily.writeStringInFile(jsonsPath+"asentamientos_"+x+".json", jsonSt, false);
		}
	}
	
	/**
	 * Realiza la lectura y procesamiento de un archivo xml para obtener datos y 
	 * convertirlo a json
	 * @param xmlName nombre de archivo a procesar y generar
	 * @param idLabel etiqueta para el campo 'id'  [ {&lt;idLabel&gt;:x, nombre: "????"} ]
	 * @return
	 */
	protected static String jsonFromXml(String xmlName, String idLabel){
		String formt = "\n", fmt2 = "   ";
		StringBuilder sbJson = new StringBuilder("[");
		String txtId, texto;
		try {
			 File fXmlFile = new File(xmlsPath+xmlName);//"states_1.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				
				doc.getDocumentElement().normalize();
//				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				NodeList nList = doc.getElementsByTagName("item");
				System.out.println("----------------------------");
				sbJson.append(formt);
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					System.out.println("\nCurrent Element [" + temp+ "]: " + nNode.getNodeName());					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						txtId = eElement.getAttribute("id");
						texto = eElement.getTextContent();
						System.out.println("item id : " + txtId);
						System.out.println("item texto : " + texto );
						
						sbJson.append(fmt2).append("{")
						.append("\"").append(idLabel).append("\":").append(txtId).append(",")
						.append("\"").append("nombre").append("\":\"").append(texto).append("\"}")
						.append(temp==nList.getLength()-1?"":",").append(formt);
					}
				}
		 }catch (Exception e){ e.printStackTrace();		 }
		sbJson.append("]");
		return sbJson.toString();
	}
	
	
	/**
	 * Ejemplo funcional 
	 */
	public static void myKong() {
	    try {
		File fXmlFile = new File(xmlSample);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("staff");

		System.out.println("----------------------------");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				System.out.println("Staff id : " + eElement.getAttribute("id"));
				System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
				System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
				System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
				System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

			}
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	  }
	

}
