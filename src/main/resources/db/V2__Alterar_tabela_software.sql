ALTER TABLE Software
    MODIFY COLUMN dispositivo VARCHAR(50) NOT NULL,       -- Garantir que o dispositivo seja armazenado como VARCHAR
    MODIFY COLUMN status VARCHAR(50) NOT NULL;             -- Garantir que o status seja armazenado como VARCHAR

CREATE TABLE IF NOT EXISTS Software_Servicos (
    software_id BIGINT,
    servico VARCHAR(120) NOT NULL,      -- Garantir que servicos sejam armazenados como VARCHAR
    PRIMARY KEY (software_id, servico),
    FOREIGN KEY (software_id) REFERENCES Software(id) ON DELETE CASCADE
);

ALTER TABLE Software_Servicos
    ADD CONSTRAINT FK_Software_Servicos_Software
    FOREIGN KEY (software_id) REFERENCES Software(id)
    ON DELETE CASCADE;
