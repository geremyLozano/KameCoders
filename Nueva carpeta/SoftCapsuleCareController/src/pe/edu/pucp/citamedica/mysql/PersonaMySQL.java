package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.dao.PersonaDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import pe.edu.pucp.dbmanager.config.DBManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import pe.edu.pucp.citamedica.paciente.model.Persona;
import pe.edu.pucp.citamedica.usuario.model.Usuario;

public class PersonaMySQL implements PersonaDAO{
    private Connection con;
    private Statement st;
    private PreparedStatement pstPersona;
    private PreparedStatement pstUsuario;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Persona persona,Usuario usuario){
        int resultado  =0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero,dni) values(?,?,?,?,?,?,?,?)";
            pstPersona = con.prepareStatement(sql);
            pstPersona.setString(1, persona.getNombre());
            pstPersona.setString(2, persona.getApellido());
            pstPersona.setString(3, persona.getCorreoElectronico());
            pstPersona.setInt(4, persona.getNumTelefono());
            pstPersona.setString(5, persona.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(persona.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.setString(7, String.valueOf(persona.getGenero()));
            pstPersona.setString(8, persona.getDNI());
            pstPersona.executeUpdate();
            
            rs = pstPersona.getGeneratedKeys();//Obtengo el IDPERSONA GENERADO
            int idPersona = 0;
            if(rs.next()){
                idPersona = rs.getInt(1);
            }
            
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
    public int modificar(Persona persona){
        int resultado = 0;
        sql = "UPDATE Persona SET dni = ?, nombre = ?, apellido = ?, correoElectronico = ?, numTelefono = ?"
                + "direccion = ?, fechaNacimiento = ?, genero = ? " + " WHERE idPersona = ?";

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pstPersona = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pstPersona.setString(1, persona.getDNI());
            pstPersona.setString(2, persona.getApellido());
            pstPersona.setString(3, persona.getCorreoElectronico());
            pstPersona.setInt(4, persona.getNumTelefono());
            pstPersona.setString(5, persona.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(persona.getFechaNacimiento().getTime());
            pstPersona.setDate(6, sqlDate);
            pstPersona.setString(7, String.valueOf(persona.getGenero()));
            pstPersona.setInt(8, persona.getIdPersona());
            // Ejecutar la consulta de actualización
            resultado = pstPersona.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Médico modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún médico con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir la excepción si ocurre un error
        }

        return resultado;
    }
    
    @Override
    public int eliminar(int idPersona){
        int resultado = 0;
        sql = "DELETE FROM Persona WHERE idPersona = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstPersona = con.prepareStatement(sql)) {

            pstPersona.setInt(1, idPersona);

            resultado = pstPersona.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("La fila de la tabla Persona se ha eliminado correctamente.");
            } else {
                System.out.println("No se encontró ninguna persona con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
    
    @Override
    public ArrayList<Persona> listarTodos(){
        ArrayList<Persona> listaPersona = new ArrayList<>();
        sql = "SELECT * FROM Persona";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstPersona = con.prepareStatement(sql);
             ResultSet rs = pstPersona.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Persona
                Persona persona = new Persona();
                persona.setIdPersona(rs.getInt("idPersona"));
                persona.setDNI(rs.getString("DNI"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setCorreoElectronico(rs.getString("correoElectronico"));
                persona.setNumTelefono(rs.getInt("numTelefono"));
                persona.setDireccion(rs.getString("direccion"));
                //Obtener la fecha de la base de datos
                java.sql.Date sqlDate = rs.getDate("fechaNacimiento");
                //Convertir java.sql.Date a java.util.Date
                java.util.Date fecha = new java.util.Date(sqlDate.getTime());
                persona.setFechaNacimiento(fecha);
                persona.setGenero(rs.getString("genero").charAt(0));

                //Añadir el objeto Persona a la lista
                listaPersona.add(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }
        return listaPersona;  // Retornar la lista de personacion
    }
    
    @Override
    public Persona obtenerPorId(int idPersona){
        Persona persona = null;
        sql = "SELECT * FROM Persona WHERE idPersona = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstPersona = con.prepareStatement(sql)) {

            pstPersona.setInt(1, idPersona);
            rs = pstPersona.executeQuery();

            if (rs.next()) {
                persona = new Persona();
                persona.setIdPersona(rs.getInt("idPersona"));
                persona.setDNI(rs.getString("DNI"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setCorreoElectronico(rs.getString("correoElectronico"));
                persona.setNumTelefono(rs.getInt("numTelefono"));
                persona.setDireccion(rs.getString("direccion"));
                //Obtener la fecha de la base de datos
                java.sql.Date sqlDate = rs.getDate("fechaNacimiento");
                //Convertir java.sql.Date a java.util.Date
                java.util.Date fecha = new java.util.Date(sqlDate.getTime());
                persona.setFechaNacimiento(fecha);
                persona.setGenero(rs.getString("genero").charAt(0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persona;
    }
    
    
}
