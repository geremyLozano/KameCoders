-- Archivo: sp_insertar_cita_medica.sql

DELIMITER //

CREATE PROCEDURE sp_insertar_cita_medica(
    IN p_tipoCita VARCHAR(45),
    IN p_estadoCita VARCHAR(45),
    IN p_fecha DATE,
    IN p_hora TIME,
    IN p_idMedico INT,
    IN p_idPaciente INT,
    IN p_plataforma VARCHAR(100),
    IN p_enlace VARCHAR(400),
    IN p_duracion TIME,
    IN p_numeroAmbiente INT,
    IN p_idPago INT
)
BEGIN
    -- Verificar que el paciente exista y esté activo
    IF NOT EXISTS (SELECT 1 FROM Paciente WHERE idPaciente = p_idPaciente AND activo = 1) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El paciente no existe o no está activo';
    END IF;

    -- Verificar que el médico exista y esté activo
    IF NOT EXISTS (SELECT 1 FROM Medico WHERE idMedico = p_idMedico AND activo = 1) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El médico no existe o no está activo';
    END IF;

    -- Verificar que no haya un conflicto de horario para el médico y ambiente
    IF EXISTS (SELECT 1 FROM CitaMedica 
               WHERE idMedico = p_idMedico 
               AND fecha = p_fecha 
               AND hora = p_hora
               AND numeroAmbiente = p_numeroAmbiente) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El médico o el ambiente ya están ocupados en la misma fecha y hora';
    END IF;

    -- Insertar la nueva cita médica
    INSERT INTO CitaMedica (tipoCita, estadoCita, fecha, hora, idMedico, idPaciente, plataforma, enlace, duracion, numeroAmbiente, idPago, activo)
    VALUES (p_tipoCita, p_estadoCita, p_fecha, p_hora, p_idMedico, p_idPaciente, p_plataforma, p_enlace, p_duracion, p_numeroAmbiente, p_idPago, 1);

END //

DELIMITER ;




-- Archivo: sp_modificar_cita_medica.sql

DELIMITER //

CREATE PROCEDURE sp_modificar_cita_medica(
    IN p_idCitaMedica INT,
    IN p_estadoCita VARCHAR(45),
    IN p_fecha DATE,
    IN p_hora TIME
)
BEGIN
    -- Declaraciones de variables primero
    DECLARE v_idMedico INT;
    DECLARE v_numeroAmbiente INT;

    -- Verificar que la cita médica exista
    IF NOT EXISTS (SELECT 1 FROM CitaMedica WHERE idCitaMedica = p_idCitaMedica) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cita médica no existe';
    END IF;

    -- Obtener el médico y el ambiente asociados a la cita
    SELECT idMedico, numeroAmbiente INTO v_idMedico, v_numeroAmbiente
    FROM CitaMedica
    WHERE idCitaMedica = p_idCitaMedica;

    -- Verificar que no haya conflicto de horario con el médico o ambiente
    IF EXISTS (SELECT 1 FROM CitaMedica 
               WHERE idMedico = v_idMedico 
               AND fecha = p_fecha 
               AND hora = p_hora
               AND idCitaMedica != p_idCitaMedica
               AND numeroAmbiente = v_numeroAmbiente) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Conflicto de horario: el médico o el ambiente ya están ocupados en la misma fecha y hora';
    END IF;

    -- Modificar los datos de la cita médica
    UPDATE CitaMedica
    SET estadoCita = p_estadoCita,
        fecha = p_fecha,
        hora = p_hora
    WHERE idCitaMedica = p_idCitaMedica;

END //

DELIMITER ;



-- Archivo: sp_eliminar_cita_medica.sql

DELIMITER //

CREATE PROCEDURE sp_eliminar_cita_medica(
    IN p_idCitaMedica INT
)
BEGIN
    -- Verificar que la cita médica exista
    IF NOT EXISTS (SELECT 1 FROM CitaMedica WHERE idCitaMedica = p_idCitaMedica) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cita médica no existe';
    END IF;

    -- Eliminar los procedimientos relacionados a la cita (si aplica)
    DELETE FROM ProcedimientoCita WHERE idCitaMedica = p_idCitaMedica;

    -- Eliminar los reportes médicos relacionados a la cita (si aplica)
    DELETE FROM ReporteMedico WHERE CitaMedica_idCitaMedica = p_idCitaMedica;

    -- Eliminar los pagos relacionados a la cita (si aplica)
    DELETE FROM Pago WHERE idCitaMedica = p_idCitaMedica;

    -- Finalmente, eliminar la cita médica
    DELETE FROM CitaMedica WHERE idCitaMedica = p_idCitaMedica;
END //

DELIMITER ;



-- Archivo: sp_listar_todas_citas.sql

DELIMITER //

CREATE PROCEDURE sp_listar_todas_citas()
BEGIN
    -- Seleccionar las citas médicas que están activas
    SELECT idCitaMedica, fecha, hora, estadoCita 
    FROM CitaMedica
    WHERE activo = 1;
END //

DELIMITER ;






