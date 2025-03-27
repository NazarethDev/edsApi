CREATE TABLE Cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nomeCliente VARCHAR(255) NOT NULL,
    contatoCliente VARCHAR(255),
    emailCliente VARCHAR(255),
    dataCadastro DATETIME,
    dataAtualizacao DATETIME
);

CREATE TABLE Conserto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    descricaoProblema TEXT,
    arquivo VARCHAR(255),
    tempoDeUso VARCHAR(100),
    tipoAparelho VARCHAR(50),
    fabricante VARCHAR(50),
    status VARCHAR(50),
    dataSolicitacao DATETIME,
    dataAtualizacao DATETIME,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE Impressao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    arquivoImpressao VARCHAR(255),
    materialImpressao VARCHAR(50),
    dimensao VARCHAR(50),
    status VARCHAR(50),
    unidades INT,
    dataSolicitacao DATETIME,
    dataAtualizacao DATETIME,
    tipo VARCHAR(50),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE CriacaoDesign (
    id BIGINT PRIMARY KEY,
    ideiasDesign TEXT,
    arquivoReferencia VARCHAR(255),
    FOREIGN KEY (id) REFERENCES Impressao(id)
);

CREATE TABLE Software (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    detalhesServico TEXT,
    dispositivo VARCHAR(50),
    status VARCHAR(50),
    dataSolicitacao DATETIME,
    dataAtualizacao DATETIME,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE Software_Servicos (
    software_id BIGINT,
    servico VARCHAR(120),
    PRIMARY KEY (software_id, servico),
    FOREIGN KEY (software_id) REFERENCES Software(id)
);