package pe.edu.pucp.citamedica.program.main;

import pe.edu.pucp.citamedica.dao.PacienteDao;
import pe.edu.pucp.citamedica.mysql.PacienteMySQL;
import pe.edu.pucp.citamedica.paciente.model.Paciente;



public class Principal {
    public static void main(String[] args) {
        Paciente pac = new Paciente();
        pac.setDNI(32432);
        pac.setNombre("Jack");
        PacienteDao paciente = new PacienteMySQL();
        int resultado = paciente.insertar(pac);
        if(resultado!=-1)
            System.out.println("Se insertó correctamente");
        else
            System.out.println("Ocurrió un error");
    }
}
