/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;



import pe.edu.pucp.citamedica.dao.HistorialMedicoDAO;
import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.citamedica.mysql.HistorialMedicoMySQL;





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
    
    
    
}
