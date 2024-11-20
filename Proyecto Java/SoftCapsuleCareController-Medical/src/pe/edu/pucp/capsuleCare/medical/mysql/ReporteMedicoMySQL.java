package pe.edu.pucp.capsuleCare.medical.mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.capsuleCare.medical.dao.ReporteMedicoDAO;

import pe.edu.pucp.citamedica.model.consultas.ReporteMedico;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class ReporteMedicoMySQL implements ReporteMedicoDAO {
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;

    @Override
    public int insertar(ReporteMedico reporteMedico) {
        int resultado = 0;

        // Suponiendo que ya tienes un método para obtener la CitaMedica relacionada
        int idCitaMedica = reporteMedico.getIdCitaMedica(); // Obtén el ID de la cita médica adecuada

        // Verificar que el ID de Cita Médica sea válido
        if (idCitaMedica <= 0) {
            System.out.println("Error: ID de Cita Médica no válido.");
            return resultado; // Salimos del método si el ID no es válido
        }

        try {
            con = DBPoolManager.getInstance().getConnection(); // Cambié a DBManager para mantener consistencia
            sql = "CALL sp_insertar_reporte_medico(?, ?, ?, ?, ?)"; // Procedimiento para insertar
            pst = con.prepareStatement(sql);

            // Configuramos los parámetros del reporte médico
            pst.setString(1, reporteMedico.getDiagnostico());
            pst.setString(2, reporteMedico.getTratamiento());
            pst.setString(3, reporteMedico.getEnfermedad());
            pst.setDate(4, new java.sql.Date(reporteMedico.getFecha().getTime())); // Convertimos java.util.Date a java.sql.Date
            pst.setInt(5, idCitaMedica);  // Pasamos el ID de Cita Médica

            // Ejecutar la inserción
            resultado = pst.executeUpdate();
            System.out.println("Reporte Médico insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error en la inserción del reporte médico: " + e.getMessage());
        } finally {
            // Cerrar la conexión y otros recursos
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "CALL sp_modificar_reporte_medico(?, ?, ?, ?, ?)"; // Procedimiento para modificar
            pst = con.prepareStatement(sql);

            // Configuramos los parámetros del reporte médico
            pst.setInt(1, reporteMedico.getIdReporteMedico());
            pst.setString(2, reporteMedico.getDiagnostico());
            pst.setString(3, reporteMedico.getTratamiento());
            pst.setString(4, reporteMedico.getEnfermedad());
            pst.setDate(5, new java.sql.Date(reporteMedico.getFecha().getTime())); // Convertimos java.util.Date a java.sql.Date

            // Ejecutamos la actualización
            resultado = pst.executeUpdate();

            if (resultado > 0) {
                System.out.println("Reporte médico modificado correctamente.");
            } else {
                System.out.println("No se encontró el reporte médico con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el reporte médico: " + e.getMessage());
        } finally {
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
    public int eliminar(int idReporteMedico) {
        int resultado = 0;

        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "CALL sp_eliminar_logico_reporte_medico(?)"; // Procedimiento para eliminación lógica
            pst = con.prepareStatement(sql);
            pst.setInt(1, idReporteMedico);

            // Ejecutar la eliminación lógica
            resultado = pst.executeUpdate();

            if (resultado > 0) {
                System.out.println("Reporte médico eliminado (lógicamente) correctamente.");
            } else {
                System.out.println("No se encontró ningún ReporteMedico con ese ID o ya está inactivo.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el ReporteMedico: " + e.getMessage());
        } finally {
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
    public ArrayList<ReporteMedico> listarTodos() {
        ArrayList<ReporteMedico> reportesMedicos = new ArrayList<>();

        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "CALL sp_listar_todos_reportes_medicos()"; // Procedimiento para listar todos los reportes médicos
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                ReporteMedico reporteMedico = new ReporteMedico(
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getString("enfermedad"),
                    new java.util.Date(rs.getDate("fecha").getTime()), // Convertimos java.sql.Date a java.util.Date
                    rs.getInt("idCitaMedica") // Incluimos el ID de la cita médica
                );
                reportesMedicos.add(reporteMedico);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los reportes médicos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "CALL sp_obtener_reporte_medico_por_id(?)"; // Procedimiento para obtener reporte médico por ID
            pst = con.prepareStatement(sql);
            pst.setInt(1, idReporteMedico);

            rs = pst.executeQuery();

            if (rs.next()) {
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
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return reporteMedico;
    }
}
