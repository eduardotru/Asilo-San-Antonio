ALTER TABLE Asilo.Paciente
ADD numCuarto int unsigned;

ALTER TABLE Asilo.Paciente
ADD numCama int unsigned;

DROP TABLE Asilo.Cuarto;

DROP TABLE Asilo.Cama;

ALTER TABLE Asilo.Paciente
ADD numeroReferencia varchar(50);