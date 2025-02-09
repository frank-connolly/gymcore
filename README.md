# Welcome to GymCore
## _A Just Java Project_ ☕

GymCore is a simple RESTful API for managing gym members and their subscriptions.

## Group Project Basics    
Before you open a PR you must:  
- Ensure all tests are passing ✅ 
- Run the application locally and test its working as expected ✅

### The Stack:
- Java
- Spring Boot
- PostgresDB (H2 in-memory DB for tests)
- Keycloak (for authentication)
- Docker
- GitHub Actions

### Dev Profile
Specify 'dev' profile when running the application to trigger the dev profile. This profile will add basic test data to the local DB. 

If running via command line use `--spring.profiles.active=dev`
If running via IDE, add `dev` to the active profiles in the run configuration.

### Swagger UI
To access the Swagger UI, navigate to `http://localhost:8080/api-docs` in your browser.
From there you will also see a link to the OpenAPI specification in raw JSON format.