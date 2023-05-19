
CREATE TABLE if not exists tEstacion(
  nId_Estacion INTEGER(3) PRIMARY KEY NOT NULL ,
  cNombre VARCHAR(30) NOT NULL ,
  cDireccion VARCHAR(50) NOT NULL ,
  cTelefono VARCHAR(15),
  cCorreoElectronico VARCHAR(30)
);


CREATE TABLE if not exists tEspecialidad(
    cNombre VARCHAR(15) PRIMARY KEY NOT NULL ,
    nSalario INTEGER NOT NULL
);


CREATE TABLE if not exists tTrabajadores(
  nId_Trabajador INTEGER(3) PRIMARY KEY UNIQUE NOT NULL ,
  nId_Estacion INTEGER(3)   NOT NULL,
  cNombreEspecialidad VARCHAR(15)  NOT NULL,
  cNombre VARCHAR(15) NOT NULL ,
  nTelefono VARCHAR(15),
  cCorreoElectronico VARCHAR(30) UNIQUE NOT NULL ,
  cNombreUsuario VARCHAR(15) UNIQUE NOT NULL ,
  cPassword VARCHAR(20) NOT NULL,

  FOREIGN KEY (nId_Estacion) REFERENCES tEstacion(nId_Estacion)
                                        ON UPDATE CASCADE
                                        ON DELETE RESTRICT,
  FOREIGN KEY (cNombreEspecialidad) REFERENCES tEspecialidad(cNombre)
                                        ON UPDATE CASCADE
                                        ON DELETE RESTRICT
);


CREATE TABLE if not exists tPropietario(
    cDNI CHAR(9) PRIMARY KEY UNIQUE NOT NULL ,
    cNombre VARCHAR(15) NOT NULL ,
    cApellidos VARCHAR(30) NOT NULL,
    cTelefono VARCHAR(15),
    cCorreoElectronico VARCHAR(30)
);


CREATE TABLE if not exists  tVehiculo(
    cMatricula CHAR(7) PRIMARY KEY UNIQUE NOT NULL ,
    cDniPropietario CHAR(9) NOT NULL ,
    cMarca VARCHAR(20) NOT NULL ,
    cModelo VARCHAR(20) NOT NULL ,
    cTipoVehiculo VARCHAR(15) NOT NULL ,
    cTipoMotor VARCHAR(10) NOT NULL ,
    dFecha_Matriculacion DATE NOT NULL,
    dFecha_UltimaRevision DATE NOT NULL,

    FOREIGN KEY (cDniPropietario) REFERENCES tPropietario(cDNI)
                                     ON UPDATE CASCADE
                                     ON DELETE RESTRICT
);


CREATE TABLE if not exists tCitas(
  nId_Trabajador INTEGER(3)  UNIQUE NOT NULL ,
  dFecha_Citacion DATE  NOT NULL,
  cMatricula CHAR(7) NOT NULL ,
  PRIMARY KEY (nId_Trabajador,dFecha_Citacion,cMatricula),

  FOREIGN KEY (nId_Trabajador) REFERENCES tTrabajadores(nId_Trabajador)
                                 ON UPDATE CASCADE
                                 ON DELETE RESTRICT ,
  FOREIGN KEY (cMatricula) REFERENCES  tVehiculo(cMatricula)
                                 ON UPDATE CASCADE
                                 ON DELETE RESTRICT
);

