package pe.edu.pucp.citamedica.mysql;

import java.sql.Connection;
import pe.edu.pucp.citamedica.dao.AmbienteMedicoDAO;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.AmbienteMedico;
import pe.edu.pucp.citamedica.model.clinica.TipoAmbiente;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class AmbienteMedicoMySQL implements AmbienteMedicoDAO{
    
    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    
    
    @Override
    public int insertar(AmbienteMedico ambiente) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            // Llamar al procedimiento almacenado
            sql = "{CALL sp_insertar_ambiente_medico(?, ?, ?, ?)}";
            cst = con.prepareCall(sql);
            // Pasar los parámetros al procedimiento almacenado
            cst.setInt(1, ambiente.getNumPiso());
            cst.setString(2, ambiente.getUbicacion());
            cst.setInt(3, ambiente.getCapacidad());
            
            
            
            cst.setString(4, ambiente.getTipoAmbiente().name());

            
            
            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return resultado;
    }

    @Override
    public int modificar(AmbienteMedico ambiente) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_actualizar_ambiente_medico(?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros al procedimiento almacenado
            cst.setInt(1, ambiente.getIdAmbiente());
            cst.setInt(2, ambiente.getNumPiso());
            cst.setString(3, ambiente.getUbicacion());
            cst.setInt(4, ambiente.getCapacidad());
            cst.setString(5, ambiente.getTipoAmbiente().toString());

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                System.out.println("Error: " + e.getMessage());
            } else {
                System.out.println("Error general: " + e.getMessage());
            }
        }
        return resultado;
    }

        
    @Override
    public ArrayList<AmbienteMedico> listarTodos() {
        ArrayList<AmbienteMedico> ambientes = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_listar_todos_ambiente_medico()}";
            cst = con.prepareCall(sql);

            // Ejecutar el procedimiento y obtener el resultado
            rs = cst.executeQuery();

            while (rs.next()) {
                AmbienteMedico ambiente = new AmbienteMedico();
                ambiente.setIdAmbiente(rs.getInt("idAmbienteMedico"));
                ambiente.setNumPiso(rs.getInt("numPiso"));
                ambiente.setUbicacion(rs.getString("ubicacion"));
                ambiente.setCapacidad(rs.getInt("capacidad"));
                   
                String tipoAmbienteStr = rs.getString("tipoAmbiente");
                if (tipoAmbienteStr != null) {
                  try {
                      ambiente.setTipoAmbiente(TipoAmbiente.valueOf(tipoAmbienteStr));
                  } catch (IllegalArgumentException e) {
                      System.out.println("Valor inválido para TipoAmbiente: " + tipoAmbienteStr);
                      ambiente.setTipoAmbiente(null); // O un valor predeterminado
                  }
                } else {
                  ambiente.setTipoAmbiente(null);
                }
                ambiente.setActivo(rs.getBoolean("activo"));
                
                
                ambientes.add(ambiente);
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
        return ambientes;
    }

    @Override
    public AmbienteMedico obtenerPorId(int idAmbienteMedico) {
        AmbienteMedico ambiente = null;
        try {
            con = DBPoolManager.getInstance().getConnection();

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_obtener_ambiente_medico_por_id(?)}";
            cst = con.prepareCall(sql);

            // Pasar el parámetro al procedimiento almacenado
            cst.setInt(1, idAmbienteMedico);

            // Ejecutar el procedimiento y obtener el resultado
            rs = cst.executeQuery();

            if (rs.next()) {
                ambiente = new AmbienteMedico();
                ambiente.setIdAmbiente(rs.getInt("idAmbienteMedico"));
                ambiente.setNumPiso(rs.getInt("numPiso"));
                ambiente.setUbicacion(rs.getString("ubicacion"));
                ambiente.setCapacidad(rs.getInt("capacidad"));
                            
                String tipoAmbienteStr = rs.getString("tipoAmbiente");
                if (tipoAmbienteStr != null) {
                  try {
                      ambiente.setTipoAmbiente(TipoAmbiente.valueOf(tipoAmbienteStr));
                  } catch (IllegalArgumentException e) {
                      System.out.println("Valor inválido para TipoAmbiente: " + tipoAmbienteStr);
                      ambiente.setTipoAmbiente(null); // O un valor predeterminado
                  }
                } else {
                  ambiente.setTipoAmbiente(null);
                }
                ambiente.setActivo(rs.getBoolean("activo"));
                

            } else {
                System.out.println("No se encontró ningún ambiente médico con el ID proporcionado.");
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
        return ambiente;
    }

    @Override
    public int eliminar(int idAmbienteMedico) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();

            // Llamar al procedimiento almacenado
            sql = "{CALL sp_eliminar_logico_ambiente_medico(?)}";
            cst = con.prepareCall(sql);

            // Pasar el parámetro al procedimiento almacenado
            cst.setInt(1, idAmbienteMedico);

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                System.out.println("Error: " + e.getMessage());
            } else {
                System.out.println("Error general: " + e.getMessage());
            }
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
    
    
    @Override
    public int cambiarEstadoAmbiente(int idAmbiente) {
        int resultado = 0;
        try{
            con = DBPoolManager.getInstance().getConnection();
            sql = "{call sp_cambio_estado_ambiente_medico(?)}";
            cst = con.prepareCall(sql);  
            cst.setInt(1, idAmbiente);
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    
    
    
}
