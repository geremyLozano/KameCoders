package pe.edu.pucp.capsuleCare.users.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;

public interface PersonaDAO {
    int insertar(Persona persona,Usuario usuario);
    int modificar(Persona persona);
    int eliminar(int idPersona);
    ArrayList<Persona> listarTodos();
    Persona obtenerPorId(int idPersona);
}
