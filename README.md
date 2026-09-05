# Identity Card Processing Service

A Spring Boot service for processing **Romanian identity cards** in both **old** and **new** formats.  
It can:

- receive one or two uploaded card images
- extract identity data with OCR and AI assistance
- validate extracted data
- save both the parsed identity card and uploaded image to the database
- search saved identity cards
- return image metadata
- download stored images

---

## 📚 Table of Contents

1. [Overview](#-overview)
2. [Frameworks & Technologies Used](#-frameworks--technologies-used)
3. [Main Features](#-main-features)
4. [API Endpoints](#-api-endpoints)
5. [Request Flow](#-request-flow)
6. [Code Deep Dive](#-code-deep-dive)
7. [Validation Rules](#-validation-rules)
8. [Persistence Layer](#-persistence-layer)
9. [Features To Improve](#-features-to-improve)
10. [Testing Strategy](#-testing-strategy)

---

## 🚀 Overview

This application is designed to process official Romanian identity cards by combining:

- **Spring Boot** for the application structure
- **Spring AI** for OCR/extraction and card classification
- **JPA/Hibernate** for persistence
- **Liquibase** for schema management
- **H2** for local development database support

The system supports both:
- **CI** = old Romanian identity card format
- **CEI** = new Romanian identity card format

---

## 🧠 Frameworks & Technologies Used

For this project, the following frameworks and tools were used:

1. **Spring Boot**  
   Used for application setup, dependency injection, configuration, and runtime management.

2. **Spring Web**  
   Used to expose REST endpoints and handle HTTP requests/responses.

3. **Spring Data JPA**  
   Used for database access and repository abstraction.

4. **Spring AI**  
   Used to communicate with the AI model for OCR, card classification, and structured extraction.

5. **Liquibase**  
   Used for database versioning and schema migrations.

6. **H2 Database**  
   Used as a lightweight local database for development and testing.

7. **SLF4J**  
   Used for structured logging across controllers, services, and utilities.

8. **Gradle**  
   Used for build automation and dependency management.

9. **Insomnia**  
   Used for manual API testing and post-install verification.

10. **JUnit & Mockito**  
    Used for unit testing and mocking dependencies.

11. **Swagger / OpenAPI**  
    Used for documenting and exploring the API.

12. **Lombok**  
    Used to reduce boilerplate code such as getters, setters, and constructors.

---

## 📋 Main Features

The service supports the following operations:

- Upload and process one or two identity card images
- Detect whether the card is **CI** or **CEI**
- Extract identity data into structured JSON
- Validate extracted business data, especially the **CNP**
- Persist identity card data into the database
- Persist uploaded image data and metadata
- Search saved identity cards by:
    - UID
    - CNP
    - first name
    - last name
- Retrieve image metadata by UID
- Download the stored image by UID

---

## 🎯 API Endpoints

The service exposes 4 endpoints:

### 1. `POST /api/ic-reader`
Receives one or two uploaded identity card images, performs OCR and validation, then saves both:
- the extracted identity card data
- the uploaded image

#### Purpose
- OCR the card
- validate extracted data
- persist card and image data

---

### 2. `GET /api/search`
Searches through saved identity cards using customizable pagination.

#### Supported search fields
- UID
- CNP
- first name
- last name

#### Purpose
- retrieve saved identity cards from the database

---

### 3. `GET /api/image/metadata/{uid}`
Returns metadata for the uploaded image associated with a given identity card UID.

#### Example metadata
- file name
- content type
- file size
- created at

---

### 4. `GET /api/image/{uid}`
Returns the stored image for a given identity card UID.

#### Purpose
- download the uploaded image

---

## 🔧 Request Flow

A simplified flow for the main upload operation:

1. Client uploads one or two images
2. Files are validated:
    - required first file
    - allowed content type
    - maximum size
3. AI classifies the card format:
    - `CI`
    - `CEI`
4. AI extracts structured identity card data
5. Extracted CNP and validity dates are validated
6. Data is mapped to the `IdentityCard` entity
7. Identity card is saved to the database
8. Uploaded image is saved to the database
9. Saved entity is mapped to response DTO and returned

---

## 📁 Code Deep Dive

This section explains the main components and how they work together.

### 1. Controller Layer

The controller layer exposes the REST API and delegates business logic to services.

#### `OcrController`
Responsible for:
- receiving image uploads
- delegating OCR and persistence to the service layer
- exposing search and image-related endpoints

Main responsibilities:
- `POST /api/ic-reader`
- `GET /api/search`

#
#### `DownloadImageController`
Responsible for:
- returning image metadata
- returning image binary data for download

The controllers are intentionally thin: they do not contain business logic, only request handling and delegation.

---

### 2. Service Layer

The service layer contains the main business logic.

#### `OcrService`
This service is responsible for:
- validating uploaded files
- loading AI prompts from resources
- classifying the card model
- extracting card data using Spring AI

Main internal steps:
- validate required image
- validate content type and size
- build `ByteArrayResource` from uploaded file
- classify card type as `CI` or `CEI`
- select the correct extraction prompt
- call AI again to extract structured data

Important validation rules:
- first file is mandatory
- allowed file types:
    - `image/jpeg`
    - `image/jpg`
    - `image/png`
- max file size: `10 MB`

#
#### `IdentityCardService`
This service orchestrates:
- OCR result usage
- business validation
- mapping to entity
- saving to database
- saving image data
- mapping entity to response DTO

Flow:
1. call `ocrService.ocr(...)`
2. validate extracted card data
3. map extraction result to `IdentityCard`
4. save entity
5. save image using `ImageService`
6. return `ResponseId`

#
#### `ImageService`
Responsible for:
- saving uploaded image data in the database
- retrieving image data by UID

It links an uploaded image to the saved `IdentityCard` entity and stores:
- raw bytes
- content type
- file name
- file size
- creation timestamp

---

### 3. Utility Layer

The utility layer centralizes reusable validation and parsing logic.

#### `DateUtil`
Parses OCR date strings into `LocalDate`.

Supported formats:
- `dd.MM.yyyy`
- `dd.MM.yy`

This is useful because OCR may return dates in slightly different formats.

#
#### `IdentityCardUtil`
Validates extracted identity card data, especially:
- **CNP checksum**
- card validity period

Validation includes:
- checking the CNP control digit
- ensuring `validFrom` is before the current date
- ensuring `validTo` is after the current date

#
#### `IdentityCardService`
Holds configuration properties such as:
- whether invalid CNPs are allowed

This makes the validation behavior configurable without changing code.

---

### 4. Mapper Layer

The mapper layer transforms data between:
- OCR DTOs
- entities
- response DTOs

#### `ExtractionToIdentityCard`
Maps `IdExtractionResult` to `IdentityCard`.

Responsibilities:
- copy extracted text fields
- parse date strings using `DateUtil`
- map MRZ lines
- map card model, CNP, names, address, validity, etc.

#
#### `IdentityCardToResponseId`
Maps `IdentityCard` to `ResponseId`.

Used when returning saved identity card data to the client.

#
#### `ImageDataToMetadata`
Maps `ImageData` to `MetadatasResponse`.

Used for the metadata endpoint.

#
#### `ResponseIdentityToPageResponse`
Wraps paginated search results into a custom `PageResponse<ResponseId>` DTO.

---

### 5. Entity Layer

The entity layer represents database tables.

#### `IdentityCard`
Represents the main saved identity card data.

Stores:
- UID
- card model
- CNP
- names
- address
- sex
- citizenship
- issue/validity dates
- MRZ lines

It also generates a UUID automatically in `@PrePersist` if not already set.

#
#### `ImageData`
Represents the uploaded image and its metadata.

Stores:
- binary image data
- content type
- file name
- file size
- creation timestamp
- relation to `IdentityCard`

---

### 6. Repository Layer

Repositories abstract database access.

#### `IdentityCardRepository`
Used for:
- saving identity cards
- searching by UID, CNP, first name, and last name

#
#### `ImageRepository`
Used for:
- saving image data
- retrieving image data by identity card ID

---

### 7. Exception Handling

The application uses a global exception handler to standardize API error responses.

Handled cases include:
- maximum upload size exceeded
- invalid business data
- generic runtime exceptions
- `ResponseStatusException` preserving its original HTTP status

Examples:
- invalid file type → `415 Unsupported Media Type`
- file too large → `413 Content Too Large`
- invalid identity card data → `400 Bad Request`

---

## 💻 Validation Rules

The following validations are applied:

### File validation
- first file must exist
- file must not be empty
- file type must be:
    - JPEG
    - JPG
    - PNG
- file size must not exceed 10 MB

### OCR/business validation
- extracted card model must be recognized as:
    - `CI`
    - `CEI`
- CNP must pass checksum validation
- validity dates must define a currently valid document, unless configuration allows invalid CNPs

---

## 🔬 Persistence Layer

The application persists two main data types:

### Identity card data
Saved in the `identity_cards` table.

### Uploaded image data
Saved in a separate image table and linked to the identity card.

This separation allows:
- efficient search on identity data
- independent retrieval of image metadata and binary image content

---

## 🔑 Testing Strategy

The project includes:
- **unit tests** for services, utilities, and mappers
- **controller tests** for endpoint delegation
- **manual API verification** through Insomnia
- **post-install verification scenarios** for:
    - valid upload
    - invalid file type
    - oversized file
    - invalid business data
    - search
    - image retrieval
    - metadata retrieval

---

## 🔧 Features to improve
- Changing the logic of the `ImageService` to return two pictures for CEI identity cards
- Custom exception types instead of generic `RuntimeException`
- Isolate Spring AI fluent calls behind a dedicated client abstraction
- Security

---

## 🎓 Summary

This project demonstrates a complete backend workflow for Romanian identity card processing:

- upload
- OCR
- validation
- persistence
- search
- metadata retrieval
- image download

It combines modern Spring technologies with AI-assisted OCR and a clean layered architecture, making it a solid foundation for further improvements and production hardening.