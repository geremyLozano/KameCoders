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
import pe.edu.pucp.citamedica.model.usuario.Usuario;
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
    public int insertar(Medico medico, Usuario usuario) {


        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            
            
            sql = "{CALL insertarMedico(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            
            
            cst = con.prepareCall(sql);
            
            
            cst.setString(1, medico.getDNI());
            cst.setString(2, medico.getNombre());
            cst.setString(3, medico.getApellido());
            cst.setString(4, medico.getCorreoElectronico());
            cst.setInt(5, medico.getNumTelefono());
            cst.setString(6, medico.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(medico.getFechaNacimiento().getTime());
            cst.setDate(7,sqlDate);
           
            cst.setString(8, String.valueOf(medico.getGenero()));
            
            cst.setString(9, medico.getNumColegiatura());
            cst.setTime(10,Time.valueOf(medico.getHoraInicioTrabajo()));
            cst.setTime(11,Time.valueOf(medico.getHoraFinTrabajo()));
            
            
//            String diasLaboralesString = medico.getDiasLaborales().stream()
//                .map(DiaSemana::name) // Obtener el nombre del enum
//                .collect(Collectors.joining(",")); // Unirlos con coma
//            
            
            cst.setString(12, medico.getDiasLaborales());       
            cst.setInt(13,medico.getAhosExp());
            cst.setBoolean(14,medico.isActivo());
            cst.setInt(15,medico.getEspecialidad().getIdEspecialidad());
            cst.registerOutParameter(16, java.sql.Types.INTEGER);
            cst.setString(17, usuario.getContrasenha());
            
            medico.setIdMedico(cst.getInt(16));
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
            
            
//            String diasLaboralesString = medico.getDiasLaborales().stream()
//                .map(DiaSemana::name) // Obtener el nombre del enum
//                .collect(Collectors.joining(",")); // Unirlos con coma
            
            
            pstMedico.setString(5, medico.getDiasLaborales());       
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
                medico.setNombre(rs.getString("nombre"));
                medico.setApellido(rs.getString("apellido"));
                
                String diasLaboralesString = rs.getString("diasLaborales"); // Este sería el valor de la base de datos

                // Crear un ArrayList<DiaLaborable>
//                ArrayList<DiaSemana> diasLaborables = new ArrayList<>();

                // Dividir la cadena y convertir a enum
//                Arrays.stream(diasLaboralesString.split(","))
//                      .map(String::trim) // Eliminar espacios en blanco, si los hay
//                      .forEach(dia -> {
//                          try {
//                              diasLaborables.add(DiaSemana.valueOf(dia));
//                          } catch (IllegalArgumentException e) {
//                              System.out.println("Día no válido: " + dia);
//                          }
//                      });

                medico.setDiasLaborales(diasLaboralesString);
                
                
                
                
                
                
                
                
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
                medico.setNombre(rs.getString("nombre"));
                medico.setApellido(rs.getString("apellido"));

                
                String diasLaboralesString = rs.getString("diasLaborales"); // Este sería el valor de la base de datos

                // Crear un ArrayList<DiaLaborable>
                //ArrayList<DiaSemana> diasLaborables = new ArrayList<>();

                // Dividir la cadena y convertir a enum
//                Arrays.stream(diasLaboralesString.split(","))
//                      .map(String::trim) // Eliminar espacios en blanco, si los hay
//                      .forEach(dia -> {
//                          try {
//                              diasLaborables.add(DiaSemana.valueOf(dia));
//                          } catch (IllegalArgumentException e) {
//                              System.out.println("Día no válido: " + dia);
//                          }
//                      });

                medico.setDiasLaborales(diasLaboralesString);
                
                
                medico.setAhosExp(rs.getInt("anhosExp"));
              //  medico.setActivo(rs.getBoolean("activo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medico;
    }
    
    @Override
    public ArrayList<Medico> listarPorEspecialidad(String especialidad) {
        ArrayList<Medico> listaMedicos = new ArrayList<>();

        // Definir la consulta SQL usando el procedimiento almacenado
        sql = "{CALL SeleccionarMedicoPorEspecialidad(?)}";

        try (Connection con = DBPoolManager.getInstance().getConnection();
             PreparedStatement pstMedico = con.prepareStatement(sql)) {

            // Establecer el parámetro para la especialidad
            pstMedico.setString(1, especialidad);

            // Ejecutar la consulta y obtener el resultado
            ResultSet rs = pstMedico.executeQuery();

            // Iterar sobre el ResultSet para construir la lista de médicos
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setIdMedico(rs.getInt("idMedico"));

                // Crear y asignar el objeto Especialidad
                Especialidad espe = new Especialidad();
                espe.setNombre(especialidad);
                medico.setEspecialidad(espe);

                // Asignar otros atributos del médico
                medico.setNumColegiatura(rs.getString("numColegiatura"));
                medico.setHoraInicioTrabajo(rs.getTime("horaInicioTrabajo").toLocalTime());
                medico.setHoraFinTrabajo(rs.getTime("horaFinTrabajo").toLocalTime());
                medico.setDiasLaborales(rs.getString("diasLaborales"));
                medico.setAhosExp(rs.getInt("anhosExp"));
                medico.setActivo(rs.getBoolean("activo"));

                // Añadir el médico a la lista
                listaMedicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción si ocurre un error
            System.out.println("Error en la base de datos: " + e.getMessage());
        }

        return listaMedicos; // Retornar la lista de médicos filtrada por especialidad
    }

    
}
