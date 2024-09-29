package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AdministradorMySQL;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;


public class Principal_2 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        Persona persona = new Persona();
        persona.setDNI("MODIFICARV7");
        persona.setNombre("Conexion2");
        persona.setApellido("probando");
        persona.setCorreoElectronico("prueba2@hotmail.com");
        persona.setNumTelefono(123312);
        persona.setDireccion("Av.Brasil 123");
        persona.setFechaNacimiento(sdf.parse("23-10-2000"));
        persona.setGenero('M');
        
        Usuario usuario = new Usuario();
        usuario.setUsername("MODIFICARV7");
        usuario.setContrasenha("admin123");
        
        Paciente paciente = new Paciente();
        PacienteDAO pac = new PacienteMySQL();
        int resultado = pac.insertar(paciente, usuario, persona);

//        Auxiliar auxiliar = new Auxiliar();
//        AuxiliarDAO aux = new AuxiliarMySQL();
//        int resultado = aux.insertar(auxiliar, usuario, persona);

//        Administrador administrador = new Administrador();
//        AdministradorDAO admin = new AdministradorMySQL();
//        int resultado = admin.insertar(administrador, usuario, persona);
        if(resultado>0)
            System.out.println("Se creo el usuario correctamente");
        else
            System.out.println("Error en la creacion");
        
        Scanner lectura = new Scanner(System.in);
        String dato = lectura.next();
        paciente.setHistorialActivo(true);
        paciente.setNombre("modificarEXITOSO");
        int mod = pac.modificar(paciente);
        if(mod>0)
            System.out.println("Se modifico correctamente");
        else
            System.out.println("Error al modificar");
        
    }
}
