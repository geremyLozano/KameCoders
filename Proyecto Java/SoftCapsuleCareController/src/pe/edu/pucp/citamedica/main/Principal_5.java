package pe.edu.pucp.citamedica.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;

public class Principal_5 {
    public static AuxiliarDAO daoAux;
    public static void main(String[] args) {
        List<Auxiliar> auxiliares = generarAuxiliares(5); // Generar una lista de 3 auxiliares
        Usuario usuario;
        daoAux = new AuxiliarMySQL();

        for (Auxiliar auxiliar : auxiliares) {
            usuario = new Usuario(); // Usar el DNI como username y una contraseña de ejemplo
            usuario.setUsername(auxiliar.getDNI());
            usuario.setContrasenha("password123");
            insertarAuxiliar(auxiliar, usuario);
        }
    }

    public static List<Auxiliar> generarAuxiliares(int cantidad) {
        List<Auxiliar> auxiliares = new ArrayList<>();
        Random random = new Random();

        // Nombres y apellidos únicos
        String[] nombres = {"Emma", "Lorenzo", "Paula", "Marcos", "Elena", "Tomás", "Clara", "Diego", "Alba", "Iván"};
        String[] apellidos = {"Delgado", "Cabrera", "Campos", "Moreno", "Reyes", "Fuentes", "Rojas", "Paredes", "Luna", "Galindo"};

        for(int especialidad = 26; especialidad<28; especialidad++){
            for (int i = 0; i < cantidad; i++) {
                String dni = String.format("%08d", random.nextInt(100000000));
                String nombre = nombres[random.nextInt(nombres.length)];
                String apellido = apellidos[random.nextInt(apellidos.length)];
                String correo = nombre.toLowerCase() + "." + apellido.toLowerCase() + "@ejemplo.com";
                int telefono = 900000000 + random.nextInt(10000000); // Número de teléfono aleatorio
                String direccion = "Calle " + (random.nextInt(100) + 1);
                Date fechaNacimiento = new Date(95, random.nextInt(12), random.nextInt(28) + 1);
                char genero = random.nextBoolean() ? 'M' : 'F';

                Auxiliar auxiliar = new Auxiliar();
                auxiliar.setDNI(dni);
                auxiliar.setNombre(nombre);
                auxiliar.setApellido(apellido);
                auxiliar.setCorreoElectronico(correo);
                auxiliar.setNumTelefono(telefono);
                auxiliar.setDireccion(direccion);
                auxiliar.setFechaNacimiento(fechaNacimiento);
                auxiliar.setGenero(genero);
                Especialidad aux = new Especialidad();
                aux.setIdEspecialidad(especialidad);
                auxiliar.setEspecialidad(aux);
                auxiliares.add(auxiliar);
            }
        }
        return auxiliares;
    }

    public static void insertarAuxiliar(Auxiliar auxiliar, Usuario usuario) {
        int resultado = daoAux.insertar(auxiliar, usuario);

        if (resultado > 0) {
            System.out.println("Auxiliar insertado con exito: " + auxiliar.getNombre() + " " + auxiliar.getApellido());
        } else {
            System.out.println("Error al insertar el auxiliar: " + auxiliar.getNombre() + " " + auxiliar.getApellido());
        }
    }

}
