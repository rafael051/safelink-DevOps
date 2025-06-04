# 🛡️ SafeLink - Sistema Inteligente de Monitoramento de Riscos Naturais
## 📄 Descrição do Projeto
O SafeLink é um sistema inteligente para monitoramento, prevenção e resposta a eventos extremos da natureza, voltado para alertas, ocorrências, regiões de risco e relatos dos usuários. Projeto desenvolvido no Challenge 2025/1 (FIAP).
## 👨‍💻 Integrantes
- Rafael Rodrigues de Almeida - RM: 557837
- Lucas Kenji Miyahira - RM: 555368
## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Security (JWT)
- ModelMapper
- PostgreSQL (Railway ou local)/Oracle (opcional)
- Redis (opcional)
- Docker
- Swagger OpenAPI
## ✅ Passo a Passo: Publicação Docker Hub
### 1) Login no Docker Hub
```bash
docker login
```
### 2) Gerar JAR
```bash
./mvnw clean package -DskipTests
```
### 3) Build da imagem Docker
```bash
docker build -t <seu-usuario>/safelink:latest .
```
### 4) Push Docker Hub
```bash
docker push <seu-usuario>/safelink:latest
```
## 🐘 Banco PostgreSQL Railway
- Crie projeto PostgreSQL no Railway (https://railway.app)
- Copie URL gerada e configure o `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
spring.datasource.username=<usuario>
spring.datasource.password=<senha>
spring.datasource.driver-class-name=org.postgresql.Driver
```
- Docker PostgreSQL local:
```bash
docker run --name safelink-postgres -e POSTGRES_DB=safelinkdb -e POSTGRES_USER=usuario -e POSTGRES_PASSWORD=senha -p 5432:5432 -d postgres:15-alpine
```
## ⚙️ Exemplo application.properties
```properties
spring.application.name=safelink
spring.datasource.url=jdbc:postgresql://localhost:5432/safelinkdb
spring.datasource.username=rm557837
spring.datasource.password=181088
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.cache.type=simple
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
#server.address=0.0.0.0
#server.port=80
```
## 🧩 Funcionalidades Principais
- Autenticação usuários (JWT)
- Cadastro e consulta alertas risco
- Monitoramento eventos naturais
- Relatos ocorrências usuários
- Gestão regiões monitoradas
- Consulta paginada e filtros dinâmicos
- Documentação automática Swagger
## 🛠️ Executar Localmente
1. Clone repositório:
```bash
git clone https://github.com/rafael051/safelink.git
cd safelink
```
2. Configure banco PostgreSQL (Railway ou local)
3. Ajuste `application.properties`
4. Execute aplicação:
```bash
./mvnw spring-boot:run
```
Ou Docker:
```bash
docker build -t safelink .
docker run -p 8080:8080 safelink
```
5. Documentação Swagger: http://localhost:8080/swagger-ui.html
## 📚 Estrutura das Pastas
```
safelink/
├─src/
│ ├─main/
│ │ ├─java/br/com/fiap/safelink/
│ │ │ ├─controller/
│ │ │ ├─dto/request/
│ │ │ ├─dto/response/
│ │ │ ├─exception/
│ │ │ ├─filter/
│ │ │ ├─model/
│ │ │ ├─repository/
│ │ │ ├─service/
│ │ │ └─config/
│ │ └─resources/
│ │   ├─application.properties
│ │   └─...
├─Dockerfile
└─README.md
```
## 🔒 Autenticação JWT
Todos endpoints (exceto login) exigem autenticação JWT. Use endpoint `/auth/login` para obter token.
## 👀 Exemplos Endpoints
## 🔁 Endpoints POST
### Criar Usuário
```http
POST /users
Content-Type: application/json
{
  "email": "usuario@email.com",
  "password": "123456",
  "role": "USER"
}
```
### Autenticar
```http
POST /login
Content-Type: application/json
{
  "email": "usuario@email.com",
  "password": "123456"
}
```
### Cadastrar Alerta
```http
POST /alertas
Authorization: Bearer <token>
Content-Type: application/json
{
  "mensagem": "Alerta de enchente em bairro X",
  "nivelRisco": "ALTO",
  "dataEmissao": "2025-06-04T10:00:00",
  "idRegiao": 1
}
```
### Cadastrar Evento Natural
```http
POST /eventos-naturais
Authorization: Bearer <token>
Content-Type: application/json
{
  "descricao": "Tempestade severa",
  "data": "2025-06-03T14:30:00",
  "idRegiao": 2
}
```
### Registrar Previsão de Risco
```http
POST /previsoes-risco
Authorization: Bearer <token>
Content-Type: application/json
{
  "nivel": "MODERADO",
  "descricao": "Previsão de ventos fortes",
  "data": "2025-06-04",
  "idRegiao": 3
}
```
### Criar Relato de Usuário
```http
POST /relatos-usuarios
Authorization: Bearer <token>
Content-Type: application/json
{
  "mensagem": "Deslizamento na encosta",
  "idEvento": 5,
  "idUsuario": 1
}
```
### Cadastrar Região
```http
POST /regioes
Authorization: Bearer <token>
Content-Type: application/json
{
  "nome": "Zona Norte",
  "cidade": "São Paulo",
  "estado": "SP",
  "latitude": -23.502,
  "longitude": -46.635
}
```
## 📝 Licença
Projeto acadêmico — sem fins lucrativos.
## ✉️ Contato
- rafael.XXXXXX@fiap.com.br
- lucas.XXXXXX@fiap.com.br
