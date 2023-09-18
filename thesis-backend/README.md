# thesis-backend

This folder contains the backend source code for .thesis

# thesis-backend modules

The [backend-thesis](../README.md) module has the following sub-modules:

- `thesis-api` : The REST API
- `thesis-data` : The persistence module with ORM entities representation
- `thesis-domain` : The domain types
- `thesis-security` : All about security
- `thesis-server` : The main module that pulls the other modules together and provides the Spring Boot main class.

---

`db` : directory with sql schemas only for develop

> ### setup development environment
>
> **Backend server was written using Java 17, Spring Boot 2.7, Hibernate ORM 5.6.9 and starting on servlet Apache Tomcat 9**,
>
> 1.  install Java 17 and add to environment PATH. To check if environment see properly Java, write in terminal `java --version`
>
> - I suggest using [sdkman](https://sdkman.io/install) to manage parallel versions of multiple SDKs, especially Java
>   - after install sdkman simply run command on terminal: `sdk install java 17.0.5-amzn`. This should also update your PATH environment.
> - Alternative install some openjdk using old way for example from there -> [amazon corretto](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
>
> 2.  on terminal go to [thesis-backend](../thesis-backend) folder
> 3.  **_preparing database_**:
>
> - set up postgres using command: `make postgres`
> - create db using command: `make createdb`
> - run migration using command: `make migrateup`. For migration golang-migrate project was used. For install: 
> - - use documentation -> https://github.com/golang-migrate/migrate/tree/master/cmd/migrate 
> - - or execute `brew install golang-migrate` for MacOS or using Scoop for Windows `scoop install migrate`
> 4.  compile project using command: `./mvnw clean install`.
> 5.  go to [thesis-server](/thesis-server) folder
> 6.  run server using command: `../mvnw spring-boot:run `

---

> To check endpoints, open swagger -> http://localhost:8080/thesis/swagger-ui/index.html.
>
> To see tested requests and payloads, import [thesis.postman_collection.json](thesis.postman_collection.json) to your postman

---

> ### TO DO
>
> - JUNIT tests for thesis-domain module
> - contract test for thesis-api module
> - dockerization
