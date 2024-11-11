package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

import java.time.LocalTime;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;


@WebService(serviceName = "MedicoWS")
public class MedicoWS {
    private MedicoDAO medicoDAO;
    
    public MedicoWS(){
        medicoDAO = new MedicoMySQL();
    }
    
    @WebMethod(operationName = "insertarMedico")
    public int medicoInsertar(@WebParam(name = "medico") Medico medico,
                              @WebParam(name = "usuario") Usuario usuario) {
        
        
       
        
        
        medico.setHoraInicioTrabajo(LocalTime.MIDNIGHT);
        medico.setHoraFinTrabajo(LocalTime.MIN);
        return medicoDAO.insertar(medico,usuario);
    }
    
    @WebMethod(operationName = "buscarPorEspecialidad")
    public ArrayList<Medico> listarPorEspecialidad(@WebParam(name = "especialidad") String especialidad) {
        ArrayList<Medico> resultado = new ArrayList<Medico>();
        resultado = medicoDAO.listarPorEspecialidad(especialidad);
        return resultado;
    }
    
    
    @WebMethod(operationName = "listarMedico")
    public ArrayList<Medico> listarMedico() {
        ArrayList<Medico> resultado = medicoDAO.listarTodos();
        return resultado;
    }
    /*@WebMethod(operationName = "listarMedicoFiltro")
    public List<Paciente> listarMedicoFiltro(String filtro) {
        List<Paciente> resultado = medicoDAO.listar(filtro);
        return resultado;
    }*/
            
    @WebMethod(operationName = "eliminarMedico")
    public int medicoEliminar(@WebParam(name = "medico") int idMedico) {
        int resultado = medicoDAO.eliminar(idMedico);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarMedico")
    public int medicoModificar(@WebParam(name = "medico") Medico medico) {
        int resultado = medicoDAO.modificar(medico);
        return resultado;
    }
    
    @WebMethod(operationName = "obtenerPorIDMedico")
    public Medico medicoObtenerID(@WebParam(name = "paciente") int idMedico) {
        Medico resultado = medicoDAO.obtenerPorId(idMedico);
        return resultado;
    }
    


    @WebMethod(operationName = "insertarMedico1")
    public int medicoInsertar1(@WebParam(name = "paciente") Medico medico) {
        int resultado = medicoDAO.insertarMedico1(medico);
        return resultado;
    }

    @WebMethod(operationName = "listarMedico1")
    public ArrayList<Medico> listarMedico1() {
        ArrayList<Medico> resultado = medicoDAO.listarTodos1();
        return resultado;
    }
    
    @WebMethod(operationName = "obtenerPorIDMedico1")
    public Medico medicoObtenerID1(@WebParam(name = "medico") int idMedico) {
        Medico resultado = medicoDAO.obtenerPorId1(idMedico);
        return resultado;
    }
    
}
