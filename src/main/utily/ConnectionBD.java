package utily;
/**
 * Clase Generica que realiza conexiones a Base de Datos
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ConnectionBD {
	
	private Connection connection = null;
	/* Parametros de conexión*/
    private String sDriver = "org.postgresql.Driver";
    private String sUrlConnection;
    private String sUser;
    private String sPassword;
    private String sDBaseName;
    
    /* objetos auxiliares */
    private Statement statementDB;
    private ResultSet rsDataBase;
    
    
    
    
    public ConnectionBD()
    {        
          sDriver = "";
          sUrlConnection = "localhost";	//localhost | ec2-52-2-148-215.compute-1.amazonaws.com | dothrinstance.cclfhplprrct.us-east-1.rds.amazonaws.com
          sUser = "dothr"; //  dothr  
          sPassword = "tc34dm1n";	// tc34dm1n    	
          sDBaseName = "xe";	// xe | jwpl_tables;
    }
    
    public ConnectionBD(String url, String dbName, String userName, String pwd){    	
    	sUrlConnection = url;
		sDBaseName = dbName;
    	sUser = userName; 
        sPassword = pwd;
    }
    
    /**
     * Metodo para probar la funcionalidad/Existencia del Driver
     */
    public void testDriver() {//throws DataBaseException{
      	System.out.println("...............Probando el Driver: "+ sDriver );
      	try
    	{
    	   Class.forName( sDriver );
    	   System.out.println("ok!!!");
    	}catch (ClassNotFoundException e) {
            // Could not find the database driver
    		 e.printStackTrace();
             System.out.println("failClass");//*/
      	}catch (Exception e)
    	{
    	   e.printStackTrace();
    	}
    	System.out.println("Termina TestDriver...............");
      }
    
    
    /**
     * Establecer una conexión con base a los parametros de Inicio
     * @return
     */
    public boolean getDbConn( ){
		String url = "";
		try {
			//url = "jdbc:oracle:thin:@"+ sUrlConnection+":"+sPort+":"+sDBaseName;
			url = "jdbc:postgresql://"+sUrlConnection+"/"+sDBaseName+"?characterEncoding=UTF-8";
			DriverManager.registerDriver (new org.postgresql.Driver());	//oracle.jdbc.driver.OracleDriver()
			connection = DriverManager.getConnection(url,sUser,sPassword);
			connection.setAutoCommit(false);
			statementDB = connection.createStatement();
			//System.out.println("Conexion obtenida");			
			return true;
			
		} catch (SQLException sqlex) {
			System.out.println("Error al conectar a JDBC");
			System.out.println("Error:" + sqlex);
			return false;
		}
	}
    
    public void testDate() throws Exception{
    	String qr = "SELECT now();";
    	ResultSet rs = getQuerySet(qr);
    	System.out.println(" Query: " + qr + "\n Result:");
		if(rs!= null ){
			while (rs.next()){
				System.out.println(rs.getString(1));
			}
		}
    }
    
    /**
     * Metodo para obtener resultados de un Query (Select) en SQL
     * @param sQuery
     * @return
     * @throws Exception
     */
    public ResultSet getQuerySet(String sQuery)throws Exception
    {
      //System.out.println("El Query solicitado es: "+sQuery + "\n\n);
    	try
    	{
    	   rsDataBase = statementDB.executeQuery(sQuery);
    	   return rsDataBase;
        }
        catch(SQLException sqle)
        {
           throw new Exception(sqle.getMessage());
        }
    }

    /**
     * Metodo para insertar/Actualizar registros por medio de Instruccion SQL
     * @param sQuery
     * @throws Exception
     */
    public void updateDataBase(String sQuery)throws Exception
    {
      //System.out.println("El Update solicitado es: "+sQuery);
    	try
    	{
    		connection.setAutoCommit(true);
    	   statementDB.executeUpdate(sQuery);
    	   //connection.commit();
        }
        catch(SQLException sqle)
        {  
           try
           {
              connection.rollback();	
              throw new Exception(sqle.getMessage());
           }
           catch(SQLException sqlex)
           {
              throw new Exception(sqlex.getMessage());
           }
        }
    }

 public void updateDataBaseNoCommit(String sQuery)throws Exception
{
  //System.out.println("El Update solicitado es: "+sQuery);
	try
	{
		connection.setAutoCommit(false);
	    statementDB.executeUpdate(sQuery);
	   //connection.commit();
    }
    catch(SQLException sqle)
    {  
       try
       {
          connection.rollback();	
          throw new Exception(sqle.getMessage());
       }
       catch(SQLException sqlex)
       {
          throw new Exception(sqlex.getMessage());
       }
    }
}
    
