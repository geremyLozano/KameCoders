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
}
