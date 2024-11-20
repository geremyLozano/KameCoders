package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.capsuleCare.users.dao.EspecialidadDAO;
import pe.edu.pucp.capsuleCare.users.mysql.EspecialidadMySQL;

import pe.edu.pucp.citamedica.model.clinica.Especialidad;



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
    @WebMethod(operationName = "insertarEspecialidad")
    public int especialidadInsertar(Especialidad especialidad) {
        int resultado = espeDAO.insertar(especialidad);
        return resultado;
    }
    
    @WebMethod(operationName = "eliminarEspecialidad")
    public int especialidadEliminar( int idEspecialidad) {
        int resultado = espeDAO.eliminar(idEspecialidad);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarEspecialidad")
    public int especialidadModificar( Especialidad especialidad) {
        int resultado = espeDAO.modificar(especialidad);
        return resultado;
    }
    
    @WebMethod(operationName = "obtenerPorIDEspecialidad")
    public Especialidad especialidadObtenerID( int idEspecialidad) {
        Especialidad resultado = espeDAO.obtenerPorId(idEspecialidad);
        return resultado;
    }
    


    @WebMethod(operationName = "obtenerPorIDEspecialidad1")
    public Especialidad especialidadObtenerID1( int idEspecialidad) {
        Especialidad resultado = espeDAO.obtenerPorId1(idEspecialidad);
        return resultado;
    }
    
    @WebMethod(operationName = "insertarEspecialidad1")
    public int especialidadInsertar1( Especialidad especialidad) {
        int resultado = espeDAO.insertar1(especialidad);
        return resultado;
    }
    @WebMethod(operationName = "listarEspecialidadFiltro")
    public List<Especialidad> listarEspecialidadFiltro(String filtro) {
        List<Especialidad> resultado = espeDAO.listar(filtro);
        return resultado;
    }

    @WebMethod(operationName = "listarAscendente")
    public List<Especialidad> listarAscendente() {
        List<Especialidad> resultado = espeDAO.listarAscendente();
        return resultado;
    }
    @WebMethod(operationName = "listarDescendente")
    public List<Especialidad> listarDescendente() {
        List<Especialidad> resultado = espeDAO.listarDescendente();
        return resultado;
    }
    @WebMethod(operationName = "listarTodos1")
    public List<Especialidad> listarTodos1() {
        List<Especialidad> resultado = espeDAO.listarTodos1();
        return resultado;
    }
    @WebMethod(operationName = "listarActivoNoActivo")
    public List<Especialidad> listarActivoNoActivo(int valor) {
        List<Especialidad> resultado = espeDAO.listarActivoNoActivo(valor);
        return resultado;
    }
}
