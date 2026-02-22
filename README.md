# CampusSwap-api

# Comandos para levantar DOCKER
## DEV
```shell
docker compose --env-file .env.dev -f docker-compose.dev.yml up -d --build
```
### PROD
```shell
docker compose --env-file .env.prod -f docker-compose.prod.yml up -d --build
```