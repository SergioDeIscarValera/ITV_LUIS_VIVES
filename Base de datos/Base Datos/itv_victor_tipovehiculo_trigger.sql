use proyectoitv;

DROP FUNCTION IF EXISTS validar_tipo_vehiculo;

DELIMITER $$
CREATE FUNCTION validar_tipo_vehiculo(tipo VARCHAR(15)) RETURNS BOOLEAN
    BEGIN
        RETURN tipo = 'ADMINISTRACION' OR tipo = 'ELECTRICIDAD' OR tipo = 'MOTOR' OR tipo = 'MECANICA' OR tipo = 'INTERIOR';
    END $$;


DROP TRIGGER IF EXISTS validar_tipo_vehiculo_insert;
DROP TRIGGER IF EXISTS validar_tipo_vehiculo_update;


delimiter $$;
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
    END $$;

delimiter $$;
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
    END $$;