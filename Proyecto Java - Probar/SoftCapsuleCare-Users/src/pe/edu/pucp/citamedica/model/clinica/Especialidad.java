package pe.edu.pucp.citamedica.model.clinica;

public class Especialidad {
    private static int contador = 0;

    private int idEspecialidad;
    private String nombre;
    private double costoConsulta;
    private boolean activo;

    public Especialidad() {
    }
 
    public Especialidad(String nombre, double costoConsulta) {
        this.idEspecialidad = ++contador;
        this.nombre = nombre;
        this.costoConsulta = costoConsulta;
        this.activo = true;
    }
    
    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    
    public int getIdEspecialidad(){
        return idEspecialidad;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(double costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
}
