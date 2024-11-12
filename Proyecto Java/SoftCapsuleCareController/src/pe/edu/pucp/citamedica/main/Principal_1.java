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
        MedicoDAO medicoService = new MedicoMySQL();

        // Llamar al método listarTodos1 para obtener la lista de médicos
        ArrayList<Medico> listaMedicos = medicoService.listarTodos1();

        // Comprobar si se ha obtenido alguna lista y, si es así, mostrar los detalles de cada médico
        if (listaMedicos.isEmpty()) {
            System.out.println("No se encontraron médicos activos en la base de datos.");
        } else {
            System.out.println("Lista de Médicos activos:");
            for (Medico medico : listaMedicos) {
                System.out.println("ID Médico: " + medico.getIdMedico());
                System.out.println("DNI: " + medico.getDNI());
                System.out.println("Nombre: " + medico.getNombre());
                System.out.println("Apellido: " + medico.getApellido());
                System.out.println("Número de Colegiatura: " + medico.getNumColegiatura());
                System.out.println("Especialidad ID: " + medico.getEspecialidad().getIdEspecialidad());
                
                // Comprobar y mostrar las horas de trabajo si no son nulas
                if (medico.getHoraInicioTrabajo() != null) {
                    System.out.println("Hora de Inicio de Trabajo: " + medico.getHoraInicioTrabajo());
                }
                if (medico.getHoraFinTrabajo() != null) {
                    System.out.println("Hora de Fin de Trabajo: " + medico.getHoraFinTrabajo());
                }

                System.out.println("Días Laborales: " + medico.getDiasLaborales());
                System.out.println("Años de Experiencia: " + medico.getAhosExp());
                System.out.println("-------------------------------------------------");
            }
        }
    }
}
