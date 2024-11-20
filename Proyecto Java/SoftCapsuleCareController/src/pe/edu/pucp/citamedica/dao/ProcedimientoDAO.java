/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;

/**
 *
 * @author Usuario
 */
public interface ProcedimientoDAO {
    
    
    int insertar(Procedimiento procedimiento,int idAmbienteMedico);
    int modificar(Procedimiento procedimiento);
    int eliminar(int idProcedimiento);
    ArrayList<Procedimiento> listarTodos();
    Procedimiento obtenerPorId(int idProcedimiento);
    ArrayList<Procedimiento> listarPorPaciente(int idPaciente);
}
