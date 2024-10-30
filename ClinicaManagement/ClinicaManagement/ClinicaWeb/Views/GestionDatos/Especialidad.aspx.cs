using ClinicaWeb.CapsuleCareWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ClinicaWeb.Views.GestionDatos
{
    public partial class Especialidad : System.Web.UI.Page
    {
        public EspecialidadWSClient especialidadAPI;

        protected void Page_Init(object sender, EventArgs e)
        {
            especialidadAPI = new EspecialidadWSClient();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            //Genera Toda la Data
            gvEspecialidad.DataSource = especialidadAPI.listarEspecialidades();
            gvEspecialidad.DataBind();
        }

        protected void gvEspecialidad_PageIndexChanging(object sender, System.Web.UI.WebControls.GridViewPageEventArgs e)
        {
            gvEspecialidad.PageIndex = e.NewPageIndex;
            gvEspecialidad.DataBind();
        }

        protected void btnEliminar_Click(object sender, EventArgs e)
        {
            int idEspecialidad = Int32.Parse(((LinkButton)sender).CommandArgument);
            
            especialidadAPI.eliminarEspecialidad(idEspecialidad);
            //Vuelve a Generar toda la data
            gvEspecialidad.DataSource = especialidadAPI.listarEspecialidades();
            gvEspecialidad.DataBind();
        }

        protected void btnAgregar_Click(object sender, EventArgs e)
        {
            Response.Redirect("/Views/GestionDatos/EspecialidadGestion.aspx?op=new");
        }

        protected void btnEditar_Click(object sender, EventArgs e)
        {
            int idEspecialidad = Int32.Parse(((LinkButton)sender).CommandArgument);
            Response.Redirect("/Views/GestionDatos/EspecialidadGestion.aspx?op=edit&id=" + idEspecialidad);
        }

        protected void btnVer_Click(object sender, EventArgs e)
        {
            int idEspecialidad = Int32.Parse(((LinkButton)sender).CommandArgument);
            Response.Redirect("/Views/GestionDatos/EspecialidadGestion.aspx?op=view&id=" + idEspecialidad);
        }
    }
}