package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.usuario.Aseguradora;

public interface AseguradoraDAO {
    int insertar(Aseguradora aseguradora);
    int modificar(Aseguradora aseguradora);
    int eliminar(int idAseguradora);
    ArrayList<Aseguradora> listarTodos();
    Aseguradora obtenerPorId(int idAseguradora);
    int insertarPacienteAseguradora(int idPaciente,int idAseguradora);
}
