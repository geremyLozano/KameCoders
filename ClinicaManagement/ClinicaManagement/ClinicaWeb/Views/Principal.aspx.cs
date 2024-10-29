using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ClinicaWeb.Views
{
    public partial class Principal : System.Web.UI.Page
    {
        protected void Page_Init(object sender, EventArgs e)
        {
            if (Session["Usuario"] == null && !Request.Url.AbsolutePath.EndsWith("RegistroLogin.aspx"))
            {
                Response.Redirect("RegistroLogin.aspx");
            }
            else if (Session["Usuario"] != null && Request.Url.AbsolutePath.EndsWith("RegistroLogin.aspx"))
            {
                Response.Redirect("Principal.aspx");
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }
    }
}