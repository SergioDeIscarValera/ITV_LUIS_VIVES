# DNI válido:

CREATE FUNCTION validar_dni(dni CHAR(9)) RETURNS BOOLEAN
BEGIN
    DECLARE num_dni INTEGER(8);
    DECLARE resto INTEGER(1);
    DECLARE letra CHAR(1);

    SET num_dni = CAST(SUBSTR(dni, 1, 8) AS INTEGER);
    SET resto = num_dni MOD 23;

    CASE resto
        WHEN 0 THEN SET letra = 'T';
        WHEN 1 THEN SET letra = 'R';
        WHEN 2 THEN SET letra = 'W';
        WHEN 3 THEN SET letra = 'A';
        WHEN 4 THEN SET letra = 'G';
        WHEN 5 THEN SET letra = 'M';
        WHEN 6 THEN SET letra = 'Y';
        WHEN 7 THEN SET letra = 'F';
        WHEN 8 THEN SET letra = 'P';
        WHEN 9 THEN SET letra = 'D';
        WHEN 10 THEN SET letra = 'X';
        WHEN 11 THEN SET letra = 'B';
        WHEN 12 THEN SET letra = 'N';
        WHEN 13 THEN SET letra = 'J';
        WHEN 14 THEN SET letra = 'Z';
        WHEN 15 THEN SET letra = 'S';
        WHEN 16 THEN SET letra = 'Q';
        WHEN 17 THEN SET letra = 'V';
        WHEN 18 THEN SET letra = 'H';
        WHEN 19 THEN SET letra = 'L';
        WHEN 20 THEN SET letra = 'C';
        WHEN 21 THEN SET letra = 'K';
        WHEN 22 THEN SET letra = 'E';
    END CASE;

    RETURN letra = SUBSTR(dni, 9);
END;

