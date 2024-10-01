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

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_insertar_procedimiento(?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros al procedimiento almacenado
            cst.setString(1, procedimiento.getNombre());
            cst.setDouble(2, procedimiento.getCosto());
            cst.setString(3, procedimiento.getDescripcion());
            cst.setString(4, procedimiento.getRequisitosPrevios());
            cst.setString(5, procedimiento.getTipo().name()); // Enum to String
            cst.setInt(6, idAmbienteMedico);
            cst.setDate(7, new java.sql.Date(System.currentTimeMillis())); // Fecha actual

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                System.out.println("Error: " + e.getMessage());
            } else {
                System.out.println("Error general: " + e.getMessage());
            }
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
    public int modificar(Procedimiento procedimiento) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_modificar_procedimiento(?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros al procedimiento almacenado
            cst.setInt(1, procedimiento.getIdProcedimiento());
            cst.setString(2, procedimiento.getNombre());
            cst.setDouble(3, procedimiento.getCosto());
            cst.setString(4, procedimiento.getDescripcion());
            cst.setString(5, procedimiento.getRequisitosPrevios());
            cst.setString(6, procedimiento.getTipo().name());
            cst.setDate(7, new java.sql.Date(System.currentTimeMillis()));  // Fecha actual

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();

            if (resultado > 0) {
                System.out.println("Procedimiento modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún procedimiento con ese ID.");
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                System.out.println("Error: " + e.getMessage());
            } else {
                System.out.println("Error general: " + e.getMessage());
            }
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

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_eliminar_logico_procedimiento(?)}";
            cst = con.prepareCall(sql);

            // Pasar el ID del procedimiento al procedimiento almacenado
            cst.setInt(1, idProcedimiento);

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();

            if (resultado > 0) {
                System.out.println("Procedimiento eliminado (lógicamente) correctamente.");
            } else {
                System.out.println("No se encontró ningún procedimiento con ese ID.");
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                System.out.println("Error: " + e.getMessage());
            } else {
                System.out.println("Error general: " + e.getMessage());
            }
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

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_listar_todos_procedimientos()}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Procedimiento
                Procedimiento procedimiento = new Procedimiento();

                procedimiento.setIdProcedimiento(rs.getInt("idProcedimiento"));
                procedimiento.setNombre(rs.getString("nombre"));
                procedimiento.setCosto(rs.getDouble("costo"));
                procedimiento.setDescripcion(rs.getString("descripcion"));
                procedimiento.setRequisitosPrevios(rs.getString("requisitosPrevios"));

                // Obtener el valor del tipo de procedimiento y asignarlo al enum
                TipoProcedimiento tipo = TipoProcedimiento.valueOf(rs.getString("tipoProcedimiento"));
                procedimiento.setTipo(tipo);

                // Agregar el procedimiento a la lista
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

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_obtener_procedimiento_por_id(?)}";
            cst = con.prepareCall(sql);

            // Pasar el parámetro (ID del procedimiento)
            cst.setInt(1, idProcedimiento);

            // Ejecutar la consulta
            rs = cst.executeQuery();

            // Verificar si se obtuvo algún resultado
            if (rs.next()) {
                procedimiento = new Procedimiento();

                // Asignar los valores del ResultSet al objeto Procedimiento
                procedimiento.setIdProcedimiento(rs.getInt("idProcedimiento"));
                procedimiento.setNombre(rs.getString("nombre"));
                procedimiento.setCosto(rs.getDouble("costo"));
                procedimiento.setDescripcion(rs.getString("descripcion"));
                procedimiento.setRequisitosPrevios(rs.getString("requisitosPrevios"));

                // Convertir el tipo de procedimiento (enum)
                TipoProcedimiento tipo = TipoProcedimiento.valueOf(rs.getString("tipoProcedimiento"));
                procedimiento.setTipo(tipo);

                // Comprobar si está activo o inactivo
                int activo = rs.getInt("activo");
                if (activo == 1) {
                    System.out.println("El procedimiento está activo.");
                } else {
                    System.out.println("El procedimiento está inactivo.");
                }
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
}
