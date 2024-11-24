package pe.edu.pucp.seguridad;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import pe.edu.pucp.capsuleCare.medical.dao.CitaMedicaDAO;
import pe.edu.pucp.capsuleCare.medical.mysql.CitaMedicaMySQL;

import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.EstadoCita;
import pe.edu.pucp.citamedica.model.usuario.Persona;



@WebListener
public class RecordatorioListener implements ServletContextListener {

    private static final String GMAIL_USER = "capsulecare2024@gmail.com";
    private static final String GMAIL_PASSWORD = "vopwspwyzcspgeis";
    private EmailService emailService;
    private ScheduledExecutorService scheduler;
    private CitaMedicaDAO citaDAO;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            emailService = new EmailService(GMAIL_USER, GMAIL_PASSWORD);
            citaDAO = new CitaMedicaMySQL();
            scheduler = Executors.newSingleThreadScheduledExecutor();
            
            // Programar la tarea para revisar las citas pendientes cada 10 minutos
            scheduler.scheduleAtFixedRate(this::checkAndSendReminders, 0, 10, TimeUnit.MINUTES);
            // Programar la tarea para revisar y finalizar citas cada 10 minutos
            scheduler.scheduleAtFixedRate(this::checkAndFinalizeAppointments, 0, 10, TimeUnit.MINUTES);
            
        } catch (Exception e) {
            System.err.println("Error al iniciar el listener: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }
    }

    private void checkAndSendReminders() {
        try {
            List<Persona> personas = new ArrayList<>();
            citaDAO.obtenerCitasPendientes(personas);

            if (!personas.isEmpty()) {
                for (Persona persona : personas) {
                    sendReminderEmail(persona);
                }
            } else {
                System.out.println("No hay citas médicas programadas para enviar recordatorio.");
            }
        } catch (Exception e) {
            System.err.println("Error al revisar y enviar recordatorios: " + e.getMessage());
        }
    }

    private void sendReminderEmail(Persona persona) {
        try {
            String emailBody = EmailTemplate.getReminderEmailTemplate(persona);
            emailService.sendEmail(
                persona.getCorreoElectronico(),
                "Recordatorio de Cita Médica",
                emailBody
            );
            System.out.println("Recordatorio enviado a: " + persona.getCorreoElectronico());
        } catch (Exception e) {
            System.err.println("Error al enviar recordatorio: " + e.getMessage());
        }
    }
    
    private void checkAndFinalizeAppointments() {
        try {
            ArrayList<CitaMedica> citas = citaDAO.listarTodos(); // Obtener todas las citas médicas

            for (CitaMedica cita : citas) {
                if (cita.getActivo() != false) { // Procesar solo citas activas
                    LocalDate fechaCita = new java.sql.Date(cita.getFecha().getTime()).toLocalDate();
                    LocalTime horaCita = cita.getHora(); // Hora de la cita
                    LocalTime ahoraHora = LocalTime.now(); // Hora actual
                    LocalDate hoy = LocalDate.now(); // Fecha actual

                    // Convertir fechas a String en formato "yyyy-MM-dd" para asegurarse
                    String fechaCitaStr = fechaCita.toString();
                    String hoyStr = hoy.toString();
                    
                    // Comparar las fechas
                    if (fechaCitaStr.equals(hoyStr)) {
                        // Si la hora actual está entre la hora de la cita y una hora después
                        if (ahoraHora.isAfter(horaCita) && ahoraHora.isBefore(horaCita.plusHours(1)) && 
                                cita.getEstado().toString().compareTo("En_progreso")!=0 && cita.getEstado().toString().compareTo("Pendiente")!=0 &&
                                cita.getEstado().toString().compareTo("Cancelada")!=0) {
                            citaDAO.actualizarEstadoCita(cita.getIdCitaMedica(), EstadoCita.En_progreso);
                            System.out.println("Cita cambiada a 'En_progreso': " + cita.getIdCitaMedica());
                        }
                        // Si la hora actual es mayor que la hora de la cita más una hora
                        else if (ahoraHora.isAfter(horaCita.plusHours(1)) && cita.getEstado().toString().compareTo("Finalizada")!=0 
                                && cita.getEstado().toString().compareTo("En_progreso") == 0) {
                            citaDAO.actualizarEstadoCita(cita.getIdCitaMedica(), EstadoCita.Finalizada);
                            System.out.println("Cita cambiada a 'Finalizado': " + cita.getIdCitaMedica());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al revisar y finalizar citas: " + e.getMessage());
            e.printStackTrace();
        }
    }

}