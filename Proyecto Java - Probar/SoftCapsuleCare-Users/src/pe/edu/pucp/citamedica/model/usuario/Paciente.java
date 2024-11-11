package pe.edu.pucp.citamedica.model.usuario;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.citamedica.model.consultas.TipoCita;

public class Paciente extends Persona {
    private static int contador = 0;

    private int idPaciente;
    private boolean historialActivo;
    private ArrayList<CitaMedica> citas;
    private HistorialMedico historialMedico;
    private ArrayList<Comunicacion> comunicaciones;
    private ArrayList<Aseguradora> seguros; 
    private boolean activo;
    
    public Paciente() {
        this.idPaciente = ++contador;
        this.citas = new ArrayList<>();
    }

    public Paciente(boolean historialActivo, ArrayList<CitaMedica> citas, HistorialMedico historialMedico,
                    ArrayList<Comunicacion> comunicaciones, ArrayList<Aseguradora> seguros, 
                    String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, String DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idPaciente = ++contador;
        this.historialActivo = historialActivo;
        this.citas = citas != null ? citas : new ArrayList<>();
        this.historialMedico = historialMedico;
        this.comunicaciones = comunicaciones;
        this.seguros = seguros;
        this.activo = true;
    }

    // Getters y Setters

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    
    public boolean getHistorialActivo() {
        return historialActivo;
    }

    public void setHistorialActivo(boolean historialActivo) {
        this.historialActivo = historialActivo;
    }

    public ArrayList<CitaMedica> getCitas() {
        return new ArrayList<>(citas);
    }

    public void setCitas(ArrayList<CitaMedica> citas) {
        this.citas = citas;
    }

    public HistorialMedico getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(HistorialMedico historialMedico) {
        this.historialMedico = historialMedico;
    }

    public ArrayList<Comunicacion> getComunicaciones() {
        return comunicaciones;
    }

    public void setComunicaciones(ArrayList<Comunicacion> comunicaciones) {
        this.comunicaciones = comunicaciones;
    }

    public ArrayList<Aseguradora> getSeguros() {
        return seguros;
    }

    public void setAesugradora(ArrayList<Aseguradora> seguros) {
        this.seguros = seguros;
    }

    @Override
    public void actualizarInformacion() {
        // Lógica para actualizar la información del paciente
    }

    public void programarCita(TipoCita tipo, Date fecha, LocalTime hora, String medico) {
        // Lógica para programar una cita médica
    }

    public void cancelarCita(int idCitaMedica) {
        // Lógica para cancelar una cita médica
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
