<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Main.Master" AutoEventWireup="true" CodeBehind="PacienteLista.aspx.cs" Inherits="TestWeb.Views.PacienteLista" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Scripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Body" runat="server">

    <div class="container">
        <h2>Listado de Pacientes</h2>
        <div class="container row">
            <div class="text-end pb-3">
                <asp:Button ID="btnAgregar" runat="server"
                    CssClass="btn btn-success" OnClick="btnAgregar_Click"
                    Text="Agregar Paciente" />
            </div>
        </div>

        <div class="container row">
            <asp:GridView ID="gvPaciente" AllowPaging="true" PageSize="5"
                AutoGenerateColumns="false" OnPageIndexChanging="gvPaciente_PageIndexChanging"
                CssClass="table table-striped table-responsive table-hover"
                runat="server">

                <columns>
                    <asp:BoundField HeaderText="Id Paciente" DataField="idPaciente" />
                    <asp:BoundField HeaderText="DNI" DataField="DNI" />
                    <asp:BoundField HeaderText="Nombre" DataField="nombre" />
                    <asp:BoundField HeaderText="Apellido" DataField="apellido" />
                    <asp:BoundField HeaderText="Correo" DataField="correoElectronico" />
                    
                    <asp:BoundField HeaderText="Fecha de Nacimiento" DataField="fechaNacimiento" />
      
                    <asp:BoundField HeaderText="Historial Activo" DataField="historialActivo" />
                    <asp:BoundField HeaderText="Activo" DataField="activo" />

                    <asp:TemplateField HeaderText="Acciones"
                        HeaderStyle-CssClass="text-end" ItemStyle-CssClass="text-end">
                        <itemtemplate>
                            <asp:LinkButton ID="btnVer" runat="server"
                                CommandArgument='<%# Eval("idPaciente") %>'
                                OnClick="btnVer_Click"
                                Text="<i class='fas fa-eye ps-2'></i>" />
                            <asp:LinkButton ID="btnEditar" runat="server"
                                CommandArgument='<%# Eval("idPaciente") %>'
                                OnClick="btnEditar_Click"
                                Text="<i class='fas fa-edit ps-2'></i>" />
                            <asp:LinkButton ID="btnEliminar" runat="server"
                                CommandArgument='<%# Eval("idPaciente") %>'
                                OnClick="btnEliminar_Click"
                                OnClientClick="return confirm('¿Estas seguro de eliminar?')"
                                Text="<i class='fas fa-trash ps-2'></i>" />

                        </itemtemplate>
                    </asp:TemplateField>
                </columns>

            </asp:GridView>
        </div>
    </div>
</asp:Content>
