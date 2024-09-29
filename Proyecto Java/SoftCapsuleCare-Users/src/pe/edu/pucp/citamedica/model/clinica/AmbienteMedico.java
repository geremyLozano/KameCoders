package pe.edu.pucp.citamedica.model.clinica;

public class AmbienteMedico {
    private static int contador = 0;

    private int idAmbiente;
    private int numPiso;
    private String ubicacion;
    private int capacidad;
    private TipoAmbiente tipoAmbiente;
    private boolean activo;

    public AmbienteMedico() {
    }
    
    
    
    public AmbienteMedico(int numPiso, String ubicacion, int capacidad, TipoAmbiente tipoAmbiente) {
        this.idAmbiente = ++contador;
        this.numPiso = numPiso;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.tipoAmbiente = tipoAmbiente;
        this.activo = true;
    }

    public TipoAmbiente getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }


    public int getNumPiso() {
        return numPiso;
    }

    public void setNumPiso(int numPiso) {
        this.numPiso = numPiso;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the idAmbiente
     */
    public int getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(int idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    
}
