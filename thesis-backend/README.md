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


to open swagger go to URL -> http://localhost:8080/thesis/swagger-ui/index.html