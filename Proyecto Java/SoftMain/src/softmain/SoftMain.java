package softmain;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.EstadoCita;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;

public class SoftMain {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        // Creación de un paciente
        Paciente paciente = new Paciente();
        // Datos de Persona-Paciente
        paciente.setNombre("Maria");
        paciente.setApellido("Perez");
        paciente.setCorreoElectronico("mariaperez@gmail.com");
        paciente.setNumTelefono(990990345);
        paciente.setDireccion("San Miguel, 1234");
        paciente.setFechaNacimiento(sdf.parse("19-10-1987"));
        // Datos de Paciente
        paciente.setHistorialActivo(true);
        paciente.setGenero('F');

        // Insertar paciente en la base de datos
        PacienteDAO daoPaciente = new PacienteMySQL();
        int resultado = daoPaciente.insertar(paciente);

        if (resultado == 1) {
            System.out.println("El paciente ha sido registrado con éxito");

            // Supongamos que la cita está asociada a este paciente
            CitaMedica cita = new CitaMedica();
            cita.setPaciente(paciente);  // Asignar paciente a la cita
            cita.setFecha(sdf.parse("25-09-2024"));   // Fecha de la cita
            // Hora de la cita usando LocalTime
            cita.setHora(LocalTime.parse("10:30:00"));  // Marcar la hora de la cita

            // Insertar cita en la base de datos
            CitaMedicaDAO daoCita = new CitaMedicaMySQL();
            int resultadoCita = daoCita.insertar(cita);

            if (resultadoCita == 1) {
                System.out.println("Cita registrada con éxito");

                // Confirmar la asistencia del paciente a la cita
                cita.setEstado(EstadoCita.ASISTIDA);  // Marcar la cita como asistida
                int resultadoConfirmacion = daoCita.modificar(cita);

                if (resultadoConfirmacion == 1) {
                    System.out.println("Asistencia confirmada exitosamente");
                } else {
                    System.out.println("Error al confirmar la asistencia");
                }
            } else {
                System.out.println("Hubo un error al registrar la cita");
            }
        } else {
            System.out.println("Hubo un error al registrar el paciente");
        }
    }
}
