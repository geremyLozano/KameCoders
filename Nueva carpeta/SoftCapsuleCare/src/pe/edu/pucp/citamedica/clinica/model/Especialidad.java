package pe.edu.pucp.citamedica.clinica.model;

public class Especialidad {
    private static int contador = 0;

    private int idEspecialidad;
    private String nombre;
    private double costoConsulta;

    public Especialidad() {
    }
 
    public Especialidad(String nombre, double costoConsulta) {
        this.idEspecialidad = ++contador;
        this.nombre = nombre;
        this.costoConsulta = costoConsulta;
    }
<<<<<<< HEAD

=======
    
    public Especialidad(){}
    
>>>>>>> bef43f378d4329cc4505c281b58d7979a13aaf29
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
    
}
