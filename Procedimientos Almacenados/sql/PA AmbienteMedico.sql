-- Archivo: sp_insertar_ambiente_medico_activo.sql

DELIMITER //

CREATE PROCEDURE sp_insertar_ambiente_medico(
    IN p_numPiso INT,
    IN p_ubicacion VARCHAR(100),
    IN p_capacidad INT,
    IN p_tipoAmbiente VARCHAR(50)
)
BEGIN
    -- Verificar si ya existe un ambiente con la misma ubicación y número de piso que esté activo
    IF EXISTS (SELECT 1 FROM AmbienteMedico 
               WHERE numPiso = p_numPiso AND ubicacion = p_ubicacion AND activo = 1) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El ambiente médico ya existe con la misma ubicación y número de piso';
    ELSE
        -- Si no hay duplicados, insertar el nuevo ambiente médico
        INSERT INTO AmbienteMedico (numPiso, ubicacion, capacidad, tipoAmbiente, activo)
        VALUES (p_numPiso, p_ubicacion, p_capacidad, p_tipoAmbiente, 1);
    END IF;
END //

DELIMITER ;


-- Archivo: sp_actualizar_ambiente_medico.sql

DELIMITER //

CREATE PROCEDURE sp_actualizar_ambiente_medico(
    IN p_idAmbienteMedico INT,
    IN p_numPiso INT,
    IN p_ubicacion VARCHAR(100),
    IN p_capacidad INT,
    IN p_tipoAmbiente VARCHAR(50)
)
BEGIN
    -- Verificar si ya existe otro ambiente con la misma ubicación y número de piso
    IF EXISTS (SELECT 1 FROM AmbienteMedico 
               WHERE numPiso = p_numPiso AND ubicacion = p_ubicacion 
               AND idAmbienteMedico != p_idAmbienteMedico AND activo = 1) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Otro ambiente médico ya existe con la misma ubicación y número de piso';
    ELSE
        -- Actualizar el ambiente médico
        UPDATE AmbienteMedico
        SET numPiso = p_numPiso,
            ubicacion = p_ubicacion,
            capacidad = p_capacidad,
            tipoAmbiente = p_tipoAmbiente
        WHERE idAmbienteMedico = p_idAmbienteMedico;
    END IF;
END //

DELIMITER ;




-- Archivo: sp_obtener_ambiente_medico_por_id.sql

DELIMITER //

CREATE PROCEDURE sp_obtener_ambiente_medico_por_id(
    IN p_idAmbienteMedico INT
)
BEGIN
    -- Seleccionar el ambiente médico, independientemente de si está activo o no
    SELECT idAmbienteMedico, numPiso, ubicacion, capacidad, tipoAmbiente, activo
    FROM AmbienteMedico
    WHERE idAmbienteMedico = p_idAmbienteMedico;
END //

DELIMITER ;



-- Archivo: sp_listar_todos_ambiente_medico.sql

DELIMITER //

CREATE PROCEDURE sp_listar_todos_ambiente_medico()
BEGIN
    -- Seleccionar solo los ambientes médicos activos
    SELECT idAmbienteMedico, numPiso, ubicacion, capacidad, tipoAmbiente
    FROM AmbienteMedico
    WHERE activo = 1;
END //

DELIMITER ;



-- Archivo: sp_eliminar_logico_ambiente_medico.sql

DELIMITER //

CREATE PROCEDURE sp_eliminar_logico_ambiente_medico(
    IN p_idAmbienteMedico INT
)
BEGIN
    -- Verificar si hay procedimientos activos asociados al ambiente médico
    IF EXISTS (SELECT 1 FROM Procedimiento WHERE idAmbienteMedico = p_idAmbienteMedico AND activo = 1) THEN
        -- Si hay procedimientos activos, lanzar un error
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede eliminar el ambiente médico porque tiene procedimientos activos asociados';
    ELSE
        -- Si no hay procedimientos asociados, proceder con la eliminación lógica del ambiente médico
        UPDATE AmbienteMedico
        SET activo = 0
        WHERE idAmbienteMedico = p_idAmbienteMedico;
    END IF;
END //

DELIMITER ;


/*
CALL sp_insertar_ambiente_medico(1, 'Ubicación A', 100, 'Consultorio');
CALL sp_insertar_ambiente_medico(2, 'Ubicación B', 50, 'Laboratorio');
CALL sp_insertar_ambiente_medico(3, 'Ubicación C', 200, 'Quirófano');
SELECT * FROM AmbienteMedico;
CALL sp_eliminar_logico_ambiente_medico(2);
SELECT * FROM AmbienteMedico WHERE idAmbienteMedico = 2;
SELECT * FROM AmbienteMedico WHERE activo = 1;
CALL sp_actualizar_ambiente_medico(1, 5, 'Nueva Ubicación X', 150, 'Nuevo Tipo');
SELECT * FROM AmbienteMedico WHERE idAmbienteMedico = 1;
CALL sp_listar_todos_ambiente_medico();
*/







