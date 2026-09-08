# BFF Agendador

Backend for Frontend (BFF) do sistema de agendamento de tarefas. Expõe uma API unificada para o cliente e delega as operações aos microsserviços **usuario**, **agendador-tarefas** e **notificacao** via OpenFeign.

Também executa um **cron** que busca tarefas próximas do horário do evento e dispara notificações por e-mail.

## Tecnologias

- Java 17
- Spring Boot 4.1.1
- Spring Cloud OpenFeign
- SpringDoc OpenAPI (Swagger)
- Lombok
- Maven

## Arquitetura

```
Cliente / Swagger
       │
       ▼
  BFF Agendador (8083)
       │
       ├──► usuario (8080)        — cadastro, login, JWT
       ├──► agendador-tarefas (8081) — CRUD de tarefas
       └──► notificacao (8082)    — envio de e-mail
```

## Estrutura do projeto

```
bff-agendador/
├── bff-agendador/
│   ├── src/main/java/com/javanauta/bff_agendador/
│   │   ├── controller/          — UsuarioController, TarefasController
│   │   ├── business/            — services, DTOs, enums, CronService
│   │   └── infrastructure/
│   │       ├── client/          — Feign clients (Usuario, Tarefas, Email)
│   │       ├── config/          — OpenApiConfig
│   │       └── exception/       — GlobalExceptionHandler
│   └── src/main/resources/
│       └── application.properties
└── .github/workflows/
    └── maven.yml                — CI com Maven
```

## Pré-requisitos

- JDK 17+
- Maven 3.9+ (ou use o `./mvnw` incluído no projeto)
- Microsserviços em execução:
  - **usuario** na porta `8080`
  - **agendador-tarefas** na porta `8081`
  - **notificacao** na porta `8082`

## Configuração

Edite `bff-agendador/src/main/resources/application.properties`:

```properties
usuario.url=http://localhost:8080
tarefas.url=http://localhost:8081
notificacao.url=http://localhost:8082
server.port=8083

# Credenciais usadas pelo cron para autenticar no microsserviço usuario
usuario.email=seu-email@exemplo.com
usuario.senha=sua-senha

# Executa a cada 5 minutos
cron.tarefas=0 0/5 * * * *
```

> **Importante:** `usuario.email` e `usuario.senha` devem corresponder a um usuário **cadastrado** no microsserviço usuario. Não commite credenciais reais no repositório.

## Docker (stack completa)

Na pasta deste repositório (`bff-agendador`), com os projetos **usuario**, **agendador-tarefas** e **notificacao** ao lado:

```bash
docker compose up --build
```

Isso sobe:

| Serviço | Porta | Banco |
|---------|-------|--------|
| usuario | 8080 | Postgres (`5432`, db `agendadorTarefa`) |
| agendador-tarefas | 8081 | MongoDB (`27017`, db `db_agendador`) |
| notificacao | 8082 | — |
| bff-agendador | 8083 | — |

Swagger do BFF: http://localhost:8083/swagger-ui.html

As credenciais do cron (`usuario.email` / `usuario.senha`) podem ser sobrescritas com `USUARIO_EMAIL` e `USUARIO_SENHA` no ambiente ou em um arquivo `.env`.

## Como executar (local, sem Docker)

Entre na pasta do módulo Maven:

```bash
cd bff-agendador
```

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8083`.

## Swagger

- **UI:** http://localhost:8083/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8083/v3/api-docs

Para testar endpoints protegidos:

1. Faça login em `POST /usuario/login`
2. Copie o valor de `authorization` ou `token`
3. Clique em **Authorize** no Swagger e cole `Bearer <token>`

## Endpoints

### Usuário (`/usuario`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| POST | `/usuario` | Cadastrar usuário | Não |
| POST | `/usuario/login` | Login (retorna JWT) | Não |
| GET | `/usuario?email=` | Buscar por e-mail | Sim |
| GET | `/usuario/todos` | Listar todos | Sim |
| PUT | `/usuario` | Atualizar usuário | Sim |
| DELETE | `/usuario/{email}` | Deletar por e-mail | Sim |
| POST | `/usuario/endereco` | Cadastrar endereço | Sim |
| POST | `/usuario/telefone` | Cadastrar telefone | Sim |
| PUT | `/usuario/endereco?id=` | Atualizar endereço | Sim |
| PUT | `/usuario/telefone?id=` | Atualizar telefone | Sim |

### Tarefas (`/tarefas`)

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| POST | `/tarefas` | Gravar tarefa | Sim |
| GET | `/tarefas` | Listar tarefas do usuário logado | Sim |
| GET | `/tarefas/eventos?dataInicial=&dataFinal=` | Buscar por período | Sim |
| PUT | `/tarefas?id=` | Atualizar tarefa | Sim |
| PATCH | `/tarefas?status=&id=` | Alterar status de notificação | Sim |
| DELETE | `/tarefas?id=` | Deletar tarefa | Sim |

## Cron de notificação

O `CronService` roda automaticamente conforme `cron.tarefas` e:

1. Faz login no microsserviço **usuario** com `usuario.email` / `usuario.senha`
2. Busca tarefas **PENDENTES** com `dataEvento` entre **agora + 1h** e **agora + 1h + 5min**
3. Envia e-mail via microsserviço **notificacao**
4. Atualiza o status da tarefa para `NOTIFICADO`

### Exemplo de JSON para gravar tarefa (teste do cron)

```json
{
  "nomeTarefa": "Reunião com cliente",
  "descricao": "Apresentar proposta",
  "dataEvento": "02-09-2026 16:00:00"
}
```

Formato de `dataEvento`: `dd-MM-yyyy HH:mm:ss`

## CI

O workflow GitHub Actions (`.github/workflows/maven.yml`) executa `mvn clean verify` a cada push/PR na branch `master`.

## Build

```bash
cd bff-agendador
./mvnw clean verify
```
