
package pe.edu.pucp.dbmanager.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager dbManager;
    private Connection con;
    private final String host = "pruebaprog3.c7qeiwgs42xd.us-east-1.rds.amazonaws.com";
    private final String port = "3306";
    private final String db = "mydb";
    private final String username = "admin";
    private final String password = "pucpprogra3#";
    
    private DBManager(){
        connectToDatabase();
    }
    
    private void connectToDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://"+host + ":" + port + "/" + db; 
            this.con = DriverManager.getConnection(url, username, password);    
            System.out.println("....conexion realizada...");
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public Connection getConnection() throws SQLException{
        if(con == null || con.isClosed())
            connectToDatabase();
        return con;
    }
    
    public synchronized static DBManager getInstance(){
        if(dbManager == null){
             dbManager = new DBManager();
        }
        return dbManager;
    }  
}
