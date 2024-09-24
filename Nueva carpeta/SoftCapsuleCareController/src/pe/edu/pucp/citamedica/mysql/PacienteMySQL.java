package pe.edu.pucp.citamedica.mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.PacienteDao;
import pe.edu.pucp.citamedica.paciente.model.Paciente;
import pe.edu.pucp.dbmanager.config.DBManager;

public class PacienteMySQL implements PacienteDao{
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
            pstPersona = con.prepareStatement(sql);
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
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call PACIENTE_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    /*
    @Override
    public int eliminar(int idPaciente) {
        int resultado = 0;
        String sql = "DELETE FROM Paciente WHERE idPaciente = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstPaciente = con.prepareStatement(sql)) {

            pstPaciente.setInt(1, idPaciente);

            resultado = pstPaciente.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("Paciente eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún paciente con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultado;
    }

    */

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    
    
    
}
