package pe.edu.pucp.citamedica.mysql;

import java.sql.Timestamp;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import pe.edu.pucp.citamedica.dao.TokenDAO;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class TokenMySQL implements TokenDAO{
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int crear(String email, String token, Date expiracion) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL TokenGuardar(?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setString(1, email);
            cst.setString(2, token);
            cst.setTimestamp(3, new Timestamp(expiracion.getTime()));
            resultado = cst.executeUpdate();
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int validarToken(String token) {
        int resultado = -1;
        int esValido = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            String sql = "{CALL TokenValidar(?, ?)}";
            cst = con.prepareCall(sql);

            cst.setString(1, token);

            cst.registerOutParameter(2, java.sql.Types.INTEGER);

            cst.execute();

            esValido = cst.getInt(2);

            resultado = esValido;

        } catch (SQLException e) {
            System.err.println("Error al validar el token: " + e.getMessage());
        } finally {
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
        return resultado;
    }
}
