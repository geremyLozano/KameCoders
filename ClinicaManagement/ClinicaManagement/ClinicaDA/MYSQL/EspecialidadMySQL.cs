using ClinicaDA.DAO;
using ClinicaModel;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TiendaPucpDBmanager;

namespace ClinicaDA.MYSQL
{
    public class EspecialidadMySQL: EspecialidadDAO
    {
        public List<Especialidad> listar()
        {
            List<Especialidad> resultado = new List<Especialidad>();
            try
            {
                // Ejecutar el procedimiento almacenado para obtener las especialidades activas
                MySqlDataReader reader = DBPoolManager.Instance.EjecutarProcedimientoDatos("ESPECIALIDAD_LISTAR_TODAS", null);

                while (reader.Read())
                {
                    // Crear una nueva instancia de Especialidad
                    Especialidad especialidad = new Especialidad();

                    // Mapear los valores obtenidos de la base de datos
                    especialidad.IdEspecialidad = reader.GetInt32("idEspecialidad");

                    if (!reader.IsDBNull(reader.GetOrdinal("nombre")))
                        especialidad.Nombre = reader.GetString("nombre");

                    if (!reader.IsDBNull(reader.GetOrdinal("costoConsulta")))
                        especialidad.Costo = reader.GetDouble("costoConsulta");

                    if (!reader.IsDBNull(reader.GetOrdinal("activo")))
                        especialidad.Activo = reader.GetBoolean("activo");

                    // Agregar la especialidad a la lista de resultados
                    resultado.Add(especialidad);
                }
            }
            catch (Exception ex)
            {
                throw; // Lanza la excepción hacia afuera
            }
            finally
            {
                DBPoolManager.Instance.CloseConnection(); // Cerrar la conexión
            }
            return resultado;
        }

    }
}
