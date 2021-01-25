# Car Rental Service 

REST API for a simple car rental service.
 
## Description

A REST API that fulfil the requirements detailed below:

Register a car with its plate number, model and year

Register availability of a car from date/time, to date/time, rental price per hour

Search for available cars to rent from date/time, to date/time, maximum rental price per hour

Register a user (to rent a car)

Book a car from date/time, to date/time, user id, car id

## Running

1. Java 8 and Maven 3 should be installed.

2. Change directory to the root folder of the application.

3. Run the below maven command to generate the JAR file.

```bash
mvn clean package
```

4. Run the JAR file

```bash
java -jar target/car-rental-service-0.0.1-SNAPSHOT.jar
```

5. Go to the below URL - the URL will be redirected to Swagger UI.

```bash
http://localhost:8080
```

## Endpoints

1. register user (/users) - HTTP POST

- Sample Request - body

```
{
  "email": "mark.yani@gmail.com",
  "name": "Mark Yani"
}
```

- Sample Response

```
{
  "id": 1,
  "created": "2021-01-25T02:07:47.07",
  "name": "Mark Yani",
  "email": "mark.yani@gmail.com"
}
```

2. register car (/cars) - HTTP POST

- Sample Request - body

```
{
  "model": "BMW - X6",
  "plateNumber": "AWE2443",
  "year": 2019
}
```

- Sample Response

```
{
  "id": 1,
  "created": "2021-01-25T02:09:32.519",
  "plateNumber": "AWE2443",
  "model": "BMW - X6",
  "year": 2019
}
```

3. update car availability (/cars/availability/{carId}) - HTTP PUT

- Sample Request (/cars/availability/1) - body

```
{
  "availableFrom": "2021-05-15T21:00",
  "availableTo": "2021-10-20T21:00",
  "pricePerHour": 10
}
```

- Sample Response

```
{
  "id": 1,
  "created": "2021-01-25T02:15:30.827",
  "plateNumber": "AWE2443",
  "model": "BMW - X6",
  "year": 2019,
  "pricePerHour": 10,
  "availabeFrom": "2021-05-15T21:00:00",
  "availabeTo": "2021-10-20T21:00:00"
}
```

4. search cars availability (/cars) - HTTP GET

- Sample Request - body

```
/cars?maxPricePerHour=100&page=0&pageSize=5&rentFrom=2021-07-17T21%3A00&rentTo=2021-07-25T21%3A00
```

- Sample Response

```
[
  {
    "id": 2,
    "created": "2021-01-25T02:22:33.009",
    "plateNumber": "34DS3",
    "model": "Volvo",
    "year": 2005,
    "pricePerHour": 10,
    "availabeFrom": "2021-06-20T21:00:00",
    "availabeTo": "2022-07-20T21:00:00"
  },
  {
    "id": 1,
    "created": "2021-01-25T02:15:30.827",
    "plateNumber": "AWE2443",
    "model": "BMW - X6",
    "year": 2019,
    "pricePerHour": 10,
    "availabeFrom": "2021-05-15T21:00:00",
    "availabeTo": "2021-10-20T21:00:00"
  }
]
```

5. create booking (/bookings) - HTTP POST

- Sample Request - body

```
{
  "carId": 1,
  "userId": 1,
  "beginning": "2021-06-15T21:00",
  "end": "2021-06-20T21:00"  
}
```

- Sample Response

```
{
  "id": 1,
  "created": "2021-01-25T02:25:07.536",
  "beginning": "2021-06-15T21:00:00",
  "end": "2021-06-20T21:00:00",
  "user": {
    "id": 1,
    "name": "Mark Yani",
    "email": "mark.yani@gmail.com"
  },
  "car": {
    "id": 1,
    "plateNumber": "AWE2443",
    "model": "BMW - X6",
    "year": 2019
  }
}
```

## Technologies

1. Java 8

2. Spring boot with Spring data JPA and Hibernate.

3. Maven 3

4. H2 database

5. Junit 4

6. Swagger UI

7. SonarLint
