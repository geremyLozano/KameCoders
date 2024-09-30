package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;

public class Principal_3 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Persona persona = new Persona();
        persona.setDNI("unaytreinta");
        persona.setNombre("Conexion2");
        persona.setApellido("probando");
        persona.setCorreoElectronico("prueba2@hotmail.com");
        persona.setNumTelefono(123312);
        persona.setDireccion("Av.Brasil 123");
        persona.setFechaNacimiento(sdf.parse("23-10-2000"));
        persona.setGenero('M');
        
        Usuario usuario = new Usuario();
        usuario.setUsername("unaytreinta");
        usuario.setContrasenha("admin123");
        
        Auxiliar aux = new Auxiliar();
        AuxiliarDAO ad = new AuxiliarMySQL();
        int resultado = ad.insertar(aux, usuario, persona);
        if(resultado>0)
            System.out.println("Auxiliar ingreso correctamente");
        else
            System.out.println("Error en la creacion");
    }
}
