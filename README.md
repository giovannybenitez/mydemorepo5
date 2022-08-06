# Instrucciones Generales

Antes de comenzar:

Esta aplicacion fue realizada en Java y Spring Boot y utiliza jpa para en manejo de bases de datos en H2.

Adicionalmente se siguen las recomendaciones que se especifican en el Challenge como por ejemplo:
- Arquitectura de la API
- Patrones de diseño
- Nomenclaturas de variables, métodos, clases e interfaces
- Documentación en el código (comentarios / javadocs)
- Responsabilidad de Clases
- Manejo de errores
- Testing / Cobertura


Al ejecutar la aplicación con spring boot, por defecto se ejecutará unos scripts de bases de datos que se encuentran en el archivo /src/main/resources/data.sql
sSi se desea ingresar a la consola de H2 para visualizar los datos, se debe realizar los siguientes pasos:

1. Cargar la siguiente url en su navegador: http://localhost:8080/h2-console
2. Agregar parametros de conexión
	- JDBC UR: jdbc:h2:mem:demo5ml-loans
	- User Name: sa
	- Password:
3. Oprimir botón "Test connection"
3. Oprimir botón "Connect"

- select * from target;
- select * from customer;
- select * from loan;

Postman collectioncurl -X GET localhost:8080/quiz -H "Accept: application/json" -H "Content-Type: application/json"

curl -X GET localhost:8080/collect-student-responses/1 -H "Accept: application/json" -H "Content-Type: application/json"


curl -X POST localhost:8080/create-student -H 'Content-type:application/json' -d '{"name":"Giovanny","age":34,"city":"Medellin","timeZone":"GMT-5"}'

curl -X POST localhost:8080/create-quiz -H 'Content-type:application/json' -d '{"quizId":null,"questions":[{"questionId":null,"question":"Capital de Colombia?","option1":"Medellin","option2":"Bogota","option3":"Cali","option4":"Armenia","correctAnswer":"Bogota","quiz":null},{"questionId":null,"question":"Cantidad departamentos tiene Colombia?","option1":"25","option2":"37","option3":"32","option4":"30","correctAnswer":"32","quiz":null}],"date":"2022-08-04T14:13:44.041+00:00"}'

curl -X PUT http://localhost:8080/set-quiz-schedule/17/2022-10-01 -H 'Content-type:application/json'
