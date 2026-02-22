# =========================
# Stage 1: build (Maven + JDK 25)
# =========================
FROM maven:3-eclipse-temurin-25 AS build
WORKDIR /app

# Copiamos primero lo mínimo para cachear dependencias
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

# Asegurar permisos + evitar CRLF en mvnw
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Descargar dependencias (mejor caché)
RUN ./mvnw -DskipTests dependency:go-offline

# Copiamos el código y compilamos
COPY src/ src/
RUN ./mvnw -DskipTests package

# =========================
# Stage 2: runtime (JRE 25)
# =========================
FROM eclipse-temurin:25-jre-alpine AS runtime
WORKDIR /app

# Usuario no-root
RUN addgroup -S spring && adduser -S spring -G spring

RUN mkdir -p /app/uploads && chown -R spring:spring /app/uploads

# Copiar jar
COPY --from=build /app/target/*.jar /app/app.jar

# Copiar entrypoint
COPY ./docker/entrypoint.sh /app/entrypoint.sh
RUN sed -i 's/\r$//' /app/entrypoint.sh && chmod +x /app/entrypoint.sh

RUN chown -R spring:spring /app

USER spring:spring

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080

ENTRYPOINT ["/app/entrypoint.sh"]
