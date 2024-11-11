package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.dao.PagoDAO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.time.LocalDate;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class PagoMySQL implements PagoDAO{
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Pago pago) {
        int idPagoGenerado = -1; // Cambié el nombre de la variable para mayor claridad
        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "{CALL PagoInsertar(?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Registrar el parámetro de salida para obtener el idPago generado
            cst.registerOutParameter(1, java.sql.Types.INTEGER);

            // Configurar los parámetros de entrada
            cst.setDouble(2, pago.getDescuentoPorSeguro());
            cst.setDouble(3, pago.getMontoParcial());
            cst.setDouble(4, pago.getMontoTotal());
            cst.setString(5, pago.getConcepto());
            cst.setInt(6, pago.getIdPaciente());

            // Ejecutar el procedimiento almacenado
            cst.executeUpdate();

            // Obtener el idPago generado
            idPagoGenerado = cst.getInt(1);
            pago.setIdPago(idPagoGenerado);

            // Asignar valores adicionales al objeto Pago
            pago.setEstado(true);
            pago.setFechaPago(java.sql.Date.valueOf(LocalDate.now()));

        } catch (SQLException e) {
            System.out.println("Error al insertar el pago: " + e.getMessage());
        } finally {
            try {
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return idPagoGenerado; // Retorna el idPago generado, o -1 si hubo un error
    }

        
    @Override
    public int eliminar(int idPago){
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call PagoEliminar(?)}";
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PagoListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Pago pago = new Pago();
                pago.setIdPago(rs.getInt("idPago"));
                pago.setDescuentoPorSeguro(rs.getDouble("descuentoPorSeguro"));
                pago.setMontoParcial(rs.getDouble("montoParcial"));
                pago.setMontoTotal(rs.getDouble("montoTotal"));
                pago.setFechaPago(rs.getDate("fechaPago"));
                pago.setConcepto(rs.getString("concepto"));
                pago.setEstado(rs.getBoolean("estado"));
                pago.setIdPaciente(rs.getInt("idPaciente"));
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
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL PagoListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPago);
            rs = cst.executeQuery();
            if (rs.next()) {
                pago = new Pago();
                pago.setIdPago(rs.getInt("idPago"));
                pago.setDescuentoPorSeguro(rs.getDouble("descuentoPorSeguro"));
                pago.setMontoParcial(rs.getDouble("montoParcial"));
                pago.setMontoTotal(rs.getDouble("montoTotal"));
                pago.setFechaPago(rs.getDate("fechaPago"));
                pago.setConcepto(rs.getString("concepto"));
                pago.setEstado(rs.getBoolean("estado"));
                pago.setIdPaciente(rs.getInt("idPaciente"));
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
