package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class UsuarioMySQL implements UsuarioDAO{
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;

    @Override
    public int insertar(Usuario usuario) {
        int resultado = 0;
//        try {
//            con = DBManager.getInstance().getConnection();
//            sql = "INSERT into Usuario(username,contrasenha,idpersona) values(?,?,?)";
//            pst = con.prepareStatement(sql);
//            pst.setString(1, usuario.getUsername());
//            pst.setString(2, usuario.getContrasenha());
//            pst.setInt(3,usuario.getDatosPersonales().getIdPersona());
//            resultado = pst.executeUpdate();
//        } catch (SQLException e) {
//            System.out.print(e.getMessage());
//        }
        return resultado;
    }

    @Override
    public int modificar(Usuario usuario) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL UsuarioModificar(?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, usuario.getIdUsuario());
            cst.setString(2, usuario.getContrasenha());
            resultado = cst.executeUpdate();
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int eliminar(int idUsuario) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call USUARIO_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idUsuario);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public ArrayList<Usuario> listarTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT * FROM Usuario";
            rs = st.executeQuery(sql);
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha"));
                int idPersona = rs.getInt("idpersona");
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return usuarios;
    }

    @Override
    public Usuario obtenerPorId(int idUsuario) {
       Usuario usuario = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT * FROM Usuario WHERE idUsuario = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idUsuario);
            rs = pst.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha"));
                int idPersona = rs.getInt("idpersona");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return usuario;
    }
}
