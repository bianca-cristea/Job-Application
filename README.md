# Job Application

Java Spring Boot Project

## Functinalities

- CRUD Operations
- 

### Endpoints for jobs
GET /jobs -> get all jobs 

GET /jobs/{id} -> get a specific job by id

POST /jobs-> create a new job

DELETE /jobs/{id} -> delete a specific job by id

PUT /jobs/{id} -> edit a job

GET /jobs/{id}/company -> get the company associated with a specific job by id

### Endpoints for company
GET /companies -> get all companies

PUT /companies/{id} -> update company by id

POST /companies -> create company

DELETE /companies/{id} -> delete a company

GET /companies/{id} -> get a company by id

### Endpoints for review
GET /companies/{companyId}/reviews

POST /companies/{companyId}/reviews

GET /companies/{companyId}/reviews/{reviewId}

PUT /companies/{companyId}/reviews/{reviewId}

DELETE /companies/{companyId}/reviews/{reviewId}

### Endpoints for role
GET /roles -> get all roles

PUT /roles/{id} -> update company by id

POST /roles -> create company

DELETE /roles/{id} -> delete a company

GET /roles/{id} -> get a company by id

## Author
Cristea Bianca-Stefania


