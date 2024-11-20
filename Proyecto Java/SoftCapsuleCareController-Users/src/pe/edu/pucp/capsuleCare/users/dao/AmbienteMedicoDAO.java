package pe.edu.pucp.capsuleCare.users.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.AmbienteMedico;

public interface AmbienteMedicoDAO {
    int insertar(AmbienteMedico aseguradora);
    int modificar(AmbienteMedico aseguradora);
    int eliminar(int idAmbienteMedico);
    ArrayList<AmbienteMedico> listarTodos();
    AmbienteMedico obtenerPorId(int idAmbienteMedico);
    
    int cambiarEstadoAmbiente(int idAmbienteMedico);
}
