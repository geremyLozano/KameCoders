package pe.edu.pucp.capsuleCare.users.mysql;

import java.sql.Connection;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import pe.edu.pucp.capsuleCare.users.dao.AseguradoraDAO;
import pe.edu.pucp.citamedica.model.usuario.Aseguradora;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class AseguradoraMySQL implements AseguradoraDAO {
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;

    @Override
    public int insertar(Aseguradora aseguradora) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_INSERTAR(?,?,?,?,?,?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, aseguradora.getNombre());
            cst.setString(2, aseguradora.getDireccion());
            cst.setInt(3, aseguradora.getTelefono());
            cst.setString(4, aseguradora.getTipoSeguro());
            cst.setDouble(5, aseguradora.getPorcentajeDescuento());
            cst.setBoolean(6, aseguradora.isActivo());
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }
        return resultado;
    }

    @Override
    public int modificar(Aseguradora aseguradora) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_MODIFICAR(?,?,?,?,?,?,?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, aseguradora.getIdAseguradora());
            cst.setString(2, aseguradora.getNombre());
            cst.setString(3, aseguradora.getDireccion());
            cst.setInt(4, aseguradora.getTelefono());
            cst.setString(5, aseguradora.getTipoSeguro());
            cst.setDouble(6, aseguradora.getPorcentajeDescuento());
            cst.setBoolean(7, aseguradora.isActivo());
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }
        return resultado;
    }

    @Override
    public int eliminar(int idAseguradora) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAseguradora);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }
        return resultado;
    }

    @Override
    public ArrayList<Aseguradora> listarTodos() {
        ArrayList<Aseguradora> aseguradoras = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call ASEGURADORA_LISTAR()}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();

            while (rs.next()) {
                Aseguradora aseguradora = new Aseguradora();
                aseguradora.setIdAseguradora(rs.getInt("idAseguradora"));
                aseguradora.setNombre(rs.getString("nombre"));
                aseguradora.setDireccion(rs.getString("direccion"));
                aseguradora.setTelefono(rs.getInt("telefono"));
                aseguradora.setTipoSeguro(rs.getString("tipoSeguro"));
                aseguradora.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
                aseguradora.setActivo(rs.getBoolean("activo"));
                aseguradoras.add(aseguradora);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }
        return aseguradoras;
    }

    @Override
    public Aseguradora obtenerPorId(int idAseguradora) {
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
                aseguradora.setNombre(rs.getString("nombre"));
                aseguradora.setDireccion(rs.getString("direccion"));
                aseguradora.setTelefono(rs.getInt("telefono"));
                aseguradora.setTipoSeguro(rs.getString("tipoSeguro"));
                aseguradora.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
                aseguradora.setActivo(rs.getBoolean("activo"));
            } else {
                System.out.println("No se encontró la Aseguradora");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }
        return aseguradora;
    }

    @Override
    public int insertarPacienteAseguradora(int idPaciente, int idAseguradora) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call PACIENTE_ASEGURADORA_INSERTAR(?,?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);
            cst.setInt(2, idAseguradora);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }
        return resultado;
    }

    @Override
    public ArrayList<Aseguradora> listarPorPaciente(int idPaciente) {
        ArrayList<Aseguradora> aseguradoras = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call listarAseguradorasDePaciente(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);
        
            rs = cst.executeQuery();

            while (rs.next()) {
                Aseguradora aseguradora = new Aseguradora();
                aseguradora.setIdAseguradora(rs.getInt("idAseguradora"));
                aseguradora.setNombre(rs.getString("nombre"));
                aseguradora.setDireccion(rs.getString("direccion"));
                aseguradora.setTelefono(rs.getInt("telefono"));
                aseguradora.setTipoSeguro(rs.getString("tipoSeguro"));
                aseguradora.setPorcentajeDescuento(rs.getDouble("porcentajeDescuento"));
                aseguradora.setActivo(rs.getBoolean("activo"));
                aseguradoras.add(aseguradora);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }
        return aseguradoras;
    }
    
    
    
}
