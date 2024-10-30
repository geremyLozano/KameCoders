using System;
//using TestModels;

namespace TestWeb
{
    public partial class Site1 : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            /*if (Session["Usuario"] != null) {
                Usuario user = (Usuario)Session["Usuario"];
                LblUsuario.Text = user.Nombre;
            }*/
        }

        protected void BtnCerrarSesion_Click(object sender, EventArgs e)
        {
            /*Session["Usuario"] = null;
            Response.Redirect("/Login.aspx");*/
        }
    }
}