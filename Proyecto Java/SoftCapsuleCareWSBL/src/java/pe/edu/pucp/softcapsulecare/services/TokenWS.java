package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.Date;
import pe.edu.pucp.capsuleCare.users.dao.TokenDAO;
import pe.edu.pucp.capsuleCare.users.mysql.TokenMySQL;


@WebService(serviceName = "TokenWS")
public class TokenWS {
    private final TokenDAO tokenDAO;
    
    public TokenWS(){
        tokenDAO = new TokenMySQL();
    }
    
    @WebMethod(operationName = "guardarToken")
    public int guardarToken(@WebParam(name = "correo") String correo,
                            @WebParam(name = "token") String token,
                            @WebParam(name = "expiracion") Date expiracion) {
        return tokenDAO.crear(correo,token,expiracion);
    }
    
    @WebMethod(operationName = "validarToken")
    public int validarToken(@WebParam(name = "token") String token) {
        return tokenDAO.validarToken(token);
    }
}
