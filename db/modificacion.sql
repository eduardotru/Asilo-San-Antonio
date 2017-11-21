ALTER TABLE Asilo.Paciente
ADD numCuarto int unsigned;

ALTER TABLE Asilo.Paciente
ADD numCama int unsigned;

DROP TABLE Asilo.Cuarto;

DROP TABLE Asilo.Cama;

ALTER TABLE Asilo.Paciente
ADD numeroReferencia varchar(50);

ALTER TABLE Asilo.PacienteMedicamento
ADD dosis int;

ALTER TABLE Asilo.Medicamento
DROP COLUMN dosis;