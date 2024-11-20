package softmain;

import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.AseguradoraDAO;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.dao.EspecialidadDAO;
import pe.edu.pucp.citamedica.dao.HistorialMedicoDAO;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.dao.PersonaDAO;
import pe.edu.pucp.citamedica.dao.ProcedimientoDAO;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.EstadoCita;
import pe.edu.pucp.citamedica.model.consultas.TipoCita;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.TipoProcedimiento;
import pe.edu.pucp.citamedica.mysql.AseguradoraMySQL;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;
import pe.edu.pucp.citamedica.mysql.EspecialidadMySQL;
import pe.edu.pucp.citamedica.mysql.HistorialMedicoMySQL;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;
import pe.edu.pucp.citamedica.mysql.PersonaMySQL;
import pe.edu.pucp.citamedica.mysql.ProcedimientoMySQL;

public class SoftMain {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        
        
        // Test por consola
        
        
//        // Creación de un paciente
//        Paciente paciente = new Paciente();
//        // Datos de Persona-Paciente
//        paciente.setNombre("Maria");
//        paciente.setApellido("Perez");
//        paciente.setCorreoElectronico("mariaperez@gmail.com");
//        paciente.setNumTelefono(990990345);
//        paciente.setDireccion("San Miguel, 1234");
//        paciente.setFechaNacimiento(sdf.parse("19-10-1987"));
//        // Datos de Paciente
//        paciente.setHistorialActivo(true);
//        paciente.setGenero('F');
//
//        // Insertar paciente en la base de datos
//        PacienteDAO daoPaciente = new PacienteMySQL();
//        int resultado = daoPaciente.insertar(paciente);
//
//        if (resultado == 1) {
//            System.out.println("El paciente ha sido registrado con éxito");
//
//            // Supongamos que la cita está asociada a este paciente
//            CitaMedica cita = new CitaMedica();
//            cita.setPaciente(paciente);  // Asignar paciente a la cita
//            cita.setFecha(sdf.parse("25-09-2024"));   // Fecha de la cita
//            // Hora de la cita usando LocalTime
//            cita.setHora(LocalTime.parse("10:30:00"));  // Marcar la hora de la cita
//
//            // Insertar cita en la base de datos
//            CitaMedicaDAO daoCita = new CitaMedicaMySQL();
//            int resultadoCita = daoCita.insertar(cita);
//
//            if (resultadoCita == 1) {
//                System.out.println("Cita registrada con éxito");
//
//                // Confirmar la asistencia del paciente a la cita
//                cita.setEstado(EstadoCita.ASISTIDA);  // Marcar la cita como asistida
//                int resultadoConfirmacion = daoCita.modificar(cita);
//
//                if (resultadoConfirmacion == 1) {
//                    System.out.println("Asistencia confirmada exitosamente");
//                } else {
//                    System.out.println("Error al confirmar la asistencia");
//                }
//            } else {
//                System.out.println("Hubo un error al registrar la cita");
//            }
//        } else {
//            System.out.println("Hubo un error al registrar el paciente");
//        }
// Formateador de fecha
        
        // Crear una instancia del DAO de Procedimiento
//            ProcedimientoDAO daoProcedimiento = new ProcedimientoMySQL();
//
//            // Llamar al método listarTodos()
//            ArrayList<Procedimiento> listaProcedimientos = daoProcedimiento.listarTodos();
//
//            // Verificar si la lista no está vacía
//            if (!listaProcedimientos.isEmpty()) {
//                System.out.println("Lista de procedimientos registrados:");
//                System.out.println("------------------------------------");
//
//                // Iterar sobre la lista y mostrar cada procedimiento
//                for (Procedimiento p : listaProcedimientos) {
//                    System.out.println("ID: " + p.getIdProcedimiento());
//                    System.out.println("Nombre: " + p.getNombre());
//                    System.out.println("Costo: " + p.getCosto());
//                    System.out.println("Descripción: " + p.getDescripcion());
//                    System.out.println("Requisitos Previos: " + p.getRequisitosPrevios());
//                    System.out.println("Tipo: " + p.getTipo());
//                    System.out.println("Activo: " + (p.isActivo() ? "Sí" : "No"));
//                    System.out.println("------------------------------------");
//                }
//            } else {
//                System.out.println("No se encontraron procedimientos registrados.");
//            }
        HistorialMedicoDAO dao = new HistorialMedicoMySQL();
         System.out.println(dao.obtenerPorPaciente(85).getIdHistorial()
         );
        
    }
}
