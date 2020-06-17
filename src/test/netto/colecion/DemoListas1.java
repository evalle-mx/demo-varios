package netto.colecion;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class DemoListas1 {

	public static void main(String[] args) {
		testRemoveFromList();
	}
	
	/**
	 * Muestra el funcionamiento para REMOVER elementos de Lista
	 */
	public static void testRemoveFromList(){
		List<String> lsCadenas = new ArrayList<String>();
		
		lsCadenas.add("Canada");
		lsCadenas.add("USA");
		lsCadenas.add("Venezuela");
		lsCadenas.add("Mexico");
		lsCadenas.add("Brasil");
		lsCadenas.add("Cuba");
		
		// remove South-american Countries:
		ListIterator<String> itCadenas = lsCadenas.listIterator();
		String pais;
		while(itCadenas.hasNext()){
			pais = itCadenas.next();
			System.out.println(pais);
			if(pais.equals("Venezuela") || pais.equals("Brasil"))
				itCadenas.remove();			
		}
		
		System.out.println("Paises restantes: ");
		for(String pais2 : lsCadenas){
			System.out.print( pais2 + ", ");
		}
		
	}

}
