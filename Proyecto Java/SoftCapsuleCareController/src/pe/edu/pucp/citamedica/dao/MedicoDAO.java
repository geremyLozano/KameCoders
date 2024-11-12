    package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

/**
 *
 * @author berna
 */
public interface MedicoDAO {
    int insertar(Medico medico, Usuario usuario);
    int modificar(Medico medico);
    int eliminar(int idMedico);
    ArrayList<Medico> listarTodos();
    Medico obtenerPorId(int idMedico);
    ArrayList<Medico> listarPorEspecialidad(String especialidad);
    ArrayList<Medico> listarTodos1();
}
