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
import pe.edu.pucp.citamedica.dao.PacienteDao;

public class PacienteMySQL implements PacienteDao{
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    @Override
    public int insertar(Paciente paciente) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{INSERT into Paciente(dni,nombre) values(?,?))";
            cst = con.prepareCall(sql);
            cst.setInt(1, paciente.getDNI());
            cst.setString(2, paciente.getNombre());
            resultado = cst.executeUpdate();
            
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Paciente paciente) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE paciente SET nombre = ? WHERE idPaciente = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, paciente.getNombre());
            pst.setInt(2, paciente.getIdPaciente());
            resultado = pst.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int eliminar(int idPaciente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Paciente> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Paciente obtenerPorId(int idPaciente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
}
