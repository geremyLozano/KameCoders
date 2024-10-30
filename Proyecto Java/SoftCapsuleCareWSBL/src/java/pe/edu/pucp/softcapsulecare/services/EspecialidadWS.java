package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.EspecialidadDAO;
import pe.edu.pucp.citamedica.model.clinica.Especialidad;
import pe.edu.pucp.citamedica.mysql.EspecialidadMySQL;


@WebService(serviceName = "EspecialidadWS")
public class EspecialidadWS {
    private EspecialidadDAO espeDAO;
    
    public EspecialidadWS(){
        espeDAO = new EspecialidadMySQL();
    }
    
    @WebMethod(operationName = "listarEspecialidades")
    public ArrayList<Especialidad> listarEspecialidades() {
        ArrayList<Especialidad> resultado = espeDAO.listarTodos();
        return resultado;
    }
}
