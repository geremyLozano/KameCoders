/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.clinica.model.Administrador;
import pe.edu.pucp.citamedica.dao.AdministradorDao;
import pe.edu.pucp.dbmanager.config.DBManager;

/**
 *
 * @author Usuario
 */
public class AdministradorMySQL implements AdministradorDao{

    
    private Connection con;
    //private PreparedStatement pst;
    private String sql;
    private CallableStatement cst;
    
    private PreparedStatement pstPersona;
    private PreparedStatement pstAdministrador;
    
     private ResultSet rs;
    
    
    
    
    @Override
    public int insertar(Administrador administrador) {
        
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero) values(?,?,?,?,?,?))";
            
            pstPersona = con.prepareStatement(sql);
            pstPersona.setString(1, administrador.getNombre());
            pstPersona.setString(2, administrador.getApellido());
            pstPersona.setString(3, administrador.getCorreoElectronico());
            pstPersona.setInt(4, administrador.getNumTelefono());
            pstPersona.setString(5, administrador.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(administrador.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.executeUpdate();
            
            rs = pstPersona.getGeneratedKeys();//Obtengo el IDPERSONA GENERADO
            int idPersona = 0;
            if(rs.next()){
                idPersona = rs.getInt(1);
            }
            
             sql = "{INSERT INTO Administrador(idpersona) "
                    + "values(?)}";
            pstAdministrador = con.prepareStatement(sql);
            pstAdministrador.setInt(1, idPersona);
           
            resultado = pstAdministrador.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Administrador administrador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int eliminar(int idAdministrador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Administrador> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Administrador obtenerPorId(int idAdministrador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
    
}
