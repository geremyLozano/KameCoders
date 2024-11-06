package pe.edu.pucp.citamedica.model.consultas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class CitaMedica {
    private int idCitaMedica;
    private TipoCita tipo;
    private EstadoCita estado;
    private int idPaciente;
    private int idreporteMedico; //String DNI_paciente;
    private int idHistorialMedico;
    private int idMedico;
    private Date fecha;
    private LocalTime hora;
    private String plataforma;
    private String enlace;
    private LocalTime duracion;
    private int numeroAmbiente;
    private int idPago;
    private boolean activo;
    private String horaStr;
    private String duracionStr;
    private String fechaStr;
    private String tipoStr;
    private String estadoStr;

    public CitaMedica() {
    }
    
    public CitaMedica(int idCitaMedica, TipoCita tipo, EstadoCita estado, Date fecha, LocalTime hora, String plataforma, String enlace, LocalTime duracion, int numeroAmbiente, boolean activo) {
        this.idCitaMedica = idCitaMedica;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.plataforma = plataforma;
        this.enlace = enlace;
        this.duracion = duracion;
        this.numeroAmbiente = numeroAmbiente;
        this.activo = activo;
    }

    public int getIdCitaMedica() {
        return idCitaMedica;
    }

    public void setIdCitaMedica(int idCitaMedica) {
        this.idCitaMedica = idCitaMedica;
    }

    public TipoCita getTipo() {
        return tipo;
    }

    public void setTipo(TipoCita tipo) {
        this.tipo = tipo;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdreporteMedico() {
        return idreporteMedico;
    }

    public void setIdreporteMedico(int idreporteMedico) {
        this.idreporteMedico = idreporteMedico;
    }

    public int getIdHistorialMedico() {
        return idHistorialMedico;
    }

    public void setIdHistorialMedico(int idHistorialMedico) {
        this.idHistorialMedico = idHistorialMedico;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
        this.horaStr = hora.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
        this.duracionStr = duracion.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public int getNumeroAmbiente() {
        return numeroAmbiente;
    }

    public void setNumeroAmbiente(int numeroAmbiente) {
        this.numeroAmbiente = numeroAmbiente;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public void setHoraStr(String horaStr){
        this.horaStr = horaStr;
    }
    
    public String getHoraStr(){
        return horaStr;
    }
    
    public void setDuracionStr(String duracionStr){
        this.duracionStr = duracionStr;
    }
    
    public String getDuracionStr(){
        return duracionStr;
    }
    
    public void setFechaStr(String fechaStr){
        this.fechaStr = fechaStr;
    }
    
    public String getFechaStr(){
        return fechaStr;
    }
    
    public void setTipoStr(String tipoStr){
        this.tipoStr = tipoStr;
    }
    
    public String getTipoStr(){
        return tipoStr;
    }
    
    public void setEstadoStr(String estadoStr){
        this.estadoStr = estadoStr;
    }
    
    public String getEstadoStr(){
        return estadoStr;
    }

}
