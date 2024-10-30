using ClinicaModel;
using System.Collections.Generic;

namespace ClinicaDA.DAO
{
    public interface MedicoDAO
    {
        int insertar(Medico medico);
        int modificar(Medico medico);
        int eliminar(int idMedico);
        List<Medico> listar();
        Medico obtenerPorId(int idMedico);

        List<Medico> obtenerPorEspecialidad(string Especialidad);
        Usuario ExisteUsuario(string username, string contrasenha);
    }
}
