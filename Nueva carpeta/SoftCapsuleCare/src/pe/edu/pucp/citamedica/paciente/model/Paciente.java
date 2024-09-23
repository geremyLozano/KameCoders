package pe.edu.pucp.citamedica.paciente.model;
import pe.edu.pucp.citamedica.consultas.model.CitaMedica;
import pe.edu.pucp.citamedica.consultas.model.HistorialMedico;
import pe.edu.pucp.citamedica.comunicacion.model.Comunicacion;
import pe.edu.pucp.citamedica.consultas.model.TipoCita;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Paciente extends Persona {
    private static int contador = 0;

    private int idPaciente;
    private boolean historialActivo;
    private ArrayList<CitaMedica> citas;
    private HistorialMedico historialMedico;
    private ArrayList<Comunicacion> comunicaciones;
    private Aseguradora seguro;

    public Paciente() {
        this.idPaciente = ++contador;
        this.citas = new ArrayList<>();
    }

    public Paciente(boolean historialActivo, ArrayList<CitaMedica> citas, HistorialMedico historialMedico,
                    ArrayList<Comunicacion> comunicaciones, Aseguradora seguro, 
                    String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, int DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idPaciente = ++contador;
        this.historialActivo = historialActivo;
        this.citas = citas != null ? citas : new ArrayList<>();
        this.historialMedico = historialMedico;
        this.comunicaciones = comunicaciones;
        this.seguro = seguro;
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

    public Aseguradora getSeguro() {
        return seguro;
    }

    public void setSeguro(Aseguradora seguro) {
        this.seguro = seguro;
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
}
