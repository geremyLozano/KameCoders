package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.citamedica.dao.HistorialMedicoDAO;
import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.citamedica.mysql.HistorialMedicoMySQL;

public class Principal_2 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        HistorialMedico historial = new HistorialMedico();
        historial.setFechaDeCreacion(sdf.parse("12-03-2019"));
        historial.setNumeroDocumentoIdentidadPaciente("44444");
        HistorialMedicoDAO hist = new HistorialMedicoMySQL();
        int resultado = hist.insertar(historial);
        if(resultado != 0)
            System.out.println("Ingreso correctamente");
        else
            System.out.println("Ocurrio un error al insertar");
//        int valor = hist.modificar(historial);
//        if(valor != 0)
//            System.out.println("Ingreso correctamente");
//        else
//            System.out.println("Ocurrio un error al insertar");
    }
}
