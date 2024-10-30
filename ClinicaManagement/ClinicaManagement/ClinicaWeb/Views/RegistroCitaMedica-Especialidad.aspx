<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Plantilla.Master" AutoEventWireup="true" CodeBehind="RegistroCitaMedica-Especialidad.aspx.cs" Inherits="ClinicaWeb.Views.WebForm1" %>

<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
    Registro de citas médicas por especialidad
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="Body" runat="server">
    <div class="container mt-5">
        <div class="card shadow-lg">
            <div class="card-header text-center bg-primary text-white">
                <h3>Registro de citas médicas por especialidad</h3>
            </div>
            <div class="card-body">
                <div class="form-group">
                    <label for="ddlEspecialidades">Seleccione una especialidad:</label>
                    <asp:DropDownList ID="ddlEspecialidades" CssClass="form-control" runat="server" AutoPostBack="true">
                    </asp:DropDownList>
                </div>
                <!-- Puedes agregar más campos aquí -->
            </div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="Scripts" runat="server">
    <!-- Scripts adicionales -->
</asp:Content>
