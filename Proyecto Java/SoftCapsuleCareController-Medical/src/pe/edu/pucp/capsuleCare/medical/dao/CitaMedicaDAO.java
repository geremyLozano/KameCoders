package pe.edu.pucp.capsuleCare.medical.dao;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.EstadoCita;
import pe.edu.pucp.citamedica.model.usuario.Persona;



public interface CitaMedicaDAO {
    int insertar(CitaMedica citaMedica);
    int modificar(CitaMedica citaMedica);
    int eliminar(int idCitaMedica);
    ArrayList<CitaMedica> listarTodos();
    CitaMedica obtenerPorId(int idCitaMedica);
    ArrayList<CitaMedica> listarPorPaciente(int idPaciente);
    void obtenerCitasPendientes(List<Persona> personas);
    ArrayList<CitaMedica> listarPorMedico(int idMedico);
    int actualizarEstadoCita(int idCitaMedica, EstadoCita estado);
}
