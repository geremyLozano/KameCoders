/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.capsuleCare.medical.dao.HistorialMedicoDAO;
import pe.edu.pucp.capsuleCare.medical.mysql.HistorialMedicoMySQL;




import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.citamedica.model.dto.HistorialMedicoDto;






/**
 *
 * @author Usuario
 */
@WebService(serviceName = "HistorialMedicoWS")
public class HistorialMedicoWS {
    
    

    private HistorialMedicoDAO daoHistorial;
    
    @WebMethod(operationName = "obtenerHistorialMedicoPorId")
    public HistorialMedico obtenerHistorialMedicoPorId(@WebParam(name = "idHistorialMedico") int idHistorialMedico) {
        
        
        HistorialMedico historial =null;
        
        try{
            
            daoHistorial= new HistorialMedicoMySQL();
            
            historial=daoHistorial.obtenerPorId(idHistorialMedico);
            
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        
        
        return historial;
    }
    
    
    
    
    @WebMethod(operationName = "listarTodosPorCampImp")
    public ArrayList<HistorialMedicoDto> listarTodosPorCampImp() {
        ArrayList<HistorialMedicoDto> historial = null;

        try{
            daoHistorial = new HistorialMedicoMySQL();
            historial = daoHistorial.listarTodosPorCampImp();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return historial;
    }
    
    
    

    @WebMethod(operationName = "cambiarEstadoHistorialPorId")
    public int cambiarEstadoHistorialPorId( int idHistorialMedico) {
        
        
        daoHistorial = new HistorialMedicoMySQL();
        
        int resultado = daoHistorial.cambiarEstadoHistorial(idHistorialMedico);
        
        
        return resultado;
    }
    
    
    
    @WebMethod(operationName = "modificarHistorialMedico")
    public int modificarHistorialMedico( HistorialMedico historial) {
        
             
        int resultado =0;
     
        try{
            daoHistorial = new HistorialMedicoMySQL();
            resultado = daoHistorial.modificar(historial);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return resultado;
    }
    
    
    
    @WebMethod(operationName = "listarTodosPorDniNombreApellido")
    public ArrayList<HistorialMedicoDto> listarTodosPorDniNombreApellido(String patron) {
        
        
        ArrayList<HistorialMedicoDto> historial = null;

        try{
            daoHistorial = new HistorialMedicoMySQL();
            historial = daoHistorial.listarTodosPorDniNombreApellido(patron);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return historial;
    }
    
    @WebMethod(operationName = "obtenerHistorialMedicoPorIdPersona")
    public HistorialMedico obtenerHistorialMedicoPorIdPersona(@WebParam(name = "idPersona") int idPersona) {
        
        
        HistorialMedico historial =null;
        
        try{
            
            daoHistorial= new HistorialMedicoMySQL();
            
            historial=daoHistorial.obtenerPorPaciente(idPersona);
            
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        
        
        return historial;
    }
    
    
}
