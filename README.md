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

**Pasos para ingresar a la consola en H2:**

1. Cargar la siguiente url en su navegador: http://localhost:8080/h2-console
2. Agregar parametros de conexión

	
	- JDBC UR: jdbc:h2:mem:demo5ml-loans
	- User Name: sa
	- Password:
	
3. Oprimir botón "Test connection"
4. Oprimir botón "Connect"
5. Ejecutar los siguientes scripts


## Scripts para validar información en la base de datos

```
select * from target;

select * from customer;

select * from loan;

select * from payment;


select * from loan where user_id = 5;
select * from payment where loan_id = 4;

```

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

**Obtener deuda por identificador del prestamo:**

```
curl -X GET localhost:8080/debt-by-loan/4 -H "Accept: application/json" -H "Content-Type: application/json"
```

**Obtener deuda de todos los prestamos:**

```
curl -X GET localhost:8080/debt-by-all-loans -H "Accept: application/json" -H "Content-Type: application/json" -d '{"date":"2022-08-07T23:59:59"}'
```

**Obtener deuda de los prestamos por target:**

```
curl -X GET localhost:8080/debt-by-target/PREMIUM -H "Accept: application/json" -H "Content-Type: application/json" -d '{"date":"2022-08-07T23:59:59"}'
```


**Modifica los parametros de un target:**

```
curl -X PUT http://localhost:8080/change-target/3/PREMIUM -H "Accept: application/json" -H 'Content-type:application/json' -d '{"minLoanCount":5, "maxLoanCount":1000, "minLoanAllowed":500000, "maxLoanAllowed":500000, "rate":0.07, "maxAmount":5000000}'
```


## Pruebas unitarias

Se realiza varias pruebas unitarias con las funciones más importantes con el fin de validar el correcto funcionamiento de la aplicación y para garantizar la cobertura de esta misma solución.


## Nota desarrollador

Se realiza el 99% de la aplicación siguiendo las instrucciones del challenge, el único inconveniente que se tuvo fué aplicar la formula para determinar el valor de la cuota al crear un prestamo.

Según el documento un prestamo de $1000 a 12 cuotas para un usuario PREMIUM cuyo interés es 0.05 genera un valor de cuota de 85.60, traté de generar ese mismo valor en un excel como en la aplicación y no logré tener éxito en esta formula.