CREATE TRIGGER validar_dni_insert_tpropietario
BEFORE INSERT ON tpropietario
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_dni(NEW.cDNI) THEN
        SET error_message = CONCAT('El DNI ', NEW.cDNI, ' no es válido.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

CREATE TRIGGER validar_dni_update_tpropietario
BEFORE UPDATE ON tpropietario
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_dni(NEW.cDNI) THEN
        SET error_message = CONCAT('El DNI ', NEW.cDNI, ' no es válido.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

# Contraseña cifrada:
CREATE FUNCTION cifrar_contraseña(contraseña VARCHAR(255)) RETURNS VARCHAR(255)
BEGIN
    DECLARE contraseña_cifrada VARCHAR(255);
    SET contraseña_cifrada = SHA2(contraseña, 256);
    RETURN contraseña_cifrada;
END;

CREATE TRIGGER cifrar_contraseña_insert BEFORE INSERT ON ttrabajadores
FOR EACH ROW
BEGIN
    SET NEW.cPassword = cifrar_contraseña(NEW.cPassword);
END;

CREATE TRIGGER cifrar_contraseña_update BEFORE UPDATE ON ttrabajadores
FOR EACH ROW
BEGIN
    SET NEW.cPassword = cifrar_contraseña(NEW.cPassword);
END;

# Vehicle válido:
CREATE FUNCTION validar_tipo_vehiculo(tipo VARCHAR(15)) RETURNS BOOLEAN
BEGIN
    RETURN tipo = 'CAMIÓN' OR tipo = 'MOTOCICLETA' OR tipo = 'TURISMO' OR tipo = 'FURGONETA';
END;

CREATE TRIGGER validar_tipo_vehiculo_insert
BEFORE INSERT ON tvehiculo
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_tipo_vehiculo(NEW.cTipoVehiculo) THEN
        SET error_message = CONCAT('El tipo de vehículo ', NEW.cTipoVehiculo, ' no es válido.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

CREATE TRIGGER validar_tipo_vehiculo_update
BEFORE UPDATE ON tvehiculo
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_tipo_vehiculo(NEW.cTipoVehiculo) THEN
        SET error_message = CONCAT('El tipo de vehículo ', NEW.cTipoVehiculo, ' no es válido.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

# Motor válido:
CREATE FUNCTION validar_tipo_motor(tipo VARCHAR(15)) RETURNS BOOLEAN
BEGIN
    RETURN tipo = 'HÍBRIDO' OR tipo = 'ELÉCTRICO' OR tipo = 'DIESEL' OR tipo = 'GASOLINA';
END;

CREATE TRIGGER validar_tipo_motor_insert
BEFORE INSERT ON tvehiculo
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_tipo_motor(NEW.cTipoMotor) THEN
        SET error_message = CONCAT('El tipo de motor ', NEW.cTipoMotor, ' no es válido.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END ;

CREATE TRIGGER validar_tipo_motor_update
BEFORE UPDATE ON tvehiculo
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_tipo_motor(NEW.cTipoMotor) THEN
        SET error_message = CONCAT('El tipo de motor ', NEW.cTipoMotor, ' no es válido.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

# Especialidad valida:
CREATE FUNCTION validar_especialidad(especialidad VARCHAR(15)) RETURNS BOOLEAN
BEGIN
    RETURN especialidad = 'ADMINISTRACION' OR especialidad = 'ELECTRICIDAD' OR especialidad = 'MOTOR' OR especialidad = 'MECANICA' OR especialidad = 'INTERIOR';
END;

CREATE TRIGGER validar_especialidad_insert
BEFORE INSERT ON ttrabajadores
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_especialidad(NEW.cNombreEspecialidad) THEN
        SET error_message = CONCAT('La especialidad ', NEW.cNombreEspecialidad, ' no es válida.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

CREATE TRIGGER validar_especialidad_update
BEFORE UPDATE ON ttrabajadores
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_especialidad(NEW.cNombreEspecialidad) THEN
        SET error_message = CONCAT('La especialidad ', NEW.cNombreEspecialidad, ' no es válida.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

# Matricula Valida:
CREATE FUNCTION validar_matricula(matricula CHAR(7)) RETURNS BOOLEAN
BEGIN
    RETURN matricula REGEXP '^[0-9]{4}[B-DF-HJ-NP-TV-Z]{3}$';
END;

CREATE TRIGGER validar_matricula_insert_vehiculo
BEFORE INSERT ON tvehiculo
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_matricula(NEW.cMatricula) THEN
        SET error_message = CONCAT('La matrícula ', NEW.cMatricula, ' no es válida.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

CREATE TRIGGER validar_matricula_update_vehiculo
BEFORE UPDATE ON tvehiculo
FOR EACH ROW
BEGIN
    DECLARE error_message VARCHAR(30);
    IF NOT validar_matricula(NEW.cMatricula) THEN
        SET error_message = CONCAT('La matrícula ', NEW.cMatricula, ' no es válida.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = error_message;
    end IF;
END;

# Fecha matriculación menor que fecha de ultima revisión
CREATE TRIGGER validar_fechas_insert
BEFORE INSERT ON tvehiculo
FOR EACH ROW
BEGIN
    IF (NEW.dFecha_Matriculacion > NEW.dFecha_UltimaRevision) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La fecha de matriculación debe ser menor que la fecha de la última revisión.';
    end IF;
END;

CREATE TRIGGER validar_fechas_update
BEFORE UPDATE ON tvehiculo
FOR EACH ROW
BEGIN
    IF (NEW.dFecha_Matriculacion > NEW.dFecha_UltimaRevision) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La fecha de matriculación debe ser menor que la fecha de la última revisión.';
    end IF;
END;

CREATE TRIGGER validar_fecha_cita_insert
BEFORE INSERT ON tcitas
FOR EACH ROW
BEGIN
    IF (NEW.dFecha_Citacion < NOW()) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La fecha de la cita debe ser futura.';
    end IF;
END;

CREATE TRIGGER validar_fecha_cita_update
BEFORE UPDATE ON tcitas
FOR EACH ROW
BEGIN
    IF (NEW.dFecha_Citacion < NOW()) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La fecha de la cita debe ser futura.';
    end IF;
END;

# Actualizar ultima revisión:
CREATE TRIGGER ultima_revision AFTER INSERT ON tcitas
FOR EACH ROW
BEGIN
    UPDATE tvehiculo SET tvehiculo.dFecha_UltimaRevision = NEW.dFecha_Citacion WHERE tvehiculo.cMatricula = NEW.cMatricula;
END;