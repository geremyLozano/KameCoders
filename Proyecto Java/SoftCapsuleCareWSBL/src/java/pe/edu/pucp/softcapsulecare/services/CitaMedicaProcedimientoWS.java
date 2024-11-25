package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.capsuleCare.medical.dao.CitaProcedimientoDAO;
import pe.edu.pucp.capsuleCare.medical.mysql.CitaProcedimientoMySQL;
import pe.edu.pucp.citamedica.model.consultas.CitaMedicaProcedimiento;

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
            // Convertir fechaRealizacion (string esperado) a java.sql.Date
//            if (cmp.getFechaRealizacion() != null) {
//                try {
//                    // Suponiendo que fechaRealizacion es inicialmente un String
//                    //cmp.setFechaRealizacion(java.sql.Date.valueOf(cmp.getFechaRealizacion().toString()));
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Error al convertir fechaRealizacion: " + cmp.getFechaRealizacion());
//                    cmp.setFechaRealizacion(null); // Dejar null si hay un error
//                }
//            }

            // Log para depuración
            if (cmp.getFechaRealizacion() != null) {
                // Convertir de java.util.Date a java.sql.Date
                cmp.setFechaRealizacion(new java.sql.Date(cmp.getFechaRealizacion().getTime()));
            }
            
            System.out.println("ID Cita Médica: " + cmp.getIdCitaMedica());
            System.out.println("ID Procedimiento: " + cmp.getIdProcedimiento());
            System.out.println("Fecha Realización (convertida): " + cmp.getFechaRealizacion());
            System.out.println("Hora Realización: " + cmp.getHoraRealizacion());

            // Llamar al DAO para insertar
            daoCitaProcedimiento = new CitaProcedimientoMySQL();
            resultado = daoCitaProcedimiento.insertar(cmp);
        } catch (Exception ex) {
            System.out.println("Error en insertarCitaMedicaProcedimiento: " + ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }





    /**
     * Modifica un registro existente en la tabla CitaMedica_has_Procedimiento.
     */
    /**
 * Modifica un registro existente en la tabla CitaMedica_has_Procedimiento.
 */
    @WebMethod(operationName = "modificarCitaMedicaProcedimiento")
    public int modificarCitaMedicaProcedimiento(
            @WebParam(name = "idCitaMedica") int idCitaMedica,
            @WebParam(name = "idProcedimiento") int idProcedimiento,
            @WebParam(name = "idPago") int idPago,
            @WebParam(name = "asistencia") boolean asistencia,
            @WebParam(name = "observaciones") String observaciones,
            @WebParam(name = "fechaResultado") Date fechaResultado,
            @WebParam(name = "fechaRealizacion") Date fechaRealizacion, // Nuevo parámetro
            @WebParam(name = "horaRealizacion") String horaRealizacion, // Nuevo parámetro
            @WebParam(name = "activo") boolean activo) {
        int resultado = 0;
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL();

            // Convertir las fechas a java.sql.Date si no son nulas
            java.sql.Date sqlFechaResultado = fechaResultado != null ? new java.sql.Date(fechaResultado.getTime()) : null;
            java.sql.Date sqlFechaRealizacion = fechaRealizacion != null ? new java.sql.Date(fechaRealizacion.getTime()) : null;

            // Llamar al método del DAO que usa el nuevo procedimiento almacenado
            resultado = daoCitaProcedimiento.modificar(
                    idCitaMedica,
                    idProcedimiento,
                    idPago,
                    asistencia,
                    observaciones,
                    sqlFechaResultado,
                    sqlFechaRealizacion,
                    horaRealizacion,
                    activo);
        } catch (Exception ex) {
            System.out.println("Error en modificarCitaMedicaProcedimiento: " + ex.getMessage());
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
            @WebParam(name = "idCitaMedica") Integer idCitaMedica,
            @WebParam(name = "idProcedimiento") Integer idProcedimiento,
            @WebParam(name = "estadoPago") Integer estadoPago,
            @WebParam(name = "fechaInicio") Date fechaInicio,
            @WebParam(name = "fechaFin") Date fechaFin,
            @WebParam(name = "horaInicio") String horaInicio,
            @WebParam(name = "horaFin") String horaFin) {
        ArrayList<CitaMedicaProcedimiento> lista = new ArrayList<>();
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL(); // Instanciar el DAO

            // Convertir java.util.Date a java.sql.Date si no son nulas
            java.sql.Date sqlFechaInicio = (fechaInicio != null) ? new java.sql.Date(fechaInicio.getTime()) : null;
            java.sql.Date sqlFechaFin = (fechaFin != null) ? new java.sql.Date(fechaFin.getTime()) : null;

            // Manejar parámetros opcionales: Enviar `-1` o `null` según corresponda
            lista = daoCitaProcedimiento.listarFiltrado(
                    idCitaMedica != null ? idCitaMedica : -1,
                    idProcedimiento != null ? idProcedimiento : -1,
                    estadoPago != null ? estadoPago : -1,
                    sqlFechaInicio,
                    sqlFechaFin,
                    horaInicio != null && !horaInicio.isEmpty() ? horaInicio : null,
                    horaFin != null && !horaFin.isEmpty() ? horaFin : null
            );

            // Depuración: imprimir los resultados obtenidos
            for (CitaMedicaProcedimiento cmp : lista) {
                System.out.println("ID Cita Médica: " + cmp.getIdCitaMedica());
                System.out.println("ID Procedimiento: " + cmp.getIdProcedimiento());
                System.out.println("Fecha Realización: " + cmp.getFechaRealizacion());
                System.out.println("Hora Realización: " + cmp.getHoraRealizacion());
                System.out.println("Observaciones: " + cmp.getObservaciones());
                System.out.println("Activo: " + cmp.getActivo());
            }

        } catch (Exception ex) {
            System.out.println("Error en listarFiltrado: " + ex.getMessage());
            ex.printStackTrace();
        }
        return lista;
    }
    
    @WebMethod(operationName = "listarHorasDisponibles")
    public ArrayList<String> listarHorasDisponibles(@WebParam(name = "fecha") String fecha) {
        ArrayList<String> horasDisponibles = new ArrayList<>();
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL();

            // Convertir el String de fecha al formato java.sql.Date
            java.sql.Date sqlFecha = java.sql.Date.valueOf(fecha); // El formato debe ser "yyyy-MM-dd"

            // Llamar al método del DAO
            horasDisponibles = daoCitaProcedimiento.listarHorasDisponibles(sqlFecha);
        } catch (IllegalArgumentException e) {
            System.out.println("Error al convertir la fecha: Formato incorrecto. Debe ser 'yyyy-MM-dd'.");
            e.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error en listarHorasDisponibles: " + ex.getMessage());
            ex.printStackTrace();
        }
        return horasDisponibles;
    }

    @WebMethod(operationName = "listarFiltrado2")
    public ArrayList<CitaMedicaProcedimiento> listarFiltrado2(
            @WebParam(name = "dniPaciente") String dniPaciente,
            @WebParam(name = "idCitaMedica") Integer idCitaMedica,
            @WebParam(name = "idProcedimiento") Integer idProcedimiento,
            @WebParam(name = "estadoPago") Integer estadoPago,
            @WebParam(name = "fechaInicio") Date fechaInicio, // Utiliza java.util.Date
            @WebParam(name = "fechaFin") Date fechaFin, // Utiliza java.util.Date
            @WebParam(name = "horaInicio") String horaInicio,
            @WebParam(name = "horaFin") String horaFin
    ) {
        // Imprimir los valores recibidos
        System.out.println("Valores recibidos en listarFiltrado2:");
        System.out.println("DNI Paciente: " + dniPaciente);
        System.out.println("ID Cita Médica: " + (idCitaMedica != null ? idCitaMedica : "No especificado (-1)"));
        System.out.println("ID Procedimiento: " + (idProcedimiento != null ? idProcedimiento : "No especificado (-1)"));
        System.out.println("Estado Pago: " + (estadoPago != null ? estadoPago : "No especificado (-1)"));
        System.out.println("Fecha Inicio: " + (fechaInicio != null ? fechaInicio : "No especificada"));
        System.out.println("Fecha Fin: " + (fechaFin != null ? fechaFin : "No especificada"));
        System.out.println("Hora Inicio: " + (horaInicio != null ? horaInicio : "No especificada"));
        System.out.println("Hora Fin: " + (horaFin != null ? horaFin : "No especificada"));

        ArrayList<CitaMedicaProcedimiento> procedimientos = new ArrayList<>();
        try {
            daoCitaProcedimiento = new CitaProcedimientoMySQL(); 
            // Convertir fechas java.util.Date a java.sql.Date
            java.sql.Date sqlFechaInicio = (fechaInicio != null) ? new java.sql.Date(fechaInicio.getTime()) : null;
            java.sql.Date sqlFechaFin = (fechaFin != null) ? new java.sql.Date(fechaFin.getTime()) : null;

            // Imprimir valores convertidos
            System.out.println("Fecha Inicio (SQL): " + (sqlFechaInicio != null ? sqlFechaInicio : "NULL"));
            System.out.println("Fecha Fin (SQL): " + (sqlFechaFin != null ? sqlFechaFin : "NULL"));

            // Llamar al método del DAO
            procedimientos = daoCitaProcedimiento.listarFiltrado2(
                    dniPaciente,
                    idCitaMedica != null ? idCitaMedica : -1,
                    idProcedimiento != null ? idProcedimiento : -1,
                    estadoPago != null ? estadoPago : -1,
                    sqlFechaInicio,
                    sqlFechaFin,
                    horaInicio,
                    horaFin
            );

            // Imprimir resultados obtenidos
            System.out.println("Resultados obtenidos:");
            for (CitaMedicaProcedimiento cmp : procedimientos) {
                System.out.println("ID Cita Médica: " + cmp.getIdCitaMedica());
                System.out.println("ID Procedimiento: " + cmp.getIdProcedimiento());
                System.out.println("Estado Pago: " + (cmp.getIdPago() != null ? cmp.getIdPago() : "Sin pago registrado"));
                System.out.println("Fecha Realización: " + (cmp.getFechaRealizacion() != null ? cmp.getFechaRealizacion() : "No registrada"));
                System.out.println("Hora Realización: " + (cmp.getHoraRealizacion() != null ? cmp.getHoraRealizacion() : "No registrada"));
                
                System.out.println("-------------------------");
            }
        } catch (Exception ex) {
            System.out.println("Error en listarFiltrado2: " + ex.getMessage());
            ex.printStackTrace();
        }
        return procedimientos;
    }

        

}
