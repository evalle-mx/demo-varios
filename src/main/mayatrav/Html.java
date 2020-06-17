package mayatrav;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import utily.FileUtily;
import utily.JsonUtily;

public class Html {
	protected static final String CITY_TOURS_FILE = "/home/dothr/app/webServer/sitios/maya/app/json/cityTours.json";
	protected static final String PROX_VIAJES_FILE = "/home/dothr/app/webServer/sitios/maya/app/json/proxViajes.json";
	
	protected static final String HTM_DIR = "/home/dothr/workspace/MyProjects/files/htm/Maya/";
	protected static final String MAYA_FACE = "https://www.facebook.com/MayaTravelers/events/";
	
	protected static final String TEMPL_PROX_VIAJES = "temp-proxViajes.htm";
	protected static final String TEMPL_CITY_TOURS = "temp-cityTours.htm";
	protected static final String TEMPL_HOME_PV = "temp-homePViajes.htm";
	
	protected static final String URL_IMG= "app/img/";
	protected static final String DEF_IMG = "logo_mayatravels.png";
	protected static final String CHARSET = "UTF-8";
	
	static Logger log4j = Logger.getLogger( Html.class );
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		generaHtmlProxViajes();
//		generaHtmlCityTour();
	}
	
	
	public static void generaHtmlPvHome(){
		log4j.debug("<generaHtmlPvHome> Generando HTML para index.html (Home)...");
		StringBuilder sbHtml = new StringBuilder("        <!-- ******  Generado en HtmlGenerator (").append(new java.util.Date()).append(") **** [agregarlo dentro de: tab-content container] --> \n\n");
		
		JSONArray jsViajes;
		JSONObject json;
		try{
			jsViajes = (JSONArray)JsonUtily.getJson(PROX_VIAJES_FILE);
			log4j.debug("Viajes []: " + jsViajes.toString().replace("[{\"descV", "[\n{\"descV").replace("},{", "},\n{"));
			StringBuilder htm = FileUtily.getBuilderFile(HTM_DIR+TEMPL_HOME_PV, CHARSET);
			String stHtm;
			for(int x=0; x<jsViajes.length();x++){
				json = jsViajes.getJSONObject(x);
				stHtm = htm.toString();				
				if(x==0){
					stHtm = stHtm.replace("{{active}}", " active");
				}else{
					stHtm = stHtm.replace("{{active}}", "");
				}
				
				if(!json.has("homeImg")){
					if(!json.has("imgName")){
						json.put("imgUrl", URL_IMG+DEF_IMG);
					}else{
						json.put("imgUrl", URL_IMG+json.getString("imgName"));
					}
				}else{
					json.put("imgUrl", URL_IMG+json.getString("homeImg"));
				}
				
				sbHtml
				.append( stHtm
						 .replace("{{imgUrl}}", json.getString("imgUrl") )
						 .replace("{{fechaViaje}}", json.getString("fechaViaje") )
						);
			}
		}catch (Exception e){
			log4j.fatal("<generaHtmlPvHome> Error: ", e);
		}
		FileUtily.writeStringInFile(HTM_DIR+TEMPL_HOME_PV.replace("temp", "Generated"), sbHtml.toString(), false);
		
	}
	
	/**
	 * Genera codigo HTML a partir de la plantilla de proximos viajes
	 */
	public static void generaHtmlProxViajes(){
		log4j.debug("<generaHtmlProxViajes> Generando HTML para Proximos.html ...");
		StringBuilder sbHtml = new StringBuilder("        <!-- ******  Generado en HtmlGenerator (").append(new java.util.Date()).append(") **** [agregarlo dentro de: tab-content container] --> \n\n");
		
		StringBuilder sbMenu = new StringBuilder();
		String menuUlDesk = "<ul class=\"nav nav-pills visible-md visible-lg\" role=\"tablist\"> \n";
		String menuUlMovil = "<i class=\"visible-xs visible-sm\">Selecciona el Viaje de tu interés</i>\n" +
				"<ul class=\"nav nav-tabs responsive-tabs visible-xs visible-sm\" role=\"tablist\"> \n";
		String menuLiElem = "\t <li{{class}}><a data-toggle=\"tab\" role=\"tab\"" +
				 " title=\"{{fechaViaje}}\" alt=\"{{fechaViaje}}\" href=\"/#tabViaje{{idViaje}}\"> {{tabName}} </a></li>\n";
		JSONArray jsViajes;
		JSONObject json;
		try{
			log4j.debug("Obtiendo Json de proximos VIajes: \n " + PROX_VIAJES_FILE );
			jsViajes = (JSONArray)JsonUtily.getJson(PROX_VIAJES_FILE);
			log4j.debug("json: \n" + jsViajes);
			StringBuilder htm = FileUtily.getBuilderFile(HTM_DIR+TEMPL_PROX_VIAJES, CHARSET);
			String stHtm, idViaje;
			for(int x=0; x<jsViajes.length();x++){
				json = jsViajes.getJSONObject(x);
				stHtm = htm.toString();
				idViaje = ""+(x+1);
				log4j.debug(x + ": "+ json.getString("tituloViaje"));
				if(x==0){
					stHtm = stHtm.replace("{{active}}", "in active");
					//Menú
					sbMenu.append( menuLiElem.replace("{{class}}", " class=\"active\"").replace("{{idViaje}}", idViaje)
							.replace("{{fechaViaje}}", json.getString("fechaViaje")) .replace("{{tabName}}", json.getString("tabName")));
				}else{
					stHtm = stHtm.replace("{{active}}", "");
					//Menú
					sbMenu.append( menuLiElem.replace("{{class}}", "").replace("{{idViaje}}", idViaje)
							.replace("{{fechaViaje}}", json.getString("fechaViaje")) .replace("{{tabName}}", json.getString("tabName")));
				}
				/* Validación de elementos */
				if(!json.has("condiciones")){
					stHtm = stHtm.replace("**", "");
					json.put("condiciones", "");
				}
				if(!json.has("imgName")){
					json.put("imgUrl", URL_IMG+DEF_IMG);
				}else{
					json.put("imgUrl", URL_IMG+json.getString("imgName"));
				}
				
				if(json.has("incluye")){
					json.put("incluyeHtml", frmArrayIncluye( true, json.get("incluye"), "				           " ) );
					//json.put("incluyeMovil", frmArrayIncluye( false, json.get("incluye"),null) );
				}else{
					json.put("incluyeHtml", "");json.put("incluyeMovil","");
					stHtm = stHtm.replace("<b>Incluye</b>:", "");
				}
				
				sbHtml.append(
						stHtm.replace("{{idViaje}}", idViaje)
							.replace("{{imgUrl}}", json.getString("imgUrl"))
							.replace("{{fechaViaje}}", json.getString("fechaViaje"))
							.replace("{{duracionViaje}}", json.getString("duracionViaje"))
							.replace("{{tituloViaje}}", json.getString("tituloViaje"))
							.replace("{{costoViaje}}", json.getString("costoViaje"))
							.replace("{{descViaje}}", json.getString("descViaje"))
							.replace("{{incluye}}", json.getString("incluyeHtml"))
							.replace("{{condiciones}}", json.getString("condiciones"))
							.replace("{{salida}}", json.has("salida")?"<b>Salida:</b> "+json.getString("salida")+" <br>":"")
							.replace("{{regreso}}", json.has("regreso")?"<b>Regreso:</b> "+json.getString("regreso"):"")							
							.replace("{{linkEvento}}", json.has("linkEvento")?json.getString("linkEvento"):MAYA_FACE)
							.replace("{{salidaMv}}", json.has("salida")?"<b>Salida:</b> "+setSalidaRegreso(json.getString("salida"))+" <br>":"")
							.replace("{{regresoMv}}", json.has("regreso")?"<b>Regreso:</b> "+setSalidaRegreso(json.getString("regreso")):"")	
							//.replace("{{imgMv}}", json.has("imgMv")?URL_IMG+json.getString("imgMv"):json.getString("imgUrl"))
							//.replace("{{incluyeMv}}", json.getString("incluyeMovil"))
							);
			}			
			//Menús
			sbHtml.append("\n\n\t\t <!--  CODIGO DE MENÚ: --> \n")
			.append(menuUlDesk).append(sbMenu).append("\t</ul>\n\n")
			.append(menuUlMovil).append(sbMenu).append("\t<span class=\"glyphicon glyphicon-triangle-bottom\"></span>\t\n</ul>\n \t\t ");
			
		}catch (Exception e){
			log4j.fatal("<generaHtmlProxViajes> Error: ", e);
		}
		FileUtily.writeStringInFile(HTM_DIR+"Generated-proxViajes.htm", sbHtml.toString(), false);
		
		generaHtmlPvHome();
	}
	
	private static String setSalidaRegreso(String texto){
		return texto.replace("Deportivo", "Dptvo.").replace("Aeropuerto", "Apto.")
				.replace("dirección", "dir").replace("de la mañana", "a.m.")
				.replace("22 horas", "10:00 p.m.");
	}

	/**
	 * Genera codigo HTML a partir de la plantilla de City Tours
	 */
	public static void generaHtmlCityTour(){
		log4j.debug("<generaHtmlCityTour>");
		StringBuilder sbHtml = new StringBuilder("        <!-- ******  Generado en HtmlGenerator (").append(new java.util.Date()).append(") **** [agregarlo dentro de: tab-content container] --> \n\n");
		
		StringBuilder sbMenu = new StringBuilder();
		String menuUlMovil = "<i class=\"visible-xs visible-sm\">Selecciona el Tour de tu interés</i>\n" +
				"<ul class=\"nav nav-tabs responsive-tabs visible-xs visible-sm\" role=\"tablist\"> \n";
		String menuUlDesktop = "<ul class=\"nav nav-tabs visible-md visible-lg\" role=\"tablist\"> \n";
		
		String menuLiElem = "\t <li{{class}}><a data-toggle=\"pill\" role=\"tab\"" +
				 " href=\"/#tabTour{{idTour}}\"> Tour{{idTour}} </a></li>\n";
		JSONArray jsTours;
		JSONObject json;
		try{
			jsTours = (JSONArray)JsonUtily.getJson(CITY_TOURS_FILE);
			log4j.debug("Tours []: " + jsTours);
			StringBuilder htm = FileUtily.getBuilderFile(HTM_DIR+TEMPL_CITY_TOURS, CHARSET);
			String stHtm, idTour;
			for(int x=0; x<jsTours.length();x++){
				json = jsTours.getJSONObject(x);
				stHtm = htm.toString();
				idTour = ""+(x+1);	//json.getString("idTour");
				if(x==0){
					stHtm = stHtm.replace("{{active}}", " in active ");
					//Menú
					sbMenu.append( menuLiElem.replace("{{class}}", " class=\"active\"").replace("{{idTour}}", idTour));
				}else{
					stHtm = stHtm.replace("{{active}}", "");
					//Menú
					sbMenu.append( menuLiElem.replace("{{class}}", "").replace("{{idTour}}", idTour));
				}
				/* Validación de elementos */
				
				if(!json.has("imgName")){
					json.put("imgUrl", URL_IMG+DEF_IMG);
				}else{
					json.put("imgUrl", URL_IMG+json.getString("imgName"));
				}
				
				if(json.has("incluye")){
					json.put("incluyeHtml", frmArrayIncluye( true, json.get("incluye"), "					      		" ) );
					//json.put("incluyeMovil", frmArrayIncluye( false, json.get("incluye"),null) );
				}else{
					json.put("incluyeHtml", "");	//json.put("incluyeMovil","");
					stHtm = stHtm.replace("<b>Incluye</b>:", "");
				}

				sbHtml.append(
						stHtm.replace("{{idTour}}", idTour)
							.replace("{{imgUrl}}", json.getString("imgUrl"))
							.replace("{{tituloTour}}", json.getString("tituloTour"))
							.replace("{{costoTour}}", json.getString("costoTour"))
							.replace("{{incluye}}", json.getString("incluyeHtml"))
							);
			}
			
			//Menús
			sbHtml.append("\n\n\t\t <!--  CODIGO DE MENÚ: --> \n")
				.append(menuUlDesktop).append(sbMenu).append("\t</ul>\n")
				.append(menuUlMovil).append(sbMenu).append("\t<span class=\"glyphicon glyphicon-triangle-bottom\"></span>\t</ul>\n\n");
			
		}catch (Exception e){
			log4j.fatal("<generaHtmlCityTour> Error: ", e);
		}
//		log4j.debug("html Final: \n"+ sbHtml );
		FileUtily.writeStringInFile(HTM_DIR+"Generated-cityTours.htm", sbHtml.toString(), false);
	}
	
	
	private static String frmArrayIncluye(boolean desktop, Object jsArr, String ident){
		String html = "";
		JSONArray arreglo = (JSONArray)jsArr;
		try{
			if(desktop){  //Desktop (<ul>...</ul>)
				if(ident==null){
					ident = "\t";
				}
				html = "<ul>\n";
				for(int y=0; y<arreglo.length();y++){
					html+=ident+"  <li>"+arreglo.get(y)+"</li>\n";
				}				
				html += ident+"</ul>";
			}else{  //Movil
				html = "<b>Incluye:</b>";
				for(int y=0; y<arreglo.length();y++){
					html+=" "+arreglo.get(y)+(y==arreglo.length()-1?".":",");
				}
			}
		}catch (Exception e){
			log4j.error("Error", e);
		}
		
		return html;
	}
	
//	/**
//	 * inicializa un objeto JSON (Object/Array) a partir de un archivo en la ruta recibida
//	 * @param jsInstance
//	 * @param jsonPath
//	 */
//	public static Object getJson(String jsonPath){
//		//JSONArray jsInstance=null;
//		Object jsInstance = null;
//		try {
//			BufferedReader infile = new BufferedReader(
//	        		 new InputStreamReader(
//	                         new FileInputStream(jsonPath), CHARSET));
//	        String strLine;
//	        StringBuilder sb = new StringBuilder();
//	        while ((strLine = infile.readLine()) != null) 
//	        {
//	            sb.append(strLine);
//	        }
//	        infile.close();
//	        
//	        if(sb.toString().trim().startsWith("[")){
//	        	jsInstance = new JSONArray( sb.toString() );
//	        }else{
//	        	jsInstance = new JSONObject( sb.toString() );
//	        }	        
//		} catch (Exception e) {			
//			log4j.fatal("Excepción al generar Json ", e);
//		}
//		return jsInstance;
//	}

}
