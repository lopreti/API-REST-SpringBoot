# API REST - Gerenciamento de Cursos

API REST para cadastro e gerenciamento de cursos de uma instituição de ensino, desenvolvida com Java Spring Boot.

## Funcionalidades

- Listar todos os cursos ativos
- Buscar curso por ID
- Cadastrar novo curso
- Atualizar nome e período de um curso
- Exclusão lógica de curso
- Listar todos os períodos disponíveis

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 25.0.2 |
| Spring Boot | 4.0.6 |
| MariaDB | 12.2.2 |
| Flyway | 11.14.1 |
| SpringDoc OpenAPI (Swagger) | 3.0.2 |
| Lombok | 1.18.46 |
| Maven | 3.x |

## ⚙️ Como rodar localmente

### Pré-requisitos
- Java 21+
- MariaDB instalado e rodando
- Maven

### Passo a passo

1. Clone o repositório:
```bash
git clone https://github.com/lopreti/API-REST-SpringBoot.git
```

2. Crie o banco de dados no MariaDB:
```sql
CREATE DATABASE api_cursos;
```

3. Configure o `application.properties` com suas credenciais:
```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/api_cursos
spring.datasource.username=root
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
```

4. Rode o projeto pelo IntelliJ ou via terminal:
```bash
./mvnw spring-boot:run
```

5. Acesse o Swagger:
```
http://localhost:8080/lopreti
```

## Endpoints

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| GET | /cursos | Lista todos os cursos ativos | 200 |
| GET | /cursos/{id} | Busca curso por ID | 200 / 404 |
| POST | /cursos | Cadastra um curso | 201 / 409 |
| PUT | /cursos | Atualiza um curso | 200 / 404 / 409 |
| DELETE | /cursos/{id} | Exclusão lógica | 204 / 404 |
| GET | /cursos/periodos | Lista os períodos | 200 |

## 
Feito por **Isabella G. Lopreti**
