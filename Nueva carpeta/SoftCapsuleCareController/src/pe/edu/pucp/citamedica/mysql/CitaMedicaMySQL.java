package pe.edu.pucp.citamedica.mysql;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.consultas.model.CitaMedica;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.dbmanager.config.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import pe.edu.pucp.citamedica.procedimiento.model.Procedimiento;
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
        pstCitaMedica.setInt(5, cita.getMedico().getIdMedico()); // Asumiendo que Medico tiene un id
        pstCitaMedica.setInt(6, cita.getPaciente().getIdPaciente()); // Asumiendo que Paciente tiene un id
        pstCitaMedica.setString(7, cita.getPlataforma());
        pstCitaMedica.setString(8, cita.getEnlace());
        pstCitaMedica.setTime(9, java.sql.Time.valueOf(cita.getDuracion())); // Conversion de LocalTime a java.sql.Time
        pstCitaMedica.setInt(10, cita.getNumeroAmbiente());
        pstCitaMedica.setInt(11, cita.getPago().getIdPago()); // Asumiendo que Pago tiene un id
        
        // Ejecutamos la inserción y obtenemos las llaves generadas
        pstCitaMedica.executeUpdate();
        rs = pstCitaMedica.getGeneratedKeys();
        if (rs.next()) {
            cita.setId(rs.getString(1)); // Guardamos el ID generado de la cita en el objeto
        }
        
        // Inserción adicional para los procedimientos asociados a la cita médica
        sql = "INSERT INTO ProcedimientoCita(idCitaMedica, idProcedimiento) VALUES (?, ?)";
        for (Procedimiento proc : cita.getProcedimientos()) {
            pstProcedimiento = con.prepareStatement(sql);
            pstProcedimiento.setString(1, cita.getId());
            pstProcedimiento.setString(2, proc.getIdProcedimiento()); // Asumiendo que Procedimiento tiene un id
            pstProcedimiento.executeUpdate();
        }

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int eliminar(int idCitaMedica) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<CitaMedica> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CitaMedica obtenerPorId(int idCitaMedica) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
