using System;

namespace ClinicaModel
{
    public class Especialidad
    {
        private static int contador = 0;
        private int idEspecialidad;
        private string nombre;
        private double costo;
        private bool activo;

        public int IdEspecialidad { get; set; }
        public string Nombre { get; set; }
        public double Costo { get; set; }
        public bool Activo { get; set; }

        public Especialidad()
        {
            IdEspecialidad = ++contador;
        }

        public Especialidad(string nombre, double costo, bool activo)
        {
            IdEspecialidad = ++contador;
            Nombre = nombre;
            Costo = costo;
            Activo = activo;
        }
    }
}
