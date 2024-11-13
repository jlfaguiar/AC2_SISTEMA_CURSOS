# Etapa de build usando Maven para compilar o projeto
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

# Copia o código-fonte para o container de build
COPY . .

# Compila o código e cria o JAR
RUN mvn clean package -DskipTests

# Etapa final usando OpenJDK para rodar a aplicação
FROM openjdk:17-jdk-slim

WORKDIR /app

# Define o argumento de perfil e a variável de ambiente no container
ARG PROFILE=test
ENV SPRING_PROFILES_ACTIVE=$PROFILE

# Copia o JAR compilado da etapa de build
COPY --from=builder /app/target/ac2-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta de execução da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
