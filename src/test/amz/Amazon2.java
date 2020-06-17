package amz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Amazon2 {
	
	// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    int minimumHours(int rows, int columns, List<List<Integer> > grid)
    {
        // WRITE YOUR CODE HERE
    	Iterator<List<Integer>> itGrid = grid.iterator();
    	List<Integer> ls;
    	
    	boolean noZero=false, ones=false;
    	int hours = 1;   	
    	
    	
    	Integer iBase;
    	int y=0;
    	while(itGrid.hasNext()) {
        	ls = itGrid.next();
        	System.out.println(ls);
        	
        	for(int x=0;x<ls.size();x++) {
        		iBase=ls.get(x);
        		
        		
        		if(iBase==1) {
        			ones=true;//At least one 
        			System.out.println("("+x+","+y+") look for adjacency");
        			if(x<ls.size()-2) {
        				if(ls.get(x+1)==0) {
        					hours++;
        				}
        			}
        		}
        	}
        	
        	y++;
        	
    	}
    	
    	if(!ones) {
    		return -1;
    	}
    	
    	return hours;
    }
    // METHOD SIGNATURE ENDS
    
    
    
    
    
    public static void main(String[] args) {
		
    	Amazon2 demo = new Amazon2();
    	
    	List<List<Integer>> grid = new ArrayList<List<Integer>>();
    	
    	String linea = "1,0,0,0,0"; 
    	grid.add(parsInts(linea));
    	linea = "0,1,0,0,0"; 
    	grid.add(parsInts(linea));
    	linea = "0,0,1,0,0"; 
    	grid.add(parsInts(linea));
    	linea = "0,0,0,1,0"; 
    	grid.add(parsInts(linea));
    	linea = "0,0,0,0,1"; 
    	grid.add(parsInts(linea));
    	
    	
    	System.out.println("numH: " + demo.minimumHours(5, 5, grid) );
    	
    	
	}
    
    private static List<Integer> parsInts(String linea){
    	List<String> lsData = Arrays.asList(linea.split("\\s*,\\s*"));
    	List<Integer> lsLinea = new ArrayList<Integer>();
    	for(int x=0;x<lsData.size();x++) {
    		int pInt = Integer.parseInt(lsData.get(x));
    		lsLinea.add(pInt);    		
    	}
    	return lsLinea;
    }
    

}
