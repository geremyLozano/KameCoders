/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.mysql;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.ProcedimientoDAO;
import pe.edu.pucp.citamedica.procedimiento.model.Procedimiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.dbmanager.config.DBManager;


/**
 *
 * @author Usuario
 */
public class ProcedimientoMySQL implements ProcedimientoDAO{
    
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
      
    @Override
    public int insertar(Procedimiento procedimiento,int idAmbienteMedico) {
        
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            
            
          
            
           sql= "INSERT INTO Procedimiento (nombre,"
+ "costo,descripcion,requisitosPrevios,tipoProcedimiento,"
+ "ambienteMedico_idAmbienteMedico,fecha) VALUES (?,?,?,?,?,?,?)";

            
              
            pst = con.prepareStatement(sql);
            pst.setString(1,procedimiento.getNombre());
            pst.setDouble(2, procedimiento.getCosto());
            pst.setString(3,procedimiento.getDescripcion());
            pst.setString(4,procedimiento.getRequisitosPrevios());    
            pst.setString(5,procedimiento.getTipo().name());
            pst.setInt(6,idAmbienteMedico);
            
            
            java.sql.Date sqlDate = new java.sql.Date(procedimiento.getFecha().getTime());
            
            pst.setDate(7,sqlDate);
            
            pst.executeUpdate();
                        
            resultado = pst.executeUpdate();
            
        } catch (SQLException e) {
           // e.printStackTrace();
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
           // e.printStackTrace();
            System.out.print("Error general" + e.getMessage());
        }
        
        
        return resultado;     
    }

    @Override
    public int modificar(Procedimiento procedimiento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int eliminar(int idProcedimiento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Procedimiento> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Procedimiento obtenerPorId(int idProcedimiento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
    
    
    
    
    
    
}
