package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.citamedica.dao.CitaMedicaDAO;
import pe.edu.pucp.citamedica.mysql.CitaMedicaMySQL;
import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
/**
 *
 * @author User
 */
@WebService(serviceName = "CitaMedicaWS")
public class CitaMedicaWS {
    private final CitaMedicaDAO citaMedicaDao;

    public CitaMedicaWS(){
        citaMedicaDao = new CitaMedicaMySQL();
    }
    
    @WebMethod(operationName = "insertarCitaMedica")
    public int administradorInsertar(@WebParam(name = "cita") CitaMedica cita){
        int resultado = citaMedicaDao.insertar(cita);
        return resultado;
    }
}
