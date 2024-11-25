package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import pe.edu.pucp.capsuleCare.users.dao.UsuarioDAO;
import pe.edu.pucp.capsuleCare.users.mysql.UsuarioMySQL;

import pe.edu.pucp.citamedica.model.usuario.Usuario;


@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {
    private UsuarioDAO usuDAO;
    
    public UsuarioWS(){
        usuDAO = new UsuarioMySQL();
    }

    @WebMethod(operationName = "verificarUsuario")
    public Usuario verificarUsuario(@WebParam(name = "username") String username,
                                      @WebParam(name = "contrasenha") String contrasenha) {
        //#00b9e8
        return usuDAO.ExisteUsuario(username,contrasenha);
    }
    
    @WebMethod(operationName = "modificarUsuario")
    public int modificarUsuario(@WebParam(name = "username") Usuario usuario) {
        return usuDAO.modificar(usuario);
    }
    
    @WebMethod(operationName = "obtenerPorID")
    public Usuario obtenerPorID(@WebParam(name = "username") int id) {
        return usuDAO.obtenerPorId(id);
    }
    
    @WebMethod(operationName = "validarUsuario")
    public Usuario validarUsuario(@WebParam(name = "username") String username) {
        return usuDAO.VerificarUsuario(username);
    }
    
    @WebMethod(operationName = "validarReset")
    public Usuario validarReset(@WebParam(name = "username") String username,
                                      @WebParam(name = "correo") String correo) {
        return usuDAO.ValidarReset(username,correo);
    }
    
    @WebMethod(operationName = "obtenerRoles")
    public List<String> obtenerRoles(@WebParam(name = "idPersona") int idPersona) {
        List<String> roles = usuDAO.obtenerRoles(idPersona);
        return roles != null ? roles : Collections.emptyList();
    }


    @WebMethod(operationName = "listarUser")
    public ArrayList<Usuario> listarUser() {
        ArrayList<Usuario> resultado = usuDAO.listarTodos();
        return resultado;
    }
    @WebMethod(operationName = "listarActivoNoActivoUser")
    public List<Usuario> listarActivoNoActivoUser(int valor) {
        List<Usuario> resultado = usuDAO.listarActivoNoActivo(valor);
        return resultado;
    }
    @WebMethod(operationName = "listarFiltroUser")
    public List<Usuario> listarFiltroUser(String filtro)  {
        List<Usuario> resultado = usuDAO.listar(filtro);
        return resultado;
    }
    @WebMethod(operationName = "listarUser1")
    public ArrayList<Usuario> listarUser1() {
        ArrayList<Usuario> resultado = usuDAO.listarTodos1();
        return resultado;
    }
    @WebMethod(operationName = "eliminarUsuario")
    public int usuarioEliminar(@WebParam(name = "usuario") int idUsuario) {
        int resultado = usuDAO.eliminar(idUsuario);
        return resultado;
    }
}
