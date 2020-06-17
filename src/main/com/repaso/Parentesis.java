package com.repaso;

import java.util.Stack;

public class Parentesis {

//	Pila pila = new Pila();  // Pila para guardar los parentesis de apertura
	Stack<Character> pila = new Stack<Character>();


	  public static void main (String [] pps) {
		  String linea = 
//					"";// => 0
				 "(()(";// => 2
//				 "(())()(((";// => 3
//				 "))((";// => 4
//				 "))(()))))))))";// => 9
//				 "(())))))))))(())))))(((((";// => 17
//				 "))))))))))(())))))((((((((((((((";// => 28
//	      if (pps.length != 1) {
//		  System.out.println("Forma de uso: java Parentesis expresion");
//	      } else {
//		  new Parentesis(pps[0]);
//	      }
		  new Parentesis(linea);
	  }

	    /*
	     * Metodo para verificar que una expresion tiene balanceados sus parentesis
	     * @param linea -- cadena que tiene la expresion a examinar
	     */
	  public Parentesis (String linea) {
	      for (int i = 0; i < linea.length(); i++) {
		  if (linea.charAt(i) == '(') pila.push(new Character(')'));
		  else if (linea.charAt(i) == '{') pila.push(new Character('}'));
		  else if (linea.charAt(i) == '[') pila.push(new Character(']'));
		  else if (linea.charAt(i) == ')') verifica(')');
		  else if (linea.charAt(i) == '}') verifica('}');
		  else if (linea.charAt(i) == ']') verifica(']');
	      }

	      if (pila.isEmpty())//estaVacia())
		  System.out.println("Parentesis balanceados");
	      else 
		  System.out.println("Parentesis NO balanceados");	  
	  }

	    /*
	     * Metodo privado que recibe un parentesis de cerrado y verifica que en
	     * el tope de la pila se encuentre el de apertura
	     * @param c -- parentesis de cerrado
	     */
	  private void verifica (char c) {
	      if (pila.isEmpty()) {
		  System.out.println("Parentesis NO balanceados"); 
		  System.exit(0); 
	      } else {
		  Character s = (Character) pila.pop();  
		  if (c != s.charValue()) {
		      System.out.println("Parentesis NO balanceados"); 
		      System.exit(0); 
		  }
	      }
	  }

}
