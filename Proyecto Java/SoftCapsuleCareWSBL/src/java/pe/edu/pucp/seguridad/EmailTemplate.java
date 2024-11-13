package pe.edu.pucp.seguridad;

import pe.edu.pucp.citamedica.model.usuario.Persona;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmailTemplate {
    
    public static String getReminderEmailTemplate(Persona persona) {
        LocalDateTime citaTime = LocalDateTime.now().plusHours(1);
        citaTime = citaTime.withMinute(0).withSecond(0).withNano(0);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaFormateada = citaTime.format(formatter);
        
        return String.format("""
            <table cellpadding="0" cellspacing="0" border="0" width="100%%">
                <tr>
                    <td align="center">
                        <table cellpadding="0" cellspacing="0" border="0" width="600" style="border-collapse: collapse;">
                            <tr>
                                <td style="background-color: #00b9e8; padding: 20px; text-align: center;">
                                    <h1 style="color: white; margin: 0; font-family: Arial, sans-serif;">Recordatorio de Cita Médica</h1>
                                </td>
                            </tr>
                            <tr>
                                <td style="background-color: #f9f9f9; padding: 20px; font-family: Arial, sans-serif;">
                                    <p>Estimado(a) %s %s,</p>
                                    <p>Le recordamos que tiene una cita médica programada para hoy a las:</p>
                                    <p style="font-size: 24px; font-weight: bold; color: #00b9e8; text-align: center;">%s</p>
                                    <p>Por favor, llegue 15 minutos antes de su cita.</p>
                                </td>
                            </tr>
                            <tr>
                                <td style="text-align: center; padding: 20px; color: #666; font-family: Arial, sans-serif;">
                                    <p>Este es un correo automático, por favor no responda a este mensaje.</p>
                                    <p>© 2024 Sistema de Citas Médicas</p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            """, 
            persona.getNombre(), 
            persona.getApellido(), 
            horaFormateada
        );
    }
}