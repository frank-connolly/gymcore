# Welcome to GymCore
## _A Just Java Project_ ☕

GymCore is a simple RESTful API for managing gym members and their subscriptions.

## Group Project Basics    
Before you open a PR you must:  
- Ensure all tests are passing ✅ 
- Run the application locally and test its working as expected ✅

### The Stack:
- Java version: 23
    - Installed with SDKMan:
      ```
      sdk install java 23.0.2-tem
      ```
- Spring Boot version: 3.4.2
- PostgresDB (H2 in-memory DB for tests)
- Keycloak (for authentication)
- Docker
- GitHub Actions

---
## Running the application locally

---
### Dev Profile
Specify 'dev' profile when running the application to trigger the dev profile. This profile will add basic test data to the local DB. 

If running via command line use `--spring.profiles.active=dev`
If running via IDE, add `dev` to the active profiles in the run configuration.

Your application will be available at http://localhost:8080

---
### Swagger UI
To access the Swagger UI, in your browser navigate to:

http://localhost:8080/api-docs

From there you will also see a link to the OpenAPI specification in raw JSON format.