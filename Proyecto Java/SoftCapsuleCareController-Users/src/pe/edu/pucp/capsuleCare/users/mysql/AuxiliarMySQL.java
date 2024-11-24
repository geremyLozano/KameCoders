package pe.edu.pucp.capsuleCare.users.mysql;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.List;
import java.sql.PreparedStatement;
import pe.edu.pucp.capsuleCare.users.dao.AuxiliarDAO;
import pe.edu.pucp.capsuleCare.users.seguridad.PasswordHash;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;


public class AuxiliarMySQL implements AuxiliarDAO{
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Auxiliar auxiliar, Usuario usuario) {
        int resultado = -1;
        String hashedPassword = null;
        Connection con = null;
        CallableStatement cst = null;

        // Intentamos generar el hash de la contraseña
        try {
            hashedPassword = PasswordHash.hashPassword(usuario.getContrasenha());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error al hashear la contraseña: " + e.getMessage());
            return resultado;
        }

        try {
            // Obtener conexión
            con = DBPoolManager.getInstance().getConnection();
            String sql = "{CALL AuxiliarInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Registrar los parámetros de salida
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.registerOutParameter(2, java.sql.Types.INTEGER);

            // Establecer los parámetros de entrada
            cst.setString(3, auxiliar.getDNI());
            cst.setString(4, hashedPassword); // Usamos la contraseña hasheada
            cst.setString(5, auxiliar.getDNI());
            cst.setString(6, auxiliar.getNombre());
            cst.setString(7, auxiliar.getApellido());
            cst.setString(8, auxiliar.getCorreoElectronico());
            cst.setInt(9, auxiliar.getNumTelefono());
            cst.setString(10, auxiliar.getDireccion());
            cst.setDate(11, new java.sql.Date(auxiliar.getFechaNacimiento().getTime()));
            cst.setString(12, String.valueOf(auxiliar.getGenero()));
            cst.setInt(13, auxiliar.getEspecialidad().getIdEspecialidad());

            // Ejecutar la consulta
            resultado = cst.executeUpdate();

            // Obtener los parámetros de salida
            auxiliar.setIdPersona(cst.getInt(1));
            usuario.setIdUsuario(cst.getInt(2));
            auxiliar.setIdAuxiliar(auxiliar.getIdPersona());
            auxiliar.setActivo(false);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Cerrar el CallableStatement y la conexión, si es necesario
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    DBPoolManager.getInstance().cerrarConexion();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }

        return resultado;
    }
  
    @Override
    public int modificar(Auxiliar auxiliar) {
        int resultado = -1;
        Connection con = null;
        CallableStatement cst = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "{CALL AuxiliarModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            cst.setInt(1, auxiliar.getIdAuxiliar());
            cst.setString(2, auxiliar.getDNI());
            cst.setString(3, auxiliar.getNombre());
            cst.setString(4, auxiliar.getApellido());
            cst.setString(5, auxiliar.getCorreoElectronico());
            cst.setInt(6, auxiliar.getNumTelefono());
            cst.setString(7, auxiliar.getDireccion());
            cst.setDate(8, new java.sql.Date(auxiliar.getFechaNacimiento().getTime()));
            cst.setString(9, String.valueOf(auxiliar.getGenero()));
            cst.setInt(10, 1);  // Suponiendo que el valor 1 es fijo o se calcula antes
            cst.setBoolean(11, auxiliar.isActivo());

            resultado = cst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Cerrar recursos en el bloque finally
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    DBPoolManager.getInstance().cerrarConexion();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }

        return resultado;
    }
    
    @Override
    public int eliminar(int idAuxiliar) {
        int resultado = 0;
        Connection con = null;
        CallableStatement cst = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "{call AuxiliarEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idAuxiliar);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Si DBPoolManager maneja la conexión, solo cerramos los recursos directamente
            try {
                if (cst != null) {
                    cst.close();
                }
                // Aquí solo cerramos la conexión si DBPoolManager no lo hace automáticamente
                DBPoolManager.getInstance().cerrarConexion();
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }

        return resultado;
    }

    @Override
    public ArrayList<Auxiliar> listarTodos() {
        ArrayList<Auxiliar> auxiliares = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Auxiliar auxiliar = new Auxiliar();
                Especialidad esp = new Especialidad();
                auxiliar.setIdAuxiliar(rs.getInt("idPersona"));
                auxiliar.setDNI(rs.getString("DNI"));
                auxiliar.setNombre(rs.getString("nombre"));
                auxiliar.setApellido(rs.getString("apellido"));
                esp.setNombre(rs.getString("especialidad"));
                auxiliar.setEspecialidad(esp);
                auxiliar.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                auxiliar.setActivo(rs.getBoolean("activo"));
                auxiliares.add(auxiliar);
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
        return auxiliares;
    }

    @Override
    public Auxiliar obtenerPorId(int idAuxiliar) {
        Auxiliar auxiliar = null;
        Connection con = null;
        CallableStatement cst = null;
        ResultSet rs = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAuxiliar);
            rs = cst.executeQuery();

            if (rs.next()) {
                auxiliar = new Auxiliar();
                Especialidad esp = new Especialidad();
                auxiliar.setIdAuxiliar(rs.getInt("idPersona"));
                auxiliar.setDNI(rs.getString("DNI"));
                auxiliar.setNombre(rs.getString("nombre"));
                auxiliar.setApellido(rs.getString("apellido"));
                esp.setNombre(rs.getString("especialidad"));
                auxiliar.setEspecialidad(esp);
                auxiliar.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                auxiliar.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();  // Cerrar ResultSet
                }
                if (cst != null) {
                    cst.close();  // Cerrar CallableStatement
                }
                if (con != null) {
                    con.close();  // Cerrar Connection
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return auxiliar;
    }


    @Override
    public Auxiliar obtenerPorId1(int idAuxiliar) {
        Auxiliar resultado = null;
        String query = "SELECT Persona.*, Auxiliar.activo, Auxiliar.idEspecialidad "
                + "FROM Persona "
                + "JOIN Auxiliar ON Persona.idPersona = Auxiliar.idAuxiliar "
                + "WHERE Auxiliar.activo = ? AND Auxiliar.idAuxiliar = ?";

        try {
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);

            statement.setBoolean(1, true);
            statement.setInt(2, idAuxiliar);

            // Ejecutar la consulta y procesar el resultado
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                resultado = new Auxiliar();
                resultado.setIdAuxiliar(resultSet.getInt("idPersona"));
                Especialidad especialidad = new Especialidad();
                especialidad.setIdEspecialidad(resultSet.getInt("idEspecialidad"));
                resultado.setEspecialidad(especialidad);
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
    public int insertar1(Auxiliar auxiliar) {
        int resultado = 0;
        int idPersona = 0;

        String queryPersona = "INSERT INTO Persona(DNI, nombre, apellido, correoElectronico, numTelefono, direccion, fechaNacimiento, genero) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryAuxiliar = "INSERT INTO Auxiliar(idAuxiliar, activo) "
                + "VALUES (?, ?)";

        try (Connection conn = DBPoolManager.getInstance().getConnection(); 
                PreparedStatement psPersona = conn.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS); PreparedStatement psAuxiliar = conn.prepareStatement(queryAuxiliar)) {

            psPersona.setString(1, auxiliar.getDNI());
            psPersona.setString(2, auxiliar.getNombre());
            psPersona.setString(3, auxiliar.getApellido());
            psPersona.setString(4, auxiliar.getCorreoElectronico());
            psPersona.setInt(5, auxiliar.getNumTelefono());
            psPersona.setString(6, auxiliar.getDireccion());
            psPersona.setDate(7, new java.sql.Date(auxiliar.getFechaNacimiento().getTime()));
            psPersona.setString(8, String.valueOf(auxiliar.getGenero()));
            
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
            psAuxiliar.setInt(1, idPersona);
            psAuxiliar.setBoolean(2, auxiliar.isActivo());
            resultado = psAuxiliar.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
    
    @Override
    public List<Auxiliar> listarFiltro(String filtro) {
        System.out.println("Filtro recibido: " + filtro);
        List<Auxiliar> result = new ArrayList<>();
        Connection con = null;

        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "SELECT a.idAuxiliar, p.DNI, p.nombre, p.apellido, p.correoElectronico, p.fechaNacimiento, a.activo, a.idEspecialidad, e.nombre AS EspeNombre "
                    + "FROM Auxiliar a "
                    + "JOIN Persona p ON a.idAuxiliar = p.idPersona "
                    + "JOIN Especialidad e ON e.idEspecialidad = a.idEspecialidad "
                    + "WHERE p.nombre LIKE ? OR p.apellido LIKE ? OR e.nombre LIKE ? ";

            PreparedStatement cmd = con.prepareStatement(sql);
            cmd.setString(1, "%" + filtro + "%");
            cmd.setString(2, "%" + filtro + "%");
            cmd.setString(3, "%" + filtro + "%");

            ResultSet cursor = cmd.executeQuery();
            while (cursor.next()) {
                Auxiliar auxiliar = new Auxiliar();

                auxiliar.setDNI(cursor.getString("DNI"));
                if (cursor.getObject("nombre") != null) {
                    auxiliar.setNombre(cursor.getString("nombre"));
                }
                auxiliar.setApellido(cursor.getString("apellido"));
                auxiliar.setCorreoElectronico(cursor.getString("correoElectronico"));
                auxiliar.setFechaNacimiento(cursor.getDate("fechaNacimiento"));
                auxiliar.setIdAuxiliar(cursor.getInt("idAuxiliar"));
                auxiliar.setActivo(cursor.getBoolean("activo"));

                Especialidad esp = new Especialidad();
                esp.setIdEspecialidad(cursor.getInt("idEspecialidad"));
                esp.setNombre(cursor.getString("EspeNombre"));
                auxiliar.setEspecialidad(esp);

                result.add(auxiliar);
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBPoolManager.getInstance().cerrarConexion();
        }

        return result;
    }
    
    @Override
    public ArrayList<Auxiliar> listarTodos1() {
        ArrayList<Auxiliar> listaAuxiliar = new ArrayList<>();

        String sql = "SELECT e.idEspecialidad, e.nombre as EspecialidadNombre, "
                + "p.DNI, p.nombre, p.apellido,p.direccion "
                + "FROM Auxiliar a "
                + "JOIN Persona p ON a.idAuxiliar = p.idPersona "
                + "JOIN Especialidad e ON a.idEspecialidad = e.idEspecialidad WHERE a.activo = true";

        try (Connection con = DBPoolManager.getInstance().getConnection(); PreparedStatement pstAuxiliar = con.prepareStatement(sql); ResultSet rs = pstAuxiliar.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Medico
                Auxiliar auxiliar = new Auxiliar();
                auxiliar.setIdAuxiliar(rs.getInt("idAuxiliar"));

                // Crear un objeto Especialidad y asignarlo al Medico
                Especialidad especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                especialidad.setNombre(rs.getString("EspecialidadNombre"));
                auxiliar.setEspecialidad(especialidad);

                // Asignar atributos heredados de Persona
                auxiliar.setDNI(rs.getString("DNI"));
                auxiliar.setNombre(rs.getString("nombre"));
                auxiliar.setApellido(rs.getString("apellido"));
                auxiliar.setDireccion(rs.getString("EspecialidadNombre"));
                

                // Agregar el medico a la lista
                listaAuxiliar.add(auxiliar);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }

        return listaAuxiliar;
    }

    @Override
    public int modificar_v2(Auxiliar auxiliar) {
        int resultado = 0;
        String query = "UPDATE Auxiliar SET idEspecialidad = ?, "
                     + "activo = true "
                     + "WHERE idAuxiliar = ?";

        try {
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);

            statement.setInt(1, auxiliar.getEspecialidad().getIdEspecialidad());
            statement.setInt(2, auxiliar.getIdAuxiliar());
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
