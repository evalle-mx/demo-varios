package mayatrav;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import mayatrav.vo.ViajeVo;

public class TagsEditor {
	
	/* >>>>
	 Funcionalidad de reemplazo Duplicada de AppTransactionalStr (Servicio Notificación)
	 */
	public final static String TAG_NAME="<name>";
	public final static String TAG_ACTIVE="<active>";
	public final static String TAG_HOMEIMG="<homeImg>";
	public final static String TAG_MENUIMG="<menuImg>";
	public final static String TAG_TABNAME="<tabName>";
	public final static String TAG_TITULO="<titulo>";
	public final static String TAG_FECHA="<fecha>";
	public final static String TAG_DURACION="<duracion>";
	public final static String TAG_COSTO="<costo>";
	public final static String TAG_DESCRIPCION="<descripcion>";
	public final static String TAG_CONDICIONES="<condiciones>";
	public final static String TAG_SALIDA="<salida>";
	public final static String TAG_REGRESO="<regreso>";
	public final static String TAG_SALIDAMV="<salida>";
	public final static String TAG_REGRESOMV="<regreso>";
	public final static String TAG_LINKEVENTO="<linkEvento>";
	/**
	 * Se sustituyen etiquetas en el mensaje dado
	 * @param mensaje, es el string donde estan las etiquetas a reemplazar
	 * @param notificationDto, objeto con informacion de la notificacion
	 * @return
	 */
	public static String replaceTags(String cuerpoTexto, ViajeVo viajeVo){
		Matcher matcher = Pattern.compile("{{(\\w+|\\w+:\\w+)}}").matcher(cuerpoTexto);
		while(matcher.find()){
			cuerpoTexto=cuerpoTexto.replace(matcher.group(), getTagValue(matcher.group(), viajeVo));
		}
		return cuerpoTexto;
	}
	/**
	 * Lista de tags a reemplazar 
	 * @param tokenTag, tag que va a ser reemplazado
	 * @return
	 */
	public static String getTagValue(String tokenTag, ViajeVo viajeVo){
		//System.out.println("Entrada tokenTag="+tokenTag	);
		
		if(tokenTag.equals(TAG_NAME)){
			tokenTag = viajeVo.getName();
		}
		else if(tokenTag.equals(TAG_HOMEIMG)){
			tokenTag = viajeVo.getHomeImg();
		}
		else if(tokenTag.equals(TAG_MENUIMG)){
			tokenTag = viajeVo.getMenuImg();
		}
		else if(tokenTag.equals(TAG_TABNAME)){
			tokenTag = viajeVo.getTabName();
		 }
		else if(tokenTag.equals(TAG_TITULO)){
				tokenTag = viajeVo.getTitulo();
		 }
		else if(tokenTag.equals(TAG_FECHA)){
				tokenTag = viajeVo.getFecha();
		 }
		else if(tokenTag.equals(TAG_DURACION)){
				tokenTag = viajeVo.getDuracion();
		 }
		else if(tokenTag.equals(TAG_COSTO)){
				tokenTag = viajeVo.getCosto();
		 }
		else if(tokenTag.equals(TAG_DESCRIPCION)){
				tokenTag = viajeVo.getDescripcion();
		 }
		else if(tokenTag.equals(TAG_CONDICIONES)){
				tokenTag = viajeVo.getCondiciones();
		 }
		else if(tokenTag.equals(TAG_SALIDA)){
				tokenTag = viajeVo.getSalida();
		 }
		else if(tokenTag.equals(TAG_REGRESO)){
				tokenTag = viajeVo.getRegreso();
		 }
		else if(tokenTag.equals(TAG_SALIDAMV)){
				tokenTag = viajeVo.getSalida();
		 }
		else if(tokenTag.equals(TAG_REGRESOMV)){
				tokenTag = viajeVo.getRegreso();
		 }
		else if(tokenTag.equals(TAG_LINKEVENTO)){
				tokenTag = viajeVo.getLinkEvento();
		}		
		else if(tokenTag.equals(TAG_ACTIVE)){
			tokenTag = viajeVo.getActive();
		}
		else{
			tokenTag = "";
		}
		//System.out.println("<getTagValue> salida tokenTag="+tokenTag);
		return tokenTag;
	}
	/* <<<<<<<<<<<<<<<< */
	
	

	
	/**
	 * Hace un reemplazo dinamico dependiendo el contenido del Json
	 * @param cuerpoTexto
	 * @param jsonViaje
	 * @return
	 */
	public static String replaceTags(String cuerpoTexto, JSONObject json) throws Exception {
		String[] nombres = JSONObject.getNames(json);		
		String campo = "";
		for(int x=0;x<nombres.length;x++){
			campo = nombres[x];
			Object jsObj;
			if(json.has(campo) ){
				jsObj = json.get(campo);
				if(jsObj instanceof String){
					//System.out.println("campo: <"+campo+"> = "+json.getString(campo));
					cuerpoTexto = cuerpoTexto.replace("{{"+campo+"}}", json.getString(campo));
				}
				else if (jsObj instanceof JSONArray){
					if(campo.equals("incluye")){/* caso incluye (MayaTravels proximosVIajes) */
						String fmtInc = fmtIncluye(json.getJSONArray(campo), "\t                                    ");
						cuerpoTexto = cuerpoTexto.replace("{{"+campo+"}}", fmtInc );
					}
					else{/* No determinado */
						System.err.println("PROCESAR ARRAY " + x);
					}
				}
			}
		}
		return cuerpoTexto;
	}
	
	/**
	 * Formatea los elementos de arreglo Sencillo: <br>
	 * ["uno","dos","tres"]
	 * @param jsArr
	 * @param ident
	 * @return
	 * @throws Exception
	 */
	private static String fmtIncluye(JSONArray jsArr, String ident) throws Exception {
		String html = "";
		html = "<ul>\n";
		for(int y=0; y<jsArr.length();y++){
			html+=ident+"\t<li>"+jsArr.get(y)+"</li>\n";
		}				
		html += ident+"</ul>";
		
		return html;
	}

}
