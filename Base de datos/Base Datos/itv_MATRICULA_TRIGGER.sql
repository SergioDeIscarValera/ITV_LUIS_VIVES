use proyectoitv;

DROP FUNCTION IF EXISTS validar_matricula;

DELIMITER $$
CREATE FUNCTION validar_matricula(matricula CHAR(7)) RETURNS BOOLEAN
    BEGIN
        RETURN matricula REGEXP '^[0-9]{4}[B-DF-HJ-NP-TV-Z]{3}$';
    END $$;


DROP TRIGGER IF EXISTS validar_matricula_insert_vehiculo;
DROP TRIGGER IF EXISTS validar_matricula_update_vehiculo;
DROP TRIGGER IF EXISTS validar_matricula_insert_citas;
DROP TRIGGER IF EXISTS validar_matricula_update_citas;


delimiter $$;
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
    END $$;

delimiter $$;
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
    END $$;

delimiter $$;
CREATE TRIGGER validar_matricula_insert_citas
BEFORE INSERT ON tcitas
FOR EACH ROW
    BEGIN
        DECLARE error_message VARCHAR(30);
        IF NOT validar_matricula(NEW.cMatricula) THEN
            SET error_message = CONCAT('La matrícula ', NEW.cMatricula, ' no es válida.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = error_message;
        end IF;
    END $$;

delimiter $$;
CREATE TRIGGER validar_matricula_update_citas
BEFORE UPDATE ON tcitas
FOR EACH ROW
    BEGIN
        DECLARE error_message VARCHAR(30);
        IF NOT validar_matricula(NEW.cMatricula) THEN
            SET error_message = CONCAT('La matrícula ', NEW.cMatricula, ' no es válida.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = error_message;
        end IF;
    END $$;