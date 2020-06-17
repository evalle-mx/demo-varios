package mayatrav;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import utily.FileUtily;
import utily.JsonUtily;

public class GeneraAngularHtml {
	
	static Logger log4j = Logger.getLogger( GeneraAngularHtml.class );
	
	protected static final String CHARSET = "UTF-8";
	/* Archivo json origen de los datos */
	
	protected static final String MAYA_DIR = "/home/dothr/workspace/MyProjects/mayaTravels/";
	//*/Esp
	protected static final String PROX_VIAJES_FILE = "/home/dothr/app/webServer/sitios/maya/resource/json/proxViajes.json";
	protected static final String LANG = "es/"; // en/ | 'es/' //*/
	/*/Inglés
	protected static final String PROX_VIAJES_FILE = "/home/dothr/app/webServer/sitios/maya/resource/json/proxViajes.en.json";
	protected static final String LANG = "en/"; // en/ | ''//*/
	
	/* Plantillas */
	protected static final String PV_HOME = "templates/"+LANG+"pv-home.htm"; //T_HOME_PR_VIAJE
	protected static final String PV_VIEW = "templates/"+LANG+"pv-view.htm"; //T_PR_VIAJE
	protected static final String PV_DIVMOVIL = "templates/"+LANG+"pv-divMovil.htm";//T_PR_VIAJEMOVIL
	protected static final String PV_DIVSCREEN = "templates/"+LANG+"pv-divScreen.htm"; //T_PR_VIAJESCREEN
	
//	protected static final StringBuilder SBPV_NOMORESCREEN = new StringBuilder("                    <!-- NO HAY MÁS VIAJES [Movil] -->\n                    ")
//		.append("<div class=\"col-sm-6\">\n                      ")
//		.append("<a href=\"home.html#proximosv\">\n                        ")
//		.append("<img class=\"img-fluid\" src=\"app/imagen/proximos/pvm-blank.jpg\" alt=\"Nuevos viajes proximamente\">\n                      ")
//		.append("</a>\n                    </div>\n");
	protected static final String PV_NOMORE_SCREEN = "templates/"+LANG+"pv-noMoreScreen.htm";
//	protected static final StringBuilder SBPV_NOMOREMOVIL = new StringBuilder("\n                    <!-- NO HAY MÁS VIAJES [Screen|] -->\n                    ")
//		.append("<div class=\"col-md-3 col-sm-6 mb-4\">\n                      ")
//		.append("<a href=\"home.html#proximosv\">\n                        ")
//		.append("<img class=\"img-fluid\" src=\"app/imagen/proximos/pvm-blank.jpg\" alt=\"Nuevos viajes proximamente\">\n                      </a>")
//		.append("                    </div>\n");
	protected static final String PV_NOMORE_MOVIL = "templates/"+LANG+"pv-noMoreMov.htm";
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		log4j.debug("<generaAll> ");		
		JSONArray jsViajes;
		String archivoSegmentos = "html/"+LANG+"_PROX-V_.htm";
		try{
			jsViajes = (JSONArray)JsonUtily.getJson(PROX_VIAJES_FILE);
			
			
			if(generaHomeDiv(jsViajes, MAYA_DIR+archivoSegmentos)){
				log4j.debug("<generaAll> se generó el archivo "+ MAYA_DIR+archivoSegmentos );
				if(generaVistasViaje(jsViajes, MAYA_DIR)){
					log4j.debug("<generaAll> se generaron archivos de vista.html " );
					log4j.debug("\n SE genero versión de idioma : "+LANG);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			log4j.fatal("<generaAll> Error al procesar: "+e.getMessage(), e );
		}
	}
	
	/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< */
		
	/**
	 * Procesa la descripcion a un formato HTML
	 * @param descripcion
	 * @return
	 */
	private static String descHtml(String descripcion){
		return descripcion.replace(". ", ".<br>");
	}
	/**
	 * Genera el HTML de listado de elementos incluidos en el viaje
	 * @param jsArr
	 * @return
	 * @throws Exception
	 */
	private static String incluyeHtml(JSONArray jsArr) throws Exception {
		StringBuilder sbHtml = new StringBuilder("\n");
		String ident = "                    ", elem, iconoLista="thumbs-o-up";
		if(jsArr!=null){
			for(int y=0; y<jsArr.length();y++){
				//sbHtml.append(ident).append("\t<li>").append(jsArr.get(y)).append("</li>\n");
				elem = jsArr.get(y).toString();
				if(elem.indexOf("Transpor")!=-1){
					iconoLista="bus";
				} else if(elem.toLowerCase().indexOf("vuelo")!=-1){
					iconoLista="plane";
				} else if(elem.toLowerCase().indexOf("hospedaje")!=-1){
					iconoLista="hotel";
				} else if(elem.toLowerCase().indexOf("comida")!=-1){
					iconoLista="cutlery";
				} else if(elem.toLowerCase().indexOf("desayuno")!=-1){
					iconoLista="coffee";
				} else if(elem.toLowerCase().indexOf("entrada")!=-1){
					iconoLista="ticket";
				} else if(elem.toLowerCase().indexOf("Ferry")!=-1){
					iconoLista="ship";
				}  else if(elem.toLowerCase().indexOf("yate")!=-1){
					iconoLista="ship";
				}  else if(elem.toLowerCase().indexOf("terrestre")!=-1){
					iconoLista="bus";
				} 
				
				
				else if(elem.indexOf("Box lunch")!=-1){
					iconoLista="shopping-basket";
				} else if(elem.indexOf("Conductor")!=-1){
					iconoLista="group";
				} else{
					iconoLista="thumbs-o-up";
				}
				
				//TODO cambiar cadena por objeto en JSON para determinar tipo de icono en lista
				sbHtml.append(ident).append("\t<li class=\"list-group-item\"> <span class=\"fa fa-")
				.append(iconoLista).append("\"></span> &nbsp;").append(jsArr.get(y)).append("</li>\n");
			}
		}else{
			//html = "<h3>No hay elementos en Incluye</h3>";
			sbHtml.append("<h3>No hay elementos en Incluye</h3>");
		}
		return sbHtml.toString();
	}
	
	/**
	 * Genera la subSección de viajes futuros
	 * @param jsArray
	 * @param indice
	 * @return
	 * @throws Exception
	 */
	private static String futurosHtml(JSONArray jsArray, int indice, int tipo) throws Exception {
		StringBuilder sb;
		String plantilla, pTmp=null;
		int maxDiv = 4;
		if(tipo == 1){ //Screen
			maxDiv = 4;
			plantilla =FileUtily.getBuilderFile(MAYA_DIR+PV_DIVMOVIL, CHARSET).toString(); 
		}else{//Movil
			maxDiv = 2;
			plantilla =FileUtily.getBuilderFile(MAYA_DIR+PV_DIVSCREEN, CHARSET).toString();
		}		 
		
		if(jsArray!=null && jsArray.length()>0){
			JSONObject json;
			sb = new StringBuilder();
			Integer duracion;
			String stDias, clTransp;
			int iDivMov = 0;
			for(int x=indice+1; x<jsArray.length();x++){
				if(iDivMov<maxDiv){
					json = jsArray.getJSONObject(x);
					duracion = new Integer(json.getString("duracion"));
					if(duracion==1){
						stDias="";
					}else{
						stDias ="s";
					}
					if(!json.has("clTransp")){
						Integer tipoTransporte = new Integer(json.getString("tipoTransporte"));
						if(tipoTransporte==1){
							clTransp="bus";
						}else if(tipoTransporte==2){
							clTransp="plane";
						}else if(tipoTransporte==0){
							clTransp="male";
						}else{
							clTransp="male";
						}
					}else{
						clTransp=json.getString("clTransp");
					}
					
					pTmp = plantilla;
					pTmp = pTmp.replace("{{INDICE}}", String.valueOf(x)).replace("{{name}}", json.getString("name")).replace("{{titulo}}", json.getString("titulo"));
					pTmp = pTmp.replace("{{sumario}}", json.getString("sumario")).replace("{{costo}}", json.getString("costo"));
					pTmp = pTmp.replace("{{duracion}}", String.valueOf(json.get("duracion"))).replace("{{stDias}}", stDias);					
					pTmp = pTmp.replace("{{clTransp}}", clTransp).replace("{{fecha}}", json.getString("fecha"));
					sb.append(pTmp);
					iDivMov++;
				}
			}
			if(pTmp == null){
				if(tipo==1){
					sb = FileUtily.getBuilderFile(MAYA_DIR+PV_NOMORE_SCREEN, CHARSET);
				}else{
					sb = FileUtily.getBuilderFile(MAYA_DIR+PV_NOMORE_MOVIL, CHARSET);
				}
				//sb = tipo == 1?SBPV_NOMOREMOVIL:SBPV_NOMORESCREEN;//new StringBuilder(noMore);
			}
		}
		else{
			//sb = tipo == 1?SBPV_NOMOREMOVIL:SBPV_NOMORESCREEN; //new StringBuilder(noMore);
			if(tipo==1){
				sb = FileUtily.getBuilderFile(MAYA_DIR+PV_NOMORE_SCREEN, CHARSET);
			}else{
				sb = FileUtily.getBuilderFile(MAYA_DIR+PV_NOMORE_MOVIL, CHARSET);
			}
		}
		return sb.toString();
	}
	/**
	 * Genera los html para cada viaje con detalle y ligas a futuros viajes
	 * @param jsArray
	 * @param outhPath
	 * @return
	 * @throws Exception
	 */
	public static boolean generaVistasViaje(JSONArray jsViajes, String outhPath) throws Exception {
		boolean res = false;
		log4j.debug("<generaPagsViaje> ");		
		JSONObject json;
		StringBuilder sbView;
		Integer duracion=1, tipoTransporte;
		String name, titulo, sumario, descripcion, costo, fecha, salida="", regreso="", linkEvento, condiciones="";
		
		String clDuracion, clTransp, stDias, pagina;
		String HIDECONDS, HIDESALIDA, HIDEREGRESO;
		
		String vistaHtml = FileUtily.getBuilderFile(MAYA_DIR+PV_VIEW, CHARSET).toString(), planTmp;
//		String paginaHtml = FileUtily.getBuilderFile(OUTPUT_DIR+T_PAG_VIAJE, CHARSET).toString();
		
		if(jsViajes!=null && jsViajes.length()>0){
			JSONArray arrIncluye;
			for(int x=0; x<jsViajes.length();x++){
				sbView = new StringBuilder();
				arrIncluye = null;
				HIDECONDS="hidden";
				HIDESALIDA="hidden";
				HIDEREGRESO="hidden";
				condiciones = "";
				
				json = jsViajes.getJSONObject(x);
				name = json.getString("name");
				titulo = json.getString("titulo");
				costo = json.getString("costo");
				fecha = json.getString("fecha");
				sumario = json.getString("sumario");
				descripcion = json.getString("descripcion");
				linkEvento = json.getString("linkEvento");
				
				/* opcionales */
				if(json.has("salida")){
					salida = json.getString("salida");
					HIDESALIDA = "";
				}
				if(json.has("regreso")){
					regreso = json.getString("regreso");
					HIDEREGRESO = "";
				}
				if(json.has("condiciones")){
					condiciones = json.getString("condiciones");
					HIDECONDS = "";
				}
				if(json.has("incluye")){
					arrIncluye = json.getJSONArray("incluye");
				}
				
				/* numerales */
				duracion = new Integer(json.getString("duracion"));
				tipoTransporte = new Integer(json.getString("tipoTransporte"));
				
				/* CONDICIONALES */
				if(duracion==1){
					clDuracion = "undia";
					stDias="";
				}else if(duracion==2){
					clDuracion="findesem";
					stDias ="s";
				}else{
					clDuracion="largo";
					stDias ="s";
				}
				json.put("clDuracion", clDuracion);
				
				if(tipoTransporte==1){
					clTransp="bus";
				}else if(tipoTransporte==2){
					clTransp="plane";
				}else if(tipoTransporte==0){
					clTransp="male";
				}else{
					clTransp="male";
				}
				json.put("clTransp", clTransp);
				pagina = name;
				json.put("pagina", pagina);
				
				planTmp = vistaHtml;
				planTmp = planTmp.replace("{{clDuracion}}", clDuracion).replace("{{name}}", name).replace("{{titulo}}", titulo);
				planTmp = planTmp.replace("{{sumario}}", sumario).replace("{{descripcion}}", descripcion).replace("{{costo}}", costo);
				planTmp = planTmp.replace("{{clTransp}}", clTransp).replace("{{duracion}}", String.valueOf(duracion)).replace("{{stDias}}", stDias);					
				planTmp = planTmp.replace("{{fecha}}", fecha).replace("{{pagina}}", pagina).replace("{{linkEvento}}", linkEvento);
				planTmp = planTmp.replace("{{salida}}", salida).replace("{{regreso}}", regreso).replace("{{condiciones}}", condiciones);
				
				//Despues de evaluar los parametros, se llenan los elementos CONDICIONADOS
				planTmp = planTmp.replace("{{HIDECONDS}}", HIDECONDS).replace("{{HIDESALIDA}}", HIDESALIDA).replace("{{HIDEREGRESO}}", HIDEREGRESO);
				planTmp = planTmp.replace("{{INCLUYELISTA}}", incluyeHtml(arrIncluye)).replace("{{HTMLDESCRIPCION}}", descHtml(descripcion))
						//.replace("{{VIAJESFUTURO}}", futurosHtml(jsArray, x));
						.replace("{{VFMOVIL}}", futurosHtml(jsViajes,x,1))
						.replace("{{VFSCREEN}}", futurosHtml(jsViajes,x,2));
				sbView.append(planTmp);
//				sb.append(paginaHtml.replace("{{HTML-t-pViajeX}}", planTmp));//planTmp);				
				FileUtily.writeStringInFile(outhPath+"html/"+LANG+name+".html", sbView.toString(), false);
				log4j.debug("<generaPagsViaje> se genero vista " + outhPath+"html/"+LANG+name+".html");				
			}
			res = true;
		}

		log4j.debug("<generaPagsViaje> Fin de metodo ");
		return res;
	}
	
	/**
	 * Genera un segmento HTML para insertar en la página de Home.html, en la sección "proximos"
	 * @param jsArray
	 * @param outhPath
	 * @return
	 * @throws Exception
	 */
	public static boolean generaHomeDiv(JSONArray jsViajes, String outhFile) throws Exception {
		boolean res = false;
		log4j.debug("<generaHomeDiv> ");
		
		JSONObject json;
		
		StringBuilder sbHtml = new StringBuilder("\t\t\t\t\t <!-- ########  GeneraHtml.java ").append(new java.util.Date()).append(" ########## --> ");
		StringBuilder sbRoutes = new StringBuilder("\n\n<!-- >>>>>>>>>>>>> Ruteo de vistas de Prox Viajes [script/routpv.js] <<<<<<<<<<<<<<<<< --> \n\n<script>\n'use strict';\n\n")
			.append("/* >>>>>>>>>>>>>> Routing <<<<<<<<<<<<<< */\n")
			.append("mayaApp.config(function ($locationProvider, $routeProvider) {\n")
			.append("    $routeProvider\n");
		StringBuilder sbHomeGalery = new StringBuilder("\n\n\t<!-- ####### GALERIA DE PROXIMOS VIAJES [section/home-gallery.html] #####  -->\n                  <!-- items galeria --> \n");

		Integer duracion=1, tipoTransporte;
		String name, titulo, sumario, descripcion, costo, fecha;
		String clDuracion, clTransp, stDias;
		String ruta = "";
			
			String plantilla = FileUtily.getBuilderFile(MAYA_DIR+PV_HOME, CHARSET).toString(), pTmp;
			
			if(jsViajes!=null && jsViajes.length()>0){
				for(int x=0; x<jsViajes.length();x++){
					json = jsViajes.getJSONObject(x);
					name = json.getString("name");
					titulo = json.getString("titulo");
					costo = json.getString("costo");
					fecha = json.getString("fecha");
					sumario = json.getString("sumario");
					descripcion = json.getString("descripcion");
					duracion = new Integer(json.getString("duracion"));
					tipoTransporte = new Integer(json.getString("tipoTransporte"));
					
					if(duracion==1){
						clDuracion = "undia";
						stDias="";
					}else if(duracion==2){
						clDuracion="findesem";
						stDias ="s";
					}else{
						clDuracion="largo";
						stDias ="s";
					}
					
					if(tipoTransporte==1){
						clTransp="bus";
					}else if(tipoTransporte==2){
						clTransp="plane";
					}else if(tipoTransporte==0){
						clTransp="male";
					}else{
						clTransp="male";
					}
					ruta = name;
					pTmp = plantilla;
					pTmp = pTmp.replace("{{clDuracion}}", clDuracion).replace("{{name}}", name).replace("{{titulo}}", titulo);
					pTmp = pTmp.replace("{{sumario}}", sumario).replace("{{descripcion}}", descripcion).replace("{{costo}}", costo);
					pTmp = pTmp.replace("{{clTransp}}", clTransp).replace("{{duracion}}", String.valueOf(duracion)).replace("{{stDias}}", stDias);					
					pTmp = pTmp.replace("{{fecha}}", fecha).replace("{{ruta}}", ruta);
					sbHtml.append(pTmp);
					
					/* Archivo Ruteo */
					if(x==0){
						//sbRoutes.append("    .when('/', {\n    	templateUrl: 'app/view/viajes/").append(ruta).append(".html'\n    })\n");
						sbRoutes.append("    .when('/', {\n    	templateUrl: 'app/view/viajes/").append(ruta).append(".html',\n            ")
						.append("controller :function ($scope, $route, $rootScope, $routeParams, $compile, $location, $anchorScroll ) {\n            ")
						.append("document.title = 'Maya Travels: ").append("Nuestros Próximos Viajes").append("';\n            $location.hash('info');\n")
						.append("            $anchorScroll();\n            $('[data-toggle=\"popover\"]').popover();\n        }\n    })\n");
					}
					//sbRoutes.append("    .when('/").append(ruta).append("', {\n    	templateUrl: 'app/view/viajes/").append(ruta).append(".html'\n    })\n");
					sbRoutes.append("    .when('/").append(ruta).append("', {\n    	templateUrl: 'app/view/viajes/").append(ruta).append(".html',\n            ")
					.append("controller :function ($scope, $route, $rootScope, $routeParams, $compile, $location, $anchorScroll ) {\n            ")
					.append("document.title = 'Maya Travels: ").append(titulo).append("';\n            $location.hash('info');\n")
					.append("            $anchorScroll();\n            $('[data-toggle=\"popover\"]').popover();\n        }\n    })\n");
					
					/* Archivo galeria Home */
					sbHomeGalery.append("                  <div class=\"item").append(x==0?" active":"")
					.append("\">\n                    <a href=\"proximosViajes.html#/")
					.append(ruta).append("\">\n                        <img src=\"img/home/")
					.append(ruta).append("-home.jpg\">\n                    </a>\n                  </div>\n");
				}
				sbRoutes.append("    .otherwise({\n	   redirectTo: '/'\n    });\n});\n</script>");
				sbHomeGalery.append("<!-- //FIN de Galeria -->\n");
				sbHtml.append(sbHomeGalery).append(sbRoutes);
				String sbString = sbHtml.toString();
				if(LANG.equals("en/")){
					sbString = sbString.replace("app/view/viajes/", "app/view/trips/");
				}
				FileUtily.writeStringInFile(outhFile, sbString, false);
				res = true;				
			}else{
				log4j.error("<generaHomeDiv> No hay elementos");
				res = false;
			}
		log4j.debug("<generaHomeDiv> Fin de metodo ");
		return res;
	}

}
