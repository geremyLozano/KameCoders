package pe.edu.pucp.citamedica.model.consultas;

import pe.edu.pucp.citamedica.model.procedimiento.Procedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Paciente;


public class CitaMedica {
    private String idCitaMedica;
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

    public CitaMedica(String idCitaMedica, TipoCita tipo, EstadoCita estado, Date fecha, LocalTime hora, String plataforma, String enlace, LocalTime duracion, int numeroAmbiente, boolean activo) {
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

    public String getIdCitaMedica() {
        return idCitaMedica;
    }

    public void setIdCitaMedica(String idCitaMedica) {
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

}
