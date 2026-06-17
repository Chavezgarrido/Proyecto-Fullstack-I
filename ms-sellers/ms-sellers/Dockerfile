# --- ETAPA 1: Compilación interna del proyecto ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiamos el archivo de configuración de Maven (POM)
COPY pom.xml .

# Copiamos todo el código fuente del microservicio
COPY src ./src

# Compilamos y generamos el archivo .jar saltándonos los tests
RUN mvn clean package -DskipTests

# --- ETAPA 2: Imagen final de ejecución ligera ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto que expondrá el contenedor (Cámbialo según el servicio)
EXPOSE 8087

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]