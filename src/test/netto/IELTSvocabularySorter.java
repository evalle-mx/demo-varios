package netto;

import java.util.Collections;
import java.util.List;

import utily.FileUtily;

public class IELTSvocabularySorter {
	
	private static String vocabCsv = "/home/dothr/Downloads/VocabularyWords";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sortVocabCSV();

	}
	
	
	private static void sortVocabCSV(){
		System.out.println("Reading: "+vocabCsv+".csv");
		List<String> words = FileUtily.getLinesFile(vocabCsv+".csv");
		System.out.println("shuffle words");
		Collections.shuffle(words);
		System.out.println("Creating sb");
		StringBuilder sb = new StringBuilder();
		for(int x=0;x<words.size();x++){
			sb.append(words.get(x)).append("\n");
		}
		System.out.println("Writing file: " + vocabCsv+"b.csv");
		FileUtily.writeStringInFile(vocabCsv+"b.csv", sb.toString(), false);
		System.out.println("End");
	}

}
