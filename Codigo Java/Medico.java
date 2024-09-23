package logica;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


public class Medico extends Persona{
    private static int contador = 0;

    private int idMedico;
    private Especialidad especialidad;
    private String numColegiatura;
    private LocalTime horaInicioTrabajo;
    private LocalTime horaFinTrabajo;
    private int ahosExp;
    private ArrayList<CitaMedica>citas;
    private boolean activo;
    private ArrayList<DiaSemana>diasLaborales;

    public Medico() {
        citas = new ArrayList<>();
        diasLaborales = new ArrayList<>();
    }

    public Medico(Especialidad especialidad, String numColegiatura, LocalTime horaInicioTrabajo,
     LocalTime horaFinTrabajo, int ahosExp, ArrayList<CitaMedica> citas, boolean activo,
      ArrayList<DiaSemana> diasLaborales,String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, int DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idMedico = ++contador;
        this.especialidad = especialidad;
        this.numColegiatura = numColegiatura;
        this.horaInicioTrabajo = horaInicioTrabajo;
        this.horaFinTrabajo = horaFinTrabajo;
        this.ahosExp = ahosExp;
        this.citas = new ArrayList<>();
        this.diasLaborales = new ArrayList<>();
        this.activo = activo;
    }

    public ArrayList<DiaSemana> getDiasLaborales() {
        return new ArrayList<DiaSemana>(diasLaborales);
    }

    public void setDiasLaborales(ArrayList<DiaSemana> diasLaborales) {
        this.diasLaborales = diasLaborales;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumColegiatura() {
        return numColegiatura;
    }

    public void setNumColegiatura(String numColegiatura) {
        this.numColegiatura = numColegiatura;
    }

    public LocalTime getHoraInicioTrabajo() {
        return horaInicioTrabajo;
    }

    public void setHoraInicioTrabajo(LocalTime horaInicioTrabajo) {
        this.horaInicioTrabajo = horaInicioTrabajo;
    }

    public LocalTime getHoraFinTrabajo() {
        return horaFinTrabajo;
    }

    public void setHoraFinTrabajo(LocalTime horaFinTrabajo) {
        this.horaFinTrabajo = horaFinTrabajo;
    }

    public int getAhosExp() {
        return ahosExp;
    }

    public void setAhosExp(int ahosExp) {
        this.ahosExp = ahosExp;
    }

    public ArrayList<CitaMedica> getCitas() {
        return new ArrayList<>(citas);
    }

    public void setCitas(CitaMedica c) {
        this.citas.add(c);
    }

    @Override
    public void actualizarInformacion() {
        
    }
    
    public void generarReporteMedico(String diagnostico, String tratamiento, String enfermedad,
                                     Date fecha){
        
    }
}
