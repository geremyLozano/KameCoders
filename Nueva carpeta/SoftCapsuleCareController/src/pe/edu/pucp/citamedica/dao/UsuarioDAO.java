package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.usuario.model.Usuario;

public interface UsuarioDAO {
    int insertar(Usuario usuario,int idPersona);
    int modificar(Usuario usuario);
    int eliminar(int idUsuario);
    ArrayList<Usuario> listarTodos();
    Usuario obtenerPorId(int idUsuario);
}
