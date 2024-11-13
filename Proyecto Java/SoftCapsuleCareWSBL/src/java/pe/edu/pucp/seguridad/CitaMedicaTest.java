import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;

public class CitaMedicaTest {
    // Variables para la base de datos (simuladas aquí)
    private static Connection con;
    private static PreparedStatement pst;
    private static ResultSet rs;

    // Método main para probar la obtención de citas pendientes
    public static void main(String[] args) {
        List<Persona> personas = new ArrayList<>();
        CitaMedicaDAO dao = new CitaMedicaMySQL();
        // Llamar al método para obtener las citas pendientes
        dao.obtenerCitasPendientes(personas);
        
        // Mostrar los resultados
        if (personas.isEmpty()) {
            System.out.println("No hay citas pendientes.");
        } else {
            System.out.println("Citas pendientes para recordatorio:");
            for (Persona persona : personas) {
                System.out.println("Nombre: " + persona.getNombre() + " " + persona.getApellido());
                System.out.println("Correo: " + persona.getCorreoElectronico());
            }
        }
    }
}
