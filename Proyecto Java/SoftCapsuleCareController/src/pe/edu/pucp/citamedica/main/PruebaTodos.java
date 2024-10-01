package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import pe.edu.pucp.citamedica.dao.ComunicacionDAO;
import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;
import pe.edu.pucp.citamedica.model.comunicacion.TipoComunicacion;
import pe.edu.pucp.citamedica.mysql.ComunicacionMySQL;


public class PruebaTodos {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        ComunicacionDAO comu = new ComunicacionMySQL();
        Comunicacion c = new Comunicacion();
        c.setTipo(TipoComunicacion.Queja);
        c.setContenido("prueba");
        c.setFechaComunicacion(sdf.parse("12-12-2012"));
        c.setIdPaciente(20);
        System.out.println(comu.insertar(c)!=0 ? "Comunicacion insertada":"Error al registrar");
    }
}
