<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Plantilla.Master" AutoEventWireup="true" CodeBehind="RegistrarCitaMedica-Especialidad.aspx.cs" Inherits="ClinicaWeb.Views.WebForm1" %>

<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
    Registro de Cita Médica
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="Body" runat="server">
    <div style="position: relative; min-height: 100vh; overflow: hidden;">
        <img src="../template/images/auth/lockscreen-bg.jpg" alt="Fondo" style="
             position: absolute;
             top: 0;
             left: 0;
             width: 100%;
             height: 100%;
             object-fit: cover;
             filter: brightness(50%);
             z-index: -1;">

        <div class="container mt-5">
            <asp:ScriptManager ID="ScriptManager1" runat="server" />

            <!-- Card con contenido -->
            <div class="card shadow-sm" style="border-radius: 20px; position: relative; z-index: 1;">
                <div class="card-body">
                    <h4 class="card-title text-center mb-4"><strong>Reservación de citas médicas</strong></h4>

                    <!-- UpdatePanel para actualizaciones parciales -->
                    <asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <!-- Campo de especialidad -->
                            <div class="form-group mb-2">
                                <label for="EspecialidadDropdown" class="form-label">
                                    <strong>Especialidad</strong>
                                </label>

                                <asp:DropDownList ID="EspecialidadDropdown" runat="server" CssClass="form-select"
                                                  AutoPostBack="true" OnSelectedIndexChanged="EspecialidadDropdown_SelectedIndexChanged">
                                </asp:DropDownList>
                            </div>

                            <!-- Panel de médicos -->
                            <asp:Panel ID="MedicoPanel" runat="server" CssClass="fade-in-panel" Visible="false">
                                <div class="form-group mb-2">
                                    <label for="MedicoDropdown" class="form-label">
                                        <strong>Médico</strong>
                                    </label>

                                    <asp:DropDownList ID="MedicoDropdown" runat="server" CssClass="form-select"
                                                      AutoPostBack="true" OnSelectedIndexChanged="MedicoDropdown_SelectedIndexChanged">
                                    </asp:DropDownList>
                                </div>
                            </asp:Panel>

                            <!-- Panel de información del médico -->
                            <asp:Panel ID="InfoMedicoPanel" runat="server" CssClass="info-medico-panel" Visible="false">
                                <div class="mt-3 p-3" style="background-color: rgba(0, 0, 0, 0.05); border-radius: 5px;">
                                    <p class="mb-1"><strong>Días Laborables:</strong> 
                                        <asp:Label ID="LblDiasLaborables" runat="server" CssClass="text-muted"></asp:Label>
                                    </p>
                                    <p class="mb-0"><strong>Horario Laboral:</strong> 
                                        <asp:Label ID="LblHorarioLaboral" runat="server" CssClass="text-muted"></asp:Label>
                                    </p>
                                </div>
                            </asp:Panel>

                            <!-- Panel de selección de día y hora -->
                            <asp:Panel ID="SeleccionPanel" runat="server" CssClass="fade-in-panel" Visible="false">
                                <div class="row mt-3">
                                    <div class="col-md-6">
                                        <label for="DiaDropdown" class="form-label">
                                            <strong>Seleccionar Día</strong>
                                        </label>
                                        <asp:DropDownList ID="DiaDropdown" runat="server" CssClass="form-select"
                                                          AutoPostBack="true" OnSelectedIndexChanged="DiaDropdown_SelectedIndexChanged">
                                        </asp:DropDownList>
                                    </div>

                                    <div class="col-md-6">
                                        <label for="HoraDropdown" class="form-label">
                                            <strong>Seleccionar Hora</strong>
                                        </label>
                                        <asp:DropDownList ID="HoraDropdown" runat="server" CssClass="form-select"
                                                          AutoPostBack="true" OnSelectedIndexChanged="HoraDropdown_SelectedIndexChanged">
                                        </asp:DropDownList>
                                    </div>
                                </div>
                            </asp:Panel>

                            <!-- Panel de botones de Regresar y Pagar -->
                            <asp:Panel ID="BotonesPanel" runat="server" CssClass="fade-in-panel text-center mt-3" Visible="false">
                                <button type="button" class="btn btn-secondary me-2">Regresar</button>
                                <!-- Botón para abrir el modal de pago -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#pagoModal" style="background-color: #0078D4;"><strong>Pagar</strong></button>
                            </asp:Panel>
                        </ContentTemplate>
                        <Triggers>
                            <asp:AsyncPostBackTrigger ControlID="EspecialidadDropdown" EventName="SelectedIndexChanged" />
                            <asp:AsyncPostBackTrigger ControlID="MedicoDropdown" EventName="SelectedIndexChanged" />
                            <asp:AsyncPostBackTrigger ControlID="DiaDropdown" EventName="SelectedIndexChanged" />
                            <asp:AsyncPostBackTrigger ControlID="HoraDropdown" EventName="SelectedIndexChanged" />
                        </Triggers>
                    </asp:UpdatePanel>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para el pago con tarjeta de crédito -->
    <div class="modal fade" id="pagoModal" tabindex="-1" aria-labelledby="pagoModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="pagoModalLabel"><strong>Pago con Tarjeta de Crédito</strong></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>

                <!-- Imágenes de tarjetas de crédito -->
                <!-- Imágenes de tarjetas de crédito -->
                <div class="text-end mb-2" style="margin-top: 5px; padding-right: 10px;">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/4/41/Visa_Logo.png" alt="Visa" style="width: 40px; margin-right: 5px; vertical-align: middle;">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/2/2a/Mastercard-logo.svg" alt="Mastercard" style="width: 40px; vertical-align: middle;">
                </div>

                <div class="modal-body">
                    <form>
                        <div class="input-group mb-3" style="margin-top: -5px;">
                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                            <input type="text" class="form-control" id="nombreTarjeta" placeholder="Nombre en la tarjeta" required>
                        </div>

                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="bi bi-credit-card"></i></span>
                            <input type="text" class="form-control" id="numeroTarjeta" maxlength="16" placeholder="Número de tarjeta" required>
                        </div>

                        <div class="row">
                            <div class="col-6 mb-3">
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-calendar-event"></i></span>
                                    <input type="month" class="form-control" id="expiracion" required>
                                </div>
                            </div>

                            <div class="col-6 mb-3">
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-lock"></i></span>
                                    <input type="password" class="form-control" id="cvv" maxlength="3" placeholder="CVV" required>
                                </div>
                            </div>
                        </div>

                        <!-- Recuadro para el monto total -->
                        <div class="mt-2 py-2 px-3" style="background-color: #e8f5e9; border: 1px solid #a5d6a7; border-radius: 5px; text-align: center;">
                            <span style="color: #2e7d32; font-size: 16px;"><strong>Total a Pagar: S/150</strong></span>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" runat="server" onserverclick="ProcesarPago_Click">Procesar Pago</button>
                </div>
            </div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="Scripts" runat="server">
    <!-- Bootstrap JS y CSS para íconos -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

    <!-- CSS para ajustar el ancho del modal -->
    <style>
        .modal-dialog {
            max-width: 350px; /* Ajusta el ancho según lo necesites */
        }
    </style>


    <!-- Script de manejo del efecto de desvanecimiento y reset de paneles -->
    <script type="text/javascript">
        $(document).ready(function () {
            var panelVisible = {
                medicoPanel: false,
                infoMedicoPanel: false,
                seleccionPanel: false,
                botonesPanel: false
            };

            var prm = Sys.WebForms.PageRequestManager.getInstance();

            prm.add_endRequest(function () {
                var medicoPanel = $('#<%= MedicoPanel.ClientID %>');
                var infoMedicoPanel = $('#<%= InfoMedicoPanel.ClientID %>');
                var seleccionPanel = $('#<%= SeleccionPanel.ClientID %>');
                var botonesPanel = $('#<%= BotonesPanel.ClientID %>');

                if (medicoPanel.is(':visible') && !panelVisible.medicoPanel) {
                    medicoPanel.hide().fadeIn(500);
                    panelVisible.medicoPanel = true;
                } else if (!medicoPanel.is(':visible')) {
                    panelVisible.medicoPanel = false;
                }

                if (infoMedicoPanel.is(':visible') && !panelVisible.infoMedicoPanel) {
                    infoMedicoPanel.hide().fadeIn(500);
                    panelVisible.infoMedicoPanel = true;
                } else if (!infoMedicoPanel.is(':visible')) {
                    panelVisible.infoMedicoPanel = false;
                }

                if (seleccionPanel.is(':visible') && !panelVisible.seleccionPanel) {
                    seleccionPanel.hide().fadeIn(500);
                    panelVisible.seleccionPanel = true;
                } else if (!seleccionPanel.is(':visible')) {
                    panelVisible.seleccionPanel = false;
                }

                if (botonesPanel.is(':visible') && !panelVisible.botonesPanel) {
                    botonesPanel.hide().fadeIn(500);
                    panelVisible.botonesPanel = true;
                } else if (!botonesPanel.is(':visible')) {
                    panelVisible.botonesPanel = false;
                }
            });

            function resetPanels() {
                $('#<%= MedicoPanel.ClientID %>').hide();
                $('#<%= InfoMedicoPanel.ClientID %>').hide();
                $('#<%= SeleccionPanel.ClientID %>').hide();
                $('#<%= BotonesPanel.ClientID %>').hide();

                panelVisible.medicoPanel = false;
                panelVisible.infoMedicoPanel = false;
                panelVisible.seleccionPanel = false;
                panelVisible.botonesPanel = false;
            }

            $('#<%= EspecialidadDropdown.ClientID %>').on('change', function () {
                resetPanels();
            });

            $('#<%= MedicoDropdown.ClientID %>').on('change', function () {
                resetPanels();
            });

            $('#<%= DiaDropdown.ClientID %>').on('change', function () {
                resetPanels();
            });
        });
    </script>
</asp:Content>
