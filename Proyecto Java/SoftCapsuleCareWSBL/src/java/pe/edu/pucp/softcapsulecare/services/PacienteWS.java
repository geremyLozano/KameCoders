package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.capsuleCare.users.dao.PacienteDAO;
import pe.edu.pucp.capsuleCare.users.mysql.PacienteMySQL;

import pe.edu.pucp.citamedica.model.usuario.Paciente;
import pe.edu.pucp.citamedica.model.usuario.Usuario;


@WebService(serviceName = "PacienteWS")
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
    @WebMethod(operationName = "listarPaciente")
    public ArrayList<Paciente> listarPaciente() {
        ArrayList<Paciente> resultado = pacienteDAO.listarTodos();
        return resultado;
    }
    @WebMethod(operationName = "listarPacienteFiltro")
    public List<Paciente> listarPacienteFiltro(String filtro) {
        List<Paciente> resultado = pacienteDAO.listar(filtro);
        return resultado;
    }
            
    @WebMethod(operationName = "eliminarPaciente")
    public int pacienteEliminar(@WebParam(name = "paciente") int idPaciente) {
        int resultado = pacienteDAO.eliminar(idPaciente);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarPaciente")
    public int pacienteModificar(@WebParam(name = "especialidad") Paciente paciente) {
        int resultado = pacienteDAO.modificar(paciente);
        return resultado;
    }
    
    @WebMethod(operationName = "obtenerPorIDPaciente")
    public Paciente pacienteObtenerID(@WebParam(name = "paciente") int idPaciente) {
        Paciente resultado = pacienteDAO.obtenerPorId(idPaciente);
        return resultado;
    }
    


    @WebMethod(operationName = "obtenerPorIDPaciente1")
    public Paciente pacienteObtenerID1(@WebParam(name = "paciente") int idPaciente) {
        Paciente resultado = pacienteDAO.obtenerPorId1(idPaciente);
        return resultado;
    }
    @WebMethod(operationName = "insertarPaciente1")
    public int pacienteInsertar1(@WebParam(name = "paciente") Paciente paciente) {
        int resultado = pacienteDAO.insertar1(paciente);
        return resultado;
    }
    
    @WebMethod(operationName = "modificar_v2_paciente")
    public int modificarPaciente_V2(@WebParam(name = "paciente") Paciente paciente) {
        return pacienteDAO.modificar_v2(paciente);
    }

}
