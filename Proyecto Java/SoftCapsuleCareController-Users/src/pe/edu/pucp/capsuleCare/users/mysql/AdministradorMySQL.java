package pe.edu.pucp.capsuleCare.users.mysql;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import pe.edu.pucp.capsuleCare.users.dao.AdministradorDAO;
import pe.edu.pucp.capsuleCare.users.seguridad.PasswordHash;
import pe.edu.pucp.citamedica.model.clinica.Administrador;

import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;



public class AdministradorMySQL implements AdministradorDAO{
 
    private Connection con;
    private String sql;
    private CallableStatement cst;
    private ResultSet rs;
   
    @Override
    public int insertar(Administrador administrador, Usuario usuario) {
        int resultado = -1;
        String hashedPassword;

        // Intentamos generar el hash de la contraseña
        try {
            hashedPassword = PasswordHash.hashPassword(usuario.getContrasenha());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error al hashear la contraseña: " + e.getMessage());
            return resultado;
        }

        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AdministradorInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
            cst.setString(3, usuario.getUsername());
            cst.setString(4, hashedPassword); // Usamos la contraseña hasheada
            cst.setString(5, administrador.getDNI());
            cst.setString(6, administrador.getNombre());
            cst.setString(7, administrador.getApellido());
            cst.setString(8, administrador.getCorreoElectronico());
            cst.setInt(9, administrador.getNumTelefono());
            cst.setString(10, administrador.getDireccion());
            cst.setDate(11, new java.sql.Date(administrador.getFechaNacimiento().getTime()));
            cst.setString(12, String.valueOf(administrador.getGenero()));

            resultado = cst.executeUpdate();

            administrador.setIdPersona(cst.getInt(1));
            usuario.setIdUsuario(cst.getInt(2));
//            administrador.setIdAdministrador(administrador.getIdPersona());
//            administrador.setDNI(administrador.getDNI());
//            administrador.setNombre(administrador.getNombre());
//            administrador.setApellido(administrador.getApellido());
//            administrador.setCorreoElectronico(administrador.getCorreoElectronico());
//            administrador.setNumTelefono(administrador.getNumTelefono());
//            administrador.setDireccion(administrador.getDireccion());
//            administrador.setFechaNacimiento(administrador.getFechaNacimiento());
//            administrador.setGenero(administrador.getGenero());
            administrador.setActivo(false);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            // Cerrar los recursos de base de datos
            try {
                
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }

        return resultado;
    }

