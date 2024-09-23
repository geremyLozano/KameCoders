package pe.edu.pucp.citamedica.consultas.model;
import pe.edu.pucp.citamedica.paciente.model.Paciente;

import java.util.ArrayList;
import java.util.Date;


public class HistorialMedico {
    private static int contador = 0;

    private int idHistorial;
    private Date fechaDeCreacion;
    private String numeroDocumentoIdentidadPaciente;
    private ArrayList<CitaMedica>citas;

    public HistorialMedico() {
        citas = new ArrayList<>();
    }

    public HistorialMedico(Date fechaDeCreacion, String numeroDocumentoIdentidadPaciente, ArrayList<CitaMedica> citas) {
        this.idHistorial = ++contador;
        this.fechaDeCreacion = fechaDeCreacion;
        this.numeroDocumentoIdentidadPaciente = numeroDocumentoIdentidadPaciente;
        this.citas = new ArrayList<>();
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public String getNumeroDocumentoIdentidadPaciente() {
        return numeroDocumentoIdentidadPaciente;
    }

    public void setNumeroDocumentoIdentidadPaciente(String numeroDocumentoIdentidadPaciente) {
        this.numeroDocumentoIdentidadPaciente = numeroDocumentoIdentidadPaciente;
    }

    public ArrayList<CitaMedica> getCitas() {
        return new ArrayList<CitaMedica>(citas);
    }

    public void setCitas(ArrayList<CitaMedica> citas) {
        this.citas = citas;
    }

    
}
