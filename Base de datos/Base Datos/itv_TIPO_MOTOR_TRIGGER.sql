use proyectoitv;

DROP FUNCTION IF EXISTS validar_tipo_motor;

DELIMITER $$
CREATE FUNCTION validar_tipo_motor(tipo VARCHAR(15)) RETURNS BOOLEAN
    BEGIN
        RETURN tipo = 'HÍBRIDO' OR tipo = 'ELÉCTRICO' OR tipo = 'DIESEL' OR tipo = 'GASOLINA';
    END $$;


DROP TRIGGER IF EXISTS validar_tipo_motor_insert;
DROP TRIGGER IF EXISTS validar_tipo_motor_update;


delimiter $$;
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
    END $$;

delimiter $$;
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
    END $$;