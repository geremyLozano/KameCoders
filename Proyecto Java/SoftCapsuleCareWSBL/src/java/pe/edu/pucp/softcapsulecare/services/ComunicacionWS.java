
package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.ComunicacionDAO;
import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;
import pe.edu.pucp.citamedica.mysql.ComunicacionMySQL;

@WebService(serviceName = "ComunicacionWS")
public class ComunicacionWS {

    private ComunicacionDAO comunicacionDAO;

    public ComunicacionWS() {
        comunicacionDAO = new ComunicacionMySQL();
    }

    // -------- Métodos para el Administrador --------
    @WebMethod(operationName = "adminInsertarComunicacion")
    public int adminInsertarComunicacion(@WebParam(name = "comunicacion") Comunicacion comunicacion) {
        return comunicacionDAO.insertar(comunicacion);
    }

    @WebMethod(operationName = "adminModificarContenidoComunicacion")
    public int adminModificarContenidoComunicacion(@WebParam(name = "comunicacion") Comunicacion comunicacion) {
        return comunicacionDAO.modificar(comunicacion);
    }

    @WebMethod(operationName = "adminEliminarComunicacion")
    public int adminEliminarComunicacion(@WebParam(name = "idComunicacion") int idComunicacion) {
        return comunicacionDAO.eliminar(idComunicacion);
    }

    @WebMethod(operationName = "adminListarTodasLasComunicaciones")
    public ArrayList<Comunicacion> adminListarTodasLasComunicaciones() {
        return comunicacionDAO.listarTodos();
    }

    // -------- Métodos para el Paciente --------
    @WebMethod(operationName = "pacienteInsertarComunicacion")
    public int pacienteInsertarComunicacion(@WebParam(name = "comunicacion") Comunicacion comunicacion) {
        // Permitir tanto 'QUEJA' como 'SUGERENCIA'
        String tipo = comunicacion.getTipo().name();
        if (!tipo.equals("QUEJA") && !tipo.equals("SUGERENCIA")) {
            return -1; // Retornar error si no es válido
        }
        return comunicacionDAO.insertar(comunicacion);
    }

    @WebMethod(operationName = "pacienteModificarContenidoComunicacion")
    public int pacienteModificarContenidoComunicacion(@WebParam(name = "comunicacion") Comunicacion comunicacion) {
        // Permitir solo modificaciones en el contenido de la comunicación
        return comunicacionDAO.modificar(comunicacion);
    }

    @WebMethod(operationName = "pacienteListarComunicaciones")
    public ArrayList<Comunicacion> pacienteListarComunicaciones(@WebParam(name = "idPaciente") int idPaciente) {
        ArrayList<Comunicacion> todas = comunicacionDAO.listarTodos();
        ArrayList<Comunicacion> filtradas = new ArrayList<>();
        for (Comunicacion c : todas) {
            if (c.getIdPaciente() == idPaciente) {
                filtradas.add(c);
            }
        }
        return filtradas;
    }

    @WebMethod(operationName = "pacienteVerContenidoComunicacion")
    public String pacienteVerContenidoComunicacion(@WebParam(name = "idComunicacion") int idComunicacion) {
        Comunicacion comunicacion = comunicacionDAO.obtenerPorId(idComunicacion);
        return (comunicacion != null) ? comunicacion.getContenido() : "No encontrada";
    }
}