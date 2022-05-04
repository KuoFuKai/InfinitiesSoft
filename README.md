# InfinitiesSoft Demo Project

---

This is a demo Restful project to build a book management system created for InfinitiesSoft, I using a couple of technology to realize it including: SpringBoot, H2 in-memory DB, Spring Data JPA etc...

## ****Requirements****

To begin using this project, please make sure you have already installed:

1. Java 11
2. Maven 3.X.X
3. Lombok
4. Docker (Optional)

## Data Structure

As a book management system, we using below structure to **CRUD** data.

| Field | Type |
| --- | --- |
| name | Mandantory |
| author | Mandantory |
| translator | Optional |
| isbn | Mandantory |
| publisher | Mandantory |
| publicationDate | Mandantory |
| listPrice | Mandantory |

All data types sets to String in order to make containing query easier to call.

We need to using above data structure as payload in create or update scenario, for instance:

```json
{
"name":"Java: From zero to give up",
"author":"KUO FU-KAI",
"translator":"WU YU-HUEI",
"isbn":"19930806",
"publisher":"HULIN STREET",
"publicationDate":"2022-05-05",
"listPrice":"500"
}
```

## Restful API Commands

### 1. Create Book

---

- **POST** /book
    
    Create a book.
    
    - ****Parameters****
        
        None
        
    - ****Payload****
        
        ***Required***
        
    - Response
        
        
        | Http Status | Response |
        | --- | --- |
        | 201 Created. | book with ID |
        | 500 Internal Server Error. | None |

### 2. Find Book

---

- **GET** /book
    
    Get a list of books by paramaters you input, or get all books by no input.
    
    - ****Parameters****
        
        
        | Mandantory | Optional |
        | --- | --- |
        |  | name=[alphanumeric] |
        |  | author=[alphanumeric] |
        |  | translator=[alphanumeric] |
        |  | isbn=[alphanumeric] |
        |  | publisher=[alphanumeric] |
        |  | publicationDate=[alphanumeric] |
        |  | listPrice=[alphanumeric] |
    - ****Payload****
        
        None
        
    - Response
        
        
        | Http Status | Response |
        | --- | --- |
        | 200 OK. | List of books |
        | 204 No Content. | None |
        | 500 Internal Server Error. | None |
- **GET** /book/{id}
    
    Get a book with ID.
    
    - ****Parameters****
        
        None
        
    - ****Payload****
        
        None
        
    - Response
        
        
        | Http Status | Response |
        | --- | --- |
        | 200 OK. | A book |
        | 404 Not Found | None |

### 3. Update Book

---

- **PUT** /book/{id}
    
    Update a book with ID.
    
    - ****Parameters****
        
        None
        
    - ****Payload****
        
        ***Required***
        
    - Response
        
        
        | Http Status | Response |
        | --- | --- |
        | 200 OK. | A book |
        | 404 Not Found. | None |
        | 500 Internal Server Error. | None |

### 4. Delete Book

---

- **DELETE** /book
    
    Delete all books.
    
    - ****Parameters****
        
        None
        
    - ****Payload****
        
        None
        
    - Response
        
        
        | Http Status | Response |
        | --- | --- |
        | 200 OK. | None |
        | 500 Internal Server Error. | None |
- **DELETE** /book/{id}
    
    Delete a book with ID.
    
    - ****Parameters****
        
        None
        
    - ****Payload****
        
        None
        
    - Response
        
        
        | Http Status | Response |
        | --- | --- |
        | 200 OK. | None |
        | 500 Internal Server Error. | None |

## Dockerize (Optional)

---

- Dockerfile
    
    ```docker
    FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.11_9-slim
    LABEL maintainer="kevin08062006@gmail.com"
    EXPOSE 8080
    ADD target/infinitiessoft_demo_project.jar infinitiessoft_demo_project.jar
    ENTRYPOINT ["java","-jar","/infinitiessoft_demo_project.jar"]
    ```
    
- Run below 2 codes in your cmd or Powershell.
    
    ```docker
    docker build -t infinitiessoft_demo_project.jar .
    docker run -p 9090:8080 spring-boot-docker.jar
    ```