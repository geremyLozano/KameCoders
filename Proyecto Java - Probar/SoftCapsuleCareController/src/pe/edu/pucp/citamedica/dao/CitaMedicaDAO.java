package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.usuario.Persona;

public interface CitaMedicaDAO {
    int insertar(CitaMedica citaMedica);
    int modificar(CitaMedica citaMedica);
    int eliminar(int idCitaMedica);
    ArrayList<CitaMedica> listarTodos();
    CitaMedica obtenerPorId(int idCitaMedica);
    ArrayList<CitaMedica> listarPorPaciente(int idPaciente);
    void obtenerCitasPendientes(List<Persona> personas);
}