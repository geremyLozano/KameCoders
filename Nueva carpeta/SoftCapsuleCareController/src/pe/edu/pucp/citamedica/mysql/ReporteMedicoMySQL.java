package pe.edu.pucp.citamedica.mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.consultas.model.ReporteMedico;
import pe.edu.pucp.citamedica.dao.ReporteMedicoDAO;
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
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into ReporteMedico(diagnostico,tratamiento,enfermedad,fecha,"
                    + "values(?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, reporteMedico.getDiagnostico());
            pst.setString(2, reporteMedico.getTratamiento());
            pst.setString(3, reporteMedico.getEnfermedad());
            java.sql.Date sqlDate = new java.sql.Date(reporteMedico.getFecha().getTime());
            pst.setDate(4,sqlDate);
            pst.executeUpdate();
            
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;}

    @Override
    public int modificar(ReporteMedico reporteMedico) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE paciente SET tratamiento = ? WHERE idPaciente = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, reporteMedico.getTratamiento());
            pst.setInt(2, reporteMedico.getIdReporteMedico());
            
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int eliminar(int idReporteMedico) {
        int resultado = 0;
        String sql = "DELETE FROM ReporteMedico WHERE idReporteMedico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idReporteMedico);

            resultado = pst.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("ReporteMedico eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún ReporteMedico con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultado;
    }

    @Override
    public ArrayList<ReporteMedico> listarTodos() {
        ArrayList<ReporteMedico> reportesMedicos = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT idReporteMedico,diagnostico,tratamiento,enfermedad "
                    + "FROM ReporteMedico";
            rs = st.executeQuery(sql);
            while(rs.next()){
                ReporteMedico reporteMedico = new ReporteMedico();
                reporteMedico.setIdReporteMedico(rs.getInt("idReporteMedico"));
                reporteMedico.setDiagnostico(rs.getString("diagnostico"));
                reporteMedico.setTratamiento(rs.getString("tratamiento"));
                reporteMedico.setEnfermedad(rs.getString("enfermedad"));
                
                reportesMedicos.add(reporteMedico);
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
        return reportesMedicos;
    }

    @Override
    public ReporteMedico obtenerPorId(int idReporteMedico) {
        ReporteMedico reporteMedico = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT diagnostico, tratamiento,enfermedad FROM ReporteMedico "
                    + "WHERE idReporteMedico = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idReporteMedico);
  
            rs = pst.executeQuery();

            if (rs.next()) {
                reporteMedico = new ReporteMedico();
                reporteMedico.setDiagnostico(rs.getString("diagnostico"));
                reporteMedico.setTratamiento(rs.getString("tratamiento"));
                reporteMedico.setEnfermedad(rs.getString("enfermedad"));
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
        return reporteMedico;
    }
    
}
