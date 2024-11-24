package pe.edu.pucp.capsuleCare.medical.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import pe.edu.pucp.capsuleCare.medical.dao.ComunicacionDAO;

import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;
import pe.edu.pucp.citamedica.model.comunicacion.EstadoComunicacion;
import pe.edu.pucp.citamedica.model.comunicacion.TipoComunicacion;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class ComunicacionMySQL implements ComunicacionDAO {
    
    private Connection con;
    private PreparedStatement pstComunica;
    private String sql;
    private ResultSet rs;
    private PreparedStatement pst;

    @Override
    public int insertar(Comunicacion comunicacion) {
        int resultado = 0;
        
        String sql = "CALL COMUNICACION_INSERTAR(?,?,?,?,?,?)";
        
        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pstComunica = con.prepareStatement(sql)){
           
            
            pstComunica.setString(1, comunicacion.getTipo().name());
            pstComunica.setString(2, comunicacion.getContenido());
            Date sqlDate = new Date(comunicacion.getFechaComunicacion().getTime());
            pstComunica.setDate(3, sqlDate);
            pstComunica.setInt(4, comunicacion.getIdPaciente());
            pstComunica.setString(5, comunicacion.getEstado().name());
            pstComunica.setString(6, comunicacion.getRespuesta());
            
            resultado = pstComunica.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Comunicacion comunicacion) {
        int resultado = 0;
        sql = "CALL COMUNICACION_ACTUALIZAR(?,?,?,?,?,?,?,?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pstComunica = con.prepareStatement(sql)) {

            pstComunica.setInt(1, comunicacion.getIdComunicacion());
            pstComunica.setString(2, comunicacion.getTipo().name());
            pstComunica.setString(3, comunicacion.getContenido());
            Date sqlDate = new Date(comunicacion.getFechaComunicacion().getTime());
            pstComunica.setDate(4, sqlDate);
            pstComunica.setBoolean(5, comunicacion.isActivo());
            pstComunica.setInt(6, comunicacion.getIdPaciente());
            pstComunica.setString(7, comunicacion.getEstado().name());
            pstComunica.setString(8, comunicacion.getRespuesta());

            resultado = pstComunica.executeUpdate();
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public ArrayList<Comunicacion> listarTodos() {
        ArrayList<Comunicacion> listaComunicacion = new ArrayList<>();
        String sql = "CALL COMUNICACION_LISTAR_TODOS()";

        try (Connection con = DBPoolManager.getInstance().getConnection(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Comunicacion comunica = new Comunicacion();

                // Asignar valores desde la base de datos al objeto Comunicación
                comunica.setIdComunicacion(rs.getInt("idComunicacion"));
                comunica.setTipo(TipoComunicacion.valueOf(rs.getString("tipoComunicacion"))); // Siempre tiene valor
                comunica.setContenido(rs.getString("contenido"));
                comunica.setFechaComunicacion(rs.getDate("fechaComunicacion"));
                comunica.setActivo(rs.getBoolean("activo"));
                comunica.setIdPaciente(rs.getInt("idPaciente"));
                comunica.setEstado(EstadoComunicacion.valueOf(rs.getString("estado"))); // Siempre tiene valor

                // Manejar el campo 'respuesta' si es nulo
                String respuesta = rs.getString("respuesta");
                comunica.setRespuesta(respuesta != null ? respuesta : "RESPUESTA PENDIENTE");

                // Agregar la comunicación a la lista
                listaComunicacion.add(comunica);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar todas las comunicaciones: " + e.getMessage());
        }

        return listaComunicacion;
    }

    
    @Override
    public Comunicacion obtenerPorId(int idComunicacion) {
        Comunicacion comunica = null;
        sql = "CALL COMUNICACION_BUSCARPORID(?)";

        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pstComunicacion = con.prepareStatement(sql);
                ResultSet rs = pstComunicacion.executeQuery();) {

            pstComunicacion.setInt(1, idComunicacion);
           

            if (rs.next()) {
                comunica = new Comunicacion();
                comunica.setIdComunicacion(rs.getInt("idComunicacion"));
                comunica.setTipo(TipoComunicacion.valueOf(rs.getString("tipoComunicacion")));
                comunica.setContenido(rs.getString("contenido"));
                
                Date sqlDate = rs.getDate("fechaComunicacion");
                comunica.setFechaComunicacion(new java.util.Date(sqlDate.getTime()));
                
                comunica.setActivo(rs.getBoolean("activo"));
                comunica.setIdPaciente(rs.getInt("idPaciente"));
                comunica.setEstado(EstadoComunicacion.valueOf(rs.getString("estado")));
                comunica.setRespuesta(rs.getString("respuesta"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comunica;
    }
    @Override
    public ArrayList<Comunicacion> listarPorPaciente(int idPaciente) {
        ArrayList<Comunicacion> listaComunicacion = new ArrayList<>();
        String sql = "CALL COMUNICACION_LISTAR_POR_PACIENTE(?)";

        try (Connection con = DBPoolManager.getInstance().getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idPaciente);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Comunicacion comunica = new Comunicacion();
                comunica.setIdComunicacion(rs.getInt("idComunicacion"));
                comunica.setTipo(TipoComunicacion.valueOf(rs.getString("tipoComunicacion")));
                comunica.setContenido(rs.getString("contenido"));
                comunica.setFechaComunicacion(rs.getDate("fechaComunicacion"));
                comunica.setActivo(rs.getBoolean("activo"));
                comunica.setIdPaciente(rs.getInt("idPaciente"));
                comunica.setEstado(EstadoComunicacion.valueOf(rs.getString("estado")));
                comunica.setRespuesta(rs.getString("respuesta"));
                listaComunicacion.add(comunica);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar comunicaciones por paciente: " + e.getMessage());
        }
        return listaComunicacion;
    }

    @Override
    public ArrayList<Comunicacion> listarComunicacionesFiltradas(String tipo, String estado, java.util.Date fechaInicio, java.util.Date fechaFin, Integer idPaciente) {
        ArrayList<Comunicacion> listaComunicacion = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Comunicacion WHERE activo = 1"); // Solo incluir comunicaciones activas

        // Construir la consulta dinámica según los filtros proporcionados
        if (idPaciente != null && idPaciente != 0) {
            sql.append(" AND idPaciente = ?");
        }
        if (tipo != null && !tipo.isEmpty()) {
            sql.append(" AND tipoComunicacion = ?");
        }
        if (estado != null && !estado.isEmpty()) {
            sql.append(" AND estado = ?");
        }
        if (fechaInicio != null) {
            sql.append(" AND fechaComunicacion >= ?");
        }
        if (fechaFin != null) {
            sql.append(" AND fechaComunicacion <= ?");
        }

        try (Connection con = DBPoolManager.getInstance().getConnection(); PreparedStatement pstComunicacion = con.prepareStatement(sql.toString())) {

            // Asignar los parámetros al PreparedStatement de forma dinámica
            int paramIndex = 1;

            if (idPaciente != null && idPaciente != 0) {
                pstComunicacion.setInt(paramIndex++, idPaciente);
            }
            if (tipo != null && !tipo.isEmpty()) {
                pstComunicacion.setString(paramIndex++, tipo.toUpperCase());
            }
            if (estado != null && !estado.isEmpty()) {
                pstComunicacion.setString(paramIndex++, estado.toUpperCase());
            }
            if (fechaInicio != null) {
                pstComunicacion.setDate(paramIndex++, new java.sql.Date(fechaInicio.getTime()));
            }
            if (fechaFin != null) {
                pstComunicacion.setDate(paramIndex++, new java.sql.Date(fechaFin.getTime()));
            }

            try (ResultSet rs = pstComunicacion.executeQuery()) {
                while (rs.next()) {
                    Comunicacion comunica = new Comunicacion();

                    // Asignar los valores recuperados del ResultSet al objeto Comunicacion
                    comunica.setIdComunicacion(rs.getInt("idComunicacion"));
                    comunica.setContenido(rs.getString("contenido"));
                    comunica.setActivo(rs.getBoolean("activo"));
                    comunica.setIdPaciente(rs.getInt("idPaciente"));

                    // Manejar tipoComunicacion
                    String tipoComunicacion = rs.getString("tipoComunicacion");
                    comunica.setTipo(tipoComunicacion != null ? TipoComunicacion.valueOf(tipoComunicacion.toUpperCase()) : null);

                    // Manejar estado
                    String estadoComunicacion = rs.getString("estado");
                    comunica.setEstado(estadoComunicacion != null ? EstadoComunicacion.valueOf(estadoComunicacion.toUpperCase()) : null);

                    // Manejar fechaComunicacion
                    java.sql.Date sqlDate = rs.getDate("fechaComunicacion");
                    comunica.setFechaComunicacion(sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null);

                    // Manejar respuesta
                    String respuesta = rs.getString("respuesta");
                    comunica.setRespuesta(respuesta != null ? respuesta : "RESPUESTA PENDIENTE");

                    listaComunicacion.add(comunica);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

        return listaComunicacion;
    }
    
    @Override
    public ArrayList<String[]> obtenerConteoPorEstado() {
        ArrayList<String[]> conteoPorEstado = new ArrayList<>();
        String sql = "CALL COMUNICACION_CONTAR_POR_ESTADO()"; // Llamada al procedimiento almacenado

        try (Connection con = DBPoolManager.getInstance().getConnection(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            // Procesar los resultados del ResultSet
            while (rs.next()) {
                String estado = rs.getString("estado");
                String conteo = String.valueOf(rs.getInt("conteo"));
                conteoPorEstado.add(new String[]{estado, conteo});
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el conteo por estado: " + e.getMessage());
            e.printStackTrace();
        }

        return conteoPorEstado;
    }
    
    








    
}