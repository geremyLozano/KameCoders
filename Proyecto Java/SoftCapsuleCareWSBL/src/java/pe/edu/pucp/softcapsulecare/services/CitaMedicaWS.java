/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
/**
 *
 * @author diego
 */
@WebService(serviceName = "CitaMedicaWS")
public class CitaMedicaWS {
    private CitaMedicaDAO citaMedicaDAO;
    
    public CitaMedicaWS(){
        citaMedicaDAO = new CitaMedicaMySQL();
    }
    
    @WebMethod(operationName = "insertarCitaMedica")
    public int insertarCitaMedica(@WebParam(name = "cita") CitaMedica citaMedica) {
        return citaMedicaDAO.insertar(citaMedica);
    }
    
    @WebMethod(operationName = "modificarCitaMedica")
    public int modificarCitaMedica(@WebParam(name = "cita") CitaMedica citaMedica) {
        return citaMedicaDAO.modificar(citaMedica);
    }
    
    @WebMethod(operationName = "eliminarCitaMedica")
    public int eliminarCitaMedica(@WebParam(name = "cita") int idCitaMedica) {
        return citaMedicaDAO.eliminar(idCitaMedica);
    }

    @WebMethod(operationName = "listar")
    public ArrayList<CitaMedica> listar() {
        return citaMedicaDAO.listarTodos();
    }
    
    @WebMethod(operationName = "listarPorIdPaciente")
    public ArrayList<CitaMedica> listarPorIdPaciente(@WebParam(name = "idPaciente") int idPaciente) {
        return citaMedicaDAO.listarPorPaciente(idPaciente);
    }
    
}
