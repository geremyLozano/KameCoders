package pe.edu.pucp.citamedica.clinica.model;
import pe.edu.pucp.citamedica.paciente.model.Persona;
import java.util.Date;

public class Administrador extends Persona {
    private static int contador = 0;
    
    private int idAdministrador;

    public Administrador(String nombre, String apellido, String correoElectronico, int numTelefono, String direccion, 
                   Date fechaNacimiento, char genero, int DNI) {
        super(nombre,apellido,correoElectronico,numTelefono,direccion,fechaNacimiento,genero,DNI);
        this.idAdministrador = ++contador;
    }

    public void gestionarRoles(){
        
    }
    
    public void gestionarQuejas(){
        
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }
    
    

    @Override
    public void actualizarInformacion() {
        
    }
}
