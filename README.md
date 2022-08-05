# Instructions

curl -X GET localhost:8080/quiz -H "Accept: application/json" -H "Content-Type: application/json"

curl -X GET localhost:8080/collect-student-responses/1 -H "Accept: application/json" -H "Content-Type: application/json"


curl -X POST localhost:8080/create-student -H 'Content-type:application/json' -d '{"name":"Giovanny","age":34,"city":"Medellin","timeZone":"GMT-5"}'

curl -X POST localhost:8080/create-quiz -H 'Content-type:application/json' -d '{"quizId":null,"questions":[{"questionId":null,"question":"Capital de Colombia?","option1":"Medellin","option2":"Bogota","option3":"Cali","option4":"Armenia","correctAnswer":"Bogota","quiz":null},{"questionId":null,"question":"Cantidad departamentos tiene Colombia?","option1":"25","option2":"37","option3":"32","option4":"30","correctAnswer":"32","quiz":null}],"date":"2022-08-04T14:13:44.041+00:00"}'

curl -X PUT http://localhost:8080/set-quiz-schedule/17/2022-10-01 -H 'Content-type:application/json'
