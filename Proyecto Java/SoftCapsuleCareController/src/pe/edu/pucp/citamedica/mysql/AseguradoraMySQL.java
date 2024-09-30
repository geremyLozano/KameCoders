package pe.edu.pucp.citamedica.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import pe.edu.pucp.citamedica.dao.AseguradoraDAO;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.citamedica.model.usuario.Aseguradora;

public class AseguradoraMySQL implements AseguradoraDAO{
    private Connection con;
    private PreparedStatement pst;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;

    @Override
    public int insertar(Aseguradora aseguradora){
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_INSERTAR(?,?,?,?,?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, aseguradora.getDireccion());
            cst.setInt(2, aseguradora.getTelefono());
            cst.setString(3, aseguradora.getTipoSeguro());
            cst.setDouble(4, aseguradora.getPorcentajeDescuento());
            cst.setBoolean(5, aseguradora.isActivo());
            resultado = cst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    @Override
    public int modificar(Aseguradora aseguradora){
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_MODIFICAR(?,?,?,?,?,?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, aseguradora.getDireccion());
            cst.setInt(2, aseguradora.getTelefono());
            cst.setString(3, aseguradora.getTipoSeguro());
            cst.setDouble(4, aseguradora.getPorcentajeDescuento());
            cst.setBoolean(5, aseguradora.isActivo());
            resultado = cst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        return resultado;
    }
    
    @Override
    public int eliminar(int idAseguradora){
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAseguradora);
            resultado = cst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public ArrayList<Aseguradora> listarTodos(){
        ArrayList<Aseguradora> aseguradoras = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_LISTAR_TODAS()}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();

            while (rs.next()) {
                Aseguradora aseguradora = new Aseguradora();
                aseguradora.setDireccion(rs.getString("direccion"));
                aseguradora.setTelefono(rs.getInt("telefono"));
                aseguradora.setTipoSeguro(rs.getString("tipoSeguro"));
                aseguradora.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
                aseguradora.setActivo(rs.getBoolean("activo"));
                aseguradoras.add(aseguradora);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
        return aseguradoras;
    }
    
    @Override
    public Aseguradora obtenerPorId(int idAseguradora){
       Aseguradora aseguradora = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_BUSCAR_POR_ID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAseguradora);
            rs = cst.executeQuery();

            if (rs.next()) {
                aseguradora = new Aseguradora();
                aseguradora.setIdAseguradora(rs.getInt("idAseguradora"));
                aseguradora.setDireccion(rs.getString("direccion"));
                aseguradora.setTelefono(rs.getInt("telefono"));
                aseguradora.setTipoSeguro(rs.getString("tipoSeguro"));
                aseguradora.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
                aseguradora.setActivo(rs.getBoolean("activo"));
            }else{
                System.out.println("No se encontro la Aseguradora");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        return aseguradora;
    }
    
    /*@Override
    public int insertar(Aseguradora aseguradora){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Aseguradora(direccion,telefono,tipoSeguro,porcentajeDescuento,activo) values(?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, aseguradora.getDireccion());
            pst.setInt(2, aseguradora.getTelefono());
            pst.setString(3,aseguradora.getTipoSeguro());
            pst.setDouble(4, aseguradora.getPorcentajeDescuento());
            pst.setBoolean(5,aseguradora.isActivo());
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }
    @Override
    public int modificar(Aseguradora aseguradora){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE Aseguradora SET activo = ? WHERE idAseguradora = ?";
            pst = con.prepareStatement(sql);
            pst.setBoolean(1, aseguradora.isActivo());
            pst.setInt(2, aseguradora.getIdAseguradora());
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int eliminar(int idAseguradora){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAseguradora);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public ArrayList<Aseguradora> listarTodos(){
        ArrayList<Aseguradora> aseguradoras = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT idAseguradora,direccion,telefono,tipoSeguro,porcentajeDescuento,activo"
                    + " FROM Aseguradora WHERE "
                    + "activo = 1";
            rs = st.executeQuery(sql);
            while(rs.next()){
                Aseguradora aseguradora = new Aseguradora();
                aseguradora.setIdAseguradora(rs.getInt("idAseguradora"));
                aseguradora.setDireccion(rs.getString("direccion"));
                aseguradora.setTelefono(rs.getInt("telefono"));
                aseguradora.setTipoSeguro(rs.getString("tipoSeguro"));
                aseguradora.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
                aseguradora.setActivo(rs.getBoolean("activo"));
                aseguradoras.add(aseguradora);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return aseguradoras;
    }
    
    @Override
    public Aseguradora obtenerPorId(int idAseguradora){
        Aseguradora aseguradora = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT idAseguradora, activo FROM Aseguradora WHERE idAseguradora = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idAseguradora);
            rs = pst.executeQuery();

            if (rs.next()) {
                aseguradora = new Aseguradora();
                aseguradora.setIdAseguradora(rs.getInt("idAseguradora"));
                aseguradora.setActivo(rs.getBoolean("activo"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return aseguradora;
    }*/
}
