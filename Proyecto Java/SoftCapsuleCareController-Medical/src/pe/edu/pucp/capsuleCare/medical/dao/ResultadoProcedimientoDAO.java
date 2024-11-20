package pe.edu.pucp.capsuleCare.medical.dao;

import java.util.ArrayList;
import pe.edu.pucp.citamedica.model.procedimiento.ResultadoProcedimiento;


public interface ResultadoProcedimientoDAO {
    int insertar(ResultadoProcedimiento resultado);
    int modificar(ResultadoProcedimiento resultado);
    int eliminar(int idResultadoProcedimiento);
    ArrayList<ResultadoProcedimiento> listarTodos();
    ResultadoProcedimiento obtenerPorId(int idResultadoProcedimiento);
}
