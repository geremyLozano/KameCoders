
package pe.edu.pucp.dbmanager.config;
/*import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;*/

public class DBPoolManager {
    /*private static DBPoolManager dbManager;
    private BasicDataSource dataSource;
    private final String host = "pruebadb.c5s044w8ei2l.us-east-1.rds.amazonaws.com";
    private final String port = "3306";
    private final String db = "tienda";
    private final String username = "admin";
    private final String password = "12345678";
    
    private DBPoolManager(){
        connectToDatabase();
    }
    
    private void connectToDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://"+host + ":" + port + "/" + db; 
            dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            //pool connection configuration
            dataSource.setInitialSize(3); 
            dataSource.setMaxTotal(10);            
            dataSource.setMaxOpenPreparedStatements(100);
            System.out.println("....conexion realizada...");
        }catch(ClassNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public Connection getConnection() throws SQLException{
        if(dataSource == null || dataSource.isClosed())
            connectToDatabase();
        return dataSource.getConnection();
    }
    
    public synchronized static DBPoolManager getInstance(){
        if(dbManager == null){
             dbManager = new DBPoolManager();
        }
        return dbManager;
    } */
}
