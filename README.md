# Sistema de Gerenciamento de Contratos de Internet

## Disciplina
Desenvolvimento Web II – PPGTI / UFRN  
Prof. Dr. Jean Mário Moreira de Lima  

---

## Repositório
https://github.com/victorfibra/devweb2-entrega03

---

## Descrição do Projeto

Este projeto consiste no desenvolvimento de uma API REST utilizando **Spring Boot** e **Spring Data JPA** para gerenciamento de um sistema de contratos de serviços de internet.

O sistema permite o cadastro e gerenciamento de clientes, planos, serviços adicionais e contratos de internet, além da implementação de auditoria com persistência em base de dados separada.

---

## Justificativa da Escolha do Domínio

O domínio de **provedor de serviços de internet** foi escolhido por ser um cenário real e amplamente utilizado no mercado, envolvendo múltiplas entidades relacionadas e regras de negócio bem definidas.

Além disso, esse domínio permite explorar conceitos importantes da disciplina, como:

- Relacionamentos entre entidades (1:N e N:N)
- Persistência em múltiplas bases de dados
- Uso de DTOs para desacoplamento da API
- Implementação de auditoria de operações
- Consultas otimizadas com JPQL e SQL nativo

A escolha também facilita a simulação de um sistema corporativo real, com separação clara entre dados operacionais e logs de auditoria.

---

## Domínio do Sistema

- Clientes
- Planos de Internet
- Serviços Adicionais
- Contratos de Internet
- Logs de Auditoria (base separada)

---

## Modelagem de Dados

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
- CascadeType.PERSIST aplicado no relacionamento de serviços

---

## Persistência em Múltiplas Bases de Dados

O sistema utiliza duas bases H2 independentes:

### Base Principal (`db_principal`)
Responsável pelos dados do domínio principal:
- Clientes
- Planos
- Serviços
- Contratos

### Base de Auditoria (`db_auditoria`)
Responsável pelos logs do sistema:
- Registro de criação de contratos
- Data e hora das operações
- ID do contrato

---

## Funcionalidade de Auditoria

Sempre que um contrato é criado:

1. O contrato é persistido na base principal
2. Um log é automaticamente gerado
3. O log é salvo na base de auditoria

Isso garante rastreabilidade das operações do sistema.

---

## Arquitetura da Aplicação

- Controller (camada de API)
- Service (regras de negócio)
- Repository (persistência)
- DTO (entrada e saída)
- Model (entidades)
- Audit (base separada)

---

## DTOs

### Entrada (Request)
Utilizados para criação e atualização de contratos:

- Validações com Bean Validation (@NotNull, @NotBlank)

### Saída (Response)
- Exposição controlada de dados
- Ocultação de entidades completas
- Retorno apenas de IDs relacionados

---

## Validações

- `@NotNull`
- `@NotBlank`
- `@Valid`

---

## Consultas Customizadas

- JPQL com `JOIN FETCH` para evitar LazyInitializationException
- SQL nativo para consultas diretas

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
- GET /contratos/nativo
- POST /contratos
- PUT /contratos/{id}
- DELETE /contratos/{id}

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate
- H2 Database (múltiplas instâncias)
- Maven

---

## Segurança (Spring Security)

O sistema implementa controle de acesso baseado em roles:

- ROLE_MASTER
- ROLE_CONTRIBUTOR
- ROLE_AUDITOR

### Regras de acesso:
- Público: endpoints informativos
- Todas as roles: listagens
- Roles específicas: criação, atualização e exclusão

---

## Como Executar

```bash
./mvnw spring-boot:run

Exemplo de JSON (Contrato)
{
  "dataAtivacao": "2026-04-23",
  "clienteId": 1,
  "planoId": 1,
  "servicosIds": [1]
}