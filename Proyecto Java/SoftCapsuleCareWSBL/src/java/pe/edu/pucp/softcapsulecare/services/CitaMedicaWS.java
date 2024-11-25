package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import pe.edu.pucp.capsuleCare.medical.dao.CitaMedicaDAO;
import pe.edu.pucp.capsuleCare.medical.mysql.CitaMedicaMySQL;

import pe.edu.pucp.citamedica.model.consultas.CitaMedica;
import pe.edu.pucp.citamedica.model.consultas.EstadoCita;


import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.sql.Date;

import pe.edu.pucp.dbmanager.config.DBPoolManager;

/**
 *
 * @author diego
 */
@WebService(serviceName = "CitaMedicaWS")
public class CitaMedicaWS {
    private CitaMedicaDAO citaMedicaDAO;
    
    public CitaMedicaWS(){
        citaMedicaDAO = new CitaMedicaMySQL();
    }
    
    @WebMethod(operationName = "insertarCitaMedica")
    public int insertarCitaMedica(@WebParam(name = "cita") CitaMedica citaMedica) {
        return citaMedicaDAO.insertar(citaMedica);
    }
    
    @WebMethod(operationName = "modificarCitaMedica")
    public int modificarCitaMedica(@WebParam(name = "cita") CitaMedica citaMedica) {
        return citaMedicaDAO.modificar(citaMedica);
    }
    
    @WebMethod(operationName = "eliminarCitaMedica")
    public int eliminarCitaMedica(@WebParam(name = "cita") int idCitaMedica) {
        return citaMedicaDAO.eliminar(idCitaMedica);
    }

    @WebMethod(operationName = "listar")
    public ArrayList<CitaMedica> listar() {
        return citaMedicaDAO.listarTodos();
    }
    
    @WebMethod(operationName = "listarPorIdPaciente")
    public ArrayList<CitaMedica> listarPorIdPaciente(@WebParam(name = "idPaciente") int idPaciente) {
        return citaMedicaDAO.listarPorPaciente(idPaciente);
    }
    
    @WebMethod(operationName = "obtenerPorId")
    public CitaMedica obtenerPorId(@WebParam(name = "idPaciente") int idPaciente) {
        return citaMedicaDAO.obtenerPorId(idPaciente);
    }
    
    @WebMethod(operationName = "listarPorIdMedico")
    public ArrayList<CitaMedica> listarPorIdMedico(@WebParam(name = "idMedico") int idMedico) {
        return citaMedicaDAO.listarPorMedico(idMedico);
    }
    
    @WebMethod(operationName = "actualizarEstadoCitaMedica")
    public int actualizarEstadoCitaMedica(@WebParam(name = "cita") int idCitaMedica,
            @WebParam(name = "estadoCita") EstadoCita estado) {
        return citaMedicaDAO.actualizarEstadoCita(idCitaMedica,estado);
    }
    
     private String getFileResource(String fileName){
        String filePath = MedicoWS.class.getResource("/pe/edu/pucp/resources/"+fileName).getPath();
        filePath = filePath.replace("%20", " ");
        return filePath;
    }


//    @WebMethod(operationName = "reportePDF")
//    public byte[] reportePDF(@WebParam(name = "fechaIni") String fechaIni, 
//                         @WebParam(name = "fechaFin") String fechaFin) throws Exception {
//        try {
//            Map<String, Object> params = new HashMap<>();
//
//            params.put("logoSecundario",ImageIO.read(new File(getFileResource("logoSecundario.png"))));
//            
//            
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            java.util.Date fechaI = sdf.parse(fechaIni);
//            java.util.Date fechaF = sdf.parse(fechaFin);
//            
//            
//            java.sql.Date sqlFechaIni = new java.sql.Date(fechaI.getTime());
//            java.sql.Date sqlFechaFin = new java.sql.Date(fechaF.getTime());
//            
//            params.put("fechaIni", sqlFechaIni);
//            params.put("fechaFin", sqlFechaFin);
//            
//            return generarBuffer(getFileResource("citaMedicaReporte.jrxml"), params);
//         } catch (Exception ex) {
//            Logger.getLogger(MedicoWS.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

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
        Connection conn = DBPoolManager.getInstance().getConnection();
        JasperPrint jp = JasperFillManager.fillReport(jr,params, conn);
        return JasperExportManager.exportReportToPdf(jp);
    }
     
    @WebMethod(operationName = "reporteCitasProcedimientos")
    public byte[] citas_procedimientos_pdf() throws Exception {
        try {
            // Parámetros del reporte
            Map<String, Object> params = new HashMap<>();
            params.put("URL", ImageIO.read(new File(getFileResource("logo.png"))));

            // Generar el PDF y obtener los bytes
            return generarBufferSinCompilado("Citas-Procedimientos", params); // Usamos generarBuffer que retorna los bytes
        } catch (Exception ex) {
            Logger.getLogger(MedicoWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public byte[] generarBufferSinCompilado(String jrxmlFileName, Map<String, Object> params) throws Exception {
        // Definir la ruta del archivo compilado (.jasper)
        String fileJasper = getFileResource(jrxmlFileName + ".jasper");

        // Cargar siempre el archivo Jasper compilado
        JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(fileJasper);

        // Inicializar la conexión
        Connection conn = null;
        try {
            // Obtener la conexión
            conn = DBPoolManager.getInstance().getConnection();

            // Poblar el reporte con los parámetros
            JasperPrint jp = JasperFillManager.fillReport(jr, params, conn);

            // Exportar el reporte a un buffer de bytes (en formato PDF)
            return JasperExportManager.exportReportToPdf(jp);

        } finally {
            // Asegurarse de que la conexión se cierre
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace(); // Manejo de error opcional
                }
            }
        }
    }
}
