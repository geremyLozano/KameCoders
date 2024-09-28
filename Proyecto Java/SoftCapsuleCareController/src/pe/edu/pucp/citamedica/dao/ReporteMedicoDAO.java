package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.consultas.model.ReporteMedico;

public interface ReporteMedicoDAO {
    int insertar(ReporteMedico reporteMedico);
    int modificar(ReporteMedico pacireporteMedicoente);
    int eliminar(int idReporteMedico);
    ArrayList<ReporteMedico> listarTodos();
    ReporteMedico obtenerPorId(int idReporteMedico);
}
