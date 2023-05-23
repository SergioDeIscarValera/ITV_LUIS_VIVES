use proyectoitv;

DROP TRIGGER IF EXISTS avisoActualizacionCita;
DROP TABLE IF EXISTS log;

CREATE TABLE log(
    matricula CHAR(7),
    oldFecha TIMESTAMP,
    oldTrabajador INTEGER(3),
    newFecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    newTrabajador INTEGER(3)
);

delimiter $$;
CREATE TRIGGER avisoActualizacionCita
AFTER UPDATE ON tCitas
FOR EACH ROW
    BEGIN
        INSERT INTO log VALUES (NEW.cMatricula, OLD.dFecha_Citacion, OLD.nId_Trabajador, NEW.dFecha_Citacion, NEW.nId_Trabajador);
    END;
$$;

