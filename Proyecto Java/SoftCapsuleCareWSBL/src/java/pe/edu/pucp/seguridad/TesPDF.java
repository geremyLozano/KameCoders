package pe.edu.pucp.seguridad;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class TesPDF {

    // Método para obtener la ruta del recurso
    private String getFileResource(String fileName){ 
        String filePath = TesPDF.class.getResource("/pe/edu/pucp/resources/"+fileName).getPath();
        filePath = filePath.replace("%20", " "); // Reemplaza el espacio codificado con "%20" por espacio normal
        return filePath;
    }
    
    public String getResourceDirectory() {
        // Obtener la ruta absoluta del directorio de resources
        String resourcePath = TesPDF.class.getResource("/pe/edu/pucp/resources/").getPath();
        return resourcePath.replace("%20", " "); // Reemplazar espacios codificados
    }

    public static void main(String[] args) {
        try {
            TesPDF tesPDF = new TesPDF();
            tesPDF.reportePDF(); // Llamar al método para generar el reporte
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportePDF() throws Exception {
        try {
            // Parámetros del reporte
            Map<String, Object> params = new HashMap<>();
            params.put("URL", ImageIO.read(new File(getFileResource("logo.png")))); // Logo en el reporte
            
            // Generar el archivo PDF
            byte[] pdfBytes = generarBuffer("Citas-Procedimientos", params);
            
            // Ruta donde se guardará el archivo PDF (en /pe/edu/pucp/resources)
            String outputFilePath = getResourceDirectory() + "reporte.pdf";
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(pdfBytes);
            }
            
            System.out.println("PDF generado correctamente en: " + outputFilePath);
        } catch (Exception ex) {
            Logger.getLogger(TesPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Compila el xml, genera el reporte y lo retorna como un array de bytes
     */
    public byte[] generarBuffer(String jrxmlFileName, Map<String, Object> params) throws Exception {
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
