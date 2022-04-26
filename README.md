# FinTechRepo
**Fin Tech Spring Boot Project Technical Details :**

Layered Architecture Description: 
Application is designed and developed based on layered architecture.Here we have
Three backend layers controller ,service and Repository.Below described each layer
implementation in detail.

Rest Api’s endpoints and response schema
The rest endpoints are exposed to use by consumers to retrieve data from backend
databases, based on input parameters.
Using Swagger UI we can go through api documentation.

Controller-Layer
Spring controller used to handle requests from consumer.Here spring controller
receives request along with required input data and based on action and
requestmapping configuration it will select the method to be executed.
In Controller class we have used multiple Spring annotations like @Controller on class
level @GetMapping ,@Postmapping,@pathVariable etc to perform and map controller
actions.
@restController to work controller class for restful service like http get,http post ,http
put ,http delete etc.

Service-Layer
Service Classes are designed and developed to provide service to controller classes
.With @Service annotation we define service class where we have implemented a
service method with business logic to interact with repository classes to provide
services.


Repository-Layer
Repository Classes are designed and developed to provide operations on database
data like add ,get, find,delete etc .With @Repository annotation we define a
repository class where we have used spring boot JPA CrudRepository of Spring
framework which provides methods to deal with database operations.


Entity-Layer
Model classes are a representation of database tables which are plain pojo classes .
With annotation @Entity we define a model class as a database table having the same
variables data members as table columns in the database.
3.6 Entity-Layer
For Security purposes I have implemented the JWT security mechanism. One
username authenticated successfully will generate a jwt security token and this token
needs to be sent with each request through the header as a bearer authentication
element.


Database
For development purposes we have used Spring Boot inbuilt database H2
dependencies which provides runtime database service to create tables and perform
database activities like create update select delete etc.For this we have added H2
dependency while creating Spring Boot starter project.

Technical configuration
For developing this app we have used the Spring Boot starter project usingIntelliJ IDE.
Created a new Spring starter project with initial dependencies,Spring
web,WebSecurity,Spring-mail-sender, H2 database,JPA,DevTools,lombok,OpenAPI etc.
For deploying web applications we have used Spring Boot inbuilt tomcat server for
testing purposes and JAVA 11 development environment used to develop java
applications.

