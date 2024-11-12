package pe.edu.pucp.citamedica.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pe.edu.pucp.citamedica.dao.PagoDAO;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;
import pe.edu.pucp.citamedica.mysql.PagoMySQL;

public class Principal_4 {
    public static void main(String[] args) {
        PagoDAO pag = new PagoMySQL();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        Pago pago = new Pago();
//        pago.setConcepto("Rayos X");
//        pago.setDescuentoPorSeguro(40);
//        pago.setMontoParcial(100);
//        pago.setMontoTotal(60);
//        pago.setIdPaciente(20);
//        int resultado = pag.insertar(pago);
//        System.out.println("Fecha: "+pago.getFechaPago());
//        if(resultado>0)
//            System.out.println("Pago creado correctamente");
//        else
//            System.out.println("Error al crear el pago");
//        int eliminado = pag.eliminar(3);
//        System.out.println(eliminado>0 ? "Pago eliminado":"Error al eliminar");

//        ArrayList<Pago>pagos=pag.listarTodos();
//        for(Pago p:pagos){
//            String fecha = sdf.format(p.getFechaPago());
//            System.out.println("idPago:"+p.getIdPago()+"   descuento:"+p.getDescuentoPorSeguro()+
//                    "   montoParcial:"+p.getMontoParcial()+"     total:"+p.getMontoTotal()+
//                    "   fecha:"+fecha+"    concepto:"+p.getConcepto()+"  idPaciente:"+p.getIdPaciente());
//        }
        Pago p = pag.obtenerPorId(2);
        String fecha = sdf.format(p.getFechaPago());
        System.out.println("idPago:"+p.getIdPago()+"   descuento:"+p.getDescuentoPorSeguro()+
                    "   montoParcial:"+p.getMontoParcial()+"     total:"+p.getMontoTotal()+
                    "   fecha:"+fecha+"    concepto:"+p.getConcepto()+"  idPaciente:"+p.getIdPaciente());
    }
}
