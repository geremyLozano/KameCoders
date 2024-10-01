package pe.edu.pucp.citamedica.main;

import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.UsuarioMySQL;


public class Principal_6 {
    public static void main(String[] args) {
        UsuarioDAO usu = new UsuarioMySQL();
        Usuario u = new Usuario();
        u.setIdUsuario(2);
        u.setContrasenha("cincoydiez");
        System.out.println(usu.modificar(u)>0 ? "Usuario modificado":"Error al modificar");
    }
}
