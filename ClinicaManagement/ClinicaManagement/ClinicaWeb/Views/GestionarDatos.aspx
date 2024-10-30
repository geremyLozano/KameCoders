<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Main.Master" AutoEventWireup="true" CodeBehind="GestionarDatos.aspx.cs" Inherits="ClinicaWeb.Views.GestionarDatos" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Scripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Body" runat="server">
    <link href="../Public/css1/style.css" rel="stylesheet" />
    <nav style="--bs-breadcrumb-divider: '>'; font-size: 14px">
        <ol class="breadcrumb">
            <li class="breadcrumb-item">
                <i class="fa-solid fa-house"></i>
            </li>
            <li class="breadcrumb-item">Gestionar Datos</li>
            <li class="breadcrumb-item">Gestion</li>
        </ol>
    </nav>

    <div class="container">
        <h2>Gestion de Datos</h2>
        <div class="col">
            <a href="GestionDatos/Especialidad.aspx" class="boton">Especialidad</a>
            <br>
            <br>
            <a href="GestionDatos/PacienteLista.aspx" class="boton">Paciente</a><br>
            <br>
            
            
        </div>

    </div>
</asp:Content>
