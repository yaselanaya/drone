**Drones**

_Application to manage the drones and medications._


**Technologies used to implement the application.**
- Spring Boot 2.3.2
- JVM 11
- H2 2.1.4
- Hibernate 5.4.18
- Swagger 2.9.2
- Apache Maven

**Specifications to use the application.**

- Was used h2 as database to implement the solution, in the resource path there is a script which populate 
the database with drones and medications, to access to the database after running the application the user
  has to open the link **http://localhost:8080/h2-console** in the browser and connect to the database using 
  the following configuration:
  
  `Driver Class: org.h2.Driver`
  
  `JDBC URL: jdbc:h2:mem:drone`

  `UserName: drone`

  `Password: drone`

- Was integrated swagger to document the API and to test all endpoints associated with the logic of the application,
  to access to the swagger the user has to open the URL **"http://localhost:8080/swagger-ui.html"** in the browser, and
  the user will see the API documentation to uses the different endpoints.

- Was used Apache Maven to the automated build, to compile the application execute **"mvn clean package"** and
  in the **"target"** path execute the jar with the command **"java -jar drone-0.0.1-SNAPSHOT.jar"**.

- In the **"postman_collection"** path of the project, there is a JSON file called **"Drone Api Rest.postman_collection.json"**
  with the collection of the requests to test in Postman, import the file, and executed in order of the appear.
  
- A Schedule Task was implemented to run every hour to check the battery of the drones and save the logs in the event log table. 

- To list the data was created a JSON string to the parameter with the format:
  {"search": "","filters":[{"field":"fieldName", "operator":"EQUALITY", "value":"fieldValue"}],"pageable":{"page":0,"size":200,"orders":[{"direction":"DESC","property":"propertyName"}]}}

Where:
**search key**: In the case of the drones filter all the drones that its serial number contain the string value,
and in the case of the medications filter all the medications that its name or code contain the string value.

**filter key**: This keys is an array of the objects that contain three keys each one, the field that represents the name of the field,
the operator that represents the SQL operator that the query will be uses, and the value.

**e.g: {"field":"status", "operator":"EQUALITY", "value":"IDLE"}**

This filter search all the drones with the state IDLE.

**pageable key**: This key is an object used to set the limit of the rows listed, and the order of the elements,
it has three keys(page, size and the orders), the orders key is an array with the asc or desc order per a field.
