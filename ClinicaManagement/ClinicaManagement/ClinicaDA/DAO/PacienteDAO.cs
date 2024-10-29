using ClinicaModel;
using System.Collections.Generic;

namespace ClinicaDA.DAO
{
    public interface PacienteDAO
    {
        int insertar(Paciente paciente, Usuario usuario);
        int modificar(Paciente paciente);
        int eliminar(int idPaciente);
        List<Paciente> listar();
        Paciente obtenerPorId(int idPaciente);
    }
}
