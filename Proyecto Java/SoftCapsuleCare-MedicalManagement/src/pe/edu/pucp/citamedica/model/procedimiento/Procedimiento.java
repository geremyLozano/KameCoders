package pe.edu.pucp.citamedica.model.procedimiento;
import java.util.ArrayList;
import java.util.Date;

public class Procedimiento {
    private int idProcedimiento;
    private String nombre;
    private double costo;
    private ArrayList<ResultadoProcedimiento> resultados;
    private String descripcion;
    private String requisitosPrevios;
    private TipoProcedimiento tipoProcedimiento; // Mantiene el nombre consistente con la columna de la BD
    private boolean activo;
    private Date fecha; // Nuevo campo para la fecha
    private String fechaString;

    public Procedimiento() {
        this.resultados = new ArrayList<>();
    }

    public Procedimiento(int idProcedimiento, String nombre, double costo, String descripcion, String requisitosPrevios, TipoProcedimiento tipoProcedimiento, boolean activo, Date fecha) {
        this.idProcedimiento = idProcedimiento;
        this.nombre = nombre;
        this.costo = costo;
        this.resultados = new ArrayList<>();
        this.descripcion = descripcion;
        this.requisitosPrevios = requisitosPrevios;
        this.tipoProcedimiento = tipoProcedimiento;
        this.activo = activo;
        this.fecha = fecha;
    }

    // Getter y setter para 'fecha'
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString (String fecha) {
        this.fechaString = fecha;
    }
    

    // Getter y setter para 'resultados'
    public ArrayList<ResultadoProcedimiento> getResultados() {
        return resultados;
    }

    // Getter y setter para 'descripcion'
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getter y setter para 'requisitosPrevios'
    public String getRequisitosPrevios() {
        return requisitosPrevios;
    }

    public void setRequisitosPrevios(String requisitosPrevios) {
        this.requisitosPrevios = requisitosPrevios;
    }

    // Getter y setter para 'tipoProcedimiento'
    public TipoProcedimiento getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(TipoProcedimiento tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    // Getter y setter para 'idProcedimiento'
    public int getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(int idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    // Getter y setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter y setter para 'costo'
    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    // Getter y setter para 'activo'
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
