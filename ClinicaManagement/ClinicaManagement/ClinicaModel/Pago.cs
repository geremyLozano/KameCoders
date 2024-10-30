using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClinicaModel
{

    public class Pago
    {
        private static int contador = 0;
        private int idPago;
        private double descuentoSeguro;
        private double montoParcial;
        private double montoTotal;
        private DateTime fechaPago;
        private string concepto;
        private bool estado;
        private int idPaciente;

        public int IdPago { get; set; }
        public double DescuentoSeguro { get; set; }
        public double MontoParcial { get; set; }
        public double MontoTotal { get; set; }
        public DateTime FechaPago { get; set; }
        public string Concepto { get; set; }
        public bool Estado { get; set; }
        public int IdPaciente { get; set; }

        public Pago()
        {
            IdPago = ++contador;
        }

        public Pago(double descuentoSeguro, double montoParcial, double montoTotal, DateTime fechaPago, string concepto, bool estado, int idPaciente)
        {
            IdPago = ++contador;
            DescuentoSeguro = descuentoSeguro;
            MontoParcial = montoParcial;
            MontoTotal = montoTotal;
            FechaPago = fechaPago;
            Concepto = concepto;
            Estado = estado;
            IdPaciente = idPaciente;
        }
    }
}
