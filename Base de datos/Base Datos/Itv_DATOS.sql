INSERT INTO testacion (nId_Estacion, cNombre, cDireccion, cTelefono, cCorreoElectronico)
VALUES (001, 'Estacion 001', 'C/Rioja, 7', 915874596, 'inspeccionamostucoche@gmail.com');

INSERT INTO tespecialidad (cNombre, nSalario)
VALUES ('ADMINISTRACION', 1650),
       ('ELECTRICIDAD', 1800),
       ('MOTOR', 1700),
       ('MECANICA', 1600),
       ('INTERIOR', 1750);

INSERT INTO ttrabajadores (nId_Trabajador, nId_Estacion, cNombreEspecialidad, cNombre, cTelefono, cCorreoElectronico, cNombreUsuario, cPassword)
VALUES (001, 001, 'ADMINISTRACION', 'Sonia', '625874952', 'soniagomez@gmail.com', 'soniagomez', ''),
       (002, 001, 'ELECTRICIDAD', 'Pablo', '652256984', 'pablomesas@hotmail.com', 'pablomesas', ''),
       (003, 001, 'MOTOR', 'Rocio', '696658974', 'rociofuente@gmail.com', 'rociofuente', ''),
       (004, 001, 'MECANICA', 'David', '623550485', 'daviddominguez@outlook.com', 'daviddominguez', ''),
       (005, 001, 'INTERIOR', 'Sergio', '656654147', 'sergioperez@gmail.com', 'sergioperez', ''),
       (006, 001, 'ELECTRICIDAD', 'Rosa', '655692418', 'rosaquintana@hotmail.com', 'rosaquintana', ''),
       (007, 001, 'MOTOR', 'German', '665210102', 'germansolis@gmail.com', 'germansolis', ''),
       (008, 001, 'MECANICA', 'Ruben', '654851596', 'rubenmoral@gmail.com', 'rubenmoral', ''),
       (009, 001, 'INTERIOR', 'Maria', '652225896', 'mariamoreno@outlook.com', 'mariamoreno', ''),
       (010, 001, 'ADMINISTRACION', 'Diego', '625148525', 'diegotorres@hotmail.com', 'diegotorres', ''),
       (011, 001, 'ELECTRICIDAD', 'Fran', '626535745', 'frangomez@gmail.com', 'frangomez', ''),
       (012, 001, 'MOTOR', 'Oscar', '698556263', 'oscargarcia@outlook.com', 'oscargarcia', ''),
       (013, 001, 'MECANICA', 'Rebeca', '663562665', 'rebecachacon@gmail.com', 'rebecachacon', ''),
       (014, 001, 'ELECTRICIDAD', 'Raquel', '651411203', 'raquelperez@gmail.com', 'raquelperez', ''),
       (015, 001, 'MECANICA', 'Alvaro', '658798520', 'alvaroroble@hotmail.com', 'alvaroroble', '');

INSERT INTO tpropietario (cDNI, cNombre, cApellidos, cTelefono, cCorreoElectronico)
VALUES ('54875241R', 'Sara', 'Gomez', '652258745', 'saragomez@gmail.com'),
       ('51487524T', 'Thales', 'Galan', '625014825', 'thalesgalan@gmail.com'),
       ('52638541M', 'Clara', 'Dominguez', '625487525', 'claradominguez@outlook.com'),
       ('53698524X', 'Gema', 'Paz', '652558952', 'gemapaz@hotmail.com'),
       ('52003258M', 'Daniel', 'Lopez', '625520312', 'daniellopez@gmail.com'),
       ('52485100C', 'Jose', 'Lozano', '620225415', 'joselozano@outlook.com'),
       ('53669854K', 'Laura', 'Sanchez', '625410201', 'laurasanchez@gmail.com'),
       ('52201587M', 'Sergio', 'Pozas', '659889962', 'sergiopozas@hotmail.com'),
       ('52255895X', 'Ivan', 'Yela', '633020021', 'ivanyela@hotmail.com'),
       ('58966512D', 'Mada', 'Sanz', '666410254', 'madasanz@gmail.com'),
       ('52487956R', 'Hugo', 'Navas', '632545215', 'hugonavas@outlook.com'),
       ('51448759J', 'Roman', 'Garcia', '655252123', 'romangarcia@gmail.com'),
       ('52148759P', 'Gloria', 'Benito', '663587520', 'gloriabenito@hotmail.com'),
       ('58745145V', 'Juan', 'Colon', '635548752', 'juancolon@gmail.com'),
       ('55214857E', 'Rosa', 'Perez', '699877741', 'rosaperez@outlook.com');

