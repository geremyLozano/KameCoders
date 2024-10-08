package pe.edu.pucp.citamedica.model.comunicacion;

import java.text.SimpleDateFormat;

import java.util.Date;

public class Comunicacion {
    private static int contador = 0;
    
    private int idComunicacion;
    private TipoComunicacion tipo;
    private String contenido;
    private Date fechaComunicacion;
    private boolean activo;
    private int idPaciente;
    
    public Comunicacion() {
    }

    public Comunicacion(TipoComunicacion tipo, String contenido, Date fechaComunicacion) {
        this.idComunicacion = ++contador;
        this.tipo = tipo;
        this.contenido = contenido;
        this.fechaComunicacion = fechaComunicacion;
        this.activo = true;
    }
    
    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public void setIdComunicacion(int idComunicacion) {
        this.idComunicacion = idComunicacion;
    }

    public int getIdComunicacion() {
        return idComunicacion;
    }

    public TipoComunicacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoComunicacion tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(Date fechaComunicacion) {
        this.fechaComunicacion = fechaComunicacion;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formateado = sdf.format(fechaComunicacion);
        return "Comunicacion{" + "idComunicacion=" + idComunicacion + ", tipo=" + tipo + ", contenido=" + contenido + ", fechaComunicacion=" + formateado + '}';
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}
