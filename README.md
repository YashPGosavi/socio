# SOCio – Social Network Backend (Spring Boot)

SOCio is a backend system for a social networking application, built using
Spring Boot, JWT authentication, Apache Kafka, and MySQL.

## 🚀 Summary
A lightweight social network backend that supports users, posts, likes,
comments, follows, a personalized feed, and notifications delivered via Kafka.
The project is intended as a starter microservice and learning project.

## ✅ Features
- User registration & login (JWT-based authentication)
- Create posts
- Like & comment on posts
- Follow / unfollow users
- Personalized feed
- Real-time notifications using Kafka
- Global exception handling
- Swagger (OpenAPI) interactive docs
- Docker Compose for running Kafka/Zookeeper locally

## 🧱 Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA (works with H2/MySQL/Postgres)
- MySQL (example, configurable)
- Apache Kafka
- Docker / Docker Compose
- springdoc-openapi (Swagger UI)
- Maven

## 🏗 Architecture
Layered architecture (Controller → Service → Repository). Events (post liked,
commented, user followed) are published to Kafka so notification handling can be
scaled independently.

## ▶️ Quickstart (Local development)

Prerequisites:
- JDK 17
- Maven 3.6+
- Docker & Docker Compose (to run Kafka locally)

1. Start Kafka (optional, for notifications):

```bash
# from project root (docker-compose.yml is included)
docker compose up -d
```

2. Build and run the application:

```bash
mvn clean package
mvn spring-boot:run
# or
java -jar target/socio-0.0.1-SNAPSHOT.jar
```

3. Open the API docs (if application runs on localhost:8080):
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Swagger UI:    http://localhost:8080/swagger-ui/index.html


## ⚙️ Configuration
Configuration is read from `src/main/resources/application.properties`.
Example settings to get started (add to your `application.properties` or
`application-dev.properties`):

```properties
# Server
server.port=8080

# Datasource (example MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/socio?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=changeme
spring.jpa.hibernate.ddl-auto=update

# JWT
app.jwt.secret=change_this_secret
app.jwt.expiration-ms=86400000

# Springdoc (Swagger)
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
```

Adjust database and JWT properties before running in production.

## 🔐 Authentication
This app uses JWT tokens. Typical flow:
1. POST /users/register — create a user (email + password)
2. POST /users/login — supply `email` and `password`; returns a JWT token
3. For protected endpoints add header: `Authorization: Bearer <token>`

### Example: register & login (curl)

```bash
# Register
curl -X POST "http://localhost:8080/users/register" \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@example.com","password":"password123","name":"Alice"}'

# Login (returns JWT token string)
curl -X POST "http://localhost:8080/users/login" \
  -d "email=alice@example.com&password=password123"
```

Once you have a token, you can try protected endpoints in the Swagger UI by
clicking the "Authorize" button and pasting `Bearer <token>`.

## 📘 API Overview (selected endpoints)
- POST /users/register — Register a new user (JSON body)
- POST /users/login — Login (form params email + password)
- GET /users — (admin/dev) list users

- POST /posts — Create a post (authenticated, form param `content`)
- GET /posts — List posts

- POST /likes/{postId} — Like a post (authenticated)
- DELETE /likes/{postId} — Unlike a post
- GET /likes/{postId}/count — Get like count

- POST /comments/{postId}?content=... — Add comment (authenticated)
- DELETE /comments/{commentId} — Delete comment
- GET /comments/post/{postId} — Get comments for a post

- POST /follow/{userIdToFollow} — Follow user (authenticated)
- DELETE /follow/{userIdToUnfollow} — Unfollow user
- GET /follow/{userId}/followers — Followers count
- GET /follow/{userId}/following — Following count

- GET /feed — Fetch feed for the authenticated user

- GET /notifications — List notifications
- PUT /notifications/{id}/read — Mark a notification as read

Refer to the Swagger UI for request/response schemas and example values.

## 🧪 Tests
Run unit tests with:

```bash
mvn test
```

## 🛠 Troubleshooting

### 1) Application failed to start: Missing BCryptPasswordEncoder bean
If you see this error:

> Parameter 1 of constructor in com.socio.socio.service.UserService required a bean of type 'org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder' that could not be found.

It means a `PasswordEncoder` bean isn't defined. Fix by adding a bean in your
security configuration class. Example (put inside your `SecurityConfig` or a
`@Configuration` class):

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Use `PasswordEncoder` in your services (constructor-inject `PasswordEncoder`
instead of `BCryptPasswordEncoder`) to make the code easier to test.


### 2) Swagger UI is blocked by security
If your `SecurityConfig` blocks `/swagger-ui/**` or `/v3/api-docs/**`, the
Swagger UI will not load. For local development, allow these endpoints. Example
Spring Security configuration snippet:

```java
// inside your HttpSecurity configuration
http.authorizeHttpRequests()
    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
    // ... other matchers
    .anyRequest().authenticated();
```

Only whitelist these endpoints for development; secure them in production.

### 3) Kafka connectivity errors
If Kafka isn't running, you can either:
- Start Kafka with `docker compose up -d` (recommended for local dev), or
- Disable/guard Kafka producers/consumers from running when no broker is
  configured (add `@ConditionalOnProperty` on Kafka beans).

### 4) Database errors
- Make sure your `spring.datasource.*` properties match your DB instance.
- For quick testing, switch to H2 in-memory DB by adding H2 and updating
  properties.
