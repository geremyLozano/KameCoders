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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.EstadoCita;
import pe.edu.pucp.citamedica.model.consultas.TipoCita;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

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
            con = DBPoolManager.getInstance().getConnection();
            String sql = "{CALL sp_insertar_cita_medica(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            
            LocalTime horaLocalTime;
            LocalTime duracionLocalTime;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaLocalDate = LocalDate.parse(cita.getFechaStr(), dateFormatter);
            java.sql.Date fecha = java.sql.Date.valueOf(fechaLocalDate);
            
            
            if (cita.getHoraStr().length() == 5) { // Formato "HH:mm"
                horaLocalTime = LocalTime.parse(cita.getHoraStr(), DateTimeFormatter.ofPattern("HH:mm"));
            } else { // Formato "HH:mm:ss"
                horaLocalTime = LocalTime.parse(cita.getHoraStr(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            }

            if (cita.getDuracionStr().length() == 5) { // Formato "HH:mm"
                duracionLocalTime = LocalTime.parse(cita.getDuracionStr(), DateTimeFormatter.ofPattern("HH:mm"));
            } else { // Formato "HH:mm:ss"
                duracionLocalTime = LocalTime.parse(cita.getDuracionStr(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
            
            java.sql.Time hora = java.sql.Time.valueOf(horaLocalTime);
            java.sql.Time duracion = java.sql.Time.valueOf(duracionLocalTime);

            // Seteamos los parámetros del procedimiento
            cst.setNull(1,java.sql.Types.VARCHAR);
            cst.setString(2,EstadoCita.Pendiente.toString()); // EstadoCita es un enum
            cst.setDate(3,fecha);
            cst.setTime(4,hora);
            cst.setInt(5, cita.getIdMedico());
            cst.setInt(6, cita.getIdPaciente());
            cst.setNull(7, java.sql.Types.VARCHAR);
            cst.setNull(8, java.sql.Types.VARCHAR);
            cst.setTime(9,duracion);
            cst.setNull(10, java.sql.Types.INTEGER);
            cst.setInt(11, cita.getIdPago());

            // Ejecutamos el procedimiento
            cst.executeUpdate();
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
            con = DBPoolManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado.
            cst = con.prepareCall(sql);

            // Establecer los parámetros para el procedimiento.
            cst.setInt(1, citaMedica.getIdCitaMedica());  // Convertir el ID si es String
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
            con = DBPoolManager.getInstance().getConnection();

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
            con = DBPoolManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado
            cst = con.prepareCall(sql);

            // Ejecutar la consulta
            rs = cst.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                CitaMedica citaMedica = new CitaMedica();

                // Asignar valores a los atributos de CitaMedica desde el ResultSet
                citaMedica.setIdCitaMedica(rs.getInt("idCitaMedica"));
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
        String sql = "{CALL ObtenerCitaMedicaPorId(?)}";  // Procedimiento almacenado

        try {
            // Obtener la conexión a la base de datos
            con = DBPoolManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado
            cst = con.prepareCall(sql);
            cst.setInt(1, idCitaMedica);  // Establecer el parámetro de entrada (ID de la cita)

            // Ejecutar la consulta
            rs = cst.executeQuery();

            // Si se encuentra el registro
            if (rs.next()) {
                citaMedica = new CitaMedica();

                // Asignar valores desde el ResultSet a la CitaMedica
                citaMedica.setIdCitaMedica(rs.getInt("idCitaMedica"));
                citaMedica.setTipoStr(rs.getString("tipoCita"));
                citaMedica.setEstadoStr(rs.getString("estadoCita"));
                citaMedica.setIdHistorialMedico(rs.getInt("idHistorialMedico"));
                // Convertir el estado de la cita (asumiendo que es un enum)
                EstadoCita estado = EstadoCita.valueOf(citaMedica.getEstadoStr());
                citaMedica.setEstado(estado);
                citaMedica.setIdMedico(rs.getInt("idMedico"));
                citaMedica.setFechaStr(rs.getDate("fecha").toString());
                citaMedica.setHoraStr(rs.getTime("hora").toString());
                citaMedica.setPlataforma(rs.getString("plataforma"));
                citaMedica.setPlataforma(rs.getString("enlace"));
                citaMedica.setDuracionStr(rs.getTime("duracion").toString());
                citaMedica.setNumeroAmbiente(rs.getInt("numeroAmbiente"));
                citaMedica.setIdPago(rs.getInt("idPago"));
                citaMedica.setActivo(rs.getBoolean("activo"));
                citaMedica.setIdPaciente(rs.getInt("idPaciente"));
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
    
    @Override
    public ArrayList<CitaMedica> listarPorPaciente(int idPaciente) {
        ArrayList<CitaMedica> listaCitas = new ArrayList<>();
        Connection con = null;
        CallableStatement cst = null;
        ResultSet rs = null;
        String sql = "{CALL sp_listar_citas_por_idPaciente(?)}";  // Procedimiento almacenado

        try {
            // Obtener la conexión a la base de datos
            con = DBPoolManager.getInstance().getConnection();

            // Preparar la llamada al procedimiento almacenado
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);  // Establecer el parámetro de entrada (ID del paciente)

            // Ejecutar la consulta
            rs = cst.executeQuery();

            // Iterar sobre el ResultSet y agregar cada cita a la lista
            while (rs.next()) {
                CitaMedica citaMedica = new CitaMedica();

                // Asignar valores desde el ResultSet a la CitaMedica
                citaMedica.setIdCitaMedica(rs.getInt("idCitaMedica"));

                // Verificar si tipoCita es null antes de convertirlo al enum
                String tipoCitaStr = rs.getString("tipoCita");
                if (tipoCitaStr != null) {
                    citaMedica.setTipo(TipoCita.valueOf(tipoCitaStr));
                } else {
                    // Puedes asignar un valor predeterminado o dejarlo como null si es opcional
                    citaMedica.setTipo(TipoCita.NO_ASIGNADO); // Suponiendo que tienes un valor DEFAULT en TipoCita
                }

                citaMedica.setEstado(EstadoCita.valueOf(rs.getString("estadoCita")));
                citaMedica.setIdHistorialMedico(rs.getInt("idHistorialMedico"));
                citaMedica.setIdMedico(rs.getInt("idMedico"));
                citaMedica.setFecha(rs.getDate("fecha"));
                citaMedica.setHora(rs.getTime("hora").toLocalTime());
                citaMedica.setPlataforma(rs.getString("plataforma"));
                citaMedica.setEnlace(rs.getString("enlace"));
                citaMedica.setDuracion(rs.getTime("duracion").toLocalTime());
                citaMedica.setNumeroAmbiente(rs.getInt("numeroAmbiente"));
                citaMedica.setIdPago(rs.getInt("idPago"));
                citaMedica.setActivo(true);
                citaMedica.setIdPaciente(rs.getInt("idPaciente"));

                // Agregar la cita a la lista
                listaCitas.add(citaMedica);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar citas médicas por paciente: " + e.getMessage());
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
        return listaCitas;
    }


}
