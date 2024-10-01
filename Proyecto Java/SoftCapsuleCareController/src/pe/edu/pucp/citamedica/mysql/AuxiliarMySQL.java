package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class AuxiliarMySQL implements AuxiliarDAO{
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Auxiliar auxiliariliar, Usuario usuario, Persona persona){
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarInsertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
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
            auxiliariliar.setIdAuxiliar(persona.getIdPersona());
            auxiliariliar.setDNI(persona.getDNI());
            auxiliariliar.setNombre(persona.getNombre());
            auxiliariliar.setApellido(persona.getApellido());
            auxiliariliar.setCorreoElectronico(persona.getCorreoElectronico());
            auxiliariliar.setNumTelefono(persona.getNumTelefono());
            auxiliariliar.setDireccion(persona.getDireccion());
            auxiliariliar.setFechaNacimiento(persona.getFechaNacimiento());
            auxiliariliar.setGenero(persona.getGenero());
            auxiliariliar.setActivo(true);
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int modificar(Auxiliar auxiliariliar) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, auxiliariliar.getIdAuxiliar());
            cst.setString(2, auxiliariliar.getDNI());
            cst.setString(3, auxiliariliar.getNombre());
            cst.setString(4, auxiliariliar.getApellido());
            cst.setString(5, auxiliariliar.getCorreoElectronico());
            cst.setInt(6, auxiliariliar.getNumTelefono());
            cst.setString(7, auxiliariliar.getDireccion());
            cst.setDate(8, new java.sql.Date(auxiliariliar.getFechaNacimiento().getTime()));
            cst.setString(9, String.valueOf(auxiliariliar.getGenero()));
            cst.setInt(10, 1);
            cst.setBoolean(11, auxiliariliar.isActivo());
            
            resultado = cst.executeUpdate();
            
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int eliminar(int idAuxiliar) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call AuxiliarEliminar(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idAuxiliar);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    @Override
    public ArrayList<Auxiliar> listarTodos() {
        ArrayList<Auxiliar> auxiliariliares = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarListar}";
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Auxiliar auxiliar = new Auxiliar();
                Especialidad esp = new Especialidad();
                auxiliar.setIdAuxiliar(rs.getInt("idPersona"));
                auxiliar.setDNI(rs.getString("DNI"));
                auxiliar.setNombre(rs.getString("nombre"));
                auxiliar.setApellido(rs.getString("apellido"));
                esp.setNombre(rs.getString("especialidad"));
                auxiliar.setEspecialidad(esp);
                auxiliar.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                auxiliar.setActivo(rs.getBoolean("activo"));
                auxiliariliares.add(auxiliar);
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
        return auxiliariliares;
    }

    @Override
    public Auxiliar obtenerPorId(int idAuxiliar) {
        Auxiliar auxiliar = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarListarPorID(?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, idAuxiliar);
            rs = cst.executeQuery();
            if (rs.next()) {
                auxiliar = new Auxiliar();
                Especialidad esp = new Especialidad();
                auxiliar.setIdAuxiliar(rs.getInt("idPersona"));
                auxiliar.setDNI(rs.getString("DNI"));
                auxiliar.setNombre(rs.getString("nombre"));
                auxiliar.setApellido(rs.getString("apellido"));
                esp.setNombre(rs.getString("especialidad"));
                auxiliar.setEspecialidad(esp);
                auxiliar.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                auxiliar.setActivo(rs.getBoolean("activo"));
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
        return auxiliar;
    }  
}
