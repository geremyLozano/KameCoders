<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Login.Master" AutoEventWireup="true" CodeBehind="RegistroLogin.aspx.cs" Inherits="ClinicaWeb.Views.RegistroLogin" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        function mostrarCamposMedico() {
            var rol = document.getElementById('<%= ddlRol.ClientID %>').value;
            var modal = document.getElementById('modalCamposMedico');
            var eyeContainer = document.getElementById('iconEyeContainer'); // Contenedor del ícono
            var auxiliarEspecialidadContainer = document.getElementById('auxiliarEspecialidadContainer');

            if (rol === 'medico') {
                modal.style.display = 'block'; // Mostrar modal si es médico
                eyeContainer.style.display = 'inline'; // Mostrar ícono del ojo
                auxiliarEspecialidadContainer.style.display = 'none';
            } else {
                modal.style.display = 'none'; // Ocultar modal
                eyeContainer.style.display = 'none'; // Ocultar ícono del ojo

                if (rol === 'auxiliar') {
                    auxiliarEspecialidadContainer.style.display = 'block';
                } else {
                    auxiliarEspecialidadContainer.style.display = 'none';
                }
            }
        }

        function cerrarModal() {
            document.getElementById('modalCamposMedico').style.display = 'none';
        }

        function mostrarError(mensaje) {
            var lblError = document.getElementById('<%= lblErrorRegistro.ClientID %>');
            if (lblError) {
                lblError.innerHTML = mensaje;
                lblError.style.display = 'block';
            }
        }

    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="LoginReg" runat="server">
    <div class="container">
        <div class="forms-container">
            <div class="signin-signup">
                <div class="sign-in-form">
                    <i class="fas fa-capsules login-icon"></i>
                    <h2 class="title">Bienvenido a CapsuleCare</h2>
                    <p class="subtitle">Tu salud, nuestra prioridad en cada cápsula de cuidado</p>
                    <div class="input-field">
                        <i class="fas fa-id-card"></i>
                        <asp:TextBox ID="txtDNI" runat="server" placeholder="DNI" />
                    </div>
                    <div class="input-field">
                        <i class="fas fa-lock"></i>
                        <asp:TextBox ID="txtPassword" runat="server" TextMode="Password" placeholder="Contraseña" />
                    </div>
                    <a href="#" class="reset-password-link2" onclick="openModal2(); return false;">Restablecer contraseña</a>
                    <asp:Button ID="btnLogin" runat="server" Text="Ingresar" CssClass="btn solid" OnClick="btnLogin_Click"/>
                    <asp:Label ID="lblErrorRegistro" runat="server" CssClass="error-message" Visible="false" Text="El DNI ingresado ya existe" />
                    <asp:Label ID="lblError" runat="server" ForeColor="Red" Visible="false"></asp:Label>
                </div>

                <!-- Modal de Restablecer Contraseña -->
                <div id="resetPasswordModal2" class="modal2">
                    <div class="modal-content2">
                        <h3>Restablecer Contraseña</h3>
                        <div class="modal-input-field2">
                            <i class="fas fa-user"></i>
                            <asp:TextBox ID="txtUsuarioReset" runat="server" CssClass="modal-input2" placeholder="Usuario" />
                        </div>
                        <div class="modal-input-field2">
                            <i class="fas fa-lock"></i>
                            <asp:TextBox ID="txtNewPassword" runat="server" TextMode="Password" placeholder="Nueva contraseña" CssClass="modal-input2" />
                        </div>
                        <div class="modal-input-field2">
                            <i class="fas fa-lock"></i>
                            <asp:TextBox ID="txtConfirmPassword" runat="server" TextMode="Password" placeholder="Confirmar contraseña" CssClass="modal-input2" />
                        </div>
                        <asp:Label ID="lblErrorRestablecer" runat="server" CssClass="error-message2" Visible="false"></asp:Label>
                        <div class="modal-buttons2">
                            <asp:Button ID="btnConfirmReset" runat="server" Text="CONFIRMAR" CssClass="btn-modal2 confirm2" OnClientClick="return validateReset();" OnClick="btnConfirmReset_Click" />
                            <asp:Button ID="btnBack" runat="server" Text="VOLVER" CssClass="btn-modal2 back2" OnClientClick="closeModal2(); return false;" />   
                        </div>
                    </div>
                </div>
                
                <div class="sign-up-form">
                    <h2 class="title">Registrarse</h2>
                    <div class="form-row">
                        <div class="input-field">
                            <i class="fas fa-id-card"></i>
                            <asp:TextBox ID="txtRegDNI" runat="server" placeholder="DNI" />
                        </div>
                        <div class="input-field">
                            <i class="fas fa-user"></i>
                            <asp:TextBox ID="txtNombre" runat="server" placeholder="Nombre" />
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="input-field">
                            <i class="fas fa-user"></i>
                            <asp:TextBox ID="txtApellido" runat="server" placeholder="Apellido" />
                        </div>
                        <div class="input-field">
                            <i class="fas fa-envelope"></i>
                            <asp:TextBox ID="txtEmail" runat="server" TextMode="Email" placeholder="Correo" />
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="input-field">
                            <i class="fas fa-phone"></i>
                            <asp:TextBox ID="txtTelefono" runat="server" placeholder="Teléfono" />
                        </div>
                        <div class="input-field">
                            <i class="fas fa-home"></i>
                            <asp:TextBox ID="txtDireccion" runat="server" placeholder="Dirección" />
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="input-field">
                            <i class="fas fa-calendar"></i>
                            <input id="dtpFechaNacimiento" runat="server" type="date" max=""/>
                        </div>
                        <div class="select-field">
                            <i class="fas fa-venus-mars"></i>
                            <asp:DropDownList ID="ddlGenero" runat="server">
                                <asp:ListItem Text="Género" Value="" disabled Selected />
                                <asp:ListItem Text="Masculino" Value="Masculino" />
                                <asp:ListItem Text="Femenino" Value="Femenino" />
                            </asp:DropDownList>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="select-field">
                            <i class="fas fa-user-tag"></i>
                            <asp:DropDownList ID="ddlRol" runat="server" onchange="mostrarCamposMedico()">
                                <asp:ListItem Text="Rol" Value="" disabled Selected />
                                <asp:ListItem Text="Paciente" Value="paciente" />
                                <asp:ListItem Text="Médico" Value="medico" />
                                <asp:ListItem Text="Administrador" Value="administrador" />
                                <asp:ListItem Text="Auxiliar" Value="auxiliar" />
                            </asp:DropDownList>
                            <span id="iconEyeContainer" style="display: none;">
                                <i class="fas fa-eye" onclick="mostrarCamposMedico()" title="Ver información médica"></i>
                            </span>
                        </div>
                        
                        <div class="input-field">
                            <i class="fas fa-lock"></i>
                            <asp:TextBox ID="txtRegPassword" runat="server" TextMode="Password" placeholder="Contraseña" />
                        </div>
                    </div>

                    <div class="form-row" id="listaEspecialidades">
                        <div id="auxiliarEspecialidadContainer" style="display: none;">
                            <div class="select-field" style="max-width: 100%;">
                                <i class="fas fa-stethoscope"></i>
                                <asp:DropDownList ID="ddlEspecialidad_aux" runat="server" CssClass="select-field-input"></asp:DropDownList>
                            </div>
                        </div>
                    </div>

         <!-- Modal para los campos de médico -->
        <div id="modalCamposMedico" class="modal" style="display: none;">
            <div class="modal-content">
                <span class="close" onclick="cerrarModal()">&times;</span>
                <h3 id="infoMedica">Información Médica</h3>
                <div class="form-row">
                    <div class="input-field">
                        <i class="fas fa-id-badge"></i>
                        <asp:TextBox ID="txtNumColegiatura" runat="server" placeholder="Num. Colegiatura" />
                    </div>
                    <div class="input-field">
                        <i class="fas fa-briefcase"></i>
                        <asp:TextBox ID="txtAnhosExperiencia" runat="server" placeholder="Años de Experiencia" min="0" />
                    </div>
                </div>
                <div class="form-row" id="listaEspecialidades2">
                    <div class="select-field" style="max-width: 100%;">
                        <i class="fas fa-stethoscope"></i>
                        <asp:DropDownList ID="ddlEspecialidad" runat="server" AutoPostBack="False" OnSelectedIndexChanged="ddlEspecialidad_SelectedIndexChanged">
                        </asp:DropDownList>
                    </div>
                </div>
            </div>
        </div>
                    <asp:Button ID="btnRegistro" runat="server" Text="Registrarse" CssClass="btn" OnClick="btnRegistro_Click" />
                </div>
            </div>
        </div>

        <div class="panels-container">
            <div class="panel left-panel">
                <div class="content">
                    <h3>¿Eres nuevo aquí?</h3>
                    <p>Regístrate para acceder a todos nuestros servicios.</p>
                    <button type="button" class="btn transparent" id="sign-up-btn">Registrarse</button>
                </div>
                <img src="/Public/images/log.svg" class="image" alt="" />
            </div>
            <div class="panel right-panel">
                <div class="content">
                    <h3>¿Ya tienes una cuenta?</h3>
                    <p>Inicia sesión para acceder a tu perfil.</p>
                    <button type="button" class="btn transparent" id="sign-in-btn">Iniciar Sesión</button>
                </div>
                <img src="/Public/images/register.svg" class="image" alt="" />
            </div>
        </div>
       </div>
    <script src="/Public/js/app.js"></script>
    <script src="/Public/js/loginReg.js"></script>
    <link rel="stylesheet" href="/Public/css/modal.css">
</asp:Content>
