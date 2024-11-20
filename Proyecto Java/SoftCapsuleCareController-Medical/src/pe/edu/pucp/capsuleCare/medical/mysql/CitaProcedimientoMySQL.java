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
            sql = "{CALL CITA_PROCEDIMIENTO_Insertar(?, ?, ?, ?, ?, ?)}";
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
            cst.setBoolean(6, cmp.getActivo());

            resultado = cst.executeUpdate();
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
    public int modificar(int idCitaMedica, int idProcedimiento, int idPago, boolean asistencia, String observaciones, Date fechaResultado, boolean activo) {
        int resultado = 0;
        try {
            con = DBPoolManager.getInstance().getConnection();
            // Llamar al procedimiento almacenado
            sql = "{CALL CITA_PROCEDIMIENTO_Modificar2(?, ?, ?, ?, ?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros
            cst.setInt(1, idCitaMedica); // ID de la cita médica
            cst.setInt(2, idProcedimiento); // ID del procedimiento
            cst.setInt(3, idPago); // ID del pago
            cst.setBoolean(4, asistencia); // Asistencia
            cst.setString(5, observaciones); // Observaciones
            cst.setDate(6, fechaResultado != null ? new java.sql.Date(fechaResultado.getTime()) : null); // Fecha de resultado
            cst.setBoolean(7, activo); // Activo (1: activo, 0: inactivo)

            // Ejecutar el procedimiento
            resultado = cst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
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
    public ArrayList<CitaMedicaProcedimiento> listarFiltrado(int idCitaMedica, int idProcedimiento, int estadoPago) {
        ArrayList<CitaMedicaProcedimiento> lista = new ArrayList<>();
        try {
            // Obtener la conexión desde el pool
            con = DBPoolManager.getInstance().getConnection();
            if (con == null || con.isClosed()) {
                System.out.println("Conexión no disponible");
                return lista;
            }

            // Llamar al procedimiento almacenado que aplica los filtros
            sql = "{CALL CITA_PROCEDIMIENTO_FILTRADOS(?, ?, ?)}";
            cst = con.prepareCall(sql);

            // Pasar los parámetros de filtrado al procedimiento
            cst.setInt(1, idCitaMedica); // -1 para "Todos"
            cst.setInt(2, idProcedimiento); // -1 para "Todos"
            cst.setInt(3, estadoPago); // -1 para "Todos", 1 para pagado, 0 para pendiente

            // Ejecutar el procedimiento almacenado
            rs = cst.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                // Crear una nueva instancia de CitaMedicaProcedimiento
                CitaMedicaProcedimiento cmp = new CitaMedicaProcedimiento();

                // Asignar valores desde el ResultSet
                cmp.setIdCitaMedica(rs.getInt("idCitaMedica"));
                cmp.setIdProcedimiento(rs.getInt("idProcedimiento"));

                // Manejar correctamente idPago, considerando nulos
                int idPago = rs.getInt("idPago");
            cmp.setIdPago(rs.wasNull() ? -1 : idPago); // Si es nulo, asignar -1

            // Asignar los valores restantes
            cmp.setAsistencia(rs.getBoolean("asistencia"));
            cmp.setObservaciones(rs.getString("observaciones"));

            // Manejo de la fecha, considerando nulos
            Date fechaResultado = rs.getDate("fechaResultado");
            cmp.setFechaResultado(fechaResultado != null ? fechaResultado : null);

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

    




}
