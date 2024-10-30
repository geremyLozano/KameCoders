using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClinicaModel
{
    public class HistorialMedico
    {
        private static int contador = 0;
        private int idHistorialMedico;
        private DateTime fechaCreacion;
        private int idPaciente;

        public int IdHistorialMedico { get; set; }
        public DateTime FechaCreacion { get; set; }
        public int IdPaciente { get; set; }

        public HistorialMedico()
        {
            IdHistorialMedico = ++contador;
        }

        public HistorialMedico(DateTime fechaCreacion, int idPaciente)
        {
            IdHistorialMedico = ++contador;
            FechaCreacion = fechaCreacion;
            IdPaciente = idPaciente;
        }


    }

}
