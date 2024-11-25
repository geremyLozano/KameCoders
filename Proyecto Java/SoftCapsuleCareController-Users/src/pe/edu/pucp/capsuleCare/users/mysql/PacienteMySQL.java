package pe.edu.pucp.capsuleCare.users.mysql;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import pe.edu.pucp.citamedica.model.usuario.Paciente;

import pe.edu.pucp.citamedica.model.usuario.Persona;
import java.sql.Date; // Para java.sql.Date
import java.util.Calendar; // Para Calendar
import java.util.List;
import pe.edu.pucp.capsuleCare.users.dao.PacienteDAO;
import pe.edu.pucp.capsuleCare.users.seguridad.PasswordHash;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;


public class PacienteMySQL implements PacienteDAO{
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Paciente paciente, Usuario usuario) {
        int resultado = -1;
        String hashedPassword;

        // Primero intentamos generar el hash de la contraseña
        try {
            hashedPassword = PasswordHash.hashPassword(usuario.getContrasenha());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error al hashear la contraseña: " + e.getMessage());
            return resultado;
        }

        // Si el hash se generó correctamente, procedemos con la inserción
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
            cst.setString(3, paciente.getDNI());
            cst.setString(4, hashedPassword);
            cst.setString(5, paciente.getDNI());
            cst.setString(6, paciente.getNombre());
            cst.setString(7, paciente.getApellido());
            cst.setString(8, paciente.getCorreoElectronico());
            cst.setInt(9, paciente.getNumTelefono());
            cst.setString(10, paciente.getDireccion());
            cst.setDate(11, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            cst.setString(12, String.valueOf(paciente.getGenero()));

            resultado = cst.executeUpdate();

            paciente.setIdPersona(cst.getInt(1));
            usuario.setIdUsuario(cst.getInt(2));
            usuario.setIdPersona(paciente.getIdPaciente());
            usuario.setActivo(true);

            paciente.setIdPaciente(paciente.getIdPersona());
            paciente.setDNI(paciente.getDNI());
//            paciente.setNombre(paciente.getNombre());
//            paciente.setApellido(paciente.getApellido());
//            paciente.setCorreoElectronico(paciente.getCorreoElectronico());
//            paciente.setNumTelefono(paciente.getNumTelefono());
//            paciente.setDireccion(paciente.getDireccion());
//            paciente.setFechaNacimiento(paciente.getFechaNacimiento());
//            paciente.setGenero(paciente.getGenero());
            paciente.setActivo(true);
            paciente.setHistorialActivo(false);

            return resultado;
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            return resultado;
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
    }

    @Override
    public int eliminar(int idPaciente) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call PacienteEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idPaciente);
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
    public ArrayList<Paciente> listarTodos() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPersona"));
                paciente.setDNI(rs.getString("DNI"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setCorreoElectronico(rs.getString("correoElectronico"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                paciente.setHistorialActivo(rs.getBoolean("historialActivo"));
                pacientes.add(paciente);
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
        return pacientes;
    }

    @Override
    public Paciente obtenerPorId(int idPaciente) {
        Paciente paciente = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);
            rs = cst.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPersona"));
                paciente.setDNI(rs.getString("DNI"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setCorreoElectronico(rs.getString("correoElectronico"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                paciente.setHistorialActivo(rs.getBoolean("historialActivo"));
                paciente.setActivo(true);
                paciente.setGenero(rs.getString("genero").charAt(0));
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
        return paciente;
    }

    @Override
    public int modificar(Paciente paciente) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, paciente.getIdPaciente());
            cst.setString(2, paciente.getDNI());
            cst.setString(3, paciente.getNombre());
            cst.setString(4, paciente.getApellido());
            cst.setString(5, paciente.getCorreoElectronico());
            cst.setInt(6, paciente.getNumTelefono());
            cst.setString(7, paciente.getDireccion());
            cst.setDate(8, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            cst.setString(9, String.valueOf(paciente.getGenero()));
            cst.setBoolean(10, paciente.getHistorialActivo());
            cst.setBoolean(11, paciente.isActivo());
        
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
    public Paciente obtenerPorId1(int idPaciente) {
        Paciente resultado = null;
        String query = "SELECT Persona.*, Paciente.historialActivo, Paciente.activo "
                + "FROM Persona "
                + "JOIN Paciente ON Persona.idPersona = Paciente.idPaciente "
                + "WHERE Paciente.activo = ? AND Paciente.idPaciente = ?";

        try {
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);

            statement.setBoolean(1, true);
            statement.setInt(2, idPaciente);

            // Ejecutar la consulta y procesar el resultado
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Solo esperamos un resultado, por lo que usamos 'if'
                resultado = new Paciente();
                resultado.setIdPaciente(resultSet.getInt("idPersona"));
                resultado.setDNI(resultSet.getString("DNI"));
                resultado.setNombre(resultSet.getString("nombre"));
                resultado.setApellido(resultSet.getString("apellido"));
                resultado.setCorreoElectronico(resultSet.getString("correoElectronico"));
                resultado.setNumTelefono(resultSet.getInt("numTelefono"));
                resultado.setDireccion(resultSet.getString("direccion"));
                resultado.setFechaNacimiento(resultSet.getDate("fechaNacimiento"));
                resultado.setGenero(resultSet.getString("genero").charAt(0)); // Convertir a char
                resultado.setHistorialActivo(resultSet.getBoolean("historialActivo"));
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
    public int insertar1(Paciente paciente) {
        int resultado = 0;
        int idPersona = 0;

        String queryPersona = "INSERT INTO Persona(DNI, nombre, apellido, correoElectronico, numTelefono, direccion, fechaNacimiento, genero) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryPaciente = "INSERT INTO Paciente(idPaciente, historialActivo,activo) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = DBPoolManager.getInstance().getConnection(); 
                PreparedStatement psPersona = conn.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS); PreparedStatement psPaciente = conn.prepareStatement(queryPaciente)) {

            // Insertar en la tabla Persona
            psPersona.setString(1, paciente.getDNI());
            psPersona.setString(2, paciente.getNombre());
            psPersona.setString(3, paciente.getApellido());
            psPersona.setString(4, paciente.getCorreoElectronico());
            psPersona.setInt(5, paciente.getNumTelefono());
            psPersona.setString(6, paciente.getDireccion());
            psPersona.setDate(7, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            psPersona.setString(8, String.valueOf(paciente.getGenero()));
            psPersona.executeUpdate();

            // Obtener el idPersona generado
            try (ResultSet generatedKeys = psPersona.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idPersona = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Fallo al insertar en Persona, no se pudo obtener el ID.");
                }
            }

            psPaciente.setInt(1, idPersona);
            psPaciente.setBoolean(2, paciente.getHistorialActivo());
            psPaciente.setBoolean(3, paciente.isActivo());
            resultado = psPaciente.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public List<Paciente> listar(String filtro) {
        List<Paciente> result = new ArrayList<>();
        con = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "SELECT p.DNI, p.nombre, p.apellido, p.correoElectronico, p.numTelefono, " +
                         "p.direccion, p.fechaNacimiento, p.genero, pac.idPaciente, pac.activo " +
                         "FROM Persona p " +
                         "JOIN Paciente pac ON p.idPersona = pac.idPaciente " +
                         "WHERE p.nombre LIKE ? OR p.apellido LIKE ? OR p.DNI LIKE ?";
            PreparedStatement cmd = con.prepareStatement(sql);
            cmd.setString(1, "%" + filtro + "%");
            cmd.setString(2, "%" + filtro + "%");
            cmd.setString(3, "%" + filtro + "%");

            ResultSet cursor = cmd.executeQuery();
            while (cursor.next()) {
                Paciente paciente = new Paciente();

                // Datos de Persona
                paciente.setDNI(cursor.getString("DNI"));
                paciente.setNombre(cursor.getString("nombre"));
                paciente.setApellido(cursor.getString("apellido"));
                paciente.setCorreoElectronico(cursor.getString("correoElectronico"));
                paciente.setNumTelefono(cursor.getInt("numTelefono"));
                paciente.setDireccion(cursor.getString("direccion"));
                paciente.setFechaNacimiento(cursor.getDate("fechaNacimiento"));
                paciente.setGenero(cursor.getString("genero").charAt(0));

                // Datos específicos de Paciente
                paciente.setIdPaciente(cursor.getInt("idPaciente"));
                paciente.setActivo(cursor.getBoolean("activo"));

                result.add(paciente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }

        return result;
    }

    @Override
    public int modificar_v2(Paciente paciente) {
        int resultado = 0;
        String query = "UPDATE Paciente SET historialActivo = ?, "
                     + "activo = true "
                     + "WHERE idPaciente = ?";

        try {
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);

            statement.setBoolean(1, paciente.getHistorialActivo());
            statement.setInt(2, paciente.getIdPaciente());

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
