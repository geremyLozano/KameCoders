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
    public int insertar(HistorialMedico historial) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL HistorialMedicoInsertar(?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.setInt(2, historial.getIdPaciente());
            resultado = cst.executeUpdate();
            historial.setIdHistorial(cst.getInt(1));
            historial.setActivo(true);
        } catch (SQLException e) {
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
            System.out.print("Error general" + e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(HistorialMedico historial) {
        int resultado = 0;
        sql = "UPDATE HistorialMedico SET fechaCreacion = ?, "
                + "numeroDocumentoIdentidadPaciente = ? " + " WHERE idHistorialMedico = ?";

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pstHistorial = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            java.sql.Date sqlDate = new java.sql.Date(historial.getFechaDeCreacion().getTime());
            pstHistorial.setDate(1, sqlDate);
            pstHistorial.setInt(2, historial.getIdPaciente());
            pstHistorial.setInt(3, historial.getIdHistorial());
            // Ejecutar la consulta de actualización
            resultado = pstHistorial.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("HistorialMedico modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún historial con ese ID.");
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
                HistorialMedico hist = new HistorialMedico();
                hist.setIdHistorial(rs.getInt("idHistorialMedico"));
                hist.setFechaDeCreacion(rs.getDate("fechaCreacion"));
                hist.setActivo(rs.getBoolean("activo"));
                hist.setIdPaciente(rs.getInt("idPaciente"));
                listaHistorial.add(hist);
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

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstHistorial = con.prepareStatement(sql)) {

            pstHistorial.setInt(1, idHistorial);
            rs = pstHistorial.executeQuery();

            if (rs.next()) {
                historial = new HistorialMedico();
                historial.setIdHistorial(rs.getInt("idHistorialMedico"));
//                historial.setNumeroDocumentoIdentidadPaciente(rs.getString("numeroDocumentoIdentidadPaciente"));
                java.sql.Date sqlDate = rs.getDate("fechaCreacion");
                //Convertir java.sql.Date a java.util.Date
                java.util.Date fecha = new java.util.Date(sqlDate.getTime());
                historial.setFechaDeCreacion(fecha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }
    
}