public void closeConnection()
{
    try
    {
    	if(statementDB!=null){
    		statementDB.close();
    	}
    	if(connection!=null){
    		connection.close();
    	}
      // System.out.println("Connection close");
    }
    catch(SQLException sqle)
    {
       System.out.println(sqle);	           
    }
}

public void commitQuery() throws SQLException{
	connection.commit();
}

/**
 * Ejemplo para leer la tabla de Persona en PostgreSQL
 * @param conn
 */
public static void testReadPersonas(ConnectionBD conn){
	try{
		String sql = "select * From Persona p " 
					+ " where p.id_persona < 15"
				;
		ResultSet rs = conn.getQuerySet(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		if(rs!= null ){
			int columnas = rsmd.getColumnCount();
			List<String> lsColumnName = new ArrayList<String>();
			for(int iCol = 0; iCol<columnas; iCol++){
				lsColumnName.add(iCol,rsmd.getColumnName(iCol+1));
			}
			System.out.println("lsColumnName= " + lsColumnName );
			while (rs.next()){
				StringBuilder st = new StringBuilder();
				for(int i=0; i<columnas; i++){
					st.append(rs.getString(i+1)).append("|");
				}
				String imp = st.toString();
				System.out.println(imp.substring(0,imp.length()-1));	
			}
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		conn.closeConnection();
	}
}

protected void testConn(String tableName, String filtro){
	boolean tablaVacia = true;
	try{
		String sql = "select * From "+tableName + (filtro!=null?" "+filtro:"");
		ResultSet rs = this.getQuerySet(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		if(rs!= null ){
			int columnas = rsmd.getColumnCount();
			List<String> lsColumnName = new ArrayList<String>();
			for(int iCol = 0; iCol<columnas; iCol++){
				lsColumnName.add(iCol,rsmd.getColumnName(iCol+1));
			}
			System.out.println("lsColumnName= " + lsColumnName );
			while (rs.next()){
				tablaVacia = false;
				StringBuilder st = new StringBuilder();
				for(int i=0; i<columnas; i++){
					st.append(rs.getString(i+1)).append("|");
				}
				String imp = st.toString();
				System.out.println(imp.substring(0,imp.length()-1));	
			}
			if(tablaVacia){
				System.out.println(">> NO HAY DATOS EN LA TABLA: "+ tableName +"<<");
			}
		}
	}catch (Exception e) {
		e.printStackTrace();
	}	
}


/**
 * 
 * @param args
 */
    public static void main(String[] args) {
		ConnectionBD conn = new ConnectionBD();
		conn.getDbConn();
		conn.testConn("Persona", "WHERE id_persona = 35");
		/*/ 1)Test Connection
		conn.testDriver();
		
		conn.closeConnection(); //*/		
		
		// 3) Query en otro metodo
//		testReadPersonas(conn); 
		//*/
		/*  2 ) Query's
		try{
			int nRegistros = 0;
			String sql = "select * From TELEFONOS t";
			ResultSet rs = conn.getQuerySet(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			if(rs!= null ){
				int columnas = rsmd.getColumnCount();
				while (rs.next()){
					StringBuilder st = new StringBuilder();
					for(int i=0; i<columnas; i++){
						st.append(rs.getString(i+1)).append("|");
					}
					String imp = st.toString();
					System.out.println(imp.substring(0,imp.length()-1));					
//					System.out.print("CVE_FONDO: " + rs.getString("CVE_FONDO"));
//					System.out.print(", CVE_CUENTA_MAYOR: " + rs.getString("CVE_CUENTA_MAYOR"));
					nRegistros++;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			conn.closeConnection();
		}
		System.out.println("total de registros: " + nRegistros); //*/
		
	}

}