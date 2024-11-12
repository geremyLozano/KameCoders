package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;


public interface ComunicacionDAO {
    int insertar(Comunicacion comunicacion);
    int modificar(Comunicacion comunicacion);
    int eliminar(int idComunicacion);
    ArrayList<Comunicacion> listarTodos();
    Comunicacion obtenerPorId(int idComunicacion);
}
