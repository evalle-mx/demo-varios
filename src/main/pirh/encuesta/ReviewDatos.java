/**
 * Clase emergente al aparecer una incongruencia entre correos, acceso y numero de evaluados
 */
package pirh.encuesta;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import utily.ConnectionBD;
import utily.FileUtily;

public class ReviewDatos extends LeeDatosCsv {
	
	private static int max = 86;
	private static int base = 10;
	

	public static void main(String[] args) {
//		contador();
		try {
//			contador();
//			pwdGen();
//			relPersona();
//			encuentraEvaluado();
//			getIds();
//			sqlEvaluacion();
			sqlUpdPwd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void contador(){
		
		for(int x=0; x<max; x++){
			System.out.println(base+x);
		}
	}

	
	//*
	public static void pwdGen() throws Exception{
		String pwd, encripPwd;
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("INSERT INTO HISTORICO_PASSWORD (id_historico_password,id_persona,password,fecha) VALUES \n");
		for(int x=0; x<max; x++){
			//System.out.println(base+x);
			pwd = getRandomPwd(8);
			encripPwd = encrypSHA256(pwd);
			System.out.println((base+x) +";" + pwd + ";"+ encripPwd );
			sbSql.append("(").append(base+x).append(",").append(base+x).append(",'").append(encripPwd).append("','2014-11-06 00:00:00.295'),\n");
		}
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-InsertsPwd.sql", sbSql.toString(), false);
	} 
	
	
	public static void relPersona() throws Exception {
		StringBuilder sbSql = new StringBuilder("INSERT INTO RELACION_EMPRESA_PERSONA \n(ID_RELACION_EMPRESA_PERSONA, ID_ROL, ID_EMPRESA, ID_PERSONA, FECHA_CREACION, REPRESENTANTE, ESTATUS_REGISTRO,CLAVE_INTERNA) VALUES \n");
		
		for(int x=0; x<max;x++){			
			sbSql.append("(").append(base+x).append(",6,2,").append(base+x).append(",'2014-11-06 00:00:00.295',false,true,null),\n");
			//(10,6,1,10,'2014-11-06 00:00:00.295',false,true,null),
		}
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-InsertsRelPers.sql", sbSql.toString(), false);
	}
	//*/
	
	
	
	public static void encuentraEvaluado() throws Exception {
		String fileBase = "/home/dothr/Documents/SELEX/Encuesta/BaseEvaluad.csv";
		List<String> lsLinea = FileUtily.getLinesFile(fileBase, "ISO-8859-1");//"ISO-8859-1"
		Iterator<String> itLinea = lsLinea.iterator();
		List<String> tokens;
		String evaluado, evaluador, email, correl, tipoRel; 
		StringBuilder sqlEvaluado = new StringBuilder();
		StringBuilder sqlUsuario = new StringBuilder();
		while(itLinea.hasNext()){
			tokens = Arrays.asList( itLinea.next().split("\\s*;\\s*") );
			evaluado = tokens.get(0);
			evaluador = tokens.get(1);
			email = tokens.get(2);
			correl = tokens.get(3);
			tipoRel = tokens.get(4);
//			correl = tokens.get(5);
			//System.out.println(evaluado+ " es evaluado por: " + evaluador +", como "+ correl + "["+tipoRel+"]");
			System.out.println(evaluado);
			sqlEvaluado.append("SELECT id_persona, nombre, email from persona where nombre ='").append(evaluado).append("';\n");
			sqlUsuario.append("SELECT id_persona, nombre, email from persona where nombre ='").append(evaluador).append("';\n");
		}
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-getIdEvaluado.sql", sqlEvaluado.toString(), false);		
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-getIdUsuario.sql", sqlUsuario.toString(), false);
	}
	
	
	public static void getIds() throws Exception {
		ConnectionBD connDb = new ConnectionBD();
		connDb.getDbConn();
		ResultSet rs;
		String fileEvaluados = "/home/dothr/Documents/SELEX/Encuesta/Fix/TW-getIdEvaluado.sql";
		
		String fileUsuarios = "/home/dothr/Documents/SELEX/Encuesta/Fix/TW-getIdUsuario.sql";
		List<String> lsLinea = FileUtily.getLinesFile(fileUsuarios);
				//FileUtily.getLinesFile(fileEvaluados);
		Iterator<String> itLinea = lsLinea.iterator();
		String sentencia; 
		StringBuilder sbIds = new StringBuilder();
		int registro =0;
		while(itLinea.hasNext()){
			sentencia = itLinea.next();
			System.out.println(sentencia);
			rs = connDb.getQuerySet(sentencia);
					//"SELECT id_persona from persona where nombre ='Olga Lydia Vitela Espinoza';");
			if(rs!= null ){
				while (rs.next()){
						//System.out.println(rs.getLong(1));
						sbIds.append(rs.getLong(1))
						.append(";")
						.append(rs.getString(2)).append(";").append(rs.getString(3))
						//.append(sentencia.replace("SELECT id_persona from persona where nombre ='", "").replace("';", ""))
								.append("\n");
						registro++;
					}
			}
		}
		System.out.println("se generaron "+registro +" ids");
		//FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-IDSEval.csv", sbIds.toString(), false);
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-IDSUsuario.csv", sbIds.toString(), false);
		
		connDb.closeConnection();
		
	}
	
	public static void sqlUpdPwd() throws Exception {
		String filePwd = "/home/dothr/Documents/SELEX/Encuesta/Fix/PassWord.csv";
		List<String> lsLinea = FileUtily.getLinesFile(filePwd);
		List<String> tokens;
		Iterator<String> itLinea = lsLinea.iterator();
		String idPersona, nombre, pwd, encrypt;
		
		/*/ 1 obtener idPersona
		ConnectionBD connDb = new ConnectionBD();
		connDb.getDbConn();
		ResultSet rs;
		String sentencia; 
		StringBuilder sbCsv = new StringBuilder();
		
		while(itLinea.hasNext()){
			tokens = Arrays.asList( itLinea.next().split("\\s*;\\s*") );
			nombre = tokens.get(0);
			sentencia= "SELECT id_persona, nombre from persona where nombre ='"+nombre+"';";
			rs = connDb.getQuerySet(sentencia);
			//"SELECT id_persona from persona where nombre ='Olga Lydia Vitela Espinoza';");
			if(rs!= null ){
				while (rs.next()){
						//System.out.println(rs.getLong(1));
						sbCsv.append(rs.getLong(1))
						.append(";")
						.append(rs.getString(2)).append("\n");
					}
			}
		}
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/PassWord-2.csv", sbCsv.toString(), false);
		//*/

		StringBuilder sbPwd = new StringBuilder();
		while(itLinea.hasNext()){
			tokens = Arrays.asList( itLinea.next().split("\\s*;\\s*") );
			idPersona = tokens.get(0);
			nombre = tokens.get(1);
			pwd = tokens.get(2);
			encrypt = tokens.get(3);
			sbPwd.append("UPDATE HISTORICO_PASSWORD SET password = '").append(encrypt)
					.append("' where id_persona = ").append(idPersona).append("; /*").append(pwd).append("*/ \n");
		}
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/UPDATE-H.PassWord.sql", sbPwd.toString(), false);
	}
	
	public static void sqlEvaluacion() throws Exception {
		String fileRel = "/home/dothr/Documents/SELEX/Encuesta/Fix/RelEvaluacion.csv";
		List<String> lsLinea = FileUtily.getLinesFile(fileRel);
		List<String> tokens;
		String idEvaluado, evaluado, idUsuario, usuario, email, correl, tipoEval;
		Iterator<String> itLinea = lsLinea.iterator();
		StringBuilder sbSqlEval = new StringBuilder("INSERT INTO enc_evaluacion (id_evaluacion, id_usuario, id_evaluado, id_correlacion, terminado) \nVALUES \n");
		StringBuilder sbSqlResp = new StringBuilder("INSERT INTO enc_respuesta (id_respuesta, id_evaluacion, id_reactivo, valor_numero) \nVALUES \n");
		int bEvalIndice = 1;
		int bRespIndice = 1;
		
		while(itLinea.hasNext()){
			tokens = Arrays.asList( itLinea.next().split("\\s*;\\s*") );
			idEvaluado = tokens.get(0);
			evaluado = tokens.get(1);
			idUsuario = tokens.get(2);
			usuario = tokens.get(3);
			email = tokens.get(4);
			correl = tokens.get(5);
			tipoEval = tokens.get(6);
			sbSqlEval.append("(").append(bEvalIndice).append(",").append(idUsuario).append(",").append(idEvaluado).append(",").append(tipoEval).append(",false)")
			.append(itLinea.hasNext()?",":";").append("\n");	
			for(int x=0; x<9;x++){
				sbSqlResp.append("(").append(bRespIndice).append(",").append(bEvalIndice).append(",").append(x+1).append(",null)")
				.append(",\n");
				bRespIndice++;
			}
			bEvalIndice++;
		}
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-INS-ENC-Evaluacion.sql", sbSqlEval.toString(), false);
		FileUtily.writeStringInFile("/home/dothr/Documents/SELEX/Encuesta/Fix/TW-INS-ENC-Resp.sql", sbSqlResp.toString(), false);
	}
}
