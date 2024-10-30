using ClinicaWeb.CapsuleCareWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ClinicaWeb.Views
{
    public partial class HistorialMedico : System.Web.UI.Page
    {

        // private EventoWSClient daoEvento = new EventoWSClient();

        private HistorialMedicoWSClient daoHistorial = new HistorialMedicoWSClient();


        protected void Page_Init(object sender, EventArgs e)
        {

            historialMedico hist = new historialMedico();

            hist = daoHistorial.obtenerHistorialMedicoPorId(1);

            txtAltura.Text = "" + hist.altura;

            txtPeso.Text = "" + hist.peso;

            txtTipoDeSangre.Text = "" + hist.tipoSangre;

            txtEnfPreexist.InnerHtml = "" + hist.enferPreExist;


            txtAlergias.InnerHtml = "" + hist.alergias;

            txtCirugiasPrevias.InnerHtml = "" + hist.cirugiasPrevias;

            txtVacunas.InnerHtml = "" + hist.vacunas;




            txtAltura.Enabled = false; 
            txtPeso.Enabled = false;
            txtTipoDeSangre.Enabled = false;

        }



            protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void lbRegresar_Click(object sender, EventArgs e)
        {


            Response.Redirect("Principal.aspx");


        }



    }
}