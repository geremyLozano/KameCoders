package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.ResultSet;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBManager;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class AuxiliarMySQL implements AuxiliarDAO{
    private Connection con;
    private PreparedStatement pstPersona;
    private PreparedStatement pstAuxiliar;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Auxiliar auxiliar, Usuario usuario, Persona persona){
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
            auxiliar.setIdAuxiliar(persona.getIdPersona());
            auxiliar.setDNI(persona.getDNI());
            auxiliar.setNombre(persona.getNombre());
            auxiliar.setApellido(persona.getApellido());
            auxiliar.setCorreoElectronico(persona.getCorreoElectronico());
            auxiliar.setNumTelefono(persona.getNumTelefono());
            auxiliar.setDireccion(persona.getDireccion());
            auxiliar.setFechaNacimiento(persona.getFechaNacimiento());
            auxiliar.setGenero(persona.getGenero());
            auxiliar.setActivo(true);
        return resultado;
        }   catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int modificar(Auxiliar auxiliar) {
        int resultado = -1;
        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL AuxiliarModificar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            cst.setInt(1, auxiliar.getIdAuxiliar());
            cst.setString(2, auxiliar.getDNI());
            cst.setString(3, auxiliar.getNombre());
            cst.setString(4, auxiliar.getApellido());
            cst.setString(5, auxiliar.getCorreoElectronico());
            cst.setInt(6, auxiliar.getNumTelefono());
            cst.setString(7, auxiliar.getDireccion());
            cst.setDate(8, new java.sql.Date(auxiliar.getFechaNacimiento().getTime()));
            cst.setString(9, String.valueOf(auxiliar.getGenero()));
            cst.setInt(10, 1);
            cst.setBoolean(11, auxiliar.isActivo());
            
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
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{call AUXILIAR_ELIMINAR(?)}";
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
        ArrayList<Auxiliar> auxiliares = new ArrayList<>();
        try {
            con = DBManager.getInstance().getConnection();
            st = con.createStatement();
            sql = "SELECT idAuxiliar,nombre,apellido FROM paciente WHERE "
                    + "activo = 1";
            rs = st.executeQuery(sql);
            while(rs.next()){
                Auxiliar auxiliar = new Auxiliar();
                auxiliar.setIdAuxiliar(rs.getInt("idAuxiliar"));
                auxiliar.setNombre(rs.getString("nombre"));
                auxiliar.setApellido(rs.getString("apellido"));
                auxiliar.setActivo(true);
                auxiliares.add(auxiliar);
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
        return auxiliares;
    }

    @Override
    public Auxiliar obtenerPorId(int idAuxiliar) {
        Auxiliar auxiliar = null;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "SELECT idAuxiliar, activo FROM Auxiliar WHERE idAuxiliar = ?";
            pstAuxiliar = con.prepareStatement(sql);
            pstAuxiliar.setInt(1, idAuxiliar);
            rs = pstAuxiliar.executeQuery();

            if (rs.next()) {
                auxiliar = new Auxiliar();
                auxiliar.setIdAuxiliar(rs.getInt("idAuxiliar"));
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
