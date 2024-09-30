package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;

public class Principal_3 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Persona persona = new Persona();
//        persona.setDNI("16:07");
//        persona.setNombre("Conexion2");
//        persona.setApellido("probando");
//        persona.setCorreoElectronico("prueba2@hotmail.com");
//        persona.setNumTelefono(123312);
//        persona.setDireccion("Av.Brasil 123");
//        persona.setFechaNacimiento(sdf.parse("23-10-2000"));
//        persona.setGenero('M');
//        
//        Usuario usuario = new Usuario();
//        usuario.setUsername("16:07");
//        usuario.setContrasenha("admin123");
//        
//        Auxiliar aux = new Auxiliar();
//        AuxiliarDAO ad = new AuxiliarMySQL();
//        int resultado = ad.insertar(aux, usuario, persona);
//        if(resultado>0)
//            System.out.println("Auxiliar ingreso correctamente");
//        else
//            System.out.println("Error en la creacion");
//        Scanner in = new Scanner(System.in);
//        String dato = in.next();
//        aux.setNombre("AUXILIARMODIFICADO");
//        int valor = ad.modificar(aux);
//        if(valor>0)
//            System.out.println("Modificado correctamente");
//        else
//            System.out.println("ERROR al modificar");
        AuxiliarDAO ad = new AuxiliarMySQL();
        int valor = ad.eliminar(26);
        if(valor>0)
            System.out.println("Eliminado correctamente");
        else
            System.out.println("Error al eliminar");
    }
}
