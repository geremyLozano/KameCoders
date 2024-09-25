package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.comunicacion.model.Comunicacion;
import pe.edu.pucp.citamedica.comunicacion.model.TipoComunicacion;
import pe.edu.pucp.citamedica.dao.ComunicacionDAO;
import pe.edu.pucp.citamedica.mysql.ComunicacionMySQL;

public class Principal {

    public static void main(String args[]) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


//////////////PRUEBA PARA COMUNICACION//////////////////////
        //PRUEBA PARA INSERTAR
        Comunicacion comu = new Comunicacion();
        comu.setContenido("prueba 5 de comunicacion");
        comu.setFechaComunicacion(sdf.parse("25-09-1990"));
        comu.setTipo(TipoComunicacion.Queja);
        ComunicacionDAO comunica = new ComunicacionMySQL();
        int resultado = comunica.insertar(comu);
        
        if(resultado != 0)
            System.out.println("Conexion correcta");
        else
            System.out.println("Error en la conexion");
        
        
        //PARA MODIFICAR
//        Comunicacion comu = new Comunicacion();
//        comu.setContenido("prueba 5 de comunicacion");
//        comu.setFechaComunicacion(sdf.parse("29-09-1990"));
//        comu.setTipo(TipoComunicacion.Queja);
//        ComunicacionDAO comunica = new ComunicacionMySQL();
//        comu.setIdComunicacion(2);
//        int resultado = comunica.modificar(comu);
//        if(resultado != 0)
//            System.out.println("Conexion correcta");
//        else
//            System.out.println("Error en la conexion");
        
        //PARA VER TODOS
//        ComunicacionDAO comunica = new ComunicacionMySQL();
//        ArrayList<Comunicacion>comunicaciones = comunica.listarTodos();
//        for(Comunicacion c:comunicaciones){
//            System.out.println(c);
//        }
        
        //PARA ELIMINAR UNA FILA
//        ComunicacionDAO comunica = new ComunicacionMySQL();
//        int resultado = comunica.eliminar(2);
//        if(resultado != 0)
//            System.out.println("Conexion correcta");
//        else
//            System.out.println("Error en la conexion");
        

        //PARA BUSCAR POR ID
//        ComunicacionDAO comunica = new ComunicacionMySQL();
//        Comunicacion comu = comunica.obtenerPorId(1);
//        System.out.println(comu);
        
    }
    
}
