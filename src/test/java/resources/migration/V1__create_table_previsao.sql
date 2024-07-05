DROP TABLE IF EXISTS previsao;
CREATE TABLE previsao (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    nome_cidade VARCHAR(30) NOT NULL,
    data_cadastro DATE NOT NULL,
    previsao_turno VARCHAR(255),
    previsao_tempo VARCHAR(255),
    temperatura_maxima INT NOT NULL,
    temperatura_minima INT NOT NULL,
    precipitacao INT NOT NULL,
    umidade INT NOT NULL,
    velocidade_do_vento INT NOT NULL,
    CONSTRAINT CHK_nome_cidade CHECK (CHAR_LENGTH(nome_cidade) >= 2 AND CHAR_LENGTH(nome_cidade) <= 30)
);

