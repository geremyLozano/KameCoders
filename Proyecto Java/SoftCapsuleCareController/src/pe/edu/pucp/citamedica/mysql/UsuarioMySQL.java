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
import pe.edu.pucp.seguridad.PasswordHash;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class UsuarioMySQL implements UsuarioDAO {

    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;

    @Override
    public int insertar(Usuario usuario) {
        int resultado = 0;
        // El método insertar está comentado, se mantiene así
        return resultado;
    }

    @Override
    public int modificar(Usuario usuario) {
        int resultado = -1;
        try {
            // Primero hasheamos la nueva contraseña
            String hashedPassword = PasswordHash.hashPassword(usuario.getContrasenha());

            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL UsuarioModificar(?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, usuario.getIdUsuario());
            cst.setString(2, hashedPassword);
            resultado = cst.executeUpdate();
            return resultado;
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error al hashear la contraseña: " + e.getMessage());
        } finally {
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
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
        } finally {
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setContrasenha(rs.getString("contrasenha")); // No decodificamos la contraseña
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
                usuario.setContrasenha(rs.getString("contrasenha")); // No decodificamos la contraseña
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setActivo(true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return usuario;
    }

    @Override
    public Usuario ExisteUsuario(String username, String contrasenha) {
        Usuario usuario = null;
        try {
            // Primero obtenemos el usuario por username
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL UsuarioValidar(?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, username);
            rs = cst.executeQuery();

            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("contrasenha");
                // Verificamos si la contraseña coincide
                if (PasswordHash.verifyPassword(contrasenha, hashedPasswordFromDB)) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setContrasenha(hashedPasswordFromDB); // Mantenemos el hash
                    usuario.setIdPersona(rs.getInt("idPersona"));
                    usuario.setActivo(true);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error al verificar la contraseña: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
                usuario.setContrasenha(rs.getString("contrasenha")); // No decodificamos la contraseña
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setActivo(true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
                usuario.setContrasenha(rs.getString("contrasenha")); // No decodificamos la contraseña
                usuario.setIdPersona(rs.getInt("idPersona"));
                usuario.setActivo(true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
            
            String queryMedico = "SELECT 'Medico' AS rol FROM Medico WHERE idMedico = ? AND activo = true";
            statementMedico = con.prepareStatement(queryMedico);
            statementMedico.setInt(1, idPersona);
            rs = statementMedico.executeQuery();
            if (rs.next()) {
                rolesActivos.add(rs.getString("rol"));
            }

            String queryAdministrador = "SELECT 'Administrador' AS rol FROM Administrador WHERE idAdministrador = ? AND activo = true";
            statementAdministrador = con.prepareStatement(queryAdministrador);
            statementAdministrador.setInt(1, idPersona);
            rs = statementAdministrador.executeQuery();
            if (rs.next()) {
                rolesActivos.add(rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statementMedico != null) {
                    statementMedico.close();
                }
                if (statementPaciente != null) {
                    statementPaciente.close();
                }
                if (statementAuxiliar != null) {
                    statementAuxiliar.close();
                }
                if (statementAdministrador != null) {
                    statementAdministrador.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return rolesActivos;
    }


    
    @Override
    public List<Usuario> listarActivoNoActivo(int valor) {
        List<Usuario> result = new ArrayList<>(); // Lista vacía inicializada
        Connection con = null;
        PreparedStatement cmd = null;
        ResultSet cursor = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "SELECT idUsuario, username, contrasenha, idPersona, activo "
                       + "FROM Usuario WHERE activo = ?";
            cmd = con.prepareStatement(sql);
            cmd.setInt(1, valor);
            cursor = cmd.executeQuery();
            while (cursor.next()) {
                Usuario user = new Usuario();
                user.setIdUsuario(cursor.getInt("idUsuario"));
                if (cursor.getObject("username") != null) {
                    user.setUsername(cursor.getString("username"));
                }
                if (cursor.getObject("contrasenha") != null) {
                    user.setContrasenha(cursor.getString("contrasenha"));
                }
                user.setIdPersona(cursor.getInt("idPersona"));
                user.setActivo(cursor.getBoolean("activo"));
                result.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Cerrar el ResultSet, PreparedStatement y Connection en el bloque finally
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (cmd != null) {
                    cmd.close();
                }
                DBPoolManager.getInstance().cerrarConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    @Override
    public List<Usuario> listar(String filtro) {
        List<Usuario> result = new ArrayList<Usuario>(); // lista vacía inicializada
        Connection con = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "SELECT u.idUsuario, u.username, u.contrasenha, u.idPersona, u.activo "
                    + "FROM Usuario u "
                    + "JOIN Persona p ON u.idPersona = p.idPersona "
                    + "WHERE p.nombre LIKE ? OR p.apellido LIKE ? OR u.username LIKE ? ";
            PreparedStatement cmd = con.prepareStatement(sql);
            cmd.setString(1, "%" + filtro + "%");
            cmd.setString(2, "%" + filtro + "%");
            cmd.setString(3, "%" + filtro + "%");
            
            ResultSet cursor = cmd.executeQuery();
            while (cursor.next()) {
                Usuario user = new Usuario();
                user.setIdUsuario(cursor.getInt("idUsuario"));
                user.setUsername(cursor.getString("username"));
                user.setContrasenha(cursor.getString("contrasenha"));
                user.setIdPersona(cursor.getInt("idPersona"));
                user.setActivo(cursor.getBoolean("activo"));
                result.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }

        return result;            
    }
    
    @Override
    public ArrayList<Usuario> listarTodos1() {
        ArrayList<Usuario> result = new ArrayList<Usuario>(); // lista vacía inicializada
        Connection con = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "SELECT u.idUsuario, u.username, u.contrasenha, u.idPersona, u.activo "
                    + "FROM Usuario u ";
            PreparedStatement cmd = con.prepareStatement(sql);

            ResultSet cursor = cmd.executeQuery();
            while (cursor.next()) {
                Usuario user = new Usuario();
                user.setIdUsuario(cursor.getInt("idUsuario"));
                user.setUsername(cursor.getString("username"));
                user.setContrasenha(cursor.getString("contrasenha"));
                user.setIdPersona(cursor.getInt("idPersona"));
                user.setActivo(cursor.getBoolean("activo"));
                result.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }

        return result;            
    }

}
