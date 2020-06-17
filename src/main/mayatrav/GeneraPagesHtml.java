package mayatrav;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import utily.FileUtily;
import utily.JsonUtily;

public class GeneraPagesHtml {
	
	static Logger log4j = Logger.getLogger( GeneraPagesHtml.class );
	
	protected static final String CHARSET = "UTF-8";
	protected static final String PROX_VIAJES_FILE = "/home/dothr/app/webServer/sitios/maya/resource/json/proxViajes.json";
	protected static final String HTM_DIR = "/home/dothr/workspace/MyProjects/mayaTravels/";
//	protected static final String MAYA_FACEEVENTS = "https://www.facebook.com/pg/MayaTravelsCDMX/events/";
	
	/* Plantillas */
	protected static final String T_HOME_PR_VIAJE = "templates/t-pViajeHome.htm";
	protected static final String T_PR_VIAJE = "templates/t-pViajeX.htm";
	//protected static final String T_PR_VIAJE1 = "templates/t-pViajeX1.htm";
	protected static final String T_PR_VIAJEMOVIL = "templates/t-pVFMovil.htm";
	protected static final String T_PR_VIAJESCREEN = "templates/t-pVFScreen.htm";
	
	protected static final String T_PAG_VIAJE = "templates/t-Viaje.html";
	
