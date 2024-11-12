package pe.edu.pucp.citamedica.model.clinica;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;


public class Medico extends Persona{
    private static int contador = 0;
    private int idMedico;
    private Especialidad especialidad;
    private String numColegiatura;
    private LocalTime horaInicioTrabajo;
    private LocalTime horaFinTrabajo;
    private Date horaIni;
    private Date horaFin;
    private int ahosExp;
    private ArrayList<CitaMedica>citas;
    private boolean activo;
    //private ArrayList<DiaSemana>diasLaborales;
    private String diasLaborales;
    private String horaInicioTrabajoStr;
    private String horaFinTrabajoStr;
    
    public Medico() {
        
        citas = new ArrayList<>();
        //diasLaborales = new ArrayList<>();
    }

    public Medico(Especialidad especialidad, String numColegiatura, LocalTime horaInicioTrabajo,
     LocalTime horaFinTrabajo, int ahosExp, ArrayList<CitaMedica> citas, boolean activo,
      String diasLaborales,String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, String DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idMedico = ++contador;
        this.especialidad = especialidad;
        this.numColegiatura = numColegiatura;
        this.horaInicioTrabajo = horaInicioTrabajo;
        this.horaFinTrabajo = horaFinTrabajo;
        this.ahosExp = ahosExp;
        this.citas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.horaInicioTrabajoStr = horaInicioTrabajo.format(formatter);
        this.horaFinTrabajoStr = horaFinTrabajo.format(formatter);
        //this.diasLaborales = new ArrayList<>();
        this.activo = true;
    }
    
    public Date getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Date horaIni) {
        this.horaIni = horaIni;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
    public int getIdMedico(){
        return idMedico;
    }
    public void setIdMedico(int id){
        this.idMedico = id;
    }
    
//    public ArrayList<DiaSemana> getDiasLaborales() {
//        return new ArrayList<DiaSemana>(diasLaborales);
//    }
    
    public String getDiasLaborales(){
        return diasLaborales;
    }

//    public void setDiasLaborales(ArrayList<DiaSemana> diasLaborales) {
//        this.diasLaborales = diasLaborales;
//    }
    
    public void setDiasLaborales(String diasLaborales){
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
        this.horaInicioTrabajoStr = horaInicioTrabajo.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public LocalTime getHoraFinTrabajo() {
        return horaFinTrabajo;
    }

    public void setHoraFinTrabajo(LocalTime horaFinTrabajo) {
        this.horaFinTrabajo = horaFinTrabajo;
        this.horaFinTrabajoStr = horaFinTrabajo.format(DateTimeFormatter.ofPattern("HH:mm"));
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
    
    public void setHoraInicioTrabajoStr(String horaInicioTrabajoStr) {
        this.horaInicioTrabajoStr = horaInicioTrabajoStr;
    }
    
    public void setHoraFinTrabajoStr(String horaFinTrabajoStr) {
        this.horaFinTrabajoStr = horaFinTrabajoStr;
    }
    
    public String getHoraInicioTrabajoStr() {
        return horaInicioTrabajoStr;
    }

    public String getHoraFinTrabajoStr() {
        return horaFinTrabajoStr;
    }
    
    
    
}
