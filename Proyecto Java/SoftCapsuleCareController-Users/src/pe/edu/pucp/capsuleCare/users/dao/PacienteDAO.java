package pe.edu.pucp.capsuleCare.users.dao;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface PacienteDAO{
    int insertar(Paciente paciente, Usuario usuario);
    int modificar(Paciente paciente);
    int eliminar(int idPaciente);
    ArrayList<Paciente> listarTodos();
    Paciente obtenerPorId(int idPaciente);

    public Paciente obtenerPorId1(int idPaciente);
    public int insertar1(Paciente paciente);
    List<Paciente> listar(String filtro);
    int modificar_v2(Paciente paciente);
}
