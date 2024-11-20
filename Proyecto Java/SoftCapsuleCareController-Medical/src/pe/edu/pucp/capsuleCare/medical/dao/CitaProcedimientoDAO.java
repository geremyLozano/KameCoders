/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.capsuleCare.medical.dao;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.CitaMedicaProcedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;


public interface CitaProcedimientoDAO {
    int insertar(CitaMedicaProcedimiento cmp);
   
    int modificar(int idCitaMedica, int idProcedimiento, 
            int idPago, boolean asistencia, String observaciones,
            Date fechaResultado, boolean activo);
    int eliminar(int idCitaMedica, int idProcedimiento);
    ArrayList<CitaMedicaProcedimiento> listarPorCitaMedica(int idCitaMedica);
    CitaMedicaProcedimiento obtenerPorIds(int idCitaMedica, int idProcedimiento);
    ArrayList<CitaMedicaProcedimiento> listarTodos(); 
    ArrayList<CitaMedicaProcedimiento> listarFiltrado(int idCitaMedica, int idProcedimiento, int estadoPago);
}
