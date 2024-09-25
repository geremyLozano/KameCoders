package pe.edu.pucp.citamedica.mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.paciente.model.Paciente;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.citamedica.dao.PacienteDAO;

public class PacienteMySQL implements PacienteDAO{
    private Connection con;
    private Statement st;
    private PreparedStatement pstPersona;
    private PreparedStatement pstPaciente;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    @Override
    public int insertar(Paciente paciente) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero) values(?,?,?,?,?,?,?)";
            pstPersona = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstPersona.setString(1, paciente.getNombre());
            pstPersona.setString(2, paciente.getApellido());
            pstPersona.setString(3, paciente.getCorreoElectronico());
            pstPersona.setInt(4, paciente.getNumTelefono());
            pstPersona.setString(5, paciente.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(paciente.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.setString(7, String.valueOf(paciente.getGenero()));
            pstPersona.executeUpdate();
            
            rs = pstPersona.getGeneratedKeys();//Obtengo el IDPERSONA GENERADO
            int idPersona = 0;
            if(rs.next()){
                idPersona = rs.getInt(1);
            }
            
            sql = "INSERT INTO Paciente(idpersona,historialActivo) "
                    + "values(?,?)";
            pstPaciente = con.prepareStatement(sql);
            pstPaciente.setInt(1, idPersona);
            pstPaciente.setBoolean(2, paciente.getHistorialActivo());
            resultado = pstPaciente.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int eliminar(int idPaciente) {
        int resultado = 0;
        String sql = "DELETE FROM Paciente WHERE idPaciente = ?";
        String sqlPersona = "DELETE FROM Persona WHERE idPersona = "
                + "(SELECT idpersona FROM Paciente WHERE idPaciente = ?)";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstPaciente = con.prepareStatement(sql)) {

            pstPaciente.setInt(1, idPaciente);

            resultado = pstPaciente.executeUpdate();

            if (resultado > 0) {
                System.out.println("Paciente eliminado correctamente.");
                try (PreparedStatement pstPersona = con.prepareStatement(sqlPersona)){
                    pstPersona.setInt(1, idPaciente);
                    int resultadoPersona = pstPersona.executeUpdate();
                    if(resultadoPersona>0){
                        System.out.println("Datos Persona del Paciente han sido eliminados");                     
                    }else{
                        System.out.println("No se encontro la persona asociada");
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
        return resultado;
    }

    @Override
    public ArrayList<Paciente> listarTodos() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT idPaciente,nombre,apellido FROM paciente WHERE "
                    + "historialActivo = 1";
            rs = st.executeQuery(sql);
            while(rs.next()){
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setHistorialActivo(true);
                pacientes.add(paciente);
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
        return pacientes;
    }

    @Override
    public Paciente obtenerPorId(int idPaciente) {
        Paciente paciente = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT idPaciente, activo FROM Paciente WHERE idPaciente = ?";
            pstPaciente = con.prepareStatement(sql);
            pstPaciente.setInt(1, idPaciente);
            rs = pstPaciente.executeQuery();

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPaciente"));
                paciente.setHistorialActivo(rs.getBoolean("activo"));
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
        return paciente;
    }

    @Override
    public int modificar(Paciente paciente) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE paciente SET historialActivo = ? WHERE idPaciente = ?";
            pstPaciente = con.prepareStatement(sql);
            pstPaciente.setBoolean(1, paciente.getHistorialActivo());
            pstPaciente.setInt(2, paciente.getIdPaciente());
            resultado = pstPaciente.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }   
}
