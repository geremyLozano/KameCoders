package pe.edu.pucp.seguridad;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;

@WebListener
public class RecordatorioListener implements ServletContextListener {

    private static final String GMAIL_USER = "josephaparicio12@gmail.com";
    private static final String GMAIL_PASSWORD = "tnqraecksivopdcn";
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
}