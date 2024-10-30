<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Main.Master" AutoEventWireup="true" CodeBehind="EspecialidadGestion.aspx.cs" Inherits="TestWeb.Views.EspecialidadGestion" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Scripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Body" runat="server">
    <div class="container">
    <div class="card">
        <div class="card-header">
            <h2>Registrar una Especialidad</h2>
        </div>
        <div class="card-body">
            <div class="mb-3">
                <label for="txtIdEspecialidad" class="form-label">Id Especialidad</label>
                <asp:TextBox ID="txtIdEspecialidad" CssClass="form-control" ReadOnly="true" runat="server"></asp:TextBox>                    
            </div>
            <div class="mb-3">
                <label for="txtNombre" class="form-label">Nombre de la Especialidad</label>
                <asp:TextBox ID="txtNombre" CssClass="form-control"  runat="server"></asp:TextBox>                    
            </div>
            <div class="mb-3">
                <label for="txtCostoConsulta" class="form-label">Costo de la Consulta</label>
                <asp:TextBox ID="txtCostoConsulta" CssClass="form-control" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3 form-check">
                <label class="form-check-label" for="chkActivo">¿Esta Activo?</label>
                <asp:CheckBox ID="chkActivo" CssClass="form-check-input" runat="server" />                    
            </div>
        </div>
        <div class="card-footer">
            <asp:Button ID="btnAceptar" CssClass="btn btn-success" runat="server" Text="Aceptar"
                OnClick="btnAceptar_Click"/>
            <asp:Button ID="btnCancelar" CssClass="btn btn-primary" runat="server" Text="Cancelar"
                OnClick="btnCancelar_Click"/>
        </div>
    </div>
</div>
</asp:Content>
