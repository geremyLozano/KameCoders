<%@ Page Title="" Language="C#" MasterPageFile="~/Master/Plantilla.Master" AutoEventWireup="true" CodeBehind="HistorialMedico.aspx.cs" Inherits="ClinicaWeb.Views.HistorialMedico" %>
<asp:Content ID="Content1" ContentPlaceHolderID="Title" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="Body" runat="server">



        <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>
                    <!-- Cambiar el titulo dependiendo de si se registran o muestran datos -->
                    <asp:Label ID="lblTitulo" runat="server" Text="Historial medico:"></asp:Label>
                </h2>
            </div>
            <div class="card-body pb-2">
                <div class="row mt-2">
                 
                    <div class="col-md-6">


                     <div class="col-md-6">                      
                          <asp:Label ID="lblNombres" runat="server" Text="Nombres:" CssClass="col-form-label fw-bold"></asp:Label>
                          <asp:TextBox ID="txtNombres" runat="server" CssClass="form-control" ></asp:TextBox>
                     </div>
                            
                    </div>

                     <div class="col-md-6">
                       <asp:Label ID="lblFechaNacimiento" runat="server" Text="Fecha de nacimiento:" CssClass="col-form-label fw-bold" ></asp:Label>
                       <input id="dtpFechaNacimiento" runat="server" type="date" class="form-control"/>
                     </div>



                </div>


                 <div class="row mt-2">

                        <div class="col-md-6">                      
                             <asp:Label ID="lblApellidos" runat="server" Text="Apellidos:" CssClass="col-form-label fw-bold"></asp:Label>
                             <asp:TextBox ID="txtApellidos" runat="server" CssClass="form-control" ></asp:TextBox>
                        </div>

                      <div class="col-md-6">  
                          <asp:Label ID="Label2" runat="server" Text="Genero:" CssClass="col-form-label fw-bold"></asp:Label>
                          <div class="form-control">
                              <div class="form-check form-check-inline">
                                  <input id="rbMasculino" class="form-check-input" type="radio" runat="server" name="tipoGenero"/>
                                  <label id="lblMasculino" class="form-check-label" for="cphContenido_rbMasculino">Masculino</label>
                              </div>
                              <div class="form-check form-check-inline">
                                  <input id="rbFemenino" class="form-check-input" type="radio" runat="server" name="tipoGenero"/>
                                  <label id="lblFemenino" class="form-check-label" for="cphContenido_rbFemenino">Femenino</label>
                              </div>
                          </div>   
                      </div>


                </div>














                <div class="row mt-2">

                    <div class="col-md-6">
                         <asp:Label ID="lblDni" runat="server" Text="DNI:" CssClass="col-form-label fw-bold"></asp:Label>
                         <asp:TextBox ID="txtDni" runat="server" CssClass="form-control" ></asp:TextBox>
                    </div>

                   
                     <div class="col-md-6">                      
                         <asp:Label ID="lblPeso" runat="server" Text="Peso:" CssClass="col-form-label fw-bold"></asp:Label>
                         <asp:TextBox ID="txtPeso" runat="server" CssClass="form-control" ></asp:TextBox>
                    </div>



                </div>


                 <div class="row mt-2">

                      <div class="col-md-6">                      
                           <asp:Label ID="lblAltura" runat="server" Text="Altura:" CssClass="col-form-label fw-bold"></asp:Label>
                           <asp:TextBox ID="txtAltura" runat="server" CssClass="form-control" ></asp:TextBox>
                      </div>


                       <div class="col-md-6">                      
                          <asp:Label ID="lblTipoDeSangre" runat="server" Text="Tipo de sangre:" CssClass="col-form-label fw-bold"></asp:Label>
                          <asp:TextBox ID="txtTipoDeSangre" runat="server" CssClass="form-control" ></asp:TextBox>
                     </div>

                 </div>




                 <div class="row mt-2">

                     <div class="col-md-12 mb-3">
                            <asp:Label ID="lblEnfPreexist" runat="server" Text="Enfermedades preexistentes:" CssClass="col-sm-3 col-form-label fw-bold" />
                            <textarea id="txtEnfPreexist" class="form-control" rows="3" cols="20" runat="server" ></textarea>
                     </div>


                       



                 </div>


                  <div class="row mt-2">

                      <div class="col-md-12 mb-3">
                             <asp:Label ID="lblAlergias" runat="server" Text="Alergias:" CssClass="col-sm-3 col-form-label fw-bold" />
                             <textarea id="txtAlergias" class="form-control" rows="3" cols="20" runat="server" ></textarea>
                      </div>


                      



                  </div>



                <div class="row mt-2">

                    <div class="col-md-12 mb-3">
                           <asp:Label ID="lblCirugiasPrevias" runat="server" Text="Cirugias previas:" CssClass="col-sm-3 col-form-label fw-bold" />
                           <textarea id="txtCirugiasPrevias" class="form-control" rows="3" cols="20" runat="server" ></textarea>
                    </div>


                    
                </div>








                 <div class="row mt-2">

                     <div class="col-md-12 mb-3">
                            <asp:Label ID="lblVacunas" runat="server" Text="Vacunas:" CssClass="col-sm-3 col-form-label fw-bold" />
                            <textarea id="txtVacunas" class="form-control" rows="3" cols="20" runat="server" readonly="readonly"></textarea>
                     </div>


                      
                 </div>




            </div>

            
            <div class="card-footer clearfix">
                <asp:LinkButton ID="lbRegresar" runat="server" Text="<i class='fa-solid fa-rotate-left'></i> Regresar" CssClass="float-start btn btn-secondary" OnClick="lbRegresar_Click"/>
            
            </div>



          
        </div>
    </div>

  






</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="Scripts" runat="server">




</asp:Content>
