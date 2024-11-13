package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.citamedica.model.dto.HistorialMedicoDto;

public interface HistorialMedicoDAO {
    int insertar(HistorialMedico historial);
    int modificar(HistorialMedico historial);
    int eliminar(int idHistorial);
    ArrayList<HistorialMedico> listarTodos();
    HistorialMedico obtenerPorId(int idHistorial);
    
    
     ArrayList<HistorialMedicoDto> listarTodosPorCampImp();
    
}