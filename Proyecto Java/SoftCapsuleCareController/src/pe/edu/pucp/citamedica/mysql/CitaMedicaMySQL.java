package pe.edu.pucp.citamedica.mysql;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.EstadoCita;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;
import pe.edu.pucp.dbmanager.config.DBManager;

public class CitaMedicaMySQL implements CitaMedicaDAO {
    private Connection con;
    private PreparedStatement pstProcedimiento;
    private PreparedStatement pstCitaMedica;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;
    @Override
    public int insertar(CitaMedica cita) {
        int resultado = 0;

        try {
            con = DBManager.getInstance().getConnection();

            // Llamamos al procedimiento almacenado
            String sql = "{CALL sp_insertar_cita_medica(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Seteamos los parámetros del procedimiento
            cst.setString(1, cita.getTipo().name()); // TipoCita es un enum
            cst.setString(2, cita.getEstado().name()); // EstadoCita es un enum
            cst.setDate(3, new java.sql.Date(cita.getFecha().getTime()));
            cst.setTime(4, java.sql.Time.valueOf(cita.getHora()));
            cst.setInt(5, cita.getIdMedico());
            cst.setInt(6, cita.getIdPaciente());
            cst.setString(7, cita.getPlataforma());
            cst.setString(8, cita.getEnlace());
            cst.setTime(9, java.sql.Time.valueOf(cita.getDuracion()));
            cst.setInt(10, cita.getNumeroAmbiente());
            cst.setInt(11, cita.getIdPago());

            // Ejecutamos el procedimiento
            cst.execute();
            resultado = 1; // Si todo va bien, retornamos éxito

        } catch (SQLException e) {
            System.out.println("Error al insertar la cita médica: " + e.getMessage());
        } finally {
            try {
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return resultado;
    }


    @Override
    public int modificar(CitaMedica citaMedica) {
        int resultado = 0;
        Connection con = null;
        CallableStatement cst = null;
        String sql = "{CALL sp_modificar_cita_medica(?, ?, ?, ?)}";

        try {
            // Obtener la conexión desde el DBManager.
            con = DBManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado.
            cst = con.prepareCall(sql);

            // Establecer los parámetros para el procedimiento.
            cst.setInt(1, Integer.parseInt(citaMedica.getIdCitaMedica()));  // Convertir el ID si es String
            cst.setString(2, citaMedica.getEstado().name());                 // Asumimos que EstadoCita es un enum
            cst.setDate(3, new java.sql.Date(citaMedica.getFecha().getTime()));
            cst.setTime(4, java.sql.Time.valueOf(citaMedica.getHora()));

            // Ejecutar el procedimiento almacenado.
            resultado = cst.executeUpdate();

        } catch (SQLException e) {
            // Manejar cualquier excepción SQL.
            System.out.println("Error al modificar la cita médica: " + e.getMessage());
        } finally {
            // Cerrar los recursos de base de datos.
            try {
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public int eliminar(int idCitaMedica) {
        int resultado = 0;
        Connection con = null;
        CallableStatement cst = null;
        String sql = "{CALL sp_eliminar_cita_medica(?)}";  // Procedimiento almacenado

        try {
            // Obtener la conexión a la base de datos
            con = DBManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado
            cst = con.prepareCall(sql);

            // Establecer el valor del parámetro (ID de la cita médica)
            cst.setInt(1, idCitaMedica);

            // Ejecutar la consulta
            resultado = cst.executeUpdate();

        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL
            System.out.println("Error al eliminar cita médica: " + e.getMessage());
        } finally {
            // Cerrar los recursos de base de datos
            try {
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        // Devolver el resultado
        return resultado;
    }

    
    @Override
    public ArrayList<CitaMedica> listarTodos() {
        ArrayList<CitaMedica> citasMedicas = new ArrayList<>();
        Connection con = null;
        CallableStatement cst = null;
        ResultSet rs = null;
        String sql = "{CALL sp_listar_todas_citas()}";  // Procedimiento almacenado

        try {
            // Obtener la conexión a la base de datos
            con = DBManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado
            cst = con.prepareCall(sql);

            // Ejecutar la consulta
            rs = cst.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                CitaMedica citaMedica = new CitaMedica();

                // Asignar valores a los atributos de CitaMedica desde el ResultSet
                citaMedica.setIdCitaMedica(rs.getString("idCitaMedica"));
                citaMedica.setFecha(rs.getDate("fecha"));
                citaMedica.setHora(rs.getTime("hora").toLocalTime());

                // Convertir el valor del estado de String a EstadoCita
                String estadoStr = rs.getString("estadoCita");
                EstadoCita estado = EstadoCita.valueOf(estadoStr.toUpperCase());  // Conversión a enum
                citaMedica.setEstado(estado);

                // Añadir la CitaMedica a la lista
                citasMedicas.add(citaMedica);
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL
            System.out.println("Error al listar citas médicas: " + e.getMessage());
        } finally {
            // Cerrar los recursos de base de datos
            try {
                if (rs != null) rs.close();
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }

        // Devolver la lista de citas médicas
        return citasMedicas;
    }

    @Override
    public CitaMedica obtenerPorId(int idCitaMedica) {
        CitaMedica citaMedica = null;
        Connection con = null;
        CallableStatement cst = null;
        ResultSet rs = null;
        String sql = "{CALL sp_obtener_cita_medica_por_id(?)}";  // Procedimiento almacenado

        try {
            // Obtener la conexión a la base de datos
            con = DBManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado
            cst = con.prepareCall(sql);
            cst.setInt(1, idCitaMedica);  // Establecer el parámetro de entrada (ID de la cita)

            // Ejecutar la consulta
            rs = cst.executeQuery();

            // Si se encuentra el registro
            if (rs.next()) {
                citaMedica = new CitaMedica();

                // Asignar valores desde el ResultSet a la CitaMedica
                citaMedica.setIdCitaMedica(rs.getString("idCitaMedica"));
                citaMedica.setFecha(rs.getDate("fecha"));
                citaMedica.setHora(rs.getTime("hora").toLocalTime());

                // Convertir el estado de la cita (asumiendo que es un enum)
                String estadoStr = rs.getString("estadoCita");
                EstadoCita estado = EstadoCita.valueOf(estadoStr.toUpperCase());
                citaMedica.setEstado(estado);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener cita médica: " + e.getMessage());
        } finally {
            // Cerrar los recursos de base de datos
            try {
                if (rs != null) rs.close();
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }

        // Devolver la cita médica o null si no se encontró
        return citaMedica;
    }

    
}
