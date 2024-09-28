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
import pe.edu.pucp.dbmanager.config.DBManager;

public abstract class AuxiliarMySQL implements AuxiliarDAO{
    private Connection con;
    private PreparedStatement pstPersona;
    private PreparedStatement pstAuxiliar;
    private CallableStatement cst;
    private Statement st;
    private String sql;
    private ResultSet rs;
    
    @Override
    public int insertar(Auxiliar auxiliar){
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();
            sql = "{INSERT into Persona(nombre,apellido,correoElectronico,numTelefono,"
                    + "direccion,fechaNacimiento,genero) values(?,?,?,?,?,?))";
            pstPersona = con.prepareStatement(sql);
            pstPersona.setString(1, auxiliar.getNombre());
            pstPersona.setString(2, auxiliar.getApellido());
            pstPersona.setString(3, auxiliar.getCorreoElectronico());
            pstPersona.setInt(4, auxiliar.getNumTelefono());
            pstPersona.setString(5, auxiliar.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(auxiliar.getFechaNacimiento().getTime());
            pstPersona.setDate(6,sqlDate);
            pstPersona.executeUpdate();
            
            rs = pstPersona.getGeneratedKeys();//Obtengo el IDPERSONA GENERADO
            int idAuxiliar = 0;
            if(rs.next()){
                idAuxiliar = rs.getInt(1);
            }
            
            sql = "{INSERT INTO Auxiliar(idAuxiliar,activo) "
                    + "values(?,?)}";
            pstAuxiliar = con.prepareStatement(sql);
            pstAuxiliar.setInt(1, idAuxiliar);
            pstAuxiliar.setBoolean(2, auxiliar.isActivo());
            resultado = pstAuxiliar.executeUpdate();
            
            sql = "INSERT into Auxiliar(dni,nombre) values(?,?)";
            cst = con.prepareCall(sql);
            cst.setString(1,auxiliar.getDNI());
            cst.setString(2, auxiliar.getNombre());
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }
    
    @Override
    public int modificar(Auxiliar auxiliar) {
        int resultado = 0;
        try {
            con = DBManager.getInstance().getConnection();

            sql = "UPDATE auxiliar SET activo = ? WHERE idAuxiliar = ?";
            pstAuxiliar = con.prepareStatement(sql);
            pstAuxiliar.setBoolean(1, auxiliar.isActivo());
            pstAuxiliar.setInt(2, auxiliar.getIdAuxiliar());
            resultado = pstAuxiliar.executeUpdate();  
            
        } catch (SQLException e) {
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
