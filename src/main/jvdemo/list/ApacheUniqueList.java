package jvdemo.list;
/*
 * http://www.java2s.com/Code/Java/Apache-Common/ListExample1.htm
 */
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.collections.list.CursorableLinkedList;

import java.util.List;
import java.util.ListIterator;

/**
 * Clase demo que ocupa libreria de Apache para evitar duplicado en lista
 * @author dothr
 *
 */
public class ApacheUniqueList {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		ApacheUniqueList listExample = new ApacheUniqueList();
	    listExample.createLists();

	    uniqueList.add("Value1");
	    uniqueList.add("Value1"); 
	    System.out.println(uniqueList); // should contain only one element

	    cursorList.add("Element1"); 
	    cursorList.add("Element2"); 
	    cursorList.add("Element3"); 

	    ListIterator iterator = cursorList.listIterator();
	    iterator.next(); // cursor now between 0th and 1st element
	    iterator.add("Element2.5"); // adds this between 0th and 1st element

	    //System.err.println(cursorList); // modification done to the iterator are visible in the list
	    System.out.println(cursorList);
	  }

	  private void createLists() {
	    uniqueList = SetUniqueList.decorate(new TreeList());
	    cursorList = new CursorableLinkedList();
	  }

	  private static List uniqueList;
	  private static List cursorList;

}
