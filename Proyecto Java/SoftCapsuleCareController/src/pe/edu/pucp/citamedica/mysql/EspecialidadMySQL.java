package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.dao.EspecialidadDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class EspecialidadMySQL implements EspecialidadDAO {
    private Connection con;
    private PreparedStatement pst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Especialidad especialidad) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "CALL ESPECIALIDAD_INSERTAR(?,?)";
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
        sql = "CALL ESPECIALIDAD_ACTUALIZAR(?,?,?,?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pst.setInt(1,especialidad.getIdEspecialidad());
            pst.setString(2,especialidad.getNombre());
            pst.setDouble(3,especialidad.getCostoConsulta());
            pst.setBoolean(4,especialidad.isActivo());
            
            // Ejecutar la consulta de actualización
            resultado = pst.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Especialidad modificado correctamente.");
            } else {
                System.out.println("No se encontró ninguna especualidad con ese ID.");
            }

        } catch (SQLException e) {
            // Imprimir la excepción si ocurre un error
            System.out.println(e.getMessage());
            
        }

        return resultado;
    }

    @Override
    public int eliminar(int idEspecialidad) {
        int resultado = 0;
        sql = "CALL ESPECIALIDAD_ELIMINAR(?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();
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
            System.out.println(e.getMessage());
        }

        return resultado;
    }


    @Override
    public ArrayList<Especialidad> listarTodos() {
        ArrayList<Especialidad> listaEspecialidad = new ArrayList<>();
        sql = "CALL ESPECIALIDAD_LISTAR_TODAS()";
        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto 
                Especialidad especialidad = new Especialidad();
                
                especialidad.setNombre(rs.getString("nombre"));
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                especialidad.setCostoConsulta(rs.getDouble("costoConsulta"));

                // Añadir el objeto Medico a la lista
                listaEspecialidad.add(especialidad);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listaEspecialidad;  // Retornar la lista de médicos
    }

    @Override
    public Especialidad obtenerPorId(int idEspecialidad) {
        Especialidad especialidad = null;
        sql = "CALL ESPECIALIDAD_BUSCARPORID(?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idEspecialidad);
            rs = pst.executeQuery();

            if (rs.next()) {
                especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                especialidad.setCostoConsulta(rs.getDouble("costoConsulta"));          
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return especialidad;
    }
    @Override
    public Especialidad obtenerPorId1(int idEspecialidad) {
        Especialidad resultado = null;
        String query = "SELECT * FROM Especialidad WHERE activo = ? AND idEspecialidad = ?";

        try {
            // Crear una conexión y preparar la declaración
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);
            statement.setBoolean(1, true); // Establecer el parámetro activo
            statement.setInt(2, idEspecialidad); // Establecer el parámetro idEspecialidad

            // Ejecutar la consulta
            ResultSet reader = statement.executeQuery();

            if (reader != null) {
                if (reader.next()) { // Usar next() para obtener la primera fila
                    resultado = new Especialidad();
                    resultado.setIdEspecialidad(reader.getInt("idEspecialidad"));
                    resultado.setNombre(reader.getString("nombre"));
                    resultado.setCostoConsulta(reader.getDouble("costoConsulta"));
                    resultado.setActivo(reader.getBoolean("activo"));
                }
            }

            // Cerrar el ResultSet y PreparedStatement
            reader.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        } finally {
            DBPoolManager.getInstance().cerrarConexion(); // Cerrar conexión
        }

        return resultado;
    }
    
    public int insertar1(Especialidad especialidad) {
        String query = "INSERT INTO Especialidad(nombre, costoConsulta, activo) VALUES (?, ?, ?)";
        int resultado = 0;

        try {
            // Crear una conexión y preparar la declaración
            PreparedStatement statement = DBPoolManager.getInstance().getConnection().prepareStatement(query);

            // Establecer los parámetros
            statement.setString(1, especialidad.getNombre());
            statement.setDouble(2, especialidad.getCostoConsulta());
            statement.setBoolean(3, especialidad.isActivo());

            // Ejecutar la inserción
            resultado = statement.executeUpdate();

            // Cerrar el PreparedStatement
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        } finally {
            DBPoolManager.getInstance().cerrarConexion(); // Cerrar conexión
        }

        return resultado;
    }
}
