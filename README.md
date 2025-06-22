# Job Application Platform

O aplicație completă de tip CRUD pentru gestiunea aplicațiilor de angajare, cu backend în **Spring Boot** și frontend în **React.js**.  
Include gestiune pentru: aplicații, companii, joburi, interviuri, recenzii, utilizatori și roluri.

---

## Tehnologii folosite

### Backend (Spring Boot)
- Spring Web
- Spring Data JPA
- Spring Security
- Hibernate
- PostgreSQL

### Frontend (React)
- React 18+
- React Router DOM
- Fetch API
- CSS simplu
---
## Cum rulezi proiectul

### 1. Rulează backend-ul

Asigură-te că ai PostgreSQL instalat și creează o bază de date `job_application`.

Apoi în `application.properties` (sau `application.yml`) configurează:
spring.datasource.url=jdbc:postgresql://localhost:5432/job_application
spring.datasource.username=postgres
spring.datasource.password=parola_ta
spring.jpa.hibernate.ddl-auto=update

Pornește aplicația Spring Boot
Accesibil la: http://localhost:8080

Deschide un terminal în frontend/job-application-ui/ și rulează:
npm install
npm run dev
Accesibil la: http://localhost:5173

Funcționalități implementate CRUD complet pentru:

- Aplicări la joburi

- Companii

- Interviuri

- Joburi

- Recenzii

- Roluri

- Utilizatori

Spring Security cu roluri: USER, ADMIN

API REST complet

Interfață modernă în React

Autor
Bianca Cristea
Master Inginerie Software , UniBuc
2025