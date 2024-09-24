package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.citamedica.clinica.model.Auxiliar;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;
import pe.edu.pucp.citamedica.mysql.UsuarioMySQL;
import pe.edu.pucp.citamedica.usuario.model.Usuario;

public class Principal {

    public static void main(String args[]) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Usuario usuario = new Usuario();
//        //Datos de Persona-Usuario
//        usuario.setNombre("Miguel");
//        usuario.setApellido("Guanira");
//        usuario.setCorreoElectronico("miguelguanira@gmail.com");
//        usuario.setNumTelefono(990990345);
//        usuario.setDireccion("Av. Universitaria, 123");
//        usuario.setFechaNacimiento(sdf.parse("10-08-1970"));
//        usuario.setGenero('F');
//        //Datos de Usuario
//        usuario.setUsername(12345678);
//        usuario.setContrasenha("usuario1");
//        UsuarioDAO usuarioDao = new UsuarioMySQL();
//        int resultado = usuarioDao.insertar(usuario);
//        if(resultado == 1)
//            System.out.println("Se ha registrado con exito");
//        else
//            System.out.println("Ha ocurrido un error");
    }
    
}
