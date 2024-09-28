package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.comunicacion.model.Comunicacion;
import pe.edu.pucp.citamedica.comunicacion.model.TipoComunicacion;
import pe.edu.pucp.citamedica.dao.ComunicacionDAO;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;
import pe.edu.pucp.citamedica.mysql.ComunicacionMySQL;
import pe.edu.pucp.citamedica.mysql.UsuarioMySQL;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public class Principal {

    public static void main(String args[]) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Usuario usuario = new Usuario();
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

//////////////PRUEBA PARA COMUNICACION//////////////////////
//        Comunicacion comu = new Comunicacion();
//        comu.setContenido("prueba 2 de comunicacion");
//        comu.setFechaComunicacion(sdf.parse("25-09-2024"));
//        comu.setTipo(TipoComunicacion.Sugerencia);
//        comu.setIdComunicacion(2);
        ComunicacionDAO comunica = new ComunicacionMySQL();
        int resultado = comunica.eliminar(2);
//        ArrayList<Comunicacion>comunicaciones = comunica.listarTodos();
//        for(Comunicacion c:comunicaciones){
//            System.out.println(c);
//        }
//        int resultado = comunica.insertar(comu);
//        int resultado = comunica.modificar(comu);
        if(resultado != 0)
            System.out.println("Conexion correcta");
        else
            System.out.println("Error en la conexion");
    }
    
}
