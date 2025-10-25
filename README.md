# Torneo Galáctico

Sistema de gestión del Gran Torneo Galáctico - Un enfrentamiento entre las especies más poderosas del universo que ocurre cada 500 años.

## Descripción

Aplicación que permite:
- Registrar especies con nombre, nivel de poder y habilidad especial
- Realizar combates entre especies (manual y aleatorio)
- Visualizar ranking de especies por victorias
- Consultar historial completo de combates

### Reglas de Combate
1. Gana la especie con mayor **nivel de poder**
2. En caso de empate, gana la especie con nombre **alfabéticamente primero**

## Stack Tecnológico

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Maven**
- **Lombok**

## Instalación y Ejecución sin Docker

#### 1. Clonar el repositorio
```bash
git clone git@github.com:Victor242424/galactic-tournament-complete.git
cd galactic-tournament-complete
```

#### 2. Compilar y ejecutar el backend
```bash
# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run

El backend estará disponible en: `http://localhost:8080`

#### 3. Abrir el frontend
Abrir directamente el archivo index.html en el navegador

## Ejecutar Tests

```bash
# Ejecutar todos los tests
mvn test
```

## Instalación y Ejecución con Docker

### Construir y ejecutar
docker-compose up --build

### Ejecutar en segundo plano
docker-compose up -d

### Ver logs
docker-compose logs -f

### Detener
docker-compose down
La aplicación estará disponible en:

API: http://localhost:8080
H2 Console: http://localhost:8080/h2-console

## API Endpoints

### Especies

#### Registrar nueva especie
```http
POST /api/especies
Content-Type: application/json

{
  "nombre": "Kryptoniano",
  "nivelPoder": 9500,
  "habilidadEspecial": "Superfuerza y vuelo"
}
```

#### Listar todas las especies
```http
GET /api/especies
```

#### Obtener especie por ID
```http
GET /api/especies/{id}
```

### Combates

#### Iniciar combate manual
```http
POST /api/combates
Content-Type: application/json

{
  "especie1Id": 1,
  "especie2Id": 2
}
```

#### Iniciar combate aleatorio
```http
POST /api/combates/aleatorio
```

#### Listar historial de combates
```http
GET /api/combates
```

### Ranking

#### Obtener ranking
```http
GET /api/ranking
```

## Arquitectura

### Backend (Arquitectura en Capas)

```
Controller (API REST)
    ↓
Service (Lógica de Negocio)
    ↓
Repository (Acceso a Datos)
    ↓
Database (H2 en memoria)
```
