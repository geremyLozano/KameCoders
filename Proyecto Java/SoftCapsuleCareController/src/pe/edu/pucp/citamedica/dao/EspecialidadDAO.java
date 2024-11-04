package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;

public interface EspecialidadDAO{
    int insertar(Especialidad especialidad);
    int modificar(Especialidad especialidad);
    int eliminar(int idEspecialidad);
    ArrayList<Especialidad> listarTodos();
    Especialidad obtenerPorId(int idEspecialidad);
    Especialidad obtenerPorId1(int idEspecialidad);
    public int insertar1(Especialidad especialidad);
    List<Especialidad> listar(String filtro);
}
