package net.dothr.seed;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import utily.FileUtily;

public class ConcatenaSolrDoc {
	static Logger log4j = Logger.getLogger( ConcatenaSolrDoc.class );

	final static String NUTCH_TCE_PATH = "/home/dothr/Documents/TCE/semillas_cvs/Nutch/Semillas/";
	final static String SOLR_DOCS_PATH = NUTCH_TCE_PATH+"solrDocs/";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String nm = "12.39-45";
		String site = "http://elecyelectind.blogspot.mx/2012/06/";
		concatenaDocs(nm, site);
	}
	
	
	
	public static void concatenaDocs(String nm, String site){
		log4j.debug("<concatenaDocs> ");
		ArrayList<String> lsDocs = getLsArchs(); 
		Iterator<String> itDocs = lsDocs.iterator();
		String slrNameOut = "solrDoc.c_"+nm+".txt";
		String fileName;
		StringBuilder sbMain = new StringBuilder();
		while(itDocs.hasNext()){
			fileName = itDocs.next();
			log4j.debug("<concatenaDocs> archivo " + fileName );
			sbMain.append(FileUtily.getBuilderFile(SOLR_DOCS_PATH+fileName, "UTF-8") ).append("\n");
		}
		log4j.debug("<concatenaDocs> \n Site: "+ site 
				+ "\nDocs Concatenados: " + lsDocs 
				+ "\nArchivo Concatenado: " + SOLR_DOCS_PATH+slrNameOut );
		FileUtily.writeStringInFile(SOLR_DOCS_PATH+slrNameOut, sbMain.toString(), false);
		log4j.debug("<concatenaDocs> ");
	}

	private static ArrayList<String> getLsArchs(){
		ArrayList<String> lsDocs = new ArrayList<String>();
		lsDocs.add("solrDoc.12-39.txt");
		 lsDocs.add("solrDoc.12-40.txt");
		 lsDocs.add("solrDoc.12-41.txt");
		 lsDocs.add("solrDoc.12-42.txt");
		 lsDocs.add("solrDoc.12-43.txt");
		 lsDocs.add("solrDoc.12-44.txt");
		 lsDocs.add("solrDoc.12-45.txt");
		 
		return lsDocs;
	}
	
}
