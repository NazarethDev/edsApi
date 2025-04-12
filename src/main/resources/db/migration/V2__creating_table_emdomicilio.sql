CREATE TABLE domiciliar (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    logradouro VARCHAR(255),
    numero_casa VARCHAR(50),
    cep VARCHAR(20),
    complemento VARCHAR(255),
    periodo VARCHAR(50),
    data DATE,
    tipo_entidade VARCHAR(50) DEFAULT 'visitaTecnica'
);

ALTER TABLE conserto
ADD COLUMN domicilio_id BIGINT,
ADD CONSTRAINT fk_conserto_domiciliar FOREIGN KEY (domicilio_id) REFERENCES domiciliar(id);

ALTER TABLE software
ADD COLUMN domicilio_id BIGINT,
ADD CONSTRAINT fk_software_domiciliar FOREIGN KEY (domicilio_id) REFERENCES domiciliar(id);