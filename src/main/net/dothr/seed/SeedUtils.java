package net.dothr.seed;

import java.util.ArrayList;
import java.util.Iterator;

public class SeedUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * Elimina los Tokens (Palabras/frases) del proceso Nutch
	 * @param slrContent
	 * @return
	 */
	public static String cleanContent(String slrContent){
		Iterator<String> itToks = lsRmTokens().iterator();
		String token = "";
		while(itToks.hasNext()){
			token = itToks.next();
//			log4j.debug("Reemplazando "+token);
			slrContent = slrContent.replace(token, " ");
		}
	
		return slrContent.replaceAll("\\s{2,}", " ").trim();
	}
	
	private static ArrayList<String> lsRmTokens(){
		ArrayList<String> lsToks = new ArrayList<String>();
		lsToks.add("Publicar un comentario en la entrada");
		 lsToks.add("No hay comentarios:");
		 lsToks.add("Compartir en Pinterest ");
		 lsToks.add("Compartir con Facebook");
		 lsToks.add("Entrada antigua ");
		 lsToks.add("Página principal ");
		 lsToks.add("Suscribirse a ");
		 lsToks.add("Enviar comentarios ");
		 lsToks.add("Compartir con Twitter");
		 lsToks.add("Escribe un blog ");
		 lsToks.add("Enviar por correo electrónico");
		 lsToks.add("Glosarios");
		 lsToks.add("Glosario");
		 lsToks.add("Indice");
		 lsToks.add("Condiciones de uso");
		 lsToks.add("Contacto");
		 lsToks.add("Amor");
		 lsToks.add("Magazine");
		 lsToks.add("Creaciones");
		 lsToks.add("Vagoteca");
		 lsToks.add("El Rincón del Vago,");
		 lsToks.add("en Salamanca desde 1998");
		 lsToks.add("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
		 lsToks.add("Letras, abreviaturas, prefijos y términos");
		 lsToks.add("Buscar");
		 lsToks.add("Sube tus documentos");
		 lsToks.add("Inicio");
		 lsToks.add("Trabajos y Tareas");
		 lsToks.add("Descargar");
		 lsToks.add("Fecha");
		 lsToks.add("Mapa del sitio");
		 lsToks.add("Diccionario");
		 lsToks.add("Estás en ");
		 lsToks.add("Diccionario");
		 lsToks.add("(Inglés) ");
		 lsToks.add("(español-inglés)");
		 lsToks.add("(inglés-español)");
		 lsToks.add("Archivo del blog");
		 
		 //Particulares
		 lsToks.add("Temas Glosario Telecomunicaciones");
		 lsToks.add("publicidad");
		 lsToks.add("Blog");
		 lsToks.add("Correo");
		 lsToks.add("Tests");
		 lsToks.add("Diccionario de Electrónica");
		 lsToks.add("Diccionario básico de electrónica");
		 lsToks.add("Letra A");
		 lsToks.add("Letra B");
		 lsToks.add("Letra C");
		 lsToks.add("Letra D");
		 lsToks.add("Letra E");
		 lsToks.add("Letra F");
		 lsToks.add("Letra G");
		 lsToks.add("Letra H");
		 lsToks.add("Letra I");
		 lsToks.add("Letra J");
		 lsToks.add("Letra K");
		 lsToks.add("Letra L");
		 lsToks.add("Letra M");
		 lsToks.add("Letra N");
		 lsToks.add("Letra O");
		 lsToks.add("Letra P");
		 lsToks.add("Letra Q");
		 lsToks.add("Letra R");
		 lsToks.add("Letra S");
		 lsToks.add("Letra T");
		 lsToks.add("Letra U");
		 lsToks.add("Letra V");
		 lsToks.add("Letra W");
		 lsToks.add("Letra X");
		 lsToks.add("Letra Y");
		 lsToks.add("Letra Z");
		 lsToks.add("Inicio");
		 lsToks.add("© Todos los derechos reservados - 2016");
		 lsToks.add("|  Inicio |  Recomendar a un amigo | Mapa del sitio |  Contacto | Acerca de |  Aviso legal |  Enlaces | In English |");
		 lsToks.add("< Abonado Glosario Telecomunicaciones Abono/Cargo de suscripción a la línea");
		 lsToks.add("Novedades Términos juridicos");
		 lsToks.add("Enfermedades infecciosas");
		 lsToks.add("Términos militares");
		 lsToks.add("Profesiones");
		 lsToks.add("Culinario Psicología Topografía");
		 lsToks.add("Geodesia y GPS");
		 lsToks.add("Herramientas de jardinería");
		 lsToks.add(" Documentos ");
		 lsToks.add("Prácticas Universitarios");
		 lsToks.add(" -   -");
		 lsToks.add("básico de electrónica -");
		 lsToks.add(">  ");
		 lsToks.add("®  AMG 1998 - 2016  ");
		 lsToks.add("¿ Quieres enlazarnos ?");
		 
		 lsToks.add("1998 - ");
		 lsToks.add("Recomendar a un amigo ");
		 lsToks.add("básico de electrónica");
		 lsToks.add("|    |   |   |    | Acerca de |  Aviso legal |  Enlaces | In English |");
		 lsToks.add("Símbolos eléctricos Símbolos electrónicos Índice alfabético Tabla periódica de símbolos Simbología básica Simbolos eléctricos en PDF + Recursos");
		 lsToks.add("lunes, 4 de junio de 2012");
		 lsToks.add("Electricidad y Electronica Industrial Electricidad y Electronica Industrial");
		 
		 lsToks.add("Datos personales ");
		 lsToks.add("Dionisio Perez ");
		 lsToks.add("Ver todo mi perfil Plantilla Sencillo");
		 lsToks.add("Enlace permanente: Área comercial básica -");		 
		 lsToks.add("Actualizaciones Dibujo Técnico Ética Educación física Electrónica, Informática y Telecomunicaciones ");
		 lsToks.add("Química");
		 lsToks.add("Área comercial básica ");
		 lsToks.add("Listado Temas ");
		 lsToks.add("Telecomunicaciones / Término");
		 lsToks.add("Esta definición fue tomada del Atlas Comercial y Guía de Mercadeo de Rand McNally.");
		 lsToks.add("Enlace permanente:");		 
		 lsToks.add("2016-10-14 08:34:48");
		 lsToks.add("2016-10-14 08:");
		 lsToks.add("La mayor colección de símbolos eléctricos y electrónicos de ayer y de hoy en la red      ");
		 lsToks.add("+ Recursos  ");
//		 lsToks.add("Enlace permanente:");
//		 lsToks.add("Enlace permanente:");
//		 lsToks.add("Enlace permanente:");
//		 lsToks.add("Enlace permanente:");
		 
		 
		
		return lsToks;
	}

}
