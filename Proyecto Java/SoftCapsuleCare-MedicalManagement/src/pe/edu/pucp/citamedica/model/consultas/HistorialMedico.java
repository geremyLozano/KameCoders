package pe.edu.pucp.citamedica.model.consultas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HistorialMedico {
    private static int contador = 0;

    private int idHistorial;
    private Date fechaDeCreacion;
    private int idPaciente;
    private ArrayList<CitaMedica>citas;
    private boolean activo;

    public HistorialMedico() {
        citas = new ArrayList<>();
    }

    public HistorialMedico(Date fechaDeCreacion, int idPaciente, ArrayList<CitaMedica> citas) {
        this.idHistorial = ++contador;
        this.fechaDeCreacion = fechaDeCreacion;
        this.idPaciente = idPaciente;
        this.citas = new ArrayList<>();
        this.activo = true;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Date fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public ArrayList<CitaMedica> getCitas() {
        return new ArrayList<CitaMedica>(citas);
    }

    public void setCitas(ArrayList<CitaMedica> citas) {
        this.citas = citas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fecha = sdf.format(fechaDeCreacion);
        return "HistorialMedico{" + "idHistorial=" + idHistorial + ", fechaDeCreacion=" + fecha + ", idPaciente=" + idPaciente + ", activo=" + activo + '}';
    }
    
}
