# Complete Java Spring Developer — 6-Month Learning Plan

**Goal:** Go from intermediate Java to a fully employable Java/Spring Software Developer, mastering the entire ecosystem used in professional backend engineering.

**Pace:** 8 hours/day, 5 days/week (Monday – Friday)
**Prerequisites:** Intermediate Java (Collections, Streams, Exceptions, basic OOP)
**Total Duration:** ~24 weeks (6 calendar months), ~120 working days

> [!NOTE]
> **How this plan is structured:**
> - **Phase 1 — Month 1 & 2 (Weeks 1–8):** Spring Boot fundamentals, REST, JPA, Spring AI, testing, security — culminating in your OCR project.
> - **Phase 2 — Month 3 & 4 (Weeks 9–16):** Deep Java, design patterns, architecture, advanced Spring Security, caching, AOP, Docker, and CI/CD (GitHub Actions + Jenkins).
> - **Phase 3 — Month 5 (Weeks 17–20):** Microservices, Spring Cloud, messaging (Kafka/RabbitMQ), and cloud deployment.
> - **Phase 4 — Month 6 (Weeks 21–24):** Reactive programming, performance, monitoring, capstone project, and interview prep.
>
> Each week ends with a **Weekend Self-Assessment** section — a checklist of things you should be able to explain or build from memory.

---
---

# Phase 1: Spring Boot Fundamentals & First Project (Weeks 1–8)

> This phase builds your Spring Boot foundation and delivers a working Romanian ID OCR application with REST APIs, database persistence, Spring AI integration, testing, and basic security.

---

## Month 1 — Weeks 1–4

### Week 1: Git, Spring Boot & Web Basics
*Goal: Set up the environment, run a Spring Boot application, and understand HTTP/REST essentials.*

#### Monday: Git Crash Course + Build Tool + IDE Setup
- **Morning (4h): Git & GitHub Essentials**
  - Configure Git (`user.name`, `user.email`).
  - Core commands: `init`, `add`, `commit`, `status`, `log`, `diff`, `branch`, `checkout`, `merge`.
  - Creating a repo on GitHub, pushing, and merging a feature branch.
  - Setup `.gitignore` (exclude `/target`, `.idea`, `.env`, local configs) and discuss why we never commit secrets.
- **Afternoon (4h): Build Tools & IDE Configuration**
  - **Gradle:** Understand the anatomy of a `build.gradle` (dependencies, plugins, properties, tasks).
  - **Maven:** Understand the anatomy of a `pom.xml` (dependencies, plugins, build lifecycle: `clean`, `compile`, `test`, `package`, `install`). Know both tools — most teams use one or the other.
  - IDE navigation: Import Gradle/Maven project, debug configurations, built-in tools (refactoring, search, database viewer).
  - Spring Boot DevTools — enable automatic restart and live reload during development (`spring-boot-devtools`).
  - **Task:** Initialize the Git repository, add a basic Gradle structure (and optionally a parallel Maven `pom.xml`), commit, and push.

#### Tuesday: Spring Concepts & Spring Boot Quickstart
- **Morning (4h): Dependency Injection & Spring Core**
  - Dependency Injection (DI) and Inversion of Control (IoC) conceptually.
  - Spring Beans, ApplicationContext, and core annotations (`@Component`, `@Service`, `@Repository`, `@Configuration`, `@Bean`).
