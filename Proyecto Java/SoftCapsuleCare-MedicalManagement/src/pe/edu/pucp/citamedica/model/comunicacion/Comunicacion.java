package pe.edu.pucp.citamedica.model.comunicacion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comunicacion {
    private static int contador = 0;

    // Atributos
    private int idComunicacion;
    private TipoComunicacion tipo;
    private String contenido;
    private Date fechaComunicacion;
    private boolean activo;
    private int idPaciente;
    private EstadoComunicacion estado; // Usando el nuevo enum EstadoComunicacion
    private String respuesta;

    // Constructor por defecto
    public Comunicacion() {
    }

    // Constructor con parámetros
    public Comunicacion(TipoComunicacion tipo, String contenido, Date fechaComunicacion, EstadoComunicacion estado, String respuesta) {
        this.idComunicacion = ++contador;
        this.tipo = tipo;
        this.contenido = contenido;
        this.fechaComunicacion = fechaComunicacion;
        this.activo = true;
        this.estado = estado;
        this.respuesta = respuesta;
    }

    // Getters y Setters

    public int getIdComunicacion() {
        return idComunicacion;
    }

    public void setIdComunicacion(int idComunicacion) {
        this.idComunicacion = idComunicacion;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public EstadoComunicacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoComunicacion estado) {
        this.estado = estado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    // Sobrescribir el método toString para mostrar todos los atributos
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formateado = (fechaComunicacion != null) ? sdf.format(fechaComunicacion) : "N/A";
        return "Comunicacion{" +
                "idComunicacion=" + idComunicacion +
                ", tipo=" + tipo +
                ", contenido='" + contenido + '\'' +
                ", fechaComunicacion=" + formateado +
                ", activo=" + activo +
                ", idPaciente=" + idPaciente +
                ", estado=" + estado +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}

