# Barber API

API REST para gerenciamento de clientes e agendamentos de uma barbearia, desenvolvida com Spring Boot, Spring Web MVC, Spring Data JPA e PostgreSQL.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Maven Wrapper
- Lombok

## Funcionalidades

- Cadastro, listagem, atualizacao e remocao de clientes
- Criacao e gestao de agendamentos
- Cancelamento e finalizacao de agendamentos
- Consulta de agendamentos ativos
- Consulta de agendamentos do dia
- Consulta de horarios disponiveis por data

## Regras de negocio atuais

- O agendamento nao pode ser criado para uma data no passado
- O horario de atendimento vai de `09:00` ate `19:00`
- Nao e permitido criar dois agendamentos no mesmo `dataHora`
- Apenas agendamentos com status `AGENDADO` aparecem nas rotas de ativos e do dia
- Um agendamento cancelado nao pode ser finalizado
- Um agendamento finalizado nao pode ser cancelado

## Configuracao do banco

As propriedades atuais estao em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/barber-api
spring.datasource.username=postgres
spring.datasource.password=123
spring.jpa.hibernate.ddl-auto=update
```

Antes de subir a aplicacao, crie um banco PostgreSQL chamado `barber-api`.

## Como executar

### Pre-requisitos

- Java 21 instalado
- PostgreSQL em execucao

### Passos

1. Ajuste as credenciais do banco em `src/main/resources/application.properties`, se necessario.
2. Crie o banco `barber-api` no PostgreSQL.
3. Execute a aplicacao:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Por padrao, a API sobe em:

```text
http://localhost:8080
```

## Modelo de dados

### Cliente

```json
{
  "id": 1,
  "nome": "Roberto",
  "telefone": "11999999999"
}
```

### Agendamento

Entidade persistida:

```json
{
  "id": 1,
  "dataHora": "2026-05-20T10:00:00",
  "cliente": {
    "id": 1
  },
  "status": "AGENDADO"
}
```

Resposta da API:

```json
{
  "id": 1,
  "dataHora": "2026-05-20T10:00:00",
  "clienteNome": "Roberto",
  "clienteTelefone": "11999999999"
}
```

## Endpoints

### Clientes

#### `GET /clientes`

Lista todos os clientes.

#### `GET /clientes/{id}`

Busca um cliente por ID.

#### `POST /clientes`

Cria um novo cliente.

Body:

```json
{
  "nome": "Roberto",
  "telefone": "11999999999"
}
```

Validacoes:

- `nome`: obrigatorio, entre 3 e 120 caracteres
- `telefone`: obrigatorio, entre 9 e 11 caracteres

#### `PUT /clientes/{id}`

Atualiza um cliente existente.

Body:

```json
{
  "nome": "Roberto Atualizado",
  "telefone": "11988887777"
}
```

#### `DELETE /clientes/{id}`

Remove um cliente.

### Agendamentos

#### `GET /agendamentos`

Lista todos os agendamentos.

#### `GET /agendamentos/{id}`

Busca um agendamento por ID.

#### `GET /agendamentos/ativos`

Lista apenas agendamentos com status `AGENDADO`.

#### `GET /agendamentos/hoje`

Lista os agendamentos `AGENDADO` da data atual do servidor.

#### `GET /agendamentos/disponiveis?data=2026-05-20`

Retorna os horarios livres do dia informado.

Horarios considerados pela aplicacao:

```text
09:00, 10:00, 11:00, 12:00, 13:00, 14:00, 15:00, 16:00, 17:00, 18:00, 19:00
```

#### `POST /agendamentos`

Cria um novo agendamento.

Body:

```json
{
  "clienteId": 1,
  "dataHora": "2026-05-20T10:00:00"
}
```

#### `PUT /agendamentos/{id}`

Atualiza data/hora e cliente do agendamento.

Body:

```json
{
  "clienteId": 1,
  "dataHora": "2026-05-20T11:00:00"
}
```

#### `PATCH /agendamentos/{id}/cancelar`

Altera o status do agendamento para `CANCELADO`.

#### `PATCH /agendamentos/{id}/finalizar`

Altera o status do agendamento para `FINALIZADO`.

## Status de agendamento

Os valores possiveis sao:

- `AGENDADO`
- `CANCELADO`
- `FINALIZADO`

## Tratamento de erros atual

A API ja trata estas excecoes com resposta `400 Bad Request`:

- horario indisponivel
- data invalida

Formato de resposta:

```json
{
  "erro": "Mensagem do erro"
}
```

## Pontos em aberto no projeto

- `AuthController` existe, mas ainda nao possui endpoints implementados
- `BarbeiroController` existe, mas ainda nao possui endpoints implementados
- Nao ha documentacao Swagger/OpenAPI configurada
- Algumas excecoes como busca por ID inexistente ainda nao possuem tratamento global

## Testes

Para rodar os testes:

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```
