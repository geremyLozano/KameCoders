package pe.edu.pucp.capsuleCare.medical.dao;

import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;

public interface ComunicacionDAO {
    int insertar(Comunicacion comunicacion);
    int modificar(Comunicacion comunicacion);
    int eliminar(int idComunicacion);
    ArrayList<Comunicacion> listarTodos();
    Comunicacion obtenerPorId(int idComunicacion);
    ArrayList<Comunicacion> listarPorPaciente(int idPaciente); // Nuevo método
    ArrayList<Comunicacion> listarComunicacionesFiltradas(String tipo, String estado, Date fechaInicio, Date fechaFin, Integer idPaciente);
    public ArrayList<String[]> obtenerConteoPorEstado();
}
