package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.clinica.DiaSemana;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AdministradorMySQL;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;


public class Principal_2 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        Medico med = new Medico();
        med.setDNI("543534");
        med.setNombre("Alonso");
        med.setApellido("Perez");
        med.setCorreoElectronico("prueba2@hotmail.com");
        med.setNumTelefono(123312);
        med.setDireccion("Av.Brasil 123");
        med.setFechaNacimiento(sdf.parse("23-10-2000"));
        med.setGenero('M');
        med.setNumColegiatura("43242");
        med.setHoraFinTrabajo(LocalTime.of(14, 30));
        med.setHoraInicioTrabajo(LocalTime.of(20, 0));
//        ArrayList<DiaSemana> diasLaborales = new ArrayList<>();
//        diasLaborales.add(DiaSemana.Lunes);
//        diasLaborales.add(DiaSemana.Martes);
//        diasLaborales.add(DiaSemana.Miercoles);
        med.setDiasLaborales("Lunes-Martes-Miercoles");
        med.setAhosExp(5);
        med.setActivo(true);
//        Paciente paciente = new Paciente();
//        PacienteDAO pac = new PacienteMySQL();
        MedicoDAO m = new MedicoMySQL();
//        int resultado = m.insertar(med);

//        Auxiliar auxiliar = new Auxiliar();
//        AuxiliarDAO aux = new AuxiliarMySQL();
//        int resultado = aux.insertar(auxiliar, usuario, med);

//        Administrador administrador = new Administrador();
//        AdministradorDAO admin = new AdministradorMySQL();
//        int resultado = admin.insertar(administrador, usuario, med);
//        if(resultado>0)
//            System.out.println("Se creo el usuario correctamente");
//        else
//            System.out.println("Error en la creacion");
//        
//        Scanner lectura = new Scanner(System.in);
//        String dato = lectura.next();
//        paciente.setHistorialActivo(true);
//        paciente.setNombre("modificarEXITOSO");
//        int mod = pac.modificar(paciente);
//        if(mod>0)
//            System.out.println("Se modifico correctamente");
//        else
//            System.out.println("Error al modificar");
        
        
//        ArrayList<Paciente>lista=pac.listarTodos();
//        for(Paciente p:lista){
//            String fecha = sdf.format(p.getFechaNacimiento());
//            System.out.println("IdPaciente: "+p.getIdPaciente() + "  DNI:"+p.getDNI()+"   "
//                    + "Nombre:"+p.getNombre()+"   Apellido:"+p.getApellido()+
//                    "   Correo:"+p.getCorreoElectronico()+"   FechaNacimiento:"+fecha+
//                    "   historialActivo:"+p.getHistorialActivo());
//            System.out.println("---------------------------------");
//        }
//        int eliminado = pac.eliminar(18);
//        if(eliminado>0)
//            System.out.println("Paciente eliminado correctamente");
//        else
//            System.out.println("Error con el eliminado");
//        Paciente p = pac.obtenerPorId(20);
//        String fecha = sdf.format(p.getFechaNacimiento());
//        System.out.println("IdPaciente: "+p.getIdPaciente() + "  DNI:"+p.getDNI()+"   "
//                    + "Nombre:"+p.getNombre()+"   Apellido:"+p.getApellido()+
//                    "   Correo:"+p.getCorreoElectronico()+"   FechaNacimiento:"+fecha+
//                    "   historialActivo:"+p.getHistorialActivo());
    }
}
