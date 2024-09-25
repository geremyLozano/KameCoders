package pe.edu.pucp.citamedica.procedimiento.model;
import pe.edu.pucp.citamedica.procedimiento.model.EstadoResultado;
import java.util.Date;

public class ResultadoProcedimiento {
    private String idResultado;  // Identificador único para el resultado del procedimiento
    private Procedimiento procedimiento;  // Referencia al Procedimiento que produjo este resultado
    private String observaciones;  // Cualquier comentario o detalle sobre el resultado
    private EstadoResultado estado;  // Completado, Pendiente, Inconcluso, etc.
    private Date fechaResultado;  // Fecha en la que se obtuvieron los resultados

    public ResultadoProcedimiento(){
    }

    // Constructor
    public ResultadoProcedimiento(String idResultado, Procedimiento procedimiento, String observaciones, String estado, Date fechaResultado) {
        this.idResultado = idResultado;
        this.procedimiento = procedimiento;
        this.observaciones = observaciones;
        this.estado = EstadoResultado.Inconcluso;
        this.fechaResultado = fechaResultado;
    }

    // Getters y Setters
    public String getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(String idResultado) {
        this.idResultado = idResultado;
    }

    public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EstadoResultado getEstado() {
        return estado;
    }

    public void setEstado(EstadoResultado estado) {
        this.estado = estado;
    }

    public Date getFechaResultado() {
        return fechaResultado;
    }

    public void setFechaResultado(Date fechaResultado) {
        this.fechaResultado = fechaResultado;
    }
}
