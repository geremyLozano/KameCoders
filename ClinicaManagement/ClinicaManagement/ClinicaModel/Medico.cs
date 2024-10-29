using System;

namespace ClinicaModel
{
    public class Medico : Persona
    {
        private static int contador = 0;
        private int idMedico;
        private Especialidad especialidad;
        private string numColegiatura;
        private TimeSpan horaInicio;
        private TimeSpan horaFin;
        private string diasLaborales;
        private int anhosExp;
        private bool activo;

        public int IdMedico { get; set; }
        public Especialidad Especialidad { get; set; }
        public string NumColegiatura { get; set; }
        public TimeSpan HoraInicio { get; set; }
        public TimeSpan HoraFin { get; set; }
        public string DiasLaborales { get; set; }
        public int AnhosExp { get; set; }
        public bool Activo { get; set; }

        public Medico()
        {
            IdMedico = ++contador;
        }

        public Medico(string nombre, string apellido, string correo, int telefono, string direccion, DateTime fechaNacimiento, char genero, string dni, Especialidad especialidad, string numColegiatura, TimeSpan horaInicio, TimeSpan horaFin, string diasLaborales, int anhosExp, bool activo)
            : base(nombre, apellido, correo, telefono, direccion, fechaNacimiento, genero, dni)
        {
            IdMedico = ++contador;
            Especialidad = especialidad;
            NumColegiatura = numColegiatura;
            HoraInicio = horaInicio;
            HoraFin = horaFin;
            DiasLaborales = diasLaborales;
            AnhosExp = anhosExp;
            Activo = activo;
        }

        public override string ToString()
        {
            return "Id: " + IdMedico + " - Especialidad: " + Especialidad.Nombre + " - Colegiatura: " + NumColegiatura + " - Horario: " + HoraInicio + " - " + HoraFin + " - Dias Laborales: " + DiasLaborales + " - Años de Experiencia: " + AnhosExp;
        }
    }

}
