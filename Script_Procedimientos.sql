-- Eliminación de Procedimientos almacenados
-- Procedimientos relacionados a paciente
DROP PROCEDURE IF EXISTS PACIENTE_INSERTAR;
DROP PROCEDURE IF EXISTS PACIENTE_MODIFICAR;
DROP PROCEDURE IF EXISTS PACIENTE_ELIMINAR;
DROP PROCEDURE IF EXISTS PACIENTE_LISTAR;
-- Procedimientos relacionados a medico
DROP PROCEDURE IF EXISTS MEDICO_INSERTAR;
DROP PROCEDURE IF EXISTS MEDICO_MODIFICAR;
DROP PROCEDURE IF EXISTS MEDICO_ELIMINAR;
DROP PROCEDURE IF EXISTS MEDICO_LISTAR;
-- Procedimientos relacionados a auxiliar
DROP PROCEDURE IF EXISTS AUXILIAR_INSERTAR;
DROP PROCEDURE IF EXISTS AUXILIAR_MODIFICAR;
DROP PROCEDURE IF EXISTS AUXILIAR_ELIMINAR;
DROP PROCEDURE IF EXISTS AUXILIAR_LISTAR;
-- Procedimientos relacionados a administrador
DROP PROCEDURE IF EXISTS ADMIN_INSERTAR;
DROP PROCEDURE IF EXISTS ADMIN_MODIFICAR;
DROP PROCEDURE IF EXISTS ADMIN_ELIMINAR;
DROP PROCEDURE IF EXISTS ADMIN_LISTAR;


-- Creación de Procedimientos Almacenados
DELIMITER $
-- Procedimientos relacionados a areas
DELIMITER $$

CREATE PROCEDURE PACIENTE_INSERTAR (
    OUT _id_paciente INT,
    IN _nombre VARCHAR(60),
    IN _apellido VARCHAR(60),
    IN _correoElectronico VARCHAR(45),
    IN _numTelefono INT,
    IN _direccion VARCHAR(100),
    IN _fechaNacimiento DATE,
    IN _genero CHAR(1),
    IN _idComunicacion INT,
    IN _historialActivo TINYINT,
    IN _idPago INT
)
BEGIN
    DECLARE last_insert_id INT;

    INSERT INTO Persona (
		dni,
        nombre, 
        apellido, 
        correoElectronico, 
        numTelefono, 
        direccion, 
        fechaNacimiento,
        genero,
        idComunicacion
    ) 
    VALUES (
		_dni,
        _nombre, 
        _apellido, 
        _correoElectronico, 
        _numTelefono, 
        _direccion, 
        _fechaNacimiento,
        _genero,
        _idComunicacion
    );
    
    SET last_insert_id = LAST_INSERT_ID();
    
    INSERT INTO Paciente (
        idpersona, 
        historialActivo, 
        idPago
    ) 
    VALUES (
        last_insert_id, 
        _historialActivo, 
        _idPago
    );
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE PACIENTE_MODIFICAR (
    IN _idPaciente INT,
    IN _numTelefono INT
)
BEGIN
    DECLARE persona_id INT;

    SELECT idpersona INTO persona_id
    FROM Paciente
    WHERE idPaciente = _idPaciente;
    
    IF persona_id IS NOT NULL THEN
        UPDATE Persona SET numTelefono = _numTelefono WHERE idpersona = persona_id;
    ELSE
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
    SELECT idpersona INTO persona_id FROM Paciente WHERE idPaciente = _id_paciente;
    
    IF persona_id IS NOT NULL THEN
        DELETE FROM Paciente WHERE idPaciente = _id_paciente;
        DELETE FROM Persona WHERE idpersona = persona_id;
    ELSE
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
    JOIN Persona per ON p.idpersona = per.idpersona; 
END $$

DELIMITER ;

-----------------------------------------------------

DELIMITER $$

