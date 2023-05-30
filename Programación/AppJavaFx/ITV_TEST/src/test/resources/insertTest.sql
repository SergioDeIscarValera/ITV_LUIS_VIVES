INSERT INTO tEstacion (nId_Estacion, cNombre, cDireccion, cTelefono, cCorreoElectronico)
VALUES (001, 'Estacion 001', 'C/Rioja, 7', 915874596, 'inspeccionamostucoche@gmail.com');

INSERT INTO tEspecialidad (cNombre, nSalario)
VALUES ('ADMINISTRACION', 1650),
       ('ELECTRICIDAD', 1800),
       ('MOTOR', 1700),
       ('MECANICA', 1600),
       ('INTERIOR', 1750);

INSERT INTO tTrabajadores (nId_Trabajador, nId_Estacion, cNombreEspecialidad, cNombre, cTelefono, cCorreoElectronico, cNombreUsuario, cPassword, nId_Responsable)
VALUES (001, 001, 'ADMINISTRACION', 'Sonia', '625874952', 'soniagomez@gmail.com', 'soniagomez', '', 001),
       (002, 001, 'ELECTRICIDAD', 'Pablo', '652256984', 'pablomesas@hotmail.com', 'pablomesas', '', 001),
       (003, 001, 'MOTOR', 'Rocio', '696658974', 'rociofuente@gmail.com', 'rociofuente', '', 001),
       (004, 001, 'ELECTRICIDAD', 'Raquel', '651411203', 'raquelperez@gmail.com', 'raquelperez', '', 001),
       (005, 001, 'MECANICA', 'Alvaro', '658798520', 'alvaroroble@hotmail.com', 'alvaroroble', '', 001);

INSERT INTO tPropietario (cDNI, cNombre, cApellidos, cTelefono, cCorreoElectronico)
VALUES ('54875241R', 'Sara', 'Gomez', '652258745', 'saragomez@gmail.com'),
       ('51487524T', 'Thales', 'Galan', '625014825', 'thalesgalan@gmail.com'),
       ('55214857E', 'Rosa', 'Perez', '699877741', 'rosaperez@outlook.com');

INSERT INTO tVehiculo (cMatricula, cDniPropietario, cMarca, cModelo, cTipoVehiculo, cTipoMotor, dFecha_Matriculacion, dFecha_UltimaRevision)
VALUES  ('5421GVJ', '54875241R', 'Alfa Romeo', 'Stelvio', 'TURISMO', 'GASOLINA', DATE('2017-02-25'), TIMESTAMP ('2022-06-23 12:47:29')),
        ('2385KLP', '54875241R', 'Ford', 'Transit', 'FURGONETA', 'HIBRIDO', DATE('2019-08-12'), TIMESTAMP('2023-04-15 10:52:02')),
        ('7843FBD', '51487524T', 'Mercedes-Benz', 'Actros', 'CAMION', 'DIESEL', DATE('2016-05-10'), TIMESTAMP('2021-11-30 11:44:56')),
        ('6521RDS', '55214857E', 'Yamaha', 'YZF-R6', 'MOTOCICLETA', 'GASOLINA', DATE('2020-09-02'), TIMESTAMP('2023-01-18 10:43:34')),
        ('8796QWT', '55214857E', 'Volkswagen', 'Golf', 'TURISMO', 'HIBRIDO', DATE('2018-12-07'), TIMESTAMP('2022-08-09 18:31:15'));

INSERT INTO tCitas (nId_Trabajador, dFecha_Citacion, cMatricula)
VALUES (003, TIMESTAMP('2023-06-06 14:45:51'), '5421GVJ'),
       (002, TIMESTAMP('2023-06-01 08:49:04'), '2385KLP'),
       (004, TIMESTAMP('2023-06-23 12:14:52'), '8796QWT');