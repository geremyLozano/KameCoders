package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import pe.edu.pucp.citamedica.dao.ComunicacionDAO;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;
import pe.edu.pucp.citamedica.model.comunicacion.TipoComunicacion;
import pe.edu.pucp.citamedica.mysql.ComunicacionMySQL;
import pe.edu.pucp.citamedica.mysql.UsuarioMySQL;


public class PruebaTodos {
    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        ComunicacionDAO comu = new ComunicacionMySQL();
//        Comunicacion c = new Comunicacion();
//        c.setTipo(TipoComunicacion.Queja);
//        c.setContenido("prueba");
//        c.setFechaComunicacion(sdf.parse("12-12-2012"));
//        c.setIdPaciente(20);
//        System.out.println(comu.insertar(c)!=0 ? "Comunicacion insertada":"Error al registrar");
        UsuarioDAO usuarioDAO = new UsuarioMySQL();
        
        // ID de la persona que quieres verificar (cambia el valor según tus necesidades)
        int idPersona = 44;
        
        // Obtener roles activos para la persona
        List<String> rolesActivos = usuarioDAO.obtenerRoles(idPersona);
        
        // Imprimir roles activos
        if (rolesActivos.isEmpty()) {
            System.out.println("No hay roles activos para el usuario con ID: " + idPersona);
        } else {
            System.out.println("Roles activos para el usuario con ID " + idPersona + ":");
            for (String rol : rolesActivos) {
                System.out.println("- " + rol);
            }
        }
    }
}
