package pe.edu.pucp.capsuleCare.users.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Date;
import pe.edu.pucp.capsuleCare.users.dao.PersonaDAO;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public class PersonaMySQL implements PersonaDAO{
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private PreparedStatement pstUsuario;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Persona persona,Usuario usuario){
        int resultado  =0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL InsertarPersona(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
            cst.setString(3, usuario.getUsername());
            cst.setString(4, usuario.getContrasenha());
            cst.setString(5, persona.getDNI());
            cst.setString(6, persona.getNombre());
            cst.setString(7, persona.getApellido());
            cst.setString(8, persona.getCorreoElectronico());
            cst.setInt(9, persona.getNumTelefono());
            cst.setString(10, persona.getDireccion());
            cst.setDate(11, new java.sql.Date(persona.getFechaNacimiento().getTime()));
            cst.setString(12, String.valueOf(persona.getGenero()));
        
            resultado = cst.executeUpdate();
            
            persona.setIdPersona(cst.getInt(1));
            usuario.setIdUsuario(cst.getInt(2));
            
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
    public int modificar(Persona persona){
        int resultado = 0;
        String sql = "UPDATE Persona SET dni = ?, nombre = ?, apellido = ?, correoElectronico = ?, "
                + "numTelefono = ?, direccion = ?, fechaNacimiento = ?, genero = ? "
                + "WHERE idPersona = ?";

        try {
            // Obtener la conexión desde DBManager y preparar la sentencia
            con = DBPoolManager.getInstance().getConnection();
            pst = con.prepareStatement(sql);

            // Configurar los valores en el PreparedStatement
            pst.setString(1, persona.getDNI());
            pst.setString(2, persona.getNombre());
            pst.setString(3, persona.getApellido());
            pst.setString(4, persona.getCorreoElectronico());
            pst.setInt(5, persona.getNumTelefono());
            pst.setString(6, persona.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(persona.getFechaNacimiento().getTime());
            pst.setDate(7, sqlDate);
            pst.setString(8, String.valueOf(persona.getGenero()));
            pst.setInt(9, persona.getIdPersona());

            // Ejecutar la consulta de actualización y obtener la cantidad de filas afectadas
            resultado = pst.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Médico modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún médico con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir la excepción si ocurre un error
        } finally {
            // Cerrar recursos para liberar la conexión y el PreparedStatement
            if (pst != null) try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultado;
    }
    
    @Override
    public int eliminar(int idPersona){
        int resultado = 0;
        sql = "DELETE FROM Persona WHERE idPersona = ?";

        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement cst = con.prepareStatement(sql)) {

            cst.setInt(1, idPersona);

            resultado = cst.executeUpdate();

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
        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement cst = con.prepareStatement(sql);
             ResultSet rs = cst.executeQuery()) {

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

        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement cst = con.prepareStatement(sql)) {

            cst.setInt(1, idPersona);
            rs = cst.executeQuery();

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
