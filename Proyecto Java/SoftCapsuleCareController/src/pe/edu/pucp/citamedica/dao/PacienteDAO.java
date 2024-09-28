package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.usuario.Paciente;

public interface PacienteDAO{
    int insertar(Paciente paciente);
    int modificar(Paciente paciente);
    int eliminar(int idPaciente);
    ArrayList<Paciente> listarTodos();
    Paciente obtenerPorId(int idPaciente);
}
