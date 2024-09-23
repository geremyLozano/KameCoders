package logica;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


public class CitaMedica {
    private String idCitaMedica;
    private TipoCita tipo;
    private EstadoCita estado;
    private Date fecha;
    private LocalTime hora;
    private Medico medico;
    private Paciente paciente; //String DNI_paciente;
    private String plataforma;
    private String enlace;
    private LocalTime duracion;
    private ReporteMedico reporte;
    private ArrayList<Procedimiento>procedimientos;
    private int numeroAmbiente;
    private Pago pago;

    public CitaMedica() {
        procedimientos = new ArrayList<>();
    }
    //Dentro de CitaMedica, parametro String DNI_paciente
    public CitaMedica(String idCitaMedica, TipoCita tipo, EstadoCita estado, Date fecha, LocalTime hora, Medico medico, Paciente paciente, String plataforma, String enlace, LocalTime duracion, ReporteMedico reporte, ArrayList<Procedimiento> procedimientos, int numeroAmbiente, Pago pago) {
        this.idCitaMedica = idCitaMedica;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.medico = medico;
        this.paciente = paciente; // this.DNI_paciente = DNI_paciente;
        this.plataforma = plataforma;
        this.enlace = enlace;
        this.duracion = duracion;
        this.reporte = reporte;
        this.procedimientos = procedimientos;
        this.numeroAmbiente = numeroAmbiente;
        this.pago = pago;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    //public void setDNI_paciente(String DNI_paciente) {
    //    this.DNI_paciente = DNI_paciente;
    //}

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
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

    public ReporteMedico getReporte() {
        return reporte;
    }

    public void setReporte(ReporteMedico reporte) {
        this.reporte = reporte;
    }

    public ArrayList<Procedimiento> getProcedimientos() {
        return procedimientos;
    }

    public void setProcedimientos(ArrayList<Procedimiento> procedimientos) {
        this.procedimientos = procedimientos;
    }

    public int getNumeroAmbiente() {
        return numeroAmbiente;
    }

    public void setNumeroAmbiente(int numeroAmbiente) {
        this.numeroAmbiente = numeroAmbiente;
    }

    public String getId() {
        return idCitaMedica;
    }

    public void setId(String idCitaMedica) {
        this.idCitaMedica = idCitaMedica;
    }

    public TipoCita getTipo() {
        return tipo;
    }

    public void setTipo(TipoCita tipo) {
        this.tipo = tipo;
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

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    
}
