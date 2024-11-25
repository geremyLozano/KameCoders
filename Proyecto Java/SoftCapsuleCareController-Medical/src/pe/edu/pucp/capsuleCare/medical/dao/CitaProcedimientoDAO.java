/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.capsuleCare.medical.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.CitaMedicaProcedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;


public interface CitaProcedimientoDAO {
    int insertar(CitaMedicaProcedimiento cmp);
   
    public int modificar(int idCitaMedica, int idProcedimiento,
            int idPago, boolean asistencia, 
            String observaciones, Date fechaResultado,
            Date fechaRealizacion, String horaRealizacion, boolean activo);
    int eliminar(int idCitaMedica, int idProcedimiento);
    ArrayList<CitaMedicaProcedimiento> listarPorCitaMedica(int idCitaMedica);
    CitaMedicaProcedimiento obtenerPorIds(int idCitaMedica, int idProcedimiento);
    ArrayList<CitaMedicaProcedimiento> listarTodos(); 
    
    ArrayList<CitaMedicaProcedimiento> listarFiltrado(
            Integer idCitaMedica,
            Integer idProcedimiento,
            Integer estadoPago,
            Date fechaInicio,
            Date fechaFin,
            String horaInicio,
            String horaFin);
    public ArrayList<String> listarHorasDisponibles(Date fecha);
    public ArrayList<CitaMedicaProcedimiento> listarFiltrado2(
            String dniPaciente, Integer idCitaMedica, Integer idProcedimiento,
            Integer estadoPago, Date fechaInicio, Date fechaFin,
            String horaInicio, String horaFin
    );

}
