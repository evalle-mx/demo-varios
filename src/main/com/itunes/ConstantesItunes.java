package com.itunes;
/**
 * Archivo de Constantes (Rutas, archivos) referente al paquete <br><b>com.itunes.*</b><br>
 * @author dothr
 *
 */
public abstract class ConstantesItunes {

	public static final String ITUNES_DIR = "/home/dothr/Music/ITUNES-/";
	public static final String JSONLIST_DIR = "/home/dothr/Music/ITUNES-/JSONlists/";
	public static final String PCHARSET = "UTF-8";
	
	//ConvertMainXml.java
	public static final String XmlItunesLibrary = "cp.iTunes_Library.xml"; // cp.iTunes_Library.TEST.xml |
	public static final String TRACKS_FILE_NAME = "tracksDB.json";
	
	//ConvertXml.java
	public static final String XmlPlayList = "2.pList.Playlists.xml";	//test.pList.xml
	public static final String xmlTracks = "1.test.pList.tracks.xml";	//1.pList.tracks.xml | 1.test.pList.tracks.xml
	
	//CompletePlayLists.java
	public final static String sQuery = "SELECT id_track_item, itunes_id_track, track_name, track_artist," +
			" file_path FROM track_item WHERE itunes_id_track = '<TrackID>';";
	
	//CompletePlayLists.java
	public final static String exportLibray = "/home/dothr/Music/8GB/";
	
	
	/*
	 * PROCESO PARA ITUNES=Migración
	 * >> JAVA
	 * 1. ConvertMainXml.crawlItunesXML() para Procesar el archivo xml de Itunes y generar Listas y Archivo de canciones (ITUNES/JSONlist)
	 * 2. JsonToSql.createSQLTracks() para generar el archivo de Inserts de Información de archivos mp3,mp4 (ITUNES/JSONlist)
	 * 3. Ejecutar INSERTS_TRACKS.sql para agregar a la Base de Datos (itunes) en la tabla track_item 
	 * 4. ExportPlayLists.importCompleteList() Para completar las listas con los datos de cancion en Base de Datos (8GB/JSONlist)
	 * 	4.b Modificar/Verificar el atributo MUSIC_HDD con la ruta de la carpeta MUSIC Origen
	 * 5. ExportPlayLists.exportList() Crea PlayLists (.m3u) y Scripts (.sh) para importar de HDD (8GB/PlayLists & 8GB/Scripts )
	 * <<
	 * >> EXTERNO
	 * 6. Darle permiso de ejecución a cada archivo sh. [chmod u+x *]
	 * 	6.b. Conectar/Ubicar el MUSIC_HDD (DRIVE)
	 * 7. Ejecutar cada archivo para obtener unicamente los mp3 en la carpeta de copia 
	 * 
	 */
}
