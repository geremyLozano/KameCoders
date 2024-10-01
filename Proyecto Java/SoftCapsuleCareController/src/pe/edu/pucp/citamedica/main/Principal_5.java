package pe.edu.pucp.citamedica.main;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.HistorialMedicoDAO;
import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.citamedica.mysql.HistorialMedicoMySQL;

public class Principal_5 {
    public static void main(String[] args) {
        HistorialMedicoDAO hist = new HistorialMedicoMySQL();
//        HistorialMedico med = new HistorialMedico();
//        med.setIdPaciente(17);
//        System.out.println(hist.insertar(med)>0 ? "Historial medico creado":"Error con la creacion");
//        System.out.println(hist.eliminar(2)!=0 ? "Historial medico eliminado":"Error con la eliminacion");
//        ArrayList<HistorialMedico>hst=hist.listarTodos();
//        for(HistorialMedico h:hst){
//            System.out.println(h.toString());
//        }
        HistorialMedico h = hist.obtenerPorId(2);
        System.out.println(h.toString());
    }
}
