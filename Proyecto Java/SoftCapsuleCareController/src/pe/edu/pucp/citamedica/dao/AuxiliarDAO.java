package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;

public interface AuxiliarDAO{
    int insertar(Auxiliar auxiliar);
    int modificar(Auxiliar auxiliar);
    int eliminar(int idAuxiliar);
    ArrayList<Auxiliar> listarTodos();
    Auxiliar obtenerPorId(int idAuxiliar);
}
