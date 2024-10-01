--ESPECIALIDAD_INSERTAR

DELIMITER //
CREATE PROCEDURE ESPECIALIDAD_INSERTAR(
    IN p_nombre VARCHAR(80),
    IN p_costoConsulta DOUBLE
)
BEGIN
    INSERT INTO Especialidad (nombre, costoConsulta, activo)
    VALUES (p_nombre, p_costoConsulta, 1);
END //
DELIMITER ;

--ESPECIALIDAD_MODIFICAR

DELIMITER //
CREATE PROCEDURE ESPECIALIDAD_ACTUALIZAR(
    IN p_idEspecialidad INT,
    IN p_nombre VARCHAR(80),
    IN p_costoConsulta DOUBLE,
    IN p_activo TINYINT
)
BEGIN
    UPDATE Especialidad
    SET nombre = p_nombre,
        costoConsulta = p_costoConsulta,
        activo = p_activo
    WHERE idEspecialidad = p_idEspecialidad;
END //
DELIMITER ;

--ESPECIALIDAD_ELIMINAR

DELIMITER //
CREATE PROCEDURE ESPECIALIDAD_ELIMINAR(
    IN p_idEspecialidad INT
)
BEGIN
    UPDATE Especialidad
    SET activo = 0
    WHERE idEspecialidad = p_idEspecialidad;
END //
DELIMITER ;

--LISTAR TODOS

CREATE DEFINER=`admin`@`%` PROCEDURE `ESPECIALIDAD_LISTAR_TODAS`()
 BEGIN
     SELECT idEspecialidad, nombre, costoConsulta, activo
     FROM Especialidad
     WHERE activo = 1;
 END

--ESPECIALIDAD BUSCAR POR ID

DELIMITER //
CREATE PROCEDURE ESPECIALIDAD_BUSCARPORID(
    IN p_idEspecialidad INT
)
BEGIN
    SELECT * 
    FROM Especialidad
    WHERE idEspecialidad = p_idEspecialidad;
END //
DELIMITER ;



