📚 Book Tool – Backend (Spring Boot)

Backend for the Book Tool project, built with Spring Boot, MySQL, and Docker, with integration to the public OpenLibrary API to enrich book data.

—

🧰 Technologies

- Java 17
- Spring Boot 3
- Spring Data JPA (Hibernate)
- MySQL 8
- Docker & Docker Compose
- Maven
- OpenLibrary API

—

📂 Project structure (high level)

src
└── main
├── java
│   └── com.booktool.backend
│       ├── api
│       │   └── dto              # API-facing DTOs (REST contracts)
│       │       ├── EnrichmentStatusDto
│       │       └── EnumDTO
│       │
│       ├── config               # Spring configuration (beans, clients, etc.)
│       │   └── RestTemplateConfig
│       │
│       ├── controller           # REST controllers (HTTP layer)
│       │   ├── BookController
│       │   ├── EnrichmentController
│       │   └── EnumController
│       │
│       ├── domain               # Core domain model and business concepts
│       │   ├── compensation     # Compensation-related domain logic
│       │   ├── isbn             # ISBN-related domain logic
│       │   ├── Book             # Book domain entity
│       │   ├── Category         # Book category enum
│       │   └── Language         # Language enum
│       │
│       ├── exception            # Global error handling
│       │   ├── ApiError
│       │   └── GlobalExceptionHandler
│       │
│       ├── integration
│       │   └── openlibrary      # OpenLibrary external integration
│       │       ├── OpenLibraryClient
│       │       └── OpenLibraryEditionDTO
│       │
│       ├── job                  # Scheduled/background jobs
│       │   └── BookEnrichmentJob
│       │
│       ├── repository           # Persistence layer (JPA repositories)
│       │   └── BookRepository
│       │
│       └── service              # Application services (use cases)
│           ├── BookService
│           ├── BookEnrichmentService
│           └── EnrichmentStatusService
│
└── resources
├── application.yml          # Spring Boot configuration
└── application.properties  # Alternative configuration format

—

✅ Prerequisites

Make sure you have installed:

- Java 17

```
java -version
```

- Docker

```
docker --version
```

- Docker Compose

```
docker compose version
```

- Maven (optional — you can use the included `mvnw`)

```
mvn -v
```

—

🐬 Database (MySQL via Docker)

The project includes a `docker-compose.yml` that brings up MySQL automatically.

1️⃣ Start the database

From the project root:

```
docker compose up -d
```

This will create:

- MySQL on localhost:3306
- A database and user configured automatically

Stop the DB with:

```
docker compose down
```

—

⚙️ Application configuration

In `src/main/resources/application.yml`

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/books
    username: books
    password: books
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

In `src/main/resources/application.properties`

```
spring.datasource.url=jdbc:mysql://localhost:3306/books?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=books
spring.datasource.password=books
spring.jpa.hibernate.ddl-auto=update
```

—

▶️ Run the application locally

Option A: with the Maven Wrapper (recommended)

```
./mvnw spring-boot:run
```

On Windows:

```
mvnw.cmd spring-boot:run
```

—

Option B: from IntelliJ IDEA

1. Open the project
2. Open `BookToolApplication`
3. Click Run ▶️

—

🌐 API access

The app runs by default at:

http://localhost:8080

Examples:

- Get books:
```
GET http://localhost:8080/api/books
```
- Create a book:
```
POST http://localhost:8080/api/books
```
- Get enrichment status:
```
GET http://localhost:8080/api/enrichment/status```
```

🔁 Automatic book enrichment

The system includes a scheduled job that:

- Runs periodically using Spring `@Scheduled`
- Queries OpenLibrary by ISBN
- Automatically fills in:
  - Year
  - Page count
  - Language
  - Publisher
  - Link to OpenLibrary
- Recalculates compensation if data changes

Enrichment runs asynchronously on a dedicated scheduler thread and does not block HTTP requests.

—

📊 Logging

The backend uses SLF4J + Logback.

Key events are logged, including:

- Application startup
- Book creation
- Enrichment runs
- Data changes detected during enrichment
- Enrichment status updates

Logs clearly distinguish between:
- HTTP request threads
- Background scheduler threads

—

🧪 Running tests

```
mvn clean test
```

If using JaCoCo, coverage report is generated at:

```
target/site/jacoco/index.html
```

—

🧠 Important notes

- ISBN is normalized automatically before processing
- Languages and categories are managed via enums
- Compensation is calculated using decoupled domain logic
- Schema updates are handled automatically via Hibernate
- The backend is ready to be containerized for deployment
