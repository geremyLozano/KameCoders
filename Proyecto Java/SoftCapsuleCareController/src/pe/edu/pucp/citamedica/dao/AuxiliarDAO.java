package pe.edu.pucp.citamedica.dao;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface AuxiliarDAO{
    int insertar(Auxiliar auxiliar, Usuario usuario);
    int modificar(Auxiliar auxiliar);
    int eliminar(int idAuxiliar);
    ArrayList<Auxiliar> listarTodos();
    Auxiliar obtenerPorId(int idAuxiliar);

    Auxiliar obtenerPorId1(int idAuxiliar);
    int insertar1(Auxiliar auxiliar);
    List<Auxiliar> listarFiltro(String filtro);
    ArrayList<Auxiliar> listarTodos1();
    int modificar_v2(Auxiliar auxiliar);
}
