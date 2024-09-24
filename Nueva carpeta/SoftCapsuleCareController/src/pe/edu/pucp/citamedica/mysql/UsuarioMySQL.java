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
    private PreparedStatement pstPersona;
    private PreparedStatement pstUsuario;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;

    @Override
    public int insertar(Usuario usuario) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero) values(?,?,?,?,?,?,?)";
            pstPersona = con.prepareStatement(sql);
            pstPersona.setString(1, usuario.getNombre());
            pstPersona.setString(2, usuario.getApellido());
            pstPersona.setString(3, usuario.getCorreoElectronico());
            pstPersona.setInt(4, usuario.getNumTelefono());
            pstPersona.setString(5, usuario.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(usuario.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.setString(7, String.valueOf(usuario.getGenero()));
            pstPersona.executeUpdate();
            
            sql = "SELECT @@LAST_INSERT_ID AS id";
            pstPersona = con.prepareStatement(sql);
            rs = pstPersona.executeQuery();
            int idPersona=0;
            if (rs.next()) {
                idPersona = rs.getInt("id");//Obtenemos el ID generado
            }
            
            sql = "INSERT INTO Usuario(username,contrasenha,idPersona) "
                    + "values(?,?,?)";
            pstUsuario = con.prepareStatement(sql);
            pstUsuario.setInt(1, usuario.getUsername());
            pstUsuario.setString(2, usuario.getContrasenha());
            pstUsuario.setInt(3, idPersona);
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
