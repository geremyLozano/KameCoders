-- Eliminación de Procedimientos almacenados
-- Procedimientos relacionados a areas
DROP PROCEDURE IF EXISTS PACIENTE_INSERTAR;
DROP PROCEDURE IF EXISTS PACIENTE_MODIFICAR;
DROP PROCEDURE IF EXISTS PACIENTE_ELIMINAR;
DROP PROCEDURE IF EXISTS PACIENTE_LISTAR;
-- Procedimientos relacionados a empleados
DROP PROCEDURE IF EXISTS MEDICO_INSERTAR;
DROP PROCEDURE IF EXISTS MEDICO_MODIFICAR;
DROP PROCEDURE IF EXISTS MEDICO_ELIMINAR;
DROP PROCEDURE IF EXISTS MEDICO_LISTAR;

-- Creación de Procedimientos Almacenados
DELIMITER $
-- Procedimientos relacionados a areas
DELIMITER $$

CREATE PROCEDURE PACIENTE_INSERTAR (
    IN p_nombre VARCHAR(60),
    IN p_apellido VARCHAR(60),
    IN p_correoElectronico VARCHAR(45),
    IN p_numTelefono INT,
    IN p_direccion VARCHAR(100),
    IN p_fechaNacimiento DATE,
    IN p_historialActivo TINYINT,
    IN p_idPago INT
)
BEGIN
    DECLARE last_insert_id INT;

    -- Insertar primero en la tabla Persona
    INSERT INTO Persona (
        nombre, 
        apellido, 
        correoElectronico, 
        numTelefono, 
        direccion, 
        fechaNacimiento
    ) 
    VALUES (
        p_nombre, 
        p_apellido, 
        p_correoElectronico, 
        p_numTelefono, 
        p_direccion, 
        p_fechaNacimiento
    );
    
    -- Obtener el último id insertado de Persona (idpersona)
    SET last_insert_id = LAST_INSERT_ID();
    
    -- Insertar en la tabla Paciente usando el id de Persona
    INSERT INTO Paciente (
        idpersona, 
        historialActivo, 
        idPago
    ) 
    VALUES (
        last_insert_id, 
        p_historialActivo, 
        p_idPago
    );
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE PACIENTE_MODIFICAR (
    IN p_idPaciente INT,
    IN p_numTelefono INT
)
BEGIN
    DECLARE persona_id INT;

    -- Obtener el id de la persona relacionada con el idPaciente
    SELECT idpersona INTO persona_id
    FROM Paciente
    WHERE idPaciente = p_idPaciente;
    
    -- Verificar que se haya encontrado un idpersona válido
    IF persona_id IS NOT NULL THEN
        -- Actualizar el número de teléfono en la tabla Persona
        UPDATE Persona
        SET numTelefono = p_numTelefono
        WHERE idpersona = persona_id;
    ELSE
        -- Imprimir error si no se encuentra el paciente
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe o no está vinculado a una persona';
    END IF;
    
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE PACIENTE_ELIMINAR (
    IN _id_paciente INT
)
BEGIN
    DECLARE persona_id INT;

    -- Obtener el id de la persona relacionada con el idPaciente
    SELECT idpersona INTO persona_id
    FROM Paciente
    WHERE idPaciente = _id_paciente;
    
    -- Verificar que se haya encontrado un idpersona válido
    IF persona_id IS NOT NULL THEN
        -- Eliminar el paciente de la tabla Paciente
        DELETE FROM Paciente
        WHERE idPaciente = _id_paciente;

        -- Eliminar la persona asociada de la tabla Persona
        DELETE FROM Persona
        WHERE idpersona = persona_id;
    ELSE
        -- Imprimir error si no se encuentra el paciente
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe';
    END IF;

END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE PACIENTE_LISTAR()
BEGIN
    SELECT 
        p.idPaciente,
        p.historialActivo,
        p.idPago,
        per.idpersona,
        per.nombre,
        per.apellido,
        per.correoElectronico,
        per.numTelefono,
        per.direccion,
        per.fechaNacimiento
    FROM Paciente p
	-- JOIN une la tabla de la persona con paciente
    JOIN Persona per ON p.idpersona = per.idpersona; 
END $$

DELIMITER ;
