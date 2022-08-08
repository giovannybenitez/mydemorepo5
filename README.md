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


## Eventos disponibles para ejecutar en Curl

**Crear solicitud de prestamo:**

```
curl -X POST localhost:8080/create-loan-request -H 'Content-type:application/json' -d '{"amount":1000,"term":12,"userId":5}'
```
**Obtener prestamos por fechas y paginado:**

```
curl -X GET localhost:8080/loans -H "Accept: application/json" -H "Content-Type: application/json" -d '{"from":"2019-01-01T00:00:00", "to":"2019-12-31T23:59:59", "page":0, "size":5}'
```
**Registrar un pago por prestamo:**

```
curl -X POST localhost:8080/register-payment -H 'Content-type:application/json' -d '{"loanId":28,"amount":0}'
```

**Obtener deuda por prestamo:**

```
curl -X GET localhost:8080/debt-by-loan/1 -H "Accept: application/json" -H "Content-Type: application/json" -d '{"date":""}'
```


**Cur de prueba:**

```
curl -X PUT http://localhost:8080/set-quiz-schedule/17/2022-10-01 -H 'Content-type:application/json'
```
