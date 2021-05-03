# API Json Diff
This project offer an API to check diff between encoded Base64 data.

## Requirements
Java 11+ \
Maven 3+

## Stack
Spring Boot 2.4.5 \
Swagger \
REST-assured (integration tests)

## Usage
Startup application
```
mvn spring-boot:run
```

Add left content to diff
```
PUT 
/v1/diff/{id}/left 
```
Add right content to diff
```
PUT 
/v1/diff/{id}/right 
```
Result sounds like: 
```
{
  "result": "Offset: [8] Length: [11]"
}
```

## API Documentation
http://localhost:8080/swagger-ui/
