package pe.edu.pucp.capsuleCare.users.dao;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface UsuarioDAO {
    int insertar(Usuario usuario);
    int modificar(Usuario usuario);
    int eliminar(int idUsuario);
    ArrayList<Usuario> listarTodos();
    Usuario obtenerPorId(int idUsuario);
    Usuario ExisteUsuario(String username, String contrasenha);
    Usuario VerificarUsuario(String username);
    Usuario ValidarReset(String username, String correo);
    List<String>obtenerRoles(int idPersona);

    List<Usuario> listarActivoNoActivo(int valor);
    List<Usuario> listar(String filtro);
    ArrayList<Usuario> listarTodos1();
}
