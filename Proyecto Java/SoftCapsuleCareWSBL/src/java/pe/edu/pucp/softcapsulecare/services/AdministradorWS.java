/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.capsuleCare.users.dao.AdministradorDAO;
import pe.edu.pucp.capsuleCare.users.mysql.AdministradorMySQL;

import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.usuario.Usuario;


@WebService(serviceName = "AdministradorWS")
public class AdministradorWS {
    private AdministradorDAO adminDAO;
    
    public AdministradorWS(){
        adminDAO = new AdministradorMySQL();
    }
    
    @WebMethod(operationName = "insertarAdministrador")
    public int administradorInsertar(@WebParam(name = "admin") Administrador admin,
                                     @WebParam(name = "usuario") Usuario usuario) {
        int resultado = adminDAO.insertar(admin,usuario);
        return resultado;
    }



   //Insertado Geremy
    @WebMethod(operationName = "listarAdmin")
    public ArrayList<Administrador> listarAdmin() {
        ArrayList<Administrador> resultado = adminDAO.listarTodos();
        return resultado;
    }
    
    @WebMethod(operationName = "eliminarAdmin")
    public int adminEliminar(@WebParam(name = "admin") int idAdmin) {
        int resultado = adminDAO.eliminar(idAdmin);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarAdmin")
    public int adminModificar(@WebParam(name = "admin") Administrador admin) {
        int resultado = adminDAO.modificar(admin);
        return resultado;
    }
    
    
    
    @WebMethod(operationName = "obtenerPorIDAdmin1")
    public Administrador adminObtenerID1(@WebParam(name = "admin") int idAdmin) {
        Administrador resultado = adminDAO.obtenerPorId1(idAdmin);
        return resultado;
    }
    
    @WebMethod(operationName = "insertarAdmin1")
    public int adminInsertar1(@WebParam(name = "administrador") Administrador admin) {
        int resultado = adminDAO.insertar1(admin);
        return resultado;
    }
    
    @WebMethod(operationName = "listarAdminFiltro")
    public List<Administrador> listarAdminFiltro(String filtro) {
        List<Administrador> resultado = adminDAO.listar(filtro);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarAdmin_v2")
    public int modificarAdmin_V2(@WebParam(name = "admin") Administrador admin) {
        return adminDAO.modificar_v2(admin);
    }
}
