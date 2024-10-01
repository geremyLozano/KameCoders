package pe.edu.pucp.citamedica.main;

import pe.edu.pucp.citamedica.dao.HistorialMedicoDAO;
import pe.edu.pucp.citamedica.model.consultas.HistorialMedico;
import pe.edu.pucp.citamedica.mysql.HistorialMedicoMySQL;

public class Principal_5 {
    public static void main(String[] args) {
        HistorialMedicoDAO hist = new HistorialMedicoMySQL();
        HistorialMedico med = new HistorialMedico();
        med.setIdPaciente(17);
        System.out.println(hist.insertar(med)>0 ? "Historial medico creado":"Error con la creacion");
    }
}
