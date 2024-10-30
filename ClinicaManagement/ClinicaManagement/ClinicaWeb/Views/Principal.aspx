<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Plantilla.Master" AutoEventWireup="true" CodeBehind="Principal.aspx.cs" Inherits="ClinicaWeb.Views.Principal" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Body" runat="server">
    <main role="main">
        <div class="jumbotron" style="position: relative; color: white; min-height: 400px;">
            <!-- Imagen de fondo usando img -->
            <img src="/template/images/auth/lockscreen-bg.jpg" style="width: 100%; height: 100%; object-fit: cover; position: absolute; top: 0; left: 0;">
            <div class="container position-relative" style="z-index: 1;">
                <h1 class="display-3">Bienvenido de regreso</h1>
                <p>Con CapsuleCare, la salud no espera, y tú tampoco deberías. Encuentra tu camino hacia el bienestar, una cita a la vez.</p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button">Nosotros</a></p>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-md-3">
                    <div class="card shadow-sm mb-4 rounded">
                        <div class="card-body">
                            <h2 class="card-title">Historial de citas</h2>
                            <p class="card-text">Consulta tus citas pasadas con detalles del médico, fecha y diagnóstico, para un seguimiento completo.</p>
                            <a class="btn btn-secondary" href="#" role="button">Ver historial</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card shadow-sm mb-4 rounded">
                        <div class="card-body">
                            <h2 class="card-title">Reservar cita</h2>
                            <p class="card-text">Agenda tu cita eligiendo al especialista, seleccionando la fecha y hora disponibles, y recibe tu consulta.</p>
                            <a class="btn btn-secondary" href="RegistroCitaMedica-Especialidad.aspx" role="button">Agendar cita</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card shadow-sm mb-4 rounded">
                        <div class="card-body">
                            <h2 class="card-title">Resultados Médicos</h2>
                            <p class="card-text">Accede a tus resultados de pruebas y análisis médicos de manera segura y desde cualquier lugar.</p>
                            <a class="btn btn-secondary" href="#" role="button">Ver resultados</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card shadow-sm mb-4 rounded">
                        <div class="card-body">
                            <h2 class="card-title">Reservar laboratorio</h2>
                            <p class="card-text">Programa tus pruebas de laboratorio seleccionando fecha y servicio de forma rápida y segura.</p>
                            <a class="btn btn-secondary" href="#" role="button">Agendar prueba</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card shadow-sm mb-4 rounded">
                        <div class="card-body">
                            <h2 class="card-title">Historial medico</h2>
                            <p class="card-text">Registro detallado de tu salud que incluye diagnósticos , alergias ,etc.</p>
                            <a class="btn btn-secondary" href="HistorialMedico.aspx" role="button">Ver historial medico</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card shadow-sm mb-4 rounded">
                        <div class="card-body">
                            <h2 class="card-title">Gestionar Datos</h2>
                            <p class="card-text">Programa tus pruebas de laboratorio seleccionando fecha y servicio de forma rápida y segura.</p>
                            <a class="btn btn-secondary" href="GestionarDatos.aspx" role="button">Agendar prueba</a>
                        </div>
                    </div>
                </div>
            </div>

            <hr>
        </div>
    </main>

    <footer class="container">
        <p>© CapsuleCare 2024</p>
    </footer>
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Scripts" runat="server">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="../../assets/js/vendor/popper.min.js"></script>
    <script src="../../dist/js/bootstrap.min.js"></script>
</asp:Content>
