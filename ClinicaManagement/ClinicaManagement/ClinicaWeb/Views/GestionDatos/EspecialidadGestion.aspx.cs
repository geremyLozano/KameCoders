using ClinicaModel;
using ClinicaWeb.CapsuleCareWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace TestWeb.Views
{
    public partial class EspecialidadGestion : System.Web.UI.Page
    {
       
        protected EspecialidadWSClient especialidadAPI;
        protected string opcion;

        protected void Page_Init(object sender, EventArgs e)
        {
            especialidadAPI = new EspecialidadWSClient();
            opcion = Request.QueryString["op"];
            string idespecialidad_str = Request.QueryString["id"];
            if ((opcion == "edit" || opcion == "view") && idespecialidad_str != null)
            {
                CargarDatos(int.Parse(idespecialidad_str));
                if (opcion == "view")
                    desabilitarControles();
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        private void CargarDatos(int id_especialidad)
        {
            especialidad especialidad = especialidadAPI.obtenerPorIDEspecialidad1(id_especialidad);
            if(especialidad  != null)
            {
                txtIdEspecialidad.Text = "" + especialidad.idEspecialidad;
                txtNombre.Text = especialidad.nombre;
                txtCostoConsulta.Text = "" + especialidad.costoConsulta;
                chkActivo.Checked = especialidad.activo;
            }
            else
            {
                Response.Redirect("/Views/GestionDatos/Especialidad.aspx");
            }
        }

        private void desabilitarControles()
        {
            txtIdEspecialidad.Enabled = false;
            txtNombre.Enabled = false;
            txtCostoConsulta.Enabled = false;
            chkActivo.Enabled = false;
            btnAceptar.Enabled = false;
        }

        protected void btnAceptar_Click(object sender, EventArgs e)
        {
            if (opcion == "new")
            {
                especialidad especialidad = new especialidad();
                especialidad.nombre = txtNombre.Text;
                especialidad.costoConsulta = double.Parse(txtCostoConsulta.Text);
                especialidad.activo = chkActivo.Checked;
                if (especialidadAPI.insertarEspecialidad1(especialidad) != 0)
                {
                    Response.Redirect("/Views/GestionDatos/Especialidad.aspx");
                }
            }
            else if (!string.IsNullOrEmpty(txtIdEspecialidad.Text))
            {
                especialidad especialidad = new especialidad();
                especialidad.idEspecialidad = int.Parse(txtIdEspecialidad.Text);
                especialidad.nombre = txtNombre.Text;
                especialidad.costoConsulta = double.Parse(txtCostoConsulta.Text);
                especialidad.activo = chkActivo.Checked;
                
                if (especialidadAPI.modificarEspecialidad(especialidad) != 0)
                {
                    Response.Redirect("/Views/GestionDatos/Especialidad.aspx");
                }
            }
        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            Response.Redirect("/Views/GestionDatos/Especialidad.aspx");
        }

    }
    
}