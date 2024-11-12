package pe.edu.pucp.citamedica.main;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.UsuarioMySQL;


public class Principal_6 {
    public static void main(String[] args) {
        UsuarioDAO usu = new UsuarioMySQL();
//        Usuario u = new Usuario();
//        u.setIdUsuario(2);
//        u.setContrasenha("cincoydiez");
//        System.out.println(usu.modificar(u)>0 ? "Usuario modificado":"Error al modificar");
//        System.out.println(usu.eliminar(3)>0 ? "Usuario eliminado":"Error al eliminar");
//        ArrayList<Usuario>us=usu.listarTodos();
//        for(Usuario u:us){
//            System.out.println(u.toString());
//        }
        System.out.println(usu.obtenerPorId(1).toString());
    }
}
