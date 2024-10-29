using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ClinicaWeb.Views
{
    public partial class Pacientes : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Llamamos al método que genera los datos de ejemplo
                gvEmpleados.DataSource = GetEmpleados();
                gvEmpleados.DataBind();
            }
        }
        public class Empleado
        {
            public int IdPersona { get; set; }
            public string Nombre { get; set; }
            public string ApellidoPaterno { get; set; }
            public string Area { get; set; }
            public string Cargo { get; set; }
            public decimal Sueldo { get; set; }
        }

        // Método que genera una lista de empleados de ejemplo
        private List<Empleado> GetEmpleados()
        {
            return new List<Empleado>
            {
                new Empleado { IdPersona = 1, Nombre = "Juan", ApellidoPaterno = "Pérez", Area = "IT", Cargo = "Desarrollador", Sueldo = 3500.00M },
                new Empleado { IdPersona = 2, Nombre = "Ana", ApellidoPaterno = "García", Area = "Recursos Humanos", Cargo = "Gerente", Sueldo = 4500.00M },
                new Empleado { IdPersona = 3, Nombre = "Luis", ApellidoPaterno = "Martínez", Area = "Finanzas", Cargo = "Contador", Sueldo = 4000.00M },
                new Empleado { IdPersona = 4, Nombre = "María", ApellidoPaterno = "Rodríguez", Area = "Ventas", Cargo = "Vendedor", Sueldo = 3000.00M },
                new Empleado { IdPersona = 5, Nombre = "Carlos", ApellidoPaterno = "Hernández", Area = "Marketing", Cargo = "Analista", Sueldo = 3200.00M },
                new Empleado { IdPersona = 6, Nombre = "Javier", ApellidoPaterno = "González", Area = "IT", Cargo = "Desarrollador", Sueldo = 3600.00M },
                new Empleado { IdPersona = 7, Nombre = "Lucía", ApellidoPaterno = "Torres", Area = "Recursos Humanos", Cargo = "Asistente", Sueldo = 2800.00M },
                new Empleado { IdPersona = 8, Nombre = "Pedro", ApellidoPaterno = "López", Area = "Finanzas", Cargo = "Analista", Sueldo = 4200.00M },
                new Empleado { IdPersona = 9, Nombre = "Clara", ApellidoPaterno = "Mendoza", Area = "Ventas", Cargo = "Ejecutiva", Sueldo = 2900.00M },
                new Empleado { IdPersona = 10, Nombre = "Sofía", ApellidoPaterno = "Jiménez", Area = "Marketing", Cargo = "Coordinadora", Sueldo = 3700.00M },
                new Empleado { IdPersona = 11, Nombre = "Raúl", ApellidoPaterno = "Martínez", Area = "IT", Cargo = "Soporte Técnico", Sueldo = 3100.00M },
                new Empleado { IdPersona = 12, Nombre = "Ana", ApellidoPaterno = "Romero", Area = "Recursos Humanos", Cargo = "Reclutador", Sueldo = 4500.00M },
                new Empleado { IdPersona = 13, Nombre = "Miguel", ApellidoPaterno = "Díaz", Area = "Finanzas", Cargo = "Auditor", Sueldo = 4900.00M },
                new Empleado { IdPersona = 14, Nombre = "Teresa", ApellidoPaterno = "Salazar", Area = "Ventas", Cargo = "Gerente de Ventas", Sueldo = 5500.00M },
                new Empleado { IdPersona = 15, Nombre = "Esteban", ApellidoPaterno = "Morales", Area = "Marketing", Cargo = "Director de Marketing", Sueldo = 6000.00M }
            };
        }


        protected void gvEmpleados_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvEmpleados.PageIndex = e.NewPageIndex;
            gvEmpleados.DataSource = GetEmpleados();
            gvEmpleados.DataBind();
        }
    }
}