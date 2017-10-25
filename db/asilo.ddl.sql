create table Paciente (
    id int unsigned AUTO_INCREMENT,
    nombre varchar(200),
    fechaNacimiento date,
    sexo char(1),
    estado varchar(50),
    idSeguro int unsigned,
    idServicioEmergencia int unsigned,
    PRIMARY KEY(id)
);

create table Padecimientos(
    id int unsigned AUTO_INCREMENT,
    idPaciente int unsigned,
    padecimiento varchar(100),
    PRIMARY KEY(id),
    FOREIGN KEY(idPaciente) REFERENCES Paciente(id)
);

create table Cuarto(
    id int unsigned AUTO_INCREMENT,
    numCuarto int unsigned,
    PRIMARY KEY(id)
);

create table Cama(
    id int unsigned AUTO_INCREMENT,
    numCama int unsigned,
    idPaciente int unsigned,
    idCuarto int unsigned,
    PRIMARY KEY(id),
    FOREIGN KEY(idPaciente) references Paciente(id),
    FOREIGN KEY(idCuarto) references Cuarto(id)
);

create table Seguro(
    id int unsigned AUTO_INCREMENT,
    nombre varchar(100),
    numPoliza varchar(100),
    PRIMARY KEY(id)
);

create table ServicioEmergencia(
    id int unsigned AUTO_INCREMENT,
    nombre varchar(200),
    telefono varchar(20),
    direccion varchar(300),
    PRIMARY KEY(id)
);

alter table Paciente 
add FOREIGN KEY(idSeguro) references Seguro(id);

alter table Paciente
add FOREIGN KEY(idServicioEmergencia) references ServicioEmergencia(id);

create table FamiliarResponsable(
    id int unsigned AUTO_INCREMENT,
    nombre varchar(100),
    relacion varchar(30),
    telefono varchar(20),
    idPaciente int unsigned,
    PRIMARY KEY(id),
    FOREIGN KEY(idPaciente) references Paciente(id)
);

create table Enfermero(
    id int unsigned AUTO_INCREMENT,
    nombre varchar(100),
    PRIMARY KEY(id)
);

create table Evento(
    id int unsigned AUTO_INCREMENT,
    asunto varchar(200),
    descripcion varchar(2000),
    estaHospitalito char(1),
    avisoFamiliar char(1),
    requirioConsulta char(1),
    fecha date,
    idPaciente int unsigned,
    idEnfermero int unsigned,
    PRIMARY KEY(id),
    FOREIGN KEY(idPaciente) references Paciente(id),
    FOREIGN KEY(idEnfermero) references Enfermero(id)
);

create table Medicamento(
    id int unsigned AUTO_INCREMENT,
    dosis int,
    medidaDosis varchar(10),
    nombreGenerico varchar(200),
    PRIMARY KEY(id)
);

create table PacienteMedicamento(
    idPaciente int unsigned,
    idMedicamento int unsigned,
    tomaManana varchar(1),
    tomaMedio varchar(1),
    tomaTarde varchar(1),
    fechaInicio date,
    duracion int,
    PRIMARY KEY(idPaciente, idMedicamento),
    FOREIGN KEY(idPaciente) references Paciente(id),
    FOREIGN KEY(idMedicamento) references Medicamento(id)
);

create table EnvaseMedicina(
    id int unsigned AUTO_INCREMENT,
    nombreComercial varchar(200),
    fechaSurtimiento date,
    presentacion varchar(30),
    cantidad int,
    idMedicamento int unsigned,
    idPaciente int unsigned,
    PRIMARY KEY(id),
    FOREIGN KEY(idMedicamento) references Medicamento(id),
    FOREIGN KEY(idPaciente) references Paciente(id)
);

