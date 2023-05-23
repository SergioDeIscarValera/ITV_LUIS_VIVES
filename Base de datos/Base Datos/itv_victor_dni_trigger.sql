use proyectoitv;

DROP FUNCTION IF EXISTS validar_dni;

DELIMITER $$
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
    END $$;



DROP TRIGGER IF EXISTS validar_dni_insert_tpropietario;
DROP TRIGGER IF EXISTS validar_dni_update_tpropietario;
DROP TRIGGER IF EXISTS validar_dni_insert_tvehiculo;
DROP TRIGGER IF EXISTS validar_dni_update_tvehiculo;


delimiter $$;
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
    END $$;

delimiter $$;
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
    END $$;

    delimiter $$;
CREATE TRIGGER validar_dni_insert_tvehiculo
BEFORE INSERT ON tvehiculo
FOR EACH ROW
    BEGIN
        DECLARE error_message VARCHAR(30);
        IF NOT validar_dni(NEW.cDniPropietario) THEN
            SET error_message = CONCAT('El DNI ', NEW.cDniPropietario, ' no es válido.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = error_message;
        end IF;
    END $$;

    delimiter $$;
CREATE TRIGGER validar_dni_update_tvehiculo
BEFORE UPDATE ON tvehiculo
FOR EACH ROW
    BEGIN
        DECLARE error_message VARCHAR(30);
        IF NOT validar_dni(NEW.cDniPropietario) THEN
            SET error_message = CONCAT('El DNI ', NEW.cDniPropietario, ' no es válido.');
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = error_message;
        end IF;
    END $$;