package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.consultas.model.HistorialMedico;
import pe.edu.pucp.citamedica.dao.HistorialMedicoDAO;
import pe.edu.pucp.dbmanager.config.DBManager;

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
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into HistorialMedico(fechaCreacion,numeroDocumentoIdentidadPaciente) "
                    + "values(?,?)";
            pst = con.prepareStatement(sql);
            java.sql.Date sqlDate = new java.sql.Date(historial.getFechaDeCreacion().getTime());
            pst.setDate(1, sqlDate);
            pst.setString(2, historial.getNumeroDocumentoIdentidadPaciente());
            resultado = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
            e.printStackTrace();
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
            pstHistorial.setString(2, historial.getNumeroDocumentoIdentidadPaciente());
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
        sql = "DELETE FROM HistorialMedico WHERE idHistorialMedico = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstHistorial = con.prepareStatement(sql)) {

            pstHistorial.setInt(1, idHistorial);

            resultado = pstHistorial.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("La fila de la tabla HistorialMedico se ha eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningun historial medico con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public ArrayList<HistorialMedico> listarTodos() {
        ArrayList<HistorialMedico> listaHistorial = new ArrayList<>();
        String sql = "SELECT * FROM HistorialMedico";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstHistorial = con.prepareStatement(sql);
             ResultSet rs = pstHistorial.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto HistorialMedico
                HistorialMedico historial = new HistorialMedico();
                historial.setIdHistorial(rs.getInt("idHistorialMedico"));
                historial.setNumeroDocumentoIdentidadPaciente(rs.getString("numeroDocumentoIdentidadPaciente"));
                //Obtener la fecha de la base de datos
                java.sql.Date sqlDate = rs.getDate("fechaCreacion");
                //Convertir java.sql.Date a java.util.Date
                java.util.Date fecha = new java.util.Date(sqlDate.getTime());
                historial.setFechaDeCreacion(fecha);
                //Añadir el objeto HistorialMedico a la lista
                listaHistorial.add(historial);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }
        return listaHistorial;  // Retornar la lista de médicos
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
                historial.setNumeroDocumentoIdentidadPaciente(rs.getString("numeroDocumentoIdentidadPaciente"));
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
