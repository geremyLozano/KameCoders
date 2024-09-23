package pe.edu.pucp.citamedica.procedimientos.model;

import pe.edu.pucp.citamedica.consultas.model.CitaMedica;
import pe.edu.pucp.citamedica.clinica.model.AmbienteClinico;

import java.util.Date;
import java.util.ArrayList;


public class Procedimiento {
    private String idProcedimiento;
    private String nombre;
    private double costo;
    private ArrayList<ResultadoProcedimiento> resultados;
    private String descripcion;
    private String requisitosPrevios;
    private TipoProcedimiento tipo;
    private Date fecha;

    public Procedimiento() {
        this.resultados = new ArrayList<>();
    }

    public Procedimiento(String idProcedimiento, String nombre, double costo, String resultado, String descripcion, String requisitosPrevios, TipoProcedimiento tipo, Date fecha) {
        this.idProcedimiento = idProcedimiento;
        this.nombre = nombre;
        this.costo = costo;
        this.resultados = new ArrayList<>();
        this.descripcion = descripcion;
        this.requisitosPrevios = requisitosPrevios;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public ArrayList<ResultadoProcedimiento> getResultados() {
        return resultados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequisitosPrevios() {
        return requisitosPrevios;
    }

    public void setRequisitosPrevios(String requisitosPrevios) {
        this.requisitosPrevios = requisitosPrevios;
    }

    public TipoProcedimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoProcedimiento tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public String getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(String idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
    
}
