using ClinicaDA.DAO;
using ClinicaDA.MYSQL;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ClinicaWeb.Views
{
    public partial class Medicos : System.Web.UI.Page
    {
        protected MedicoDAO medicoDAO;
        protected void Page_Init(object sender, EventArgs e)
        {
            medicoDAO = new MedicoMySQL();
        }
        protected void Page_Load(object sender, EventArgs e)
        {
            gvMedicos.DataSource = medicoDAO.listar();
            gvMedicos.DataBind();
        }

        protected void gvMedicos_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvMedicos.PageIndex = e.NewPageIndex;
            gvMedicos.DataBind();
        }
    }
}