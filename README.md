# Quickcheck

Aplicativo para marcação de consultas.

## Pré-requisitos

- IDE: Intellij (recomendado)
- Java: 17
- Banco de Dados: MySQL Server + MySQL Workbench

## Instalação

Crie uma base de dados com o nome `quickcheck` no MySQL Workbench 

Modifique o username e o password no arquivo `resources` > `application.yml` para conectar na sua base de dados MySQL

Obs: 
- O username e o password precisam ser os mesmos da sua base de dados local
- Por padrão, o username é `root` e o password é `abc`

```
spring:
    datasource:
        url: ${JDBC_DATABASE_URL:jdbc:mysql://localhost:3306/quickcheck?serverTimezone=UTC}
        username: ${JDBC_DATABASE_USERNAME:root}
        password: ${JDBC_DATABASE_PASSWORD:abc}
```

Para inserir os dados mockados pela primeira vez, abra o arquivo `QuickcheckApplication.java` e altere o valor da variável `firstLoad` para `true`

Após inserir os dados, reverter a alteração (muda o valor de `firstLoad` para `false`)

Em seguida, no arquivo `QuickcheckApplication.java`, execute a aplicação (atalho: Shift + F10)

Obs: Lombok deve estar configurado na IDE. 
Se estiver usando o IntelliJ, instale o plugin `Lombok` e habilite `annotation processing` nas configurações -
[ver mais](https://bootify.io/next-steps/spring-boot-with-lombok.html).

Após, a API estará disponível no endereço `localhost:8080`.

## Equipe

- Flávio Raposo
- João Pedro Marinho
- José Adeilton
- Renan Leite Vieira
- Renan Vila Bela
- Rian Vinícius
- Robério José

## Referências

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
