package pe.edu.pucp.citamedica.mysql;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.stream.Collectors;
import pe.edu.pucp.citamedica.model.clinica.DiaSemana;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.dbmanager.config.DBPoolManager;
import pe.edu.pucp.seguridad.PasswordHash;

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
        int resultado = -1;
        String hashedPassword;

        // Intentamos generar el hash de la contraseña
        try {
            hashedPassword = PasswordHash.hashPassword(usuario.getContrasenha());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error al hashear la contraseña: " + e.getMessage());
            return resultado;
        }

        try {
            con = DBPoolManager.getInstance().getConnection();
            sql = "{CALL InsertarMedico(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            cst = con.prepareCall(sql);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.setString(2, medico.getDNI());
            cst.setString(3, medico.getNombre());
            cst.setString(4, medico.getApellido());
            cst.setString(5, medico.getCorreoElectronico());
            cst.setInt(6, medico.getNumTelefono());
            cst.setString(7, medico.getDireccion());
            java.sql.Date sqlDate = new java.sql.Date(medico.getFechaNacimiento().getTime());
            cst.setDate(8, sqlDate);
            cst.setString(9, String.valueOf(medico.getGenero()));
            cst.setString(10, medico.getNumColegiatura());
            cst.setTime(11, Time.valueOf(medico.getHoraInicioTrabajo()));
            cst.setTime(12, Time.valueOf(medico.getHoraFinTrabajo()));
            cst.setString(13, medico.getDiasLaborales());
            cst.setInt(14, medico.getAhosExp());
            cst.setBoolean(15, true);
            cst.setInt(16, medico.getEspecialidad().getIdEspecialidad());
            
            cst.setString(17, hashedPassword); // Usamos la contraseña hasheada

            resultado = cst.executeUpdate(); // Ejecutamos la inserción
            
            medico.setIdMedico(cst.getInt(1));

        } catch (SQLException e) {
            System.out.print("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.print("Error general: " + e.getMessage());
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
                especialidad.setNombre(rs.getString("especialidad"));
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
                medico.setNombre(rs.getString("nombre"));
                medico.setApellido(rs.getString("apellido"));
                // Añadir el médico a la lista
                listaMedicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Manejar la excepción si ocurre un error
            System.out.println("Error en la base de datos: " + e.getMessage());
        }

        return listaMedicos; // Retornar la lista de médicos filtrada por especialidad
    }





   @Override
    public int insertarMedico1(Medico medico) {
        int resultado = 0;
        int idPersona = 0;
        
        // Suponiendo que recibes `Date` o `DateTime` en el servicio y solo necesitas la hora
        LocalTime horaInicio = medico.getHoraIni().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime horaFin = medico.getHoraFin().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        String queryPersona = "INSERT INTO Persona(DNI, nombre, apellido, correoElectronico, numTelefono, direccion, fechaNacimiento, genero) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryMedico = "INSERT INTO Medico(idMedico,numColegiatura,horaInicioTrabajo,horaFinTrabajo,anhosExp,"
                + " activo,diasLaborales) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBPoolManager.getInstance().getConnection(); 
                PreparedStatement psPersona = conn.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS); PreparedStatement psMedico = conn.prepareStatement(queryMedico)) {

            // Insertar en la tabla Persona
            psPersona.setString(1, medico.getDNI());
            psPersona.setString(2, medico.getNombre());
            psPersona.setString(3, medico.getApellido());
            psPersona.setString(4, medico.getCorreoElectronico());
            psPersona.setInt(5, medico.getNumTelefono());
            psPersona.setString(6, medico.getDireccion());
            psPersona.setDate(7, new java.sql.Date(medico.getFechaNacimiento().getTime()));
            psPersona.setString(8, String.valueOf(medico.getGenero()));
            
            System.out.println("Ejecutando inserción en Persona...");
            psPersona.executeUpdate();
            
            try (ResultSet generatedKeys = psPersona.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idPersona = generatedKeys.getInt(1);
                    System.out.println("ID generado para Persona: " + idPersona);
                } else {
                    System.err.println("Fallo al insertar en Persona, no se pudo obtener el ID.");
                    return 0; 
                }
            }

            psMedico.setInt(1, idPersona);
//            psMedico.setInt(2,medico.getEspecialidad().getIdEspecialidad());
            psMedico.setString(2, medico.getNumColegiatura());
//            psMedico.setTime(4, java.sql.Time.valueOf(medico.getHoraInicioTrabajo()));
            psMedico.setTime(3, java.sql.Time.valueOf(horaInicio));