- **Afternoon (4h): Spring Boot Introduction**
  - What is Spring Boot? (Opinionated starters, auto-configuration, embedded Tomcat).
  - Generate a Web project using [start.spring.io](https://start.spring.io).
  - Run the application and write a simple `@RestController` with a `@GetMapping`.
  - **Task:** Create a running Boot application with a dummy hello-world endpoint.

#### Wednesday: HTTP Fundamentals & Postman
- **Morning (4h): REST API Design & HTTP**
  - HTTP Methods (GET, POST, PUT, PATCH, DELETE) and Status Codes (200, 201, 204, 400, 404, 409, 500).
  - Headers, Request/Response body structure, and JSON data formats.
  - Content negotiation and `Accept`/`Content-Type` headers.
- **Afternoon (4h): Postman Mastery**
  - Set up Postman, create a workspace, and write a Request Collection.
  - Use environment variables (e.g., `{{base_url}}`).
  - **Task:** Build and export a Postman collection that hits your Spring Boot endpoints.

#### Thursday: Controllers & CRUD Operations
- **Morning (4h): Designing Endpoint Mappings**
  - Request data binding: `@PathVariable`, `@RequestParam`, and `@RequestBody`.
  - Controlling response metadata with `ResponseEntity`.
- **Afternoon (4h): Mocking In-Memory CRUD**
  - Implement full CRUD operations on an in-memory collection (e.g., a list of Users).
  - **Task:** Build a `/api/v1/users` endpoint supporting GET, POST, PUT, and DELETE. Test via Postman.

#### Friday: Data Validation & DTOs
- **Morning (4h): The DTO Pattern**
  - Why separate API request/response structures from core business objects.
  - Mapping incoming JSON DTOs to domain objects (manual mapping and MapStruct introduction).
- **Afternoon (4h): Data Validation**
  - Integrate `spring-boot-starter-validation`.
  - Apply constraint annotations: `@NotBlank`, `@Size`, `@Pattern`, `@Valid`.
  - Custom validators (e.g., a `@ValidCNP` annotation).
  - **Task:** Refactor the CRUD endpoints to use request validation and DTOs.

> **Weekend Self-Assessment — Week 1:**
> - [ ] Can you explain DI/IoC without looking at notes?
> - [ ] Can you create a Spring Boot app from scratch, add a REST controller, and test it in Postman?
> - [ ] Can you create a Git branch, make changes, and merge via PR?
> - [ ] Can you explain the difference between `@RequestParam`, `@PathVariable`, and `@RequestBody`?

---

### Week 2: REST Polish, Testing & Database Foundations
*Goal: Introduce error handling, API documentation, unit testing, and database basics.*

#### Monday: Exception Handling & Swagger
- **Morning (4h): Global Exception Handling**
  - `@ControllerAdvice` and `@ExceptionHandler` annotations.
  - Return clean validation errors and custom API error formats (RFC 7807 `ProblemDetail`).
  - Creating custom exception classes (e.g., `ResourceNotFoundException`).
- **Afternoon (4h): OpenAPI / Swagger Documentation**
  - Add `springdoc-openapi-starter-webmvc-ui` to your dependencies.
  - Annotate APIs with `@Operation`, `@ApiResponse`, `@Schema` for description and interactive testing in `/swagger-ui.html`.
  - **Task:** Add validation handling to the controllers and test the Swagger UI interface.

#### Tuesday: Introduction to Unit Testing
- **Morning (4h): JUnit 5 Basics**
  - Test lifecycle: `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`.
  - Assertions: `assertEquals`, `assertThrows`, `assertAll`, `assertNotNull`.
  - Parameterized tests with `@ParameterizedTest` and `@ValueSource`.
- **Afternoon (4h): Mocking with Mockito**
  - Use `@Mock`, `@InjectMocks`, and `when().thenReturn()` to test services in isolation.
  - Verify interactions with `verify()`.
  - Argument captors and matchers.
  - **Task:** Write unit tests for your CRUD service layer (at least 5 test methods).

#### Wednesday: Week 2 Mini-Project
- **All Day (8h): Library API Implementation**
  - Develop a REST API for managing a list of Books.
  - Implement request validation, DTOs, global error handling, and Swagger documentation.
  - **Task:** Deliver a clean GitHub branch with the API complete and at least 5 unit tests passing.

#### Thursday: PostgreSQL Setup & SQL Basics
- **Morning (4h): Database Installation & SQL**
  - Install PostgreSQL and a database client (pgAdmin or DBeaver).
  - DDL: `CREATE TABLE`, `ALTER TABLE`, constraints (`PRIMARY KEY`, `FOREIGN KEY`, `UNIQUE`, `NOT NULL`).
  - DML: `SELECT`, `INSERT`, `UPDATE`, `DELETE`.
  - Joins: `INNER JOIN`, `LEFT JOIN`, `RIGHT JOIN`.
- **Afternoon (4h): Database Connections**
  - Configure the application profile (`application-dev.yml`) with datasource properties.
  - Understand connection pooling (HikariCP — Spring Boot's default).
  - **Task:** Create the `id_ocr_db` database locally and test the connection from Spring Boot.

#### Friday: Object-Relational Mapping (ORM) with JPA
- **Morning (4h): Entity Configuration**
  - Annotations: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`.
  - Map basic fields (strings, dates, enums) to tables.
  - ID generation strategies: `IDENTITY`, `SEQUENCE`, `UUID`.
- **Afternoon (4h): Audit Fields**
  - Enable JPA Auditing with `@EnableJpaAuditing` to automatically track timestamps using `@CreatedDate` and `@LastModifiedDate`.
  - **Task:** Create the JPA entity representation for the `IdentityCard`.

> **Weekend Self-Assessment — Week 2:**
> - [ ] Can you explain the purpose of `@ControllerAdvice`?
> - [ ] Can you write a unit test with Mockito from memory?
> - [ ] Can you write a basic SQL `JOIN` query?
> - [ ] Can you explain entity lifecycle states in JPA (transient, managed, detached, removed)?

---

### Week 3: Data Persistence, Profiles & Spring AI Setup
*Goal: Master database repositories, data migrations, configuration profiles, and Spring AI setup.*

#### Monday: JpaRepository & Custom Queries
- **Morning (4h): Repository Interfaces**
  - Extend `JpaRepository` and run basic CRUD methods without writing SQL.
  - Understand `save()`, `findById()`, `findAll()`, `deleteById()`, `existsById()`.
- **Afternoon (4h): Query Generation**
  - Derived query methods (e.g., `findByCnp`, `findByLastNameContainingIgnoreCase`).
  - Custom `@Query` with JPQL and native SQL.
  - Pagination with `Pageable`, `Page`, and `Slice`.
  - Sorting with `Sort`.
  - **Task:** Write repository queries for search parameters on the `IdentityCard` entity.

#### Tuesday: Database Migrations & Profiles
- **Morning (4h): Database Migrations (Liquibase & Flyway)**
  - Why DDL auto-update (`spring.jpa.hibernate.ddl-auto=update`) is dangerous in production.
  - **Liquibase:** Set up with YAML or SQL changesets. Understand changeset IDs, authors, and rollback strategies.
  - **Flyway:** Understand SQL-based versioned migrations (`V1__create_table.sql`). Compare with Liquibase — Flyway is simpler, Liquibase is more flexible.
  - Write a versioned migration for creating the `identity_cards` table (use either tool).
- **Afternoon (4h): Profiles Configuration**
  - Split settings into `application-dev.yml` (local development) and `application-test.yml` (testing).
  - Activate profiles with `SPRING_PROFILES_ACTIVE` or `--spring.profiles.active`.
  - Externalize secrets using environment variables.
  - **Task:** Run the application locally relying on Liquibase for schema creation.

#### Wednesday: Week 3 Mini-Project
- **All Day (8h): Database Migration & API Integration**
  - Refactor the Library API from Week 2 to persist books into PostgreSQL.
  - Add Liquibase migration files and service unit tests.
  - **Task:** Complete database integration and verify pagination works end-to-end.

#### Thursday: Intro to Spring AI
- **Morning (4h): Spring AI Architecture**
  - Understand the framework's abstractions (`ChatClient`, `ChatModel`, `Prompt`, `Generation`).
  - Configure the OpenAI starter dependencies.
  - Explore model options and parameters (temperature, max tokens).
- **Afternoon (4h): Secure Config & API Calls**
  - Put the OpenAI API key into environment variables or local gitignored properties.
  - Write a basic prompt controller that accepts user input.
  - **Task:** Create an endpoint `/api/v1/chat` that returns chat completions.

#### Friday: Multimodal Prompts & File Handling
- **Morning (4h): Multipart File Uploads**
  - Configure file upload limits in Spring Boot (`spring.servlet.multipart.*`).
  - Handle `MultipartFile` inputs in controllers.
  - File type validation and size restrictions.
- **Afternoon (4h): Spring AI Vision**
  - Package images in messages and pass them to OpenAI Vision.
  - **Task:** Create an endpoint `/api/v1/vision/describe` that takes an image upload and describes it.

> **Weekend Self-Assessment — Week 3:**
> - [ ] Can you explain why Liquibase is preferred over `ddl-auto=update`?
> - [ ] Can you write a derived query method for a JPA repository?
> - [ ] Can you configure multiple Spring profiles and explain when to use each?
> - [ ] Can you explain how Spring AI's `ChatClient` works?

---

### Week 4: Romanian ID OCR & Extraction Logic
*Goal: Focus on the business logic of extracting and verifying Romanian Identity Card data.*

#### Monday: Romanian ID Card Deep Dive
- **Morning (4h): Document Specifications**
  - Study Romanian ID cards (fields: CNP, Nume, Prenume, Seria, Numarul, Adresa, Sex, Cetatenie).
  - Review layouts of the old standard card format and the new format.
- **Afternoon (4h): Validation Rules**
  - Learn the calculation logic for the CNP (checksum validator).
  - Date extraction from CNP (birth date, sex, county).
  - **Task:** Write a helper class (`CnpValidator`) to validate CNP digits and test it with JUnit.

#### Tuesday: Prompt Engineering for Extraction
- **Morning (4h): Instructing LLMs**
  - Formulate robust prompt instructions for visual character recognition.
  - Structure target schemas for prompt output (JSON Schema).
  - Techniques: few-shot examples, system messages, output format constraints.
- **Afternoon (4h): OCR Iterations**
  - Test prompts against various test ID card images.
  - **Task:** Create a prompt that reliably returns a JSON structure containing the ID's fields.

#### Wednesday: Structured Output & Service Encapsulation
- **Morning (4h): Mapping JSON Responses**
  - Integrate Spring AI Structured Output Converters.
  - Bind LLM output directly to a Java Record (`IdExtractionResult`).
- **Afternoon (4h): Service Design**
  - Move Vision and parsing code into a standalone `OcrService`.
  - Handle missing fields or unreadable sections cleanly with fallback logic.
  - **Task:** Write unit tests for the service using Mockito.

#### Thursday: API Wiring & Edge Case Testing
- **Morning (4h): REST Interface Integration**
  - Connect the `OcrService` to a REST Controller `/api/v1/cards/extract`.
  - Restrict uploads to images (JPEG, PNG) and block oversized files.
- **Afternoon (4h): Edge Cases & Robustness**
  - Test with challenging inputs: blurry images, rotated photos, non-ID documents.
  - Test both old-format and new-format Romanian IDs.
  - **Task:** Build a collection of test images and verify graceful handling.

#### Friday: Phase 1 Checkpoint — Integration
- **All Day (8h): Wire Everything Together**
  - Save verified OCR extractions to PostgreSQL.
  - Add image storage (local filesystem, path saved to DB).
  - Paginated card listing and search by CNP/Name.
  - **Task:** Full POST → Extract → Validate → Persist → GET flow working end-to-end.

> **Weekend Self-Assessment — Week 4:**
> - [ ] Can you explain the CNP checksum algorithm?
> - [ ] Can you design a prompt that returns structured JSON from an LLM?
> - [ ] Can you explain the difference between a Controller, Service, and Repository?

---

## Month 2 — Weeks 5–8

### Week 5: Integration Testing & Storage
*Goal: Add integration tests, Testcontainers, and finalize data retrieval APIs.*

#### Monday: Integration Testing with MockMvc
- **Morning (4h): `@SpringBootTest` & MockMvc**
  - Difference between unit tests and integration tests.
  - `@SpringBootTest` vs `@WebMvcTest` — when to use each.
  - Testing controllers with `MockMvc`: performing requests, asserting JSON responses.
- **Afternoon (4h): Testcontainers**
  - **Prerequisite:** Install Docker Desktop now if you haven't already — Testcontainers requires it. (Docker will be covered in depth in Phase 2, but you need the basics here.)
  - Spin up PostgreSQL inside Docker containers for integration tests.
  - `@Testcontainers`, `@Container`, `@DynamicPropertySource`.
  - **Task:** Write integration tests for the card extraction and persistence flow.

#### Tuesday: Image Storage & Retrieval
- **Morning (4h): File System Storage Service**
  - Design a `StorageService` for writing/reading files to disk.
  - Configure storage paths via `@ConfigurationProperties`.
- **Afternoon (4h): Image Retrieval Endpoints**
  - Serve stored images via `GET /api/v1/cards/{id}/image` using `Resource` and `ResponseEntity`.
  - Set correct `Content-Type` headers.
  - **Task:** Enable local storage and retrieval endpoints.

#### Wednesday: Pagination, Search & Filters
- **Morning (4h): Advanced Data Retrieval**
  - Implement paginated card lists using `Pageable`.
  - JPA Specifications for dynamic query building.
- **Afternoon (4h): Search Filters**
  - Add query params to search by CNP, Name, or date range.
  - **Task:** Fully functional search, pagination, and proper 404 handling.

#### Thursday: Logging & Configuration
- **Morning (4h): SLF4J / `@Slf4j` Logging**
  - Logging levels: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`.
  - Structured logging best practices (what to log, what NOT to log — never log PII in production).
  - Configure Logback (`logback-spring.xml`) for file rotation.
- **Afternoon (4h): `@ConfigurationProperties`**
  - Replace raw `@Value` property reads with type-safe `@ConfigurationProperties` classes.
  - Validate config properties with `@Validated`.
  - **Task:** Standardize logs and configure file storage paths dynamically.

#### Friday: End-to-End Validation
- **All Day (8h): System Check**
  - Upload real sample card data, verify extraction, save to DB, and assert correctness.
  - Run all unit + integration tests.
  - **Task:** Green test suite and a Postman verification collection.

> **Weekend Self-Assessment — Week 5:**
> - [ ] Can you explain the difference between `@SpringBootTest`, `@WebMvcTest`, and `@DataJpaTest`?
> - [ ] Can you set up Testcontainers for a PostgreSQL integration test?
> - [ ] Can you explain what `@ConfigurationProperties` does and why it's better than `@Value`?

---

### Week 6: Security & Polish
*Goal: Add basic security, clean up the codebase, and deliver the finished OCR project.*

#### Monday: Spring Security Basics
- **Morning (4h): Web Security Setup**
  - Add `spring-boot-starter-security`. Understand the default security filter chain.
  - `SecurityFilterChain` bean configuration.
  - HTTP Basic authentication.
- **Afternoon (4h): Endpoint Protection**
  - `authorizeHttpRequests()` — permit public endpoints, restrict others.
  - Role-based access with `hasRole()` / `hasAuthority()`.
  - **Task:** Protect upload/download endpoints. Configure authentication in Postman and Swagger.

#### Tuesday: Code Quality & Static Analysis
- **Morning (4h): Code Cleanup**
  - Refactor naming conventions, remove dead code, fix TODOs.
  - Add Javadoc to public APIs.
  - Run a static analysis tool (SpotBugs or Checkstyle) and fix warnings.
- **Afternoon (4h): Code Coverage**
  - Configure JaCoCo for code coverage reporting.
  - Understand line coverage vs branch coverage.
  - Set a coverage baseline (e.g., 70% minimum).
  - **Task:** Generate a coverage report and identify untested code paths.

#### Wednesday: README & Developer Guide
- **Morning (4h): Documentation**
  - Document setup guides, prerequisites, DB scripts, environment variables in `README.md`.
  - Add an architecture diagram (draw.io or Mermaid).
- **Afternoon (4h): Developer Onboarding**
  - Write a `CONTRIBUTING.md` with coding conventions and PR guidelines.
  - Add a `Makefile` or shell script for common dev tasks (`make run`, `make test`, `make db-migrate`).
  - **Task:** A newcomer should be able to clone, configure, and run the app from the README alone.

#### Thursday: Final Demo & Retrospective
- **Morning (4h): Final Demo**
  - Clear local state, check out main branch, build (`./gradlew clean build` or `mvn clean verify`), run tests.
  - Upload sample card images, show output tables, demonstrate search and pagination.
- **Afternoon (4h): Retrospective & Gap Analysis**
  - What went well? What was confusing? What do you want to learn more about?
  - **Task:** Write a personal retrospective document. Identify your top 3 weak areas.

#### Friday: Consolidation & Git Workflow Practice
- **Morning (4h): Git Intermediate Skills**
  - Rebasing vs merging — when to use each.
  - `git stash` — shelve work-in-progress temporarily.
  - `git log --oneline --graph` — visualize branch history.
  - Resolving merge conflicts — hands-on practice.
- **Afternoon (4h): Project Cleanup**
  - Clean up stale branches, tag your release (`v1.0.0`).
  - Squash messy commits via interactive rebase.
  - Ensure `.gitignore` is complete (no secrets, no build artifacts).
  - **Task:** Deliver a clean `main` branch with a tagged release.

> **Weekend Self-Assessment — Week 6:**
> - [ ] Can you configure Spring Security to protect specific endpoints?
> - [ ] Can you generate a JaCoCo coverage report?
> - [ ] Can you resolve a Git merge conflict?
> - [ ] Can you explain the difference between rebase and merge?

---

### Week 7: Core Java Deep Dive — OOP, Generics & Collections
*Goal: Solidify the Java fundamentals that every senior developer is expected to know.*

#### Monday: OOP Principles & SOLID
- **Morning (4h): The Four Pillars Revisited**
  - Encapsulation, Inheritance, Polymorphism, Abstraction — with real-world Spring examples.
  - Why Spring itself is built on these principles (interface-based programming).
- **Afternoon (4h): SOLID Principles**
  - **S**ingle Responsibility, **O**pen-Closed, **L**iskov Substitution, **I**nterface Segregation, **D**ependency Inversion.
  - Identify SOLID violations in your OCR project code and refactor them.
  - **Task:** Refactor at least 3 classes in your project to better follow SOLID.

#### Tuesday: Generics & Type System
- **Morning (4h): Generics Deep Dive**
  - Type parameters, bounded types (`<T extends Comparable<T>>`), wildcards (`? extends`, `? super`).
  - Type erasure — why generics don't exist at runtime and its implications.
  - Generic methods vs generic classes.
- **Afternoon (4h): Practical Generics**
  - Write a generic `ApiResponse<T>` wrapper class.
  - Write a generic `BaseRepository` or `BaseService` pattern.
  - **Task:** Implement a generic paginated response wrapper used across all your APIs.

#### Wednesday: Collections Framework Internals
- **Morning (4h): How Collections Work Under the Hood**
  - `ArrayList` vs `LinkedList` — internal arrays vs node chains, when to use each.
  - `HashMap` — hashing, buckets, load factor, rehashing, tree bins (Java 8+).
  - `HashSet` — backed by `HashMap`, equality contract.
  - `TreeMap` / `TreeSet` — red-black trees, natural ordering.
- **Afternoon (4h): Choosing the Right Collection**
  - `ConcurrentHashMap` vs `HashMap` vs `Hashtable`.
  - `Queue`, `Deque`, `PriorityQueue`.
  - Immutable collections: `List.of()`, `Map.of()`, `Collections.unmodifiable*()`.
  - **Task:** Write a benchmark comparing `ArrayList` vs `LinkedList` for insert/search operations.

#### Thursday: Equals, HashCode & Comparable
- **Morning (4h): The Equality Contract**
  - `equals()` and `hashCode()` — the contract, common pitfalls.
  - Why you MUST override both together.
  - Implementing `Comparable<T>` and `Comparator<T>`.
- **Afternoon (4h): Records & Immutability**
  - Java Records — auto-generated `equals()`, `hashCode()`, `toString()`.
  - When to use Records vs classes (DTOs, value objects).
  - Immutable object patterns and defensive copying.
  - **Task:** Refactor your DTOs to use Java Records where appropriate.

#### Friday: Functional Programming in Java
- **Morning (4h): Lambdas & Functional Interfaces**
  - `Function<T,R>`, `Predicate<T>`, `Consumer<T>`, `Supplier<T>`, `BiFunction`.
  - Method references (`Class::method`).
  - Writing custom functional interfaces.
- **Afternoon (4h): Streams API Mastery**
  - Intermediate ops: `map`, `filter`, `flatMap`, `peek`, `distinct`, `sorted`.
  - Terminal ops: `collect`, `reduce`, `forEach`, `count`, `findFirst`, `anyMatch`.
  - Collectors: `toList()`, `toMap()`, `groupingBy()`, `partitioningBy()`, `joining()`.
  - `Optional<T>` — proper usage, anti-patterns (never use `Optional` as a field).
  - **Task:** Rewrite 5 imperative loops in your project using Streams.

> **Weekend Self-Assessment — Week 7:**
> - [ ] Can you explain all 5 SOLID principles with examples?
> - [ ] Can you explain how `HashMap` works internally (hashing, buckets, collisions)?
> - [ ] Can you explain the difference between `? extends T` and `? super T`?
> - [ ] Can you chain Stream operations fluently and choose the right Collector?

---

### Week 8: Concurrency, Modern Java & JVM Basics
*Goal: Understand multithreading, modern Java features, and how the JVM runs your code.*

#### Monday: Concurrency Fundamentals
- **Morning (4h): Threads & Synchronization**
  - `Thread`, `Runnable`, `Callable<T>`.
  - Synchronization: `synchronized` keyword, `volatile`, visibility vs atomicity.
  - Race conditions, deadlocks — recognizing and avoiding them.
- **Afternoon (4h): java.util.concurrent**
  - `ExecutorService`, `ThreadPoolExecutor`, `ScheduledExecutorService`.
  - `Future<T>`, `CompletableFuture<T>` — chaining async operations.
  - `CountDownLatch`, `Semaphore`, `CyclicBarrier`.
  - **Task:** Write a program that processes 10 files concurrently using `CompletableFuture`.

#### Tuesday: CompletableFuture & Async Spring
- **Morning (4h): CompletableFuture Deep Dive**
  - `thenApply`, `thenCompose`, `thenCombine`, `allOf`, `anyOf`.
  - Exception handling with `exceptionally` and `handle`.
- **Afternoon (4h): `@Async` in Spring**
  - Enable async processing with `@EnableAsync`.
  - Configure custom `TaskExecutor` beans.
  - Pitfalls: proxy-based AOP, self-invocation issues.
  - **Task:** Add an `@Async` method to your OCR service for background image processing.

#### Wednesday: Modern Java Features (Java 17–21+)
- **Morning (4h): Language Enhancements**
  - Sealed classes and interfaces (`sealed`, `permits`).
  - Pattern matching for `instanceof` and `switch`.
  - Text blocks (multiline strings).
  - Enhanced `switch` expressions.
- **Afternoon (4h): API Enhancements**
  - `Stream.toList()`, `Stream.mapMulti()`.
  - `HttpClient` (java.net.http) — modern HTTP calls without third-party libs.
  - Virtual Threads (Project Loom) — lightweight threads, `Thread.ofVirtual()`.
  - **Task:** Refactor one service to use Virtual Threads and compare performance.

#### Thursday: JVM Internals & Memory
- **Morning (4h): JVM Architecture**
  - Class loading: Bootstrap → Extension → Application classloaders.
  - Memory areas: Heap (Young Gen, Old Gen), Stack, Metaspace, Code Cache.
  - How objects are allocated and garbage collected.
- **Afternoon (4h): Garbage Collection**
  - GC algorithms: Serial, Parallel, G1, ZGC.
  - GC logs: how to read them (`-Xlog:gc*`).
  - Common memory issues: leaks, `OutOfMemoryError`, `StackOverflowError`.
  - **Task:** Run your app with GC logging enabled and analyze the output.

#### Friday: Exception Handling & Best Practices
- **Morning (4h): Exception Strategy**
  - Checked vs unchecked exceptions — when to use each.
  - Custom exception hierarchies for business logic.
  - Exception translation patterns (repository → service → controller).
  - Try-with-resources and `AutoCloseable`.
- **Afternoon (4h): Defensive Programming**
  - `Objects.requireNonNull()`, precondition checks.
  - Null safety strategies (Optional, `@NonNull` annotations, Kotlin-style null checks).
  - Logging exceptions properly (always log the stack trace).
  - **Task:** Review and improve exception handling across your entire OCR project.

> **Weekend Self-Assessment — Week 8:**
> - [ ] Can you explain the difference between `synchronized` and `volatile`?
> - [ ] Can you chain `CompletableFuture` operations and handle errors?
> - [ ] Can you explain heap vs stack memory?
> - [ ] Can you explain what Virtual Threads are and when to use them?

> **🎯 Phase 1 Milestone:** You now have a working Spring Boot application with REST APIs, JPA persistence, Spring AI integration, unit/integration tests, basic security, and proper documentation. Your core Java knowledge is now solid.

---
---

# Phase 2: Architecture, Advanced Spring, Docker & CI/CD (Weeks 9–16)

> Now that you have hands-on experience and solid Java fundamentals, it's time to learn professional architecture patterns, advanced Spring features, containerization, and CI/CD pipelines.

---

## Month 3 — Weeks 9–12

### Week 9: Design Patterns & Clean Architecture
*Goal: Learn the design patterns and architectural principles used in professional Spring applications.*

---

<!-- Week 9 content follows -->


#### Monday: Creational & Structural Patterns
- **Morning (4h): Creational Patterns**
  - **Builder** — construct complex objects step by step (Lombok `@Builder`, manual builders).
  - **Factory Method** — delegate object creation to subclasses or factory classes.
  - **Singleton** — Spring beans are singletons by default; understand scope implications.
- **Afternoon (4h): Structural Patterns**
  - **Adapter** — make incompatible interfaces work together (e.g., wrapping external API responses).
  - **Decorator** — add behavior dynamically (e.g., logging decorators, caching decorators).
  - **Facade** — simplify complex subsystems behind a clean interface.
  - **Task:** Identify patterns already present in your OCR project and refactor to use them explicitly.

#### Tuesday: Behavioral Patterns
- **Morning (4h): Key Behavioral Patterns**
  - **Strategy** — swap algorithms at runtime (e.g., different OCR providers).
  - **Template Method** — define skeleton algorithms with customizable steps.
  - **Observer** — event-driven decoupling (Spring's `ApplicationEventPublisher`).
  - **Chain of Responsibility** — sequential processing (Spring Security filter chain).
- **Afternoon (4h): Applying Patterns in Spring**
  - Use `ApplicationEvent` and `@EventListener` for loose coupling.
  - Implement the Strategy pattern: create an `OcrProvider` interface with multiple implementations.
  - **Task:** Refactor your OCR service to support pluggable providers using the Strategy pattern.

#### Wednesday: Layered Architecture & Clean Architecture
- **Morning (4h): Traditional Layered Architecture**
  - Controller → Service → Repository — separation of concerns.
  - Why business logic must NEVER live in controllers.
  - Package-by-feature vs package-by-layer.
- **Afternoon (4h): Clean / Hexagonal Architecture**
  - Ports and Adapters (Hexagonal Architecture) — keep business logic framework-agnostic.
  - Domain layer (entities, value objects, domain services) — no Spring annotations.
  - Application layer (use cases / application services) — orchestrates domain logic.
  - Infrastructure layer (adapters) — JPA repositories, REST controllers, external APIs.
  - **Task:** Restructure your OCR project packages following Hexagonal Architecture.

#### Thursday: Domain-Driven Design (DDD) Essentials
- **Morning (4h): DDD Strategic Concepts**
  - Ubiquitous Language — shared vocabulary between developers and domain experts.
  - Bounded Contexts — clear boundaries between subdomains.
  - Context Mapping — how bounded contexts interact.
- **Afternoon (4h): DDD Tactical Patterns**
  - Entities vs Value Objects — identity vs structural equality.
  - Aggregates and Aggregate Roots — transactional consistency boundaries.
  - Domain Events — how to model state changes as events.
  - Repository pattern — the DDD definition (not just Spring's `JpaRepository`).
  - **Task:** Model the "Identity Card Extraction" domain using DDD tactical patterns.

#### Friday: Lombok, MapStruct & Developer Productivity
- **Morning (4h): Lombok**
  - `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor`, `@Slf4j`.
  - `@Data` vs individual annotations — when `@Data` is too much.
  - Delombok — understanding what Lombok generates.
  - IDE plugin setup for seamless integration.
- **Afternoon (4h): MapStruct**
  - Annotation-based compile-time mapper generation.
  - `@Mapper`, `@Mapping`, `@MappingTarget`.
  - Nested object mapping, collection mapping, custom converters.
  - **Task:** Replace all manual DTO↔Entity mappings in your project with MapStruct.

> **Weekend Self-Assessment — Week 9:**
> - [ ] Can you explain Builder, Strategy, and Observer patterns with examples?
> - [ ] Can you draw a Hexagonal Architecture diagram and explain each layer?
> - [ ] Can you explain the difference between an Entity and a Value Object (DDD)?
> - [ ] Can you configure MapStruct for a Spring Boot project?

---

### Week 10: Advanced JPA/Hibernate & Database Mastery
*Goal: Master the ORM layer — relationships, performance, transactions, and advanced queries.*

#### Monday: Entity Relationships
- **Morning (4h): Mapping Relationships**
  - `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`.
  - `@JoinColumn`, `@JoinTable`, `mappedBy`.
  - Unidirectional vs bidirectional relationships.
  - Cascade types (`PERSIST`, `MERGE`, `REMOVE`, `ALL`) and orphan removal.
- **Afternoon (4h): Practical Relationship Mapping**
  - Model a real relationship: `IdentityCard` → `Address`, `Person` → `List<IdentityCard>`.
  - **Task:** Add entity relationships to your database model and write migration scripts.

#### Tuesday: Fetch Strategies & N+1 Problem
- **Morning (4h): Lazy vs Eager Loading**
  - `FetchType.LAZY` (default for collections) vs `FetchType.EAGER`.
  - The N+1 query problem — what it is, how to detect it, how to fix it.
  - `@EntityGraph` — declarative fetch plans.
  - `JOIN FETCH` in JPQL.
- **Afternoon (4h): Hibernate Performance**
  - Enable SQL logging (`spring.jpa.show-sql`, `hibernate.format_sql`).
  - Hibernate statistics.
  - Batch fetching (`@BatchSize`).
  - **Task:** Identify and fix any N+1 issues in your project using `JOIN FETCH`.

#### Wednesday: Transactions & Concurrency
- **Morning (4h): Transaction Management**
  - `@Transactional` — how it works (AOP proxy).
  - Propagation levels: `REQUIRED`, `REQUIRES_NEW`, `NESTED`, `SUPPORTS`.
  - Isolation levels: `READ_UNCOMMITTED`, `READ_COMMITTED`, `REPEATABLE_READ`, `SERIALIZABLE`.
  - Read-only transactions: `@Transactional(readOnly = true)`.
- **Afternoon (4h): Concurrency in Databases**
  - Optimistic locking (`@Version`).
  - Pessimistic locking (`@Lock`).
  - Handling `OptimisticLockException` gracefully.
  - **Task:** Add `@Version` to your entities and write a test that triggers optimistic lock failure.

#### Thursday: Advanced Queries & Projections
- **Morning (4h): Specifications & Criteria API**
  - `JpaSpecificationExecutor` — dynamic query building for complex search.
  - Combining specifications with `Specification.where().and().or()`.
  - When to use Specifications vs JPQL vs native SQL.
- **Afternoon (4h): Projections & DTOs from Queries**
  - Interface-based projections (closed and open projections).
  - Class-based projections (DTOs directly from JPQL).
  - `@SqlResultSetMapping` for complex native queries.
  - **Task:** Implement a dynamic search endpoint using JPA Specifications.

#### Friday: Database Indexing & Optimization
- **Morning (4h): SQL Performance**
  - Indexes: B-tree, composite indexes, partial indexes, covering indexes.
  - `EXPLAIN ANALYZE` — reading PostgreSQL query plans.
  - Common antipatterns: missing indexes on foreign keys, over-indexing.
- **Afternoon (4h): Schema Design Best Practices**
  - Normalization (1NF, 2NF, 3NF) and when to denormalize.
  - Choosing the right column types.
  - Soft deletes vs hard deletes.
  - **Task:** Add indexes to your tables, analyze query plans, and measure improvement.

> **Weekend Self-Assessment — Week 10:**
> - [ ] Can you explain the N+1 problem and 3 ways to fix it?
> - [ ] Can you explain `@Transactional` propagation and isolation levels?
> - [ ] Can you read a PostgreSQL `EXPLAIN ANALYZE` output?
> - [ ] Can you implement dynamic queries using JPA Specifications?

---

### Week 11: Advanced Spring Security
*Goal: Go beyond basic auth — JWT, OAuth2, method security, and security best practices.*

#### Monday: Spring Security Architecture Deep Dive
- **Morning (4h): Security Internals**
  - The Security Filter Chain — how requests flow through filters.
  - `SecurityContext`, `Authentication`, `Principal`, `GrantedAuthority`.
  - `UserDetailsService` and custom user stores.
  - Password encoding: `BCryptPasswordEncoder`, why MD5/SHA is not enough.
- **Afternoon (4h): Form Login & Session Management**
  - Configure form-based login (even for API apps — understand the concepts).
  - Session management: stateful vs stateless sessions.
  - CSRF protection — when to enable/disable for APIs.
  - **Task:** Implement a custom `UserDetailsService` backed by your database.

#### Tuesday: JWT Authentication
- **Morning (4h): JWT Theory**
  - JSON Web Token structure: Header, Payload, Signature.
  - Symmetric (HMAC) vs Asymmetric (RSA) signing.
  - Token expiration, refresh tokens, token revocation strategies.
- **Afternoon (4h): JWT Implementation**
  - Write a `JwtService` for generating and validating tokens.
  - Create a custom `JwtAuthenticationFilter` (extends `OncePerRequestFilter`).
  - Wire it into the `SecurityFilterChain`.
  - **Task:** Implement login (`POST /api/v1/auth/login`) and protect endpoints with JWT.

#### Wednesday: OAuth2 & Resource Server
- **Morning (4h): OAuth2 Concepts**
  - Authorization Grant types: Authorization Code, Client Credentials, PKCE.
  - Roles: Authorization Server, Resource Server, Client, Resource Owner.
  - Scopes and claims.
- **Afternoon (4h): Spring as Resource Server**
  - Configure Spring Boot as an OAuth2 Resource Server.
  - Validate JWTs from an external provider (e.g., Keycloak, Auth0, Google).
  - Map external claims to Spring authorities.
  - **Task:** Set up Keycloak locally (via Docker) and configure your app as a resource server.

#### Thursday: Method Security & CORS
- **Morning (4h): Method-Level Security**
  - `@PreAuthorize`, `@PostAuthorize`, `@Secured`.
  - SpEL expressions: `hasRole()`, `hasAuthority()`, `#paramName`.
  - `@RolesAllowed` (JSR-250).
- **Afternoon (4h): CORS Configuration**
  - Understanding Cross-Origin Resource Sharing.
  - Global CORS configuration via `WebMvcConfigurer`.
  - Per-endpoint CORS with `@CrossOrigin`.
  - Common CORS errors and how to debug them.
  - **Task:** Add method-level security to your service layer and configure CORS for a frontend origin.

#### Friday: Security Best Practices & Hardening
- **Morning (4h): Security Hardening**
  - Security headers: `X-Content-Type-Options`, `X-Frame-Options`, `Content-Security-Policy`, `Strict-Transport-Security`.
  - Rate limiting (Bucket4j or Resilience4j RateLimiter).
  - Input sanitization and SQL injection prevention (parameterized queries).
  - XSS prevention in APIs.
- **Afternoon (4h): Secrets Management**
  - Environment variables, `.env` files, Spring Cloud Config.
  - HashiCorp Vault basics — dynamic secrets.
  - Never log sensitive data (passwords, tokens, PII).
  - **Task:** Audit your project for security vulnerabilities and fix them.

> **Weekend Self-Assessment — Week 11:**
> - [ ] Can you explain the Spring Security filter chain flow?
> - [ ] Can you implement JWT authentication from scratch?
> - [ ] Can you explain OAuth2 Authorization Code flow?
> - [ ] Can you configure CORS and explain common CORS errors?

---

### Week 12: Advanced Spring Boot Features & Caching

*Goal: Master the Spring Boot features used in production — actuator, caching, scheduling, AOP.*

#### Monday: Spring Boot Actuator & Health Checks
- **Morning (4h): Actuator Endpoints**
  - Add `spring-boot-starter-actuator`.
  - Key endpoints: `/actuator/health`, `/actuator/info`, `/actuator/metrics`, `/actuator/env`.
  - Custom health indicators (`HealthIndicator` interface).
  - Securing actuator endpoints.
- **Afternoon (4h): Custom Metrics**
  - Micrometer — the metrics facade (counters, gauges, timers, distribution summaries).
  - Instrument your OCR service with custom metrics (e.g., `ocr.extraction.count`, `ocr.extraction.duration`).
  - **Task:** Add Actuator with custom health checks and metrics to your project.

#### Tuesday: Caching with Spring Cache & Redis
- **Morning (4h): Spring Cache Abstraction**
  - `@EnableCaching`, `@Cacheable`, `@CacheEvict`, `@CachePut`.
  - Cache managers: `ConcurrentMapCacheManager` (in-memory), `CaffeineCacheManager`.
  - Cache key strategies and conditional caching.
- **Afternoon (4h): Redis as a Cache Store**
  - Install Redis (Docker: `docker run -p 6379:6379 redis`).
  - Configure `spring-boot-starter-data-redis`.
  - `RedisCacheManager` configuration.
  - TTL (Time To Live) policies.
  - **Task:** Cache frequently accessed card lookups with Redis, add eviction on update/delete.

#### Wednesday: Scheduling & Events
- **Morning (4h): Task Scheduling**
  - `@EnableScheduling` and `@Scheduled` — cron expressions, fixed rate, fixed delay.
  - Timezone handling in scheduled tasks.
  - Distributed scheduling considerations (ShedLock for cluster-safe scheduling).
- **Afternoon (4h): Application Events**
  - `ApplicationEvent`, `ApplicationEventPublisher`, `@EventListener`.
  - `@TransactionalEventListener` — fire events after transaction commit.
  - Async event listeners with `@Async`.
  - **Task:** Publish an `OcrCompletedEvent` after extraction and listen for it to trigger email notification (stub).

#### Thursday: Aspect-Oriented Programming (AOP)
- **Morning (4h): AOP Concepts**
  - Cross-cutting concerns: logging, security, transactions, caching.
  - Aspect, Advice, Pointcut, Join Point, Weaving.
  - Advice types: `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`, `@Around`.
- **Afternoon (4h): Custom Aspects**
  - Write a logging aspect that logs method entry/exit and execution time.
  - Write an audit aspect that tracks who accessed which endpoint.
  - Understanding proxy-based AOP limitations (self-invocation, final methods).
  - **Task:** Add a `@LogExecutionTime` custom annotation backed by an `@Around` aspect.

#### Friday: Phase 2 Capstone — Refactored OCR Project
- **All Day (8h): Major Refactoring**
  - Apply everything from Phase 2 to your OCR project:
    - Hexagonal architecture package structure.
    - JWT authentication.
    - Redis caching for card lookups.
    - Custom metrics and health checks.
    - AOP logging aspect.
  - Run full test suite (unit + integration).
  - **Task:** Push the refactored project as a new branch. All tests green.

> **🎯 Phase 2 Milestone:** Your OCR project is now production-grade — secure, well-architected, cached, monitored, and following DDD/Clean Architecture principles.

---

## Month 4 — Weeks 13–16


### Week 13: Docker & Containerization
*Goal: Master Docker — you've used it lightly for Testcontainers, now learn it properly for development and deployment.*

#### Monday: Docker Fundamentals
- **Morning (4h): Containers vs VMs**
  - What is a container? Images, containers, layers, registries.
  - Install Docker Desktop.
  - Core commands: `docker run`, `docker ps`, `docker images`, `docker logs`, `docker exec`.
- **Afternoon (4h): Running Services in Docker**
  - Run PostgreSQL in Docker: `docker run -e POSTGRES_PASSWORD=... -p 5432:5432 postgres`.
  - Run Redis in Docker.
  - Persistent data with Docker volumes.
  - **Task:** Run your entire development database infrastructure in Docker.

#### Tuesday: Dockerfiles & Building Images
- **Morning (4h): Writing Dockerfiles**
  - `FROM`, `COPY`, `RUN`, `EXPOSE`, `CMD`, `ENTRYPOINT`.
  - Multi-stage builds — keep images small (build with JDK, run with JRE).
  - `.dockerignore` file.
  - Layer caching strategies for faster builds.
- **Afternoon (4h): Containerize Your App**
  - Write a Dockerfile for your Spring Boot application.
  - Build and run the container.
  - Spring Boot's built-in Buildpacks (`./gradlew bootBuildImage`).
  - **Task:** Build a Docker image under 200MB for your OCR application.

#### Wednesday: Docker Compose
- **Morning (4h): Multi-Container Applications**
  - `docker-compose.yml` — services, networks, volumes, environment variables.
  - Service dependencies with `depends_on` and healthchecks.
- **Afternoon (4h): Full Stack Compose**
  - Write a `docker-compose.yml` that runs: your Spring Boot app + PostgreSQL + Redis.
  - Environment variable management with `.env` files.
  - **Task:** `docker compose up` starts your entire application stack.

#### Thursday: Docker Networking & Best Practices
- **Morning (4h): Container Networking**
  - Bridge networks, host networks, custom networks.
  - DNS resolution between containers.
  - Port mapping and exposure.
- **Afternoon (4h): Production Docker Practices**
  - Running as non-root user.
  - Health checks in Dockerfiles.
  - Resource limits (memory, CPU).
  - Image scanning for vulnerabilities (Trivy, Docker Scout).
  - **Task:** Harden your Docker setup with non-root user, health checks, and resource limits.

#### Friday: Docker Project Day
- **All Day (8h): Containerized Development Environment**
  - Create a complete development Docker Compose setup with:
    - Spring Boot app (hot-reload with volume mounts).
    - PostgreSQL with seeded data.
    - Redis.
    - pgAdmin for database management.
  - Write a `Makefile` or shell scripts for common operations.
  - **Task:** Document the Docker setup in your README. Anyone should run `docker compose up` and have a working environment.

> **Weekend Self-Assessment — Week 13:**
> - [ ] Can you write a multi-stage Dockerfile from memory?
> - [ ] Can you write a `docker-compose.yml` with multiple services?
> - [ ] Can you explain Docker networking and how containers communicate?

---

### Week 14: CI/CD & DevOps Practices (GitHub Actions + Jenkins)
*Goal: Automate building, testing, and deploying your applications with industry-standard CI/CD tools.*

#### Monday: CI/CD Concepts & GitHub Actions
- **Morning (4h): CI/CD Theory**
  - Continuous Integration — automated build and test on every push.
  - Continuous Delivery vs Continuous Deployment.
  - Pipeline stages: build → test → analyze → package → deploy.
  - CI/CD tool landscape: GitHub Actions, Jenkins, GitLab CI, CircleCI — know the ecosystem.
- **Afternoon (4h): GitHub Actions**
  - Workflow files (`.github/workflows/ci.yml`).
  - Triggers: `push`, `pull_request`, `schedule`.
  - Jobs, steps, actions, runners.
  - **Task:** Create a CI pipeline that builds and tests your project on every push.

#### Tuesday: Jenkins
- **Morning (4h): Jenkins Fundamentals**
  - Install Jenkins locally (Docker: `docker run -p 8080:8080 jenkins/jenkins:lts`).
  - Jenkins UI: Dashboard, jobs, build history, console output.
  - Freestyle jobs vs Pipeline jobs — why Pipelines are preferred.
  - Installing plugins (Git, Docker, JaCoCo, Slack notifications).
- **Afternoon (4h): Jenkins Pipelines (Jenkinsfile)**
  - Declarative vs Scripted pipelines.
  - `Jenkinsfile` syntax: `pipeline`, `agent`, `stages`, `stage`, `steps`, `post`.
  - Environment variables, credentials, and parameters.
  - Multi-branch pipelines — automatic pipeline per branch.
  - **Task:** Write a `Jenkinsfile` that builds, tests, and packages your Spring Boot app.

#### Wednesday: Advanced CI/CD & Docker in Pipelines
- **Morning (4h): Testing in CI**
  - Run unit tests and integration tests (with Testcontainers in CI).
  - Service containers in GitHub Actions / Docker agents in Jenkins.
  - Test reports and coverage (JaCoCo).
  - Caching Gradle/Maven dependencies for faster builds.
- **Afternoon (4h): Docker in CI**
  - Build Docker images in CI pipelines (both GitHub Actions and Jenkins).
  - Push images to GitHub Container Registry (ghcr.io) or Docker Hub.
  - Tag images with commit SHA and `latest`.
  - **Task:** Extend both CI pipelines: build → test → build Docker image → push to registry.

#### Thursday: Code Quality Gates
- **Morning (4h): Static Analysis & Linting**
  - Checkstyle — enforce code style.
  - SpotBugs — find common bugs.
  - SonarQube / SonarCloud — code quality dashboard.
  - Integrate SonarQube with Jenkins (SonarQube Scanner plugin).
- **Afternoon (4h): Quality Gates in CI**
  - Fail the CI build if coverage drops below threshold.
  - Fail if critical bugs are found.
  - Branch protection rules — require passing CI before merge.
  - **Task:** Add SonarCloud/SonarQube analysis to both CI pipelines.

#### Friday: Git Advanced Workflows & Deployment Strategies
- **Morning (4h): Advanced Git**
  - Interactive rebase: `git rebase -i` — squash, fixup, reorder, edit commits.
  - Cherry-pick: `git cherry-pick` — apply specific commits.
  - Bisect: `git bisect` — binary search for bug-introducing commits.
  - Git Flow, GitHub Flow, Trunk-Based Development — branching strategies.
  - Conventional Commits — structured commit messages.
- **Afternoon (4h): Deployment Strategies**
  - Blue-Green deployment, Canary deployment, Rolling updates.
  - Feature flags — deploy code without enabling features.
  - Database migration strategies during deployments.
  - Deploy your Dockerized app to a cloud platform (e.g., Railway, Render, Fly.io, or AWS ECS).
  - **Task:** Adopt Conventional Commits, create PR templates, and deploy to a cloud platform.

> **Weekend Self-Assessment — Week 14:**
> - [ ] Can you write a GitHub Actions workflow from memory?
> - [ ] Can you write a Jenkins Pipeline (`Jenkinsfile`) from memory?
> - [ ] Can you explain the difference between GitHub Actions and Jenkins?
> - [ ] Can you explain Blue-Green vs Canary deployments?
> - [ ] Can you do an interactive rebase to clean up commit history?

> **🎯 Phase 2 Milestone:** You can now architect professional Spring applications with clean code, design patterns, advanced security, caching, AOP, Docker containers, and automated CI/CD pipelines with both GitHub Actions and Jenkins.

---
---

# Phase 3: Microservices & Cloud-Native Development (Weeks 17–20)

> This phase transforms you from a single-application developer into someone who can design, build, and operate distributed systems.

---

## Month 5 — Weeks 17–20

### Week 17: Microservices Architecture & Spring Cloud
*Goal: Understand microservices principles and the Spring Cloud ecosystem.*

#### Monday: Microservices Theory
- **Morning (4h): Monolith vs Microservices**
  - When to use microservices (and when NOT to — monolith-first approach).
  - Service boundaries — how to decompose by business capability.
  - Communication patterns: synchronous (HTTP/gRPC) vs asynchronous (messaging).
  - Data ownership — each service owns its database.
- **Afternoon (4h): Distributed Systems Challenges**
  - CAP theorem, eventual consistency.
  - Network partitions, latency, partial failures.
  - Distributed transactions — Saga pattern.
  - Idempotency — designing operations that can be safely retried.
  - **Task:** Design a microservices decomposition for a fictional e-commerce system (on paper).

#### Tuesday: Service Communication — REST & OpenFeign
- **Morning (4h): Inter-Service HTTP Communication**
  - `RestTemplate` (legacy) vs `WebClient` (modern) vs `RestClient` (Spring 6.1+).
  - Timeout configuration, retry logic, error handling.
- **Afternoon (4h): Declarative HTTP Clients**
  - Spring Cloud OpenFeign — declare HTTP clients as interfaces.
  - `@FeignClient`, request/response interceptors, error decoders.
  - Circuit-breaking with Feign.
  - **Task:** Create two microservices that communicate via OpenFeign.

#### Wednesday: API Gateway & Service Discovery
- **Morning (4h): API Gateway Pattern**
  - Why an API Gateway? (Routing, authentication, rate limiting, load balancing).
  - Spring Cloud Gateway — routes, predicates, filters.
  - Path-based routing, header-based routing.
- **Afternoon (4h): Service Discovery**
  - Why hardcoding URLs is bad — dynamic service registration.
  - Netflix Eureka (Spring Cloud Netflix) — service registry and discovery.
  - Client-side vs server-side discovery.
  - Alternatives: Consul, Kubernetes service discovery.
  - **Task:** Set up Eureka Server + Gateway + 2 microservices that register and discover each other.

#### Thursday: Configuration Management & Resilience
- **Morning (4h): Centralized Configuration**
  - Spring Cloud Config Server — externalize configuration.
  - Config repository (Git-based).
  - Profile-specific and application-specific configs.
  - `@RefreshScope` — refresh config without restart.
- **Afternoon (4h): Resilience Patterns**
  - Circuit Breaker with Resilience4j: `@CircuitBreaker`, states (closed, open, half-open).
  - Retry: `@Retry` with exponential backoff.
  - Bulkhead: `@Bulkhead` — isolate failures.
  - Rate Limiter: `@RateLimiter`.
  - Fallback methods.
  - **Task:** Add circuit breakers to your inter-service calls.

#### Friday: Distributed Tracing & Observability
- **Morning (4h): Tracing Across Services**
  - Micrometer Tracing (formerly Spring Cloud Sleuth) — trace IDs, span IDs.
  - Correlation IDs — track a request across multiple services.
  - Zipkin — distributed tracing UI.
- **Afternoon (4h): Centralized Logging**
  - Log aggregation patterns.
  - ELK Stack (Elasticsearch, Logstash, Kibana) — concepts.
  - Structured logging with JSON (Logstash Logback Encoder).
  - **Task:** Set up Zipkin in Docker and trace requests across your microservices.

> **Weekend Self-Assessment — Week 17:**
> - [ ] Can you explain when to use microservices vs a monolith?
> - [ ] Can you explain the Circuit Breaker pattern and its states?
> - [ ] Can you set up service discovery with Eureka?
> - [ ] Can you explain distributed tracing and correlation IDs?

---

### Week 18: Messaging — Kafka & RabbitMQ
*Goal: Build event-driven, asynchronous systems using message brokers.*

#### Monday: Messaging Concepts
- **Morning (4h): Asynchronous Communication**
  - Synchronous (request-response) vs asynchronous (fire-and-forget, pub-sub).
  - Message brokers — what they do and why they matter.
  - Message queues vs topics (point-to-point vs publish-subscribe).
  - At-least-once, at-most-once, exactly-once delivery semantics.
- **Afternoon (4h): Event-Driven Architecture**
  - Events vs Commands vs Queries.
  - Event Sourcing (concept) — store events, derive state.
  - CQRS (concept) — separate read and write models.
  - **Task:** Design an event-driven architecture for your OCR system (on paper).

#### Tuesday: RabbitMQ
- **Morning (4h): RabbitMQ Fundamentals**
  - Exchanges (direct, topic, fanout, headers), queues, bindings, routing keys.
  - Run RabbitMQ in Docker with the management UI.
  - AMQP protocol basics.
- **Afternoon (4h): Spring AMQP**
  - `spring-boot-starter-amqp`.
  - `RabbitTemplate` — sending messages.
  - `@RabbitListener` — consuming messages.
  - Message serialization (Jackson JSON converter).
  - Dead letter queues (DLQ) — handling failed messages.
  - **Task:** Publish an `OcrCompleted` event to RabbitMQ and consume it in a notification service.

#### Wednesday: Apache Kafka
- **Morning (4h): Kafka Architecture**
  - Topics, partitions, offsets, consumer groups.
  - Producers, consumers, brokers, ZooKeeper/KRaft.
  - Retention policies, compaction.
  - Run Kafka in Docker (Confluent or Bitnami images).
- **Afternoon (4h): Spring Kafka**
  - `spring-kafka` dependency.
  - `KafkaTemplate` — producing messages.
  - `@KafkaListener` — consuming messages.
  - Consumer group management, partition assignment.
  - Serialization with Avro or JSON.
  - **Task:** Reimplement the event flow using Kafka instead of RabbitMQ.

#### Thursday: Kafka vs RabbitMQ & Advanced Patterns
- **Morning (4h): When to Use What**
  - RabbitMQ: complex routing, low latency, traditional messaging.
  - Kafka: high throughput, event streaming, log replay, event sourcing.
  - Hybrid approaches.
- **Afternoon (4h): Advanced Messaging Patterns**
  - Idempotent consumers — handling duplicate messages.
  - Outbox pattern — reliable event publishing with database transactions.
  - Saga pattern implementation with messaging.
  - **Task:** Implement the Outbox pattern: save events in a DB table, publish them via a polling publisher.

#### Friday: Messaging Project Day
- **All Day (8h): Build a Mini Event-Driven System**
  - Create 3 microservices:
    1. **OCR Service** — extracts data, publishes `CardExtracted` event.
    2. **Validation Service** — validates CNP, publishes `CardValidated` or `CardRejected`.
    3. **Notification Service** — logs or emails on validation results.
  - Communication via Kafka or RabbitMQ.
  - Docker Compose for the entire stack.
  - **Task:** All 3 services running in Docker Compose, processing events end-to-end.

> **Weekend Self-Assessment — Week 18:**
> - [ ] Can you explain the difference between Kafka and RabbitMQ?
> - [ ] Can you explain consumer groups and partitions in Kafka?
> - [ ] Can you implement the Outbox pattern?
> - [ ] Can you set up a dead letter queue in RabbitMQ?

---

### Week 19: REST API Advanced Topics & gRPC
*Goal: Master API design patterns, versioning, HATEOAS, and alternative protocols.*

#### Monday: REST API Best Practices
- **Morning (4h): API Design Principles**
  - Resource naming conventions (nouns, plural, hierarchical).
  - API versioning strategies: URL path (`/v1/`), header (`Accept-Version`), media type.
  - Idempotency keys for POST requests.
  - Bulk operations and batch endpoints.
- **Afternoon (4h): HATEOAS**
  - Hypermedia As The Engine Of Application State.
  - Spring HATEOAS — `EntityModel`, `CollectionModel`, `RepresentationModelAssembler`.
  - Link relations and discoverability.
  - **Task:** Add HATEOAS to your card listing endpoints.

#### Tuesday: GraphQL with Spring
- **Morning (4h): GraphQL Concepts**
  - Queries, mutations, subscriptions.
  - Schema definition language (SDL).
  - Why GraphQL? Over-fetching, under-fetching problems in REST.
  - When REST is better than GraphQL.
- **Afternoon (4h): Spring for GraphQL**
  - `spring-boot-starter-graphql`.
  - `@QueryMapping`, `@MutationMapping`, `@SchemaMapping`.
  - DataLoader pattern (batch loading to prevent N+1).
  - **Task:** Create a GraphQL API for querying identity cards.

#### Wednesday: gRPC with Spring Boot
- **Morning (4h): gRPC Fundamentals**
  - Protocol Buffers (protobuf) — schema definition, code generation.
  - Unary, server streaming, client streaming, bidirectional streaming.
  - gRPC vs REST — binary protocol, HTTP/2, performance.
- **Afternoon (4h): gRPC in Spring Boot**
  - `grpc-spring-boot-starter`.
  - Define a `.proto` file, generate Java stubs.
  - Implement a gRPC service and client.
  - **Task:** Create a gRPC service for card validation that the OCR microservice calls.

#### Thursday: API Documentation & Contract Testing
- **Morning (4h): Advanced OpenAPI**
  - Generate OpenAPI specs from code.
  - Generate client SDKs from OpenAPI specs (OpenAPI Generator).
  - API documentation best practices — examples, error schemas.
- **Afternoon (4h): Contract Testing**
  - Consumer-Driven Contracts with Spring Cloud Contract.
  - Pact framework basics.
  - Why contract tests matter in microservices.
  - **Task:** Write contract tests between your OCR and Validation services.

#### Friday: WebSockets & Server-Sent Events
- **Morning (4h): WebSockets**
  - Full-duplex communication over a single TCP connection.
  - Spring WebSocket with STOMP protocol.
  - `@MessageMapping`, `SimpMessagingTemplate`.
  - Use case: real-time OCR processing status updates.
- **Afternoon (4h): Server-Sent Events (SSE)**
  - One-way server → client streaming over HTTP.
  - `SseEmitter` in Spring MVC.
  - When to use SSE vs WebSockets vs polling.
  - **Task:** Add real-time OCR processing status via SSE to your application.

> **Weekend Self-Assessment — Week 19:**
> - [ ] Can you explain 3 API versioning strategies and their trade-offs?
> - [ ] Can you explain the difference between REST, GraphQL, and gRPC?
> - [ ] Can you implement a WebSocket endpoint in Spring?

---

### Week 20: Kubernetes & Cloud Deployment
*Goal: Deploy and manage your applications on Kubernetes.*

#### Monday: Kubernetes Concepts
- **Morning (4h): Kubernetes Architecture**
  - Control Plane (API Server, Scheduler, Controller Manager, etcd).
  - Worker Nodes (kubelet, kube-proxy, container runtime).
  - Pods, ReplicaSets, Deployments, Services, Namespaces.
- **Afternoon (4h): Local Kubernetes Setup**
  - Install Minikube or Kind (Kubernetes in Docker).
  - `kubectl` basics: `get`, `describe`, `apply`, `logs`, `exec`, `delete`.
  - **Task:** Deploy a simple Nginx pod and access it locally.

#### Tuesday: Deploying Spring Boot to Kubernetes
- **Morning (4h): Kubernetes Resources for Spring Boot**
  - Write Deployment YAML — replicas, container specs, resource limits.
  - Write Service YAML — ClusterIP, NodePort, LoadBalancer.
  - ConfigMaps and Secrets — externalize configuration.
- **Afternoon (4h): Spring Boot on K8s**
  - Kubernetes probes: `livenessProbe`, `readinessProbe`, `startupProbe` (map to Actuator endpoints).
  - Graceful shutdown: `server.shutdown=graceful`.
  - **Task:** Deploy your OCR application + PostgreSQL to local Kubernetes.

#### Wednesday: Helm & Advanced Kubernetes
- **Morning (4h): Helm Charts**
  - Package manager for Kubernetes.
  - Helm chart structure: `Chart.yaml`, `values.yaml`, templates.
  - Install third-party charts (e.g., PostgreSQL, Redis, Kafka from Bitnami).
- **Afternoon (4h): Scaling & Networking**
  - Horizontal Pod Autoscaler (HPA).
  - Ingress controllers and Ingress resources.
  - Persistent Volume Claims (PVC) for databases.
  - **Task:** Create a Helm chart for your application.

#### Thursday: Cloud Providers (AWS/GCP Basics)
- **Morning (4h): Cloud Services Overview**
  - Compute (EC2/GCE), Managed Kubernetes (EKS/GKE), Managed Databases (RDS/Cloud SQL).
  - Object Storage (S3/GCS) — for image storage.
  - IAM (Identity and Access Management) — roles, policies.
- **Afternoon (4h): Cloud-Native Spring**
  - Spring Cloud AWS / Spring Cloud GCP — cloud-specific starters.
  - Store images in S3/GCS instead of local filesystem.
  - Managed secrets (AWS Secrets Manager / GCP Secret Manager).
  - **Task:** Migrate your image storage from local filesystem to a cloud object store.

#### Friday: Infrastructure as Code
- **All Day (8h): Terraform Basics**
  - Declarative infrastructure: providers, resources, variables, outputs.
  - Write Terraform to provision a database, a storage bucket, and a Kubernetes cluster.
  - `terraform init`, `plan`, `apply`, `destroy`.
  - State management.
  - **Task:** Write Terraform scripts to provision your cloud infrastructure.

> **🎯 Phase 3 Milestone:** You can now design, build, and deploy microservices with inter-service communication, message queues, and Kubernetes orchestration.

---
---

# Phase 4: Advanced Topics, Performance & Career Readiness (Weeks 21–24)

> The final phase rounds out your skills with reactive programming, performance engineering, monitoring, a capstone project, and interview preparation.

---

## Month 6 — Weeks 21–24

### Week 21: Reactive Programming, Performance & NoSQL
*Goal: Understand reactive programming, performance tuning, and non-relational data stores.*

> [!NOTE]
> This week condenses reactive, performance, and NoSQL into focused sessions. The goal is working knowledge, not mastery — you'll deepen these on the job.

#### Monday: Reactive Programming & Spring WebFlux
- **Morning (4h): Reactive Streams & Project Reactor**
  - Publisher, Subscriber, Subscription — the reactive streams spec.
  - `Mono<T>` (0 or 1 element) and `Flux<T>` (0 to N elements).
  - Key operators: `map`, `flatMap`, `filter`, `zip`, `merge`, `switchIfEmpty`.
  - Error handling: `onErrorResume`, `onErrorReturn`, `retry`.
  - Backpressure — handling fast producers and slow consumers.
- **Afternoon (4h): WebFlux & Decision Guide**
  - `spring-boot-starter-webflux` — Netty instead of Tomcat.
  - Annotated controllers returning `Mono`/`Flux`.
  - `WebClient` — the reactive HTTP client.
  - When reactive shines (high concurrency, I/O-bound) vs when it's overkill (CRUD apps).
  - Spring MVC with Virtual Threads as an alternative to WebFlux.
  - **Task:** Build a small reactive REST API and test with `StepVerifier` and `WebTestClient`.

#### Tuesday: Performance Profiling & JVM Tuning
- **Morning (4h): Profiling Tools**
  - JVisualVM / JConsole — visual monitoring.
  - async-profiler — CPU and allocation profiling.
  - JFR (Java Flight Recorder) — low-overhead production profiling.
  - Heap dump analysis with Eclipse MAT.
- **Afternoon (4h): JVM Flags & Benchmarking**
  - Heap sizing: `-Xms`, `-Xmx`, `-XX:MaxMetaspaceSize`.
  - GC selection: G1 vs ZGC — when to use each.
  - JMH (Java Microbenchmark Harness) — proper Java benchmarking.
  - **Task:** Profile your OCR application, identify bottlenecks, and write a JMH benchmark.

#### Wednesday: Database Performance & Load Testing
- **Morning (4h): Query Optimization**
  - Slow query logging in PostgreSQL.
  - `EXPLAIN ANALYZE` — reading query plans.
  - Index strategies, connection pool sizing (HikariCP).
  - Hibernate: second-level cache, batch inserts, N+1 detection with p6spy.
- **Afternoon (4h): Load Testing**
  - Gatling or k6 — write load test scripts.
  - Analyze results: response times (p50, p95, p99), throughput, error rates.
  - **Task:** Load test your OCR API, find the breaking point, optimize, and re-test.

#### Thursday: NoSQL Data Stores
- **Morning (4h): Redis Deep Dive**
  - Data structures: Strings, Lists, Sets, Sorted Sets, Hashes.
  - `spring-boot-starter-data-redis`, `RedisTemplate`.
  - Redis as a session store (`spring-session-data-redis`).
  - Distributed locking with Redis (Redisson).
- **Afternoon (4h): MongoDB & Elasticsearch**
  - MongoDB: `@Document`, `MongoRepository`, when to use document databases.
  - Elasticsearch: full-text search, inverted indexes, `ElasticsearchRepository`.
  - Decision guide: when to use Redis vs MongoDB vs Elasticsearch vs PostgreSQL.
  - **Task:** Add Redis caching and Elasticsearch full-text search to your card records.

#### Friday: Monitoring, Alerting & Production Operations
- **Morning (4h): Prometheus & Grafana**
  - Spring Boot Actuator + Micrometer + Prometheus exporter.
  - Grafana dashboards: JVM metrics, HTTP request rates, error rates.
  - PromQL basics.
- **Afternoon (4h): Logging, Alerting & Production Readiness**
  - Structured JSON logging with Logstash Logback Encoder.
  - MDC (Mapped Diagnostic Context) — trace IDs in all logs.
  - ELK/EFK Stack concepts.
  - Grafana alerting rules. Production checklist: graceful shutdown, health checks, externalized config.
  - **Task:** Set up Prometheus + Grafana monitoring and create a production deployment checklist.

> **Weekend Self-Assessment — Week 21:**
> - [ ] Can you explain the difference between `Mono` and `Flux`?
> - [ ] Can you explain when to use WebFlux vs MVC + Virtual Threads?
> - [ ] Can you use JFR or async-profiler to profile an application?
> - [ ] Can you set up Prometheus + Grafana for a Spring Boot app?
> - [ ] Can you explain when to use Redis vs MongoDB vs Elasticsearch vs PostgreSQL?

---

### Week 22–23: Capstone Project (2 Weeks)
*Goal: Build a complete, production-ready application from scratch that demonstrates all your skills.*

#### Capstone Project: **Document Processing Platform**

A complete document processing system that accepts various document types (IDs, passports, driver's licenses), extracts data using AI, stores results, and provides a management API.

#### Monday: Architecture & Setup
- **All Day (8h):**
  - Design the full system architecture (draw diagrams).
  - Define microservices boundaries:
    1. **API Gateway** — routing, authentication.
    2. **Document Service** — upload, storage, metadata.
    3. **Extraction Service** — AI-powered OCR and data extraction.
    4. **User Service** — authentication, authorization.
    5. **Notification Service** — email/event notifications.
  - Initialize repositories, write Dockerfiles, create Docker Compose.
  - **Deliverable:** Architecture Decision Record (ADR) and project skeleton.

#### Tuesday–Friday (Week 22): Core Implementation (32h)
- Implement each microservice with:
  - Clean/Hexagonal architecture.
  - JWT authentication (User Service issues tokens).
  - PostgreSQL persistence with Liquibase/Flyway migrations.
  - Unit and integration tests.
  - OpenAPI documentation.
- Inter-service communication via Kafka events.
- CI/CD pipeline with GitHub Actions or Jenkins.
- **Deliverable:** All services with core functionality working.

#### Monday–Tuesday (Week 23): Infrastructure & Deployment (16h)

- Kubernetes manifests or Helm charts for all services.
- Prometheus + Grafana monitoring.
- Centralized logging.
- **Deliverable:** Full CI/CD pipeline, containerized deployment.

#### Wednesday–Friday (Week 23): Polish & Documentation (24h)
- Load testing with k6/Gatling.
- Security audit.
- Comprehensive README with architecture diagrams.
- API documentation.
- Record a demo video walkthrough.
- **Deliverable:** Complete, documented, deployable project ready for a portfolio.

---

### Week 24: Interview Preparation & Career Readiness
*Goal: Prepare for Java/Spring developer interviews and next steps.*

#### Monday: Core Java Interview Topics
- **All Day (8h):**
  - Collections internals (HashMap, ConcurrentHashMap, TreeMap).
  - Concurrency (thread safety, volatile, synchronized, locks, CompletableFuture).
  - JVM memory model, GC, class loading.
  - Generics, type erasure.
  - equals/hashCode contract.
  - **Practice:** Solve 10 Java interview questions and explain answers out loud.

#### Tuesday: Spring & Architecture Interview Topics
- **All Day (8h):**
  - Spring IoC, DI, Bean lifecycle, scopes, AOP.
  - Spring Boot auto-configuration, starters, actuator.
  - Spring Security — filter chain, JWT, OAuth2.
  - Spring Data JPA — N+1, lazy loading, transactions, isolation levels.
  - Microservices patterns — circuit breaker, saga, outbox, CQRS.
  - **Practice:** Solve 10 Spring interview questions.

#### Wednesday: System Design
- **All Day (8h):**
  - System design fundamentals: load balancing, caching, database sharding, CDNs.
  - Design an e-commerce backend (user service, product catalog, order service, payment).
  - Design a URL shortener.
  - Design a real-time chat system.
  - Trade-offs: consistency vs availability, SQL vs NoSQL, monolith vs microservices.
  - **Practice:** Whiteboard 3 system designs within 45 minutes each.

#### Thursday: Coding Challenges & Problem Solving
- **All Day (8h):**
  - Data structures: Arrays, LinkedLists, Stacks, Queues, Trees, Graphs, HashMaps.
  - Algorithms: sorting, searching, BFS/DFS, dynamic programming (basics).
  - Practice on LeetCode/HackerRank — focus on Medium difficulty.
  - **Practice:** Solve 5–8 coding problems in Java.

#### Friday: Portfolio, Resume & Next Steps
- **Morning (4h): Portfolio & Resume**
  - Polish GitHub profile — pin best repositories, write great READMEs.
  - Craft your resume — quantify achievements, highlight technologies.
  - LinkedIn optimization — headline, summary, skills, project descriptions.
- **Afternoon (4h): Growth Plan & Next Steps**
  - Identify specialization paths: backend architect, platform engineer, data engineer.
  - Advanced topics to explore: Kotlin for Spring, Spring Native (GraalVM), event sourcing frameworks (Axon).
  - Open source contribution — find Spring-related projects to contribute to.
  - **Task:** Create a personal development plan for the next 6 months.

> **🎯 Phase 4 Milestone:** You are now a well-rounded Java/Spring developer ready for mid-level to senior backend roles. You have deep knowledge of the JVM, Spring ecosystem, microservices, DevOps, and system design.

---
---

# Appendix A: Technology & Tool Summary

| Category | Technologies |
|---|---|
| **Language** | Java 17–21+, Records, Sealed Classes, Virtual Threads |
| **Build Tools** | Gradle (primary), Maven (familiarity with both) |
| **Framework** | Spring Boot (+ DevTools), Spring MVC, Spring WebFlux, Spring Security, Spring Data JPA/R2DBC, Spring Cloud, Spring AI, Spring Batch |
| **Databases** | PostgreSQL, Redis, MongoDB, Elasticsearch |
| **ORM** | Hibernate / JPA |
| **Migrations** | Liquibase, Flyway |
| **Messaging** | Apache Kafka, RabbitMQ |
| **Testing** | JUnit 5, Mockito, Testcontainers, JMH, Gatling/k6, JaCoCo |
| **API** | REST, GraphQL, gRPC, WebSocket, SSE, OpenAPI/Swagger |
| **Security** | JWT, OAuth2, Spring Security, Keycloak |
| **Containers** | Docker, Docker Compose, Kubernetes, Helm |
| **CI/CD** | GitHub Actions, Jenkins (Pipelines/Jenkinsfile) |
| **Infrastructure** | Terraform, AWS/GCP basics |
| **Monitoring** | Prometheus, Grafana, Micrometer, Zipkin |
| **Logging** | SLF4J, Logback, EFK Stack |
| **Code Quality** | Checkstyle, SpotBugs, SonarQube/SonarCloud |
| **Libraries** | Lombok, MapStruct, Resilience4j |
| **IDE** | IntelliJ IDEA |
| **Version Control** | Git, GitHub |

---

# Appendix B: Recommended Books

### 📚 Primary Reading List

These are the core books to follow throughout the plan:

| Book | When to Read | How to Use It |
|---|---|---|
| *Spring Start Here* — Laurentiu Spilca | **Week 1–3** (Spring foundations) | Read alongside Phase 1. Excellent beginner-friendly introduction to Spring concepts — DI, beans, controllers, data access. Start here before *Spring in Action*. |
| *Spring in Action* (6th ed.) — Craig Walls | **Week 2–6** (Spring Boot & beyond) | Your main Spring reference. Read chapter-by-chapter as you progress through Phase 1. Revisit advanced chapters (security, reactive, integration) during Phases 2–4. |
| *Clean Code* — Robert C. Martin | **Week 7–9** (After your first project) | Read after completing Phase 1 — you'll have enough code written to appreciate the principles. Apply refactoring lessons immediately to your OCR project. |
| *Effective Java* (3rd ed.) — Joshua Bloch | **Week 7–10** (Core Java deep dive) | Read during Phase 2's Java deep dives. Each item is self-contained — match items to the week's topic (e.g., generics items during Week 7, concurrency items during Week 8). |
| *Modern Java in Action* — Urma, Fusco, Mycroft | **Week 7–8 & Week 19** (Functional Java & Reactive) | Covers lambdas, streams, CompletableFuture, and reactive programming in depth. Read Part 1–3 during Week 7–8 (functional programming), Part 4 during Week 19 (reactive). |

### 📖 Supplementary Reading

Pick these up when you reach the relevant phase:

| Book | When to Read | Why |
|---|---|---|
| *Clean Architecture* — Robert C. Martin | Week 9 (Architecture week) | Complements *Clean Code* — focuses on system-level design, hexagonal architecture, and dependency rules. |
| *Domain-Driven Design Distilled* — Vaughn Vernon | Week 9 (DDD day) | Concise DDD primer — bounded contexts, aggregates, events. Much more approachable than Evans' original book. |
| *Java Concurrency in Practice* — Brian Goetz | Week 8 (Concurrency) | The definitive guide to Java threading. Dense but essential — focus on chapters about visibility, atomicity, and the Java Memory Model. |
| *Designing Data-Intensive Applications* — Martin Kleppmann | Week 15–16 (Microservices & messaging) | The bible of distributed systems. Read during the microservices/messaging phase for deep understanding of replication, partitioning, and consistency. |
| *Release It!* (2nd ed.) — Michael Nygard | Week 21 (Production readiness) | Stability patterns, anti-patterns, and operational concerns. Perfect companion for the monitoring/production week. |

> [!TIP]
> **Reading strategy:** Don't try to read books cover-to-cover before coding. Read the relevant chapters *alongside* each week's topic, then immediately apply what you read in your daily tasks. The best learning happens when theory and practice overlap.

---

# Appendix C: Weekly Time Allocation Template

| Activity | Hours/Week | Notes |
|---|---|---|
| Guided learning (reading, videos, docs) | 12–16h | Morning sessions |
| Hands-on coding (tasks, projects) | 16–20h | Afternoon sessions + project days |
| Self-assessment & review | 2–4h | Weekend checklists |
| **Total** | **~40h** | 8h/day × 5 days |

---

> [!TIP]
> **How to get the most out of this plan:**
> 1. **Don't skip the self-assessments.** If you can't answer a question, revisit the topic.
> 2. **Build everything yourself first**, then compare with tutorials. Struggling is learning.
> 3. **Commit code daily.** Your GitHub history is your proof of progress.
> 4. **Teach what you learn.** Write a blog post or explain concepts to someone — it reveals gaps.
> 5. **Revisit Phase 1 after Phase 2.** You'll see your early code with fresh eyes and know exactly what to improve.
