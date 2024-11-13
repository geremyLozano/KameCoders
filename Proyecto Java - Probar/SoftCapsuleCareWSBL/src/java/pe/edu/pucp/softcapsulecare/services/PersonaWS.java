package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import pe.edu.pucp.citamedica.dao.PersonaDAO;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.mysql.PersonaMySQL;
@WebService(serviceName = "PersonaWS")
public class PersonaWS {
    private PersonaDAO personaDAO;
    
    public PersonaWS(){
        personaDAO = new PersonaMySQL();
    }
    @WebMethod(operationName = "obtenerPersona")
    public Persona obtenerPersona(@WebParam(name = "idPersona") int idPersona) {
        return personaDAO.obtenerPorId(idPersona);
    }
    
    @WebMethod(operationName = "modificarPersona")
    public int modificarPersona(@WebParam(name = "persona") Persona persona) {
        return personaDAO.modificar(persona);
    }
}