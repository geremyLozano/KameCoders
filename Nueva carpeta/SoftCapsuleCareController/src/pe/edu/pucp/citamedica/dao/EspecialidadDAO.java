package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.clinica.model.Especialidad;

public interface EspecialidadDAO{
    int insertar(Especialidad especialidad);
    int modificar(Especialidad especialidad);
    int eliminar(int idEspecialidad);
    ArrayList<Especialidad> listarTodos();
    Especialidad obtenerPorId(int idEspecialidad);
}
