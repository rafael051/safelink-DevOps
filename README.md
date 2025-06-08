# 🛡️ SafeLink - Sistema Inteligente de Monitoramento de Riscos Naturais
## 📄 Descrição do Projeto
O SafeLink é um sistema inteligente para monitoramento, prevenção e resposta a eventos extremos da natureza, voltado para alertas, ocorrências, regiões de risco e relatos dos usuários. Projeto desenvolvido na Global Solution 2025/1 (FIAP).
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
## ✅ Passo a Passo: Publicação da Imagem no Docker Hub

### 1) Faça login no Docker Hub

```bash
docker login
```

Informe:

- Username: rafael051
- Password: sua senha ou token de acesso.

Se aparecer:

```bash
Login Succeeded
```

### 2) Faça o push da imagem para o Docker Hub

```bash
docker push rafael051/safelink-1.0
```

O Docker enviará todas as camadas da imagem para o seu repositório.

Exemplo de saída:

```bash
The push refers to repository [docker.io/rafael051/safelink-1.0]
8fc2ab47dc9b: Pushed
...
latest: digest: sha256:...
```

### 3) Confirme no Docker Hub

Acesse:

https://hub.docker.com/repositories

Repositório: `rafael051/safelink-1.0`

Sua imagem deve aparecer com a tag: `latest`

### 4) Como outras pessoas podem usar sua imagem

Para baixar e rodar sua imagem:

```bash
docker pull rafael051/mototrack-1.0
docker run -d -p 8080:80 --name mototrack rafael051/safelink-1.0
```

---
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
docker run -p 8080:80 safelink
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

---
```
## ✅ Passo a Passo: Criação e Configuração da VM com Docker no Azure



### 1) Criar o Resource Group

```bash
az group create -l eastus -n rg-vm-global
```

### 2) Criar a Máquina Virtual

```bash
az vm create --resource-group rg-vm-global --name vm-global --image Canonical:ubuntu-24_04-lts:minimal:24.04.202505020 --size Standard_B2s --admin-username admin_fiap --admin-password admin_fiap@123
```

### 3) Criar regra de firewall para liberar a porta 8080

```bash
az network nsg rule create --resource-group rg-vm-global --nsg-name vm-globalNSG --name port_8080 --protocol tcp --priority 1010 --destination-port-range 8080
```

### 4) Criar regra de firewall para liberar a porta 80

```bash
az network nsg rule create  --resource-group rg-vm-global --nsg-name vm-globalNSG --name port_80 --protocol tcp  --priority 1020  --destination-port-range 80
```

### 5) Conectar via SSH e Instalar o Docker

#### a) Obter o IP público da VM

```bash
az vm show -d -g rg-vm-global -n vm-global --query publicIps -o tsv
```

Copie o IP retornado.

#### b) Conectar via SSH

```bash
ssh admin_fiap@<IP_DA_VM>
```

Substitua `<IP_DA_VM>` pelo IP copiado.

#### c) Instalar o Docker

```bash
sudo apt update && sudo apt install -y docker.io
```

#### d) Instalar e subir o PostgreSQL


# Baixar a imagem do PostgreSQL

```bash
sudo docker pull postgres
```
#### e) Criar rede Docker
```bash
sudo docker network create safelink-net
```

#### f) Subir o container do banco de dados
```bash
sudo docker run -d --name safelink-db --network safelink-net -e POSTGRES_USER=rm557837 -e POSTGRES_PASSWORD=181088 -e POSTGRES_DB=safelinkdb -v safelink_pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:16
```

### 6) Rodar o Container em Background

```bash
sudo docker run -d --name safelink --network safelink-net -p 8080:80 -e DB_URL=jdbc:postgresql://safelink-db:5432/safelinkdb -e DB_USERNAME=rm557837 -e DB_PASSWORD=181088 rafael051/safelink-1.0
```

### 7) (Opcional) Remover o Resource Group

```bash
az group delete --name rg-vm-challenge --yes --no-wait
```

---

✅ Pronto! Sua aplicação MotoTrack está publicada, disponível no Azure e acessível via o IP público da VM:

```
http://<IP_DA_VM>:8080/swagger-ui.html
```

---


```
### 🔒 Autenticação JWT
Todos endpoints (exceto login) exigem autenticação JWT. Use endpoint `/auth/login` para obter token.
## 👀 Exemplos Endpoints
## 🔁 Endpoints POST
### Criar Usuário
```
### Cadastrar Usuario
```http
POST /users
Content-Type: application/json
{
  "email": "usuario@safelink.com",
  "password": "s3nh@F0rte",
  "role": "ADMIN"
}
```
### Autenticar
```http
POST /login
Content-Type: application/json
{
  "email": "admin@safelink.com",
  "password": "admin123"
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
  "latitude": -23.5365,
  "longitude": -46.6333
}
```
### Cadastrar Alerta
```http
POST /alertas
Authorization: Bearer <token>
Content-Type: application/json
{
  "tipo": "Enchente",
  "nivelRisco": "ALTO",
  "mensagem": "Evacuar imediatamente a área afetada pela enchente",
  "emitidoEm": "08/06/2025 14:00:00",
  "idRegiao": 1
}
```
### Cadastrar Evento Natural
```http
POST /eventos-naturais
Authorization: Bearer <token>
Content-Type: application/json
{
  "tipo": "Deslizamento",
  "descricao": "Deslizamento de terra após fortes chuvas",
  "dataOcorrencia": "08/06/2025 14:00:00",
  "regiaoId": 1
}
```
### Registrar Previsão de Risco
```http
POST /previsoes-risco
Authorization: Bearer <token>
Content-Type: application/json
{
  "nivelPrevisto": "MÉDIO",
  "fonte": "INMET",
  "geradoEm": "08/06/2025 14:00:00",
  "regiaoId": 1
}
```
### Criar Relato de Usuário
```http
POST /relatos-usuarios
Authorization: Bearer <token>
Content-Type: application/json
{
  "mensagem": "Há deslizamento parcial na encosta próxima à escola municipal.",
  "dataRelato": "08/06/2025 14:00:00",
  "regiaoId": 1
}
```
## 📝 Licença
Projeto acadêmico — sem fins lucrativos.
## ✉️ Contato
- rm557837@fiap.com.br
- rm555368@fiap.com.br
