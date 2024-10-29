package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface UsuarioDAO {
    int insertar(Usuario usuario);
    int modificar(Usuario usuario);
    int eliminar(int idUsuario);
    ArrayList<Usuario> listarTodos();
    Usuario obtenerPorId(int idUsuario);
    Usuario ExisteUsuario(String username, String contrasenha);
    Usuario VerificarUsuario(String username);
}
