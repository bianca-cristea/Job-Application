# Job ApplicationAdd commentMore actions

Java Spring Boot Project
Aplicație web realizată în Java (Spring Boot) și React, pentru disciplina **Aplicații Web pentru Baze de Date** – Master, Anul I (2025).

## Funcționalități
## Autor
Cristea Bianca-Ștefania

- CRUD Operations
-
## Tehnologii folosite
- Java 21, Spring Boot, Spring Data JPA, Spring Security
- PostgreSQL (runtime)
- H2 (testare/profil alternativ)
- React
- JUnit 5 (testare de integrare)
- Spring Profiles (dev/test), logging

## Autor
Cristea Bianca-Stefania
## Funcționalități cheie
- CRUD complet pentru toate entitățile
- Relații JPA: OneToOne, OneToMany, ManyToOne, ManyToMany
- Validări, tratarea excepțiilor
- Autentificare cu Spring Security (JDBC)
- Paginare și sortare
- Testare unitară și integrată
- Profiluri multiple pentru baze de date

## Structura
- `backend/` – Spring Boot
- `frontend/` – React

## Rulare rapidă
##### Backend
./mvnw spring-boot:run

#### Frontend
cd frontend

npm install

npm start