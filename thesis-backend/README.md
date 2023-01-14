# thesis-backend

This folder contains the backend source code for .thesis

# thesis-backend modules

The [backend-thesis](../README.md) module has the following sub-modules:

- `thesis-api` : The REST API
- `thesis-data` : The persistence module
- `thesis-domain` : The domain types
- `thesis-security` : The security module
- `thesis-server` : The main module that pulls the other modules together and provides the Spring Boot main class.

---

`db` : directory with sql schemas only for develop


>### setup development environment
>
>1. install Java 17 and add to environment PATH. To check if environment see properly Java, write in terminal `java --version`
>2. on terminal go to [thesis-backend](../thesis-backend) folder
>3. ***preparing database***:
>   - set up postgres using command: `make postgres`
>   - create db using command: `make createdb`
>   3. run migration using command: `make migrateup`. if you don't have installed make engine, just go to .Makefile file and run command assigned to the one written above.
>4. compile project using command: `./mvnw clean install`.
>5. go to [thesis-server](/thesis-server) folder
>6. run server using command: `./mvnw spring-boot:run `

---
>To check endpoints, open swagger -> http://localhost:8080/thesis/swagger-ui/index.html.
> 
>To see tested requests and payloads, import [thesis.postman_collection.json](thesis.postman_collection.json) to your postman