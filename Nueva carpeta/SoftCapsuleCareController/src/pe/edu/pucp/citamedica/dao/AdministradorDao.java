/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.clinica.model.Administrador;

/**
 *
 * @author Usuario
 */
public interface AdministradorDao {
    
    int insertar(Administrador administrador);
    int modificar(Administrador administrador);
    int eliminar(int idAdministrador);
    ArrayList<Administrador> listarTodos();
    Administrador obtenerPorId(int idAdministrador);
    
    
}
