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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int eliminar(int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Usuario> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario obtenerPorId(int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
