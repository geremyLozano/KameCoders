package pe.edu.pucp.citamedica.model.procedimiento;

import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.procedimiento.TipoProcedimiento;
import pe.edu.pucp.citamedica.model.procedimiento.ResultadoProcedimiento;
import java.util.Date;
import java.util.ArrayList;

public class Procedimiento {
    private int idProcedimiento;
    private String nombre;
    private double costo;
    private ArrayList<ResultadoProcedimiento> resultados;
    private String descripcion;
    private String requisitosPrevios;
    private TipoProcedimiento tipoProcedimiento;
    private boolean activo;

    public Procedimiento() {
        this.resultados = new ArrayList<>();
    }

    public Procedimiento(int idProcedimiento, String nombre, double costo, String resultado, String descripcion, String requisitosPrevios, TipoProcedimiento tipo) {
        this.idProcedimiento = idProcedimiento;
        this.nombre = nombre;
        this.costo = costo;
        this.resultados = new ArrayList<>();
        this.descripcion = descripcion;
        this.requisitosPrevios = requisitosPrevios;
        this.tipo = tipo;
        this.activo = true;
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

    public int getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(int idProcedimiento) {
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
}
