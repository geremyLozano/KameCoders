package pe.edu.pucp.citamedica.mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class PacienteMySQL implements PacienteDAO{
    private Connection con;
    private Statement st;
    private PreparedStatement pstPersona;
    private PreparedStatement pstPaciente;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Paciente paciente, Usuario usuario, Persona persona) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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
            
            paciente.setIdPaciente(persona.getIdPersona());
            paciente.setDNI(persona.getDNI());
            paciente.setNombre(persona.getNombre());
            paciente.setApellido(persona.getApellido());
            paciente.setCorreoElectronico(persona.getCorreoElectronico());
            paciente.setNumTelefono(persona.getNumTelefono());
            paciente.setDireccion(persona.getDireccion());
            paciente.setFechaNacimiento(persona.getFechaNacimiento());
            paciente.setGenero(persona.getGenero());
            paciente.setActivo(true);
            paciente.setHistorialActivo(false);
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int eliminar(int idPaciente) {
        int resultado = 0;
        try{

            con = DBPoolManager.getInstance().getConnection();
            sql = "{call PacienteEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idPaciente);
            resultado = cst.executeUpdate();

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
            sql = "{CALL PacienteListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPersona"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setCorreoElectronico(rs.getString("correoElectronico"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                paciente.setHistorialActivo(rs.getBoolean("historialActivo"));
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
            st = con.createStatement();
            sql = "{CALL PacienteListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);
            rs = cst.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPersona"));
                paciente.setDNI(rs.getString("DNI"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellido(rs.getString("apellido"));
                paciente.setCorreoElectronico(rs.getString("correoElectronico"));
                paciente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                paciente.setHistorialActivo(rs.getBoolean("historialActivo"));
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
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteModificar(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, paciente.getIdPaciente());
            cst.setString(2, paciente.getDNI());
            cst.setString(3, paciente.getNombre());
            cst.setString(4, paciente.getApellido());
            cst.setString(5, paciente.getCorreoElectronico());
            cst.setInt(6, paciente.getNumTelefono());
            cst.setString(7, paciente.getDireccion());
            cst.setDate(8, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            cst.setString(9, String.valueOf(paciente.getGenero()));
        
            resultado = cst.executeUpdate();
            
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }   
}
