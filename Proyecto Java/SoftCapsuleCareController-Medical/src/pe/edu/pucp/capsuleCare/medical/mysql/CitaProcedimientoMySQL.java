/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.capsuleCare.medical.mysql;

import java.sql.*;
import java.util.ArrayList;
import pe.edu.pucp.capsuleCare.medical.dao.CitaProcedimientoDAO;
import pe.edu.pucp.citamedica.model.consultas.CitaMedicaProcedimiento;
import pe.edu.pucp.dbmanager.config.DBPoolManager;

public class CitaProcedimientoMySQL implements CitaProcedimientoDAO {

    private Connection con;
    private CallableStatement cst;
    private String sql;
    private ResultSet rs;
    @Override
    public int insertar(CitaMedicaProcedimiento cmp) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            // Procedimiento almacenado modificado
            sql = "{CALL CITA_PROCEDIMIENTO_Insertar(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros requeridos al procedimiento almacenado
            cst.setInt(1, cmp.getIdCitaMedica());
            cst.setInt(2, cmp.getIdProcedimiento());
            cst.setBoolean(3, cmp.getAsistencia());
            cst.setString(4, cmp.getObservaciones());

            if (cmp.getFechaResultado() == null) {
                cst.setNull(5, java.sql.Types.DATE);
            } else {
                cst.setDate(5, new java.sql.Date(cmp.getFechaResultado().getTime()));
            }

            if (cmp.getFechaRealizacion() == null) {
                cst.setNull(6, java.sql.Types.DATE);
            } else {
                cst.setDate(6, new java.sql.Date(cmp.getFechaRealizacion().getTime()));
            }

            if (cmp.getHoraRealizacion() == null || cmp.getHoraRealizacion().isEmpty()) {
                cst.setNull(7, java.sql.Types.TIME);
            } else {
                cst.setTime(7, java.sql.Time.valueOf(cmp.getHoraRealizacion()));
            }

            cst.setBoolean(8, cmp.getActivo());

            // Registrar el parámetro de salida
            cst.registerOutParameter(9, java.sql.Types.INTEGER);

            // Ejecutar el procedimiento almacenado
            cst.executeUpdate();

