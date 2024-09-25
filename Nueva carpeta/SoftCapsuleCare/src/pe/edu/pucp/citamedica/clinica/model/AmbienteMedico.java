package pe.edu.pucp.citamedica.clinica.model;
import pe.edu.pucp.citamedica.consultas.model.CitaMedica;
import pe.edu.pucp.citamedica.procedimiento.model.Procedimiento;

public class AmbienteMedico {
    private static int contador = 0;

    private int idAmbiente;
    private int numPiso;
    private String ubicacion;
    private int capacidad;
    private TipoAmbiente tipoAmbiente;

    public AmbienteMedico() {
    }
    
    
    
    public AmbienteMedico(int numPiso, String ubicacion, int capacidad, TipoAmbiente tipoAmbiente) {
        this.idAmbiente = ++contador;
        this.numPiso = numPiso;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.tipoAmbiente = tipoAmbiente;
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

    /**
     * @param idAmbiente the idAmbiente to set
     */
    public void setIdAmbiente(int idAmbiente) {
        this.idAmbiente = idAmbiente;
    }
    
}
