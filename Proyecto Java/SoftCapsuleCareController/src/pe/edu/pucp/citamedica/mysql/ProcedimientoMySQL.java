/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.mysql;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.ProcedimientoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.TipoProcedimiento;
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
        
        
        int resultado = 0;
        
        
        sql ="UPDATE Procedimiento SET "
                + "nombre = ?,costo = ?,descripcion = ?,requisitosPrevios = ?,"
                + "tipoProcedimiento = ?,fecha = ? WHERE idProcedimiento = ?;";

        

        try (Connection con = DBManager.getInstance().getConnection();  // Obtener la conexión desde DBManager
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Configuramos los valores a modificar en el PreparedStatement
            pst.setString(1,procedimiento.getNombre());
            pst.setDouble(2,procedimiento.getCosto());
            pst.setString(3,procedimiento.getDescripcion());
            pst.setString(4,procedimiento.getRequisitosPrevios());
            pst.setString(5,procedimiento.getTipo().name());

                   
            pst.setInt(7,procedimiento.getIdProcedimiento());
            
            // Ejecutar la consulta de actualización
            resultado = pst.executeUpdate();

            // Verificar si la modificación fue exitosa
            if (resultado > 0) {
                System.out.println("Procedimiento modificado correctamente.");
            } else {
                System.out.println("No se encontró ninguna Procedimiento con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir la excepción si ocurre un error
        }

        return resultado;
        
        
        
        
        
        
    }

    @Override
    public int eliminar(int idProcedimiento) {
        
        int resultado = 0;
        sql = "DELETE FROM Procedimiento WHERE idProcedimiento = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idProcedimiento);

            resultado = pst.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("Procedimiento eliminado correctamente.");
            } else {
                System.out.println("No se encontró ninguna Procedimiento con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public ArrayList<Procedimiento> listarTodos() {
        
        
        ArrayList<Procedimiento> listaProcedimiento = new ArrayList<>();
        String sql = "SELECT * FROM Procedimiento";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto 
                Procedimiento procedimiento = new Procedimiento();
                
                procedimiento.setIdProcedimiento(rs.getInt("idProcedimiento"));
                procedimiento.setNombre(rs.getString("nombre"));
                
                procedimiento.setCosto(rs.getDouble("costo"));
                procedimiento.setDescripcion(rs.getString("descripcion"));
               
                procedimiento.setRequisitosPrevios(rs.getString("requisitosPrevios"));              
                TipoProcedimiento tipo = TipoProcedimiento.valueOf("tipoProcedimiento");
                procedimiento.setTipo(tipo);

               
                listaProcedimiento.add(procedimiento);
            }

        } catch (SQLException e) {
            e.printStackTrace();  
        }
        return listaProcedimiento;  
        
        
    }

    @Override
    public Procedimiento obtenerPorId(int idProcedimiento) {
        
        Procedimiento procedimiento = null;
        String sql = "SELECT * FROM Procedimiento WHERE idProcedimiento = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idProcedimiento);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                procedimiento = new Procedimiento();
                
                
                procedimiento.setIdProcedimiento(rs.getInt("idProcedimiento"));
                procedimiento.setNombre(rs.getString("nombre"));
                
                procedimiento.setCosto(rs.getDouble("costo"));
                procedimiento.setDescripcion(rs.getString("descripcion"));
               
                procedimiento.setRequisitosPrevios(rs.getString("requisitosPrevios"));              
                TipoProcedimiento tipo = TipoProcedimiento.valueOf("tipoProcedimiento");
                procedimiento.setTipo(tipo);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return procedimiento;
     
        
    }
   
    
}
