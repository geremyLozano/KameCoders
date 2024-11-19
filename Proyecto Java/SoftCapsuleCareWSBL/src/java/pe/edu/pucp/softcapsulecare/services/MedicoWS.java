package pe.edu.pucp.softcapsulecare.services;


import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.io.File;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.edu.pucp.citamedica.dao.MedicoDAO;
import pe.edu.pucp.citamedica.model.clinica.Medico;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.MedicoMySQL;
import pe.edu.pucp.dbmanager.config.DBManager;


@WebService(serviceName = "MedicoWS")
public class MedicoWS {
    private MedicoDAO medicoDAO;
    
    public MedicoWS(){
        medicoDAO = new MedicoMySQL();
    }
    
    @WebMethod(operationName = "insertarMedico")
    public int medicoInsertar(@WebParam(name = "medico") Medico medico,
                              @WebParam(name = "usuario") Usuario usuario) {
        
        return medicoDAO.insertar(medico,usuario);
    }
    
    @WebMethod(operationName = "buscarPorEspecialidad")
    public ArrayList<Medico> listarPorEspecialidad(@WebParam(name = "especialidad") String especialidad) {
        ArrayList<Medico> resultado = new ArrayList<Medico>();
        resultado = medicoDAO.listarPorEspecialidad(especialidad);
        return resultado;
    }
    
    
    @WebMethod(operationName = "listarMedico")
    public ArrayList<Medico> listarMedico() {
        ArrayList<Medico> resultado = medicoDAO.listarTodos();
        return resultado;
    }
    /*@WebMethod(operationName = "listarMedicoFiltro")
    public List<Paciente> listarMedicoFiltro(String filtro) {
        List<Paciente> resultado = medicoDAO.listar(filtro);
        return resultado;
    }*/
            
    @WebMethod(operationName = "eliminarMedico")
    public int medicoEliminar(@WebParam(name = "medico") int idMedico) {
        int resultado = medicoDAO.eliminar(idMedico);
        return resultado;
    }
    
    @WebMethod(operationName = "modificarMedico")
    public int medicoModificar(@WebParam(name = "medico") Medico medico) {
        return medicoDAO.modificar(medico);
    }
    
    @WebMethod(operationName = "obtenerPorIDMedico")
    public Medico medicoObtenerID(@WebParam(name = "paciente") int idMedico) {
        Medico resultado = medicoDAO.obtenerPorId(idMedico);
        return resultado;
    }
    
    

    @WebMethod(operationName = "insertarMedico1")
    public int medicoInsertar1(@WebParam(name = "paciente") Medico medico) {
        int resultado = medicoDAO.insertarMedico1(medico);
        return resultado;
    }

    @WebMethod(operationName = "listarMedico1")
    public ArrayList<Medico> listarMedico1() {
        ArrayList<Medico> resultado = medicoDAO.listarTodos1();
        return resultado;
    }
    
    @WebMethod(operationName = "obtenerPorIDMedico1")
    public Medico medicoObtenerID1(@WebParam(name = "medico") int idMedico) {
        Medico resultado = medicoDAO.obtenerPorId1(idMedico);
        return resultado;
    }
    @WebMethod(operationName = "listarFiltroMedico")
    public List<Medico> listarFiltroMedico(String filtro)  {
        List<Medico> resultado = medicoDAO.listarFiltro(filtro);
        return resultado;
    }
    @WebMethod(operationName = "modificarMedicoV_2")
    public int medicoModificarV_2(@WebParam(name = "medico") Medico medico) {
        return medicoDAO.modificar_v2(medico);
    }
    
    
    private String getFileResource(String fileName){ 
        String filePath = MedicoWS.class.getResource("/pe/edu/pucp/resources/"+fileName).getPath();
        filePath = filePath.replace("%20", " ");
        return filePath;
    }
    
    @WebMethod(operationName = "reportePDF")
    public byte[] reportePDF() throws Exception {
        try {            
            Map<String, Object> params = new HashMap<>();  
            params.put("logo",ImageIO.read(new File(getFileResource("logo.png")))); 
            return generarBuffer(getFileResource("medicoHorizontal.jrxml"), params);                    
         } catch (Exception ex) {
            Logger.getLogger(MedicoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*
    Compila el xml,genera el reporte y lo retorna como un array de bytes
    */
    public byte[] generarBuffer(String inFileXML, Map<String, Object> params) throws Exception{
        //Se compila una sola vez
        String fileJasper = inFileXML +".jasper";
        if(!new File(fileJasper).exists()){
            //para compilar en GlassFish se requiere las librerias: jasperreports-jdt, ecj
            JasperCompileManager.compileReportToFile(inFileXML, fileJasper);         
        }
        //1- leer el archivo compilado
        JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(fileJasper);
        //2- poblar el reporte
        Connection conn = DBManager.getInstance().getConnection();
        JasperPrint jp = JasperFillManager.fillReport(jr,params, conn);          
        return JasperExportManager.exportReportToPdf(jp);
    }
    
    
    
   
    
}
