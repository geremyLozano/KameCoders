package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AdministradorMySQL;


public class Principal_1 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Persona persona = new Persona();
//        persona.setDNI("OCHOYTREINTA");
//        persona.setNombre("Conexion2");
//        persona.setApellido("probando");
//        persona.setCorreoElectronico("prueba2@hotmail.com");
//        persona.setNumTelefono(123312);
//        persona.setDireccion("Av.Brasil 123");
//        persona.setFechaNacimiento(sdf.parse("23-10-2000"));
//        persona.setGenero('M');
//        
//        Usuario usuario = new Usuario();
//        usuario.setUsername("OCHOYTREINTA");
//        usuario.setContrasenha("admin123");
//        
//        Administrador admin = new Administrador();
        AdministradorDAO ad = new AdministradorMySQL();
//        int resultado = ad.insertar(admin, usuario, persona);
//        if(resultado>0)
//            System.out.println("Administrador ingreso correctamente");
//        else
//            System.out.println("Error en la creacion");
//        
//        Scanner in = new Scanner(System.in);
//        String lect = in.next();
//        admin.setNombre("NOMBREMODIFICADO");
//        int valor = ad.modificar(admin);
//        if(valor>0)
//            System.out.println("Administrador modificado correctamente");
//        else
//            System.out.println("Error en la modificacion");
        ad.eliminar(24);
    }
}
