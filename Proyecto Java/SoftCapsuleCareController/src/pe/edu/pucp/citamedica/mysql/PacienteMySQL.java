package pe.edu.pucp.citamedica.mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class PacienteMySQL implements PacienteDAO{
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Paciente paciente, Usuario usuario) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
            cst.setString(3, usuario.getUsername());
            cst.setString(4, usuario.getContrasenha());
            cst.setString(5, paciente.getDNI());
            cst.setString(6, paciente.getNombre());
            cst.setString(7, paciente.getApellido());
            cst.setString(8, paciente.getCorreoElectronico());
            cst.setInt(9, paciente.getNumTelefono());
            cst.setString(10, paciente.getDireccion());
            cst.setDate(11, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            cst.setString(12, String.valueOf(paciente.getGenero()));
           
            resultado = cst.executeUpdate();
            
            paciente.setIdPersona(cst.getInt(1));
            usuario.setIdUsuario(cst.getInt(2));
            
            paciente.setIdPaciente(paciente.getIdPersona());
            paciente.setDNI(paciente.getDNI());
            paciente.setNombre(paciente.getNombre());
            paciente.setApellido(paciente.getApellido());
            paciente.setCorreoElectronico(paciente.getCorreoElectronico());
            paciente.setNumTelefono(paciente.getNumTelefono());
            paciente.setDireccion(paciente.getDireccion());
            paciente.setFechaNacimiento(paciente.getFechaNacimiento());
            paciente.setGenero(paciente.getGenero());
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PacienteListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("idPersona"));
                paciente.setDNI(rs.getString("DNI"));
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
            con = DBPoolManager.getInstance().getConnection();
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
            sql = "{CALL PacienteModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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
            cst.setBoolean(10, paciente.getHistorialActivo());
            cst.setBoolean(11, paciente.isActivo());
        
            resultado = cst.executeUpdate();
            
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }   
}
