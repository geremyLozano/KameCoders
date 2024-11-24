    package pe.edu.pucp.capsuleCare.users.dao;
import java.util.ArrayList;
import java.util.List;
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

    int insertarMedico1(Medico medico);
    ArrayList<Medico> listarTodos1();
    Medico obtenerPorId1(int idMedico);
    List<Medico> listarFiltro(String filtro);
    int modificar_v2(Medico medico); 
    int insertarNuevo(Medico medico, Usuario usuario);
    int modificarCompleto(Medico medico);
}
