/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.model.consultas;
import java.util.Date;

public class CitaMedicaProcedimiento {
    private int idCitaMedica;
    private int idProcedimiento;
    private Integer idPago; // Por defecto será 0
    private boolean asistencia;
    private String observaciones;
    private Date fechaResultado;
    private Date fechaRealizacion; // Nuevo atributo
    private String horaRealizacion; // Nuevo atributo
    private boolean activo;

    // Constructor por defecto
    public CitaMedicaProcedimiento() {
        this.idCitaMedica = 0;
        this.idProcedimiento = 0;
        this.idPago = 0;
        this.asistencia = false;
        this.observaciones = "";
        this.fechaResultado = null;
        this.fechaRealizacion = null; // Inicializar a null
        this.horaRealizacion = ""; // Inicializar como cadena vacía
        this.activo = true;
    }

    // Constructor con parámetros
    public CitaMedicaProcedimiento(int idCitaMedica, int idProcedimiento, 
            Integer idPago, boolean asistencia, 
            String observaciones, Date fechaResultado, 
            Date fechaRealizacion, String horaRealizacion, boolean activo) {
        this.idCitaMedica = idCitaMedica;
        this.idProcedimiento = idProcedimiento;
        this.idPago = idPago;
        this.asistencia = asistencia;
        this.observaciones = observaciones;
        this.fechaResultado = fechaResultado;
        this.fechaRealizacion = fechaRealizacion;
        this.horaRealizacion = horaRealizacion;
        this.activo = activo;
    }

    // Getters y setters
    public int getIdCitaMedica() { return idCitaMedica; }
    public void setIdCitaMedica(int idCitaMedica) { this.idCitaMedica = idCitaMedica; }

    public int getIdProcedimiento() { return idProcedimiento; }
    public void setIdProcedimiento(int idProcedimiento) { this.idProcedimiento = idProcedimiento; }

    public Integer getIdPago() { return idPago; }
    public void setIdPago(Integer idPago) { this.idPago = idPago; }

    public boolean getAsistencia() { return asistencia; }
    public void setAsistencia(boolean asistencia) { this.asistencia = asistencia; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Date getFechaResultado() { return fechaResultado; }
    public void setFechaResultado(Date fechaResultado) { this.fechaResultado = fechaResultado; }

    public Date getFechaRealizacion() { return fechaRealizacion; } // Nuevo getter
    public void setFechaRealizacion(Date fechaRealizacion) { this.fechaRealizacion = fechaRealizacion; } // Nuevo setter

    public String getHoraRealizacion() { return horaRealizacion; } // Nuevo getter
    public void setHoraRealizacion(String horaRealizacion) { this.horaRealizacion = horaRealizacion; } // Nuevo setter

    public boolean getActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
