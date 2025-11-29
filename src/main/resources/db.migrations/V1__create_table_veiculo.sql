create table veiculo
(
    id          int auto_increment primary key,
    nome        varchar(255)   not null,
    marca       varchar(255)   not null,
    cor         varchar(255)   not null,
    ano         varchar(255)   not null,
    placa       varchar(255)   not null,
    status      varchar(255)   not null,
    valorMaximo decimal(10, 2) not null,
    valorMinimo decimal(10, 2) not null,
);