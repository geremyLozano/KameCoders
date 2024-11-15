/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.AmbienteMedicoDAO;
import pe.edu.pucp.citamedica.model.clinica.AmbienteMedico;
import pe.edu.pucp.citamedica.mysql.AmbienteMedicoMySQL;

/**
 *
 * @author Usuario
 */
@WebService(serviceName = "AmbienteMedicoWS")
public class AmbienteMedicoWS {

     private AmbienteMedicoDAO daoAmbienteMedico;
    
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
    
    
    
    
    
    
}
