Context
=======
GraphQL using Spring Boot works well.
See for example https://github.com/sambenskin/graphql-spring-boot-tutorial/blob/master/Readme.md
Just download and execute.

GraphQL with Spring Cloud Function and azure is the challenge.
This application is an "hello world" application, but it still not works. 


Package the application
=======================
Download from this site and execute : mvn clean package

Launch with ide_spring_boot__run_app.bat
========================================

POST http://localhost:8080/graphql with body { "query": "{pets{name}}" }
=> return {"data":{"pets":[{"name": "Billou" }, {"name": "Boullou" }


Launch with ide_azure__run_app.bat
==================================

NOTE : Class org.amarcel.lib.graphql.GraphQlHttpRequest must be in java.

POST http://localhost:7071/api/graphql with body { "query": "{pets{name}}" }
=> return error
