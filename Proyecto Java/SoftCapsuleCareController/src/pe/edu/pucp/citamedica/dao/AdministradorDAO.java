package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface AdministradorDAO{
    
    int insertar(Administrador administrador, Usuario usuario, Persona persona);
    int modificar(Administrador administrador);
    int eliminar(int idAdministrador);
    ArrayList<Administrador> listarTodos();
    Administrador obtenerPorId(int idAdministrador);
    
}
