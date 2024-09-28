package pe.edu.pucp.citamedica.model.usuario;
import pe.edu.pucp.citamedica.model.usuario.Persona;

public class Usuario {
    private static int contador = 0;

    private int idUsuario;
    private String username;
    private String contrasenha;
    private Persona datosPersonales;

    public Usuario() {
    }

    // Incluimos el campo apellido en el constructor
    public Usuario(String contrasenha, Persona datosPersonales, String DNI) {
        // Llamamos al constructor de Persona pasando el apellido
        this.idUsuario = ++contador;
        this.username = DNI;  // Asignamos el DNI como username
        this.contrasenha = contrasenha;
        this.datosPersonales = datosPersonales;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public Persona getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(Persona datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    public void actualizarInformacion() {
        // Implementar lógica de actualización
    }
}
