package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface AuxiliarDAO{
    int insertar(Auxiliar auxiliar, Usuario usuario, Persona persona);
    int modificar(Auxiliar auxiliar);
    int eliminar(int idAuxiliar);
    ArrayList<Auxiliar> listarTodos();
    Auxiliar obtenerPorId(int idAuxiliar);
}
