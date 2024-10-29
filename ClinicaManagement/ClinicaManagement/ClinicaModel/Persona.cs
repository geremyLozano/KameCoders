using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClinicaModel
{
    public abstract class Persona
    {
        private static int contador = 0;
        private int idPersona;
        private string nombre;
        private string apellido;
        private string correo;
        private int telefono;
        private string direccion;
        private DateTime fechaNacimiento;
        private char genero;
        private string dni;

        public int IdPersona { get; set; }
        public string Nombre { get; set; }
        public string Apellido { get; set; }
        public string Correo { get; set; }
        public int Telefono { get; set; }
        public string Direccion { get; set; }
        public DateTime FechaNacimiento { get; set; }
        public char Genero { get; set; }
        public string DNI { get; set; }

        public Persona(){ }

        public Persona(string nombre, string apellido, string correo, int telefono, string direccion, DateTime fechaNacimiento, char genero, string dni)
        {
            IdPersona = ++contador;
            Nombre = nombre;
            Apellido = apellido;
            Correo = correo;
            Telefono = telefono;
            Direccion = direccion;
            FechaNacimiento = fechaNacimiento;
            Genero = genero;
            DNI = dni;
        }

    }
}
