package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.dbmanager.config.DBManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Time;
import java.util.stream.Collectors;
import pe.edu.pucp.citamedica.model.clinica.DiaSemana;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;

public class MedicoMySQL implements MedicoDAO {
    private Connection con;
    private Statement st;
    private PreparedStatement pstPersona;
    private PreparedStatement pstMedico;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Medico medico) {


        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            
            
            sql = "{CALL insertarMedico(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            
            
            pstMedico = con.prepareStatement(sql);
            
            
            pstMedico.setString(1, medico.getDNI());
            pstMedico.setString(2, medico.getNombre());
            pstMedico.setString(3, medico.getApellido());
            pstMedico.setString(4, medico.getCorreoElectronico());
            pstMedico.setInt(5, medico.getNumTelefono());
            pstMedico.setString(6, medico.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(medico.getFechaNacimiento().getTime());
            pstMedico.setDate(7,sqlDate);
           
            pstMedico.setString(8, String.valueOf(medico.getGenero()));
            
            pstMedico.setString(9, medico.getNumColegiatura());
            pstMedico.setTime(10,Time.valueOf(medico.getHoraInicioTrabajo()));
            pstMedico.setTime(11,Time.valueOf(medico.getHoraFinTrabajo()));
            
            
            String diasLaboralesString = medico.getDiasLaborales().stream()
                .map(DiaSemana::name) // Obtener el nombre del enum
                .collect(Collectors.joining(",")); // Unirlos con coma
            
            
            pstMedico.setString(12, diasLaboralesString);       
            pstMedico.setInt(13,medico.getAhosExp());
            pstMedico.setBoolean(14,medico.isActivo());
            
      
            pstMedico.executeUpdate();
            
           
            resultado = pstMedico.executeUpdate();
            
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
    public int modificar(Medico medico) {
        int resultado = 0;
        sql = "UPDATE Medico SET nombre = ?, especialidad = ?, "
                + "numColegiatura = ?, horaInicioTrabajo = ?, "
                + "horaFinTrabajo = ?, aniosExp = ?, activo = ?"
                + " WHERE idMedico = ?";

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pstMedico = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pstMedico.setString(1, medico.getNombre());
            pstMedico.setString(2, medico.getEspecialidad().getNombre());
            pstMedico.setString(3, medico.getNumColegiatura());
            pstMedico.setTime(4, Time.valueOf(medico.getHoraInicioTrabajo()));  // Convertir LocalTime a Time
            pstMedico.setTime(5, Time.valueOf(medico.getHoraFinTrabajo()));    // Convertir LocalTime a Time
            pstMedico.setInt(6, medico.getAhosExp());
            pstMedico.setBoolean(7, medico.isActivo());
            pstMedico.setInt(8, medico.getIdMedico());  // ID del médico a modificar

            // Ejecutar la consulta de actualización
            resultado = pstMedico.executeUpdate();

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
    public int eliminar(int idMedico) {
        int resultado = 0;
        sql = "DELETE FROM Medico WHERE idMedico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstMedico = con.prepareStatement(sql)) {

            pstMedico.setInt(1, idMedico);

            resultado = pstMedico.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("Médico eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún médico con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }


    @Override
    public ArrayList<Medico> listarTodos() {
        ArrayList<Medico> listaMedicos = new ArrayList<>();
        String sql = "SELECT * FROM Medico";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstMedico = con.prepareStatement(sql);
             ResultSet rs = pstMedico.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Medico
                Medico medico = new Medico();
                medico.setIdMedico(rs.getInt("idMedico"));
                medico.setNombre(rs.getString("nombre"));

                Especialidad especialidad = new Especialidad(medico.getEspecialidad().getNombre(),
                medico.getEspecialidad().getCostoConsulta());
                especialidad.setNombre(rs.getString("nombreEspecialidad"));
                medico.setEspecialidad(especialidad);

                // Asignar otros atributos del Medico
                medico.setNumColegiatura(rs.getString("numColegiatura"));
                medico.setHoraInicioTrabajo(rs.getTime("horaInicioTrabajo").toLocalTime());
                medico.setHoraFinTrabajo(rs.getTime("horaFinTrabajo").toLocalTime());
                medico.setAhosExp(rs.getInt("aniosExp"));
                medico.setActivo(rs.getBoolean("activo"));

                // Añadir el objeto Medico a la lista
                listaMedicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }
        return listaMedicos;  // Retornar la lista de médicos
    }

    @Override
    public Medico obtenerPorId(int idMedico) {
        Medico medico = null;
        String sql = "SELECT * FROM Medico WHERE idMedico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstMedico = con.prepareStatement(sql)) {

            pstMedico.setInt(1, idMedico);
            ResultSet rs = pstMedico.executeQuery();

            if (rs.next()) {
                medico = new Medico();
                medico.setIdMedico(rs.getInt("idMedico"));
                medico.setNombre(rs.getString("nombre"));

                // Crear la instancia de Especialidad y asignar los valores
                Especialidad especialidad = new Especialidad(medico.getEspecialidad().getNombre(),
                medico.getEspecialidad().getCostoConsulta());
                especialidad.setNombre(rs.getString("nombreEspecialidad"));   

                // Asignar la especialidad al médico
                medico.setEspecialidad(especialidad);

                // Asignar otros atributos del médico
                medico.setNumColegiatura(rs.getString("numColegiatura"));
                medico.setHoraInicioTrabajo(rs.getTime("horaInicioTrabajo").toLocalTime());
                medico.setHoraFinTrabajo(rs.getTime("horaFinTrabajo").toLocalTime());
                medico.setAhosExp(rs.getInt("aniosExp"));
                medico.setActivo(rs.getBoolean("activo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medico;
    }
}
