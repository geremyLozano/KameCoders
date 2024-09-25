package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.clinica.model.AmbienteMedico;

public interface AmbienteMedicoDAO {
    int insertar(AmbienteMedico aseguradora);
    int modificar(AmbienteMedico aseguradora);
    int eliminar(int idAmbienteMedico);
    ArrayList<AmbienteMedico> listarTodos();
    AmbienteMedico obtenerPorId(int idAmbienteMedico);
}
