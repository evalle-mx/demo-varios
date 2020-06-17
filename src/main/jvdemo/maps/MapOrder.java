package jvdemo.maps;

import java.util.Map;
import java.util.TreeMap;

public class MapOrder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		orderMap();
	}
	
	
//	protected static void genericMap(){
//		Map<?,?> mapaGen;
//		
//		//creandolo con tipo:
//		mapaGen = new TreeMap<Integer, String>();
//		
//		mapaGen.put(8, "Cadena 8 => ingresada 1");
//		mapaGen.put(2, "Cadena 2 => ingresada 2");
//	}

	/**
	 * Método que ejemplifica un mapa Ordenado [TreeMap<>]
	 */
	protected static void orderMap(){
		
		/*  Ejemplo con Entero
		Map<Integer,String> mapOrd = new TreeMap<Integer, String>();
		
		mapOrd.put(8, "Cadena 8 => ingresada 1");
		mapOrd.put(2, "Cadena 2 => ingresada 2");
		mapOrd.put(5, "Cadena 5 => ingresada 3");
		mapOrd.put(6, "Cadena 6 => ingresada 4");
		mapOrd.put(1, "Cadena 1 => ingresada 5");
		mapOrd.put(2, "Cadena 2 (dup) => ingresada 6"); //Si se vuelve a escribir conserva su orden
		//*/
		
		
		//* Ejemplo con String (http://www.aprenderaprogramar.com/index.php?option=com_content&view=article&id=614:interfaces-map-y-sortedmap-del-api-de-java-clases-hashmap-y-treemap-ejemplo-diferencias-cu00922c&catid=58:curso-lenguaje-programacion-java-nivel-avanzado-i&Itemid=180)
		Map<String,String> mapOrd = new TreeMap<String, String>();
		
		mapOrd.put("Trabajo", "954825748");
		mapOrd.put("Oficina", "958746362");
		mapOrd.put("Móvil","666555444");
		mapOrd.put("Casa","952473456");
		mapOrd.put("Twitter","@javaejemplo");
		mapOrd.put("facebook","nickName");		
		mapOrd.put("twitter","@otroTwitter"); //Prueba mayusculas/minusculas (No es duplicado porq distingue)
		//*/
		
		//se imprime el orden del mapa:
		System.out.println(mapOrd.toString().replaceAll(",", "\n"));
		
		
		
		
	}
}
