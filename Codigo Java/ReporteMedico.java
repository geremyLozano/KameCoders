package logica;

import java.util.Date;


public class ReporteMedico {
    private static int contador = 0;

    private int idReporteMedico;
    private String diagnostico;
    private String tratamiento;
    private String enfermedad;
    private Date fecha;

    public ReporteMedico() {
    }

    public ReporteMedico(String diagnostico, String tratamiento, String enfermedad, Date fecha) {
        this.idReporteMedico = ++contador;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.enfermedad = enfermedad;
        this.fecha = fecha;
    }


    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
}
