package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.dao.EspecialidadDAO;
import pe.edu.pucp.dbmanager.config.DBManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;

public class EspecialidadMySQL implements EspecialidadDAO {
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Especialidad especialidad) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Especialidad(nombre,costoConsulta) values(?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, especialidad.getNombre());
            pst.setDouble(2, especialidad.getCostoConsulta());
            pst.executeUpdate();
                        
            resultado = pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
            e.printStackTrace();
            System.out.print("Error general" + e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Especialidad especialidad) {
        int resultado = 0;
        sql = "UPDATE Especialidad SET nombre = ?, costoConsulta = ? WHERE idEspecialidad = ?";

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pst.setString(1,especialidad.getNombre());
            pst.setDouble(2,especialidad.getCostoConsulta());
            
            // Ejecutar la consulta de actualización
            resultado = pst.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Especialidad modificado correctamente.");
            } else {
                System.out.println("No se encontró ninguna especualidad con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir la excepción si ocurre un error
        }

        return resultado;
    }

    @Override
    public int eliminar(int idEspecialidad) {
        int resultado = 0;
        sql = "DELETE FROM Especialidad WHERE idEspecialidad = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idEspecialidad);

            resultado = pst.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("Especialidad eliminado correctamente.");
            } else {
                System.out.println("No se encontró ninguna especialidad con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }


    @Override
    public ArrayList<Especialidad> listarTodos() {
        ArrayList<Especialidad> listaEspecialidad = new ArrayList<>();
        String sql = "SELECT * FROM Especialidad";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto 
                Especialidad especialidad = new Especialidad();
                
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                especialidad.setCostoConsulta(rs.getDouble("costoConsulta"));

                // Añadir el objeto Medico a la lista
                listaEspecialidad.add(especialidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }
        return listaEspecialidad;  // Retornar la lista de médicos
    }

    @Override
    public Especialidad obtenerPorId(int idEspecialidad) {
        Especialidad especialidad = null;
        String sql = "SELECT * FROM Especialidad WHERE idEspecialidad = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idEspecialidad);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                especialidad.setCostoConsulta(rs.getDouble("costoConsulta"));          
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidad;
    }
}
