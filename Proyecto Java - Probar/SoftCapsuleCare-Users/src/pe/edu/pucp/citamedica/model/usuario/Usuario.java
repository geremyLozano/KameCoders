package pe.edu.pucp.citamedica.model.usuario;
import pe.edu.pucp.citamedica.model.usuario.Persona;

public class Usuario {
    private static int contador = 0;

    private int idUsuario;
    private String username;
    private String contrasenha;
    private boolean activo;
    private int idPersona;

    public Usuario() {
    }

    public Usuario(int idUsuario, String username, String contrasenha, boolean activo, int idPersona) {
        this.idUsuario = ++contador;
        this.username = username;
        this.contrasenha = contrasenha;
        this.activo = activo;
        this.idPersona = idPersona;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
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
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void actualizarInformacion() {
        // Implementar lógica de actualización
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", username=" + username + ", contrasenha=" + contrasenha + ", idPersona=" + idPersona + '}';
    }
    

}
