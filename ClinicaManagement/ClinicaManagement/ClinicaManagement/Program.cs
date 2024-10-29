using ClinicaDA.DAO;
using ClinicaDA.MYSQL;
using ClinicaModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClinicaManagement
{
    public class Program
    {
        public static void insertarMedico()
        {
            Medico med = new Medico();
            med.DNI = "12345678";
            med.Nombre = "Juan";
            med.Apellido = "Perez";
            med.Correo = "c#@hotmail.com";
            med.Telefono = 123456789;
            med.Direccion = "Av. Los Pinos 123";
            med.FechaNacimiento = new DateTime(1990, 1, 1);
            med.Genero = "M"[0];
            med.NumColegiatura = "123456";
            med.HoraInicio = new TimeSpan(8, 0, 0);
            med.HoraFin = new TimeSpan(16, 0, 0);
            med.DiasLaborales = "Lunes a Viernes";
            med.AnhosExp = 5;
            med.Activo = true;
            MedicoDAO medicoDAO = new MedicoMySQL();
            int resultado = medicoDAO.insertar(med);
            if (resultado != -1)
            {
                Console.WriteLine("Medico insertado correctamente");
            }
            else
            {
                Console.WriteLine("Error al insertar medico");
            }
        }
        public static void Main(string[] args)
        {
            MedicoDAO med = new MedicoMySQL();
            List<Medico> medicos = med.listar();
            foreach (Medico m in medicos)
            {
                Console.WriteLine(m);
            }
            Console.ReadLine();
        }
    }
}