//            psMedico.setTime(5, java.sql.Time.valueOf(medico.getHoraFinTrabajo()));
            psMedico.setTime(4, java.sql.Time.valueOf(horaFin));
            psMedico.setInt(5, medico.getAhosExp());
            psMedico.setBoolean(6, medico.isActivo());
            psMedico.setString(7, medico.getDiasLaborales());
            resultado = psMedico.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public ArrayList<Medico> listarTodos1() {
        ArrayList<Medico> listaMedicos = new ArrayList<>();

        String sql = "SELECT m.idMedico, m.numColegiatura, m.horaInicioTrabajo, m.horaFinTrabajo, "
                + "m.diasLaborales, m.anhosExp, e.idEspecialidad, e.nombre as EspecialidadNombre, "
                + "p.DNI, p.nombre, p.apellido "
                + "FROM Medico m "
                + "JOIN Persona p ON m.idMedico = p.idPersona "
                + "JOIN Especialidad e ON m.idEspecialidad = e.idEspecialidad WHERE m.activo = true";

        try (Connection con = DBPoolManager.getInstance().getConnection(); PreparedStatement pstMedico = con.prepareStatement(sql); ResultSet rs = pstMedico.executeQuery()) {

            // Iterar sobre cada registro en el ResultSet
            while (rs.next()) {
                // Crear un nuevo objeto Medico
                Medico medico = new Medico();
                medico.setIdMedico(rs.getInt("idMedico"));

                // Crear un objeto Especialidad y asignarlo al Medico
                Especialidad especialidad = new Especialidad();
                especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                especialidad.setNombre(rs.getString("EspecialidadNombre"));
                medico.setEspecialidad(especialidad);

                // Asignar atributos heredados de Persona
                medico.setDNI(rs.getString("DNI"));
                medico.setNombre(rs.getString("nombre"));
                medico.setApellido(rs.getString("apellido"));

                // Asignar atributos específicos de Medico
                medico.setNumColegiatura(rs.getString("numColegiatura"));

                // Convertir las horas de inicio y fin de trabajo
                Time horaInicioTrabajoTime = rs.getTime("horaInicioTrabajo");
                Time horaFinTrabajoTime = rs.getTime("horaFinTrabajo");
                if (horaInicioTrabajoTime != null) {
                    medico.setHoraInicioTrabajo(horaInicioTrabajoTime.toLocalTime());
                } else {
                    medico.setHoraInicioTrabajo(null);
                }
                if (horaFinTrabajoTime != null) {
                    medico.setHoraFinTrabajo(horaFinTrabajoTime.toLocalTime());
                } else {
                    medico.setHoraFinTrabajo(null);
                }

                // Asignar otros atributos de Medico
                medico.setDiasLaborales(rs.getString("EspecialidadNombre"));
                medico.setAhosExp(rs.getInt("anhosExp"));

                // Agregar el medico a la lista
                listaMedicos.add(medico);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar la excepción si ocurre un error
        }

        return listaMedicos;
    }
 
    @Override
    public Medico obtenerPorId1(int idMedico) {
        Medico medico = new Medico();
        String sql = "SELECT m.idMedico, m.numColegiatura, m.horaInicioTrabajo, m.horaFinTrabajo, "
                + "m.diasLaborales, m.anhosExp, e.idEspecialidad, "
                + "p.DNI, p.nombre, p.apellido, p.correoElectronico, p.numTelefono, p.direccion, p.fechaNacimiento, "
                + "p.genero "
                + "FROM Medico m "
                + "JOIN Persona p ON m.idMedico = p.idPersona "
                + "JOIN Especialidad e ON m.idEspecialidad = e.idEspecialidad WHERE m.activo = true AND m.idMedico = ?";

        try (Connection con = DBPoolManager.getInstance().getConnection(); PreparedStatement pstMedico = con.prepareStatement(sql)) {

            pstMedico.setInt(1, idMedico); 

            try (ResultSet rs = pstMedico.executeQuery()) {
                if (rs.next()) { 
                    medico.setIdMedico(rs.getInt("idMedico"));

                    Especialidad especialidad = new Especialidad();
                    especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
                    medico.setEspecialidad(especialidad);

                    medico.setDNI(rs.getString("DNI"));
                    medico.setNombre(rs.getString("nombre"));
                    medico.setApellido(rs.getString("apellido"));
                    medico.setCorreoElectronico(rs.getString("correoElectronico"));
                    medico.setNumTelefono(rs.getInt("numTelefono"));
                    medico.setDireccion(rs.getString("direccion"));
                    medico.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                    medico.setGenero(rs.getString("genero").charAt(0));

                    medico.setNumColegiatura(rs.getString("numColegiatura"));

                    // Convertir Time a LocalTime
                    Time horaInicioTrabajoTime = rs.getTime("horaInicioTrabajo");
                    Time horaFinTrabajoTime = rs.getTime("horaFinTrabajo");
                    if (horaInicioTrabajoTime != null) {
                        medico.setHoraInicioTrabajo(horaInicioTrabajoTime.toLocalTime());
                    } else {
                        medico.setHoraInicioTrabajo(null);
                    }
                    if (horaFinTrabajoTime != null) {
                        medico.setHoraFinTrabajo(horaFinTrabajoTime.toLocalTime());
                    } else {
                        medico.setHoraFinTrabajo(null);
                    }

                    medico.setDiasLaborales(rs.getString("diasLaborales"));
                    medico.setAhosExp(rs.getInt("anhosExp"));
                    medico.setActivo(rs.getBoolean("activo"));
                }
            }

        } catch (SQLException e) {
            // Usar un logger o manejar la excepción de forma más detallada
            e.printStackTrace();
        }

        return medico;
    }
 
    
}