CREATE PROCEDURE MEDICO_INSERTAR (
    IN _nombre VARCHAR(60),
    IN _apellido VARCHAR(60),
    IN _correoElectronico VARCHAR(45),
    IN _numTelefono INT,
    IN _direccion VARCHAR(100),
    IN _fechaNacimiento DATE,
    IN _genero CHAR(1),
    IN _idComunicacion INT,
    IN _idespecialidad INT,
    IN _numColegiatura VARCHAR(50),
    IN _horaInicioTrabajo TIME,
    IN _horaFinTrabajo TIME,
    IN _diasLaborales VARCHAR(200),
    IN _anhosEXP INT,
    IN _activo TINYINT
)
BEGIN
    DECLARE last_insert_id INT;

    INSERT INTO Persona (
        nombre, 
        apellido, 
        correoElectronico, 
        numTelefono, 
        direccion, 
        fechaNacimiento,
        genero,
        idComunicacion
    ) 
    VALUES (
        _nombre, 
        _apellido, 
        _correoElectronico, 
        _numTelefono, 
        _direccion, 
        _fechaNacimiento,
        _genero,
        _idComunicacion
    );
    
    SET last_insert_id = LAST_INSERT_ID();
    
    INSERT INTO Medico (
        idpersona, 
        idespecialidad, 
        numColegiatura,
	horaInicioTrabajo,
	horaFinTrabajo,
	diasLaborales,
	anhosExp,
	activo
    ) 
    VALUES (
        last_insert_id, 
        _idespecialidad,
        _numColegiatura,
        _horaInicioTrabajo ,
        _horaFinTrabajo ,
        _diasLaborales ,
        _anhosEXP ,
        _activo 
    );
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE MEDICO_MODIFICAR (
    IN _idMedico INT,
    IN _activo TINYINT
)
BEGIN
    DECLARE persona_id INT;

    SELECT idpersona INTO persona_id FROM Medico WHERE idMedico = _idMedico;
    
    IF persona_id IS NOT NULL THEN
        UPDATE Medico SET activo = _activo WHERE idpersona = persona_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe o no está vinculado a una persona';
    END IF;
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE MEDICO_ELIMINAR (
    IN _idMedico INT
)
BEGIN
    DECLARE persona_id INT;
    SELECT idpersona INTO persona_id FROM Medico WHERE idMedico = _idMedico;
    
    IF persona_id IS NOT NULL THEN
        DELETE FROM Medico WHERE idMedico = _id_medico;
        DELETE FROM Persona WHERE idpersona = persona_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe';
    END IF;

END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE MEDICO_LISTAR()
BEGIN
    SELECT 
        m.idMedico,
        m.numColegiatura,
        m.horaInicioTrabajo,
	m.horaFinTrabajo,
	m.diasLaborales,
	m.activo,
        per.idpersona,
        per.nombre,
        per.apellido,
        per.correoElectronico,
        per.numTelefono,
        per.direccion,
        per.fechaNacimiento
    FROM Medico m
    JOIN Persona per ON p.idpersona = per.idpersona; 
END $$

DELIMITER ;


-----------------------------------------------------------------

DELIMITER $$

