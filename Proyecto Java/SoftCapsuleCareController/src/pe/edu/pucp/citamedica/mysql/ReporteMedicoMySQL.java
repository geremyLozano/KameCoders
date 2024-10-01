package pe.edu.pucp.citamedica.mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.ReporteMedicoDAO;
import pe.edu.pucp.citamedica.model.consultas.ReporteMedico;
import pe.edu.pucp.dbmanager.config.DBManager;

public class ReporteMedicoMySQL implements ReporteMedicoDAO{
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(ReporteMedico reporteMedico) {
        int resultado = 0;

        // Suponiendo que ya tienes un método para obtener la CitaMedica relacionada de alguna manera
        int idCitaMedica = reporteMedico.getIdCitaMedica(); // Obtén el ID de la cita médica adecuada

        // Verificar que el ID de Cita Médica sea válido
        if (idCitaMedica <= 0) {
            System.out.println("Error: ID de Cita Médica no válido.");
            return resultado; // Salimos del método si el ID no es válido
        }

        try {
            con = DBManager.getInstance().getConnection();
            sql = "CALL sp_insertar_reporte_medico(?, ?, ?, ?, ?)"; // Agregando un parámetro más
            pst = con.prepareStatement(sql);

            // Configuramos los parámetros del reporte médico
            pst.setString(1, reporteMedico.getDiagnostico());
            pst.setString(2, reporteMedico.getTratamiento());
            pst.setString(3, reporteMedico.getEnfermedad());
            pst.setDate(4, new java.sql.Date(reporteMedico.getFecha().getTime())); // Convertimos java.util.Date a java.sql.Date
            pst.setInt(5, idCitaMedica);  // Aquí pasas el ID de Cita Médica

            // Ejecutar la inserción
            resultado = pst.executeUpdate();
            System.out.println("Reporte Médico insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la inserción del reporte médico: " + e.getMessage());
        } finally {
            // Cerrar la conexión y otros recursos si es necesario
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return resultado;
    }
    @Override
    public int modificar(ReporteMedico reporteMedico) {
        int resultado = 0;

        try {
            con = DBManager.getInstance().getConnection();
            sql = "CALL sp_modificar_reporte_medico(?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);

            // Configuramos los parámetros del reporte médico
            pst.setInt(1, reporteMedico.getIdReporteMedico());
            pst.setString(2, reporteMedico.getDiagnostico());
            pst.setString(3, reporteMedico.getTratamiento());
            pst.setString(4, reporteMedico.getEnfermedad());

            // Convertimos java.util.Date a java.sql.Date
            pst.setDate(5, new java.sql.Date(reporteMedico.getFecha().getTime()));

            // Ejecutamos la actualización
            resultado = pst.executeUpdate();

            // Verificar si la actualización fue exitosa
            if (resultado > 0) {
                System.out.println("Reporte médico modificado correctamente.");
            } else {
                System.out.println("No se encontró el reporte médico con el ID proporcionado.");
            }

        } catch (SQLException e) {
            // Capturamos el error en caso de que el procedimiento lanzara una excepción
            System.out.println("Error al modificar el reporte médico: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close(); // Cerrar PreparedStatement
                if (con != null) con.close(); // Cerramos la conexión en el bloque finally
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }

        return resultado;
    }


    @Override
    public int eliminar(int idReporteMedico) {
        int resultado = 0;

        try {
            con = DBManager.getInstance().getConnection();
            sql = "CALL sp_eliminar_logico_reporte_medico(?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idReporteMedico);

            // Ejecutar la eliminación lógica
            resultado = pst.executeUpdate();

            // Verificar si la eliminación lógica fue exitosa
            if (resultado > 0) {
                System.out.println("Reporte médico eliminado (lógicamente) correctamente.");
            } else {
                System.out.println("No se encontró ningún ReporteMedico con ese ID o ya está inactivo.");
            }

        } catch (SQLException e) {
            // Capturamos el error y mostramos el mensaje
            System.out.println("Error al eliminar el ReporteMedico: " + e.getMessage());
        } finally {
            // Cerrar recursos en el bloque finally
            try {
                if (pst != null) pst.close(); // Cerrar PreparedStatement
                if (con != null) con.close(); // Cerrar conexión
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<ReporteMedico> listarTodos() {
        ArrayList<ReporteMedico> reportesMedicos = new ArrayList<>();

        try {
            con = DBManager.getInstance().getConnection();
            sql = "CALL sp_listar_todos_reportes_medicos()";
            st = con.createStatement();
            rs = st.executeQuery(sql);

            // Iteramos sobre los resultados
            while (rs.next()) {
                // Creamos el objeto ReporteMedico usando el constructor y los valores obtenidos de la base de datos
                ReporteMedico reporteMedico = new ReporteMedico(
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getString("enfermedad"),
                    new java.util.Date(rs.getDate("fecha").getTime()),  // Convertimos java.sql.Date a java.util.Date
                    rs.getInt("idCitaMedica") // Incluimos el ID de la cita médica
                );

                // Agregamos el reporte a la lista
                reportesMedicos.add(reporteMedico);
            }

        } catch (SQLException e) {
            // Capturamos y mostramos cualquier error SQL
            System.out.println("Error al listar los reportes médicos: " + e.getMessage());
        } finally {
            // Cerrar recursos en el bloque finally
            try {
                if (rs != null) rs.close(); // Cerrar ResultSet
                if (st != null) st.close(); // Cerrar Statement
                if (con != null) con.close(); // Cerrar conexión
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return reportesMedicos;
    }


    @Override
    public ReporteMedico obtenerPorId(int idReporteMedico) {
        ReporteMedico reporteMedico = null;

        try {
            con = DBManager.getInstance().getConnection();
            sql = "CALL sp_obtener_reporte_medico_por_id(?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idReporteMedico);

            rs = pst.executeQuery();

            if (rs.next()) {
                // Utilizar el constructor para crear el objeto ReporteMedico
                reporteMedico = new ReporteMedico(
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getString("enfermedad"),
                    new java.util.Date(rs.getDate("fecha").getTime()), // Convertimos java.sql.Date a java.util.Date
                    rs.getInt("idCitaMedica") // Incluyendo el ID de Cita Médica
                );
            } else {
                System.out.println("No se encontró el reporte médico con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el reporte médico: " + e.getMessage());
        } finally {
            // Cerrar recursos en el bloque finally
            try {
                if (rs != null) rs.close(); // Cerrar ResultSet
                if (pst != null) pst.close(); // Cerrar PreparedStatement
                if (con != null) con.close(); // Cerrar conexión
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return reporteMedico;
    }


}
