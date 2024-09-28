package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;

public interface CitaMedicaDAO {
    int insertar(CitaMedica citaMedica);
    int modificar(CitaMedica citaMedica);
    int eliminar(int idCitaMedica);
    ArrayList<CitaMedica> listarTodos();
    CitaMedica obtenerPorId(int idCitaMedica);
}
