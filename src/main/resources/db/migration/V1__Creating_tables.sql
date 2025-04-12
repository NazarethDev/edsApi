CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_cliente VARCHAR(255) NOT NULL,
    contato_cliente VARCHAR(255),
    email_cliente VARCHAR(255),
    data_cadastro DATETIME,
    data_atualizacao DATETIME
);

CREATE TABLE conserto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    descricao_problema TEXT,
    arquivo VARCHAR(255),
    tempo_de_uso VARCHAR(50),
    tipo_aparelho VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    status ENUM('NOVO', 'CLIENTE_CONTATADO', 'EM_ESPERA', 'PEDIDO_CONFIRMADO', 'PROCESSANDO', 'AGUARDA_RETIRADA', 'ENTREGA_SOLICITADA', 'FINALIZADO', 'CANCELADO'),
    data_solicitacao DATETIME,
    data_atualizacao DATETIME,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE impressao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    arquivo_impressao VARCHAR(255),
    material_impressao VARCHAR(100) NOT NULL,
    status ENUM('Novo', 'Cliente contatato', 'Em espera', 'Pedido confirmado', 'Processando', 'Aguarda retirada', 'Pronto para entrega', 'Finalizado', 'Cancelado'),
    unidades INT,
    data_solicitacao DATETIME,
    data_atualizacao DATETIME,
    tipo VARCHAR(50) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE criacao_design (
    id BIGINT PRIMARY KEY,
    ideias_design TEXT,
    arquivo_referencia VARCHAR(255),
    FOREIGN KEY (id) REFERENCES impressao(id)
);

CREATE TABLE software (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    detalhes_servico TEXT,
    dispositivo VARCHAR(100) NOT NULL,
    status ENUM('NOVO', 'CLIENTE_CONTATADO', 'EM_ESPERA', 'PEDIDO_CONFIRMADO', 'PROCESSANDO', 'AGUARDA_RETIRADA', 'ENTREGA_SOLICITADA', 'FINALIZADO', 'CANCELADO'),
    data_solicitacao DATETIME,
    data_atualizacao DATETIME,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE software_servicos (
    software_id BIGINT,
    servico VARCHAR(100) NOT NULL,
    PRIMARY KEY (software_id, servico),
    FOREIGN KEY (software_id) REFERENCES software(id)
);