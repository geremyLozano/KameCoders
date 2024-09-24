package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.dao.PersonaDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import pe.edu.pucp.dbmanager.config.DBManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import pe.edu.pucp.citamedica.paciente.model.Persona;
import pe.edu.pucp.citamedica.usuario.model.Usuario;

public class PersonaMySQL implements PersonaDAO{
    private Connection con;
    private Statement st;
    private PreparedStatement pstPersona;
    private PreparedStatement pstUsuario;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Persona persona,Usuario usuario){
        int resultado  =0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero) values(?,?,?,?,?,?,?)";
            pstPersona = con.prepareStatement(sql);
            pstPersona.setString(1, persona.getNombre());
            pstPersona.setString(2, persona.getApellido());
            pstPersona.setString(3, persona.getCorreoElectronico());
            pstPersona.setInt(4, persona.getNumTelefono());
            pstPersona.setString(5, persona.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(persona.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.setString(7, String.valueOf(persona.getGenero()));
            pstPersona.executeUpdate();
            
            rs = pstPersona.getGeneratedKeys();//Obtengo el IDPERSONA GENERADO
            int idPersona = 0;
            if(rs.next()){
                idPersona = rs.getInt(1);
            }
            
            sql = "INSERT into Usuario(username,contrasenha,idpersona) values(?,?,?)";
            pstUsuario = con.prepareStatement(sql);
            pstUsuario.setInt(1, usuario.getUsername());
            pstUsuario.setString(2, usuario.getContrasenha());
            pstUsuario.setInt(3,idPersona);
            resultado = pstUsuario.executeUpdate();
            
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int modificar(Persona persona){
        int resultado  =0;
        
        return resultado;
    }
    
    @Override
    public int eliminar(int idPersona){
        int resultado  =0;
        
        return resultado;
    }
    
    @Override
    public ArrayList<Persona> listarTodos(){
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Persona obtenerPorId(int idPersona){
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
