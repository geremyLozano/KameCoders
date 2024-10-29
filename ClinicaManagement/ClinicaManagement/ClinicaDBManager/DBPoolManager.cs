using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;

namespace TiendaPucpDBmanager
{
    public class DBPoolManager
    {
        private static DBPoolManager instance;
        // Objeto de bloqueo para manejar la concurrencia de hilos.
        private static readonly object padlock = new object();

        private static string host = "examenparcial.cpsopevx2pq7.us-east-1.rds.amazonaws.com";
        private static string port = "3306";
        private static string db = "mydb";
        private static string user = "admin";
        private static string password = "pucpprogra3#";
        private MySqlConnection conn;

        private DBPoolManager()
        {
            string url = $"server={host};port={port};database={db};user={user};password={password};";
            url += "Pooling=true;Min Pool Size=5;Max Pool Size=20;"; //Pool de conexiones
            conn = new MySqlConnection(url);
        }

        public MySqlConnection Connection
        {
            get
            {
                OpenConnection(); // Abrir una conexion
                return conn;
            }
        }

        public void OpenConnection()
        {
            if (conn.State != System.Data.ConnectionState.Open)
            {
                conn.Open();
                Console.WriteLine("Conexion Abierta");
            }
        }

        public void CloseConnection()
        {
            if (conn.State != System.Data.ConnectionState.Closed)
            {
                conn.Close();
                Console.WriteLine("Conexion Cerrada");
            }
        }

        public static DBPoolManager Instance
        {
            get
            {
                // Para evitar que múltiples hilos creen instancias al mismo tiempo,
                // se introduce un bloqueo utilizando el objeto padlock.
                // Esto asegura que solo un hilo pueda ejecutar el bloque de código que crea
                // la instancia, mientras que otros hilos deben esperar a que se libere el bloqueo.
                lock (padlock)
                {
                    if (instance == null)
                    {
                        instance = new DBPoolManager();
                    }
                }
                return instance;
            }
        }

        // Metodo para ejecutar consultas que retornar un int
        public int EjecutarConsulta(string query,
                Dictionary<string, object> parametros)
        {
            int resultado = -1;
            try
            {
                OpenConnection();
                MySqlCommand cmd = new MySqlCommand(query, conn);
                if (parametros != null)
                {
                    foreach (KeyValuePair<string, object> param in parametros)
                    {
                        cmd.Parameters.AddWithValue(param.Key, param.Value);
                    }
                }
                resultado = cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Ocurrio un error: " + ex.Message);
            }
            finally
            {
                CloseConnection();
            }
            return resultado;
        }

        // Metodo para ejecutar consultas que retornan datos
        public MySqlDataReader EjecutarConsultaDatos(string query,
                Dictionary<string, object> parametros)
        {
            MySqlDataReader reader = null;
            try
            {
                OpenConnection();
                MySqlCommand cmd = new MySqlCommand(query, conn);
                if (parametros != null)
                {
                    foreach (KeyValuePair<string, object> param in parametros)
                    {
                        cmd.Parameters.AddWithValue(param.Key, param.Value);
                    }
                }
                reader = cmd.ExecuteReader();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Ocurrio un error: " + ex.Message);
            }
            //Cerrar la conexion desde el main
            return reader;
        }

        // Método para ejecutar un Stored Procedure con parámetros.
        public int EjecutarProcedimiento(string storedProcedureName, MySqlParameter[] parameters)
        {
            int resultado = -1;
            try
            {
                OpenConnection();
                MySqlCommand command = new MySqlCommand(storedProcedureName, conn);
                // Establecer el tipo de comando como Stored Procedure.
                command.CommandType = CommandType.StoredProcedure;
                if (parameters != null)
                    command.Parameters.AddRange(parameters);
                resultado = command.ExecuteNonQuery();
            }
            catch (MySqlException ex)
            {
                Console.WriteLine("Ocurrio un error: " + ex.Message);
            }
            finally
            {
                CloseConnection();
            }
            return resultado;
        }
        // Método para obtener datos de un Stored Procedure con parámetros (opcional).
        public MySqlDataReader EjecutarProcedimientoDatos(string storedProcedureName, MySqlParameter[] parameters)
        {
            MySqlDataReader reader = null;
            try
            {
                MySqlCommand command = new MySqlCommand(storedProcedureName, conn);
                OpenConnection();
                // Establecer el tipo de comando como Stored Procedure.
                command.CommandType = CommandType.StoredProcedure;
                if (parameters != null)
                    command.Parameters.AddRange(parameters);
                reader = command.ExecuteReader(); // Retorna el DataReader con los resultados.
            }
            catch (MySqlException ex)
            {
                Console.WriteLine("Ocurrio un error: " + ex.Message);
            }
            return reader; // El lector de datos debe ser cerrado después de ser utilizado.
        }

    }
}
