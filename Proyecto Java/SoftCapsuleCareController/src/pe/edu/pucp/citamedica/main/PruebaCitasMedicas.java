package pe.edu.pucp.citamedica.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;


public class PruebaCitasMedicas {
    public static void main(String[] args) {
        // Crear una instancia de Persona (debes tener una clase Persona con sus setters)
        Persona persona = new Persona();

        // Crear un objeto LocalDateTime con la fecha y hora específicas
        LocalDateTime reminderThreshold = LocalDateTime.of(2024, 11, 13, 12, 30, 0, 0);

        // Crear una instancia de la clase donde está el método obtenerCitasPendientes
        // Asumo que el nombre de la clase es "TuClase" y que tienes el método obtenerCitasPendientes implementado allí
        CitaMedicaDAO dao = new CitaMedicaMySQL();

        // Llamar al método obtenerCitasPendientes
        ArrayList<CitaMedica> citas = dao.obtenerCitasPendientes(reminderThreshold, persona);

        // Mostrar los resultados
        if (citas.isEmpty()) {
            System.out.println("No se encontraron citas pendientes.");
        } else {
            for (CitaMedica cita : citas) {
                System.out.println("Fecha de la cita: " + cita.getFecha());
                System.out.println("Hora de la cita: " + cita.getHora());
                System.out.println("ID del paciente: " + cita.getIdPaciente());
                System.out.println("Correo del paciente: " + persona.getCorreoElectronico());
                System.out.println("Nombre del paciente: " + persona.getNombre());
                System.out.println("Apellido del paciente: " + persona.getApellido());
            }
        }
    }
}
