package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.clinica.model.Medico;
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
import pe.edu.pucp.citamedica.clinica.model.Especialidad;

public class MedicoMySQL implements MedicoDAO {
    private Connection con;
    private Statement st;
    private PreparedStatement pstPersona;
    private PreparedStatement pstMedico;
    private PreparedStatement pstGetPersona;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Medico medico) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero) values(?,?,?,?,?,?)";
            pstPersona = con.prepareStatement(sql);
            pstPersona.setString(1, medico.getNombre());
            pstPersona.setString(2, medico.getApellido());
            pstPersona.setString(3, medico.getCorreoElectronico());
            pstPersona.setInt(4, medico.getNumTelefono());
            pstPersona.setString(5, medico.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(medico.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.executeUpdate();
            
            rs = pstPersona.getGeneratedKeys();//Obtengo el IDPERSONA GENERADO
            int idPersona = 0;
            if(rs.next()){
                idPersona = rs.getInt(1);
            }
            
            sql = "INSERT INTO Medico(especialidad,numColegiatura,horaInicioTrabajo,"
                    + "horaFinTrabajo, ahosExp, activo) "
                    + "values(?,?,?,?,?,?)";
            pstMedico = con.prepareStatement(sql);
            pstMedico.setInt(1, medico.getEspecialidad().getIdEspecialidad());
            pstMedico.setString(2, medico.getNumColegiatura());
            pstMedico.setTime(3,Time.valueOf(medico.getHoraInicioTrabajo()));
            pstMedico.setTime(4,Time.valueOf(medico.getHoraFinTrabajo()));
            pstMedico.setInt(5,medico.getAhosExp());
            pstMedico.setBoolean(6,medico.isActivo());
            
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
            System.out.println(e.getMessage());  // Imprimir la excepción si ocurre un error
        }

        return resultado;
    }

    @Override
    public int eliminar(int idMedico) {
        int resultado = 0;
        String sqlMedico = "DELETE FROM Medico WHERE idMedico = ?";
        String sqlPersona = "DELETE FROM Persona WHERE idPersona = ?";
        String sqlGetPersona = "SELECT idpersona FROM Medico WHERE idMedico = ?";
        try (Connection con = DBManager.getInstance().getConnection()) {
            int idPersona = 0;
            try(PreparedStatement pstGetPersona = con.prepareStatement(sqlGetPersona)){
                pstGetPersona.setInt(1, idMedico);
                ResultSet rsN = pstGetPersona.executeQuery();
                if(rsN.next()){
                    idPersona = rsN.getInt("idpersona");
                }else{
                    System.out.println("No se encontro ningun medico con ese ID");
                    return resultado;
                }
            }
            
            // Primero eliminar el registro de Paciente
            try (PreparedStatement pstMedico = con.prepareStatement(sqlMedico)) {
                pstMedico.setInt(1, idMedico);
                resultado = pstMedico.executeUpdate();
                
                if (resultado > 0) {
                    System.out.println("Datos del Medico han sido eliminados.");

                    // Luego eliminar el registro de Persona asociado
                    try (PreparedStatement pstPersona = con.prepareStatement(sqlPersona)) {
                        pstPersona.setInt(1, idPersona);
                        resultado = pstPersona.executeUpdate();

                        if (resultado > 0) {
                            System.out.println("Datos de la Persona han sido eliminados.");
                        } else {
                            System.out.println("No se encontró la persona asociada.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("No se encontró ningún paciente con ese ID.");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
