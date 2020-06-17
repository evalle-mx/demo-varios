package com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import utily.FileUtily;

public class IELTS_Assessor {

	
	public static void main(String[] args) {
		try {
//			writeResult();
			vocabTest();
//			scoreFinal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void vocabTest() throws Exception {
		String corrVocab = "/home/dothr/Documents/Personal/TEMP/VocabularyCorr.csv";	//Inglés;Español
		String vocTest = "/home/dothr/Documents/Personal/TEMP/vocabularyTest.csv";
		String evPathCsv = "/home/dothr/Documents/Personal/TEMP/vocabularyEval.csv"; //Salida
		String orden ="Esp2EN"; 	// EN2Esp | Esp2EN
		
		String linea;
		List<String> lsCorrect, lsAnswer, tokensR, tokensC;
		//Las palabras Deben tener el mismo orden para que los arreglos coincidan
		lsCorrect = FileUtily.getLinesFile(corrVocab);
		lsAnswer = FileUtily.getLinesFile(vocTest);
		
		StringBuilder sbEv = new StringBuilder();
		String resp, word,esp;
		int indice = 0, correctas= 0, inc = 0;
		if(orden.equals("Esp2EN")){//Español (vocTest) a Inglés (Correct)
			sbEv.append("ES;EN;resp\n");
			Iterator<String> itResp = lsAnswer.iterator();
			
			while(itResp.hasNext()){
				tokensR = Arrays.asList( itResp.next().split("\\s*,\\s*") );
				tokensC = Arrays.asList( lsCorrect.get(indice).split("\\s*,\\s*") );
				
				word = tokensC.get(0);	//palabra correcta (en,es)
				esp = tokensC.get(1); //significado esp
				resp = tokensR.get(1); 	//palabra a evaluar (en,es)
				
				//if(word.toUpperCase().indexOf(resp.toUpperCase())!=-1){
				if(word.toUpperCase().equals(resp.toUpperCase())){
					System.out.println("Correcta o aproximada "+resp + "/"+word);
//					sbEv.append(resp).append("\t").append(word).append("\t").append("1\n");
					correctas++;
				}else{
					//No correcta error
					sbEv.append(esp).append(";").append(word).append(";").append(resp).append("\n");
					inc++;
				}
				indice++;
			}
			
			
		}else{	//Inglés (vocTest) a Español (Correct)
			sbEv.append("Respuesta;Español;resultado\n");
		}
		System.out.println("se evaluaron "+indice+" palabras, correctas: " + correctas + " incorrectas: " + inc );
		//Se escribe archivo de resultados calificados
		FileUtily.writeStringInFile(evPathCsv, sbEv.toString(), false);
	}
	
	public static void writeResult() throws Exception {
		/* Archivo con respuestas correctas: */
		String answersCorrectCsv = "/home/dothr/Documents/Personal/TEMP/listening.Respuestas.csv";
		/* Archivo con respuestas del examen: */
		String answSheetCsv = "/home/dothr/Documents/Personal/TEMP/listening.Answer.p1.csv";
		
		/* Archivo de resultado evaluado */
		String evaluatedCsv = "/home/dothr/Documents/Personal/TEMP/listening.Evaluado.csv";
		
		Integer nActivo;
		String linea;
		List<String> lsCorrect, lsAnswer, tokens;
		
		lsCorrect = FileUtily.getLinesFile(answersCorrectCsv);
		lsAnswer = FileUtily.getLinesFile(answSheetCsv);
		
		Map<Integer, String> mpCorrect = new HashMap<Integer, String>();
		Iterator<String> itLineaA = lsCorrect.iterator();
		while(itLineaA.hasNext()){
			linea = itLineaA.next();
//			System.out.println(linea);
			tokens = Arrays.asList( linea.split("\\s*;\\s*") );
//			System.out.println("tokens: " + tokens );
			nActivo = Integer.parseInt(tokens.get(0));
			mpCorrect.put(nActivo, tokens.get(1).toUpperCase());
		}
//		System.out.println(mpCorrect.toString().replace(", ", "\n"));
		//Ya obteniendo el mapa de correctas se iteran respuestas, se califica y guarda resultado
		Iterator<String> itLineaB = lsAnswer.iterator();
		String respuesta;
		boolean bCorrecto = false;
		StringBuilder sbEvaluacion=new StringBuilder("#;escrita;resultado;correcta\n");
		
		while(itLineaB.hasNext()){
			linea = itLineaB.next();
			tokens = Arrays.asList( linea.split("\\s*;\\s*") );
			nActivo = Integer.parseInt(tokens.get(0));
			respuesta = mpCorrect.get(nActivo);
			bCorrecto = respuesta.equalsIgnoreCase(tokens.get(1));
			System.out.println(tokens.get(1)+ " == " + respuesta+ "? " 
			+ bCorrecto );
			sbEvaluacion.append(nActivo).append(";")
			.append(tokens.get(1)).append(";")
			.append(bCorrecto?"Ok":"FAIL").append(";")
			.append(bCorrecto?"":respuesta).append("\n");
			
		}
		
		//Se escribe archivo de resultados calificados
		FileUtily.writeStringInFile(evaluatedCsv, sbEvaluacion.toString(), false);
	}
	
	
	public static void scoreFinal() throws Exception {
		String evaluatedCsv = "/home/dothr/Documents/Personal/TEMP/listening.Evaluado.csv";
		List<String> lsEval = FileUtily.getLinesFile(evaluatedCsv), tokens;
		Iterator<String> itEval = lsEval.iterator();
		int points = 0;
		while(itEval.hasNext()){
			tokens = Arrays.asList( itEval.next().split("\\s*;\\s*") );
			if(tokens.get(2).toUpperCase().indexOf("OK")!=-1){
				points++;
			}
		}
		System.out.println("points: " + points );
	}
}
