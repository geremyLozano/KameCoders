package pe.edu.pucp.citamedica.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.usuario.Persona;

public interface CitaMedicaDAO {
    int insertar(CitaMedica citaMedica);
    int modificar(CitaMedica citaMedica);
    int eliminar(int idCitaMedica);
    ArrayList<CitaMedica> listarTodos();
    CitaMedica obtenerPorId(int idCitaMedica);
    ArrayList<CitaMedica> listarPorPaciente(int idPaciente);
    ArrayList<CitaMedica> obtenerCitasPendientes(LocalDateTime reminderThreshold, Persona persona);
}
