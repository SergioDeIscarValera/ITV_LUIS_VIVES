CREATE EVENT borrado_bimestral
ON SCHEDULE
    EVERY 2 MONTH
    STARTS '2023-01-01 00:00:00' -- Ajustado para que empiece a borrar el 1 de enero.
DO
    DELETE FROM tCitas