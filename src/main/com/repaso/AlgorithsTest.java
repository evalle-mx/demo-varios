package com.repaso;

import java.util.Stack;

public class AlgorithsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		minDiff();
//		testPrintSpiral();
		removeBrackets();
	}

	/**
	 * 1. Find two numbers who have the minimal difference
	 */
	public static void minDiff(){
		//numbers = 1, 5, 3, 19, 18, 25 
		// min dif = 19-18 =>|1|
		Integer [] nums = {1, 5, 3, 19, 18, 25};
		Integer dif = Integer.MAX_VALUE, fA = new Integer(0), fB= new Integer(0);
		Integer tmpA, tmpB, tmpDif;
		for(int x=0; x<nums.length-1; x++){
		   tmpA = nums[x];
		   for(int y=x+1;y<nums.length;y++){
			   tmpB = nums[y];
			   tmpDif=Math.abs(tmpA-tmpB);
			   if(tmpDif<dif){
				   dif=tmpDif;
				   fA = tmpA;
				   fB = tmpB;
			   }
		   }
		}
		
		System.out.println("MIn dif = "+ dif );
		System.out.println("fA: "+ fA + ", fB: "+fB);
	}
	
	
	public static void testPrintSpiral(){
		int[][] arrayN_N = new int[][]{
				{1,2,3},
				{4,5,6},
				{7,8,9},
				{10,11,12}
				};
		//Output: 1 2 3 6 9 12 11 10 7 4 5 8
		printSpiral(arrayN_N, arrayN_N.length, arrayN_N[0].length);
	}
	
	/**
	 * print an NxN array content as a clockwise
	 */
	public static void printSpiral(int[][] arrNN, int nRows, int nCols){
		int iTop=0;//minimo superior [iTop][i]
		int iBottom=nRows-1;//Maximo inferior [iBottom][i]
		
		int iLeft=0;//minimo izquierda (inicial)
		int iRight=nCols-1;//Maximo derecha (final)
		
		int index=-1;//Indice movil requerido
		
		int direction = 0;	//0 derecha, 1 abajo, 2 izquierda, 3 arriba
		String outPut = "";
		while(iTop<=iBottom && iLeft<=iRight){
			if(direction==0){ //fila superior, va de izquierda a derecha ([top][i]
				for(index=iLeft;index<=iRight;index++){
					outPut+=arrNN[iTop][index]+" ";
				}
				iTop++;
				direction=1;
			}
			if(direction==1){ //filas intermedias descendente, se imprime el ultimo elemento ([i][right])
				for(index=iTop;index<=iBottom;index++){
					outPut+=arrNN[index][iRight]+" ";
				}
				iRight--;
				direction=2;
			}
			
			if(direction==2){//fila inferior, va de derecha a izquierda ([bottom][i])
				for(index=iRight;index>=iLeft;index--){
					outPut+=arrNN[iBottom][index]+" ";
				}
				iBottom--;
				direction=3;
			}
			if(direction==3){ //filas intermedias ascendente, se imprime el primer elemento ([i][left])
				for(index=iBottom;index>=iTop;index--){
					outPut+=arrNN[index][iLeft]+" ";
				}
				iLeft++;
				direction=0;
			}
			
		}
		
		System.out.println(outPut);
		
		
	}
	
	
	
	public static void removeBrackets() {
		String linea = 
//				"()))((()";	//0 =>Incoh
//				"";// => 0 //Cohe
//			 "(()(";// => 2
//				"))()";// => 2'
//			 "(())()(((";// => 3
//			 "))((";// => 4
//			 "))(()))))))))";// => 9
//			 "(())))))))))(())))))(((((";// => 17
			 "))))))))))(())))))((((((((((((((";// => 28
		char open = '(';
		char close = ')';
		System.out.println("Ecuación: " + linea );
		int iOpen =0, iClose =0;
		//contar balanceo
        char[] chrs =linea.toCharArray();   
        for(int i=0;i<chrs.length; i++){
            
            if(chrs[i]==open){
                //llaves de apertura
                iOpen++;
                iClose--;
            }
            if(chrs[i]==close){
                //llaves de cerrado
                iClose++;
                iOpen--;
            }   
        }
        
        if(iOpen!=iClose){
            System.out.println("No esta balanceada la ecuación ");
            System.out.println("es requerido eliminar " + (iOpen>iClose? (iOpen)+" parentesis '(' ": (iClose)+" parentesis ')' ") );            
        }
        else{        	
        	System.out.println("Balanceada");
        	//Checar coherencia
        	System.out.println("iOpen:" +iOpen + ", iClose: "+iClose);
        }
        
        //Verificamos COherencia:
        Stack<String> pila = new Stack<String>();
        
        boolean coherente = true;
        String outPath = "";
        for(int i=0;i<chrs.length; i++){
            
            if(chrs[i]==open){
            	pila.push("(");
            }
            else if(chrs[i]==close){
            	//3.1 
            	if (!pila.empty()){ 
            		pila.pop(); 
            	}
            	//3.2
            	else { 
//            		pila.push(")");
            		outPath += "Es incorrecto ')' en "+i+", requiere abrir parentesis ( \n";
            		coherente = false;
            	}
            }
        }
        
        if(!pila.isEmpty()){
        	coherente=false;
        	outPath+="\n Eliminar "+pila.size()+" elementos";
        }
        System.out.println(outPath + " \n" + (coherente?"Es coherente":"no es coherente"));
		
	}
	
	
	public static boolean verificaParentesis(String cadena)  {

        Stack<String> pila = new Stack<String>();       int i = 0;

            while (i<cadena.length()) {  // Recorremos la expresión carácter a carácter

                if(cadena.charAt(i)=='(') {pila.push("(");} // Si el paréntesis es de apertura apilamos siempre                               

                else if  (cadena.charAt(i)==')') {  // Si el paréntesis es de cierre actuamos según el caso

                            if (!pila.empty()){ pila.pop(); } // Si la pila no está vacía desapilamos

                            else { pila.push(")"); break; } // La pila no puede empezar con un cierre, apilamos y salimos

                }

                i++;

            }

            if(pila.empty()){ return true; } else { return false; }

    }
}
