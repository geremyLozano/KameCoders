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
        
        // SQL para insertar los datos de la cita médica
        sql = "INSERT INTO CitaMedica(tipo, estado, fecha, hora, idMedico, idPaciente, plataforma, enlace, duracion, numeroAmbiente, idPago) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        pstCitaMedica = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        // Seteamos los parámetros para la inserción
        pstCitaMedica.setString(1, cita.getTipo().name()); // Asumiendo que TipoCita es un enum
        pstCitaMedica.setString(2, cita.getEstado().name()); // Asumiendo que EstadoCita es un enum
        java.sql.Date sqlDate = new java.sql.Date(cita.getFecha().getTime());
        pstCitaMedica.setDate(3, sqlDate);
        pstCitaMedica.setTime(4, java.sql.Time.valueOf(cita.getHora())); // Conversion de LocalTime a java.sql.Time
        pstCitaMedica.setInt(5, cita.getIdMedico()); // Asumiendo que Medico tiene un id
        pstCitaMedica.setInt(6, cita.getIdPaciente()); // Asumiendo que Paciente tiene un id
        pstCitaMedica.setString(7, cita.getPlataforma());
        pstCitaMedica.setString(8, cita.getEnlace());
        pstCitaMedica.setTime(9, java.sql.Time.valueOf(cita.getDuracion())); // Conversion de LocalTime a java.sql.Time
        pstCitaMedica.setInt(10, cita.getNumeroAmbiente());
        pstCitaMedica.setInt(11, cita.getIdPago()); // Asumiendo que Pago tiene un id
        
        // Ejecutamos la inserción y obtenemos las llaves generadas
        pstCitaMedica.executeUpdate();
        rs = pstCitaMedica.getGeneratedKeys();
        if (rs.next()) {
            cita.setIdCitaMedica(rs.getString(1)); // Guardamos el ID generado de la cita en el objeto
        }
        
        // Inserción adicional para los procedimientos asociados a la cita médica
//        sql = "INSERT INTO ProcedimientoCita(idCitaMedica, idProcedimiento) VALUES (?, ?)";
//        for (Procedimiento proc : cita.getProcedimientos()) {
//            pstProcedimiento = con.prepareStatement(sql);
//            pstProcedimiento.setString(1, cita.getId());
//            pstProcedimiento.setInt(2, proc.getIdProcedimiento()); // Asumiendo que Procedimiento tiene un id
//            pstProcedimiento.executeUpdate();
//        }

        resultado = 1; // Si todo sale bien, retornamos un valor que indica éxito
    } catch (SQLException e) {
        System.out.println("Error al insertar la cita médica: " + e.getMessage());
    } finally {
        // Cerramos los recursos (Connection, PreparedStatement, ResultSet) en el bloque finally
        try {
            if (rs != null) rs.close();
            if (pstCitaMedica != null) pstCitaMedica.close();
            if (pstProcedimiento != null) pstProcedimiento.close();
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
        PreparedStatement pstCitaMedica = null;
        String sql = "UPDATE cita_medica SET estado = ?, fecha = ?, hora = ? WHERE idCitaMedica = ?";

        try {
            // Obtener la conexión desde el DBManager o tu gestor de conexiones.
            con = DBManager.getInstance().getConnection();

            // Preparar la consulta SQL.
            pstCitaMedica = con.prepareStatement(sql);

            // Establecer los valores para los parámetros de la consulta.
            pstCitaMedica.setString(1, citaMedica.getEstado().name());
            pstCitaMedica.setDate(2, new java.sql.Date(citaMedica.getFecha().getTime()));
            pstCitaMedica.setTime(3, java.sql.Time.valueOf(citaMedica.getHora()));
            pstCitaMedica.setString(4, citaMedica.getIdCitaMedica());

            // Ejecutar la consulta.
            resultado = pstCitaMedica.executeUpdate();

        } catch (SQLException e) {
            // Manejar cualquier excepción de SQL.
            System.out.println("Error al modificar cita médica: " + e.getMessage());
        } finally {
            // Cerrar recursos de base de datos.
            try {
                if (pstCitaMedica != null) pstCitaMedica.close();
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
        String sql = "{call CITA_MEDICA_ELIMINAR(?)}";  // Procedimiento almacenado

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
    Statement st = null;
    ResultSet rs = null;
    String sql = "SELECT idCitaMedica, fecha, hora, estado FROM cita_medica";

    try {
        // Obtener la conexión a la base de datos
        con = DBManager.getInstance().getConnection();
        
        // Crear el Statement para ejecutar la consulta
        st = con.createStatement();
        
        // Ejecutar la consulta y obtener el resultado
        rs = st.executeQuery(sql);
        
        // Procesar los resultados
        while (rs.next()) {
            CitaMedica citaMedica = new CitaMedica();
            
            // Asignar valores a los atributos de CitaMedica desde el ResultSet
            citaMedica.setIdCitaMedica(rs.getString("idCitaMedica"));
            citaMedica.setFecha(rs.getDate("fecha"));
            citaMedica.setHora(rs.getTime("hora").toLocalTime());
            // Convertir el valor del estado de String a EstadoCita
            String estadoStr = rs.getString("estado");
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
            if (st != null) st.close();
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
    PreparedStatement pstCitaMedica = null;
    ResultSet rs = null;
    String sql = "SELECT idCitaMedica, fecha, hora, estado FROM cita_medica WHERE idCitaMedica = ?";

    try {
        // Obtener la conexión a la base de datos
        con = DBManager.getInstance().getConnection();
        
        // Preparar la consulta SQL
        pstCitaMedica = con.prepareStatement(sql);
        pstCitaMedica.setInt(1, idCitaMedica);
        
        // Ejecutar la consulta
        rs = pstCitaMedica.executeQuery();
        
        // Si se encuentra el registro
        if (rs.next()) {
            citaMedica = new CitaMedica();
            
            // Asignar valores desde el ResultSet a la CitaMedica
            citaMedica.setIdCitaMedica(rs.getString("idCitaMedica"));
            citaMedica.setFecha(rs.getDate("fecha"));
            citaMedica.setHora(rs.getTime("hora").toLocalTime());
            
            // Convertir el estado (asumiendo que es un enum)
            String estadoStr = rs.getString("estado");
            EstadoCita estado = EstadoCita.valueOf(estadoStr.toUpperCase());
            citaMedica.setEstado(estado);
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener cita médica: " + e.getMessage());
    } finally {
        // Cerrar los recursos
        try {
            if (rs != null) rs.close();
            if (pstCitaMedica != null) pstCitaMedica.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }
    
    // Devolver la cita médica o null si no se encontró
    return citaMedica;
}

    
}
