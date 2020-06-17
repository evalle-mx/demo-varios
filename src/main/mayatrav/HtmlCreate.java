package mayatrav;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utily.FileUtily;
import utily.JsonUtily;

public class HtmlCreate extends Html{

	static Logger log4j = Logger.getLogger( HtmlCreate.class );
	protected static final String TEMPL_VISTA_PV = "template-VistaPViajes.htm";
	protected static final String TEMPL_MENU_PV = "template-MenuPV.htm";
	protected static final String TEMPL_AppJS = "TEMP-pvrout.txt";
	
	private static final String PV_OUTPUT = "/outPut/_multElementsPV.html";
	
	public static void main(String[] args) {
		generaProximosViajes();
	}
	
	
	
	
	/**
	 * Metodo principal para construir elementos de Proximos Viajes
	 * @return
	 */
	public static boolean generaProximosViajes(){
		boolean res = false;
		log4j.debug("<generaProximosViajes> ");		
		JSONArray jsArray;
		StringBuilder sb;
				
		try {
			jsArray = (JSONArray)JsonUtily.getJson(PROX_VIAJES_FILE);
			
			if(jsArray!=null && jsArray.length()>0){
				/* 1.- Agregar segmento carousel proximos viajes en Home */
				sb = genHomePVCarousel(jsArray);
				FileUtily.writeStringInFile(HTM_DIR+PV_OUTPUT, sb.toString(), false);
				log4j.debug("<generaProximosViajes> Se añadio Carousel al archivo "+ HTM_DIR+PV_OUTPUT );
				
				/* 2.- Agregar Routing en app.js */
				sb = genAppJsRouter(jsArray);
				FileUtily.writeStringInFile(HTM_DIR+PV_OUTPUT, sb.toString(), true);
				log4j.debug("<generaProximosViajes> Se añadio Routing al archivo "+ HTM_DIR+PV_OUTPUT );
				
				/* 3.- Agregar Menu (Botones/Tab) de proximos viajes [prox-menu.html] */
				sb = generaPV_MenuTabs(jsArray);
				//FileUtily.writeStringInFile(HTM_DIR+"/outPut/prox-menu.html", sb.toString(), false);
				FileUtily.writeStringInFile(HTM_DIR+PV_OUTPUT, sb.toString(), true);
				log4j.debug("<generaProximosViajes> Se añadio menu(angular) al archivo "+ HTM_DIR+PV_OUTPUT );
				
				
				
				//Generar Archivo pv-Views.html (viajesHome.html)
				sb = generaPV_MenuHome(jsArray);
				FileUtily.writeStringInFile(HTM_DIR+"/outPut/pv-viajes.html", sb.toString(), false);
				log4j.debug("<generaProximosViajes> Se escribio archivo "+ HTM_DIR+"/outPut/pv-viajes.html" );
				
				//Generar archivos <viaje>.html por Viaje
				int generados = generaPV_Views(jsArray);
				if(generados == jsArray.length()){
					log4j.debug("<generaProximosViajes> se generarón correctamente los " + generados +" Elementos");
					res = true;
				}
			}else{
				log4j.error("<generaProximosViajes> No hay elementos");
			}			
		} catch (Exception e) {
			log4j.debug("<generaProximosViajes> Error al procesar, No se conluye correctamente el proceso: \n", e);
			res = false;
		}	
		log4j.debug("<generaProximosViajes> Fin de Proceso ");
		return res;
	}
	
	
	protected static StringBuilder genAppJsRouter(JSONArray jsArray) throws Exception {
		StringBuilder plantilla = FileUtily.getBuilderFile(HTM_DIR+TEMPL_AppJS, CHARSET);
		StringBuilder sbRouting = new StringBuilder();
		JSONObject json;
		
		for(int x=0; x<jsArray.length();x++){
			json = jsArray.getJSONObject(x);
			sbRouting.append(
					routeTemplate.replace("{{name}}", json.getString("name")).replace("{{titulo}}", json.getString("titulo")) 
					);
		}		
		StringBuilder sbPVJs = new StringBuilder("<!-- ++++++++++++++++++++++++++++++++++++ Routing Proximos Viajes [app/scripts/app.js] ++++++++++++++++++++++++++++++++++++ --> \n")
		.append("<script>\n");
		
		sbPVJs.append(plantilla.toString().replace("<<ROUTING>>", sbRouting.toString()) ).append("\n");
		
		sbPVJs.append("</script>\n\n\n\n");
		return sbPVJs;
	}
	/**
	 * Genera codigo que se colocará en el Home para el carousel Principal
	 * @param jsArray
	 * @return
	 * @throws Exception
	 */
	protected static StringBuilder genHomePVCarousel(JSONArray jsArray) throws Exception {
		StringBuilder sbPVTabMenu = new StringBuilder("<!-- ++++++++++++++++++++++++++++++++++++ Carousel Proximos Viajes [app/section/home-principal.html] ++++++++++++++++++++++++++++++++++++ --> \n");
		String name = "";	
		JSONObject json;
		//TODO pasar los segmentos html a plantillas <<ITEMS>>, <<INDICADORES>>
		sbPVTabMenu.append("                        <!-- Carousel Principal -->").append("\n")
		.append("                        <div id=\"carousel-principal\" class=\"carousel slide carousel-fade\" data-ride=\"carousel\" data-interval=\"3200\">").append("\n")
        .append("                            <!-- Wrapper para Imagenes principales -->").append("\n")
        .append("                            <div class=\"carousel-inner\" title=\"Dá CLICK para ver nuestros proximos viajes!!\">").append("\n");
        
		//Agrega los ITEMS
		for(int x=0; x<jsArray.length();x++){
			json = jsArray.getJSONObject(x);
			name = json.getString("name");
			sbPVTabMenu.append("                                 <!-- ").append(json.has("titulo")?json.getString("titulo"):name).append(" -->").append("\n")
				.append("                                <div class=\"item").append(x==0?" active":"").append("\">").append("\n")
				.append("                                    <a href=\"proximos.html#/").append(name).append("\">").append("\n")
				.append("                                        <img src=\"app/img/pv-").append(name).append(".jpg\" class=\"img-thumbnail\">").append("\n")
				.append("                                    </a>").append("\n")
				.append("                               </div>").append("\n");
		}
		// Agrega los INDICADORES
		sbPVTabMenu.append("                            </div>").append("\n")
		.append("                            <!-- Indicadores -->").append("\n")
		.append("                            <ol class=\"carousel-indicators\" id=\"indicator-principal\">").append("\n");
		for(int x=0; x<jsArray.length();x++){
			sbPVTabMenu.append("                                <li class=\"").append(x==0?"active":"")
				.append("\" data-target=\"#carousel-principal\" data-slide-to=\"").append(x).append("\">")
				.append("<!-- <span class=fa fa-bus></span> -->")
				.append("</li>").append("\n");
		}		
		sbPVTabMenu
		.append("                            </ol>").append("\n")
		.append("                            <!-- direccionales -->").append("\n")
		.append("                            <a class=\"left carousel-control\" href=\"#carousel-principal\" role=\"button\" data-slide=\"prev\">").append("\n")
		.append("                                <span class=\"glyphicon glyphicon-chevron-left\" aria-hidden=\"true\"></span>").append("\n")
		.append("                                <span class=\"sr-only\">Anterior</span>").append("\n")
		.append("                            </a>").append("\n")
		.append("                            <a class=\"right carousel-control\" href=\"#carousel-principal\" role=\"button\" data-slide=\"next\">").append("\n")
		.append("                                <span class=\"glyphicon glyphicon-chevron-right\" aria-hidden=\"true\"></span>").append("\n")
		.append("                                <span class=\"sr-only\">Siguiente</span>").append("\n")
		.append("                            </a>").append("\n")
		.append("                        </div><!-- //Carousel Principal -->").append("\n")
		.append("\n\n\n\n");
		return sbPVTabMenu;
	}
	/**
	 * Genera el HTML de Menu (Tabs) de Proximos Viajes
	 * @param jsArray
	 * @return
	 * @throws Exception
	 */
	protected static StringBuilder generaPV_MenuTabs(JSONArray jsArray) throws Exception {
		StringBuilder sbPVTabMenu = new StringBuilder("<!-- ++++++++++++++++++++++++++++++++++++ Menú tabulado o botones  [app/section/prox-menu.html] ++++++++++++++++++++++++++++++++++++ --> \n");
		JSONObject json;
		//TODO pasar los segmentos html a plantillas <<TABS>>
		sbPVTabMenu
		.append("       <div class=\"row\" id=\"deskmenu\">\n")
		.append("                <div class=\"col-lg-12\" >\n")
		.append("                    <div class=\"visible-md visible-lg\" style=\"line-height: 60px;\">\n");
		
		for(int x=0; x<jsArray.length();x++){
			try{
				json = jsArray.getJSONObject(x);				
				sbPVTabMenu.append("\t\t\t\t\t\t <a class=\"btn btn-default\" href=\"#/")
				.append(json.getString("name")).append("\" role=\"button\">")
				.append(json.getString("tabName")).append("</a> \n");
			}catch (Exception e){
				log4j.debug("Error al procesar elemento " + x, e);
				throw e;
			}
		}
		
		sbPVTabMenu.append("                    </div>\n\n                    <div style=\"text-align: center; font-size: 18px;\" >\n")
		.append("                        <a class=\"visible-xs visible-sm\" href=\"#/\" style=\"color: #fff; text-decoration: none;\">\n\t\t\t\t\t\t\t<span class=\"fa fa-th\"></span> &nbsp;Ver Todos</a>\n")
		.append("                    </div>\n                </div>\n        </div>").append("\n");
		
		return sbPVTabMenu;
	}
	
	
	/**
	 * Genera el HTML de Menu (HOME) de Proximos Viajes
	 * @param jsArray
	 * @return
	 * @throws Exception
	 */
	protected static StringBuilder generaPV_MenuHome(JSONArray jsArray) throws Exception {
		StringBuilder sbPVMenu = new StringBuilder();
		log4j.debug("<generaProximosViajes> ");
		String pvMenu = "";
		int panelByRow = 4, panelNum=1;
		JSONObject json;
		//TODO pasar los segmentos html a plantillas <<PANEL-ROWS>>
		sbPVMenu.append("       \n        <div class=\"row\" id=\"pv-top\">\n	        <div class=\"col-sm-12 col-md-12\">\n")
		.append("                <p>\n                    ")
		.append("Te mostramos las opciones de viajes proximos a realizar, solo <b>dále click </b> en el de tu interés.\n")
		.append("                </p>\n            </div>\n        </div>\n\n")
		.append("        <!-- Renglon 1 -->\n        <div class=\"row\">\n");

		for(int x=0; x<jsArray.length();x++){
			try{
				json = jsArray.getJSONObject(x);
				json.put("idViaje", String.valueOf(x+1));
				pvMenu = formatedTemplate(HTM_DIR+TEMPL_MENU_PV, json);
				sbPVMenu.append("\n").append(pvMenu);
				if(panelNum==panelByRow){
					sbPVMenu.append("        </div><!-- //row -->\n\n        <!-- Renglon ")
						.append((x/panelByRow)+2).append(" -->\n        <div class=\"row\">\n");
					panelNum=0;
				}
				panelNum++;
			}catch (Exception e){
				log4j.debug("Error al procesar elemento " + x, e);
				throw e;
			}
		}
		sbPVMenu
			.append("\n        </div><!-- //row -->\n")
			.append("\n\t\t<a class=\"visible-xs visible-sm\" style=\"color: #fff; text-decoration: none;\" ")
			.append(" ng-click=\"scrollTo('pv-top')\">\n<span class=\"fa fa-level-up\"></span> Inicio</a>\n");		
		return sbPVMenu;
	}
	/**
	 * Genera las vistas por cada elemento del JSON prox-viajes.json
	 * @param jsArray
	 * @return
	 * @throws Exception
	 */
	protected static int generaPV_Views(JSONArray jsArray) throws Exception{
		log4j.debug("<generaPV_Views> ");
		int generados = 0;
		String vista= "", name ="";
		JSONObject json;

		for(int x=0; x<jsArray.length();x++){
			try{
				json = jsArray.getJSONObject(x);
				name = json.getString("name");
				json.put("idViaje", String.valueOf(x+1));
				preProcessJson(json, x);
				vista = formatedTemplate(HTM_DIR+TEMPL_VISTA_PV, json );
				//log4j.debug("<generaProximosViajes> vista: \n"+ vista);
				FileUtily.writeStringInFile(HTM_DIR+"/outPut/pv-"+name+".html", vista, false);
				generados++;
			}catch (Exception e){
				log4j.debug("Error al procesar elemento " + x, e);
				throw e;
			}
		}
		log4j.debug("<generaPV_Views> generados "+generados+" archivos view.html");
		return generados;
	}
	
