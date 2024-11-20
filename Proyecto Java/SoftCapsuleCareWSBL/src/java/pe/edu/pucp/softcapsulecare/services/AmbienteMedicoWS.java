/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.capsuleCare.users.dao.AmbienteMedicoDAO;
import pe.edu.pucp.capsuleCare.users.mysql.AmbienteMedicoMySQL;

import pe.edu.pucp.citamedica.model.clinica.AmbienteMedico;


/**
 *
 * @author Usuario
 */
@WebService(serviceName = "AmbienteMedicoWS")
public class AmbienteMedicoWS {

     private AmbienteMedicoDAO daoAmbienteMedico;
     
     
     
      @WebMethod(operationName = "insertarAmbienteMedico")
    public int insertarAmbienteMedico( AmbienteMedico ambiente) {
        
        
        
        int resultado =0;

          
        try{
            daoAmbienteMedico = new AmbienteMedicoMySQL();
            resultado = daoAmbienteMedico.insertar(ambiente);
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return resultado;
        

        
    }
    
    
    
     @WebMethod(operationName = "modificarAmbienteMedico")
    public int modificarAmbienteMedico( AmbienteMedico ambiente) {
        
             
        int resultado =0;
     
        try{
            daoAmbienteMedico = new AmbienteMedicoMySQL();
            resultado = daoAmbienteMedico.modificar(ambiente);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return resultado;
    }
    
    
    
    
     @WebMethod(operationName = "listarTodosAmbientes")
    public ArrayList<AmbienteMedico> listarTodosAmbientes() {
        
        ArrayList<AmbienteMedico> ambientesMedicos = null;

        try{
            
            daoAmbienteMedico = new AmbienteMedicoMySQL();
            ambientesMedicos = daoAmbienteMedico.listarTodos();
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return ambientesMedicos;
    }
    
    
      @WebMethod(operationName = "obtenerAmbienteMedicoPorId")
    public AmbienteMedico obtenerAmbienteMedicoPorId(@WebParam(name = "idAmbienteMedico") int idAmbienteMedico) {
        
        
        AmbienteMedico ambienteMedico =null;
        
        try{
            
            daoAmbienteMedico= new AmbienteMedicoMySQL();
            
            ambienteMedico=daoAmbienteMedico.obtenerPorId(idAmbienteMedico);
            
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        
        
        return ambienteMedico;
    }
    
      @WebMethod(operationName = "cambiarEstadoAmbienteMedico")
    public int cambiarEstadoAmbienteMedico( int idAmbienteMedico) {
        
        
        daoAmbienteMedico = new AmbienteMedicoMySQL();
        
        int resultado = daoAmbienteMedico.cambiarEstadoAmbiente(idAmbienteMedico);
        
        
        return resultado;
    }
    
    
    
    
    
    
    
    
    
}
