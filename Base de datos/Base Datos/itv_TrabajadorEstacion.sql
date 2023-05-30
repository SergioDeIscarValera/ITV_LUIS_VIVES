use itvprueba;
DESCRIBE ttrabajadores;
DESCRIBE testacion;
CREATE FUNCTION trabajador_pertenece_estación(
    IN nombreUsuario VARCHAR(100),
    IN idEstación INT
) RETURNS BOOLEAN
BEGIN
   RETURN idEstación = (SELECT nId_Estacion FROM ttrabajadores WHERE cNombre = nombreUsuario);
END;