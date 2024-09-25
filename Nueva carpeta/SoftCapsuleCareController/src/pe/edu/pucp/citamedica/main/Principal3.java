/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.clinica.model.AmbienteMedico;
import pe.edu.pucp.citamedica.clinica.model.TipoAmbiente;
import pe.edu.pucp.citamedica.dao.AmbienteMedicoDAO;
import pe.edu.pucp.citamedica.mysql.AmbienteMedicoMySQL;

/**
 *
 * @author Usuario
 */
public class Principal3 {
    
    
     public static void main(String[] args) throws ParseException {
         
         
         //INSERTAR

        AmbienteMedico ambiente = new AmbienteMedico(1,"torre 7",34,TipoAmbiente.Laboratorio);
        
        
        AmbienteMedicoDAO daoAmbiente = new AmbienteMedicoMySQL();
        
        int resultado = daoAmbiente.insertar(ambiente);
        
        
         if(resultado == 1)
            System.out.println("Se ha registrado con exito");
        else
            System.out.println("Ha ocurrido un error");
      

         //ELIMINAR 
         
//         AmbienteMedicoDAO daoAmbiente = new AmbienteMedicoMySQL();
//         
//         int resultado = daoAmbiente.eliminar(1);
//         
//         
//        if(resultado == 1)
//            System.out.println("Se ha eliminado con exito");
//        else
//            System.out.println("Ha ocurrido un error");
        
        


//        //LISTAR
//          AmbienteMedicoDAO daoAmbiente = new AmbienteMedicoMySQL();
//          
//          ArrayList<AmbienteMedico> lista= new ArrayList<AmbienteMedico>();
//
//         lista = daoAmbiente.listarTodos();
//         
//         
//         for(AmbienteMedico ambiente:lista){
//                    
//             System.out.println("idAmbiente : "+ ambiente.getIdAmbiente());
//              System.out.println("numPiso : "+ ambiente.getNumPiso());
//              System.out.println("ubicacion : "+ ambiente.getUbicacion());
//               System.out.println("capacidad : "+ ambiente.getCapacidad());
//                System.out.println("tipoAmbiente : "+ ambiente.getTipoAmbiente());
//         }
         

            //Obtener por id
            
            
//            AmbienteMedicoDAO daoAmbiente = new AmbienteMedicoMySQL();
//          
//            AmbienteMedico ambiente = new AmbienteMedico();
//
//            ambiente = daoAmbiente.obtenerPorId(1);
//
//            
//           System.out.println("idAmbiente : "+ ambiente.getIdAmbiente());
//           System.out.println("numPiso : "+ ambiente.getNumPiso());
//           System.out.println("ubicacion : "+ ambiente.getUbicacion());
//           System.out.println("capacidad : "+ ambiente.getCapacidad());
//           System.out.println("tipoAmbiente : "+ ambiente.getTipoAmbiente());
        
        
            // ELIMINAR     
//            AmbienteMedicoDAO daoAmbiente = new AmbienteMedicoMySQL();
//            int resultado = daoAmbiente.eliminar(1);                
//            if(resultado == 1)
//                       System.out.println("Se ha eliminado con exito");
//                   else
//                       System.out.println("Ha ocurrido un error");
            
            
            
            
        //MODIFICAR
            
//        AmbienteMedico ambiente = new AmbienteMedico(2,1,"torre 5",35,TipoAmbiente.Laboratorio);              
//        AmbienteMedicoDAO daoAmbiente = new AmbienteMedicoMySQL();
//        
//        int resultado = daoAmbiente.modificar(ambiente);    
//         if(resultado == 1)
//            System.out.println("Se ha MODIFICADO con exito");
//        else
//            System.out.println("Ha ocurrido un error");
            
         
         
         
         
     }
 
}
