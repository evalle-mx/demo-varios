package com.itunes;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import utily.ConnectionBD;
import utily.FileUtily;
import utily.JsonUtily;

/**
 * Lee los archivos json de Lista generados previamente (ConvertMainXml, ConvertXml)
 * , completa los datos de los Tracks  y reEscribe el archivo Json para posterior procesamiento
 * @author dothr
 *
 */
public class ExportPlayLists {
	
	static Logger log4j = Logger.getLogger( ExportPlayLists.class );
			
	private static final String JSONDIR = ConstantesItunes.JSONLIST_DIR;
	protected static ConnectionBD conn;
	
	private static String sQuery = ConstantesItunes.sQuery;
	private static String exportLibray = ConstantesItunes.exportLibray;
	private static final String CEL_SCRIPTS_DIR = exportLibray+"Scripts/";
	private static final String CEL_PLIST = exportLibray+"PlayLists/";
	private static final String CEL_JSONLIST = exportLibray+"JSONList/";
	private static String MUSIC_HDD = "cd /media/dothr/NETTO-HD710/ ";
		//cp --parents Pop_en_Espaniol/Moenia/Moenia\ -\ Manto\ Estelar.mp3 /home/dothr/Music/8GB/
	private static String CP_COMMAND = "cp --parents <TrackPath> <FileFolder> ";
	
	private static Map<String, String> mapaFolder = new HashMap<String, String>();
	
	public static void main(String[] args) {
//		importCompleteList();
		exportList();
		
//		testsetFolderMap();
//		iterateList();
	}
	
	
	/* TODO verificar 
	'Musica/Rock en Ingles/ACDC/ACDC Greatest Hits - 2011/12. Highway To Hell.mp3'
 1.Celtics.json 
 2.ROCK-enIngles.Bikers.json 
 3.ROCK-enIngles.Rock-Leyends.json 
 4.SoundTrack.MoviesSoundTrack.json 

"Musica/Rock en Ingles/Soundgarden/soundgarden - live to rise.mp3"
 ROCK-enIngles.Bikers.json 

‘Musica/Rock en Ingles/Sunrise Avenue - Fairytale Gone Bad.mp3’
* ROCK-enIngles.SadBlueRock.json 

* Santana.Carlos_Santana_Gold.json 

Musica/Musette-Tango-Jazz-Indie/PateDeFua/2013-Bon Apetite/16 Munieca.mp3
* Vi-State.json

* Instrumental&NewAge.Beth_-_The_Covers_Collection_2.json
	
	*/
	
	/**
	 * Itera las listas para generar archivos m3u y Sh
	 */
	public static void exportList(){
		log4j.debug("<exportList>");
		List<String> lsPlayLists = lista8GBPlayLists();
		Iterator<String> itPlayList = lsPlayLists.iterator();
		while(itPlayList.hasNext()){
			exportList(itPlayList.next());
		}
		log4j.debug("<exportList> \n FIN DE PROCESO ");
	}
	