	public static void main(String[] args) {
		generaAll();
	}
	
	
	public static boolean generaAll(){
		boolean res = false;
		log4j.debug("<generaAll> ");		
		JSONArray jsArray;
//		JSONObject json;
		
		try{
			jsArray = (JSONArray)JsonUtily.getJson(PROX_VIAJES_FILE);
			
			
			if(generaHomeDiv(jsArray, HTM_DIR+"out/_home-proximosV_.html")){
				log4j.debug("<generaAll> se genero el archivo "+ HTM_DIR+"_home-proximosV_.html" );
				if(generaPagsViaje(jsArray, HTM_DIR)){
					log4j.debug("<generaAll> se generaron archivos html " );
					res = true;
				}
				
			}
		}catch (Exception e){
			e.printStackTrace();
			log4j.fatal("<generaAll> Error al procesar: "+e.getMessage(), e );
		}
		
		return res;
	}
	
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
		String ident = "                    ";
		if(jsArr!=null){
			for(int y=0; y<jsArr.length();y++){
				//html+=ident+"\t<li>"+jsArr.get(y)+"</li>\n";
				sbHtml.append(ident).append("\t<li>").append(jsArr.get(y)).append("</li>\n");
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
	private static String futurosHtml(JSONArray jsArray, int indice, int tipo) throws Exception { //T_PR_VIAJEMOVIL
		StringBuilder sb;
		String plantilla, pTmp=null, noMore;
		int maxDiv = 4;
		if(tipo == 1){ //Screen
			maxDiv = 4;
			plantilla =FileUtily.getBuilderFile(HTM_DIR+T_PR_VIAJEMOVIL, CHARSET).toString(); 
			noMore = "<!-- NO HAY MÁS VIAJES FUTUROS -->\n"+"<div class=\"col-md-3 col-sm-6 mb-4\">\n          " +
					"<a href=\"../home.html#portfolio\">\n            " +
					"<img class=\"img-fluid\" src=\"../imagen/proximos/pvm-blank.jpg\" alt=\"\">\n          </a>\n        </div>"; 
		}else{//Movil
			maxDiv = 2;
			plantilla =FileUtily.getBuilderFile(HTM_DIR+T_PR_VIAJESCREEN, CHARSET).toString();
			noMore = "<!-- NO HAY MÁS VIAJES FUTUROS -->\n"+"<div class=\"col-sm-6\">\n          " +
					"<a href=\"../home.html#portfolio\">\n            " +
					"<img class=\"img-fluid\" src=\"../imagen/proximos/pvm-blank.jpg\" alt=\"\">\n          </a>\n        </div>";
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
				sb = new StringBuilder(noMore);
			}
		}
		else{
			sb = new StringBuilder(noMore);
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
	public static boolean generaPagsViaje(JSONArray jsArray, String outhPath) throws Exception {
		boolean res = false;
		log4j.debug("<generaPagsViaje> ");		
		JSONObject json;
		StringBuilder sb;
		Integer duracion=1, tipoTransporte;
		String name, titulo, sumario, descripcion, costo, fecha, salida="", regreso="", linkEvento, condiciones="";
		
		String clDuracion, clTransp, stDias, pagina;
		String HIDECONDS, HIDESALIDA, HIDEREGRESO;
		
		String plantilla = FileUtily.getBuilderFile(HTM_DIR+T_PR_VIAJE, CHARSET).toString(), planTmp;
		String paginaHtml = FileUtily.getBuilderFile(HTM_DIR+T_PAG_VIAJE, CHARSET).toString();
		
		if(jsArray!=null && jsArray.length()>0){
			JSONArray arrIncluye;
			for(int x=0; x<jsArray.length();x++){
				sb = new StringBuilder();
				arrIncluye = null;
				HIDECONDS="hidden";
				HIDESALIDA="hidden";
				HIDEREGRESO="hidden";
				condiciones = "";
				
				json = jsArray.getJSONObject(x);
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
				
				planTmp = plantilla;
				planTmp = planTmp.replace("{{clDuracion}}", clDuracion).replace("{{name}}", name).replace("{{titulo}}", titulo);
				planTmp = planTmp.replace("{{sumario}}", sumario).replace("{{descripcion}}", descripcion).replace("{{costo}}", costo);
				planTmp = planTmp.replace("{{clTransp}}", clTransp).replace("{{duracion}}", String.valueOf(duracion)).replace("{{stDias}}", stDias);					
				planTmp = planTmp.replace("{{fecha}}", fecha).replace("{{pagina}}", pagina).replace("{{linkEvento}}", linkEvento);
				planTmp = planTmp.replace("{{salida}}", salida).replace("{{regreso}}", regreso).replace("{{condiciones}}", condiciones);
				
				//Despues de evaluar los parametros, se llenan los elementos CONDICIONADOS
				planTmp = planTmp.replace("{{HIDECONDS}}", HIDECONDS).replace("{{HIDESALIDA}}", HIDESALIDA).replace("{{HIDEREGRESO}}", HIDEREGRESO);
				planTmp = planTmp.replace("{{INCLUYELISTA}}", incluyeHtml(arrIncluye)).replace("{{HTMLDESCRIPCION}}", descHtml(descripcion))
						//.replace("{{VIAJESFUTURO}}", futurosHtml(jsArray, x));
						.replace("{{VFMOVIL}}", futurosHtml(jsArray,x,1))
						.replace("{{VFSCREEN}}", futurosHtml(jsArray,x,2));
				
				sb.append(paginaHtml.replace("{{HTML-t-pViajeX}}", planTmp));//planTmp);
				//Se escribe un .html por viaje
				
				FileUtily.writeStringInFile(outhPath+"out/"+name+".html", sb.toString(), false);
				log4j.debug("<generaPagsViaje> se genero " + outhPath+"out/"+name+".html");				
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
	public static boolean generaHomeDiv(JSONArray jsArray, String outhFile) throws Exception {
		boolean res = false;
		log4j.debug("<generaHomeDiv> ");
		
		JSONObject json;
		
		StringBuilder sb = new StringBuilder("\t\t\t\t\t <!--   GeneraHtml.java ").append(new java.util.Date()).append(" --> ");

		Integer duracion=1, tipoTransporte;
		String name, titulo, sumario, descripcion, costo, fecha;
		String clDuracion, clTransp, stDias;
		String pagina = "proximos";
			
			String plantilla = FileUtily.getBuilderFile(HTM_DIR+T_HOME_PR_VIAJE, CHARSET).toString(), pTmp;
			
			if(jsArray!=null && jsArray.length()>0){
				for(int x=0; x<jsArray.length();x++){
					json = jsArray.getJSONObject(x);
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
					pagina = name;
					pTmp = plantilla;
					pTmp = pTmp.replace("{{clDuracion}}", clDuracion).replace("{{name}}", name).replace("{{titulo}}", titulo);
					pTmp = pTmp.replace("{{sumario}}", sumario).replace("{{descripcion}}", descripcion).replace("{{costo}}", costo);
					pTmp = pTmp.replace("{{clTransp}}", clTransp).replace("{{duracion}}", String.valueOf(duracion)).replace("{{stDias}}", stDias);					
					pTmp = pTmp.replace("{{fecha}}", fecha).replace("{{pagina}}", pagina);
					sb.append(pTmp);
					
				}
				
				FileUtily.writeStringInFile(outhFile, sb.toString(), false);
//				log4j.debug("<generaHomeDiv> Archivo temporal "+ outhPath );
				res = true;				
			}else{
				log4j.error("<generaHomeDiv> No hay elementos");
				res = false;
			}
		log4j.debug("<generaHomeDiv> Fin de metodo ");
		return res;
	}

}
