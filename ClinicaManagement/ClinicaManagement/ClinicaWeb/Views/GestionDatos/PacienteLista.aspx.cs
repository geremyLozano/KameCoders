using ClinicaWeb.CapsuleCareWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace TestWeb.Views
{
    public partial class PacienteLista : System.Web.UI.Page
    {
        protected PacienteWSClient pacienteAPI;

        protected void Page_Init(object sender, EventArgs e)
        {
            pacienteAPI = new PacienteWSClient();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            gvPaciente.DataSource = pacienteAPI.listarPaciente();
            gvPaciente.DataBind();
        }

        protected void gvPaciente_PageIndexChanging(object sender, System.Web.UI.WebControls.GridViewPageEventArgs e)
        {
            gvPaciente.PageIndex = e.NewPageIndex;
            gvPaciente.DataBind();
        }

        protected void btnEliminar_Click(object sender, EventArgs e)
        {
            int idPaciente = Int32.Parse(((LinkButton)sender).CommandArgument);
            pacienteAPI.eliminarPaciente(idPaciente);
            gvPaciente.DataSource = pacienteAPI.listarPaciente();
            gvPaciente.DataBind();
        }

        protected void btnAgregar_Click(object sender, EventArgs e)
        {
            Response.Redirect("/Views/GestionDatos/PacienteGestion.aspx?op=new");
        }

        protected void btnEditar_Click(object sender, EventArgs e)
        {
            int idPaciente = Int32.Parse(((LinkButton)sender).CommandArgument);
            Response.Redirect("/Views/GestionDatos/PacienteGestion.aspx?op=edit&id=" + idPaciente);
        }

        protected void btnVer_Click(object sender, EventArgs e)
        {
            int idPaciente = Int32.Parse(((LinkButton)sender).CommandArgument);
            Response.Redirect("/Views/GestionDatos/PacienteGestion.aspx?op=view&id=" + idPaciente);
        }
    }
}