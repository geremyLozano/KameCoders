package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.pago.model.Pago;
import pe.edu.pucp.citamedica.dao.PagoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import pe.edu.pucp.dbmanager.config.DBManager;

public class PagoMySQL implements PagoDAO{
    private Connection con;
    private PreparedStatement pst;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Pago pago){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Pago(DNI,descuentoPorSeguro,montoParcial,montoTotal,fechaPago,concepto,estado)"
                    + " values(?,?,?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, pago.getDNI());
            pst.setDouble(2, pago.getDescuentoPorSeguro());
            pst.setDouble(3, pago.getMontoParcial());
            pst.setDouble(4, pago.getMontoTotal());
            java.sql.Date sqlDate = new java.sql.Date(pago.getFechaPago().getTime());
            pst.setDate(5,sqlDate);
            pst.setString(6,pago.getConcepto());
            pst.setBoolean(7,pago.getEstado());
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
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
                pago.setDNI(rs.getString("DNI"));
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
                pago.setDNI(rs.getString("DNI"));
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
