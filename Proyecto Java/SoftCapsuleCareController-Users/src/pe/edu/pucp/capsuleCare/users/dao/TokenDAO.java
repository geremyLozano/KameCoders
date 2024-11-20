package pe.edu.pucp.capsuleCare.users.dao;
import java.util.Date;


public interface TokenDAO {
    int crear(String email, String token, Date expiracion);
    int validarToken(String token);
}
