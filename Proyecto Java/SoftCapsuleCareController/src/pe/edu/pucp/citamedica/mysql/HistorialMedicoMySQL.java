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
import pe.edu.pucp.citamedica.model.dto.HistorialMedicoDto;
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
        try {
            con = DBPoolManager.getInstance().getConnection();
            
            
            sql = "{CALL HistorialMedicoModificar(?,?,?,?,?,?,?,?)}";
            
            
            
            pst = con.prepareStatement(sql);
            
            pst.setInt(1, historialorial.getIdHistorial());           
            pst.setString(2, historialorial.getEnferPreExist());
             pst.setString(3, historialorial.getAlergias());
             pst.setString(4, historialorial.getCirugiasPrevias());
             pst.setString(5, historialorial.getVacunas());

             pst.setDouble(6, historialorial.getPeso());
             pst.setDouble(7, historialorial.getAltura());
             pst.setString(8, historialorial.getTipoSangre());
            
   
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
    public int cambiarEstadoHistorial(int idHistorial) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call HistorialMedicoCambiarEstado(?)}";
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
                
                
                historial.setEnferPreExist(rs.getString("enferPreExist"));
                historial.setAlergias(rs.getString("alergias"));
                historial.setCirugiasPrevias(rs.getString("cirugiasPrevias"));
                historial.setVacunas(rs.getString("vacunas"));
                
                historial.setPeso(rs.getDouble("peso"));
                historial.setAltura(rs.getDouble("altura"));
                
                historial.setTipoSangre(rs.getString("tipoSangre"));
                
                
                
                
                
                
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return historial;
    }
    
    
    	 @Override
    public ArrayList<HistorialMedicoDto> listarTodosPorCampImp() {
    
        
        ArrayList<HistorialMedicoDto> listaHistorial = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "{CALL HistorialMedicoListarPorCamposImp}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                HistorialMedicoDto historial = new HistorialMedicoDto();
                
                historial.setIdHistorialMedico(rs.getInt("idHistorialMedico"));
                historial.setFechaCreacion(rs.getDate("fechaCreacion"));
                historial.setIdPaciente(rs.getInt("idPaciente"));    
                historial.setActivo(rs.getBoolean("activo"));
                historial.setDniPaciente(rs.getString("DNI"));              
                historial.setNombrePaciente(rs.getString("nombre"));
                historial.setApellidoPaciente(rs.getString("apellido"));
               
                
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
    public ArrayList<HistorialMedicoDto> listarTodosPorDniNombreApellido(String patronCaracteres){
        
        
        
        ArrayList<HistorialMedicoDto> listaHistorial = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "{CALL HistorialMedicoListarPorDniNombreApellido(?)}";
            cst = con.prepareCall(sql);
            
            cst.setString(1, patronCaracteres);
            
            
            rs = cst.executeQuery();
            while(rs.next()){
                HistorialMedicoDto historial = new HistorialMedicoDto();
                
                historial.setIdHistorialMedico(rs.getInt("idHistorialMedico"));
                historial.setFechaCreacion(rs.getDate("fechaCreacion"));
                historial.setIdPaciente(rs.getInt("idPaciente"));    
                historial.setActivo(rs.getBoolean("activo"));
                historial.setDniPaciente(rs.getString("DNI"));              
                historial.setNombrePaciente(rs.getString("nombre"));
                historial.setApellidoPaciente(rs.getString("apellido"));
               
                
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
    public HistorialMedico obtenerPorPaciente(int idPaciente) {
        HistorialMedico historial = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "{CALL HistorialMedicoListarPorIDPaciente(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idPaciente);
            rs = cst.executeQuery();
            if (rs.next()) {
                historial = new HistorialMedico();
                historial.setIdHistorial(rs.getInt("idHistorialMedico"));
                historial.setFechaDeCreacion(rs.getDate("fechaCreacion"));
                historial.setActivo(rs.getBoolean("activo"));
                historial.setIdPaciente(rs.getInt("idPaciente"));
                
                
                historial.setEnferPreExist(rs.getString("enferPreExist"));
                historial.setAlergias(rs.getString("alergias"));
                historial.setCirugiasPrevias(rs.getString("cirugiasPrevias"));
                historial.setVacunas(rs.getString("vacunas"));
                
                historial.setPeso(rs.getDouble("peso"));
                historial.setAltura(rs.getDouble("altura"));
                historial.setTipoSangre(rs.getString("tipoSangre"));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return historial;
    }
    
}
