package pe.edu.pucp.citamedica.mysql;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.ProcedimientoDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.TipoProcedimiento;
import pe.edu.pucp.dbmanager.config.DBPoolManager;


/**
 *
 * @author Usuario
 */
public class ProcedimientoMySQL implements ProcedimientoDAO{
    
  
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;

    
    @Override
    public int insertar(Procedimiento procedimiento, int idAmbienteMedico) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL sp_insertar_procedimiento(?, ?, ?, ?, ?, ?, ?)}"; // Ahora incluye el parámetro fecha
            cst = con.prepareCall(sql);
            cst.setString(1, procedimiento.getNombre());
            cst.setDouble(2, procedimiento.getCosto());
            cst.setString(3, procedimiento.getDescripcion());
            cst.setString(4, procedimiento.getRequisitosPrevios());
            cst.setString(5, procedimiento.getTipoProcedimiento().name());
            cst.setInt(6, idAmbienteMedico);
            cst.setDate(7, new java.sql.Date(procedimiento.getFecha().getTime())); // Enviar la fecha
            
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }


    @Override
    public int modificar(Procedimiento procedimiento) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL sp_modificar_procedimiento(?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, procedimiento.getIdProcedimiento());
            cst.setString(2, procedimiento.getNombre());
            cst.setDouble(3, procedimiento.getCosto());
            cst.setString(4, procedimiento.getDescripcion());
            cst.setString(5, procedimiento.getRequisitosPrevios());
            cst.setString(6, procedimiento.getTipoProcedimiento().name());
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public int eliminar(int idProcedimiento) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL sp_eliminar_logico_procedimiento(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idProcedimiento);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<Procedimiento> listarTodos() {
        ArrayList<Procedimiento> listaProcedimiento = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL sp_listar_todos_procedimientos()}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while (rs.next()) {
                Procedimiento procedimiento = new Procedimiento();
                procedimiento.setIdProcedimiento(rs.getInt("idProcedimiento"));
                procedimiento.setNombre(rs.getString("nombre"));
                procedimiento.setCosto(rs.getDouble("costo"));
                procedimiento.setDescripcion(rs.getString("descripcion"));
                procedimiento.setRequisitosPrevios(rs.getString("requisitosPrevios"));
                TipoProcedimiento tipo = TipoProcedimiento.valueOf(rs.getString("tipoProcedimiento"));
                procedimiento.setTipoProcedimiento(tipo);
                listaProcedimiento.add(procedimiento);
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
        return listaProcedimiento;
    }

    @Override
    public Procedimiento obtenerPorId(int idProcedimiento) {
        Procedimiento procedimiento = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL sp_obtener_procedimiento_por_id(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idProcedimiento);
            rs = cst.executeQuery();

            if (rs.next()) {
                procedimiento = new Procedimiento();
                procedimiento.setIdProcedimiento(rs.getInt("idProcedimiento"));
                procedimiento.setNombre(rs.getString("nombre"));
                procedimiento.setCosto(rs.getDouble("costo"));
                procedimiento.setDescripcion(rs.getString("descripcion"));
                procedimiento.setRequisitosPrevios(rs.getString("requisitosPrevios"));
                TipoProcedimiento tipo = TipoProcedimiento.valueOf(rs.getString("tipoProcedimiento"));
                procedimiento.setTipoProcedimiento(tipo);
            } else {
                System.out.println("No se encontró ningún procedimiento con el ID proporcionado.");
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
        return procedimiento;
    }
    
    @Override
    public ArrayList<Procedimiento> listarPorPaciente(int idPaciente) {
        ArrayList<Procedimiento> listaProcedimientos = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL sp_obtener_procedimientos_por_paciente(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);
            rs = cst.executeQuery();

            while (rs.next()) {
                Procedimiento procedimiento = new Procedimiento();
                procedimiento.setIdProcedimiento(rs.getInt("idProcedimiento"));
                procedimiento.setNombre(rs.getString("nombre") != null ? rs.getString("nombre") : "Sin nombre");
                procedimiento.setDescripcion(rs.getString("descripcion") != null ? rs.getString("descripcion") : "Sin descripción");
                procedimiento.setCosto(rs.getObject("costo") != null ? rs.getDouble("costo") : 0.0);
                procedimiento.setRequisitosPrevios(rs.getString("requisitosPrevios") != null ? rs.getString("requisitosPrevios") : "Sin requisitos");
                String tipoProcedimientoStr = rs.getString("tipoProcedimiento");
                procedimiento.setTipoProcedimiento(tipoProcedimientoStr != null ? TipoProcedimiento.valueOf(tipoProcedimientoStr) : null);
                listaProcedimientos.add(procedimiento);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error cerrando conexión: " + ex.getMessage());
            }
        }
        return listaProcedimientos; // Siempre retorna una lista, aunque esté vacía
    }
}