	/**
	 * Asigna y procesa datos para la salida en Texto
	 * @param json
	 * @param id
	 * @throws JSONException
	 */
	protected static void preProcessJson(JSONObject json, int id) throws JSONException{
		if(json.has("salida")){
			json.put("salidaMv", setUbicacion(json.getString("salida")) );
		}
		if(json.has("regreso")){
			json.put("regresoMv", setUbicacion(json.getString("regreso")) );
		}
		if(json.has("duracion")){
			if(json.getString("duracion").equals("1")){
				json.put("duracion", json.getString("duracion").concat(" Día"));
			}else{
				json.put("duracion", json.getString("duracion").concat(" Días"));
			}
		}
		if(json.has("costo") && json.has("condiciones")){
			json.put("costo", json.getString("costo").concat(" **"));
			json.put("condiciones", "** "+json.getString("condiciones"));
		}
		
		if(json.has("promoPdf")){
			json.put("showDoc", "");
		}else{
			json.put("showDoc", " hidden");
		}
		
		/* Para indicar elemento inicial a Bootstrap */
		if(id==0){
			json.put("active", "active");
		}else{
			json.put("active", "");
		}
		
	}
	
	
	/* ************************************************************************************************************ */
	/**
	 * Reemplaza algunos tokens/Palabras por su equivalente (para Movil)
	 * @param saliReg
	 * @return
	 */
	private static String setUbicacion(String saliReg){
		return saliReg.replace("Deportivo ", "Dptvo. ").replace("Aeropuerto ", "Apto. ")
				.replace("dirección ", "dir. ").replace("mañana ", "a.m.").replace("noche ", "p.m.").replace("tarde ", "p.m.");
	}
	
	/**
	 * Lee el template y lo envia al procesamiento para reemplazar los tags incluidos en el Json
	 * @param pathTemplate
	 * @param jsonValues
	 * @return
	 * @throws Exception
	 */
	private static String formatedTemplate(String pathTemplate, JSONObject jsonValues) throws Exception{
		StringBuilder htm = FileUtily.getBuilderFile(pathTemplate, CHARSET);
		//String tempTest = "<div class=\"visible-md visible-lg\">\n{{salida}}\n{{regreso}}\n</div";
		return TagsEditor.replaceTags(htm.toString(), jsonValues);
	}
	
	
	
	private static String routeTemplate = "    .when('/{{name}}', {" + "\n"+
			"      templateUrl: 'app/views/pv-{{name}}.html'," + "\n"+
			"        controller :function ($scope, $route, $rootScope, $routeParams, $compile, $location ) {" + "\n"+
			"          document.title = '{{titulo}}';" + "\n"+
			"          $('#deskmenu').show();" + "\n"+
			"          $scope.scrollTo('pv-{{name}}');" + "\n"+
			"        }" + "\n"+
			"    })\n";
}
