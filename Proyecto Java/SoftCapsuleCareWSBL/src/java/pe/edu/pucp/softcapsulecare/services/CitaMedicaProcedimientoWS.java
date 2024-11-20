package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.CitaProcedimientoDAO;
import pe.edu.pucp.citamedica.model.consultas.CitaMedicaProcedimiento;
import pe.edu.pucp.citamedica.mysql.CitaProcedimientoMySQL;

/**
 * Servicio Web para gestionar la relación entre Citas Médicas y Procedimientos.
 */
@WebService(serviceName = "CitaMedicaProcedimientoWS")
public class CitaMedicaProcedimientoWS {

    private CitaProcedimientoDAO daoCitaProcedimiento;

    /**
     * Inserta un nuevo registro en la tabla CitaMedica_has_Procedimiento.
     */
    @WebMethod(operationName = "insertarCitaMedicaProcedimiento")
    public int insertarCitaMedicaProcedimiento(@WebParam(name = "citaMedicaProcedimiento") CitaMedicaProcedimiento cmp) {
        int resultado = 0;
        try {
            // Imprime los valores recibidos para depuración
            System.out.println("ID Cita Médica: " + cmp.getIdCitaMedica());
            System.out.println("ID Procedimiento: " + cmp.getIdProcedimiento());
            System.out.println("Asistencia: " + cmp.getAsistencia());
            System.out.println("Observaciones: " + cmp.getObservaciones());
            System.out.println("Fecha Resultado: " + cmp.getFechaResultado());
            System.out.println("Activo: " + cmp.getActivo());

            daoCitaProcedimiento = new CitaProcedimientoMySQL();
            resultado = daoCitaProcedimiento.insertar(cmp);
        } catch (Exception ex) {
            System.out.println("Error en insertarCitaMedicaProcedimiento: " + ex.getMessage());
        }
        return resultado;
    }


    /**
     * Modifica un registro existente en la tabla CitaMedica_has_Procedimiento.
     */
    @WebMethod(operationName = "modificarCitaMedicaProcedimiento")
    public int modificarCitaMedicaProcedimiento(@WebParam(name = "citaMedicaProcedimiento") CitaMedicaProcedimiento cmp) {
        int resultado = 0;
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL();
            resultado = daoCitaProcedimiento.modificar(cmp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    /**
     * Realiza una eliminación lógica de un registro en la tabla CitaMedica_has_Procedimiento.
     */
    @WebMethod(operationName = "eliminarCitaMedicaProcedimiento")
    public int eliminarCitaMedicaProcedimiento(
            @WebParam(name = "idCitaMedica") int idCitaMedica,
            @WebParam(name = "idProcedimiento") int idProcedimiento) {
        int resultado = 0;
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL();
            resultado = daoCitaProcedimiento.eliminar(idCitaMedica, idProcedimiento);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    /**
     * Lista todos los registros activos asociados a una cita médica.
     */
    @WebMethod(operationName = "listarPorCitaMedica")
    public ArrayList<CitaMedicaProcedimiento> listarPorCitaMedica(@WebParam(name = "idCitaMedica") int idCitaMedica) {
        ArrayList<CitaMedicaProcedimiento> lista = null;
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL();
            lista = daoCitaProcedimiento.listarPorCitaMedica(idCitaMedica);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene un registro específico por ID de cita médica y procedimiento.
     */
    @WebMethod(operationName = "obtenerCitaMedicaProcedimientoPorIds")
    public CitaMedicaProcedimiento obtenerCitaMedicaProcedimientoPorIds(
            @WebParam(name = "idCitaMedica") int idCitaMedica,
            @WebParam(name = "idProcedimiento") int idProcedimiento) {
        CitaMedicaProcedimiento cmp = null;
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL();
            cmp = daoCitaProcedimiento.obtenerPorIds(idCitaMedica, idProcedimiento);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return cmp;
    }
    
    @WebMethod(operationName = "listarTodosCitaMedicaProcedimiento")
    public ArrayList<CitaMedicaProcedimiento> listarTodosCitaMedicaProcedimiento() {
        ArrayList<CitaMedicaProcedimiento> lista = null;
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL();
            lista = daoCitaProcedimiento.listarTodos();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    @WebMethod(operationName = "listarFiltrado")
    public ArrayList<CitaMedicaProcedimiento> listarFiltrado(
            @WebParam(name = "idCitaMedica") int idCitaMedica,
            @WebParam(name = "idProcedimiento") int idProcedimiento,
            @WebParam(name = "estadoPago") int estadoPago) {
        ArrayList<CitaMedicaProcedimiento> lista = new ArrayList<>();
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL(); // Instanciar el DAO
            lista = daoCitaProcedimiento.listarFiltrado(idCitaMedica, idProcedimiento, estadoPago);
        } catch (Exception ex) {
            System.out.println("Error en listarFiltrado: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }


}
