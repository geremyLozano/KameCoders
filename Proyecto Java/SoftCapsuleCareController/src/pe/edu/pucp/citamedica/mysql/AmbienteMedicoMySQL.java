package pe.edu.pucp.citamedica.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import pe.edu.pucp.citamedica.dao.AmbienteMedicoDAO;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.citamedica.model.clinica.AmbienteMedico;
import pe.edu.pucp.citamedica.model.clinica.TipoAmbiente;

public class AmbienteMedicoMySQL implements AmbienteMedicoDAO{
    private Connection con;
    private PreparedStatement pst;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(AmbienteMedico ambiente) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();

            // Llamar al procedimiento almacenado
            String sql = "{CALL sp_insertar_ambiente_medico(?, ?, ?, ?)}";
            CallableStatement cst = con.prepareCall(sql);
            // Pasar los parámetros al procedimiento almacenado
            cst.setInt(1, ambiente.getNumPiso());
            cst.setString(2, ambiente.getUbicacion());
            cst.setInt(3, ambiente.getCapacidad());
            cst.setString(4, ambiente.getTipoAmbiente().toString());

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }

    
    @Override
    public int modificar(AmbienteMedico ambiente){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE AmbienteMedico SET numPiso = ?,ubicacion = ?, "
                    + "capacidad = ?, tipoAmbiente = ? WHERE idAmbienteMedico = ?";
            pst = con.prepareStatement(sql);
            pst = con.prepareStatement(sql);
            pst.setInt(1, ambiente.getNumPiso());
            pst.setString(2,ambiente.getUbicacion());
            pst.setInt(3, ambiente.getCapacidad());
            pst.setString(4,ambiente.getTipoAmbiente().toString());
            pst.setInt(5, ambiente.getIdAmbiente());
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int eliminar(int idAmbienteMedico){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call AMBIENTEMEDICO_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAmbienteMedico);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public ArrayList<AmbienteMedico> listarTodos(){
        ArrayList<AmbienteMedico> ambientes = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT * FROM AmbienteMedico";
            rs = st.executeQuery(sql);
            while(rs.next()){
                AmbienteMedico ambiente = new AmbienteMedico();
                ambiente.setIdAmbiente(rs.getInt("idAmbienteMedico"));
                ambiente.setNumPiso(rs.getInt("numPiso"));
                ambiente.setUbicacion(rs.getString("ubicacion"));
                ambiente.setCapacidad(rs.getInt("capacidad"));
                ambiente.setTipoAmbiente(rs.getObject("tipoAmbiente",TipoAmbiente.class));
                ambientes.add(ambiente);
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
        return ambientes;
    }
    
    @Override
    public AmbienteMedico obtenerPorId(int idAmbienteMedico){
        AmbienteMedico ambiente = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT * FROM AmbienteMedico WHERE idAmbienteMedico = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idAmbienteMedico);
            rs = pst.executeQuery();

            if (rs.next()) {
                ambiente = new AmbienteMedico();
                ambiente.setIdAmbiente(rs.getInt("idAmbienteMedico"));
                ambiente.setNumPiso(rs.getInt("numPiso"));
                ambiente.setUbicacion(rs.getString("ubicacion"));
                ambiente.setCapacidad(rs.getInt("capacidad"));
                ambiente.setTipoAmbiente(rs.getObject("tipoAmbiente",TipoAmbiente.class));
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
        return ambiente;
    }
    
    
}
