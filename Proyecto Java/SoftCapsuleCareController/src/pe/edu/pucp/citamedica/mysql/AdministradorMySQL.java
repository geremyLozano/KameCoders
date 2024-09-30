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
    private String sql;
    private CallableStatement cst;
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
            administrador.setDNI(persona.getDNI());
            administrador.setNombre(persona.getNombre());
            administrador.setApellido(persona.getApellido());
            administrador.setCorreoElectronico(persona.getCorreoElectronico());
            administrador.setNumTelefono(persona.getNumTelefono());
            administrador.setDireccion(persona.getDireccion());
            administrador.setFechaNacimiento(persona.getFechaNacimiento());
            administrador.setGenero(persona.getGenero());
            administrador.setActivo(true);
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(Administrador administrador) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AdministradorModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, administrador.getIdAdministrador());
            cst.setString(2, administrador.getDNI());
            cst.setString(3, administrador.getNombre());
            cst.setString(4, administrador.getApellido());
            cst.setString(5, administrador.getCorreoElectronico());
            cst.setInt(6, administrador.getNumTelefono());
            cst.setString(7, administrador.getDireccion());
            cst.setDate(8, new java.sql.Date(administrador.getFechaNacimiento().getTime()));
            cst.setString(9, String.valueOf(administrador.getGenero()));
            cst.setBoolean(10, true); //administrador.getActivo;
        
            resultado = cst.executeUpdate();
            
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int eliminar(int idAdministrador) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call AdministradorEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idAdministrador);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public ArrayList<Administrador> listarTodos() {
         ArrayList<Administrador> administradores = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "{CALL AdministradorListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Administrador admin = new Administrador();
                admin.setIdAdministrador(rs.getInt("idPersona"));
                admin.setDNI(rs.getString("DNI"));
                admin.setNombre(rs.getString("nombre"));
                admin.setApellido(rs.getString("apellido"));
                admin.setCorreoElectronico(rs.getString("correoElectronico"));
                admin.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                admin.setActivo(rs.getBoolean("activo"));
                administradores.add(admin);
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
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "{CALL AdministradorListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAdministrador);
            rs = cst.executeQuery();
            if (rs.next()) {
                administrador = new Administrador();
                administrador.setIdAdministrador(rs.getInt("idPersona"));
                administrador.setDNI(rs.getString("DNI"));
                administrador.setNombre(rs.getString("nombre"));
                administrador.setApellido(rs.getString("apellido"));
                administrador.setCorreoElectronico(rs.getString("correoElectronico"));
                administrador.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                administrador.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return administrador;
    }   
}