	/**
	 * Genera archivo m3u y sh para obtener y cargar musica correctamente
	 * @param jsonFileName
	 */
	public static void exportList(String jsonFileName){
		log4j.debug("<exportList> " + jsonFileName);
		StringBuilder sbCommand = new StringBuilder(MUSIC_HDD).append("\n");
		StringBuilder sbM3u = new StringBuilder();
		String listName;
		try{
			String jsonPath = CEL_JSONLIST+jsonFileName;
			String stJson = FileUtily.getBuilderNoTabsFile(jsonPath, ConstantesItunes.PCHARSET).toString();
			JSONObject jsonList = new JSONObject(stJson);
			JSONArray jsTracks = jsonList.getJSONArray("Playlist Items");
//			log4j.debug("")
			listName = (jsonList.has("parentName")?jsonList.getString("parentName")+".":"") +
					jsonList.getString("Name").replace(" ", "").replace("(", "-").replace(")", "-").replace("&", "Y");
			if(jsTracks!=null && jsTracks.length()>0){
				JSONObject jsonTrack;
				String stShellPath = null, m3uPath=null;
				for(int x=0;x<jsTracks.length();x++){
					jsonTrack = jsTracks.getJSONObject(x);
					stShellPath = jsonTrack.getString("ruta").replace(" ", "\\ ")
							.replace("(", "\\(").replace(")", "\\)").replace("&", "\\&").replace("'", "\\'").replace("`", "\\`");
					m3uPath = jsonTrack.getString("ruta");
					sbCommand.append(CP_COMMAND.replace("<TrackPath>", stShellPath).replace("<FileFolder>", exportLibray ) ).append("\n");
					sbM3u.append("/").append(m3uPath).append("\n");
				}
			}
			listName = listName.replace("&", "Y").replace("'", "").replace("-", "_");
			sbCommand.append("echo SE COPIARON ARCHIVOS DE LA LISTA ").append(listName).append(" en ").append(exportLibray).append("\n");
			log4j.debug("<exportList> archivo sh a Ejecutar: \n"+CEL_SCRIPTS_DIR+listName+".sh" );
			FileUtily.writeStringInFile(CEL_SCRIPTS_DIR+listName+".sh", sbCommand.toString() , false);
			log4j.debug("<exportList> archivo m3u a Copiar en Cel: \n"+CEL_PLIST+listName+".m3u" );
			FileUtily.writeStringInFile(CEL_PLIST+listName+".m3u", sbM3u.toString() , false);
			
		}catch (Exception e){
			log4j.error(e);
		}
	}
	
	
	/**
	 * Itera un listado de playLists.json, añade nuevos elementos [completa con datos de DB y Lista padre] <br>  
	 * y Exporta a nueva ubicación el archivo Json de PlayList.
	 */
	public static void importCompleteList(){
		log4j.debug("<importCompleteList> ");
		setFolderMap();
		boolean okey = true;
		List<String> lsLista = listaItunesPlayLists();
		try{
			conn = new ConnectionBD("localhost", "itunes", "dothr", "tc34dm1n");
			conn.getDbConn();
			
			Iterator<String> itLista = lsLista.iterator();
			String jsonPath = null, stJsonFile, outPath, plsName,parentName;
			JSONObject json = null;
			while(itLista.hasNext()){
				try{
					parentName=null;
					stJsonFile= itLista.next();
					jsonPath = JSONDIR+stJsonFile;
//					log4j.debug("stJsonFile " + stJsonFile );
					json = getCompleteJson(jsonPath);
					if(json.has("Parent Persistent ID")){
						parentName = mapaFolder.get(json.getString("Parent Persistent ID"));
					}
					
					if(json.has("Folder") && json.getString("Folder").toLowerCase().equals("true")){
						if(json.has("Parent Persistent ID")){
							parentName = "Folder"+"."+(parentName!=null?parentName:"");
						}
					}
					if(json.has("Distinguished Kind") ){
							parentName = "ITUNES";
					}
					json.remove("Smart Info");json.remove("Smart Criteria");json.remove("");
					if(parentName!=null){
						json.put("parentName", parentName );
					}
					
					plsName = stJsonFile.substring(stJsonFile.indexOf(".")+1, stJsonFile.length());
					outPath = CEL_JSONLIST+(parentName!=null?parentName+".":"")+plsName;
					log4j.debug("Re-Escribiendo " + outPath );
					FileUtily.writeStringInFile(outPath, JsonUtily.formatJson(ItunesFormater.formtPlayList(json).toString() ), false);					
				}catch (Exception e){
					log4j.fatal("<exportCompleteList> Error al procesar: " + jsonPath, e);
					okey = false;
				}
			}
			
			log4j.debug("<exportCompleteList> ");
			conn.closeConnection();
		}catch(Exception e){
			//e.printStackTrace();
			log4j.fatal("<exportCompleteList> Excepcion: ", e);
			okey = false;
		}
		log4j.debug("<exportCompleteList> \nFIN DE PROCESO GENERAL " + (okey?"SIN ERRORES":"CON ERROR"));
	}
	
	/**
	 * Genera el JSON completo de una lista a partir de un archivo plano y sus items en DB
	 * @return
	 * @throws Exception
	 */
	protected static JSONObject getCompleteJson(String jsonPath) throws Exception {
//		log4j.debug("<getCompleteJson>");
		String stJson = FileUtily.getBuilderNoTabsFile(jsonPath, ConstantesItunes.PCHARSET).toString();
		
		
		JSONObject jsonList = new JSONObject(stJson), jsonItem;
		JSONArray jsItems = jsonList.getJSONArray("Playlist Items"), jsTracks=new JSONArray();
		ResultSet rs;
		for(int a=0; a<jsItems.length(); a++){
			jsonItem = jsItems.getJSONObject(a);
			//rs = conn.getQuerySet(sQuery+ jsonItem.getString("Track ID")+ "';"); <TrackID>
			rs = conn.getQuerySet(sQuery.replace("<TrackID>", jsonItem.getString("Track ID") ) );
			if(rs!= null ){
				JSONObject jsonTrack = new JSONObject(jsonList);
				while (rs.next()){
					jsonTrack.put("idTrackItem", rs.getLong("id_track_item"));
					jsonTrack.put("idItunes", rs.getString("itunes_id_track"));
					jsonTrack.put("Track ID", jsonItem.getString("Track ID"));	//Para mantener la funcionalidad
					jsonTrack.put("Nombre", rs.getString("track_name") );
					jsonTrack.put("Interprete", rs.getString("track_artist") );
					jsonTrack.put("ruta", rs.getString("file_path"));	//Location
				}
				jsTracks.put(jsonTrack);
			}
		}
		jsonList.remove("Playlist Items");
		jsonList.put("Playlist Items", jsTracks);
		jsonList.put("NombreLista", jsonList.getString("Name"));
		
		return jsonList;
	}
	
