/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.CitaMedicaProcedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;


public interface CitaProcedimientoDAO {
    int insertar(CitaMedicaProcedimiento cmp);
    int modificar(CitaMedicaProcedimiento cmp);
    int eliminar(int idCitaMedica, int idProcedimiento);
    ArrayList<CitaMedicaProcedimiento> listarPorCitaMedica(int idCitaMedica);
    CitaMedicaProcedimiento obtenerPorIds(int idCitaMedica, int idProcedimiento);
    ArrayList<CitaMedicaProcedimiento> listarTodos(); 
    ArrayList<CitaMedicaProcedimiento> listarFiltrado(int idCitaMedica, int idProcedimiento, int estadoPago);
}
