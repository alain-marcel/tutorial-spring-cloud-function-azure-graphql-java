Package
=======
mvn clean package

Launch with ide_spring_boot__run_app.bat
========================================

POST http://localhost:8080/graphql with body { "query": "{pets{name}}" }
=> return {"data":{"pets":[{"name": "Billou" }, {"name": "Boullou" }


Launch with ide_azure__run_app.bat
==================================

NOTE : Class org.amarcel.lib.graphql.GraphQlHttpRequest must be in java.

POST http://localhost:7071/api/graphql with body { "query": "{pets{name}}" }
=> return error
