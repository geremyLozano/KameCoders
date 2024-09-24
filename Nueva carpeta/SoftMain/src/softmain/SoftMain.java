package softmain;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.citamedica.paciente.model.Paciente;
import pe.edu.pucp.citamedica.dao.PacienteDao;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;

public class SoftMain {
    public static void main(String[] args) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Paciente paciente = new Paciente();
        //Datos de Persona-Paciente
        paciente.setNombre("Maria");
        paciente.setApellido("Perez");
        paciente.setCorreoElectronico("mariaperez@gmail.com");
        paciente.setNumTelefono(990990345);
        paciente.setDireccion("San Miguel, 1234");
        paciente.setFechaNacimiento(sdf.parse("19-10-1987"));
        //Datos de Paciente
        paciente.setHistorialActivo(true);
        paciente.setGenero('F');
        PacienteDao daoPaciente = new PacienteMySQL();
        int resultado = daoPaciente.insertar(paciente);
        if(resultado == 1)
            System.out.println("Se ha registrado con exito");
        else
            System.out.println("Ha ocurrido un error");
    }
}
