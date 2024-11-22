package pe.edu.pucp.dbmanager.config;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBPoolManager {
    private static DBPoolManager dbManager;
    private BasicDataSource dataSource;
    private Connection con;
    private ResultSet rs;
    private final String host = "examenparcial.cpsopevx2pq7.us-east-1.rds.amazonaws.com";
    private final String port = "3306";
    private final String db = "mydb";
    private final String username = "admin";
    private final String password = "pucpprogra3#";
    
    private DBPoolManager(){
        connectToDatabase();
    }
    
    private void connectToDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://"+host + ":" + port + "/" + db + "?useSSL=false&connectTimeout=10000";
            dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            //pool connection configuration
            dataSource.setInitialSize(5); 
            dataSource.setMaxTotal(55);            
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
    }
    
    public void cerrarConexion() {
        if(rs != null){
            try{
                rs.close();
            }catch(SQLException ex){
                System.out.println("Error al cerrar el lector:" + ex.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();  
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión:" + ex.getMessage());
            }
        }
    }
    
    public int ejecutarProcedimiento(String nombreProcedimiento, Map<String, Object> parametrosEntrada, Map<String, Object> parametrosSalida) {
        int resultado = 0;
        try{
            CallableStatement cst = formarLlamadaProcedimiento(nombreProcedimiento, parametrosEntrada, parametrosSalida);
            if(parametrosEntrada != null)
                registrarParametrosEntrada(cst, parametrosEntrada);
            if(parametrosSalida != null)
                registrarParametrosSalida(cst, parametrosSalida);
        
            resultado = cst.executeUpdate();
        
            if(parametrosSalida != null)
                obtenerValoresSalida(cst, parametrosSalida);
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            cerrarConexion();
        }
        return resultado;
    }
    
    private void registrarParametrosEntrada(CallableStatement cs, Map<String, Object> parametros) throws SQLException {
        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (value) {
                case Integer entero -> cs.setInt(key, entero);
                case String cadena -> cs.setString(key, cadena);
                case Double decimal -> cs.setDouble(key, decimal);
                case Boolean booleano -> cs.setBoolean(key, booleano);
                case java.util.Date fecha -> cs.setDate(key, new java.sql.Date(fecha.getTime()));
                case Character caracter -> cs.setString(key, String.valueOf(caracter));
                case byte[] bytes -> cs.setBytes(key, bytes);
                default -> {
			cs.setString(key, value.toString());
                }
                // Agregar más tipos según sea necesario
            }
        }
    }
    
    private void registrarParametrosSalida(CallableStatement cst, Map<String, Object> params) throws SQLException {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String nombre = entry.getKey();
            int sqlType = (int) entry.getValue();
            cst.registerOutParameter(nombre, sqlType);
        }
    }
    
    public CallableStatement formarLlamadaProcedimiento(String nombreProcedimiento, Map<String, Object> parametrosEntrada, Map<String, Object> parametrosSalida) throws SQLException{
        con = getConnection();
        StringBuilder call = new StringBuilder("{call " + nombreProcedimiento + "(");
        int cantParametrosEntrada = 0;
        int cantParametrosSalida = 0;
        if(parametrosEntrada!=null) cantParametrosEntrada = parametrosEntrada.size();
        if(parametrosSalida!=null) cantParametrosSalida = parametrosSalida.size();
        int numParams =  cantParametrosEntrada + cantParametrosSalida;
        for (int i = 0; i < numParams; i++) {
            call.append("?");
            if (i < numParams - 1) {
                call.append(",");
            }
        }
        call.append(")}");
        return con.prepareCall(call.toString());
    }
    
    public ResultSet ejecutarProcedimientoLectura(String nombreProcedimiento, Map<String, Object> parametrosEntrada){
        try{
            CallableStatement cs = formarLlamadaProcedimiento(nombreProcedimiento, parametrosEntrada, null);
            if(parametrosEntrada!=null) 
                registrarParametrosEntrada(cs,parametrosEntrada);
            rs = cs.executeQuery();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }
    
    private void obtenerValoresSalida(CallableStatement cst, Map<String, Object> parametrosSalida) throws SQLException {
        for (Map.Entry<String, Object> entry : parametrosSalida.entrySet()) {
            String nombre = entry.getKey();
            int sqlType = (int) entry.getValue();
            Object value = null;
            switch (sqlType) {
                case Types.INTEGER -> value = cst.getInt(nombre);
                case Types.VARCHAR -> value = cst.getString(nombre);
                case Types.DOUBLE -> value = cst.getDouble(nombre);
                case Types.BOOLEAN -> value = cst.getBoolean(nombre);
                case Types.DATE -> value = cst.getDate(nombre);
                // Agregar más tipos según sea necesario
            }
            parametrosSalida.put(nombre, value);
        }
    }
}