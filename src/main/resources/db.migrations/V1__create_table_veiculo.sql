CREATE TABLE tbl_veiculo
(
    id           BIGINT         NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(255)   NOT NULL,
    marca        VARCHAR(255)   NOT NULL,
    cor          VARCHAR(255)   NOT NULL,
    ano          VARCHAR(10)    NOT NULL,
    placa        VARCHAR(255)   NOT NULL,
    valor_minimo DECIMAL(19, 2) NOT NULL,
    valor_maximo DECIMAL(19, 2) NOT NULL,
    status       VARCHAR(50)    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_tbl_veiculo_placa (placa)
);

INSERT INTO tbl_veiculo (nome,
                         marca,
                         cor,
                         ano,
                         placa,
                         valor_maximo,
                         valor_minimo,
                         status)
VALUES ('Civic EX', 'Honda', 'Preto', '2020', 'ABC1D23', 120000.00, 95000.00, "ATIVO"),
       ('Corolla GLI', 'Toyota', 'Branco', '2021', 'DEF2G34', 125000.00, 98000.00, "ATIVO"),
       ('Onix LT', 'Chevrolet', 'Prata', '2019', 'GHI3J45', 70000.00, 52000.00, "ATIVO"),
       ('Gol 1.6', 'Volkswagen', 'Vermelho', '2018', 'JKL4M56', 55000.00, 38000.00, "ATIVO"),
       ('HB20 Comfort', 'Hyundai', 'Azul', '2022', 'MNO5P67', 95000.00, 73000.00, "ATIVO");