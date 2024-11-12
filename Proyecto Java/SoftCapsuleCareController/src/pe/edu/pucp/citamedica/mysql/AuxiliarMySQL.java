package pe.edu.pucp.citamedica.mysql;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.List;
import java.sql.PreparedStatement;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;
import pe.edu.pucp.seguridad.PasswordHash;

public class AuxiliarMySQL implements AuxiliarDAO{
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Auxiliar auxiliar, Usuario usuario) {
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
            sql = "{CALL AuxiliarInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
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

            resultado = cst.executeUpdate();

            auxiliar.setIdPersona(cst.getInt(1));
            usuario.setIdUsuario(cst.getInt(2));
            auxiliar.setIdAuxiliar(auxiliar.getIdPersona());
            auxiliar.setActivo(true);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultado;
    }
  
    @Override
    public int modificar(Auxiliar auxiliar) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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
            cst.setInt(10, 1);
            cst.setBoolean(11, auxiliar.isActivo());
            
            resultado = cst.executeUpdate();
            
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int eliminar(int idAuxiliar) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call AuxiliarEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idAuxiliar);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return auxiliar;
    }  




    @Override
    public Auxiliar obtenerPorId1(int idAuxiliar) {
        Auxiliar resultado = null;
        String query = "SELECT Persona.*, Auxiliar.activo "
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
            String sql = "SELECT a.idAuxiliar, p.DNI, p.nombre, p.apellido, p.correoElectronico, p.fechaNacimiento, a.activo, a.especialidad "
                    + "FROM Auxiliar a "
                    + "JOIN Persona p ON a.idAuxiliar = p.idPersona "
                    + "WHERE (p.nombre LIKE ? OR p.apellido LIKE ?) AND a.activo = true";

            PreparedStatement cmd = con.prepareStatement(sql);
            cmd.setString(1, "%" + filtro + "%");
            cmd.setString(2, "%" + filtro + "%");

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
                esp.setNombre(cursor.getString("especialidad"));
                auxiliar.setEspecialidad(esp);

                result.add(auxiliar);
            }
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
}
