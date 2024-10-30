package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.UsuarioMySQL;

@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {
    private UsuarioDAO usuDAO;
    
    public UsuarioWS(){
        usuDAO = new UsuarioMySQL();
    }

    @WebMethod(operationName = "verificarUsuario")
    public Usuario verificarUsuario(@WebParam(name = "username") String username,
                                      @WebParam(name = "contrasenha") String contrasenha) {
        Usuario usuario = null;
        usuario = usuDAO.ExisteUsuario(username,contrasenha);
        return usuario;
        //#00b9e8
    }
    
    @WebMethod(operationName = "modificarUsuario")
    public int modificarUsuario(@WebParam(name = "username") Usuario usuario) {
        int resultado = usuDAO.modificar(usuario);
        return resultado;
        //#00b9e8
    }
    
    @WebMethod(operationName = "obtenerPorID")
    public Usuario obtenerPorID(@WebParam(name = "username") int id) {
        Usuario usuario = null;
        usuario = usuDAO.obtenerPorId(id);
        return usuario;
    }
    
    @WebMethod(operationName = "validarUsuario")
    public Usuario validarUsuario(@WebParam(name = "username") String username) {
        Usuario usuario = null;
        usuario = usuDAO.VerificarUsuario(username);
        return usuario;
    }
}
