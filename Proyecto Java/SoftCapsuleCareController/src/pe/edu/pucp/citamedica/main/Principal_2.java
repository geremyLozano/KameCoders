package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;


public class Principal_2 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        PacienteDAO pac = new PacienteMySQL();
        Persona persona = new Persona();
        persona.setDNI("5050505");
        persona.setNombre("Conexion2");
        persona.setApellido("probando");
        persona.setCorreoElectronico("prueba2@hotmail.com");
        persona.setNumTelefono(123312);
        persona.setDireccion("Av.Brasil 123");
        persona.setFechaNacimiento(sdf.parse("23-10-2000"));
        persona.setGenero('M');
        
        Usuario usuario = new Usuario();
        usuario.setUsername("5050505");
        usuario.setContrasenha("admin123");
        
        Paciente paciente = new Paciente();
        
        int resultado = pac.insertar(paciente, usuario, persona);
        if(resultado>0)
            System.out.println("Se creo el paciente correctamente");
        else
            System.out.println("Error en la creacion");
        
    }
}
