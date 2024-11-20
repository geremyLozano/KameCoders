-- Archivo: sp_insertar_procedimiento.sql

DELIMITER //

CREATE PROCEDURE sp_insertar_procedimiento(
    IN p_nombre VARCHAR(100),
    IN p_costo DOUBLE,
    IN p_descripcion VARCHAR(200),
    IN p_requisitosPrevios VARCHAR(200),
    IN p_tipoProcedimiento VARCHAR(50),
    IN p_idAmbienteMedico INT
)
BEGIN
    -- Verificar si el ambiente médico está activo
    IF (SELECT activo FROM AmbienteMedico WHERE idAmbienteMedico = p_idAmbienteMedico) = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede asociar el procedimiento a un ambiente inactivo';
    ELSE
        -- Verificar si ya existe un procedimiento con el mismo nombre en el mismo ambiente médico
        IF EXISTS (SELECT 1 FROM Procedimiento WHERE nombre = p_nombre AND idAmbienteMedico = p_idAmbienteMedico) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El procedimiento ya existe en el ambiente médico';
        ELSE
            -- Insertar el nuevo procedimiento
            INSERT INTO Procedimiento (nombre, costo, descripcion, requisitosPrevios, tipoProcedimiento, idAmbienteMedico, activo)
            VALUES (p_nombre, p_costo, p_descripcion, p_requisitosPrevios, p_tipoProcedimiento, p_idAmbienteMedico, 1);
        END IF;
    END IF;
END //

DELIMITER ;



-- Archivo: sp_modificar_procedimiento.sql

DELIMITER //

CREATE PROCEDURE sp_modificar_procedimiento(
    IN p_idProcedimiento INT,
    IN p_nombre VARCHAR(100),
    IN p_costo DOUBLE,
    IN p_descripcion VARCHAR(200),
    IN p_requisitosPrevios VARCHAR(200),
    IN p_tipoProcedimiento VARCHAR(50)
)
BEGIN
    DECLARE v_idAmbienteMedico INT;

    -- Obtener el ambiente médico relacionado con el procedimiento
    SELECT idAmbienteMedico INTO v_idAmbienteMedico FROM Procedimiento WHERE idProcedimiento = p_idProcedimiento;

    -- Verificar si el ambiente médico está activo
    IF (SELECT activo FROM AmbienteMedico WHERE idAmbienteMedico = v_idAmbienteMedico) = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede modificar un procedimiento asociado a un ambiente inactivo';
    ELSE
        -- Verificar si ya existe otro procedimiento con el mismo nombre en el mismo ambiente médico
        IF EXISTS (SELECT 1 FROM Procedimiento WHERE nombre = p_nombre AND idAmbienteMedico = v_idAmbienteMedico AND idProcedimiento != p_idProcedimiento) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Ya existe otro procedimiento con el mismo nombre en el mismo ambiente médico';
        ELSE
            -- Actualizar el procedimiento
            UPDATE Procedimiento
            SET nombre = p_nombre,
                costo = p_costo,
                descripcion = p_descripcion,
                requisitosPrevios = p_requisitosPrevios,
                tipoProcedimiento = p_tipoProcedimiento
            WHERE idProcedimiento = p_idProcedimiento;
        END IF;
    END IF;
END //

DELIMITER ;


-- Archivo: sp_listar_todos_procedimientos.sql

DELIMITER //

CREATE PROCEDURE sp_listar_todos_procedimientos()
BEGIN
    -- Seleccionar todos los procedimientos que están activos
    SELECT idProcedimiento, nombre, costo, descripcion, requisitosPrevios, tipoProcedimiento
    FROM Procedimiento
    WHERE activo = 1;
END //

DELIMITER ;


-- Archivo: sp_obtener_procedimiento_por_id.sql
DELIMITER //

CREATE PROCEDURE sp_obtener_procedimiento_por_id(
    IN p_idProcedimiento INT
)
BEGIN
    -- Seleccionar el procedimiento por ID
    SELECT idProcedimiento, nombre, costo, descripcion, requisitosPrevios, tipoProcedimiento, activo
    FROM Procedimiento
    WHERE idProcedimiento = p_idProcedimiento;
END //

DELIMITER ;


-- Archivo: sp_eliminar_logico_procedimiento.sql

DELIMITER //

CREATE PROCEDURE sp_eliminar_logico_procedimiento(
    IN p_idProcedimiento INT
)
BEGIN
    -- Verificar si el procedimiento está asociado a citas médicas activas
    IF EXISTS (SELECT 1 FROM CitaMedica_has_Procedimiento
               WHERE Procedimiento_idProcedimiento = p_idProcedimiento) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede eliminar el procedimiento porque está asociado a citas médicas activas';
    ELSE
        -- Realizar la eliminación lógica del procedimiento
        UPDATE Procedimiento
        SET activo = 0
        WHERE idProcedimiento = p_idProcedimiento;
    END IF;
END //

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_obtener_procedimientos_por_paciente (
    IN p_idPaciente INT
)
BEGIN
    SELECT 
        p.idProcedimiento,
        p.nombre AS Procedimiento,
        p.descripcion,
        p.tipoProcedimiento,
        p.costo,
        c.idCitaMedica,
        c.fecha AS FechaCita,
        c.hora AS HoraCita,
        cmp.asistencia,
        cmp.observaciones,
        cmp.fechaResultado
    FROM 
        CitaMedica c
    JOIN 
        CitaMedica_has_Procedimiento cmp ON c.idCitaMedica = cmp.idCitaMedica
    JOIN 
        Procedimiento p ON cmp.Procedimiento_idProcedimiento = p.idProcedimiento
    WHERE 
        c.idPaciente = p_idPaciente;
END$$

DELIMITER ;


/*
CALL sp_insertar_procedimiento('Consulta General', 150.00, 'Consulta de rutina', 'Ninguno', 'TIPO_A', 1);
CALL sp_insertar_procedimiento('Cirugía Menor', 500.00, 'Cirugía ambulatoria', 'Ayuno de 8 horas', 'TIPO_B', 1);
CALL sp_insertar_procedimiento('Examen de Laboratorio', 100.00, 'Análisis de sangre completo', 'Ayuno de 12 horas', 'TIPO_A', 1);

CALL sp_modificar_procedimiento(2, 'Cirugía Mayor', 750.00, 'Cirugía de mayor complejidad', 'Ayuno de 12 horas', 'TIPO_B');
CALL sp_eliminar_logico_procedimiento(3);
CALL sp_listar_todos_procedimientos();
CALL sp_obtener_procedimiento_por_id(2);
*/

