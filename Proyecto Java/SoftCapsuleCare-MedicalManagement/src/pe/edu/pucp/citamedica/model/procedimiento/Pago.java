package pe.edu.pucp.citamedica.model.procedimiento;


import java.util.Date;

public class Pago {
    private int idPago;
    private double descuentoPorSeguro;
    private double montoParcial;
    private double montoTotal;
    private Date fechaPago;
    private String concepto;
    private boolean estado;
    private int idPaciente;

    public Pago() {
    }

    public Pago(int idPago, double descuentoPorSeguro,double montoParcial, double montoTotal,Date fechaPago,
    String concepto,boolean estado) {
        this.idPago = idPago;
        this.descuentoPorSeguro = descuentoPorSeguro;
        this.montoParcial = montoParcial;
        this.montoTotal = montoTotal;
        this.fechaPago = fechaPago;
        this.concepto = concepto;
        this.estado = true;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
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