	protected static void setFolderMap(){
		
		
		String stJson;
		Iterator<String> itFolder = listaFolderPlayLists().iterator();
		
		while(itFolder.hasNext()){
			try{
				stJson = FileUtily.getBuilderNoTabsFile(JSONDIR+itFolder.next(), ConstantesItunes.PCHARSET).toString();
				JSONObject json = new JSONObject(stJson);
				mapaFolder.put(json.getString("Playlist Persistent ID"), json.getString("Name").replace(" ", ""));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * Listado de PlayList para m3u y SH en Music/8GB
	 * @return
	 */
	private static List<String> lista8GBPlayLists(){
		List<String> lsFile = new ArrayList<String>();
		lsFile.add("100Bolerosdeamor.100_Boleros_de_Amor_CD1.json");
		lsFile.add("AgustínLara.En_Marimba_Vol._2.json");
		lsFile.add("AgustínLara.Grandes_Exitos.json");
		lsFile.add("Atmosfera_suave.json");
		lsFile.add("Audiolibro-ElQuijote.json");
		lsFile.add("BossaNova.Antônio_Carlos_Jobim.json");
		lsFile.add("BossaNova.Bossa_Cafe_2009.json");
		lsFile.add("BossaNova.Bossa_Mia_-_Songs_Of_Abba_Performed_By_BNB.json");
		lsFile.add("BossaNova.Cool_Songs_In_Bossa.json");
		lsFile.add("BossaNova.The_Sound_Of_Ipanema_-_2006.json");
		lsFile.add("CD-Dianita.json");
		lsFile.add("Celtics.json");
		lsFile.add("Clasica.100_Best_Mozart_-_CD1.json");
		lsFile.add("Clasica.100_Best_Mozart_-_CD2.json");
		lsFile.add("Clasicos_40.json");
		lsFile.add("Covers&Remakes.json");
		lsFile.add("EdgarOsceransky.(2001)_En_vivo_en_Pachuca.json");
		lsFile.add("EdgarOsceransky.(2001)_Estoy_aqui.json");
		lsFile.add("EdgarOsceransky.(2003)_De_carne_y_hueso.json");
		lsFile.add("EdgarOsceransky.(2003)_En_vivo_Arucas_La_Bodega_Gran_Canaria.json");
		lsFile.add("EdgarOsceransky.(2003)_Reflejos_acusticos.json");
		lsFile.add("EdgarOsceransky.(2004)_En_vivo_desde_Madrid.json");
		lsFile.add("EdgarOsceransky.(2005)_En_directo_desde_el_ojala.json");
		lsFile.add("EdgarOsceransky.(2007)_Solo,_Ni_tan_solo_(1.Solo).json");
		lsFile.add("EdgarOsceransky.(2007)_Solo,_Ni_tan_solo_(2.Ni_tan_Solo).json");
		lsFile.add("EdgarOsceransky.(2007)_Te_seguire.json");
		lsFile.add("EdgarOsceransky.(2009)_2_necios_de_verdad.json");
		lsFile.add("EdgarOsceransky.(2009)_En_Vivo_Costa_Rica.json");
		lsFile.add("EdgarOsceransky.(2010)_Con_todo_el_corazon_(Tributo_a_EO).json");
		lsFile.add("EdgarOsceransky.(2011)_En_vivo_en_El_Breve_Espacio,_Condesa.json");
		lsFile.add("EdgarOsceransky.(2011)_N_otra_vida.json");
		lsFile.add("Electronica.Acustic,_Chillout_&_Lounge.json");
		lsFile.add("Electronica.Dance-YesterHits.json");
		lsFile.add("Electronica.ElectroPop.json");
		lsFile.add("Electronica.INNA.json");
		lsFile.add("Electronica.RadioList.json");
		lsFile.add("Electronica.Sunlounger_-_The_Beach_Side_Of_Life.json");
		lsFile.add("Electronica.Vocal_Trance.json");
		lsFile.add("Enya.The_Memory_Of_Trees.json");
		lsFile.add("FernandoDelgadillo.1990_-_Fernando_Delgadillo_y_SEIMUS.json");
		lsFile.add("FernandoDelgadillo.1992_-_Concierto_aire_a_ti.json");
		lsFile.add("FernandoDelgadillo.1992_-_Matutina.json");
		lsFile.add("FernandoDelgadillo.1994_-_CronicasdeBrunodelBrenal.json");
		lsFile.add("FernandoDelgadillo.1994_-_Desviaciones_de_la_Cancion_Informal.json");
		lsFile.add("FernandoDelgadillo.1995_-_De_Vuelos_y_de_Sol.json");
		lsFile.add("FrancesBasico.json");
		lsFile.add("Hed_Kandi_BestOf.json");
		lsFile.add("Instrumental&NewAge.Beth_-_The_Covers_Collection_2.json");
		lsFile.add("Instrumental&NewAge.Di_Blasio_-_Lo_esencial.json");
		lsFile.add("Instrumental&NewAge.Erotical_Mystic_Adventure.json");
		lsFile.add("Instrumental&NewAge.Kenny_G_-The_Essential.json");
//		lsFile.add("ITUNES.Las_25_más_escuchadas.json");
//		lsFile.add("ITUNES.Mis_preferidas.json");
//		lsFile.add("ITUNES.Música_clásica.json");
//		lsFile.add("ITUNES.Música_de_los_90.json");
		lsFile.add("Jazz-musette-Tango-Indie.Indie.json");
		lsFile.add("Jazz-musette-Tango-Indie.Jazzanova-In_Between.json");
		lsFile.add("JorgeNegrete.100_Exitos_(cd1).json");
		lsFile.add("JorgeNegrete.100_Exitos_(cd2).json");
		lsFile.add("JorgeNegrete.100_Exitos_(cd3).json");
		lsFile.add("JorgeNegrete.100_Exitos_(cd4).json");
		lsFile.add("JoseAlfredoJiménez.100_Clasicas_de_Jose_ALfredo_Volumen_1-1.json");
		lsFile.add("JoseAlfredoJiménez.100_Clasicas_de_Jose_ALfredo_Volumen_1-2.json");
		lsFile.add("JoséJosé.Lo_Esencial_1.json");
		lsFile.add("JoséJosé.Lo_Esencial_2.json");
		lsFile.add("JoséJosé.Lo_Esencial_3.json");
		lsFile.add("JoséJosé.V.A._Tributo_1.json");
		lsFile.add("JuanGabriel.Con_Banda_el_Recodo..json");
		lsFile.add("JuanGabriel.Exitos_de_Coleccion_-_CD1.json");
		lsFile.add("JuanGabriel.Exitos_de_Coleccion_-_CD2.json");
		lsFile.add("JuanGabriel.Exitos_de_Coleccion_-_CD4.json");
		lsFile.add("JuanGabriel.Exitos_de_Coleccion_-_CD5.json");
		lsFile.add("Latino.Celia_Cruz.json");
		lsFile.add("Latino.Gloria_Estefan.json");
		lsFile.add("Latino.Marck_Anthony.json");
		lsFile.add("Latino.Pitbull.json");
		lsFile.add("Latino.Selena-Antologia.json");
		lsFile.add("Latino.Sergio_Mendes_-_Bom_Tempo_(2010).json");
		lsFile.add("Latino.VA_-_The_Essence_of_Cuban_Music.json");
		lsFile.add("LilaDowns.1996-AZULAO_(EnVivo).json");
		lsFile.add("LilaDowns.2011-Pecados_y_Milagros.json");
		lsFile.add("Lounge.Cafe_Chillout_de_Ibiza_-_Fiesta_del_Mar.json");
		lsFile.add("Lounge.Cafe_Santorini_-_Chillout_Music_and_Lounge_Music_(2011).json");
		lsFile.add("Lounge.Pop_Lounge_Ensemble.json");
		lsFile.add("Manzanero.Las_Mujeres_de_Manzanero_-_Mis_nuevos_Boleros_(2008).json");
		lsFile.add("Manzanero.Lo_mejor_de_Nuestra_Musica.json");
		lsFile.add("Mexicanas.Bronco.json");
		lsFile.add("Mexicanas.Corridos_Revolucionarios.json");
		lsFile.add("Mexicanas.Los_Panchos.json");
		lsFile.add("Mexicanas.Los_Tri-O.json");
		lsFile.add("Mexicanas.Viva_Mexico_y_sus_Canciones.json");
		lsFile.add("Navidad.Annette_Moreno_-_Navidad.json");
		lsFile.add("Navidad.Ardillitas_de_Lalo_Guerrero.json");
		lsFile.add("Navidad.Bing_Crosby.json");
		lsFile.add("Navidad.Christmas.json");
		lsFile.add("Navidad.Elvis_in_Christmas.json");
		lsFile.add("Navidad.En_Familia_Mexicana.json");
		lsFile.add("Navidad.Michael_Buble.json");
		lsFile.add("NichoHinojosa.Lo_esencial_de_Hinojosa_CD1.json");
		lsFile.add("NichoHinojosa.Lo_esencial_de_Hinojosa_CD2.json");
		lsFile.add("NichoHinojosa.Lo_esencial_de_Hinojosa_CD3.json");
		lsFile.add("NorahJones.2002_-_House_of_Blues.json");
		lsFile.add("NorahJones.2003_-_Live_In_New_Orleans.json");
		lsFile.add("NorahJones.2004_-_Feels_Like_Home.json");
		lsFile.add("NorahJones.2007_-_Maison_de_Radio-France.json");
		lsFile.add("NorahJones.2007_-_Not_Too_Late.json");
		lsFile.add("NorahJones.2008_-_Stay_With_Me.json");
		lsFile.add("NorahJones.2008_-_The_Greatest_Hits.json");
		lsFile.add("ópera-pop.Il_Divo.json");
		lsFile.add("ópera-pop.Il_Volo_-_(Spanish_Language_Edition).json");
		lsFile.add("ópera-pop.Il_Volo_Takes_Flight.json");
		lsFile.add("PalCamino.json");
		lsFile.add("Pamboleras.json");
		lsFile.add("PedroInfante.15_Inmortales.json");
		lsFile.add("PedroInfante.Felicidades.json");
		lsFile.add("PedroInfante.Lo_mejor_De_PInfante_1.json");
		lsFile.add("PedroInfante.Lo_mejor_De_PInfante_2.json");
		lsFile.add("PedroInfante.Rancheras.json");
		lsFile.add("POP_enEspañol.PopRomanticoClasico.json");
		lsFile.add("POP_enEspañol.RadioPop.json");
		lsFile.add("POP_enIngles.Boys_Bands.json");
		lsFile.add("POP_enIngles.Loving-She_Songs.json");
		lsFile.add("POP_enIngles.Madonna.json");
		lsFile.add("POP_enIngles.RadioList.json");
		lsFile.add("POP_enIngles.YesterHits.json");
		lsFile.add("Prehispanica.Ah_PAX_BOOB.json");
		lsFile.add("Prehispanica.Chavela_Vargas-Cupaima-2007.json");
		lsFile.add("Prehispanica.Kay.json");
		lsFile.add("Prehispanica.MEZ-ME.json");
		lsFile.add("Prehispanica.PrehispanicMusic.Vol1.json");
		lsFile.add("Prehispanica.PrehispanicMusic.Vol2-CD1.json");
		lsFile.add("Prehispanica.Xochiquetzal.json");
		lsFile.add("RadioList.json");
		lsFile.add("Relajante.Instrumental.json");
		lsFile.add("Relajante.Naturaleza.json");
		lsFile.add("Relajante.Relaxing_with_Natura.json");
		lsFile.add("RockenEspañol.Argentino-FunkRock.json");
		lsFile.add("RockenEspañol.RockEnTuIdioma-Comp.json");
		lsFile.add("RockenEspañol.Ska-punk.json");
		lsFile.add("ROCK-enIngles.Alternativo.json");
		lsFile.add("ROCK-enIngles.Bikers.json");
		lsFile.add("ROCK-enIngles.Elvis-Essential_Collection.json");
		lsFile.add("ROCK-enIngles.RadioList.json");
		lsFile.add("ROCK-enIngles.Rock-Leyends.json");
		lsFile.add("ROCK-enIngles.Rock&Roll.json");
		lsFile.add("ROCK-enIngles.SadBlueRock.json");
		lsFile.add("Salsa&Cumbia.My-Cumbia-Especial.json");
		lsFile.add("Salsa&Cumbia.My-Salsa-Especial.json");
		lsFile.add("Salsa&Cumbia.Salsa-Cumbia.json");
		lsFile.add("Santana.Carlos_Santana_Gold.json");
		lsFile.add("Santana.Corazón_[Deluxe].json");
		lsFile.add("Seal.1991.Seal.json");
		lsFile.add("Seal.1998.Human.Being.json");
		lsFile.add("Seal.2001.MTV.Unplugged.json");
		lsFile.add("Seal.2003.Seal.IV.json");
		lsFile.add("Seal.2005.Best.1991-2004-1.json");
		lsFile.add("Seal.2005.Best.1991-2004-2.json");
		lsFile.add("Seal.2005.Live.in.paris.json");
		lsFile.add("Seal.2006.One.Night.to.Remember.json");
		lsFile.add("Seal.2007.System.json");
		lsFile.add("Seal.2009.Soul-Live-.json");
		lsFile.add("Seal.2010.Seal.6.Commitment.json");
		lsFile.add("SoundTrack.Dawson's_Creek.json");
		lsFile.add("SoundTrack.Desperado.json");
		lsFile.add("SoundTrack.MoviesSoundTrack.json");
		lsFile.add("SoundTrack.SpaghettiWestern.json");
		lsFile.add("Trova&Guitarra.Guitar_Girls.json");
		lsFile.add("Universal.json");
		lsFile.add("VA.VA_-_Chillout_in_Blue_(Smooth_Relax_Beach_Lounge_Music).json");
		lsFile.add("VA.VA_-_Enjoy_Relaxation_Vol_1_(2012).json");
		lsFile.add("Vi-State.json");
		lsFile.add("WDM.json");
		lsFile.add("Yesterhits.json");
		return lsFile;
	}
	
	/**
	 * Lista de Listas tipo Folder (Carpeta) en Itunes
	 * @return
	 */
	private static List<String> listaFolderPlayLists(){
		List<String> lsFile = new ArrayList<String>();
		lsFile.add("Folder.13204.Clasica.json");
		lsFile.add("Folder.13281.Electronica.json");
		lsFile.add("Folder.13522.Instrumental&NewAge.json");
		lsFile.add("Folder.13895.Bossa_Nova.json");
		lsFile.add("Folder.14119.Enya.json");
		lsFile.add("Folder.14147.Lounge.json");
		lsFile.add("Folder.14323.Relajante.json");
		lsFile.add("Folder.14399.VA.json");
		lsFile.add("Folder.14604.Jazz-musette-Tango-Indie.json");
		lsFile.add("Folder.14631.Latino.json");
		lsFile.add("Folder.14885.Salsa_&_Cumbia.json");
		lsFile.add("Folder.15089.Santana.json");
		lsFile.add("Folder.15365.Mexicanas.json");
		lsFile.add("Folder.15961.100_Boleros_de_amor.json");
		lsFile.add("Folder.16017.Agustín_Lara.json");
		lsFile.add("Folder.16086.Jorge_Negrete.json");
		lsFile.add("Folder.16297.Jose_Alfredo_Jiménez.json");
		lsFile.add("Folder.16406.José_José.json");
		lsFile.add("Folder.16571.JuanGabriel.json");
		lsFile.add("Folder.16763.LilaDowns.json");
		lsFile.add("Folder.16826.Manzanero.json");
		lsFile.add("Folder.16885.PedroInfante.json");
		lsFile.add("Folder.17185.Navidad.json");
		lsFile.add("Folder.17523.ópera-pop.json");
		lsFile.add("Folder.17613.POP_enEspañol.json");
		lsFile.add("Folder.17654.POP_enIngles.json");
		lsFile.add("Folder.17889.Seal.json");
		lsFile.add("Folder.18305.Prehispanica.json");
		lsFile.add("Folder.18575.Rock_en_Español.json");
		lsFile.add("Folder.18653.ROCK-enIngles.json");
		lsFile.add("Folder.18919.SoundTrack.json");
		lsFile.add("Folder.19108.Trova_&_Guitarra.json");
		lsFile.add("Folder.19623.Edgar_Osceransky.json");
		lsFile.add("Folder.20169.Fernando_Delgadillo.json");
		lsFile.add("Folder.20336.Nicho_Hinojosa.json");
		lsFile.add("Folder.20472.Norah_Jones.json");
		return lsFile;
	}
	/**
	 * Lista de Listas Simples en Itunes
	 * @return
	 */
	private static List<String> listaItunesPlayLists(){
		List<String> lsFile = new ArrayList<String>();
		/* ITUNES-/JSONlists */
		lsFile.add("13241.100_Best_Mozart_-_CD1.json");
		lsFile.add("13256.100_Best_Mozart_-_CD2.json");
		lsFile.add("13389.Acustic,_Chillout_&_Lounge.json");
		lsFile.add("13414.Dance-YesterHits.json");
		lsFile.add("13422.ElectroPop.json");
		lsFile.add("13433.INNA.json");
		lsFile.add("13460.RadioList.json");
		lsFile.add("13487.Sunlounger_-_The_Beach_Side_Of_Life.json");
		lsFile.add("13514.Vocal_Trance.json");
		lsFile.add("14001.Antônio_Carlos_Jobim.json");
		lsFile.add("14018.Bossa_Mia_-_Songs_Of_Abba_Performed_By_BNB.json");
		lsFile.add("14035.Bossa_Cafe_2009.json");
		lsFile.add("14068.Cool_Songs_In_Bossa.json");
		lsFile.add("14087.The_Sound_Of_Ipanema_-_2006.json");
		lsFile.add("14133.The_Memory_Of_Trees.json");
		lsFile.add("14232.Cafe_Chillout_de_Ibiza_-_Fiesta_del_Mar.json");
		lsFile.add("14262.Cafe_Santorini_-_Chillout_Music_and_Lounge_Music_(2011).json");
		lsFile.add("14288.Pop_Lounge_Ensemble.json");
		lsFile.add("14358.Instrumental.json");
		lsFile.add("14370.Naturaleza.json");
		lsFile.add("14386.Relaxing_with_Natura.json");
		lsFile.add("14444.VA_-_Chillout_in_Blue_(Smooth_Relax_Beach_Lounge_Music).json");
		lsFile.add("14469.VA_-_Enjoy_Relaxation_Vol_1_(2012).json");
		lsFile.add("14492.Beth_-_The_Covers_Collection_2.json");
		lsFile.add("14505.Di_Blasio_-_Lo_esencial.json");
		lsFile.add("14553.Erotical_Mystic_Adventure.json");
		lsFile.add("14566.Kenny_G_-The_Essential.json");
		lsFile.add("14616.Indie.json");
		lsFile.add("14621.Jazzanova-In_Between.json");
		lsFile.add("14952.My-Cumbia-Especial.json");
		lsFile.add("14989.My-Salsa-Especial.json");
		lsFile.add("15022.Salsa-Cumbia.json");
		lsFile.add("15147.Carlos_Santana_Gold.json");
		lsFile.add("15190.Corazón_[Deluxe].json");
		lsFile.add("15208.Celia_Cruz.json");
		lsFile.add("15224.Gloria_Estefan.json");
		lsFile.add("15248.Marck_Anthony.json");
		lsFile.add("15278.Pitbull.json");
		lsFile.add("15298.Selena-Antologia.json");
		lsFile.add("15322.Sergio_Mendes_-_Bom_Tempo_(2010).json");
		lsFile.add("15337.VA_-_The_Essence_of_Cuban_Music.json");
		lsFile.add("15989.100_Boleros_de_Amor_CD1.json");
		lsFile.add("16050.En_Marimba_Vol._2.json");
		lsFile.add("16063.Grandes_Exitos.json");
		lsFile.add("16187.100_Exitos_(cd1).json");
		lsFile.add("16215.100_Exitos_(cd2).json");
		lsFile.add("16243.100_Exitos_(cd3).json");
		lsFile.add("16271.100_Exitos_(cd4).json");
		lsFile.add("16350.100_Clasicas_de_Jose_ALfredo_Volumen_1-1.json");
		lsFile.add("16378.100_Clasicas_de_Jose_ALfredo_Volumen_1-2.json");
		lsFile.add("16484.Lo_Esencial_1.json");
		lsFile.add("16507.Lo_Esencial_2.json");
		lsFile.add("16530.Lo_Esencial_3.json");
		lsFile.add("16553.V.A._Tributo_1.json");
		lsFile.add("16661.Con_Banda_el_Recodo..json");
		lsFile.add("16675.Exitos_de_Coleccion_-_CD1.json");
		lsFile.add("16700.Exitos_de_Coleccion_-_CD2.json");
		lsFile.add("16723.Exitos_de_Coleccion_-_CD4.json");
		lsFile.add("16744.Exitos_de_Coleccion_-_CD5.json");
		lsFile.add("16793.1996-AZULAO_(EnVivo).json");
		lsFile.add("16809.2011-Pecados_y_Milagros.json");
		lsFile.add("16854.Las_Mujeres_de_Manzanero_-_Mis_nuevos_Boleros_(2008).json");
		lsFile.add("16870.Lo_mejor_de_Nuestra_Musica.json");
		lsFile.add("16968.15_Inmortales.json");
		lsFile.add("16986.Felicidades.json");
		lsFile.add("17002.Lo_mejor_De_PInfante_1.json");
		lsFile.add("17031.Lo_mejor_De_PInfante_2.json");
		lsFile.add("17059.Rancheras.json");
		lsFile.add("17074.Bronco.json");
		lsFile.add("17103.Corridos_Revolucionarios.json");
		lsFile.add("17128.Los_Panchos.json");
		lsFile.add("17148.Los_Tri-O.json");
		lsFile.add("17162.Viva_Mexico_y_sus_Canciones.json");
		lsFile.add("17345.Annette_Moreno_-_Navidad.json");
		lsFile.add("17360.Ardillitas_de_Lalo_Guerrero.json");
		lsFile.add("17384.Bing_Crosby.json");
		lsFile.add("17402.Christmas.json");
		lsFile.add("17435.Elvis_in_Christmas.json");
		lsFile.add("17455.En_Familia_Mexicana.json");
		lsFile.add("17501.Michael_Buble.json");
		lsFile.add("17565.Il_Divo.json");
		lsFile.add("17580.Il_Volo_-_(Spanish_Language_Edition).json");
		lsFile.add("17595.Il_Volo_Takes_Flight.json");
		lsFile.add("17632.PopRomanticoClasico.json");
		lsFile.add("17650.RadioPop.json");
		lsFile.add("18023.1991.Seal.json");
		lsFile.add("18035.1998.Human.Being.json");
		lsFile.add("18050.2001.MTV.Unplugged.json");
		lsFile.add("18063.2003.Seal.IV.json");
		lsFile.add("18078.2005.Best.1991-2004-1.json");
		lsFile.add("18095.2005.Best.1991-2004-2.json");
		lsFile.add("18111.2005.Live.in.paris.json");
		lsFile.add("18128.2006.One.Night.to.Remember.json");
		lsFile.add("18145.2007.System.json");
		lsFile.add("18159.2009.Soul-Live-.json");
		lsFile.add("18173.2010.Seal.6.Commitment.json");
		lsFile.add("18187.Boys_Bands.json");
		lsFile.add("18215.Loving-She_Songs.json");
		lsFile.add("18236.Madonna.json");
		lsFile.add("18274.RadioList.json");
		lsFile.add("18299.YesterHits.json");
		lsFile.add("18431.Ah_PAX_BOOB.json");
		lsFile.add("18444.Chavela_Vargas-Cupaima-2007.json");
		lsFile.add("18459.Kay.json");
		lsFile.add("18475.MEZ-ME.json");
		lsFile.add("18495.PrehispanicMusic.Vol1.json");
		lsFile.add("18528.PrehispanicMusic.Vol2-CD1.json");
		lsFile.add("18560.Xochiquetzal.json");
		lsFile.add("18611.Argentino-FunkRock.json");
		lsFile.add("18636.RockEnTuIdioma-Comp.json");
		lsFile.add("18642.Ska-punk.json");
		lsFile.add("18772.Alternativo.json");
		lsFile.add("18789.Bikers.json");
		lsFile.add("18810.Elvis-Essential_Collection.json");
		lsFile.add("18841.RadioList.json");
		lsFile.add("18846.Rock&Roll.json");
		lsFile.add("18854.Rock-Leyends.json");
		lsFile.add("18879.SadBlueRock.json");
		lsFile.add("19002.Dawson's_Creek.json");
		lsFile.add("19022.Desperado.json");
		lsFile.add("19043.MoviesSoundTrack.json");
		lsFile.add("19057.SpaghettiWestern.json");
		lsFile.add("19875.(2001)_En_vivo_en_Pachuca.json");
		lsFile.add("19891.(2001)_Estoy_aqui.json");
		lsFile.add("19906.(2003)_De_carne_y_hueso.json");
		lsFile.add("19920.(2003)_En_vivo_Arucas_La_Bodega_Gran_Canaria.json");
		lsFile.add("19939.(2003)_Reflejos_acusticos.json");
		lsFile.add("19952.(2004)_En_vivo_desde_Madrid.json");
		lsFile.add("19969.(2005)_En_directo_desde_el_ojala.json");
		lsFile.add("19994.(2007)_Solo,_Ni_tan_solo_(1.Solo).json");
		lsFile.add("20017.(2007)_Solo,_Ni_tan_solo_(2.Ni_tan_Solo).json");
		lsFile.add("20036.(2007)_Te_seguire.json");
		lsFile.add("20049.(2009)_2_necios_de_verdad.json");
		lsFile.add("20069.(2009)_En_Vivo_Costa_Rica.json");
		lsFile.add("20094.(2010)_Con_todo_el_corazon_(Tributo_a_EO).json");
		lsFile.add("20111.(2011)_En_vivo_en_El_Breve_Espacio,_Condesa.json");
		lsFile.add("20156.(2011)_N_otra_vida.json");
		lsFile.add("20245.1990_-_Fernando_Delgadillo_y_SEIMUS.json");
		lsFile.add("20260.1992_-_Concierto_aire_a_ti.json");
		lsFile.add("20275.1992_-_Matutina.json");
		lsFile.add("20287.1994_-_CronicasdeBrunodelBrenal.json");
		lsFile.add("20304.1994_-_Desviaciones_de_la_Cancion_Informal.json");
		lsFile.add("20321.1995_-_De_Vuelos_y_de_Sol.json");
		lsFile.add("20401.Lo_esencial_de_Hinojosa_CD1.json");
		lsFile.add("20425.Lo_esencial_de_Hinojosa_CD2.json");
		lsFile.add("20449.Lo_esencial_de_Hinojosa_CD3.json");
		lsFile.add("20577.2002_-_House_of_Blues.json");
		lsFile.add("20590.2003_-_Live_In_New_Orleans.json");
		lsFile.add("20608.2004_-_Feels_Like_Home.json");
		lsFile.add("20624.2007_-_Maison_de_Radio-France.json");
		lsFile.add("20647.2007_-_Not_Too_Late.json");
		lsFile.add("20663.2008_-_Stay_With_Me.json");
		lsFile.add("20678.2008_-_The_Greatest_Hits.json");
		lsFile.add("20700.Guitar_Girls.json");
		lsFile.add("20737.Las_25_más_escuchadas.json");
		lsFile.add("20765.Mis_preferidas.json");
		lsFile.add("20777.Música_clásica.json");
		lsFile.add("20881.Música_de_los_90.json");
		lsFile.add("21498.Atmosfera_suave.json");
		lsFile.add("21509.Audiolibro-ElQuijote.json");
		lsFile.add("21564.CD-Dianita.json");
		lsFile.add("21591.Celtics.json");
		lsFile.add("21620.Clasicos_40.json");
		lsFile.add("21629.Covers&Remakes.json");
		lsFile.add("21648.FrancesBasico.json");
		lsFile.add("21661.Hed_Kandi_BestOf.json");
		lsFile.add("21669.PalCamino.json");
		lsFile.add("21683.Pamboleras.json");
		lsFile.add("21711.RadioList.json");
		lsFile.add("21739.Universal.json");
		lsFile.add("21744.Vi-State.json");
		lsFile.add("21764.WDM.json");
		lsFile.add("21795.Yesterhits.json");
		
		return lsFile;
	}
	
	
	public static void testsetFolderMap(){
		setFolderMap();
		System.out.println(mapaFolder);
	}
	
	
	private static void iterateList(){
		log4j.debug("<iterateList>");
		String jsonFileName; 
		StringBuilder sbTracks;
		try{
			List<String> lsPlayLists = lista8GBPlayLists();
			Iterator<String> itPlayList = lsPlayLists.iterator();
			sbTracks = new StringBuilder();
			boolean trackEncontrada = false;
		
			while(itPlayList.hasNext()){
				jsonFileName = itPlayList.next();
				String stJson = FileUtily.getBuilderNoTabsFile(CEL_JSONLIST+jsonFileName, ConstantesItunes.PCHARSET).toString();
				JSONObject jsonList = new JSONObject(stJson);				
				JSONArray jsTracks = jsonList.getJSONArray("Playlist Items");				
				if(jsTracks!=null && jsTracks.length()>0){
					JSONObject jsonTrack;			
					
					for(int x=0;x<jsTracks.length();x++){
						jsonTrack = jsTracks.getJSONObject(x);
						if(jsonTrack.getLong("idTrackItem") == 2332){
							trackEncontrada  = true;
						}
//						if(jsonTrack.getString("ruta").indexOf("Apetite")!=-1){
//							trackEncontrada  = true;
//						}
					}
				}
				
				if(trackEncontrada){
					sbTracks.append("* ").append(jsonFileName).append(" \n ");
				}
				
				trackEncontrada=false;
			}
			log4j.debug(sbTracks);
			
			
		}catch (Exception e){
			log4j.fatal("Error al iterar: ", e);
		}
	}
}
