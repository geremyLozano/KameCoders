package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.capsuleCare.users.dao.AuxiliarDAO;
import pe.edu.pucp.capsuleCare.users.mysql.AuxiliarMySQL;

import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Usuario;



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

    //Insercion Geremy
    @WebMethod(operationName = "listarAuxiliar")
    public ArrayList<Auxiliar> listarAuxiliar() {
        ArrayList<Auxiliar> resultado = auxiliarDAO.listarTodos();
        return resultado;
    }
    
    @WebMethod(operationName = "eliminarAuxiliar")
    public int auxiliarEliminar(@WebParam(name = "auxiliar") int idAuxiliar) {
        int resultado = auxiliarDAO.eliminar(idAuxiliar);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarAuxiliar")
    public int auxiliarModificar(@WebParam(name = "auxiliar") Auxiliar auxiliar) {
        int resultado = auxiliarDAO.modificar(auxiliar);
        return resultado;
    }
    
    /*@WebMethod(operationName = "obtenerPorIDAuxiliar")
    public Auxiliar pacienteObtenerID(@WebParam(name = "auxiliar") int idAuxiliar) {
        Auxiliar resultado = auxiliarDAO.obtenerPorId(idAuxiliar);
        return resultado;
    }*/
    
    
    @WebMethod(operationName = "obtenerPorIDAux1")
    public Auxiliar auxObtenerID1(@WebParam(name = "auxiliar") int idAuxiliar) {
        Auxiliar resultado = auxiliarDAO.obtenerPorId1(idAuxiliar);
        return resultado;
    }
    
    @WebMethod(operationName = "insertarAux1")
    public int auxInsertar1(@WebParam(name = "auxiliar") Auxiliar auxiliar) {
        int resultado = auxiliarDAO.insertar1(auxiliar);
        return resultado;
    }
    
    @WebMethod(operationName = "listarFiltroAuxiliar")
    public List<Auxiliar> listarFiltroAuxiliar(String filtro)  {
        List<Auxiliar> resultado = auxiliarDAO.listarFiltro(filtro);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarAuxiliar_v2")
    public int modificarAuxiliar_V2(@WebParam(name = "auxiliar") Auxiliar auxiliar) {
        return auxiliarDAO.modificar_v2(auxiliar);
    }
}
