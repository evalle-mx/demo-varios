package netto.colecion;

import java.util.HashSet;

public class DemoSets {
	
	public static void main(String[] args) {
		testNoDuplicados();
	}
	
	/**
	 * Se muestra la caracteristica que evita duplicados en el Set
	 * (No garantiza Orden)
	 */
	protected static void testNoDuplicados() {
		HashSet<String> setCad = new HashSet<String>();
		
		setCad.add("Violeta");
		setCad.add("Bequi");
		setCad.add("Violeta");
		setCad.add("Diana");
		
		System.out.println("# elementos: " + setCad.size());
		System.out.println(setCad);
		
		//Validar si existe cadena:
		String stBusq = "Bequis";
		System.out.println("Existe "+stBusq +" en el Set? "+ setCad.contains(stBusq));
		
	}

}
