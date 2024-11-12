/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.AseguradoraDAO;
import pe.edu.pucp.citamedica.model.usuario.Aseguradora;
import pe.edu.pucp.citamedica.mysql.AseguradoraMySQL;

/**
 *
 * @author Diegihno
 */
@WebService(serviceName = "AseguradoraWS")
public class AseguradoraWS {
    public AseguradoraDAO aseguradoraDAO;
    
    public AseguradoraWS(){
        aseguradoraDAO = new AseguradoraMySQL();
    }
    
    @WebMethod(operationName = "insertarAseguradora")
    public int insertarAseguradora(@WebParam(name = "aseguradora") Aseguradora aseguradora) {
        return aseguradoraDAO.insertar(aseguradora);
    }
    
    @WebMethod(operationName = "modificarAseguradora")
    public int modificarAseguradora(@WebParam(name = "aseguradora") Aseguradora aseguradora) {
        return aseguradoraDAO.modificar(aseguradora);
    }
    
    @WebMethod(operationName = "eliminarAseguradora")
    public int eliminarAseguradora(@WebParam(name = "aseguradora") int idAseguradora) {
        return aseguradoraDAO.eliminar(idAseguradora);
    }
    
    @WebMethod(operationName = "listarAseguradoras")
    public ArrayList<Aseguradora> listarAseguradoras() {
        return aseguradoraDAO.listarTodos();
    }
    
    @WebMethod(operationName = "obtenerAseguradoraPorId")
    public Aseguradora obtenerAseguradoraPorId(@WebParam(name = "aseguradora") int idAseguradora) {
        return aseguradoraDAO.obtenerPorId(idAseguradora);
    }
    
}
