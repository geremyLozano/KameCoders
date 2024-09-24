package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.clinica.model.Administrador;

public interface AdministradorDAO {
    
    int insertar(Administrador administrador);
    int modificar(Administrador administrador);
    int eliminar(int idAdministrador);
    ArrayList<Administrador> listarTodos();
    Administrador obtenerPorId(int idAdministrador);
    
}
