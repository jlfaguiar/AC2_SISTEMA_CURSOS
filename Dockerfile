# Usar uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR do projeto para o container
COPY target/ac2-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta em que o projeto vai rodar (exemplo: 8080)
EXPOSE 8080

# Comando para rodar o aplicativo
CMD ["java", "-jar", "app.jar"]