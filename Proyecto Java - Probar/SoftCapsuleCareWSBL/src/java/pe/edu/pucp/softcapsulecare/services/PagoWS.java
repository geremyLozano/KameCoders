/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;
import pe.edu.pucp.citamedica.dao.PagoDAO;
import pe.edu.pucp.citamedica.mysql.PagoMySQL;
/**
 *
 * @author User
 */
@WebService(serviceName = "PagoWS")
public class PagoWS {
    private PagoDAO pagoDAO;
    /**
     * This is a sample web service operation
     */
    public PagoWS(){
        pagoDAO = new PagoMySQL();
    }
    
    @WebMethod(operationName = "insertarPago")
    public int insertarPago(@WebParam(name = "pago") Pago pago) {
        int resultado  = pagoDAO.insertar(pago);
        return resultado;
    }
}
