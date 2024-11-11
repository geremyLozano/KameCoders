/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.ProcedimientoDAO;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;
import pe.edu.pucp.citamedica.mysql.ProcedimientoMySQL;


@WebService(serviceName = "ProcedimientoWS")
public class ProcedimientoWS {

     private ProcedimientoDAO procedimientoDAO;

    public ProcedimientoWS() {
        procedimientoDAO = new ProcedimientoMySQL();
    }

    // Método para insertar un nuevo procedimiento
    @WebMethod(operationName = "insertarProcedimiento")
    public int insertarProcedimiento(@WebParam(name = "procedimiento") Procedimiento procedimiento, 
                                     @WebParam(name = "idAmbienteMedico") int idAmbienteMedico)
    {
        System.out.println("Nombre Procedimiento: " + procedimiento.getNombre());
        System.out.println("Descripcion: " + procedimiento.getDescripcion());
        System.out.println("Requisitos previos: " + procedimiento.getRequisitosPrevios());
        System.out.println("Costo: " + procedimiento.getCosto());
        System.out.println("Tipo: " + procedimiento.getTipoProcedimiento());
        System.out.println("Id ambiente medico: " + idAmbienteMedico);
        return procedimientoDAO.insertar(procedimiento, idAmbienteMedico);
    }

    // Método para modificar un procedimiento existente
    @WebMethod(operationName = "modificarProcedimiento")
    public int modificarProcedimiento(@WebParam(name = "procedimiento") Procedimiento procedimiento) {
        return procedimientoDAO.modificar(procedimiento);
    }

    // Método para eliminar un procedimiento (eliminación lógica)
    @WebMethod(operationName = "eliminarProcedimiento")
    public int eliminarProcedimiento(@WebParam(name = "idProcedimiento") int idProcedimiento) {
        return procedimientoDAO.eliminar(idProcedimiento);
    }

    // Método para listar todos los procedimientos
    @WebMethod(operationName = "listarTodosProcedimientos")
    public ArrayList<Procedimiento> listarTodosProcedimientos() {
        return procedimientoDAO.listarTodos();
    }

    // Método para obtener un procedimiento por su ID
    @WebMethod(operationName = "obtenerProcedimientoPorId")
    public Procedimiento obtenerProcedimientoPorId(@WebParam(name = "idProcedimiento") int idProcedimiento) {
        return procedimientoDAO.obtenerPorId(idProcedimiento);
    }
}