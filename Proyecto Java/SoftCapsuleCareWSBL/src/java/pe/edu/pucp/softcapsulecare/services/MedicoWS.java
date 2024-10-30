package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;


@WebService(serviceName = "MedicoWS")
public class MedicoWS {
    private MedicoDAO medicoDAO;
    
    public MedicoWS(){
        medicoDAO = new MedicoMySQL();
    }
    
    @WebMethod(operationName = "insertarMedico")
    public int medicoInsertar(@WebParam(name = "medico") Medico medico) {
        int resultado = medicoDAO.insertar(medico);
        return resultado;
    }
    
    @WebMethod(operationName = "buscarPorEspecialidad")
    public ArrayList<Medico> listarPorEspecialidad(@WebParam(name = "especialidad") String especialidad) {
        ArrayList<Medico> resultado = new ArrayList<Medico>();
        resultado = medicoDAO.listarPorEspecialidad(especialidad);
        return resultado;
    }
    
    
}
