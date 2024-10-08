package pe.edu.pucp.citamedica.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.ComunicacionDAO;
import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;
import pe.edu.pucp.citamedica.model.comunicacion.TipoComunicacion;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class ComunicacionMySQL implements ComunicacionDAO{
    
    private Connection con;
    private PreparedStatement pstComunica;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Comunicacion comunicacion) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "CALL COMUNICACION_INSERTAR(?,?,?,?)";
            pstComunica = con.prepareStatement(sql);
            pstComunica.setString(1, comunicacion.getTipo().name());
            pstComunica.setString(2, comunicacion.getContenido());
            java.sql.Date sqlDate = new java.sql.Date(comunicacion.getFechaComunicacion().getTime());
            pstComunica.setDate(3, sqlDate);
            pstComunica.setInt(4,comunicacion.getIdPaciente());
            resultado = pstComunica.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Comunicacion comunicacion) {
        int resultado = 0;
        sql = "CALL COMUNICACION_ACTUALIZAR(?,?,?,?,?,?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pstComunica = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pstComunica.setInt(1,comunicacion.getIdComunicacion());
            pstComunica.setString(2, comunicacion.getTipo().name());
            pstComunica.setString(3, comunicacion.getContenido());
            java.sql.Date sqlDate = new java.sql.Date(comunicacion.getFechaComunicacion().getTime());
            pstComunica.setDate(4, sqlDate);
            pstComunica.setBoolean(5,comunicacion.isActivo());
            pstComunica.setInt(6, comunicacion.getIdPaciente());
            // Ejecutar la consulta de actualización
            resultado = pstComunica.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Médico modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún médico con ese ID.");
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return resultado;
    }

    @Override
    public int eliminar(int idComunicacion) {
        int resultado = 0;
        sql = "CALL COMUNICACION_ELIMINAR(?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();
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
        sql = "{CALL COMUNICACION_LISTAR_TODOS()}";
        try (Connection con = DBPoolManager.getInstance().getConnection();
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
                comunica.setActivo(rs.getBoolean("activo"));
                comunica.setIdPaciente(rs.getInt("idPaciente"));
                //Añadir el objeto Comunicacion a la lista
                listaComunicacion.add(comunica);
            }

        } catch (SQLException e) {
            // Manejar la excepción si ocurre un error
            System.out.println(e.getMessage());
        }
        return listaComunicacion;  // Retornar la lista de comunicacion
    }

    @Override
    public Comunicacion obtenerPorId(int idComunicacion) {
        Comunicacion comunica = null;
        sql = "CALL COMUNICACION_BUSCARPORID(?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();
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
                comunica.setActivo(rs.getBoolean("activo"));
                comunica.setIdPaciente(rs.getInt("idPaciente"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return comunica;
    } 
}
