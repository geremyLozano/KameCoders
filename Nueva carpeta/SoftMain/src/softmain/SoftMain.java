package softmain;

import pe.edu.pucp.citamedica.paciente.model.Paciente;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class SoftMain {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        // Creación de un paciente
        Paciente paciente = new Paciente();
        // Datos de Persona-Paciente
        paciente.setDNI("7654321");
        paciente.setNombre("Maria");
        paciente.setApellido("Perez");
        paciente.setCorreoElectronico("mariaperez@gmail.com");
        paciente.setNumTelefono(990990345);
        paciente.setDireccion("San Miguel, 1234");
        paciente.setFechaNacimiento(sdf.parse("19-10-1987"));
        // Datos de Paciente
        paciente.setHistorialActivo(true);
        paciente.setGenero('F');

        // Insertar paciente en la base de datos*/
        PacienteDAO daoPaciente = new PacienteMySQL();
        int resultado = daoPaciente.insertar(paciente);

        if (resultado == 1) {
            System.out.println("El paciente ha sido registrado con éxito");
        } else {
            System.out.println("Hubo un error al registrar el paciente");
        }
        
        Paciente paciente2 = new Paciente();
        
        paciente2.setDNI("1354863");
        paciente2.setNombre("Lorenzo");
        paciente2.setApellido("Rojas");
        paciente2.setCorreoElectronico("lorenzorojas@gmail.com");
        paciente2.setNumTelefono(985636852);
        paciente2.setDireccion("Lince, 1888");
        paciente2.setFechaNacimiento(sdf.parse("20-11-2001"));
        // Datos de Paciente
        paciente2.setHistorialActivo(true);
        paciente2.setGenero('M');

        // Insertar paciente en la base de datos
//        PacienteDAO daoPaciente = new PacienteMySQL();
//        int resultado = daoPaciente.insertar(paciente2);

        if (resultado == 1) {
            System.out.println("El paciente2 ha sido registrado con éxito");
        } else {
            System.out.println("Hubo un error al registrar el paciente");
        }
        //Probando modificar paciente
//        PacienteDAO daoPaciente = new PacienteMySQL();
//        int resultado;
        resultado = daoPaciente.modificar(paciente);
        if(resultado == 1){
            System.out.println("Paciente modificado correctamente");
        }else{
            System.out.println("Paciente no modificado ");
        }
        
        ///////////////////////////////////////
        //Probando eliminar paciente2 
//      PacienteDAO daoPaciente = new PacienteMySQL();
//      int resultado = daoPaciente.eliminar(3);
//        
//      if(resultado == 1){
//          System.out.println("El paciente2 ha sido eliminado con éxito");
//      }else{
//          System.out.println("Hubo un error al eliminar el paciente");
//      }


        
        
    }
}
