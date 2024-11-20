package pe.edu.pucp.softcapsulecare.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.capsuleCare.medical.dao.ComunicacionDAO;
import pe.edu.pucp.capsuleCare.medical.mysql.ComunicacionMySQL;

import pe.edu.pucp.citamedica.model.comunicacion.Comunicacion;
import pe.edu.pucp.citamedica.model.comunicacion.TipoComunicacion;
import pe.edu.pucp.citamedica.model.comunicacion.EstadoComunicacion;




@WebService(serviceName = "ComunicacionWS")
public class ComunicacionWS {

    private final ComunicacionDAO comunicacionDAO;

    public ComunicacionWS() {
        this.comunicacionDAO = new ComunicacionMySQL();
    }
    

    @WebMethod(operationName = "insertarComunicacion")
    public int insertarComunicacion(
            @WebParam(name = "tipo") String tipo,
            @WebParam(name = "contenido") String contenido,
            @WebParam(name = "fechaComunicacion") java.util.Date fechaComunicacion,
            @WebParam(name = "idPaciente") int idPaciente) {

        try {
            TipoComunicacion tipoSeleccionado;

            // Convertir el string en el enum correspondiente
            switch (tipo.toLowerCase()) {
                case "queja":
                    tipoSeleccionado = TipoComunicacion.Queja;
                    break;
                case "sugerencia":
                    tipoSeleccionado = TipoComunicacion.Sugerencia;
                    break;
                default:
                    System.err.println("Error: Tipo de comunicación no válido - " + tipo);
                    return 0;
            }

            // Crear la comunicación
            Comunicacion comunicacion = new Comunicacion();
            comunicacion.setTipo(tipoSeleccionado);
            comunicacion.setContenido(contenido);
            comunicacion.setFechaComunicacion(fechaComunicacion);
            comunicacion.setIdPaciente(idPaciente);
            comunicacion.setEstado(EstadoComunicacion.RECIBIDA);
            comunicacion.setRespuesta("RESPUESTA PENDIENTE");

            return comunicacionDAO.insertar(comunicacion);

        } catch (Exception e) {
            System.err.println("Error al insertar comunicación: " + e.getMessage());
            return 0;
        }
    }




    // Método para modificar el estado y la respuesta de una comunicación
    @WebMethod(operationName = "modificarComunicacion")
    public int modificarComunicacion(@WebParam(name = "idComunicacion") int idComunicacion,
                                     @WebParam(name = "estado") String nuevoEstado,
                                     @WebParam(name = "respuesta") String respuesta) {
        try {
            Comunicacion comunicacion = comunicacionDAO.obtenerPorId(idComunicacion);
            if (comunicacion != null) {
                comunicacion.setEstado(EstadoComunicacion.valueOf(nuevoEstado.toUpperCase()));
                comunicacion.setRespuesta(respuesta);
                return comunicacionDAO.modificar(comunicacion);
            }
        } catch (Exception e) {
            System.err.println("Error al modificar comunicación: " + e.getMessage());
        }
        return 0;
    }

