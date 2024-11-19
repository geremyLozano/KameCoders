package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AdministradorMySQL;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;


public class Principal_1 {
    public static void main(String[] args) throws ParseException {
        AuxiliarDAO dao = new AuxiliarMySQL();
        Auxiliar auxiliar = dao.obtenerPorId1(82);

        // Verificar si se ha obtenido un auxiliar válido y mostrar los resultados
        if (auxiliar != null) {
            System.out.println("ID Auxiliar: " + auxiliar.getIdAuxiliar());
            System.out.println("ID Especialidad: " + (auxiliar.getEspecialidad() != null ? auxiliar.getEspecialidad().getIdEspecialidad() : "N/A"));
            System.out.println("DNI: " + auxiliar.getDNI());
            System.out.println("Nombre: " + auxiliar.getNombre());
            System.out.println("Apellido: " + auxiliar.getApellido());
            System.out.println("Correo Electrónico: " + auxiliar.getCorreoElectronico());
            System.out.println("Número de Teléfono: " + auxiliar.getNumTelefono());
            System.out.println("Dirección: " + auxiliar.getDireccion());
            System.out.println("Fecha de Nacimiento: " + auxiliar.getFechaNacimiento());
            System.out.println("Género: " + auxiliar.getGenero());
            System.out.println("Activo: " + auxiliar.isActivo());
        } else {
            System.out.println("No se encontró un auxiliar con el ID especificado o el auxiliar no está activo.");
        }
    }
}
