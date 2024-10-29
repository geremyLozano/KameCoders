using System;

namespace ClinicaModel
{
    public class Paciente : Persona
    {
        private static int contador = 0;
        private int idPaciente;
        private bool historialActivo;
        private bool activo;

        public int IdPaciente { get; set; }
        public bool HistorialActivo { get; set; }
        public bool Activo { get; set; }

        public Paciente()
        {
            IdPaciente = ++contador;
        }

        public Paciente(string nombre, string apellido, string correo, int telefono, string direccion, DateTime fechaNacimiento, char genero, string dni, bool historialActivo, bool activo)
            : base(nombre, apellido, correo, telefono, direccion, fechaNacimiento, genero, dni)
        {
            IdPaciente = ++contador;
            HistorialActivo = historialActivo;
            Activo = activo;
        }
    }
}
