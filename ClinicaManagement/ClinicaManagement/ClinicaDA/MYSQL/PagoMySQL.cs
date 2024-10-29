using ClinicaDA.DAO;
using ClinicaModel;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TiendaPucpDBmanager;

namespace ClinicaDA.MYSQL
{
    public class PagoMySQL: PagoDAO
    {
        public int insertar(Pago pago)
        {
            int resultado = -1;
            try
            {
                MySqlConnection conn = DBPoolManager.Instance.Connection;
                MySqlCommand cmd = conn.CreateCommand();
                cmd.CommandText = "PagoInsertar";
                cmd.CommandType = CommandType.StoredProcedure;

                // Agregar parámetros del procedimiento almacenado
                cmd.Parameters.Add("_p_id_pago", MySqlDbType.Int32)
                              .Direction = ParameterDirection.Output;
                cmd.Parameters.AddWithValue("p_descuento", pago.DescuentoSeguro);
                cmd.Parameters.AddWithValue("p_montoParcial", pago.MontoParcial);
                cmd.Parameters.AddWithValue("p_montoTotal", pago.MontoTotal);
                cmd.Parameters.AddWithValue("p_concepto", pago.Concepto);
                cmd.Parameters.AddWithValue("p_idPaciente", pago.IdPaciente);

                // Ejecutar la consulta
                resultado = cmd.ExecuteNonQuery();

                // Obtener el ID del pago insertado
                pago.IdPago = (int)cmd.Parameters["_p_id_pago"].Value;
            }
            catch
            {
                throw; // Lanza la excepción hacia afuera
            }
            finally
            {
                DBPoolManager.Instance.CloseConnection();
            }
            return resultado;
        }

    }
}
