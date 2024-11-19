package pe.edu.pucp.citamedica.main;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.dao.UsuarioDAO;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;
import pe.edu.pucp.citamedica.mysql.UsuarioMySQL;


public class Principal_6 {
    public static MedicoDAO dao;
    public static void main(String[] args) {
        List<Medico> medicos = generarMedicos(3); // Generar una lista de 5 médicos
        Usuario usuario;
        dao=new MedicoMySQL();
        for (Medico medico : medicos) {
            usuario = new Usuario(); // Usar el DNI como username y una contraseña de ejemplo
            usuario.setUsername(medico.getDNI());
            usuario.setContrasenha("password123");
            insertarMedico(medico, usuario);
        }
    }
    
    public static List<Medico> generarMedicos(int cantidad) {
        List<Medico> medicos = new ArrayList<>();
        Random random = new Random();

        // Nombres y apellidos menos comunes
        String[] nombres = {"Lucía", "Joaquín", "Sofía", "Emilio", "Valentina", "Hugo", "Camila", "Mateo", "Ariadna", "Simón"};
        String[] apellidos = {"González", "Pérez", "López", "Torres", "Martínez", "Ramírez", "Fernández", "Ortega", "Navarro", "Cruz"};
        
        // Combinaciones de días laborales con al menos tres días y separados por guiones
        String[] diasLaborales = {
            "Lunes-Martes-Miércoles", 
            "Lunes-Miércoles-Viernes", 
            "Martes-Jueves-Sábado", 
            "Lunes-Miércoles-Viernes-Domingo",
            "Martes-Jueves-Sábado-Domingo",
            "Lunes-Martes-Viernes",
            "Lunes-Miércoles-Jueves",
            "Martes-Viernes-Sábado",
            "Lunes-Jueves-Sábado"
        };

        for (int i = 0; i < cantidad; i++) {
            String dni = String.format("%08d", random.nextInt(100000000));
            String nombre = nombres[random.nextInt(nombres.length)];
            String apellido = apellidos[random.nextInt(apellidos.length)];
            String correo = nombre.toLowerCase() + "." + apellido.toLowerCase() + "@ejemplo.com";
            int telefono = 900000000 + random.nextInt(10000000); // Número de teléfono aleatorio
            String direccion = "Calle " + (random.nextInt(100) + 1);
            Date fechaNacimiento = new Date(90, random.nextInt(12), random.nextInt(28) + 1);
            char genero = random.nextBoolean() ? 'M' : 'F';
            String colegiatura = String.valueOf(10000 + random.nextInt(90000));
            LocalTime horaInicio = LocalTime.of(14, 0);
            LocalTime horaFin = LocalTime.of(20, 0);
            String diasLab = diasLaborales[random.nextInt(diasLaborales.length)];
            int anhosExp = 1 + random.nextInt(21); // Experiencia aleatoria entre 0 y 20 años
//            int especialidad = 18 + random.nextInt(12); // ID de especialidad entre 18 y 29
            int especialidad = 21; // ID de especialidad entre 18 y 29
            
            Medico medico = new Medico();
            medico.setDNI(dni);
            medico.setNombre(nombre);
            medico.setApellido(apellido);
            medico.setCorreoElectronico(correo);
            medico.setNumTelefono(telefono);
            medico.setDireccion(direccion);
            medico.setFechaNacimiento(fechaNacimiento);
            medico.setGenero(genero);
            medico.setNumColegiatura(colegiatura);
            medico.setHoraFinTrabajo(horaFin);
            medico.setHoraInicioTrabajo(horaInicio);
            medico.setDiasLaborales(diasLab);
            medico.setAhosExp(anhosExp);
            Especialidad aux = new Especialidad();
            aux.setIdEspecialidad(especialidad);
            medico.setEspecialidad(aux);
            medicos.add(medico);
        }

        return medicos;
    }

    public static void insertarMedico(Medico medico, Usuario usuario) {
        int resultado = dao.insertar(medico, usuario);

        if (resultado > 0) {
            System.out.println("Médico insertado con éxito: " + medico.getNombre() + " " + medico.getApellido());
        } else {
            System.out.println("Error al insertar el médico: " + medico.getNombre() + " " + medico.getApellido());
        }
    }
}
