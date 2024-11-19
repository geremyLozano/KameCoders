package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface AdministradorDAO{
    
    int insertar(Administrador administrador, Usuario usuario);
    int modificar(Administrador administrador);
    int eliminar(int idAdministrador);
    ArrayList<Administrador> listarTodos();
    Administrador obtenerPorId(int idAdministrador);
    
    public Administrador obtenerPorId1(int idAdmin);
    int insertar1(Administrador admin);
    List<Administrador> listar(String filtro);
    int modificar_v2(Administrador administrador);
}
