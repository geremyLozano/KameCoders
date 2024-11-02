package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.citamedica.dao.PacienteDAO;
import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;

@WebService(serviceName = "PacienteWS",
            targetNamespace = "http://services.softcapsulecare.pucp.edu.pe/")
public class PacienteWS {
    private PacienteDAO pacienteDAO;
    
    public PacienteWS(){
        pacienteDAO = new PacienteMySQL();
    }
    
    @WebMethod(operationName = "insertarPaciente")
    public int pacienteInsertar(@WebParam(name = "paciente") Paciente paciente,
                                @WebParam(name = "usuario") Usuario usuario) {
        System.out.println("fecha: " + paciente.getFechaNacimiento());
        int resultado = pacienteDAO.insertar(paciente,usuario);
        return resultado;
    }
}
