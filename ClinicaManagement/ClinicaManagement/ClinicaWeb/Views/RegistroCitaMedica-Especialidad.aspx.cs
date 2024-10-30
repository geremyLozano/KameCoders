using System;
using System.Web.UI;
using System.Web.UI.WebControls;
using ClinicaWeb.CapsuleCareWS;

namespace ClinicaWeb.Views
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        private EspecialidadWSClient especialidadDAO = new EspecialidadWSClient();
        private MedicoWSClient medicoDAO = new MedicoWSClient();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarEspecialidades();
            }
        }

        private void CargarEspecialidades()
        {
            var especialidades = especialidadDAO.listarEspecialidades();
            ddlEspecialidades.DataSource = especialidades;
            ddlEspecialidades.DataTextField = "Nombre";
            ddlEspecialidades.DataValueField = "idEspecialidad";
            ddlEspecialidades.DataBind();
            ddlEspecialidades.Items.Insert(0, new ListItem("-- Seleccione una especialidad --", "0"));
        }
    }
}