    @Override
    public int modificar(Administrador administrador) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AdministradorModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, administrador.getIdAdministrador());
            cst.setString(2, administrador.getDNI());
            cst.setString(3, administrador.getNombre());
            cst.setString(4, administrador.getApellido());
            cst.setString(5, administrador.getCorreoElectronico());
            cst.setInt(6, administrador.getNumTelefono());
            cst.setString(7, administrador.getDireccion());
            cst.setDate(8, new java.sql.Date(administrador.getFechaNacimiento().getTime()));
            cst.setString(9, String.valueOf(administrador.getGenero()));
            cst.setBoolean(10, administrador.isActivo());
        
            resultado = cst.executeUpdate();
            
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }finally {
            // Cerrar los recursos de base de datos
            try {
               
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public int eliminar(int idAdministrador) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call AdministradorEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idAdministrador);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            // Cerrar los recursos de base de datos
            try {
                
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<Administrador> listarTodos() {
         ArrayList<Administrador> administradores = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AdministradorListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Administrador admin = new Administrador();
                admin.setIdAdministrador(rs.getInt("idPersona"));
                admin.setDNI(rs.getString("DNI"));
                admin.setNombre(rs.getString("nombre"));
                admin.setApellido(rs.getString("apellido"));
                admin.setCorreoElectronico(rs.getString("correoElectronico"));
                admin.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                admin.setActivo(rs.getBoolean("activo"));
                administradores.add(admin);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Cerrar los recursos de base de datos
            try {
                if (rs != null) rs.close();
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        return administradores;        
    }

    @Override
    public Administrador obtenerPorId(int idAdministrador) {
        Administrador administrador = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AdministradorListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAdministrador);
            rs = cst.executeQuery();
            if (rs.next()) {
                administrador = new Administrador();
                administrador.setIdAdministrador(rs.getInt("idPersona"));
                administrador.setDNI(rs.getString("DNI"));
                administrador.setNombre(rs.getString("nombre"));
                administrador.setApellido(rs.getString("apellido"));
                administrador.setCorreoElectronico(rs.getString("correoElectronico"));
                administrador.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                administrador.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Cerrar los recursos de base de datos
            try {
                if (rs != null) rs.close();
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        return administrador;
    }   



   @Override
    public Administrador obtenerPorId1(int idAdmin) {
        Administrador resultado = null;
        String query = "SELECT Persona.*, Administrador.activo "
                + "FROM Persona "
                + "JOIN Administrador ON Persona.idPersona = Administrador.idAdministrador "
                + "WHERE Administrador.activo = ? AND Administrador.idAdministrador = ?";

        try {
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);

            statement.setBoolean(1, true);
            statement.setInt(2, idAdmin);

            // Ejecutar la consulta y procesar el resultado
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                resultado = new Administrador();
                resultado.setIdAdministrador(resultSet.getInt("idPersona"));
                resultado.setDNI(resultSet.getString("DNI"));
                resultado.setNombre(resultSet.getString("nombre"));
                resultado.setApellido(resultSet.getString("apellido"));
                resultado.setCorreoElectronico(resultSet.getString("correoElectronico"));
                resultado.setNumTelefono(resultSet.getInt("numTelefono"));
                resultado.setDireccion(resultSet.getString("direccion"));
                resultado.setFechaNacimiento(resultSet.getDate("fechaNacimiento"));
                resultado.setGenero(resultSet.getString("genero").charAt(0));
                resultado.setActivo(resultSet.getBoolean("activo"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            DBPoolManager.getInstance().cerrarConexion(); 
        }

        return resultado;
        
    }

    @Override
    public int insertar1(Administrador admin) {
        int resultado = 0;
        int idPersona = 0;

        String queryPersona = "INSERT INTO Persona(DNI, nombre, apellido, correoElectronico, numTelefono, direccion, fechaNacimiento, genero) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryAdmin = "INSERT INTO Administrador(idAdministrador, activo) "
                + "VALUES (?, ?)";

        try (Connection conn = DBPoolManager.getInstance().getConnection(); 
                PreparedStatement psPersona = conn.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS); 
                PreparedStatement psAdmin = conn.prepareStatement(queryAdmin)) {

            psPersona.setString(1, admin.getDNI());
            psPersona.setString(2, admin.getNombre());
            psPersona.setString(3, admin.getApellido());
            psPersona.setString(4, admin.getCorreoElectronico());
            psPersona.setInt(5, admin.getNumTelefono());
            psPersona.setString(6, admin.getDireccion());
            psPersona.setDate(7, new java.sql.Date(admin.getFechaNacimiento().getTime()));
            psPersona.setString(8, String.valueOf(admin.getGenero()));
            psPersona.executeUpdate();

            System.out.println("Ejecutando inserción en Persona...");
            psPersona.executeUpdate();
            
            try (ResultSet generatedKeys = psPersona.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idPersona = generatedKeys.getInt(1);
                    System.out.println("ID generado para Persona: " + idPersona);
                } else {
                    System.err.println("Fallo al insertar en Persona, no se pudo obtener el ID.");
                    return 0; 
                }
            }

            psAdmin.setInt(1, idPersona);
            psAdmin.setBoolean(2, admin.isActivo());
            resultado = psAdmin.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
    
    @Override
    public List<Administrador> listar(String filtro) {
        System.out.println("Filtro recibido: " + filtro);
        List<Administrador> result = new ArrayList<>();
        Connection con = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "SELECT a.idAdministrador, p.DNI, p.nombre, p.apellido, p.correoElectronico, p.fechaNacimiento, a.activo "
                    + "FROM Administrador a "
                    + "JOIN Persona p ON a.idAdministrador = p.idPersona "
                    + "WHERE p.nombre LIKE ? ";

            PreparedStatement cmd = con.prepareStatement(sql);
            cmd.setString(1, "%" + filtro + "%"); 

            ResultSet cursor = cmd.executeQuery();
            while (cursor.next()) {
             
                Administrador admin = new Administrador();

                
                admin.setDNI(cursor.getString("DNI"));
                if (cursor.getObject("nombre") != null) {
                    admin.setNombre(cursor.getString("nombre"));
                }
                //admin.setNombre(cursor.getString("nombre"));
                admin.setApellido(cursor.getString("apellido"));
                admin.setCorreoElectronico(cursor.getString("correoElectronico"));
                admin.setFechaNacimiento(cursor.getDate("fechaNacimiento"));

                admin.setIdAdministrador(cursor.getInt("idAdministrador"));
                admin.setActivo(cursor.getBoolean("activo"));

                result.add(admin);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }

        return result;
    }

    @Override
    public int modificar_v2(Administrador administrador) {
        int resultado = 0;
        String query = "UPDATE Administrador SET activo = true "
                     + "WHERE idAdministrador = ?";

        try {
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);

            statement.setInt(1, administrador.getIdAdministrador());

            resultado = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }

        return resultado;
    }
}
