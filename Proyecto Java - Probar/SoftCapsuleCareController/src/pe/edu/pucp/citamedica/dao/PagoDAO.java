package pe.edu.pucp.citamedica.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.procedimiento.Pago;

public interface PagoDAO {
    int insertar(Pago pago);
//    int modificar(Pago pago);
    int eliminar(int idPago);
    ArrayList<Pago> listarTodos();
    Pago obtenerPorId(int idPago);
}
