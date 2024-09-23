package pe.edu.pucp.citamedica.paciente.model;

import pe.edu.pucp.citamedica.clinica.model.Medico;
import pe.edu.pucp.citamedica.clinica.model.Auxiliar;
import pe.edu.pucp.citamedica.clinica.model.Administrador;
import pe.edu.pucp.citamedica.usuario.model.Usuario;

import java.util.Date;

public abstract class Persona {  // Marcamos la clase como abstracta
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private int numTelefono;
    private String direccion;
    private Date fechaNacimiento;
    private char genero; // M O F
    private int DNI;

    public Persona() {
    }

    public Persona(String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, int DNI) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.numTelefono = numTelefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.DNI = DNI;
    }
    //nombre,apellido,correoElectronico,numTelefono,direcccion,fechaNacimiento,genero,DNI
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(int numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    // Declaramos el método como abstracto ya que Persona es abstracta
    public abstract void actualizarInformacion();
}
