package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.paciente.model.Persona;
import pe.edu.pucp.citamedica.usuario.model.Usuario;

public interface PersonaDAO {
    int insertar(Persona persona,Usuario usuario);
    int modificar(Persona persona);
    int eliminar(int idPersona);
    ArrayList<Persona> listarTodos();
    Persona obtenerPorId(int idPersona);
}
