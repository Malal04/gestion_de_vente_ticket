# Étape 1 : Utilisation d'une image Java avec la bonne version
FROM openjdk:23-jdk-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Construction de l'image finale
FROM openjdk:23-jdk-slim
WORKDIR /app
COPY --from=build /app/target/your-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
