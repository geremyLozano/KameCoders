package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AdministradorMySQL;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;


public class Principal_1 {
    public static void main(String[] args) throws ParseException {
        Usuario usuario10 = new Usuario();
usuario10.setContrasenha("mypassword000");

Medico medico10 = new Medico();
medico10.setDNI("01234569");
medico10.setNombre("Roberto");
medico10.setApellido("Álvarez");
medico10.setCorreoElectronico("roberto.alvarez@mail.com");
medico10.setNumTelefono(987321654);
medico10.setDireccion("Calle del Mar 303");

try {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date fechaNacimiento = sdf.parse("10/05/1983");
    medico10.setFechaNacimiento(fechaNacimiento);
} catch (Exception e) {
    e.printStackTrace();
}

medico10.setGenero('M');
medico10.setNumColegiatura("UV321456");
medico10.setHoraInicioTrabajo(LocalTime.of(10, 0));
medico10.setHoraFinTrabajo(LocalTime.of(17, 0));
medico10.setDiasLaborales("Martes, Jueves, Sábado");
medico10.setAhosExp(9);

Especialidad especialidad10 = new Especialidad();
especialidad10.setIdEspecialidad(25);  // Especialidad 10
medico10.setEspecialidad(especialidad10);

MedicoDAO medicoService = new MedicoMySQL();
int resultado10 = medicoService.insertar(medico10, usuario10);

if (resultado10 != -1) {
    System.out.println("El médico 10 fue insertado correctamente con ID: " + medico10.getIdMedico());
} else {
    System.out.println("Hubo un error al insertar el médico 10.");
}

    }
}
