package amz;
import java.util.List;
import java.util.ArrayList;
import  java.util.Iterator;

public class Amazon1 {

	  // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    int numberAmazonTreasureTrucks(int rows, int column,
								   List<List<Integer> > grid)
    {
        // WRITE YOUR CODE HERE
        Iterator<List<Integer>> itGrid = grid.iterator();
        int cparks=0, tpu=0;
        List<Integer> ls;
        Integer block;
        while(itGrid.hasNext()) {
        	ls = itGrid.next();
        	cparks=0;
        	
        	for(int x=0;x<column-1;x++) {
        		if(ls.get(x)==1 && ls.get(x+1)==1) {
        			cparks=1;
        		}
        		else if(rows==column) {
        			cparks=1;
        		}
        	}
        	
        	System.out.println(" "+cparks);
        	if(cparks==1) {
        		tpu++;
        	}
        }
        
        return tpu;
    }
    
    
    
    public static void main(String[] args) {
		Amazon1 dem = new Amazon1();
		List<List<Integer>> lsLs = new ArrayList<List<Integer>>();
		List<Integer> ls = new ArrayList<Integer>();
		ls.add(1);ls.add(1);ls.add(0);ls.add(0);
		lsLs.add(ls);
		ls = new ArrayList<Integer>();
		ls.add(0);ls.add(0);ls.add(1);ls.add(0);
		lsLs.add(ls);
		ls = new ArrayList<Integer>();
		ls.add(0);ls.add(0);ls.add(0);ls.add(0);
		lsLs.add(ls);
		ls = new ArrayList<Integer>();
		ls.add(1);ls.add(0);ls.add(1);ls.add(1);
		lsLs.add(ls);
		ls = new ArrayList<Integer>();
		ls.add(1);ls.add(1);ls.add(1);ls.add(1);
		lsLs.add(ls);
		int tpus= dem.numberAmazonTreasureTrucks(5, 4, lsLs);
		
		System.out.println("tpus: "+tpus);
	}
    
    
    
}
