package pe.edu.pucp.citamedica.paciente.model;

public class Aseguradora {
    private static int contador = 0;

    private int idAseguradora;
    private String direccion;
    private int telefono;
    private String tipoSeguro;
    private double porcentajeDescuento;
    private boolean activo;

    public Aseguradora() {
    }

    public Aseguradora(int idAseguradora, String direccion, int telefono, String tipoSeguro, double porcentajeDescuento, boolean activo) {
        this.idAseguradora = ++contador;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipoSeguro = tipoSeguro;
        this.porcentajeDescuento = porcentajeDescuento;
        this.activo = activo;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public int getIdAseguradora() {
        return idAseguradora;
    }

    public void setIdAseguradora(int idAseguradora) {
        this.idAseguradora = idAseguradora;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }
    
}
