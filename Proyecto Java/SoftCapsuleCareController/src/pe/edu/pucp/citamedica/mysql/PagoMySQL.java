package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.dao.PagoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class PagoMySQL implements PagoDAO{
    private Connection con;
    private PreparedStatement pst;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Pago pago){
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PagoInsertar(?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.setDouble(2, pago.getDescuentoPorSeguro());
            cst.setDouble(3, pago.getMontoParcial());
            cst.setDouble(4, pago.getMontoTotal());
            cst.setString(5, pago.getConcepto());
            cst.setInt(6, pago.getIdPaciente());
        
            resultado = cst.executeUpdate();
            
            pago.setIdPago(cst.getInt(1));
            pago.setEstado(true);
            pago.setFechaPago(java.sql.Date.valueOf(LocalDate.now()));
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int modificar(Pago pago){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE Pago SET estado = ? WHERE idPago = ?";
            pst = con.prepareStatement(sql);
            pst.setBoolean(1, pago.getEstado());
            pst.setInt(2, pago.getIdPago());
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int eliminar(int idPago){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call PAGO_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPago);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public ArrayList<Pago> listarTodos(){
        ArrayList<Pago> pagos = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT * FROM Pago";
            rs = st.executeQuery(sql);
            while(rs.next()){
                Pago pago = new Pago();
                pago.setIdPago(rs.getInt("idPago"));
                pago.setDescuentoPorSeguro(rs.getDouble("descuentoPorSeguro"));
                pago.setMontoParcial(rs.getDouble("montoParcial"));
                pago.setMontoTotal(rs.getDouble("montoTotal"));
                pago.setFechaPago(rs.getDate("fechaPago"));
                pago.setConcepto(rs.getString("concepto"));
                pago.setEstado(rs.getBoolean("estado"));
                pagos.add(pago);
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
        return pagos;
    }
    
    @Override
    public Pago obtenerPorId(int idPago){
        Pago pago = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT * FROM Pago WHERE idPago = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idPago);
            rs = pst.executeQuery();

            if (rs.next()) {
                pago = new Pago();
                pago.setIdPago(rs.getInt("idPago"));
                pago.setDescuentoPorSeguro(rs.getDouble("descuentoPorSeguro"));
                pago.setMontoParcial(rs.getDouble("montoParcial"));
                pago.setMontoTotal(rs.getDouble("montoTotal"));
                pago.setFechaPago(rs.getDate("fechaPago"));
                pago.setConcepto(rs.getString("concepto"));
                pago.setEstado(rs.getBoolean("estado"));
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
        return pago;
    }
}