CREATE PROCEDURE AUXILIAR_INSERTAR (
    IN _nombre VARCHAR(60),
    IN _apellido VARCHAR(60),
    IN _correoElectronico VARCHAR(45),
    IN _numTelefono INT,
    IN _direccion VARCHAR(100),
    IN _fechaNacimiento DATE,
    IN _genero CHAR(1),
    IN _idComunicacion INT,
    IN _idEspecialidad INT,
    IN _activo TINYINT
)
BEGIN
    DECLARE last_insert_id INT;

    INSERT INTO Persona (
        nombre, 
        apellido, 
        correoElectronico, 
        numTelefono, 
        direccion, 
        fechaNacimiento,
        genero,
        idComunicacion
    ) 
    VALUES (
        _nombre, 
        _apellido, 
        _correoElectronico, 
        _numTelefono, 
        _direccion, 
        _fechaNacimiento,
        _genero,
        _idComunicacion
    );
    
    SET last_insert_id = @@last_insert_id;
    
    INSERT INTO Auxiliar (
        idpersona, 
        idespecialidad, 
	activo
    ) 
    VALUES (
        last_insert_id, 
        _idespecialidad,
        _activo 
    );
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE AUXILIAR_MODIFICAR (
    IN _idAuxiliar INT,
    IN _activo TINYINT
)
BEGIN
    DECLARE persona_id INT;

    SELECT idpersona INTO persona_id FROM Auxiliar WHERE idAuxiliar = _idAuxiliar;
    
    IF persona_id IS NOT NULL THEN
        UPDATE Auxiliar SET activo = _activo WHERE idpersona = persona_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe o no está vinculado a una persona';
    END IF;
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE AUXILIAR_ELIMINAR (
    IN _idAuxiliar INT
)
BEGIN
    DECLARE persona_id INT;
    SELECT idpersona INTO persona_id FROM Auxiliar WHERE idAuxiliar = _idAuxiliar;
    
    IF persona_id IS NOT NULL THEN
        DELETE FROM Auxiliar WHERE idAuxiliar = _idAuxiliar;
        DELETE FROM Persona WHERE idpersona = persona_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe';
    END IF;

END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE AUXILIAR_LISTAR()
BEGIN
    SELECT 
        a.idAuxiliar,
	a.activo,
        per.idpersona,
        per.nombre,
        per.apellido,
        per.correoElectronico,
        per.numTelefono,
        per.direccion,
        per.fechaNacimiento
    FROM Auxiliar a
    JOIN Persona per ON p.idpersona = per.idpersona; 
END $$

DELIMITER ;

--------------------------------------------------------------

DELIMITER $$

CREATE PROCEDURE ADMIN_INSERTAR (
    IN _nombre VARCHAR(60),
    IN _apellido VARCHAR(60),
    IN _correoElectronico VARCHAR(45),
    IN _numTelefono INT,
    IN _direccion VARCHAR(100),
    IN _fechaNacimiento DATE,
    IN _genero CHAR(1),
    IN _idComunicacion INT
)
BEGIN
    DECLARE last_insert_id INT;

    INSERT INTO Persona (
        nombre, 
        apellido, 
        correoElectronico, 
        numTelefono, 
        direccion, 
        fechaNacimiento,
        genero,
        idComunicacion
    ) 
    VALUES (
        _nombre, 
        _apellido, 
        _correoElectronico, 
        _numTelefono, 
        _direccion, 
        _fechaNacimiento,
        _genero,
        _idComunicacion
    );
    
    SET last_insert_id = @@last_insert_id;
    
    INSERT INTO Auxiliar (
        idpersona
    ) 
    VALUES (
        last_insert_id
    );
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE ADMIN_MODIFICAR (
    IN _idAdmin INT,
    IN _numTelefono TINYINT
)
BEGIN
    DECLARE persona_id INT;

    SELECT idpersona INTO persona_id FROM Administrador WHERE idAdmin = _idAdmin;
    
    IF persona_id IS NOT NULL THEN
        UPDATE Persona SET numTelefono = _numTelefono WHERE idpersona = persona_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe o no está vinculado a una persona';
    END IF;
    
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE ADMIN_ELIMINAR (
    IN _idAmin INT
)
BEGIN
    DECLARE persona_id INT;
    SELECT idpersona INTO persona_id FROM Administrador WHERE idAmin = _idAmin;
    
    IF persona_id IS NOT NULL THEN
        DELETE FROM Administrador WHERE idAmin = _idAmin;
        DELETE FROM Persona WHERE idpersona = persona_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El idPaciente no existe';
    END IF;

END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE ADMIN_LISTAR()
BEGIN
    SELECT 
        ad.idAmin,
        per.idpersona,
        per.nombre,
        per.apellido,
        per.correoElectronico,
        per.numTelefono,
        per.direccion,
        per.fechaNacimiento
    FROM Administrador ad
    JOIN Persona per ON p.idpersona = per.idpersona; 
END $$

DELIMITER ;