# DB-desafio-weatherapp-back

Este é o repositório do backend da aplicação WeatherApp, responsável pela gestão das previsões meteorológicas.

## Pré-requisitos

Certifique-se de ter instalado em sua máquina:

-   Docker
-   Java 16
-   Maven

## Configuração do Ambiente

### 1. Clonando o Repositório

Clone o repositório para a sua máquina local:

```bash
git clone https://github.com/jonasdspereira/DB-desafio-weatherapp-back.git
cd DB-desafio-weatherapp-back
```

### 2. Configuração do Banco de Dados

A aplicação utiliza PostgreSQL e Flyway para gerenciamento de banco de dados. Certifique-se de ter o PostgreSQL instalado e configurado. Verifique e ajuste as configurações de conexão no arquivo `src/main/resources/application.properties`.

### 3. Executando a Aplicação com Docker

A aplicação pode ser executada utilizando Docker para facilitar a configuração do ambiente de desenvolvimento. Certifique-se de ter o Docker instalado.

```bash
docker-compose up -d --build
```


### 4. Executando a Aplicação sem Docker

Para executar a aplicação localmente sem Docker, certifique-se de ter o Maven instalado e execute:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

## Executando Testes

Os testes unitários são executados com JUnit e integrados com o Maven. Para executar os testes, utilize:

```bash
mvn test
```

Os relatórios de cobertura de código (Jacoco) estarão disponíveis após a execução dos testes na pasta `target/site/jacoco/index.html`.

## Documentação da API (Swagger)

A API está documentada com Swagger para facilitar o entendimento e teste dos endpoints. Após iniciar a aplicação, acesse:

```bash
http://localhost:8080/swagger-ui.html
```

## Estrutura do Projeto

-   **src/main/java**: Contém o código-fonte da aplicação.
    -   **controllers**: Controladores REST.
    -   **dto**: DTOs (Data Transfer Objects) utilizados na API.
    -   **entities**: Entidades JPA que representam as tabelas do banco de dados.
    -   **enums**: Enumerações utilizadas na aplicação.
    -   **exceptions**: Exceções customizadas da aplicação.
    -   **mapper**: Mappers para conversão entre entidades e DTOs.
    -   **repositories**: Interfaces de repositório JPA.
    -   **services**: Implementações de serviços.
-   **src/main/resources**: Recursos da aplicação.
    -   **migration**: Scripts de migração do Flyway para controle de versão do banco de dados.
    -   **application.properties**: Configurações da aplicação.
-   **src/test/java**: Contém os testes unitários da aplicação.
    -   **controllers**: Testes dos controladores.
    -   **repositories**: Testes dos repositórios JPA.
    -   **services**: Testes dos serviços.
