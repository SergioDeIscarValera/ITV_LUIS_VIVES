
use proyectoitv;

DROP PROCEDURE IF EXISTS ListarPlantilla;

delimiter $$;
CREATE PROCEDURE ListarPlantilla (estacionParam INTEGER(3))

BEGIN

    DECLARE done BOOLEAN;
    DECLARE line BLOB DEFAULT '';

    DECLARE id_trabajador INTEGER(3);
    DECLARE id_estacion INTEGER(3);
    DECLARE especialidad VARCHAR(15);
    DECLARE nombre VARCHAR(15);
    DECLARE telefono VARCHAR(15);
    DECLARE correo VARCHAR(30);
    DECLARE usuario VARCHAR(15);
    DECLARE cPassword VARCHAR(20);
    DECLARE id_responsable INTEGER(3);

    DECLARE cursor1 cursor for
    SELECT * FROM ttrabajadores WHERE nId_Estacion = estacionParam;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = true;

    IF done THEN
        SET line = 'No hay trabajadores en la estación indicada';
    ELSE

        OPEN cursor1;
        loop_lista:LOOP
            FETCH cursor1 INTO id_trabajador, id_estacion, id_responsable, especialidad, nombre, telefono, correo, usuario, cPassword;
            IF done THEN
                LEAVE loop_lista;
            end if;
            SET line = CONCAT(line, 'TRABAJADOR --> ID: ', id_trabajador, ', ID_EST.: ', id_estacion, ', ID_RESP.: ', id_responsable, ', ESPEC.: ', especialidad, ', NOMBRE: ', nombre, ', TLF: ', telefono, ', CORREO: ', correo, ', USUARIO: ', usuario, ', CONTRASEÑA: ', cPassword, '\n');
        END LOOP;
        CLOSE cursor1;
    END IF;
    SELECT line as 'Result';
END;
$$;


CALL ListarPlantilla(1)
