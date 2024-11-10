package pe.edu.pucp.citamedica.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;


public class PruebaCitasMedicas {
    public static void main(String[] args) {
        // Crear un objeto LocalDateTime para el momento actual

        // Llamar al método obtenerCitasPendientes para obtener las citas dentro del rango de 1 hora
        List<Persona> personas = new ArrayList<>();  // Lista donde se agregarán las personas
        CitaMedicaDAO dao = new CitaMedicaMySQL();
        dao.obtenerCitasPendientes(personas);

        // Procesar las personas obtenidas (que tienen citas dentro del rango de 1 hora)
        if (!personas.isEmpty()) {
            for (Persona persona : personas) {
                // Procesar cada persona (enviar recordatorio, etc.)
                System.out.println("Recordatorio de cita médica para el paciente: " + persona.getNombre() + " " + persona.getApellido());
                System.out.println("Correo electrónico: " + persona.getCorreoElectronico());
                // Aquí puedes agregar más lógica para enviar un recordatorio, etc.
            }
        } else {
            System.out.println("No hay pacientes con citas médicas programadas dentro del rango de 1 hora.");
        }
    }
        
}
