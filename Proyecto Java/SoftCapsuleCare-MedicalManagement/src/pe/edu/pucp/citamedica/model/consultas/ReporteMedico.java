package pe.edu.pucp.citamedica.model.consultas;


import java.util.Date;

public class ReporteMedico {
    private static int contador = 0;

    private final int idReporteMedico;
    private String diagnostico;
    private String tratamiento;
    private String enfermedad;
    private Date fecha;
    private boolean activo;
    private int idCitaMedica; // Atributo para la relación con CitaMedica

    // Constructor por defecto
    public ReporteMedico() {
        this.idReporteMedico = ++contador; // Inicializa el ID
        this.activo = true; // Activo por defecto
    }

    // Constructor parametrizado
    public ReporteMedico(String diagnostico, String tratamiento, String enfermedad, Date fecha, int idCitaMedica) {
        this.idReporteMedico = ++contador; // Inicializa el ID
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.enfermedad = enfermedad;
        this.fecha = fecha;
        this.activo = true; // Activo por defecto
        this.idCitaMedica = idCitaMedica; // Asignación del ID de CitaMedica
    }

    // Getters y Setters
    public int getIdReporteMedico() {
        return idReporteMedico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
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

    public int getIdCitaMedica() {
        return idCitaMedica;
    }

    public void setIdCitaMedica(int idCitaMedica) {
        this.idCitaMedica = idCitaMedica;
    }
}
