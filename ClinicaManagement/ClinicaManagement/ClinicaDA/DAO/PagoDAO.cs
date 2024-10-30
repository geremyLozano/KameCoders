using ClinicaModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClinicaDA.DAO
{
    public interface PagoDAO
    {
        int insertar(Pago pago);
    }
}
