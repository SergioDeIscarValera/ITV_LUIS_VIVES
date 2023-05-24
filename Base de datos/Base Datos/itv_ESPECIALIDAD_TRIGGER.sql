use proyectoitv;

DROP function if exists validar_especialidad;

DELIMITER $$
CREATE FUNCTION validar_especialidad(especialidad VARCHAR(15)) RETURNS BOOLEAN
    BEGIN
        RETURN especialidad = 'ADMINISTRACION' OR especialidad = 'ELECTRICIDAD' OR especialidad = 'MOTOR' OR especialidad = 'MECANICA' OR especialidad = 'INTERIOR';
    END $$;


DROP TRIGGER IF EXISTS validar_especialidad_insert;
DROP TRIGGER IF EXISTS validar_especialidad_update;



delimiter $$;
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
    END $$;

delimiter $$;
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
    END $$;