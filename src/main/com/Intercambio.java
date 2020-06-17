package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Intercambio {

	public static void main(String[] args) {
		
		selPers();
		
	}
	
	public static void selPers(){
		StringBuilder sb = new StringBuilder("Intercambio: \n");
		List<String> pers = getLista();
		List<String> pers2 = new ArrayList<String>(pers);
		String p1, p2;
		Random rand = new Random();
		for(int x=0; x<pers.size();x++){
			p1= pers.get(x);
			int rdm = -1;
			//System.out.println("Buscando para para "+p1);
			do{
				rdm = rand.nextInt(pers2.size());
				p2=pers2.get(rdm);
			}while(p1.equals(p2));
			//System.out.println(p1 + " <==> " + p2);
			sb.append(p1).append(" <==> ").append(p2).append("\n");
			pers2.remove(rdm);
		}
		System.out.println(sb);
	}
	
	private static List<String> getLista(){
		String nombres =  "Moni;Izza;Karen;Elizabeth;Maleny;Cinthia;Lucy;Omar;Letty;Perlita;Netto;Lenday";
				//"Maleni;Cinthia;Izza;Letty;Edgar;Lucy;Moni;Perlita;Eli;Netto";
		
		List<String> lsPers = Arrays.asList(nombres.split("\\s*;\\s*"));
		return lsPers;
	}
	
	/*
	Moni: Mediana/X/Y
	Izza: Chica/X/Y
	Karen: Mediana/?/?
	Elizabeth: Mediana/Chachetero/Y
	Maleny: Mediana/?/?
	Cinthia: Mediana/Chachetero/?
	Lucy: Mediana/?/?
	Omar: Grande/Boxer/Y
	Letty: Mediana/Bikini/Negro
	Perla: Chica/?/Negro
	Netto: Mediana/X/Rojo
	Lenday: Chica/?/?
	Atzi: Chica/?/?*
	Karensita: Mediana/?/?*
	*/
}
