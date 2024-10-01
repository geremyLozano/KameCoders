package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.HistorialMedicoDAO;
import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class HistorialMedicoMySQL implements HistorialMedicoDAO{
    
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(HistorialMedico historialorial) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL HistorialMedicoInsertar(?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.setInt(2, historialorial.getIdPaciente());
            resultado = cst.executeUpdate();
            historialorial.setIdHistorial(cst.getInt(1));
            historialorial.setActivo(true);
        } catch (SQLException e) {
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
            System.out.print("Error general" + e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(HistorialMedico historialorial) {
        int resultado = 0;
        sql = "UPDATE HistorialMedico SET fechaCreacion = ?, "
                + "numeroDocumentoIdentidadPaciente = ? " + " WHERE idHistorialMedico = ?";

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pstHistorial = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            java.sql.Date sqlDate = new java.sql.Date(historialorial.getFechaDeCreacion().getTime());
            pstHistorial.setDate(1, sqlDate);
            pstHistorial.setInt(2, historialorial.getIdPaciente());
            pstHistorial.setInt(3, historialorial.getIdHistorial());
            // Ejecutar la consulta de actualización
            resultado = pstHistorial.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("HistorialMedico modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún historialorial con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir la excepción si ocurre un error
        }

        return resultado;
    }

    @Override
    public int eliminar(int idHistorial) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call HistorialMedicoEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idHistorial);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public ArrayList<HistorialMedico> listarTodos() {
        ArrayList<HistorialMedico> listaHistorial = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "{CALL HistorialMedicoListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                HistorialMedico historial = new HistorialMedico();
                historial.setIdHistorial(rs.getInt("idHistorialMedico"));
                historial.setFechaDeCreacion(rs.getDate("fechaCreacion"));
                historial.setActivo(rs.getBoolean("activo"));
                historial.setIdPaciente(rs.getInt("idPaciente"));
                listaHistorial.add(historial);
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
        return listaHistorial;
    }

    @Override
    public HistorialMedico obtenerPorId(int idHistorial) {
        HistorialMedico historial = null;
        sql = "SELECT * FROM HistorialMedico WHERE idHistorialMedico = ?";

        try {
            con = DBPoolManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "{CALL HistorialMedicoListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idHistorial);
            rs = cst.executeQuery();
            if (rs.next()) {
                historial = new HistorialMedico();
                historial.setIdHistorial(rs.getInt("idHistorialMedico"));
                historial.setFechaDeCreacion(rs.getDate("fechaCreacion"));
                historial.setActivo(rs.getBoolean("activo"));
                historial.setIdPaciente(rs.getInt("idPaciente"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return historial;
    }
    
}
