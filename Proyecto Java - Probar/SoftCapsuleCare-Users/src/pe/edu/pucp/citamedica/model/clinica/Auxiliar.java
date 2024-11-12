package pe.edu.pucp.citamedica.model.clinica;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import java.util.Date;


public class Auxiliar extends Persona{
    private static int contador = 0;

    private int idAuxiliar;
    private Especialidad especialidad;
    private boolean activo;

    public Auxiliar() {
    }

    public Auxiliar(Especialidad especialidad, boolean activo,
        String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, String DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idAuxiliar = ++contador;
        this.especialidad = especialidad;
        this.activo = true;
    }
    public int getIdAuxiliar(){
        return idAuxiliar;
    }
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    @Override
    public void actualizarInformacion() {
        
    }
    
    public void cargarEvaluacionMedica(){
            
    }

    public void setIdAuxiliar(int idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }
    
}
