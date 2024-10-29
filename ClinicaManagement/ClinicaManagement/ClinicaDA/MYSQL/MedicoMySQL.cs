using ClinicaDA.DAO;
using ClinicaModel;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using TiendaPucpDBmanager;

namespace ClinicaDA.MYSQL
{
    public class MedicoMySQL : MedicoDAO
    {
        public int eliminar(int idMedico)
        {
            int resultado = -1;
            try
            {
                MySqlConnection conn = DBPoolManager.Instance.Connection;
                MySqlCommand cmd = conn.CreateCommand();
                cmd.CommandText = "EliminarMedico";
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("_id_medico", idMedico);
                resultado = cmd.ExecuteNonQuery();
            }
            catch
            {
                throw; //lanza la excepcion hacia afuera
            }
            finally
            {
                DBPoolManager.Instance.CloseConnection();
            }
            return resultado;
        }

        public Usuario ExisteUsuario(string username, string contrasenha)
        {
            Usuario usuario = null;
            try
            {
                MySqlParameter[] parameters = new MySqlParameter[2];
                parameters[0] = new MySqlParameter("dni", username);
                parameters[1] = new MySqlParameter("contra", contrasenha);
                MySqlDataReader reader = DBPoolManager.Instance.EjecutarProcedimientoDatos("ExisteUsuario", parameters);
                if (reader == null) return usuario;
                if (reader.Read())
                {
                    usuario = new Usuario();
                    usuario.IdUsuario = reader.GetInt32("idUsuario");
                    if (!reader.IsDBNull(reader.GetOrdinal("username")))
                        usuario.Username = reader.GetString("username");
                    if (!reader.IsDBNull(reader.GetOrdinal("contrasenha")))
                        usuario.Contrasenha = reader.GetString("contrasenha");
                    if (!reader.IsDBNull(reader.GetOrdinal("idPersona")))
                        usuario.IdPersona = reader.GetInt32("idPersona");
                    usuario.Activo = true;
                }
            }
            catch (Exception ex)
            {
                throw;//lanza la excepcion hacia afuera
            }
            finally
            {
                DBPoolManager.Instance.CloseConnection();
            }
            return usuario;
        }

        public int insertar(Medico medico)
        {
            int resultado = -1;
            try
            {
                MySqlConnection conn = DBPoolManager.Instance.Connection;
                MySqlCommand cmd = conn.CreateCommand();
                cmd.CommandText = "insertarMedico";
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.Add("p_id_medico", MySqlDbType.Int32)
                              .Direction = ParameterDirection.Output;
                cmd.Parameters.AddWithValue("p_DNI", medico.DNI);
                cmd.Parameters.AddWithValue("p_nombre", medico.Nombre);
                cmd.Parameters.AddWithValue("p_apellido", medico.Apellido);
                cmd.Parameters.AddWithValue("p_correoElectronico", medico.Correo);
                cmd.Parameters.AddWithValue("p_numTelefono", medico.Telefono);
                cmd.Parameters.AddWithValue("p_direccion", medico.Direccion);
                cmd.Parameters.AddWithValue("p_fechaNacimiento", medico.FechaNacimiento);
                cmd.Parameters.AddWithValue("p_genero", medico.Genero);
                cmd.Parameters.AddWithValue("p_numColegiatura", medico.NumColegiatura);
                cmd.Parameters.AddWithValue("p_horaInicioTrabajo", medico.HoraInicio);
                cmd.Parameters.AddWithValue("p_horaFinTrabajo", medico.HoraFin);
                cmd.Parameters.AddWithValue("p_diasLaborales", medico.DiasLaborales);
                cmd.Parameters.AddWithValue("p_anhosExp", medico.AnhosExp);
                cmd.Parameters.AddWithValue("p_activo", medico.Activo);
                resultado = cmd.ExecuteNonQuery();
                medico.IdPersona = (int)cmd.Parameters["p_id_medico"].Value;
            }
            catch
            {
                throw; //lanza la excepcion hacia afuera
            }
            finally
            {
                DBPoolManager.Instance.CloseConnection();
            }
            return resultado;
        }

        public List<Medico> listar()
        {
            List<Medico>resultado = new List<Medico>();
            Especialidad especialidad = new Especialidad();
            try
            {
                MySqlDataReader reader = DBPoolManager.Instance.EjecutarProcedimientoDatos("ListarMedicos", null);
                while (reader.Read())
                {
                    Medico medico = new Medico();
                    medico.IdPersona = reader.GetInt32("idMedico");
                    medico.IdMedico = reader.GetInt32("idMedico");
                    if (!reader.IsDBNull(reader.GetOrdinal("idEspecialidad")))
                        especialidad.IdEspecialidad = reader.GetInt32("idEspecialidad");
                        medico.Especialidad = especialidad;
                    if (!reader.IsDBNull(reader.GetOrdinal("numColegiatura")))
                        medico.NumColegiatura = reader.GetString("numColegiatura");
                    if (!reader.IsDBNull(reader.GetOrdinal("horaInicioTrabajo")))
                        medico.HoraInicio = reader.GetTimeSpan("horaInicioTrabajo");
                    if (!reader.IsDBNull(reader.GetOrdinal("horaFinTrabajo")))
                        medico.HoraFin = reader.GetTimeSpan("horaFinTrabajo");
                    if (!reader.IsDBNull(reader.GetOrdinal("diasLaborales")))
                        medico.DiasLaborales = reader.GetString("diasLaborales");
                    if (!reader.IsDBNull(reader.GetOrdinal("anhosExp")))
                        medico.AnhosExp = reader.GetInt32("anhosExp");
                    resultado.Add(medico);
                }
            }
            catch (Exception ex)
            {
                throw;//lanza la excepcion hacia afuera
            }
            finally
            {
                DBPoolManager.Instance.CloseConnection();
            }
            return resultado;
        }

        public int modificar(Medico medico)
        {
            throw new NotImplementedException();
        }

        public Medico obtenerPorId(int idMedico)
        {
            throw new NotImplementedException();
        }

    }
}
