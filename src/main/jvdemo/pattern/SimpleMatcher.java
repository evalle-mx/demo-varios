package jvdemo.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleMatcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		simpleMatch();
	}

	
	
	protected static void simpleMatch(){
		Matcher mat;
		mat = Pattern.compile("^http://tododefinicionesalimentarias.icoval.org/diccionario/filter:[a-z]{1}[/page/[0-9]+/]*").
				  matcher("http://tododefinicionesalimentarias.icoval.org/diccionario/filter:a/page/5/");
		
		
		if (mat.matches()) {
	         System.out.println("Coincide");
	     } else {
	         System.out.println("NO Coincide");
	     }
		
	}
}

		//^.*?/APP/v[0-9]+/(.*)$ 	-> 	http://foo.com/APP/v7/main.html
		//							-> 	/APP/v75/main.html
		// ^/(xml)?/$				->	/xml/		
		// 	^(?!.*/xml).*$			->	hola
	//	 mat = Pattern.compile("^((?!.*/xml).)*$").matcher("http://foo.com/xml/APP/v7/main.html");		
		// mat = Pattern.compile("^[0-9]+$").matcher("3434");
		// mat = Pattern.compile("^http://iesmonre.educa.aragon.es/dep/mates/webtic/glosario/((cabecera.htm)|([a-z]{1}.htm))*").
					//	matcher("http://iesmonre.educa.aragon.es/dep/mates/webtic/glosario/creditos.htm");
		// mat = Pattern.compile("^query=([A-Z]{1})([\\*]{1})").matcher("query=W*");	
		// mat = Pattern.compile("^http://www.arearh.com/glosario/([A-Z]{2}).html$").
				//matcher("http://www.arearh.com/glosario/AD.html");	
		// mat = Pattern.compile("^([a-z0-9]*\\.)*www").matcher(".www");
		// mat = Pattern.compile("^http://www.internetglosario.com/(letra-([a-z]{1}).html)*").
					 // matcher("http://www.internetglosario.com/1022/Adobe.html");
		/* mat = Pattern.compile("^http://www.internetglosario.com/([0-9]+/[A-Za-z]+.html)+").
					matcher("http://www.internetglosario.com/3434/ACC.html");*/
		// mat = Pattern.compile("^[0-9]+(%|\\)|\\.)*(.[0-9]+)*$").matcher("30%");
		// numeros: 30, 30., 30), 30%, 1.3.4, 1.2
		//http://www.metrocuadrado.com/m2-content/cms-content/glosario/ARTICULO-WEB-GLOSARIO_M2-2033436.html
		// mat = Pattern.compile("^http://www.metrocuadrado.com/m2-content/cms-content/glosario/ARTICULO-WEB-GLOSARIO_M2-[0-9]+.html").
		//matcher("http://www.metrocuadrado.com/m2-content/cms-content/glosario/ARTICULO-WEB-GLOSARIO_M2-20332f436.html");		
		//[George H. Wilson, Cleveland, Ohio, dentista estadounidense, 185S.1922J:
		//[Robert Abbe, Nueva York, N. Y., cirujano, 1851.1928]
		//[Roger Anderson, cirujano ortopédico estadounidense, 1891-1971]