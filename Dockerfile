FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/rest-with-spring-boot-java-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT  ["java", "-Djava.security.egd=file:/dev/./unrandom", "-jar", "/app.jar"]



#docker ps para listar os containers em execução
#docker logs + id do container para ver os logs