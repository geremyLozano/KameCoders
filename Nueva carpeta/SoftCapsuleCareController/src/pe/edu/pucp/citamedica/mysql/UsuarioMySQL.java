package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.usuario.model.Usuario;
import pe.edu.pucp.dbmanager.config.DBManager;

public class UsuarioMySQL implements UsuarioDAO{
    
    private Connection con;
    private Statement st;
    private PreparedStatement pstUsuario;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;

    @Override
    public int insertar(Usuario usuario,int idPersona) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Usuario(username,contrasenha,idpersona) values(?,?,?)";
            pstUsuario = con.prepareStatement(sql);
            pstUsuario.setString(1, usuario.getUsername());
            pstUsuario.setString(2, usuario.getContrasenha());
            pstUsuario.setInt(3,idPersona);
            resultado = pstUsuario.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Usuario usuario) {
        int resultado = 0;
        sql = "UPDATE Usuario SET username = ?, contrasenha = ? "
               + " WHERE idUsuario = ?";

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pstUsuario = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pstUsuario.setString(1, usuario.getUsername());
            pstUsuario.setString(2, usuario.getContrasenha());
            pstUsuario.setInt(3, usuario.getIdUsuario());
            // Ejecutar la consulta de actualización
            resultado = pstUsuario.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Usuario modificado correctamente.");
            } else {
                System.out.println("No se encontró ningun usuario con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir la excepción si ocurre un error
        }

        return resultado;
    }

    @Override
    public int eliminar(int idUsuario) {
        int resultado = 0;
        sql = "DELETE FROM Usuario WHERE idUsuario = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstUsuario = con.prepareStatement(sql)) {

            pstUsuario.setInt(1, idUsuario);

            resultado = pstUsuario.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("La fila de la tabla Usuario se ha eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningun usuario con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public ArrayList<Usuario> listarTodos() {
        ArrayList<Usuario> listaUsuario = new ArrayList<>();
        sql = "SELECT * FROM Usuario";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstUsuario = con.prepareStatement(sql);
             ResultSet rs = pstUsuario.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                //OJO: por ahora se mostrara para evidenciar la funcionalidad
                usuario.setContrasenha(rs.getString("contrasenha"));
                //Añadir el objeto Usuario a la lista
                listaUsuario.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }
        return listaUsuario;  // Retornar la lista de usuariocion
    }

    @Override
    public Usuario obtenerPorId(int idUsuario) {
        Usuario usuario = null;
        sql = "SELECT * FROM Usuario WHERE idUsuario = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstUsuario = con.prepareStatement(sql)) {

            pstUsuario.setInt(1, idUsuario);
            rs = pstUsuario.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                //Ojo: se muestra la contraseña para evidenciar la funcionalidad
                usuario.setContrasenha(rs.getString("contrasenha"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
