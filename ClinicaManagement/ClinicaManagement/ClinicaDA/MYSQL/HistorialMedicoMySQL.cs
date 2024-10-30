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
    internal class HistorialMedicoMySQL: HistorialMedicoDAO
    {
        public int insertar(HistorialMedico historialMedico)
        {
            int resultado = -1;
            try
            {
                // Obtener la conexión de la base de datos
                MySqlConnection conn = DBPoolManager.Instance.Connection;
                MySqlCommand cmd = conn.CreateCommand();
                cmd.CommandText = "HistorialMedicoInsertar";
                cmd.CommandType = CommandType.StoredProcedure;

                // Agregar los parámetros del procedimiento almacenado
                cmd.Parameters.Add("_id_historial", MySqlDbType.Int32)
                              .Direction = ParameterDirection.Output;
                cmd.Parameters.AddWithValue("_id_paciente", historialMedico.IdPaciente);

                // Ejecutar la consulta
                resultado = cmd.ExecuteNonQuery();

                // Obtener el ID del historial médico insertado
                historialMedico.IdHistorialMedico = (int)cmd.Parameters["_id_historial"].Value;
            }
            catch
            {
                throw; // Lanza la excepción hacia afuera
            }
            finally
            {
                // Cerrar la conexión
                DBPoolManager.Instance.CloseConnection();
            }
            return resultado;
        }


    }
}
