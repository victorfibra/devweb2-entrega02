# Sistema de Gerenciamento de Contratos de Internet

## Disciplina
Desenvolvimento Web II – PPGTI / UFRN  
Prof. Dr. Jean Mário Moreira de Lima

---

## Descrição do Projeto

Este projeto consiste no desenvolvimento de uma API REST utilizando Spring Boot e Spring Data JPA para gerenciamento de um sistema de contratos de serviços de internet.

O sistema permite o cadastro e gerenciamento de clientes, planos, serviços adicionais e contratos de internet, além da geração automática de logs de auditoria em uma segunda base de dados.

---

## Domínio Escolhido

O domínio escolhido foi um Sistema de Provedor de Internet, contemplando:

- Clientes
- Planos de Internet
- Serviços Adicionais
- Contratos de Internet
- Auditoria de operações

---

## Modelagem de Dados >>>

### Entidades Principais

- Cliente
- Plano
- ServicoAdicional
- ContratoInternet
- LogAuditoria (base de auditoria)

---

## Relacionamentos

### ✔ One-to-Many / Many-to-One
- Cliente → Contratos (1:N)
- Plano → Contratos (1:N)

### ✔ Many-to-Many
- Contrato ↔ Serviços Adicionais

### ✔ Cascata
- CascadeType.PERSIST aplicado na relação Many-to-Many

---

## Persistência em Múltiplas Bases

O sistema utiliza duas bases de dados H2:

### Base Principal (db_principal)
Responsável pelo domínio principal:
- Clientes
- Planos
- Contratos
- Serviços

### Base de Auditoria (db_auditoria)
Responsável pelo armazenamento de logs:

- Registro automático de criação de contratos
- Data e hora da operação
- ID do contrato criado

---

## Funcionalidade de Auditoria

Sempre que um novo contrato é criado:

1. O contrato é salvo na base principal
2. Um log de auditoria é automaticamente gerado
3. O log é persistido na base de auditoria

---

## Validações

O sistema utiliza validações com Bean Validation:

- @NotBlank → campos obrigatórios
- @NotNull → validação de valores nulos
- @Valid → validação de DTOs no Controller

---

## Consultas Customizadas

Foram implementadas consultas avançadas com Spring Data JPA:

- JPQL com JOIN FETCH para otimização de carregamento
- Query Nativa (nativeQuery = true) para consultas diretas no banco

---

## Endpoints da API

### Clientes
- GET /clientes
- POST /clientes

### Planos
- GET /planos
- POST /planos

### Serviços
- GET /servicos
- POST /servicos

### Contratos
- GET /contratos
- GET /contratos/nativo (usando SQL nativo)
- POST /contratos

---

## Tecnologias Utilizadas
Java 17
Spring Boot
Spring Data JPA
Hibernate
Banco H2 (duas instâncias em memória)
Maven

## Estrutura do Projeto
controller/
dto/
model/
model/audit/
repository/
repository/audit/
service/
config/

## Como Executar
./mvnw spring-boot:run

A aplicação será executada em:
http://localhost:8080

Console H2:
http://localhost:8080/h2-console

## Observações Finais
Este projeto foi desenvolvido com foco em:

Arquitetura em camadas
Separação de responsabilidades
Persistência em múltiplas bases de dados
Boas práticas com DTOs
Controle de relacionamento entre entidades
Registro automático de auditoria

## Exemplo de JSON (Contrato)

```json
{
  "dataAtivacao": "2026-04-23",
  "clienteId": 1,
  "planoId": 1,
  "servicosIds": [1]
}



