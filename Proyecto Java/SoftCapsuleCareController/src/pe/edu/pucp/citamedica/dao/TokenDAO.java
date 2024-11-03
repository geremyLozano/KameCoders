package pe.edu.pucp.citamedica.dao;

import java.time.LocalDateTime;


public interface TokenDAO {
    int crear(String email, String token, LocalDateTime expiracion);
    int validarToken(String token, boolean esValido, String correo);
}
