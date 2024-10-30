<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Main.Master" AutoEventWireup="true" CodeBehind="PacienteGestion.aspx.cs" Inherits="ClinicaWeb.Views.GestionDatos.PacienteGestion" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Scripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Body" runat="server">
    <div class="container">
    <div class="card">
        <div class="card-header">
            <h2>Registrar un Paciente</h2>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="txtIdPaciente" class="form-label">Id Paciente</label>
                <asp:TextBox ID="txtIdPaciente" CssClass="form-control" ReadOnly="true" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtDNI" class="form-label">DNI</label>
                <asp:TextBox ID="txtDNI" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtNombre" class="form-label">Nombre del Paciente</label>
                <asp:TextBox ID="txtNombre" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtApellido" class="form-label">Apellidos del Paciente</label>
                <asp:TextBox ID="txtApellido" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtCorreo" class="form-label">Correo Electronico</label>
                <asp:TextBox ID="txtCorreo" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtNum" class="form-label">Numero de Telefono</label>
                <asp:TextBox ID="txtNum" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtDirreccion" class="form-label">Direccion</label>
                <asp:TextBox ID="txtDirreccion" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtFechaNacimiento" class="form-label">Fecha de Nacimiento</label>
                <asp:TextBox ID="txtFechaNacimiento" CssClass="form-control" runat="server" TextMode="Date"></asp:TextBox>
            </div>
            <div class="mb-3">
                <label for="txtGenero" class="form-label">Genero (M/F)</label>
                <asp:TextBox ID="txtGenero" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3 form-check">
                <label class="form-check-label" for="chkActivo">¿Historial Activo?</label>
                <asp:CheckBox ID="chkHistorialActivo" CssClass="form-check-input" runat="server" />
            </div>
            <div class="mb-3 form-check">
                <label class="form-check-label" for="chkActivo">¿Esta Activo?</label>
                <asp:CheckBox ID="chkActivo" CssClass="form-check-input" runat="server" />
            </div>
        </div>

        <div class="card-footer">
            <asp:Button ID="btnAceptar" CssClass="btn btn-success" runat="server" Text="Aceptar"
                OnClick="btnAceptar_Click" />
            <asp:Button ID="btnCancelar" CssClass="btn btn-primary" runat="server" Text="Cancelar"
                OnClick="btnCancelar_Click" />
        </div>
    </div>
 </div>
</asp:Content>
