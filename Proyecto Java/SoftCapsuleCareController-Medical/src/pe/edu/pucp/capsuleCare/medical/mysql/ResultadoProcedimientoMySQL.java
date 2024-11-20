package pe.edu.pucp.capsuleCare.medical.mysql;


import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import pe.edu.pucp.dbmanager.config.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import pe.edu.pucp.capsuleCare.medical.dao.ResultadoProcedimientoDAO;
import pe.edu.pucp.citamedica.model.procedimiento.EstadoResultado;
import pe.edu.pucp.citamedica.model.procedimiento.ResultadoProcedimiento;

public class ResultadoProcedimientoMySQL implements ResultadoProcedimientoDAO{
    private Connection con;
    private PreparedStatement pst;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(ResultadoProcedimiento resPro) {
        int resultado = 0;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into ResultadoProcedimiento(idProcedimiento, observaciones, estado, fechaResultado) "
                    + "values(?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, resPro.getProcedimiento().getIdProcedimiento());
            pst.setString(2, resPro.getObservaciones());
            pst.setString(3, resPro.getEstado().toString());
            java.sql.Date sqlDate = new java.sql.Date(resPro.getFechaResultado().getTime());
            pst.setDate(4, sqlDate);

            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();  // Cerrar PreparedStatement
                }
                if (con != null) {
                    con.close();  // Cerrar Connection
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
    
    @Override
    public int modificar(ResultadoProcedimiento resPro) {
        int resultado = 0;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DBManager.getInstance().getConnection();
            sql = "UPDATE ResultadoProcedimiento SET estado = ? "
                    + "WHERE idResultadoProcedimiento = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, resPro.getEstado().toString());
            pst.setInt(2, resPro.getIdResultado());
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();  // Cerrar PreparedStatement
                }
                if (con != null) {
                    con.close();  // Cerrar Connection
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
    
    @Override
    public int eliminar(int idResultadoProcedimiento) {
        int resultado = 0;
        Connection con = null;
        CallableStatement cst = null;

        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call RESULTADOPROCEDIMIENTO_ELIMINAR(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idResultadoProcedimiento);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (cst != null) {
                    cst.close();  // Cerrar CallableStatement
                }
                if (con != null) {
                    con.close();  // Cerrar Connection
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
    
    @Override
    public ArrayList<ResultadoProcedimiento> listarTodos(){
        ArrayList<ResultadoProcedimiento> resultados = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT * FROM ResultadoProcedimiento";
            rs = st.executeQuery(sql);
            while(rs.next()){
                ResultadoProcedimiento resultado = new ResultadoProcedimiento();
                resultado.setEstado(EstadoResultado.valueOf(rs.getString("estado")));
                resultado.setFechaResultado(rs.getDate("fechaResultado"));
                resultado.setIdResultado(rs.getInt("idResultadoProcedimiento"));
                resultado.setObservaciones(rs.getString("observaciones"));
                //resultado.setProcedimiento();
                resultados.add(resultado);
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
        return resultados;
    }
    
    @Override
    public ResultadoProcedimiento obtenerPorId(int idResultadoProcedimiento){
        ResultadoProcedimiento resultado = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT idResultadoProcedimiento, estado FROM ResultadoProcedimiento"
                    + " WHERE idResultadoProcedimiento = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idResultadoProcedimiento);
            rs = pst.executeQuery();

            if (rs.next()) {
                resultado = new ResultadoProcedimiento();
                resultado.setIdResultado(rs.getInt("idResultadoProcedimiento"));
                resultado.setEstado(EstadoResultado.valueOf(rs.getString("estado")));
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
        return resultado;
    }
}
