package pe.edu.pucp.citamedica.usuario.model;
import pe.edu.pucp.citamedica.paciente.model.Persona;

import java.util.Date;

public class Usuario {
    private static int contador = 0;

    private int idUsuario;
    private int username;
    private String contrasenha;
    private Persona datosPersonales;

    public Usuario() {
    }

    // Incluimos el campo apellido en el constructor
    public Usuario(String contrasenha, Persona datosPersonales, int DNI) {
        // Llamamos al constructor de Persona pasando el apellido
        this.idUsuario = ++contador;
        this.username = DNI;  // Asignamos el DNI como username
        this.contrasenha = contrasenha;
        this.datosPersonales = datosPersonales;
    }

    // Getters y Setters
    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public Persona getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(Persona datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    public void actualizarInformacion() {
        // Implementar lógica de actualización
    }
}
