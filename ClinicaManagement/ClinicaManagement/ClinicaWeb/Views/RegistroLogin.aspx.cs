using ClinicaDA.DAO;
using ClinicaDA.MYSQL;
using ClinicaModel;
using ClinicaWeb.CapsuleCareWS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace ClinicaWeb.Views
{
    public partial class RegistroLogin : System.Web.UI.Page
    {
        private MedicoDAO usuarioDAO;
        private PacienteWSClient pacienteAPI;
        private MedicoWSClient medicoAPI;
        private AdministradorWSClient administradorAPI;
        private AuxiliarWSClient auxiliarAPI;
        private EspecialidadWSClient especialidadAPI;
        private UsuarioWSClient usuarioAPI;

        protected void Page_Init(object sender, EventArgs e)
        {
            pacienteAPI = new PacienteWSClient();
            medicoAPI = new MedicoWSClient();
            administradorAPI = new AdministradorWSClient();
            auxiliarAPI = new AuxiliarWSClient();
            especialidadAPI = new EspecialidadWSClient();
            usuarioAPI = new UsuarioWSClient();

            if (!IsPostBack)
            {
                ddlRol.Attributes.Add("onchange", "mostrarCamposMedico()");
                ddlEspecialidad.DataSource = especialidadAPI.listarEspecialidades();
                ddlEspecialidad.DataTextField = "nombre";
                ddlEspecialidad.DataValueField = "idEspecialidad";
                ddlEspecialidad.DataBind();

                ddlEspecialidad.Items.Insert(0, new ListItem("Seleccione Especialidad", "", true));

                ddlEspecialidad_aux.DataSource = especialidadAPI.listarEspecialidades();
                ddlEspecialidad_aux.DataTextField = "nombre";
                ddlEspecialidad_aux.DataValueField = "idEspecialidad";
                ddlEspecialidad_aux.DataBind();

                ddlEspecialidad_aux.Items.Insert(0, new ListItem("Seleccione Especialidad", "", true));
            }
        }
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void btnLogin_Click(object sender, EventArgs e)
        {
            string username = txtDNI.Text;
            string password = txtPassword.Text;
            usuario user = validarCredenciales(username, password);
            if (user != null)
            {
                Session["Usuario"] = user;
                Response.Redirect("/Views/Principal.aspx");
            }
            else
            {
                lblError.Text = "Usuario o contraseña incorrectos. Inténtelo de nuevo.";
                lblError.Visible = true;
            }
        }

        usuario validarCredenciales(string username, string password)
        {
            usuario usuario = usuarioAPI.verificarUsuario(username, password);
            return usuario;
        }

        protected void btnRegistro_Click(object sender, EventArgs e)
        {
            string dni = txtRegDNI.Text;
            string nombre = txtNombre.Text;
            string apellido = txtApellido.Text;
            string email = txtEmail.Text;
            int telefono = int.Parse(txtTelefono.Text);
            string direccion = txtDireccion.Text;
            char genero = ddlGenero.SelectedValue[0];
            DateTime fecha = DateTime.Parse(dtpFechaNacimiento.Value);
            string rol = ddlRol.SelectedValue;
            string contrasenha = txtRegPassword.Text;

            usuario usuario = new usuario()
            {
                username = dni,
                contrasenha = contrasenha
            };

            if (rol == "paciente")
            {
                paciente paciente = new paciente();
                paciente.DNI = dni;
                paciente.nombre = nombre;
                paciente.apellido = apellido;
                paciente.correoElectronico = email;
                paciente.numTelefono = telefono;
                paciente.direccion = direccion;
                paciente.fechaNacimiento = fecha;
                paciente.fechaNacimientoSpecified = true;
                paciente.genero = genero;
                int resultado = pacienteAPI.insertarPaciente(paciente, usuario);
            }
            else if (rol == "medico")
            {
                especialidad especialidad = new especialidad()
                {
                    idEspecialidad = int.Parse(ddlEspecialidad.SelectedValue)
                };
                medico medico = new medico()
                {
                    DNI = dni,
                    nombre = nombre,
                    apellido = apellido,
                    correoElectronico = email,
                    numTelefono = telefono,
                    direccion = direccion,
                    fechaNacimiento = fecha,
                    genero = genero,
                    numColegiatura = txtNumColegiatura.Text,
                    especialidad = especialidad,
                    ahosExp = int.Parse(txtAnhosExperiencia.Text)
                };
                int resultado = medicoAPI.insertarMedico(medico);
            }
            else if (rol == "administrador")
            {
                administrador administrador = new administrador()
                {
                    DNI = dni,
                    nombre = nombre,
                    apellido = apellido,
                    correoElectronico = email,
                    numTelefono = telefono,
                    direccion = direccion,
                    fechaNacimiento = fecha,
                    genero = genero
                };
                int resultado = administradorAPI.insertarAdministrador(administrador, usuario);
            }
            else if (rol == "auxiliar")
            {
                especialidad especialidad = new especialidad()
                {
                    idEspecialidad = int.Parse(ddlEspecialidad.SelectedValue)
                };

                auxiliar auxiliar = new auxiliar()
                {
                    DNI = dni,
                    nombre = nombre,
                    apellido = apellido,
                    correoElectronico = email,
                    numTelefono = telefono,
                    direccion = direccion,
                    especialidad = especialidad,
                    fechaNacimiento = fecha,
                    genero = genero
                };
                int resultado = auxiliarAPI.insertarAuxiliar(auxiliar, usuario);
            }
        }

        protected void ddlEspecialidad_SelectedIndexChanged(object sender, EventArgs e)
        {

        }
    }
}