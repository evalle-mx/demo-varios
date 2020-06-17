package jvdemo.pattern;
/*
 * http://javarevisited.blogspot.mx/2012/10/regular-expression-example-in-java-to-check-String-number.html
 */
import java.util.regex.Pattern;

/**
 * Ejemplo basico que demuestra el uso de Expresiones regulares en Java por medio de la libreria java.util.regex.Pattern
 * @author evalle
 *
 */
public class SimpleExpReg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//	     testPatterns();
		String cadena = "";//"123| null | -123 | 1.23 | -1.23";
		System.out.println(cadena + " es numero : ? " + isNumber(cadena));
		System.out.println(cadena + " es numero entero: ? " + isInteger(cadena));
		System.out.println(cadena + " es numero Positivo: ? " + isPositive(cadena));
		System.out.println(cadena + " es ID valido: ? " + isValidId(cadena));
	}
	
	
	public static boolean isNumber(String stNumber){
		//TODO, solo debe aceptar un punto
		if(stNumber != null && stNumber.length()>0){
			Pattern pattern = Pattern.compile(".*[^0-9.-].*");//acepta punto y negativos
			return !pattern.matcher(stNumber).matches();
		}
		return false;
	}
	public static boolean isInteger(String stNumber){
		if(stNumber != null){
			Pattern pattern = Pattern.compile(".*[^0-9-].*");  //acepta negativos
			return !pattern.matcher(stNumber).matches();
		}
		return false;
	}
	public static boolean isPositive(String stNumber){
		//TODO, solo debe aceptar un punto
		if(stNumber != null){
			Pattern pattern = Pattern.compile(".*[^0-9.].*"); //acepta punto
			return !pattern.matcher(stNumber).matches();
		}
		return false;
	}
	public static boolean isValidId(String stId){
		if(stId != null){
			Pattern pattern = Pattern.compile(".*[^0-9].*"); //original
			return !pattern.matcher(stId).matches();
		}
		return false;
	}
	
	
	public static void testPatterns(){
		// Regular expression in Java to check if String is number or not
        Pattern pattern = Pattern.compile(".*[^0-9].*");
       //Pattern pattern = Pattern.compile(".*\\D.*");
       String [] inputs = {"123", "-123" , "123.12", "abcd123"};
     
       for(String input: inputs){
           System.out.println( "El valor " + input + " es numero? : "
                                + !pattern.matcher(input).matches());
//        		   				+ isNumeric(input) );
       }
       System.out.println("\n Expresion regular para checar si la cadena es un digito de 6 numeros");
       // Regular expression in java to check if String is 6 digit number or not
       String [] numbers = {"123", "1234" , "123.12", "abcd123", "123456"};
       Pattern digitPattern = Pattern.compile("\\d{6}");       
       //Pattern digitPattern = Pattern.compile("\\d\\d\\d\\d\\d\\d");
       

       for(String number: numbers){
           System.out.println( "el " + number + " es un digito de 6 numeros: "
                               + digitPattern.matcher(number).matches());
       }
	}

}
