use proyectoitv;

DROP function if exists validar_especialidad;

DELIMITER $$
CREATE FUNCTION validar_especialidad(especialidad VARCHAR(15)) RETURNS BOOLEAN
    BEGIN
        RETURN especialidad = 'CAMIÓN' OR especialidad = 'MOTOCICLETA' OR especialidad = 'TURISMO' OR especialidad = 'FURGONETA';
    END $$;


DROP TRIGGER IF EXISTS validar_especialidad_insert;
DROP TRIGGER IF EXISTS validar_especialidad_update;



delimiter $$;
CREATE TRIGGER validar_especialidad_insert
BEFORE INSERT ON tvehiculo
FOR EACH ROW
    BEGIN
        DECLARE error_message VARCHAR(30);
        IF NOT validar_especialidad(NEW.cTipoVehiculo) THEN
            SET error_message = CONCAT('El tipo de vehículo ', NEW.cTipoVehiculo, ' no es válido.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = error_message;
        end IF;
    END $$;

delimiter $$;
CREATE TRIGGER validar_especialidad_update
BEFORE UPDATE ON tvehiculo
FOR EACH ROW
    BEGIN
        DECLARE error_message VARCHAR(30);
        IF NOT validar_especialidad(NEW.cTipoVehiculo) THEN
            SET error_message = CONCAT('El tipo de vehículo ', NEW.cTipoVehiculo, ' no es válido.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = error_message;
        end IF;
    END $$;