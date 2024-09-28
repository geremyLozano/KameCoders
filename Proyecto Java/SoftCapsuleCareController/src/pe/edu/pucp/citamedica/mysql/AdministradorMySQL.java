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
    public int insertar(Administrador administrador) {
        
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero) values(?,?,?,?,?,?,?)";
            
            pstPersona = con.prepareStatement(sql);
            pstPersona.setString(1, administrador.getNombre());
            pstPersona.setString(2, administrador.getApellido());
            pstPersona.setString(3, administrador.getCorreoElectronico());
            pstPersona.setInt(4, administrador.getNumTelefono());
            pstPersona.setString(5, administrador.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(administrador.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.setString(7, String.valueOf(administrador.getGenero()));
            pstPersona.executeUpdate();
            
            rs = pstPersona.getGeneratedKeys();//Obtengo el IDPERSONA GENERADO
            int idPersona = 0;
            if(rs.next()){
                idPersona = rs.getInt(1);
            }
            
             sql = "INSERT INTO Administrador(idpersona) "
                    + "values(?)";
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
