package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Medico;

/**
 *
 * @author berna
 */
public interface MedicoDAO {
    int insertar(Medico medico);
    int modificar(Medico medico);
    int eliminar(int idMedico);
    ArrayList<Medico> listarTodos();
    Medico obtenerPorId(int idMedico);
    ArrayList<Medico> listarPorEspecialidad(String especialidad);
}
