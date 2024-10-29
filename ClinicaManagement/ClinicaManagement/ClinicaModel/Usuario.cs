using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClinicaModel
{
    public class Usuario
    {
        private static int contador = 0;
        private int idUsuario;
        private string username;
        private string contrasenha;
        private bool activo;
        private int idPersona;

        public int IdUsuario { get; set; }
        public string Username { get; set; }
        public string Contrasenha { get; set; }
        public bool Activo { get; set; }
        public int IdPersona { get; set; }

        public Usuario()
        {
            IdUsuario = ++contador;
        }

        public Usuario(string username, string contrasenha, bool activo, int idPersona)
        {
            IdUsuario = ++contador;
            Username = username;
            Contrasenha = contrasenha;
            Activo = activo;
            IdPersona = idPersona;
        }
    }
}
