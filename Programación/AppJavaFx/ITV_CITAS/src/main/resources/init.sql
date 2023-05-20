CREATE TABLE IF NOT EXISTS tAppointment(
    nIdAppointment int auto_increment,
    nIdEmployee int not null,
    cCarNumber varchar(8) not null,
    dDate timestamp not null,
    PRIMARY KEY (nIdAppointment,nIdEmployee, cCarNumber, dDate)
);

INSERT INTO tAppointment(nIdEmployee, cCarNumber, dDate) VALUE
    (2, '1712 MDB', '2023-05-10 10:00:00'),
    (1, '2176 HDZ', '2023-05-19 10:15:27'),
    (1, '2176 HDZ', '2023-05-15 10:20:27');