package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class UsuarioMySQL implements UsuarioDAO{
    private Connection con;
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call UsuarioEliminar(?)}";
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL UsuarioListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha"));
                usuario.setIdPersona(rs.getInt("idPersona"));
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL UsuarioListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idUsuario);
            rs = cst.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha"));
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setActivo(true);
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

    @Override
    public Usuario ExisteUsuario(String username, String contrasenha) {
        Usuario usuario = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL ExisteUsuario(?,?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, username);
            cst.setString(2, contrasenha);
            rs = cst.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha"));
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setActivo(true);
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

    @Override
    public Usuario VerificarUsuario(String username) {
        Usuario usuario = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL UsuarioValidar(?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, username);
            rs = cst.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha"));
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setActivo(true);
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

    @Override
    public Usuario ValidarReset(String username, String correo) {
        Usuario usuario = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL UsuarioReset(?,?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, username);
            cst.setString(2, correo);
            rs = cst.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha"));
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setActivo(true);
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

    @Override
    public List<String> obtenerRoles(int idPersona) {
        List<String> rolesActivos = new ArrayList<>();
        PreparedStatement statementMedico = null;
        PreparedStatement statementPaciente = null;
        PreparedStatement statementAuxiliar = null;
        PreparedStatement statementAdministrador = null;
        try {
            con = DBPoolManager.getInstance().getConnection();

            String queryMedico = "SELECT 'Medico' AS rol FROM Medico WHERE idMedico = ? AND activo = true";
            statementMedico = con.prepareStatement(queryMedico);
            statementMedico.setInt(1, idPersona);
            rs = statementMedico.executeQuery();
            if (rs.next()) {
                rolesActivos.add(rs.getString("rol"));
            }

            String queryPaciente = "SELECT 'Paciente' AS rol FROM Paciente WHERE idPaciente = ? AND activo = true";
            statementPaciente = con.prepareStatement(queryPaciente);
            statementPaciente.setInt(1, idPersona);
            rs = statementPaciente.executeQuery();
            if (rs.next()) {
                rolesActivos.add(rs.getString("rol"));
            }

            String queryAuxiliar = "SELECT 'Auxiliar' AS rol FROM Auxiliar WHERE idAuxiliar = ? AND activo = true";
            statementAuxiliar = con.prepareStatement(queryAuxiliar);
            statementAuxiliar.setInt(1, idPersona);
            rs = statementAuxiliar.executeQuery();
            if (rs.next()) {
                rolesActivos.add(rs.getString("rol"));
            }

            // Consultar en la tabla Administrador
            String queryAdministrador = "SELECT 'Administrador' AS rol FROM Administrador WHERE idAdministrador = ? AND activo = true";
            statementAdministrador = con.prepareStatement(queryAdministrador);
            statementAdministrador.setInt(1, idPersona);
            rs = statementAdministrador.executeQuery();
            if (rs.next()) {
                rolesActivos.add(rs.getString("rol"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos
            try {
                if (rs != null) rs.close();
                if (statementMedico != null) statementMedico.close();
                if (statementPaciente != null) statementPaciente.close();
                if (statementAuxiliar != null) statementAuxiliar.close();
                if (statementAdministrador != null) statementAdministrador.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rolesActivos;
    }
}
