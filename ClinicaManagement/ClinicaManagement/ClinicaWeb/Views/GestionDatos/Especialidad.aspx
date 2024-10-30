<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Main.Master" AutoEventWireup="true" CodeBehind="Especialidad.aspx.cs" Inherits="ClinicaWeb.Views.GestionDatos.Especialidad" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Scripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Body" runat="server">
    <div class="container">
    <h2>Listado de Especialidades</h2>
    <div class="container row">
        <div class="text-end pb-3">
            <asp:Button ID="btnAgregar" runat="server"
                CssClass="btn btn-success" OnClick="btnAgregar_Click"
                Text="Agregar Especialidad" />
        </div>
    </div>

    <div class="container row">
        <asp:GridView ID="gvEspecialidad" AllowPaging="true" PageSize="5"
            AutoGenerateColumns="false" OnPageIndexChanging="gvEspecialidad_PageIndexChanging"
            CssClass="table table-striped table-responsive table-hover"
            runat="server">

            <Columns>
                <asp:BoundField HeaderText="Id Especialidad" DataField="idEspecialidad" />
                <asp:BoundField HeaderText="Nombre" DataField="nombre" />
                <asp:BoundField HeaderText="Costo Consulta" DataField="costoConsulta" />
                <asp:BoundField HeaderText="Activo" DataField="activo" />
                <asp:TemplateField HeaderText="Acciones"
                    HeaderStyle-CssClass="text-end" ItemStyle-CssClass="text-end">
                    <ItemTemplate>
                        <asp:LinkButton ID="btnVer" runat="server"
                            CommandArgument='<%# Eval("idEspecialidad") %>'
                            OnClick="btnVer_Click"
                            Text="<i class='fas fa-eye ps-2'></i>" />
                        <asp:LinkButton ID="btnEditar" runat="server"
                            CommandArgument='<%# Eval("idEspecialidad") %>'
                            OnClick="btnEditar_Click"
                            Text="<i class='fas fa-edit ps-2'></i>" />
                        <asp:LinkButton ID="btnEliminar" runat="server"
                            CommandArgument='<%# Eval("idEspecialidad") %>'
                            OnClick="btnEliminar_Click"
                            OnClientClick="return confirm('¿Estas seguro de eliminar?')"
                            Text="<i class='fas fa-trash ps-2'></i>" />

                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>

        </asp:GridView>
    </div>
</div>
</asp:Content>