            // Obtener el valor del parámetro de salida
            resultado = cst.getInt(9);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resultado;
    }

    @Override
    public int modificar(int idCitaMedica, int idProcedimiento, int idPago, boolean asistencia, String observaciones, Date fechaResultado, Date fechaRealizacion, String horaRealizacion, boolean activo) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            // Llamar al procedimiento almacenado
            sql = "{CALL CITA_PROCEDIMIENTO_Actualizar(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros
            cst.setInt(1, idCitaMedica); // ID de la cita médica
            cst.setInt(2, idProcedimiento); // ID del procedimiento
            cst.setBoolean(3, asistencia); // Asistencia
            cst.setString(4, observaciones); // Observaciones
            cst.setDate(5, fechaResultado != null ? new java.sql.Date(fechaResultado.getTime()) : null); // Fecha de resultado
            cst.setDate(6, fechaRealizacion != null ? new java.sql.Date(fechaRealizacion.getTime()) : null); // Fecha de realización
            cst.setTime(7, horaRealizacion != null && !horaRealizacion.isEmpty() ? java.sql.Time.valueOf(horaRealizacion) : null); // Hora de realización
            cst.setBoolean(8, activo); // Activo (1: activo, 0: inactivo)

            // Registrar el parámetro de salida
            cst.registerOutParameter(9, java.sql.Types.INTEGER);

            // Ejecutar el procedimiento
            cst.executeUpdate();

            // Obtener el valor del parámetro de salida
            resultado = cst.getInt(9);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }


    @Override
    public int eliminar(int idCitaMedica, int idProcedimiento) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            // Llamar al procedimiento almacenado para eliminación lógica
            sql = "{CALL CITA_PROCEDIMIENTO_EliminarLogico(?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros
            cst.setInt(1, idCitaMedica);
            cst.setInt(2, idProcedimiento);

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
    
    @Override
    public ArrayList<CitaMedicaProcedimiento> listarPorCitaMedica(int idCitaMedica) {
        ArrayList<CitaMedicaProcedimiento> lista = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                System.out.println("Conexión no disponible");
                return lista;
            }

            // Llamar al procedimiento almacenado
            sql = "{CALL CITA_PROCEDIMIENTO_ListarPorCitaMedica(?)}";
            cst = con.prepareCall(sql);

            // Pasar el parámetro
            System.out.println("Parámetro enviado: " + idCitaMedica);
            cst.setInt(1, idCitaMedica);

            // Ejecutar y obtener resultados
            System.out.println("Ejecutando procedimiento: " + sql);
            rs = cst.executeQuery();

            // Verificar si hay resultados
            if (!rs.isBeforeFirst()) {
                System.out.println("El ResultSet está vacío.");
            }

            // Obtener metadatos de las columnas
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Procesar resultados
            while (rs.next()) {
                System.out.println("Fila encontrada:");
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    System.out.println("Columna: " + columnName + " -> Valor: " + value);
                }

                // Mapeo del objeto CitaMedicaProcedimiento
                CitaMedicaProcedimiento cmp = new CitaMedicaProcedimiento();
                cmp.setIdCitaMedica(rs.getInt("idCitaMedica"));
                cmp.setIdProcedimiento(rs.getInt("idProcedimiento"));

                // Manejar posibles nulos para idPago
                int idPago = rs.getInt("idPago");
                cmp.setIdPago(rs.wasNull() ? -1 : idPago);

                // Manejar posibles nulos para fechaResultado
                Date fechaResultado = rs.getDate("fechaResultado");
                cmp.setFechaResultado(fechaResultado);

                cmp.setAsistencia(rs.getBoolean("asistencia"));
                cmp.setObservaciones(rs.getString("observaciones"));
                cmp.setActivo(rs.getBoolean("activo"));

                lista.add(cmp);
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
        return lista;
    }



    @Override
    public CitaMedicaProcedimiento obtenerPorIds(int idCitaMedica, int idProcedimiento) {
        CitaMedicaProcedimiento cmp = null;
        try {
            con = DBPoolManager.getInstance().getConnection();
            // Llamar al procedimiento almacenado
            sql = "{CALL CITA_PROCEDIMIENTO_ObtenerPorIds(?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros
            cst.setInt(1, idCitaMedica);
            cst.setInt(2, idProcedimiento);

            // Ejecutar y obtener resultados
            rs = cst.executeQuery();

            if (rs.next()) {
                cmp = new CitaMedicaProcedimiento();
                cmp.setIdCitaMedica(rs.getInt("idCitaMedica"));
                cmp.setIdProcedimiento(rs.getInt("idProcedimiento"));

                // Manejo de valores nulos en idPago
                int idPago = rs.getInt("idPago");
                cmp.setIdPago(rs.wasNull() ? -1 : idPago);

                cmp.setAsistencia(rs.getBoolean("asistencia"));
                cmp.setObservaciones(rs.getString("observaciones"));

                // Manejo de valores nulos en fechaResultado
                Date fechaResultado = rs.getDate("fechaResultado");
                cmp.setFechaResultado(fechaResultado == null ? null : fechaResultado);

                cmp.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return cmp;
    }

    @Override
    public ArrayList<CitaMedicaProcedimiento> listarTodos() {
        ArrayList<CitaMedicaProcedimiento> lista = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                System.out.println("Conexión no disponible");
                return lista;
            }

            // Llamar al procedimiento almacenado que lista todas las entradas
            sql = "{CALL CITA_PROCEDIMIENTO_ListarTodos()}";
            cst = con.prepareCall(sql);

            // Ejecutar y obtener resultados
            rs = cst.executeQuery();

            // Procesar resultados
            while (rs.next()) {
                System.out.println("idPago: " + rs.getInt("idPago"));
                System.out.println("idProcedimiento: " + rs.getInt("idProcedimiento"));

                CitaMedicaProcedimiento cmp = new CitaMedicaProcedimiento();
                cmp.setIdCitaMedica(rs.getInt("idCitaMedica"));
                cmp.setIdProcedimiento(rs.getInt("idProcedimiento"));

                // Recuperar correctamente el idPago
                int idPago = rs.getInt("idPago");
                cmp.setIdPago(rs.getInt("idPago")); // Asegúrate de manejar valores nulos

                cmp.setAsistencia(rs.getBoolean("asistencia"));
                cmp.setObservaciones(rs.getString("observaciones"));

                // Manejo correcto de fechaResultado
                Date fechaResultado = rs.getDate("fechaResultado");
                cmp.setFechaResultado(fechaResultado == null ? null : fechaResultado);

                cmp.setActivo(rs.getBoolean("activo"));

                lista.add(cmp);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return lista;
    }
    @Override
    public ArrayList<CitaMedicaProcedimiento> listarFiltrado(
            Integer idCitaMedica,
            Integer idProcedimiento,
            Integer estadoPago,
            Date fechaInicio,
            Date fechaFin,
            String horaInicio,
            String horaFin) {
        ArrayList<CitaMedicaProcedimiento> lista = new ArrayList<>();
        try {
            // Obtener la conexión desde el pool
            con = DBPoolManager.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                System.out.println("Conexión no disponible");
                return lista;
            }

            // Llamar al procedimiento almacenado con los nuevos filtros
            sql = "{CALL CITA_PROCEDIMIENTO_FILTRADOS(?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Manejo de parámetros opcionales con valores predeterminados
            cst.setInt(1, idCitaMedica != null ? idCitaMedica : -1); // -1 para "Todos"
            cst.setInt(2, idProcedimiento != null ? idProcedimiento : -1); // -1 para "Todos"
            cst.setInt(3, estadoPago != null ? estadoPago : -1); // -1 para "Todos", 1 para pagado, 0 para pendiente

            // Manejar las fechas de inicio y fin
            if (fechaInicio == null) {
                cst.setNull(4, java.sql.Types.DATE);
            } else {
                cst.setDate(4, new java.sql.Date(fechaInicio.getTime()));
            }

            if (fechaFin == null) {
                cst.setNull(5, java.sql.Types.DATE);
            } else {
                cst.setDate(5, new java.sql.Date(fechaFin.getTime()));
            }

            // Manejar las horas de inicio y fin
            if (horaInicio == null || horaInicio.isEmpty()) {
                cst.setNull(6, java.sql.Types.TIME);
            } else {
                cst.setTime(6, java.sql.Time.valueOf(horaInicio));
            }

            if (horaFin == null || horaFin.isEmpty()) {
                cst.setNull(7, java.sql.Types.TIME);
            } else {
                cst.setTime(7, java.sql.Time.valueOf(horaFin));
            }

            // Ejecutar el procedimiento almacenado
            rs = cst.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                // Crear una nueva instancia de CitaMedicaProcedimiento
                CitaMedicaProcedimiento cmp = new CitaMedicaProcedimiento();

                // Asignar valores desde el ResultSet
                cmp.setIdCitaMedica(rs.getInt("idCitaMedica"));
                cmp.setIdProcedimiento(rs.getInt("idProcedimiento"));

                // Manejar idPago, considerando nulos
                int idPago = rs.getInt("idPago");
                cmp.setIdPago(rs.wasNull() ? -1 : idPago);

                // Asignar los valores restantes
                cmp.setAsistencia(rs.getBoolean("asistencia"));
                cmp.setObservaciones(rs.getString("observaciones"));

                // Manejo de fechas y horas, considerando nulos
                cmp.setFechaResultado(rs.getDate("fechaResultado"));
                cmp.setFechaRealizacion(rs.getDate("fechaRealizacion"));

                Time horaRealizacion = rs.getTime("horaRealizacion");
                cmp.setHoraRealizacion(horaRealizacion != null ? horaRealizacion.toString() : "");

                cmp.setActivo(rs.getBoolean("activo"));

                // Agregar el objeto a la lista
                lista.add(cmp);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Liberar recursos en el bloque finally
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return lista;
    }
    
    @Override
    public ArrayList<String> listarHorasDisponibles(Date fecha) {
        ArrayList<String> horasDisponibles = new ArrayList<>();
        try {
            // Obtener la conexión desde el pool
            con = DBPoolManager.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                System.out.println("Conexión no disponible.");
                return horasDisponibles;
            }

            // Llamar al procedimiento almacenado
            String sql = "{CALL CITA_PROCEDIMIENTO_horasDisponibles(?)}";
            cst = con.prepareCall(sql);

            // Pasar el parámetro de fecha
            cst.setDate(1, new java.sql.Date(fecha.getTime()));

            // Ejecutar el procedimiento
            rs = cst.executeQuery();

            // Procesar las horas disponibles
            while (rs.next()) {
                String hora = rs.getString("hora");
                horasDisponibles.add(hora);
            }

        } catch (SQLException ex) {
            System.out.println("Error en listarHorasDisponibles: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cst != null) cst.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return horasDisponibles;
    }
    
    @Override
    public ArrayList<CitaMedicaProcedimiento> listarFiltrado2(
            String dniPaciente, Integer idCitaMedica, Integer idProcedimiento,
            Integer estadoPago, Date fechaInicio, Date fechaFin,
            String horaInicio, String horaFin
    ) {
        ArrayList<CitaMedicaProcedimiento> lista = new ArrayList<>();
        try {
            con = DBPoolManager.getInstance().getConnection();

            // Procedimiento almacenado
            sql = "{CALL CITA_PROCEDIMIENTO_FILTRADOS_COMPLETO(?, ?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar parámetros al procedimiento almacenado
            cst.setString(1, dniPaciente);
            cst.setInt(2, idCitaMedica != null ? idCitaMedica : -1);
            cst.setInt(3, idProcedimiento != null ? idProcedimiento : -1);
            cst.setInt(4, estadoPago != null ? estadoPago : -1);

            // Manejar las fechas de inicio y fin
            if (fechaInicio != null) {
                cst.setDate(5, new java.sql.Date(fechaInicio.getTime()));
            } else {
                cst.setNull(5, java.sql.Types.DATE);
            }

            if (fechaFin != null) {
                cst.setDate(6, new java.sql.Date(fechaFin.getTime()));
            } else {
                cst.setNull(6, java.sql.Types.DATE);
            }

            // Manejar las horas de inicio y fin
            if (horaInicio != null && !horaInicio.isEmpty()) {
                cst.setTime(7, java.sql.Time.valueOf(horaInicio));
            } else {
                cst.setNull(7, java.sql.Types.TIME);
            }

            if (horaFin != null && !horaFin.isEmpty()) {
                cst.setTime(8, java.sql.Time.valueOf(horaFin));
            } else {
                cst.setNull(8, java.sql.Types.TIME);
            }

            // Ejecutar el procedimiento almacenado
            rs = cst.executeQuery();

            // Procesar el resultado
            while (rs.next()) {
                CitaMedicaProcedimiento cmp = new CitaMedicaProcedimiento();
                cmp.setIdCitaMedica(rs.getInt("idCitaMedica"));
                cmp.setIdProcedimiento(rs.getInt("idProcedimiento"));
                cmp.setIdPago(rs.getObject("idPago") != null ? rs.getInt("idPago") : null);
                cmp.setAsistencia(rs.getBoolean("asistencia"));
                cmp.setObservaciones(rs.getString("observaciones"));
                cmp.setFechaResultado(rs.getDate("fechaResultado"));
                cmp.setFechaRealizacion(rs.getDate("fechaRealizacion"));
                cmp.setHoraRealizacion(rs.getString("horaRealizacion"));
                cmp.setActivo(rs.getBoolean("activo"));
                lista.add(cmp);
            }
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return lista;
    }


   

}
