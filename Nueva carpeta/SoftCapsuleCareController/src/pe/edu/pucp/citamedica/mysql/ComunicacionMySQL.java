package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.comunicacion.model.Comunicacion;
import pe.edu.pucp.citamedica.comunicacion.model.TipoComunicacion;
import pe.edu.pucp.citamedica.dao.ComunicacionDAO;
import pe.edu.pucp.dbmanager.config.DBManager;

public class ComunicacionMySQL implements ComunicacionDAO{
    
    private Connection con;
    private Statement st;
    private PreparedStatement pstComunica;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Comunicacion comunicacion) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Comunicacion(tipoComunicacion,contenido,fechaComunicacion) "
                    + "values(?,?,?)";
            pstComunica = con.prepareStatement(sql);
            pstComunica.setString(1, comunicacion.getTipo().name());
            pstComunica.setString(2, comunicacion.getContenido());
            java.sql.Date sqlDate = new java.sql.Date(comunicacion.getFechaComunicacion().getTime());
            pstComunica.setDate(3, sqlDate);
            resultado = pstComunica.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Comunicacion comunicacion) {
        int resultado = 0;
        sql = "UPDATE Comunicacion SET tipoComunicacion = ?, contenido = ?, "
                + "fechaComunicacion = ? " + " WHERE idComunicacion = ?";

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pstComunica = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pstComunica.setString(1, comunicacion.getTipo().name());
            pstComunica.setString(2, comunicacion.getContenido());
            java.sql.Date sqlDate = new java.sql.Date(comunicacion.getFechaComunicacion().getTime());
            pstComunica.setDate(3, sqlDate);
            pstComunica.setInt(4, comunicacion.getIdComunicacion());
            // Ejecutar la consulta de actualización
            resultado = pstComunica.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Médico modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún médico con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir la excepción si ocurre un error
        }

        return resultado;
    }

    @Override
    public int eliminar(int idComunicacion) {
        int resultado = 0;
        sql = "DELETE FROM Comunicacion WHERE idComunicacion = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstComunica = con.prepareStatement(sql)) {

            pstComunica.setInt(1, idComunicacion);

            resultado = pstComunica.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("La fila de la tabla Comunicacion se ha eliminado correctamente.");
            } else {
                System.out.println("No se encontró ninguna queja o sugerencia con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public ArrayList<Comunicacion> listarTodos() {
        ArrayList<Comunicacion> listaComunicacion = new ArrayList<>();
        String sql = "SELECT * FROM Comunicacion";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstComunicacion = con.prepareStatement(sql);
             ResultSet rs = pstComunicacion.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Comunicacion
                Comunicacion comunica = new Comunicacion();
                comunica.setIdComunicacion(rs.getInt("idComunicacion"));
                String tipostr = rs.getString("tipoComunicacion");
                TipoComunicacion tipo = TipoComunicacion.valueOf(tipostr);
                comunica.setTipo(tipo);
                comunica.setContenido(rs.getString("contenido"));
                //Obtener la fecha de la base de datos
                java.sql.Date sqlDate = rs.getDate("fechaComunicacion");
                //Convertir java.sql.Date a java.util.Date
                java.util.Date fecha = new java.util.Date(sqlDate.getTime());
                comunica.setFechaComunicacion(fecha);

                //Añadir el objeto Comunicacion a la lista
                listaComunicacion.add(comunica);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }
        return listaComunicacion;  // Retornar la lista de comunicacion
    }

    @Override
    public Comunicacion obtenerPorId(int idComunicacion) {
        Comunicacion comunica = null;
        sql = "SELECT * FROM Comunicacion WHERE idComunicacion = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstComunicacion = con.prepareStatement(sql)) {

            pstComunicacion.setInt(1, idComunicacion);
            rs = pstComunicacion.executeQuery();

            if (rs.next()) {
                comunica = new Comunicacion();
                comunica.setIdComunicacion(rs.getInt("idComunicacion"));
                String tipostr = rs.getString("tipoComunicacion");
                TipoComunicacion tipo = TipoComunicacion.valueOf(tipostr);
                comunica.setTipo(tipo);
                comunica.setContenido(rs.getString("contenido"));
                java.sql.Date sqlDate = rs.getDate("fechaComunicacion");
                //Convertir java.sql.Date a java.util.Date
                java.util.Date fecha = new java.util.Date(sqlDate.getTime());
                comunica.setFechaComunicacion(fecha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comunica;
    } 
}
