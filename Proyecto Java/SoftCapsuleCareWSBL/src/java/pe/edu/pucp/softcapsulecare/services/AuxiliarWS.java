package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;


@WebService(serviceName = "AuxiliarWS")
public class AuxiliarWS {
    private AuxiliarDAO auxiliarDAO;
    
    public AuxiliarWS(){
        auxiliarDAO = new AuxiliarMySQL();
    }
    
    @WebMethod(operationName = "insertarAuxiliar")
    public int auxiliarInsertar(@WebParam(name = "auxiliar") Auxiliar aux,
                                     @WebParam(name = "usuario") Usuario usuario) {
        int resultado = auxiliarDAO.insertar(aux,usuario);
        return resultado;
    }
}
