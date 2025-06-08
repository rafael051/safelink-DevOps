# 🛠️ Etapa 1: Build com Maven e Java 17 (Alpine)
FROM maven:3.9-eclipse-temurin-17-alpine AS build

# Diretório de trabalho no container
WORKDIR /app

# Copia todos os arquivos do projeto
COPY . .

# Compila a aplicação sem rodar os testes
RUN mvn clean package -DskipTests


# 🚀 Etapa 2: Runtime com JRE 17 (Alpine)
FROM eclipse-temurin:17-jre-alpine

# Cria um usuário não-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o .jar da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Permissões para o app
RUN chown -R appuser:appgroup /app

# Executa como usuário não-root
USER appuser

# Variável de ambiente padrão do Java
ENV JAVA_OPTS="-Xmx512m"

# Expondo a porta da aplicação
EXPOSE 80

# Comando de execução usando variável
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
