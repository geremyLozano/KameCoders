<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Plantilla.Master" AutoEventWireup="true" CodeBehind="Medicos.aspx.cs" Inherits="ClinicaWeb.Views.Medicos" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
            
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Body" runat="server">

    <div class="content-wrapper">
    <div class="container">
        <h2>Gestión de Médicos</h2>

        <div class="row mb-3">
            <div class="col-8">
                <div class="input-group">
                    <asp:TextBox ID="txtBuscarNombre" CssClass="form-control" placeholder="Ingresar nombre/apellido/DNI" runat="server"></asp:TextBox>
                    <asp:LinkButton ID="btnBuscar" CssClass="btn btn-secondary" runat="server">
                        <i class='fas fa-search'></i> Buscar
                    </asp:LinkButton>
                </div>
            </div>
            <div class="col-4 text-end">
                <asp:Button ID="BtnAgregar" runat="server" Text="Agregar Médico" CssClass="btn btn-success" />
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <asp:GridView ID="gvMedicos" AllowPaging="true" PageSize="5"
                    AutoGenerateColumns="false" OnPageIndexChanging="gvMedicos_PageIndexChanging"
                    CssClass="table table-striped table-hover w-100" runat="server" HeaderStyle-HorizontalAlign="Center">
                    <Columns>
                        <asp:BoundField HeaderText="Id Medico" DataField="IdMedico" ItemStyle-HorizontalAlign="Center"/>
                        <asp:BoundField HeaderText="Id Especialidad" DataField="Especialidad.IdEspecialidad" ItemStyle-HorizontalAlign="Center"/>
                        <asp:BoundField HeaderText="Número de Colegiatura" DataField="NumColegiatura" ItemStyle-HorizontalAlign="Center"/>
                        <asp:BoundField HeaderText="Hora Inicio" DataField="HoraInicio" ItemStyle-HorizontalAlign="Center"/>
                        <asp:BoundField HeaderText="Hora Final" DataField="HoraFin" ItemStyle-HorizontalAlign="Center"/>
                        <asp:BoundField HeaderText="Dias Laborales" DataField="DiasLaborales" ItemStyle-HorizontalAlign="Center"/>
                        <asp:BoundField HeaderText="Años de Exp." DataField="AnhosExp" ItemStyle-HorizontalAlign="Center"/>
                        <asp:TemplateField HeaderText="Acciones" HeaderStyle-CssClass="text-end" ItemStyle-CssClass="text-end">
                            <ItemTemplate>
                                <!-- Botón Ver -->
                                <asp:LinkButton ID="btnVer" runat="server" Text="<i class='fas fa-eye ps-2'></i>" />
                                
                                <!-- Botón Editar -->
                                <asp:LinkButton ID="btnEditar" runat="server" Text="<i class='fas fa-edit ps-2'></i>" />
                                
                                <!-- Botón Eliminar -->
                                <asp:LinkButton ID="btnEliminar" runat="server" Text="<i class='fas fa-trash ps-2'></i>" />
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                </asp:GridView>
            </div>
        </div>
    </div>
</div>


      <!-- content-wrapper ends -->
      <!-- partial:partials/_footer.html -->
      <footer class="footer">
        <div class="d-sm-flex justify-content-center justify-content-sm-between">
          <span class="text-muted d-block text-center text-sm-left d-sm-inline-block">Copyright © bootstrapdash.com 2020</span>
          <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center"> Free <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap dashboard templates</a> from Bootstrapdash.com</span>
        </div>
      </footer>
      <!-- partial -->
    </asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Scripts" runat="server">
</asp:Content>
