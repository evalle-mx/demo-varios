package net.dothr.seed;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import utily.FileUtily;

public class FileProcecer {

	
	private static String fileIn = "/home/dothr/Documents/TCE/TMP/paginaWeb.htm";
	private static String fileOut = "/home/dothr/Documents/TCE/TMP/paginaWeb-Parsed.txt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseFile();
	}
	
	
	protected static void parseFile(){
		
		StringBuilder sb = new StringBuilder();
		int nLines = 0;
		try 
	    {
			BufferedReader infile = new BufferedReader(
	        		 new InputStreamReader(
	                         new FileInputStream(fileIn), "UTF8"));
	        String strLine;
	        while ((strLine = infile.readLine()) != null) 
	        {
	        	//if(strLine.startsWith("<h3>")){
	        		//strLine+=":</h3>";
	        	if(strLine.indexOf(":</h3>")!=-1){
	        		strLine="</p>\n<h3>"+strLine;	//+"\n<p>";
	        	}
	            sb.append(strLine).append("\n");
	            nLines++;
	        }
	        infile.close();	        
	    } 
	    catch (IOException e) 
	    {	//System.err.println("IOException "+e);
	    	e.printStackTrace();
	    }
		FileUtily.writeStringInFile(fileOut, sb.toString(), false);
	}

}
