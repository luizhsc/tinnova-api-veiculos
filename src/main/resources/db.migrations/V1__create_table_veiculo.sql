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