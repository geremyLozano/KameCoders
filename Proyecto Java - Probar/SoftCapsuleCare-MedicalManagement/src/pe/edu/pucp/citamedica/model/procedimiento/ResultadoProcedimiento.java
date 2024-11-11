package pe.edu.pucp.citamedica.model.procedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.EstadoResultado;
import java.util.Date;

public class ResultadoProcedimiento {
    private int idResultado;  // Identificador único para el resultado del procedimiento
    private Procedimiento procedimiento;  // Referencia al Procedimiento que produjo este resultado
    private String observaciones;  // Cualquier comentario o detalle sobre el resultado
    private EstadoResultado estado;  // Completado, Pendiente, Inconcluso, etc.
    private Date fechaResultado;  // Fecha en la que se obtuvieron los resultados
    private boolean activo;

    public ResultadoProcedimiento(){
    }

    // Constructor
    public ResultadoProcedimiento(int idResultado, Procedimiento procedimiento, String observaciones, String estado, Date fechaResultado) {
        this.idResultado = idResultado;
        this.procedimiento = procedimiento;
        this.observaciones = observaciones;
        this.estado = EstadoResultado.Inconcluso;
        this.fechaResultado = fechaResultado;
        this.activo = true;
    }

    // Getters y Setters
    public int getIdResultado() {
        return idResultado;
    }

    public void setIdResultado(int idResultado) {
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
