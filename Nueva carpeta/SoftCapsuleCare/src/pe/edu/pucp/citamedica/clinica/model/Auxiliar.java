package pe.edu.pucp.citamedica.clinica.model;
import pe.edu.pucp.citamedica.paciente.model.Persona;
import java.util.Date;


public class Auxiliar extends Persona{

    /**
     * @return the idAuxiliar
     */
    public int getIdAuxiliar() {
        return idAuxiliar;
    }

    /**
     * @param idAuxiliar the idAuxiliar to set
     */
    public void setIdAuxiliar(int idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }
    private static int contador = 0;

    private int idAuxiliar;
    private Especialidad especialidad;
    private Medico medico;
    private boolean activo;

    public Auxiliar() {
    }

    public Auxiliar(Especialidad especialidad, Medico medico, boolean activo,
        String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, int DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idAuxiliar = ++contador;
        this.especialidad = especialidad;
        this.medico = medico;
        this.activo = activo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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
    
}
