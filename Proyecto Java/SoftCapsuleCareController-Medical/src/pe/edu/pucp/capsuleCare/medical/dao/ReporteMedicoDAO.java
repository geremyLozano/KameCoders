package pe.edu.pucp.capsuleCare.medical.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.consultas.ReporteMedico;

public interface ReporteMedicoDAO {
    int insertar(ReporteMedico reporteMedico);
    int modificar(ReporteMedico pacireporteMedicoente);
    int eliminar(int idReporteMedico);
    ArrayList<ReporteMedico> listarTodos();
    ReporteMedico obtenerPorId(int idReporteMedico);
}
