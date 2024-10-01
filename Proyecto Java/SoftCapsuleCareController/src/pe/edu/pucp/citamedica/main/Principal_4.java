package pe.edu.pucp.citamedica.main;

import pe.edu.pucp.citamedica.dao.PagoDAO;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;
import pe.edu.pucp.citamedica.mysql.PagoMySQL;

public class Principal_4 {
    public static void main(String[] args) {
        PagoDAO pag = new PagoMySQL();
        Pago pago = new Pago();
        pago.setConcepto("Rayos X");
        pago.setDescuentoPorSeguro(40);
        pago.setMontoParcial(100);
        pago.setMontoTotal(60);
        pago.setIdPaciente(20);
        int resultado = pag.insertar(pago);
        if(resultado>0)
            System.out.println("Pago creado correctamente");
        else
            System.out.println("Error al crear el pago");
    }
}
