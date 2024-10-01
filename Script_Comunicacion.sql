DELIMITER //
CREATE PROCEDURE COMUNICACION_INSERTAR(
    IN p_tipoComunicacion VARCHAR(500),
    IN p_contenido VARCHAR(400),
    IN p_fechaComunicacion DATE,
    IN p_idPaciente INT
)
BEGIN
    INSERT INTO Comunicacion (tipoComunicacion, contenido, fechaComunicacion, activo, idPaciente)
    VALUES (p_tipoComunicacion, p_contenido, p_fechaComunicacion, 1, p_idPaciente);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE COMUNICACION_ACTUALIZAR(
    IN p_idComunicacion INT,
    IN p_tipoComunicacion VARCHAR(500),
    IN p_contenido VARCHAR(400),
    IN p_fechaComunicacion DATE,
    IN p_activo TINYINT,
    IN p_idPaciente INT
)
BEGIN
    UPDATE Comunicacion
    SET tipoComunicacion = p_tipoComunicacion,
        contenido = p_contenido,
        fechaComunicacion = p_fechaComunicacion,
        activo = p_activo,
        idPaciente = p_idPaciente
    WHERE idComunicacion = p_idComunicacion;
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE COMUNICACION_ELIMINAR(
    IN p_idComunicacion INT
)
BEGIN
    UPDATE Comunicacion
    SET activo = 0
    WHERE idComunicacion = p_idComunicacion;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE COMUNICACION_LISTAR_TODOS()
BEGIN
    SELECT idComunicacion, tipoComunicacion, contenido, fechaComunicacion, activo, idPaciente
    FROM Comunicacion
    WHERE activo = 1;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE COMUNICACION_BUSCARPORID(
    IN p_idComunicacion INT
)
BEGIN
    SELECT idComunicacion, tipoComunicacion, contenido, fechaComunicacion, activo, idPaciente
    FROM Comunicacion
    WHERE idComunicacion = p_idComunicacion;
END //
DELIMITER ;




