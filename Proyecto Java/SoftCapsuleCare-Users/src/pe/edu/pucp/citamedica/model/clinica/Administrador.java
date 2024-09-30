package pe.edu.pucp.citamedica.model.clinica;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import java.util.Date;

public class Administrador extends Persona {
    private static int contador = 0;
    
    private int idAdministrador;
    private boolean activo;
    
    public Administrador() {
    }

    public Administrador(String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, String DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idAdministrador = ++contador;
        this.activo = true;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public void gestionarRoles(){
        
    }
    
    public void gestionarQuejas(){
        
    }

    @Override
    public void actualizarInformacion() {
        
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
