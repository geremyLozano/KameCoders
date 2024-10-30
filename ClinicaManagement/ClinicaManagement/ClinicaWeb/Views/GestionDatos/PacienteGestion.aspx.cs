using ClinicaDA.DAO;
using ClinicaDA.MYSQL;
using ClinicaWeb.CapsuleCareWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ClinicaWeb.Views.GestionDatos
{
    public partial class PacienteGestion : System.Web.UI.Page
    {
        protected PacienteWSClient pacienteAPI;
        protected string opcion;

        protected void Page_Init(object sender, EventArgs e)
        {
            pacienteAPI = new PacienteWSClient();

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

        private void CargarDatos(int id_paciente)
        {
            paciente paciente = pacienteAPI.obtenerPorIDPaciente1(id_paciente);
            if (paciente != null)
            {
                txtIdPaciente.Text = "" + paciente.idPaciente;
                txtDNI.Text = paciente.DNI;
                txtNombre.Text = paciente.nombre;
                txtApellido.Text = paciente.apellido;
                txtCorreo.Text = paciente.correoElectronico;
                txtNum.Text = "" + paciente.numTelefono;
                txtDirreccion.Text = paciente.direccion;
                txtFechaNacimiento.Text = "" + paciente.fechaNacimiento;
                txtGenero.Text = "" + paciente.genero;
                //chkHistorialActivo.Checked = paciente.historialActivo;
                chkActivo.Checked = paciente.activo;
            }
            else
            {
                Response.Redirect("/Views/GestionDatos/PacienteLista.aspx");
            }
        }

        private void desabilitarControles()
        {
            txtIdPaciente.Enabled = false;
            txtDNI.Enabled = false;
            txtNombre.Enabled = false;
            txtApellido.Enabled = false;
            txtCorreo.Enabled = false;
            txtNum.Enabled = false;
            txtDirreccion.Enabled = false;
            txtFechaNacimiento.Enabled = false;
            txtGenero.Enabled = false;
            chkHistorialActivo.Enabled = false;
            chkActivo.Enabled = false;
        }

        protected void btnAceptar_Click(object sender, EventArgs e)
        {
            if (opcion == "new")
            {
                paciente paciente = new paciente();

                paciente.DNI = txtDNI.Text;
                paciente.nombre = txtNombre.Text;
                paciente.apellido = txtApellido.Text;
                paciente.correoElectronico = txtCorreo.Text;
                paciente.numTelefono = Int32.Parse(txtNum.Text);
                paciente.direccion = txtDirreccion.Text;
                paciente.fechaNacimiento = DateTime.Parse(txtFechaNacimiento.Text);
                paciente.fechaNacimientoSpecified = true;
                paciente.genero = Char.Parse(txtGenero.Text);
                //paciente.historialActivo = chkHistorialActivo.Checked;
                paciente.activo = chkActivo.Checked;

                if (pacienteAPI.insertarPaciente1(paciente) != 0)
                {
                    Response.Redirect("/Views/GestionDatos/PacienteLista.aspx");
                }
            }
            else if (!string.IsNullOrEmpty(txtIdPaciente.Text))
            {
                paciente paciente = new paciente();

                paciente.idPaciente = Int32.Parse(txtIdPaciente.Text);
                paciente.DNI = txtDNI.Text;
                paciente.nombre = txtNombre.Text;
                paciente.apellido = txtApellido.Text;
                paciente.correoElectronico = txtCorreo.Text;
                paciente.numTelefono = Int32.Parse(txtNum.Text);
                paciente.direccion = txtDirreccion.Text;
                paciente.fechaNacimiento = DateTime.Parse(txtFechaNacimiento.Text);
                paciente.fechaNacimientoSpecified = true;
                paciente.genero = Char.Parse(txtGenero.Text);
                //paciente.historialActivo = chkHistorialActivo.Checked;
                paciente.activo = chkActivo.Checked;

                if (pacienteAPI.modificarPaciente(paciente) != 0)
                {
                    Response.Redirect("/Views/GestionDatos/PacienteLista.aspx");
                }
            }
        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            Response.Redirect("/Views/GestionDatos/PacienteLista.aspx");
        }
    }
}
