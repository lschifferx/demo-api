# demo-api

API REST simples com Spring Boot + Maven + Docker Compose + Jenkins.

## Endpoints
- `GET /hello?name=SeuNome` → `{ "message": "Hello, SeuNome!" }`

## Rodando local
```bash
mvn clean verify
mvn spring-boot:run
```

## Docker
```bash
docker compose up -d --build
```

## CI (Jenkins)
- Executa `mvn clean verify`
- Build & push da imagem Docker
- Deploy via Docker Compose
