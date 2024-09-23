package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.paciente.model.Paciente;

public interface PacienteDao {
    int insertar(Paciente paciente);
    int modificar(Paciente paciente);
    int eliminar(int idPaciente);
    ArrayList<Paciente> listarTodos();
    Paciente obtenerPorId(int idPaciente);
}
