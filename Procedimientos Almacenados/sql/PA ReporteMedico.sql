-- Archivo: sp_insertar_reporte_medico.sql

DELIMITER //

CREATE PROCEDURE sp_insertar_reporte_medico(
    IN p_diagnostico VARCHAR(500),
    IN p_tratamiento VARCHAR(500),
    IN p_enfermedad VARCHAR(100),
    IN p_fecha DATE,
    IN p_idCitaMedica INT  -- Agregar el parámetro para la cita médica
)
BEGIN
    -- Verificar si la cita médica existe y está activa
    IF NOT EXISTS (SELECT 1 FROM CitaMedica WHERE idCitaMedica = p_idCitaMedica AND activo = 1) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cita médica no existe o no está activa';
    ELSE
        -- Verificar si el reporte médico ya existe
        IF EXISTS (SELECT 1 FROM ReporteMedico 
                   WHERE diagnostico = p_diagnostico 
                   AND enfermedad = p_enfermedad 
                   AND fecha = p_fecha) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El reporte médico ya existe para esa fecha y diagnóstico';
        ELSE
            -- Insertar el nuevo reporte médico y vincularlo a la cita médica
            INSERT INTO ReporteMedico (diagnostico, tratamiento, enfermedad, fecha, activo, CitaMedica_idCitaMedica)
            VALUES (p_diagnostico, p_tratamiento, p_enfermedad, p_fecha, 1, p_idCitaMedica);
        END IF;
    END IF;
END //

DELIMITER ;



-- Archivo: sp_modificar_reporte_medico.sql

DELIMITER //

CREATE PROCEDURE sp_modificar_reporte_medico(
    IN p_idReporteMedico INT,
    IN p_diagnostico VARCHAR(500),
    IN p_tratamiento VARCHAR(500),
    IN p_enfermedad VARCHAR(100),
    IN p_fecha DATE,
    IN p_idCitaMedica INT -- Nuevo parámetro para ID de Cita Médica
)
BEGIN
    -- Verificar si el reporte médico existe
    IF NOT EXISTS (SELECT 1 FROM ReporteMedico WHERE idReporteMedico = p_idReporteMedico) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El reporte médico no existe';
    ELSE
        -- Verificar si ya existe otro reporte médico con los mismos datos para evitar duplicados
        IF EXISTS (SELECT 1 FROM ReporteMedico 
                   WHERE diagnostico = p_diagnostico 
                   AND tratamiento = p_tratamiento 
                   AND enfermedad = p_enfermedad 
                   AND fecha = p_fecha 
                   AND idReporteMedico != p_idReporteMedico) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Ya existe otro reporte médico con el mismo diagnóstico, tratamiento, enfermedad y fecha';
        ELSE
            -- Actualizar el reporte médico
            UPDATE ReporteMedico
            SET diagnostico = p_diagnostico,
                tratamiento = p_tratamiento,
                enfermedad = p_enfermedad,
                fecha = p_fecha,
                CitaMedica_idCitaMedica = p_idCitaMedica -- Actualizar ID de Cita Médica
            WHERE idReporteMedico = p_idReporteMedico;
        END IF;
    END IF;
END //

DELIMITER ;


-- Archivo: sp_eliminar_logico_reporte_medico.sql

DELIMITER //

CREATE PROCEDURE sp_eliminar_logico_reporte_medico(
    IN p_idReporteMedico INT
)
BEGIN
    -- Verificar si el reporte médico existe
    IF NOT EXISTS (SELECT 1 FROM ReporteMedico WHERE idReporteMedico = p_idReporteMedico) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El reporte médico no existe';
    ELSE
        -- Verificar si el reporte ya está inactivo (ya eliminado lógicamente)
        IF (SELECT activo FROM ReporteMedico WHERE idReporteMedico = p_idReporteMedico) = 0 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El reporte médico ya está inactivo';
        ELSE
            -- Realizar la eliminación lógica del reporte médico
            UPDATE ReporteMedico
            SET activo = 0
            WHERE idReporteMedico = p_idReporteMedico;

            -- Opcional: Mensaje de éxito (esto no es necesario, pero puede ser útil)
            SELECT 'Reporte médico eliminado lógicamente.' AS mensaje;
        END IF;
    END IF;
END //

DELIMITER ;

-- Archivo: sp_listar_todos_reportes_medicos.sql

DELIMITER //

CREATE PROCEDURE sp_listar_todos_reportes_medicos()
BEGIN
    -- Verificar si existen reportes médicos en la base de datos
    IF NOT EXISTS (SELECT 1 FROM ReporteMedico) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No hay reportes médicos registrados.';
    ELSE
        -- Seleccionar todos los reportes médicos que están activos (no eliminados lógicamente)
        SELECT idReporteMedico, diagnostico, tratamiento, enfermedad, fecha
        FROM ReporteMedico
        WHERE activo = 1;
    END IF;
END //

DELIMITER ;

-- Archivo: sp_obtener_reporte_medico_por_id.sql

DELIMITER //

CREATE PROCEDURE sp_obtener_reporte_medico_por_id(
    IN p_idReporteMedico INT
)
BEGIN
    -- Verificar si el reporte médico existe
    IF NOT EXISTS (SELECT 1 FROM ReporteMedico WHERE idReporteMedico = p_idReporteMedico) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El reporte médico no existe';
    ELSE
        -- Verificar si el reporte está activo
        IF (SELECT activo FROM ReporteMedico WHERE idReporteMedico = p_idReporteMedico) = 0 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El reporte médico está inactivo';
        ELSE
            -- Devolver los detalles del reporte médico
            SELECT idReporteMedico, diagnostico, tratamiento, enfermedad, fecha
            FROM ReporteMedico
            WHERE idReporteMedico = p_idReporteMedico;
        END IF;
    END IF;
END //

DELIMITER ;






