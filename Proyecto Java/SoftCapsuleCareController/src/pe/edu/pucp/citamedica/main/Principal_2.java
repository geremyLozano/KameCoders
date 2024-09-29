package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.citamedica.dao.PersonaDAO;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.PersonaMySQL;


public class Principal_2 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        PersonaDAO pers = new PersonaMySQL();
        Persona persona = new Persona();
        persona.setDNI("54321");
        persona.setNombre("Conexion2");
        persona.setApellido("probando");
        persona.setCorreoElectronico("prueba2@hotmail.com");
        persona.setNumTelefono(123312);
        persona.setDireccion("Av.Brasil 123");
        persona.setFechaNacimiento(sdf.parse("23-10-2000"));
        persona.setGenero('M');
        Usuario usuario = new Usuario();
        usuario.setUsername("54321");
        usuario.setContrasenha("admin123");
        int resultado = pers.insertar(persona, usuario);
        if(resultado>0)
            System.out.println("Se creo el usuario correctamente");
        else
            System.out.println("Error en la creacion");
        
    }
}