INSERT INTO tvehiculo (cMatricula, cDniPropietario, cMarca, cModelo, cTipoVehiculo, cTipoMotor, dFecha_Matriculacion, dFecha_UltimaRevision)
VALUES  ('5421GVJ', '54875241R', 'Alfa Romeo', 'Stelvio', 'TURISMO', 'GASOLINA', DATE('2017-02-25'), DATE('2022-06-23')),
        ('2385KLP', '51487524T', 'Ford', 'Transit', 'FURGONETA', 'HÍBRIDO', DATE('2019-08-12'), DATE('2023-04-15')),
        ('7843FBD', '52638541M', 'Mercedes-Benz', 'Actros', 'CAMIÓN', 'DIESEL', DATE('2016-05-10'), DATE('2021-11-30')),
        ('6521RDS', '53698524X', 'Yamaha', 'YZF-R6', 'MOTOCICLETA', 'GASOLINA', DATE('2020-09-02'), DATE('2023-01-18')),
        ('8796QWT', '52003258M', 'Volkswagen', 'Golf', 'TURISMO', 'HÍBRIDO', DATE('2018-12-07'), DATE('2022-08-09')),
        ('3254PLK', '52485100C', 'Renault', 'Kangoo', 'FURGONETA', 'ELÉCTRICO', DATE('2021-06-15'), DATE('2022-12-02')),
        ('4109LKJ', '53669854K', 'MAN', 'TGS', 'CAMIÓN', 'DIESEL', DATE('2015-03-20'), DATE('2020-09-25')),
        ('2156JHG', '52201587M', 'Honda', 'CBR600RR', 'MOTOCICLETA', 'GASOLINA', DATE('2009-10-30'), DATE('2019-04-07')),
        ('7531MNB', '52255895X', 'Peugeot', 'Partner', 'FURGONETA', 'HÍBRIDO', DATE('2012-01-14'), DATE('2018-05-31')),
        ('9874YBV', '58966512D', 'Scania', 'R500', 'CAMIÓN', 'DIESEL', DATE('2017-09-05'), DATE('2020-01-11')),
        ('3698VBN', '52487956R', 'BMW', 'R1250GS', 'MOTOCICLETA', 'GASOLINA', DATE('2021-02-18'), DATE('2022-06-29')),
        ('1452BNV', '51448759J', 'Toyota', 'Prius', 'TURISMO', 'HÍBRIDO', DATE('2018-07-23'), DATE('2021-11-12')),
        ('6543LKJ', '52148759P', 'Ford', 'Transit Custom', 'FURGONETA', 'ELÉCTRICO', DATE('2006-04-05'), DATE('2015-08-16')),
        ('7896KJP', '58745145V', 'Volvo', 'FH16', 'CAMIÓN', 'DIESEL', DATE('2016-12-10'), DATE('2022-04-21')),
        ('2587BVC', '55214857E', 'Kawasaki', 'Ninja 650', 'MOTOCICLETA', 'GASOLINA', DATE('2020-07-08'), DATE('2022-11-30')),
        ('9654QWR', '54875241R', 'Audi', 'A3', 'TURISMO', 'ELÉCTRICO', DATE('2019-03-17'), DATE('2021-07-25')),
        ('4578ZXC', '51487524T', 'Citroën', 'Berlingo', 'FURGONETA', 'HÍBRIDO', DATE('2021-10-22'), DATE('2023-03-14')),
        ('6325RTY', '52638541M', 'Iveco', 'Eurocargo', 'CAMIÓN', 'DIESEL', DATE('2015-07-29'), DATE('2022-12-01')),
        ('2156FGH', '53698524X', 'Suzuki', 'GSX-R750', 'MOTOCICLETA', 'GASOLINA', DATE('2020-12-03'), DATE('2023-04-09')),
        ('9654JKL', '52003258M', 'Hyundai', 'Kona', 'TURISMO', 'HÍBRIDO', DATE('2017-04-11'), DATE('2020-09-20')),
        ('3254TYW', '52485100C', 'Mercedes-Benz', 'Sprinter', 'FURGONETA', 'ELÉCTRICO', DATE('2011-11-28'), DATE('2017-04-05')),
        ('7845CVB', '53669854K', 'MAN', 'TGX', 'CAMIÓN', 'DIESEL', DATE('2016-08-19'), DATE('2023-02-24')),
        ('4569JHG', '54875241R', 'Harley-Davidson', 'Street Glide', 'MOTOCICLETA', 'GASOLINA', DATE('2019-01-27'), DATE('2022-06-02')),
        ('9652MNB', '51487524T', 'Volkswagen', 'Passat', 'TURISMO', 'HÍBRIDO', DATE('2017-06-06'), DATE('2022-10-14')),
        ('7845VBN', '52638541M', 'Nissan', 'NV200', 'FURGONETA', 'ELÉCTRICO', DATE('2020-02-15'), DATE('2021-06-27'));

INSERT INTO tcitas (nId_Trabajador, dFecha_Citacion, cMatricula)
VALUES (003, DATE('2023-06-06'), '9654JKL'),
       (011, DATE('2023-06-01'), '2587BVC'),
       (007, DATE('2023-06-23'), '3254PLK')