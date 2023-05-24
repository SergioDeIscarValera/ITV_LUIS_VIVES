use proyectoitv;


DROP TRIGGER IF EXISTS validar_fechas_insert;
DROP TRIGGER IF EXISTS validar_fechas_update;
DROP TRIGGER IF EXISTS validar_fecha_cita_insert;
DROP TRIGGER IF EXISTS validar_fecha_cita_update;


delimiter $$;
CREATE TRIGGER validar_fechas_insert
BEFORE INSERT ON tvehiculo
FOR EACH ROW
    BEGIN
        IF (NEW.dFecha_Matriculacion > NEW.dFecha_UltimaRevision) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha de matriculación debe ser menor que la fecha de la última revisión.';
        end IF;
    END $$;

delimiter $$;
CREATE TRIGGER validar_fechas_update
BEFORE UPDATE ON tvehiculo
FOR EACH ROW
    BEGIN
        IF (NEW.dFecha_Matriculacion > NEW.dFecha_UltimaRevision) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha de matriculación debe ser menor que la fecha de la última revisión.';
        end IF;
    END $$;

delimiter $$;
CREATE TRIGGER validar_fecha_cita_insert
BEFORE INSERT ON tcitas
FOR EACH ROW
    BEGIN
        IF (NEW.dFecha_Citacion < NOW()) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha de la cita debe ser futura.';
        end IF;
    END $$;

delimiter $$;
CREATE TRIGGER validar_fecha_cita_update
BEFORE UPDATE ON tcitas
FOR EACH ROW
    BEGIN
        IF (NEW.dFecha_Citacion < NOW()) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La fecha de la cita debe ser futura.';
        end IF;
    END $$;