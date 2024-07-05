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

INSERT INTO previsao (nome_cidade, data_cadastro, previsao_turno, previsao_tempo, temperatura_maxima, temperatura_minima, precipitacao, umidade, velocidade_do_vento)
SELECT
    c.nome_cidade,
    CURRENT_DATE - (random() * 365)::int AS data_cadastro,
    CASE
        WHEN random() < 0.33 THEN 'MANHA'
        WHEN random() < 0.66 THEN 'TARDE'
        ELSE 'NOITE'
    END AS previsao_turno,
    CASE
        WHEN random() < 0.15 THEN 'PARCIALMENTE_NUBLADO'
        WHEN random() < 0.25 THEN 'ENSOLARADO'
        WHEN random() < 0.40 THEN 'CHUVOSO'
        WHEN random() < 0.50 THEN 'VENTOSO'
        WHEN random() < 0.60 THEN 'TEMPESTUOSO'
        WHEN random() < 0.75 THEN 'NUBLADO'
        WHEN random() < 0.90 THEN 'NEVADO'
        ELSE 'LIMPO'
    END AS previsao_tempo,
    (random() * 40 + 20)::int AS temperatura_maxima,
    (random() * 20)::int AS temperatura_minima,
    (random() * 50)::int AS precipitacao,
    (random() * 100)::int AS umidade,
    (random() * 50)::int AS velocidade_do_vento
FROM
    (SELECT unnest(ARRAY['São Paulo', 'Rio de Janeiro', 'Belo Horizonte', 'Salvador', 'Curitiba', 'Fortaleza', 'Recife', 'Manaus', 'Porto Alegre', 'Brasília']) AS nome_cidade) AS c
CROSS JOIN generate_series(1, 100);

