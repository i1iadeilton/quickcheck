# Quickcheck

Appointment scheduling application.

## Prerequisites

- IDE: IntelliJ (recommended)
- Java: 17
- Database: MySQL Server + MySQL Workbench

## Installation

Create a database named `quickcheck` in MySQL Workbench.

Modify the `username` and `password` in the `resources` > `application.yml` file to connect to your MySQL database.

Note:
- The `username` and `password` must match your local database credentials.
- By default, the username is `root` and the password is `abc`.

```yaml
spring:
    datasource:
        url: ${JDBC_DATABASE_URL:jdbc:mysql://localhost:3306/quickcheck?serverTimezone=UTC}
        username: ${JDBC_DATABASE_USERNAME:root}
        password: ${JDBC_DATABASE_PASSWORD:abc}
```

To insert the mock data for the first time, open the `QuickcheckApplication.java` file and set the `firstLoad` variable to `true`.

After inserting the data, revert the change (set `firstLoad` back to `false`).

Next, run the application in the `QuickcheckApplication.java` file (shortcut: Shift + F10).

Note: Lombok must be configured in the IDE.  
If using IntelliJ, install the `Lombok` plugin and enable `annotation processing` in the settings —  
[see more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

After that, the API will be available at `localhost:8080`.

## Adding new routes

Edit the `repo` file of the desired entity (e.g., `HorarioRepository`), using the link below as a reference:

    https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

Next, edit the respective `service` file (e.g., `HorarioService`) to add business logic and validation rules.

Finally, modify the `resource` file (e.g., `HorarioResource`) to add the desired route and the parameters to be used.

In summary:

    repo > service > resource

## Team

- Flávio Raposo
- João Pedro Marinho
- José Adeilton
- Renan Leite Vieira
- Renan Vila Bela
- Rian Vinícius
- Robério José

## References

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
