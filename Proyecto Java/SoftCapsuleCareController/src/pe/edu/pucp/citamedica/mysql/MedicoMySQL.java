package pe.edu.pucp.citamedica.mysql;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Time;
import java.util.Arrays;
import java.util.stream.Collectors;
import pe.edu.pucp.citamedica.model.clinica.DiaSemana;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.dbmanager.config.DBPoolManager;






public class MedicoMySQL implements MedicoDAO {
    private Connection con;
    private Statement st;
    private PreparedStatement pstPersona;
    private PreparedStatement pstMedico;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    
    
    
//     public int insertar(AmbienteMedico ambiente){
//        int resultado = 0;
//        try {
//            con = DBManager.getInstance().getConnection();
//            sql = "INSERT into AmbienteMedico(numPiso,ubicacion,capacidad,tipoAmbiente)"
//                    + " values(?,?,?,?)";
//            pst = con.prepareStatement(sql);
//            pst.setInt(1, ambiente.getNumPiso());
//            pst.setString(2,ambiente.getUbicacion());
//            pst.setInt(3, ambiente.getCapacidad());
//            pst.setString(4,ambiente.getTipoAmbiente().toString());
//            resultado = pst.executeUpdate();
//        } catch (SQLException e) {
//            System.out.print(e.getMessage());
//        }
//        return resultado;
//    }
    
    
    
    @Override
    public int insertar(Medico medico) {


        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            
            
            sql = "{CALL insertarMedico(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            
            
            pstMedico = con.prepareStatement(sql);
            
            
            pstMedico.setString(1, medico.getDNI());
            pstMedico.setString(2, medico.getNombre());
            pstMedico.setString(3, medico.getApellido());
            pstMedico.setString(4, medico.getCorreoElectronico());
            pstMedico.setInt(5, medico.getNumTelefono());
            pstMedico.setString(6, medico.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(medico.getFechaNacimiento().getTime());
            pstMedico.setDate(7,sqlDate);
           
            pstMedico.setString(8, String.valueOf(medico.getGenero()));
            
            pstMedico.setString(9, medico.getNumColegiatura());
            pstMedico.setTime(10,Time.valueOf(medico.getHoraInicioTrabajo()));
            pstMedico.setTime(11,Time.valueOf(medico.getHoraFinTrabajo()));
            
            
            String diasLaboralesString = medico.getDiasLaborales().stream()
                .map(DiaSemana::name) // Obtener el nombre del enum
                .collect(Collectors.joining(",")); // Unirlos con coma
            
            
            pstMedico.setString(12, diasLaboralesString);       
            pstMedico.setInt(13,medico.getAhosExp());
            pstMedico.setBoolean(14,medico.isActivo());
            pstMedico.setInt(15,medico.getEspecialidad().getIdEspecialidad());
      
            
            resultado = pstMedico.executeUpdate();
          
            
           
           // resultado = pstMedico.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
            e.printStackTrace();
            System.out.print("Error general" + e.getMessage());
        }
        return resultado;
        
        
        



    }

