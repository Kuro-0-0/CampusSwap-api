# README.md para CampusSwap API

Aquí tienes un README completo basado en la estructura del proyecto:

# 🔄 CampusSwap API

API REST desarrollada con **Spring Boot** para la aplicación **CampusSwap**, una plataforma de intercambio de artículos entre estudiantes universitarios.

---

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Configuración del Entorno](#configuración-del-entorno)
- [Ejecución con Docker](#ejecución-con-docker)
- [Ejecución en Local](#ejecución-en-local)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Variables de Entorno](#variables-de-entorno)

---

## 📖 Descripción

CampusSwap es una plataforma que permite a los estudiantes universitarios intercambiar, comprar y vender artículos de segunda mano dentro del entorno universitario. Esta API proporciona todos los servicios backend necesarios para la aplicación móvil **CampusSwap App**.

---

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.x**
- **Maven**
- **Docker & Docker Compose**
- **PostgreSQL** (Producción)

---

## ✅ Requisitos Previos

- [Java 21+](https://adoptium.net/)
- [Maven 3.9+](https://maven.apache.org/)
- [Docker](https://www.docker.com/) y [Docker Compose](https://docs.docker.com/compose/)

---

## ⚙️ Configuración del Entorno

### Archivos de propiedades

El proyecto dispone de tres perfiles de configuración:

| Archivo | Descripción |
|---|---|
| `application.properties` | Configuración base común |
| `application-dev.properties` | Configuración para desarrollo |
| `application-prod.properties` | Configuración para producción |

### Archivos de entorno Docker

---

## 🐳 Ejecución con Docker

### Entorno de Desarrollo

```shell
docker compose --env-file .env.dev -f docker-compose.dev.yml up -d --build
```

### Entorno de Producción

```shell
docker compose --env-file .env.prod -f docker-compose.prod.yml up -d --build
```

### Comandos útiles de Docker

```shell
# Ver logs de la aplicación
docker compose logs -f

# Detener los contenedores
docker compose down

# Detener y eliminar volúmenes
docker compose down -v

# Ver contenedores en ejecución
docker ps
```

---

## 💻 Ejecución en Local

### Clonar el repositorio

```shell
git clone https://github.com/Kuro-0-0/CampusSwap-api.git
cd CampusSwap-api
```

### Compilar el proyecto

```shell
./mvnw clean install
```

### Ejecutar en perfil de desarrollo

```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Ejecutar tests

```shell
./mvnw test
```

---

## 📁 Estructura del Proyecto

```
CampusSwap-api/
├── docker/
│   └── entrypoint.sh               # Script de entrada del contenedor
├── secretos/
│   ├── credenciales.properties     # Credenciales (NO subir al repo)
│   └── prod_db_password.txt        # Contraseña DB producción (NO subir al repo)
├── src/
│   ├── main/
│   │   ├── java/com/salesianostriana/dam/campusswap/
│   │   │   └── ...                 # Código fuente principal
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       └── mensajes-validacion.properties
│   └── test/
│       └── java/com/salesianostriana/dam/campusswap/
│           └── ...                 # Tests unitarios e integración
├── uploads/                        # Directorio para ficheros subidos
├── docker-compose.dev.yml
├── docker-compose.prod.yml
├── Dockerfile
└── pom.xml
```

---

## 🔐 Variables de Entorno

| Variable | Descripción | Requerida |
|---|---|---|
| `DB_NAME` | Nombre de la base de datos | ✅ |
| `DB_USER` | Usuario de la base de datos | ✅ |
| `DB_PASSWORD` | Contraseña de la base de datos | ✅ |
| `DB_PORT` | Puerto de la base de datos | ✅ |
| `APP_PORT` | Puerto de la API | ✅ |
| `SPRING_PROFILE` | Perfil de Spring (`dev` / `prod`) | ✅ |

---

## 📱 Aplicación Móvil

Este backend da soporte a la aplicación móvil **CampusSwap App**, desarrollada en **Flutter**.  
Repositorio: [CampusSwap-app](https://github.com/Kuro-0-0/CampusSwap-app)

---

## 👤 Autores

**Pablo Garcia Maria**  
[GitHub](https://github.com/Kuro-0-0)

**Mauro Serrano **  
[GitHub](https://github.com/Mauroz9)

**Antonio Jesus Casado Bayon**  
[GitHub](https://github.com/ajcasadob)

---

## 📄 Licencia

Este proyecto está desarrollado como proyecto académico para **Salesianos Triana DAM**.
```
# Comandos para levantar DOCKER
## DEV
```shell
docker compose --env-file .env.dev -f docker-compose.dev.yml up -d --build
```
### PROD
```shell
docker compose --env-file .env.prod -f docker-compose.prod.yml up -d --build
```