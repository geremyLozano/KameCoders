package pe.edu.pucp.citamedica.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import java.sql.Statement;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

/**
 *
 * @author Usuario
 */
public class AdministradorMySQL implements AdministradorDAO{

    
    private Connection con;
    //private PreparedStatement pst;
    private String sql;
    private CallableStatement cst;
    
    private PreparedStatement pstPersona;
    private PreparedStatement pstAdministrador;
    
     private ResultSet rs;
     private Statement st;
   
    @Override
    public int insertar(Administrador administrador, Usuario usuario, Persona persona) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AdministradorInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
            cst.setString(3, usuario.getUsername());
            cst.setString(4, usuario.getContrasenha());
            cst.setString(5, persona.getDNI());
            cst.setString(6, persona.getNombre());
            cst.setString(7, persona.getApellido());
            cst.setString(8, persona.getCorreoElectronico());
            cst.setInt(9, persona.getNumTelefono());
            cst.setString(10, persona.getDireccion());
            cst.setDate(11, new java.sql.Date(persona.getFechaNacimiento().getTime()));
            cst.setString(12, String.valueOf(persona.getGenero()));
        
            resultado = cst.executeUpdate();
            
            persona.setIdPersona(cst.getInt(1));
            usuario.setIdUsuario(cst.getInt(2));
            administrador.setIdAdministrador(persona.getIdPersona());
            administrador.setActivo(true);
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Administrador administrador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int eliminar(int idAdministrador) {
        
        int resultado = 0;
        sql = "DELETE FROM Administrador WHERE idAdministrador = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idAdministrador);

            resultado = pst.executeUpdate();

            // Verificar si el registro fue eliminado
            if (resultado > 0) {
                System.out.println("Administrador eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningun Administrador con ese ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
        
        
        
    }

    @Override
    public ArrayList<Administrador> listarTodos() {
        
         ArrayList<Administrador> administradores = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT idAdministrador FROM Administrador ";
            rs = st.executeQuery(sql);
            while(rs.next()){
                Administrador administrador = new Administrador();
                administrador.setIdAdministrador(rs.getInt("idAdministrador"));             
                administradores.add(administrador);
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
        return administradores;
        
        
        
        
    }

    @Override
    public Administrador obtenerPorId(int idAdministrador) {
        
        
        Administrador administrador = null;
        String sql = "SELECT * FROM Administrador WHERE idAdministrador = ?";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idAdministrador);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                administrador = new Administrador();
                administrador.setIdAdministrador(rs.getInt("idAdministrador"));
                 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return administrador;
        
        
        
        
        
        
        
        
        
    }
    
    
    
    
    
    
}
