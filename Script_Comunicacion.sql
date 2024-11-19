-- Modificar la tabla Comunicacion para agregar las columnas 'estado' y 'respuesta'
ALTER TABLE Comunicacion
ADD COLUMN estado VARCHAR(50),
ADD COLUMN respuesta VARCHAR(500);

-- Eliminar el procedimiento si ya existe y crear uno nuevo para insertar
DROP PROCEDURE IF EXISTS COMUNICACION_INSERTAR;
DELIMITER //
CREATE PROCEDURE COMUNICACION_INSERTAR(
    IN p_tipoComunicacion VARCHAR(500),
    IN p_contenido VARCHAR(400),
    IN p_fechaComunicacion DATE,
    IN p_idPaciente INT,
    IN p_estado VARCHAR(50),
    IN p_respuesta VARCHAR(500)
)
BEGIN
    -- Insertar una nueva comunicación con el estado y respuesta proporcionados
    INSERT INTO Comunicacion (tipoComunicacion, contenido, fechaComunicacion, activo, idPaciente, estado, respuesta)
    VALUES (p_tipoComunicacion, p_contenido, p_fechaComunicacion, 1, p_idPaciente, p_estado, p_respuesta);
END //
DELIMITER ;

-- Eliminar el procedimiento si ya existe y crear uno nuevo para actualizar
DROP PROCEDURE IF EXISTS COMUNICACION_ACTUALIZAR;
DELIMITER //
CREATE PROCEDURE COMUNICACION_ACTUALIZAR(
    IN p_idComunicacion INT,
    IN p_tipoComunicacion VARCHAR(500),
    IN p_contenido VARCHAR(400),
    IN p_fechaComunicacion DATE,
    IN p_activo TINYINT,
    IN p_idPaciente INT,
    IN p_estado VARCHAR(50),
    IN p_respuesta VARCHAR(500)
)
BEGIN
    -- Actualizar la comunicación, incluyendo el estado y la respuesta
    UPDATE Comunicacion
    SET tipoComunicacion = p_tipoComunicacion,
        contenido = p_contenido,
        fechaComunicacion = p_fechaComunicacion,
        activo = p_activo,
        idPaciente = p_idPaciente,
        estado = p_estado,
        respuesta = p_respuesta
    WHERE idComunicacion = p_idComunicacion;
END //
DELIMITER ;

-- Eliminar el procedimiento si ya existe y crear uno nuevo para listar todas las comunicaciones
DROP PROCEDURE IF EXISTS COMUNICACION_LISTAR_TODOS;
DELIMITER //
CREATE PROCEDURE COMUNICACION_LISTAR_TODOS()
BEGIN
    SELECT 
        idComunicacion, 
        tipoComunicacion, 
        contenido, 
        fechaComunicacion, 
        activo, 
        idPaciente, 
        estado, 
        respuesta
    FROM Comunicacion
    WHERE activo = 1
    ORDER BY fechaComunicacion DESC;
END //
DELIMITER ;

-- Eliminar el procedimiento si ya existe y crear uno nuevo para buscar por ID
DROP PROCEDURE IF EXISTS COMUNICACION_BUSCARPORID;
DELIMITER //
CREATE PROCEDURE COMUNICACION_BUSCARPORID(
    IN p_idComunicacion INT
)
BEGIN
    SELECT 
        idComunicacion, 
        tipoComunicacion, 
        contenido, 
        fechaComunicacion, 
        activo, 
        idPaciente, 
        estado, 
        respuesta
    FROM Comunicacion
    WHERE idComunicacion = p_idComunicacion;
END //
DELIMITER ;


