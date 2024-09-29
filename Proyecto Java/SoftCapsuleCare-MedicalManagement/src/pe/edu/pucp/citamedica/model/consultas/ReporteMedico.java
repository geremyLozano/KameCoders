package pe.edu.pucp.citamedica.model.consultas;


import java.util.Date;


public class ReporteMedico {
    private static int contador = 0;

    private int idReporteMedico;
    private String diagnostico;
    private String tratamiento;
    private String enfermedad;
    private Date fecha;
    private boolean activo;

    public ReporteMedico() {
    }

    public ReporteMedico(String diagnostico, String tratamiento, String enfermedad, Date fecha) {
        this.idReporteMedico = ++contador;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.enfermedad = enfermedad;
        this.fecha = fecha;
        this.activo = true;
    }


    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public int getIdReporteMedico() {
        return idReporteMedico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
