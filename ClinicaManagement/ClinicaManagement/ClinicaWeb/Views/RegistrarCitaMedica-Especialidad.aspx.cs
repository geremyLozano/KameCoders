using System;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Collections.Generic;
using System.Linq;
using ClinicaDA.DAO;
using ClinicaDA.MYSQL;
using ClinicaModel;

namespace ClinicaWeb.Views
{
    public partial class WebForm1 : Page
    {
        private MedicoDAO medicoDAO = new MedicoMySQL();
        private EspecialidadDAO especialidadDAO = new EspecialidadMySQL();
        private PagoDAO pagoDAO = new PagoMySQL();
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarEspecialidades();
            }
        }

        private void CargarEspecialidades()
        {
            List<Especialidad> especialidades = especialidadDAO.listar();
            EspecialidadDropdown.Items.Clear();
            EspecialidadDropdown.Items.Add(new ListItem("Seleccione una especialidad", ""));
            foreach (var especialidad in especialidades)
            {
                EspecialidadDropdown.Items.Add(new ListItem(especialidad.Nombre, especialidad.IdEspecialidad.ToString()));
            }
        }

        protected void EspecialidadDropdown_SelectedIndexChanged(object sender, EventArgs e)
        {
            string especialidadSeleccionada = EspecialidadDropdown.SelectedItem.Text;

            if (!string.IsNullOrEmpty(especialidadSeleccionada) && especialidadSeleccionada != "Seleccione una especialidad")
            {
                List<Medico> medicos = medicoDAO.obtenerPorEspecialidad(especialidadSeleccionada);
                Session["MedicosCargados"] = medicos;
                CargarMedicos(medicos);
                MedicoPanel.Visible = true;

                // Reiniciar y ocultar paneles posteriores
                DiaDropdown.SelectedIndex = -1;
                HoraDropdown.SelectedIndex = -1;
                InfoMedicoPanel.Visible = false;
                SeleccionPanel.Visible = false;
                BotonesPanel.Visible = false;
            }
            else
            {
                // Ocultar todo si no se selecciona una especialidad válida
                MedicoDropdown.SelectedIndex = -1;
                DiaDropdown.SelectedIndex = -1;
                HoraDropdown.SelectedIndex = -1;
                MedicoPanel.Visible = false;
                InfoMedicoPanel.Visible = false;
                SeleccionPanel.Visible = false;
                BotonesPanel.Visible = false;
            }
        }

        private void CargarMedicos(List<Medico> medicos)
        {
            MedicoDropdown.Items.Clear();
            MedicoDropdown.Items.Add(new ListItem("Seleccione un médico", ""));
            foreach (var medico in medicos)
            {
                string nombreCompleto = $"{medico.Nombre} {medico.Apellido}";
                MedicoDropdown.Items.Add(new ListItem(nombreCompleto, medico.IdMedico.ToString()));
            }
        }

        protected void MedicoDropdown_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (MedicoDropdown.SelectedIndex > 0)
            {
                List<Medico> medicosCargados = Session["MedicosCargados"] as List<Medico>;

                if (medicosCargados != null)
                {
                    int idMedicoSeleccionado = int.Parse(MedicoDropdown.SelectedValue);
                    Medico medicoSeleccionado = medicosCargados.Find(m => m.IdMedico == idMedicoSeleccionado);

                    if (medicoSeleccionado != null)
                    {
                        // Mostrar información del médico y días laborales
                        LblDiasLaborables.Text = medicoSeleccionado.DiasLaborales;
                        LblHorarioLaboral.Text = $"{medicoSeleccionado.HoraInicio:hh\\:mm} - {medicoSeleccionado.HoraFin:hh\\:mm}";

                        List<string> diasLaborables = medicoSeleccionado.DiasLaborales.Split('-').ToList();

                        DiaDropdown.Items.Clear();
                        DiaDropdown.Items.Add(new ListItem("Seleccione un día", ""));
                        foreach (var dia in diasLaborables)
                        {
                            DiaDropdown.Items.Add(new ListItem(dia, dia));
                        }

                        HoraDropdown.Items.Clear();
                        HoraDropdown.Items.Add(new ListItem("Seleccione una hora", ""));
                        TimeSpan horaInicio = medicoSeleccionado.HoraInicio;
                        TimeSpan horaFin = medicoSeleccionado.HoraFin;
                        while (horaInicio <= horaFin)
                        {
                            HoraDropdown.Items.Add(new ListItem(horaInicio.ToString(@"hh\:mm"), horaInicio.ToString()));
                            horaInicio = horaInicio.Add(new TimeSpan(1, 0, 0));
                        }

                        InfoMedicoPanel.Visible = true;
                        SeleccionPanel.Visible = true;
                    }
                }
            }
            else
            {
                DiaDropdown.SelectedIndex = -1;
                HoraDropdown.SelectedIndex = -1;
                InfoMedicoPanel.Visible = false;
                SeleccionPanel.Visible = false;
                BotonesPanel.Visible = false;
            }
        }

        protected void DiaDropdown_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (DiaDropdown.SelectedIndex > 0)
            {
                SeleccionPanel.Visible = true;
            }
            else
            {
                HoraDropdown.SelectedIndex = -1;
                SeleccionPanel.Visible = false;
                BotonesPanel.Visible = false;
            }
        }

        protected void HoraDropdown_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (HoraDropdown.SelectedIndex > 0)
            {
                BotonesPanel.Visible = true;
            }
            else
            {
                BotonesPanel.Visible = false;
            }
        }

        protected void ProcesarPago_Click(object sender, EventArgs e)
        {
            // Obtener datos del formulario de pago
            string nombreTarjeta = Request.Form["nombreTarjeta"];
            string numeroTarjeta = Request.Form["numeroTarjeta"];
            string expiracion = Request.Form["expiracion"];
            string cvv = Request.Form["cvv"];

            // Crear una instancia de Pago
            Pago nuevoPago = new Pago();
            nuevoPago.DescuentoSeguro = 0;
            nuevoPago.Concepto = "Esto es un pago";
            nuevoPago.MontoParcial = 0;
            nuevoPago.IdPaciente = 2;
            nuevoPago.MontoTotal = 150.0;

            // Insertar el pago usando el DAO
            int resultado = pagoDAO.insertar(nuevoPago);

            if (resultado > 0)
            {
                // Mostrar un mensaje de éxito
                ScriptManager.RegisterStartupScript(this, GetType(), "PagoExitoso", "alert('Pago procesado exitosamente');", true);
            }
            else
            {
                // Mostrar un mensaje de error
                ScriptManager.RegisterStartupScript(this, GetType(), "PagoFallido", "alert('Error al procesar el pago');", true);
            }
        }

    }
}
