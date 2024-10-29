/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AdministradorMySQL;

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
}
