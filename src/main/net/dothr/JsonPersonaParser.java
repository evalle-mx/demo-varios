package net.dothr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import utily.FileUtily;

public class JsonPersonaParser {

	static Logger log4j = Logger.getLogger( JsonPersonaParser.class );
	protected static final String PERSONA_JSON_DIR = "/home/dothr/JsonUI/PersonaRecreate/curriculumManagement/Selex-2016/";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Iterator<String> itPers = getListNPersonaFile().iterator();
		StringBuilder sbLsFile = new StringBuilder();
		while(itPers.hasNext()){
			sbLsFile.append(itPers.next()).append("\n");
		}
		FileUtily.writeStringInFile("/home/dothr/JsonUI/PersonaRecreate/curriculumManagement/lsFileModelo.txt", sbLsFile.toString(), false);
		log4j.debug(sbLsFile);
	}
	
	private static List<String> getListNPersonaFile(){
		log4j.debug("<getListNPersonaFile> ");
		List<String> lsFile = listFileReadInDir();
		List<String> lsMail = emailsList();
		
		List<String> lsPersona = new ArrayList<String>();
		
		Iterator<String> itMail = lsMail.iterator();
		boolean encontrado = false;
		String email, fileName, stJson;
		JSONArray jsArr;
		JSONObject jsonPer;
		while(itMail.hasNext()){
			email = itMail.next();			
			log4j.debug("<getListNPersonaFile> buscando email: " + email );
			
			Iterator<String> itFile = lsFile.iterator();
			while(itFile.hasNext() && !encontrado){
				fileName = itFile.next();
				
				log4j.debug("fileName: " + fileName );
				try{
					stJson = FileUtily.getBuilderNoTabsFile(PERSONA_JSON_DIR+ fileName, "UTF-8").toString();
					jsArr = new JSONArray(stJson);
					jsonPer = jsArr.getJSONObject(0);
					
					if(jsonPer.has("email")){
						if(jsonPer.getString("email").equals(email) ){
							lsPersona.add(fileName);
							encontrado = true;
							log4j.debug("email encontrado en "+fileName);
						}
					}
				}catch (Exception e){
					log4j.fatal("<getListNPersonaFile> Error al procesar "+ fileName );
				}
			}
			encontrado = false;
		}
		log4j.debug("<getListNPersonaFile> lsPersona.size: " + lsPersona.size() );
		return lsPersona;
	}
	
	/**
	 * Obtiene el listado read-*.json del directorio de trabajo (solo los que empiezan read-)
	 * @return
	 */
	private static List<String> listFileReadInDir(){
		log4j.debug("<listDirRead>");
		List<String> lsPersona = new ArrayList<String>();
		Iterator<String> itFilesInDir = FileUtily.listFilesInDir(PERSONA_JSON_DIR).iterator();
		String fileName;
		while(itFilesInDir.hasNext()){
			fileName = itFilesInDir.next();
			if(fileName.startsWith("read-")){
				lsPersona.add(fileName);
			}
		}
		log4j.debug("<listDirRead> Fin size: "+lsPersona.size());
		return lsPersona;
	}
	
	private static List<String> emailsList(){
		List<String> lsMail = new ArrayList<String>();
		lsMail.add("58862230@prodigy.net.mx");
		lsMail.add("aaron_aguilar2727@hotmail.com");
		lsMail.add("acollantesr@hotmail.com");
		lsMail.add("agarciac70@gmail.com");
		lsMail.add("alekperez79@hotmail.com");
		lsMail.add("alfredocarrillo64@hotmail.com");
		lsMail.add("amtzlpz1973@hotmail.com");
		lsMail.add("ar.go.ma@outlook.com");
		lsMail.add("arnulfperez083@gmail.com");
		lsMail.add("asanchez201274@live.com.mx");
		lsMail.add("axdireccion@gmail.com");
		lsMail.add("balamgab@yahoo.com.mx");
		lsMail.add("brunoro99@yahoo.com");
		lsMail.add("car1046@hotmail.com");
		lsMail.add("carlos700201@yahoo.com.mx");
		lsMail.add("carlosaranda60@hotmail.com");
		lsMail.add("carlos.saldana@itelcel.com");
		lsMail.add("c_enriquezr@outlook.com");
		lsMail.add("cesarpcarlos@hotmail.com");
		lsMail.add("chelo.1974@hotmail.com");
		lsMail.add("christian.jimenez.rh@gmail.com");
		lsMail.add("corina.vargas@gmail.com");
		lsMail.add("cosmesalvador@hotmail.com");
		lsMail.add("cruzarma62@gmail.com");
		lsMail.add("cs.joel@gmail.com");
		lsMail.add("csuarezl1973@gmail.com");
		lsMail.add("danielms888@yahoo.com.mx");
		lsMail.add("david.constante@hotmail.com");
		lsMail.add("davidmab85@hotmail.com");
		lsMail.add("dj.lopezp@gmail.com");
		lsMail.add("donajigo@gmail.com");
		lsMail.add("edgar.gtorres@hotmail.com");
		lsMail.add("edurom69@hotmail.com");
		lsMail.add("eleazar.esther@gmail.com");
		lsMail.add("elisardzmz@gmail.com");
		lsMail.add("emiranda.28@hotmail.com");
		lsMail.add("enriquecarrilloflores@hotmail.com");
		lsMail.add("enriquev@evheadhunter.com.mx");
		lsMail.add("erika_arguetap@hotmail.com");
		lsMail.add("ernesto_garcia_tapia@hotmail.com");
		lsMail.add("eservin2@hotmail.com");
		lsMail.add("farhidcime@gmail.com");
		lsMail.add("felbarrios007@gmail.com");
		lsMail.add("felipechiwaviles@hotmail.com");
		lsMail.add("fernando.gomez.iglesias@gmail.com");
		lsMail.add("ficoazcorra20@icloud.com");
		lsMail.add("fiilibusterosport@yahoo.com.mx");
		lsMail.add("fqperez@hotmail.com");
		lsMail.add("francisco_melendez54@yahoo.com.mx");
		lsMail.add("futole@hotmail.com");
		lsMail.add("fxmr61@yahoo.com.mx");
		lsMail.add("garciajncrls@hotmail.com");
		lsMail.add("gbrionesv@hotmail.com");
		lsMail.add("gcervantesmm@gmail.com");
		lsMail.add("gc.mfer@gmail.com");
		lsMail.add("gerardo1609@hotmail.com");
		lsMail.add("geroti@hotmail.com");
		lsMail.add("geyvol.lopez@gmail.com");
		lsMail.add("g.galvezh@hotmail.com");
		lsMail.add("gilemmmc@hotmail.com");
		lsMail.add("gmedra27@hotmail.com");
		lsMail.add("gromeroc75@gmail.com");
		lsMail.add("gsmorton@hotmail.com");
		lsMail.add("guadalupe.mendoza.plata@gmail.com");
		lsMail.add("guillermo.aleman1@hotmail.com");
		lsMail.add("guillermovm1968@yahoo.com.mx");
		lsMail.add("hazaaldana@hotmail.com");
		lsMail.add("hchagoya5@gmail.com");
		lsMail.add("howmar83@hotmail.com");
		lsMail.add("hugo.alarcong@live.com.mx");
		lsMail.add("humberto.ortiz.flores@gmail.com");
		lsMail.add("ingeniero_olivera@yahoo.com.mx");
		lsMail.add("ing-francisco2011@hotmail.com");
		lsMail.add("iscdmorales@gmail.com");
		lsMail.add("ivankendo@hotmail.com");
		lsMail.add("ivanrojo_02@yahoo.com.mx");
		lsMail.add("jaime_ocampo13@hotmail.com");
		lsMail.add("jcaaus@yahoo.com");
		lsMail.add("jcmadrig@hotmail.com");
		lsMail.add("jcmendoz191@hotmail.com");
		lsMail.add("jcu258@hotmail.com");
		lsMail.add("jelias.resendizv@gmail.com");
		lsMail.add("JERICKG@HOTMAIL.COM");
		lsMail.add("jes180968@hotmail.com");
		lsMail.add("jessica.ortiz@grupoalx.com");
		lsMail.add("jesus_251290@hotmail.com");
		lsMail.add("jesusdela_rosa@hotmail.com");
		lsMail.add("jesus.espejel.hernandez@gmail.com");
		lsMail.add("jesusgomezcruz@hotmail.com");
		lsMail.add("jevar.ram@gmail.com");
		lsMail.add("jfgomezguerra@hotmail.com");
		lsMail.add("jlcanino@hotmail.com");
		lsMail.add("jluis0409@hotmail.com");
		lsMail.add("jmoncada65@hotmail.com");
		lsMail.add("joestradam_0911@hotmail.com");
		lsMail.add("jomamtz2000@yahoo.com.mx");
		lsMail.add("jordymtz@yahoo.com");
		lsMail.add("jorge.rodriguezelizondo@gmail.com");
		lsMail.add("joseluis.davilas@hotmail.com");
		lsMail.add("josuearbaiza@gmail.com");
		lsMail.add("jrdzes@gmail.com");
		lsMail.add("jtrevillo@msn.com");
		lsMail.add("juanchemontedaw@hotmail.com");
		lsMail.add("juanc_serrano_e@yahoo.com.mx");
		lsMail.add("j.w.gutierrez@hotmail.com");
		lsMail.add("jxchavezc@gmail.com");
		lsMail.add("karlaesquivel74@outlook.com");
		lsMail.add("karla.victoria@hotmail.com");
		lsMail.add("kristelsantiagog@gmail.com");
		lsMail.add("lopezleticia465@yahoo.com.mx");
		lsMail.add("lorenzovaladez@yahoo.com.mx");
		lsMail.add("lsosa65@hotmail.com");
		lsMail.add("luispascacio@hotmail.com");
		lsMail.add("macorinapuyana@gmail.com");
		lsMail.add("manueldepuebla@hotmail.com");
		lsMail.add("maoryve@gmail.com");
		lsMail.add("marcial.viveros@live.com.mx");
		lsMail.add("mariotrevino68@gmail.com");
		lsMail.add("marisol.mmtz@hotmail.com");
		lsMail.add("martias2@hotmail.com");
		lsMail.add("martinperez_@hotmail.com");
		lsMail.add("matiws_018@hotmail.com");
		lsMail.add("max_alarkon11@hotmail.com");
		lsMail.add("mayteiveth.perez@gmail.com");
		lsMail.add("mbd83@hotmail.com");
		lsMail.add("merced.hernandezjuarez@hotmail.com");
		lsMail.add("mespinosa_monroy@hotmail.com");
		lsMail.add("midario_23@yahoo.com.mx");
		lsMail.add("mireromanf@yahoo.com.mx");
		lsMail.add("miruizv@hotmail.com");
		lsMail.add("m_mazon_m@hotmail.com");
		lsMail.add("mmreys@hotmail.com");
		lsMail.add("moika_wpa@hotmail.com");
		lsMail.add("mpeimbert@gmail.com");
		lsMail.add("mrico10@msn.com");
		lsMail.add("mvaca220173@gmail.com");
		lsMail.add("my-chi-vis@hotmail.com");
		lsMail.add("nanis99@yahoo.com");
		lsMail.add("nataliagbaena@gmail.com");
		lsMail.add("norberto_abarca@msn.com");
		lsMail.add("norma_acr@hotmail.com");
		lsMail.add("npintador@hotmail.com");
		lsMail.add("nso.acastaneda@gmail.com");
		lsMail.add("obrogelio@hotmail.com");
		lsMail.add("olivares.abraham@yahoo.com.mx");
		lsMail.add("omar9garcia@hotmail.com");
		lsMail.add("omar.romero755@gmail.com");
		lsMail.add("ontiveros.gabriel@hotmail.com");
		lsMail.add("orlando.garcia0@gmail.com");
		lsMail.add("ortizgo21@hotmail.com");
		lsMail.add("oscarbarrazaperalta@hotmail.com");
		lsMail.add("pacooa22@hotmail.com");
		lsMail.add("pancho.angel@hotmail.com");
		lsMail.add("paobar2911@gmail.com");
		lsMail.add("patrick_comerford@yahoo.com.mx");
		lsMail.add("paulettifernando@gmail.com");
		lsMail.add("pepe_cantero02@hotmail.com");
		lsMail.add("pepecortes@msn.com");
		lsMail.add("pepe.gonzalezfigueroa@gmail.com");
		lsMail.add("pherdezs@hotmail.com");
		lsMail.add("plinioriv@gmail.com");
		lsMail.add("psgarcia71@hotmail.com");
		lsMail.add("psyed14@hotmail.com");
		lsMail.add("pzuviri@hotmail.com");
		lsMail.add("qcrick@gmail.com");
		lsMail.add("rafael_alvarez_aguilar@hotmail.com");
		lsMail.add("rafael.lll.ramirez@gmail.com");
		lsMail.add("rafaelpablosc@gmail.com");
		lsMail.add("ramalijo@gmail.com");
		lsMail.add("raularrgon77@gmail.com");
		lsMail.add("ray_sg@hotmail.com");
		lsMail.add("rcruzbustamante@gmail.com");
		lsMail.add("rene.gomez.de.segura@gmail.com");
		lsMail.add("reneoceguera_78@yahoo.com.mx");
		lsMail.add("reygarciap@gmail.com");
		lsMail.add("RGMALA@HOTMAIL.COM");
		lsMail.add("rllano1980@gmail.com");
		lsMail.add("rlopez12486@hotmail.com");
		lsMail.add("robertoprocelmagaldi@gmail.com");
		lsMail.add("rodriguez_jesus@hotmail.com");
		lsMail.add("rolandoflores_c@hotmail.com");
		lsMail.add("rolando.orozco.mendieta@gmail.com");
		lsMail.add("rola.pamo@gmail.com");
		lsMail.add("rpguzmanunibe@yahoo.com.mx");
		lsMail.add("rrvilla_1@hotmail.com");
		lsMail.add("sainzdiego1978@gmail.com");
		lsMail.add("sanferleo80@gmail.com");
		lsMail.add("saurinact@gmail.com");
		lsMail.add("segysa-passt@outlook.com");
		lsMail.add("sergiokuri@outlook.com");
		lsMail.add("silvapugamanuel@gmail.com");
		lsMail.add("sozapatac@yahoo.es");
		lsMail.add("t.diazdelapena@gmail.com");
		lsMail.add("terresliz@hotmail.com");
		lsMail.add("tizoc-ml@hotmail.com");
		lsMail.add("torreblanca98@hotmail.com");
		lsMail.add("valery17_delao@hotmail.com");
		lsMail.add("victorlopez_23@hotmail.com");
		lsMail.add("victorm.gutierrezj58@gmail.com");
		lsMail.add("viictor.calderon@live.com.mx");
		lsMail.add("vsdelgado@live.com.mx");
		lsMail.add("waco_v@hotmail.com");
		lsMail.add("walter.wm619@gmail.com");
		lsMail.add("ww4ur1c10@gmail.com");
		lsMail.add("zury_op@hotmail.com");
		
		return lsMail;
	}

}
