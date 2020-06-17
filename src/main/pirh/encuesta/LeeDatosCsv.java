package pirh.encuesta;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import utily.FileUtily;

public class LeeDatosCsv {

	
	public static void main(String[] args) {
		try {
//			genUsuarios();
//			generaPwds(86);
			System.out.println( encrypSHA256("Xochimilco0") );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void generaPwds(int numero) throws Exception{
		String pwd;
		for(int i=0;i<numero;i++){
			pwd = getRandomPwd(8);
			System.out.println(pwd + "\t"+encrypSHA256(pwd) );
		}
	}
	
	/**
	 * Encripta una cadena con SHA-256
	 * @param cadena
	 * @return
	 * @throws Exception
	 */
	protected static String encrypSHA256(String cadena) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(cadena.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}

	/**
	 *  obtiene una cadena alfanumerica en Mayusculas aleatoria
	 * @param longitud
	 * @return
	 */
	protected static String getRandomPwd (int longitud){//getCadenaAlfanumAleatoria (int longMin, int longMax){
		//int longMin = longitud;
		String cadenaAleatoria = "";
		int i = 0;
		int numRandom=0;
		do{
			numRandom=new Random().nextInt(longitud+1);
		}while( longitud > numRandom );
		//Despues de obtener el tamaño de la cadena, se consigue la correspondiente
		while ( i < numRandom){
			char c = (char)new Random().nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}
	
	private static void genUsuarios() throws Exception {
		List<String> lsLinea = FileUtily.getLinesFile("/home/dothr/Documents/SELEX/Encuesta/BaseEvaluad.csv","ISO-8859-1");
		Iterator<String> itLinea, itNombre;
		String linea, nombre, email; 
		List<String> tokens;
		List<String> lsNombres = new ArrayList<String>();
		List<UserDto> lsUsers = new ArrayList<UserDto>();
		int iUser = 10;	//numero de usuario (idPersona) inicial
		boolean existe = false;
		//Carga de Token(0) (Evaluador/Usuario)
				itLinea = lsLinea.iterator();
				while(itLinea.hasNext()){
					linea = itLinea.next();
					existe = false;
					
//					System.out.println(linea+":");
					tokens = Arrays.asList( linea.split("\\s*;\\s*") );
//					System.out.println(tokens.size() + "=> " + tokens);
					nombre = tokens.get(0);
					email = tokens.get(2);
					itNombre = lsNombres.iterator();
					while(itNombre.hasNext()){
						if(itNombre.next().equals(nombre)){
							existe = true;
						}
					}
					
					if(!existe){
						lsNombres.add(nombre);
						UserDto dtoTmp = new UserDto();
						dtoTmp.setIdUser(new Long(iUser));
						dtoTmp.setNombre(nombre);
						dtoTmp.setEmail( "usuario-"+iUser+"@coflex.com.mx"); //email );
						dtoTmp.setPwd( "123456789");//email.substring(0, email.indexOf("@")) );
						lsUsers.add(dtoTmp);
						iUser++;
					}
//					iLinea++;
				}
//				System.out.println(iLinea + " Lineas leidas");
				//Carga de Token(1) (Evaluado)
				itLinea = lsLinea.iterator();
				while(itLinea.hasNext()){
					linea = itLinea.next();
					existe = false;
					
//					System.out.println(linea+":");
					tokens = Arrays.asList( linea.split("\\s*;\\s*") );
//					System.out.println(tokens.size() + "=> " + tokens);
					nombre = tokens.get(1);
					email = tokens.get(2);
					itNombre = lsNombres.iterator();
					while(itNombre.hasNext()){
						if(itNombre.next().equals(nombre)){
							existe = true;
						}
					}
					
					if(!existe){
						lsNombres.add(nombre);
						UserDto dtoTmp = new UserDto();
						dtoTmp.setIdUser(new Long(iUser));
						dtoTmp.setNombre(nombre);
						dtoTmp.setEmail( "usuario-"+iUser+"@coflex.com.mx"); //email );
						dtoTmp.setPwd( "123456789");//email.substring(0, email.indexOf("@")) );
						lsUsers.add(dtoTmp);
						iUser++;
					}
//					iLinea++;
				}
//				System.out.println(iLinea + " Lineas leidas");
		
		System.out.println("\n**********\nPersonas: " + lsNombres.size() +"\n");
		printPersona(lsUsers);
		printEval(lsLinea, lsUsers);
	}
	
	
	private static Long getIdUser(List<UserDto> lsUsers, String nombre){
		Iterator<UserDto> itUser = lsUsers.iterator();
		UserDto dto;
		Long idUser = null;
		while(itUser.hasNext()){
			dto = itUser.next();
			if(dto.getNombre().equals(nombre)){
				idUser = dto.getIdUser();
			}
		}
		return idUser;
	}
	
	protected static void printEval(List<String> lsLinea, List<UserDto> lsUsers){
		//Map<Integer,String> mapPerfil = new HashMap<Integer, String>();
		Map<String,String> mapPerfil = new HashMap<String, String>();
		mapPerfil.put("1","AutoEvaluado");mapPerfil.put("2","Jefe");mapPerfil.put("3","Cliente Interno");
		mapPerfil.put("4","Par / Colateral");mapPerfil.put("5","Colaborador subordinado");mapPerfil.put("6","Externo");		
		int idEval = 1, idResp=1;
//		StringBuilder sb = new StringBuilder();
		StringBuilder sbEvaluacion = new StringBuilder();
		StringBuilder sbEncResp = new StringBuilder();
		
		List<String> tokens;
		Iterator<String> itLinea = lsLinea.iterator();
		
		String linea, usuario, evaluado, email, correlacion, idCorrelacion, code;
		Long idEvaluado=null, idUsuario=null;
		UserDto dto;
		sbEvaluacion.append("INSERT INTO enc_evaluacion VALUES \n");
		sbEncResp.append("INSERT INTO enc_respuesta VALUES \n");
		while(itLinea.hasNext()){
			linea = itLinea.next();
			tokens = Arrays.asList( linea.split("\\s*;\\s*") );
			evaluado = tokens.get(0);
			usuario = tokens.get(1);
//			System.out.println("evaluado: "+evaluado + ", usuario: " + usuario);
			email = tokens.get(2);
			correlacion = tokens.get(3);			
			idCorrelacion = tokens.get(4);//idPerfil
			code = tokens.get(5);
			
			
			idUsuario = getIdUser(lsUsers, usuario);
			idEvaluado = getIdUser(lsUsers, evaluado);
			
			
			sbEvaluacion
			.append("(")
			.append(idEval).append(",")
			.append(idUsuario).append(",")
			.append(idEvaluado).append(",")
			.append(idCorrelacion).append(",false")
			.append(itLinea.hasNext()?"),":");").append("\n");
			for(int x=0;x<9;x++){
				sbEncResp.append("(")
				.append(idResp).append(",")
				.append(idEval).append(",")
				.append((x+1)).append(",null)")
				.append(itLinea.hasNext()?",":x==8?";":",").append("\n");
				idResp++;
			}
			
			
			/*.append(usuario).append(";")//Original a comparar
			.append(evaluado).append(";")
			.append(email).append(";")
			.append(perfil).append(";")
			.append(idPerfil).append(";")
			.append(code).append("\n");//*/
			
			/*.append(idEval++).append(";") //COmplete
			.append(usuario).append(";")
			.append(idUsuario).append(";")
			.append(evaluado).append(";")
			.append(idEvaluado).append(";")
			.append(email).append(";")
			.append(perfil).append(";")
			.append(idPerfil).append(";")
			.append(code).append("\n");//*/
			idEval++;
			
		}
		
//		System.out.println(sb);
//		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/BaseEvaluad-Bis.csv", sb.toString(), false);
		
		sbEvaluacion.append("\n/* ********************* INSERTS DE RESPUESTAS  ********************** */\n")
		.append(sbEncResp);
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/TW-InsertsEvaluacion.sql", sbEvaluacion.toString(), false);
	}
	
	protected static void printPersona(List<UserDto> lsUsers){
		Iterator<UserDto> itUser = lsUsers.iterator();
		UserDto dto;
		
		/*  //Para PIRH
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("INSERT INTO persona (id_persona, nombre, email, pwd) VALUES ");
		while(itUser.hasNext()){
			dto = itUser.next();
			sbSql.append("(").append(dto.getIdUser()).append(",'")
			.append(dto.getNombre()).append("','")
			.append(dto.getEmail()).append("','").append(dto.getPwd()).append("')")
			.append(itUser.hasNext()?",":";");
		} //*/

		//Para XE
		StringBuilder sbPersona = new StringBuilder(), sbHistPws = new StringBuilder(), sbRelEmp = new StringBuilder();
//		int idPersona = 1;
		int idRol=6;
		sbPersona.append("INSERT INTO persona (id_persona, nombre, email, id_estatus_inscripcion, permiso_trabajo, ")
		.append(" numero_confirmar_inscripcion, numero_debe_publicar, fecha_creacion, fecha_confirmar_inscripcion) VALUES \n");
		
		sbHistPws.append("INSERT INTO HISTORICO_PASSWORD (id_historico_password,id_persona,password,fecha) VALUES \n");
		sbRelEmp.append(" INSERT INTO RELACION_EMPRESA_PERSONA (ID_RELACION_EMPRESA_PERSONA, ID_ROL, ID_EMPRESA, ID_PERSONA, FECHA_CREACION, REPRESENTANTE, ESTATUS_REGISTRO,CLAVE_INTERNA) VALUES \n");
		while(itUser.hasNext()){
			dto = itUser.next();
			sbPersona.append("(").append(dto.getIdUser()).append(",'")
			.append(dto.getNombre()).append("','")
			.append(dto.getEmail()).append("',")
			.append("2,true,3,3,'2014-11-06 00:00:00.295','2014-11-06 00:00:00.295')")
			.append(itUser.hasNext()?",":";").append("\n");
			//(3,3,'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225','2014-11-06 00:00:00.295');
			sbHistPws.append("(").append(dto.getIdUser()).append(",").append(dto.getIdUser())
			.append(",'15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225','2014-11-06 00:00:00.295')")
			.append(itUser.hasNext()?",":";").append("\n");
			//(3,3,1,3,'2014-11-06 00:00:00.295',false,true,null);
			sbRelEmp.append("(").append(dto.getIdUser()).append(",").append(idRol).append(",1,")
			.append(dto.getIdUser()).append(",'2014-11-06 00:00:00.295',false,true,null)")
			.append(itUser.hasNext()?",":";").append("\n");
		}
		
		sbPersona.append("\n /* **************  INSERTS HISTORICO PASSWORD ************** */\n")
		.append(sbHistPws);
		
		sbPersona.append("\n /* **************  INSERTS RELACION EMP-PERSONA ************** */\n")
		.append(sbRelEmp);
		
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/TW-InsertsEncuesta.sql", sbPersona.toString(), false);
		System.out.println(sbPersona);
	}
}