    @Override
    public int modificar(Medico medico) {
        
        
       
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            
            
            sql = "{CALL ActualizarMedico(?,?,?,?,?,?,?)}";
            
            
            pstMedico = con.prepareStatement(sql);
            
            pstMedico.setInt(1, medico.getIdMedico());           
            pstMedico.setString(2, medico.getNumColegiatura());
            pstMedico.setTime(3,Time.valueOf(medico.getHoraInicioTrabajo()));
            pstMedico.setTime(4,Time.valueOf(medico.getHoraFinTrabajo()));
            
            
            String diasLaboralesString = medico.getDiasLaborales().stream()
                .map(DiaSemana::name) // Obtener el nombre del enum
                .collect(Collectors.joining(",")); // Unirlos con coma
            
            
            pstMedico.setString(5, diasLaboralesString);       
            pstMedico.setInt(6,medico.getAhosExp());
            pstMedico.setBoolean(7,medico.isActivo());
            
      
            
            resultado = pstMedico.executeUpdate();
          
            
           
           // resultado = pstMedico.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
            e.printStackTrace();
            System.out.print("Error general" + e.getMessage());
        }
        return resultado;
        
        
        

    }

    @Override
    public int eliminar(int idMedico) {
       
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            
            
            sql = "{CALL EliminarMedico(?)}";
            
            
            pstMedico = con.prepareStatement(sql);
            
            pstMedico.setInt(1, idMedico);           
                  
            resultado = pstMedico.executeUpdate();
          
            
           
           // resultado = pstMedico.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Error en la base de datos: " + e.getMessage());
        }catch( Exception e){
            e.printStackTrace();
            System.out.print("Error general" + e.getMessage());
        }
        return resultado;
    }


    @Override
    public ArrayList<Medico> listarTodos() {
        ArrayList<Medico> listaMedicos = new ArrayList<>();
        
        
        
        sql = "{CALL ListarMedicos()}";
        
        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pstMedico = con.prepareStatement(sql);
             ResultSet rs = pstMedico.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Medico
                Medico medico = new Medico();
                medico.setIdMedico(rs.getInt("idMedico"));
              

                Especialidad especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                medico.setEspecialidad(especialidad);

                // Asignar otros atributos del Medico
                medico.setNumColegiatura(rs.getString("numColegiatura"));
                medico.setHoraInicioTrabajo(rs.getTime("horaInicioTrabajo").toLocalTime());
                medico.setHoraFinTrabajo(rs.getTime("horaFinTrabajo").toLocalTime());
                
                
                
                String diasLaboralesString = rs.getString("diasLaborales"); // Este sería el valor de la base de datos

                // Crear un ArrayList<DiaLaborable>
                ArrayList<DiaSemana> diasLaborables = new ArrayList<>();

                // Dividir la cadena y convertir a enum
                Arrays.stream(diasLaboralesString.split(","))
                      .map(String::trim) // Eliminar espacios en blanco, si los hay
                      .forEach(dia -> {
                          try {
                              diasLaborables.add(DiaSemana.valueOf(dia));
                          } catch (IllegalArgumentException e) {
                              System.out.println("Día no válido: " + dia);
                          }
                      });

                medico.setDiasLaborales(diasLaborables);
                
                
                
                
                
                
                
                
                medico.setAhosExp(rs.getInt("anhosExp"));
               // medico.setActivo(rs.getBoolean("activo"));

                // Añadir el objeto Medico a la lista
                listaMedicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }
        return listaMedicos;  // Retornar la lista de médicos
    }

    @Override
    public Medico obtenerPorId(int idMedico) {
        Medico medico = null;
        sql = "{CALL ObtenerMedicoPorId(?)}";

        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pstMedico = con.prepareStatement(sql)) {

            pstMedico.setInt(1, idMedico);
            ResultSet rs = pstMedico.executeQuery();

            if (rs.next()) {
                medico = new Medico();
                medico.setIdMedico(rs.getInt("idMedico"));
               

                Especialidad especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                medico.setEspecialidad(especialidad);
                

                // Asignar otros atributos del médico
                medico.setNumColegiatura(rs.getString("numColegiatura"));
                medico.setHoraInicioTrabajo(rs.getTime("horaInicioTrabajo").toLocalTime());
                medico.setHoraFinTrabajo(rs.getTime("horaFinTrabajo").toLocalTime());
                
                
                
                
                
                String diasLaboralesString = rs.getString("diasLaborales"); // Este sería el valor de la base de datos

                // Crear un ArrayList<DiaLaborable>
                ArrayList<DiaSemana> diasLaborables = new ArrayList<>();

                // Dividir la cadena y convertir a enum
                Arrays.stream(diasLaboralesString.split(","))
                      .map(String::trim) // Eliminar espacios en blanco, si los hay
                      .forEach(dia -> {
                          try {
                              diasLaborables.add(DiaSemana.valueOf(dia));
                          } catch (IllegalArgumentException e) {
                              System.out.println("Día no válido: " + dia);
                          }
                      });

                medico.setDiasLaborales(diasLaborables);
                
                
                medico.setAhosExp(rs.getInt("anhosExp"));
              //  medico.setActivo(rs.getBoolean("activo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medico;
    }
}