    // Método para eliminar una comunicación por ID
    @WebMethod(operationName = "eliminarComunicacion")
    public int eliminarComunicacion(@WebParam(name = "idComunicacion") int idComunicacion) {
        try {
            return comunicacionDAO.eliminar(idComunicacion);
        } catch (Exception e) {
            System.err.println("Error al eliminar comunicación: " + e.getMessage());
            return 0;
        }
    }

    
    @WebMethod(operationName = "listarComunicacionesPaciente")
    public ArrayList<Comunicacion> listarComunicacionesPaciente(@WebParam(name = "idPaciente") int idPaciente) {
        try {
            System.out.println("Invocación recibida para idPaciente: " + idPaciente);
            ArrayList<Comunicacion> resultado = comunicacionDAO.listarPorPaciente(idPaciente);
            System.out.println("Número de comunicaciones encontradas: " + resultado.size());
            return resultado;
        } catch (Exception e) {
            System.err.println("Error en listarComunicacionesPaciente: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    // Método para listar comunicaciones por estado
    @WebMethod(operationName = "listarComunicacionesPorEstado")
    public ArrayList<Comunicacion> listarComunicacionesPorEstado(@WebParam(name = "estado") String estado) {
        try {
            ArrayList<Comunicacion> todas = comunicacionDAO.listarTodos();
            ArrayList<Comunicacion> filtradas = new ArrayList<>();
            for (Comunicacion com : todas) {
                if (com.getEstado().name().equalsIgnoreCase(estado)) {
                    filtradas.add(com);
                }
            }
            return filtradas;
        } catch (Exception e) {
            System.err.println("Error al listar comunicaciones por estado: " + e.getMessage());
            return new ArrayList<>();
        }
        
        
    }
    
    @WebMethod(operationName = "listarComunicacionesFiltradas")
    public ArrayList<Comunicacion> listarComunicacionesFiltradas(
        @WebParam(name = "tipo") String tipo,
        @WebParam(name = "estado") String estado,
        @WebParam(name = "fechaInicio") Date fechaInicio,
        @WebParam(name = "fechaFin") Date fechaFin,
        @WebParam(name = "idPaciente") Integer idPaciente) {

        // Llama al método del DAO para obtener la lista filtrada
        return comunicacionDAO.listarComunicacionesFiltradas(tipo, estado, fechaInicio, fechaFin, idPaciente);
    }
  
    @WebMethod(operationName = "obtenerConteoPorEstado")
    public ArrayList<Integer> obtenerConteoPorEstado() {
        ArrayList<Integer> conteoPorEstado = new ArrayList<>();
        // Inicializar el conteo para los 4 estados
        for (int i = 0; i < 4; i++) {
            conteoPorEstado.add(0);
        }

        try {
            // Llamar al método del DAO para obtener el conteo por estado
            ArrayList<String[]> conteoList = comunicacionDAO.obtenerConteoPorEstado();

            // Procesar el resultado y asignar al ArrayList
            for (String[] conteo : conteoList) {
                String estado = conteo[0];
                int cantidad = Integer.parseInt(conteo[1]);

                // Asignar el conteo al índice correspondiente según el estado
                switch (estado.toUpperCase()) {
                    case "RECIBIDA":
                        conteoPorEstado.set(0, cantidad);
                        break;
                    case "PENDIENTE":
                        conteoPorEstado.set(1, cantidad);
                        break;
                    case "RESPONDIDA":
                        conteoPorEstado.set(2, cantidad);
                        break;
                    case "FINALIZADA":
                        conteoPorEstado.set(3, cantidad);
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el conteo por estado: " + e.getMessage());
        }

        return conteoPorEstado;
    }
    
    @WebMethod(operationName = "listarTodasLasComunicaciones")
    public ArrayList<Comunicacion> listarTodasLasComunicaciones() {
        try {
            // Mensaje de depuración para indicar que el método fue invocado
            System.out.println("Invocación recibida para listar todas las comunicaciones.");

            // Llamar al método del DAO para obtener todas las comunicaciones
            ArrayList<Comunicacion> resultado = comunicacionDAO.listarTodos();

            // Mostrar cuántas comunicaciones se encontraron
            System.out.println("Número de comunicaciones encontradas: " + resultado.size());

            return resultado;
        } catch (Exception e) {
            System.err.println("Error en listarTodasLasComunicaciones: " + e.getMessage());
            e.printStackTrace();

            // En caso de error, devolver una lista vacía
            return new ArrayList<>();
        }
    }
    
    @WebMethod(operationName = "obtenerComunicacionPorId")
    public Comunicacion obtenerComunicacionPorId(@WebParam(name = "idComunicacion") int idComunicacion) {
        try {
            System.out.println("Invocación para obtener comunicación con ID: " + idComunicacion);
            Comunicacion comunicacion = comunicacionDAO.obtenerPorId(idComunicacion);
            if (comunicacion != null) {
                System.out.println("Comunicación encontrada: " + comunicacion.getContenido());
            } else {
                System.out.println("No se encontró la comunicación con ID: " + idComunicacion);
            }
            return comunicacion;
        } catch (Exception e) {
            System.err.println("Error al obtener la comunicación por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    
    
}
