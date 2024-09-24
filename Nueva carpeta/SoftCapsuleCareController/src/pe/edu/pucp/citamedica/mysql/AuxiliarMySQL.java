package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.clinica.model.Auxiliar;
import pe.edu.pucp.citamedica.dao.AuxiliarDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.dbmanager.config.DBManager;


public abstract class AuxiliarMySQL implements AuxiliarDao{
    private Connection con;
    private PreparedStatement pst;
    private String sql;
    private CallableStatement cst;
    
    @Override
    public int insertar(Auxiliar auxiliar){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Auxiliar(dni,nombre) values(?,?)";
            cst = con.prepareCall(sql);
            cst.setInt(1,auxiliar.getDNI());
            cst.setString(2, auxiliar.getNombre());
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int modificar(Auxiliar auxiliar) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE auxiliar SET nombre = ? WHERE idAuxiliar = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, auxiliar.getNombre());
            pst.setInt(2, auxiliar.getIdAuxiliar());
            resultado = pst.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int eliminar(int idAuxiliar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Auxiliar> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Auxiliar obtenerPorId(int idAuxiliar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
