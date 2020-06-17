package netto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(testSuffle());
		spliter();

	}
	
	
	
	private static void spliter(){
		String descripcion = "/Administración/Carga Masiva/Semillas Modelo"; // "/Administración/Carga Masiva/Semillas Modelo"
		String[] subFolders;
		String fPadre = "";
		if(descripcion.indexOf("/")!=-1){
			subFolders = descripcion.split("/");
			if(subFolders.length>2){
				System.out.println("tiene ramas");
				for(int x=0;x<subFolders.length;x++){
					System.out.println(">"+subFolders[x]+"<");
				}
				fPadre = subFolders[subFolders.length-2];
				System.out.println("padre: "+fPadre);
			}
			else{
				System.out.println("es hoja");
			}
		}
		
	}
	
	private static List<String> testSuffle(){
		List<String> lista = new ArrayList<String>();
		
		lista.add("A");
		lista.add("B");
		lista.add("C");
		lista.add("D");
		
		Collections.shuffle(lista); 
		
		return lista;
	}

}
