package pe.edu.pucp.citamedica.pago.model;

import java.util.Date;

public class Pago {
    private static int contador = 0;
    private int idPago;
    private int DNI;
    private double descuentoPorSeguro;
    private double montoParcial;
    private double montoTotal;
    private Date fechaPago;
    private String concepto;
    private boolean estado;

    public Pago() {
    }

    public Pago(int idPago, int DNI, double descuentoPorSeguro,double montoParcial, double montoTotal,Date fechaPago,
    String concepto,boolean estado) {
        this.idPago = ++contador;
        this.DNI = DNI;
        this.descuentoPorSeguro = descuentoPorSeguro;
        this.montoParcial = montoParcial;
        this.montoTotal = montoTotal;
        this.fechaPago = fechaPago;
        this.concepto = concepto;
        this.estado = false;
    }

    public int getIdPago() {
        return idPago;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public double getDescuentoPorSeguro() {
        return descuentoPorSeguro;
    }

    public void setDescuentoPorSeguro(double descuentoPorSeguro) {
        this.descuentoPorSeguro = descuentoPorSeguro;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getMontoParcial() {
        return montoParcial;
    }

    public void setMontoParcial(double montoParcial) {
        this.montoParcial = montoParcial;
    }

    public Date getFechaPago(){
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago){
        this.fechaPago = fechaPago;
    }

    public String getConcepto(){
        return concepto;
    }

    public void setConcepto(String concepto){
        this.concepto = concepto;
    }

    public boolean getEstado(){
        return estado;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }
    
    public void generarComprobantePago(){
        
    }
}
