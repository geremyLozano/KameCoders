/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.capsuleCare.medical.dao.PagoDAO;
import pe.edu.pucp.capsuleCare.medical.mysql.PagoMySQL;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;


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
    @WebMethod(operationName = "listarTodosPagos")
    public java.util.List<Pago> listarTodosPagos() {
        java.util.List<Pago> listaPagos = new java.util.ArrayList<>();
        try {
            listaPagos = pagoDAO.listarTodos();

            // Imprimir los valores obtenidos
            System.out.println("Pagos obtenidos:");
            for (Pago pago : listaPagos) {
                System.out.println("ID Pago: " + pago.getIdPago());
                System.out.println("Descuento por Seguro: " + pago.getDescuentoPorSeguro());
                System.out.println("Monto Parcial: " + pago.getMontoParcial());
                System.out.println("Monto Total: " + pago.getMontoTotal());
                System.out.println("Fecha Pago: " + pago.getFechaPago());
                System.out.println("Concepto: " + pago.getConcepto());
                System.out.println("ID Paciente: " + pago.getIdPaciente());
                System.out.println("--------------");
            }
        } catch (Exception ex) {
            System.out.println("Error al listar los pagos: " + ex.getMessage());
        }
        return listaPagos;
    }


    
    
}
