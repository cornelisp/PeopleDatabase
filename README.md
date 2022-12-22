# People Database Web 

This project is a backend CRUD application for managing a database of people. It is written in Java and uses JUnit, JDBC, SQL, H2 Database, Intellij IDE, and Gradle as the build tool.

## Features

- Create, read, update, and delete (CRUD) operations for people in the database
- Ability to search for people by id or other condition.
- Ability to delete multiple people at once.


## Technologies

* Java 
* JUnit 
* JDBC
* SQL
* H2 Database
* Gradle

## Installation

1. Clone the repository
2. Open the project in Intellij IDE
3. Run the Gradle build task
4. Run the application

### SQL Script ###

```
CREATE TABLE PEOPLE(ID BIGINT AUTO_INCREMENT, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255), DOB TIMESTAMP, SALARY DECIMAL);

INSERT INTO PEOPLE (FIRST_NAME, LAST_NAME, DOB, SALARY) VALUES('Tom','Cruise','1990-03-12 12:12:10',10000.00)

select * from PEOPLE
```


## Usage

Once everything is running, you can use the CRUD operations to manage the people in the database. I suggest testing functionality with tests before running the program, then you can use available operations such as finding by id, deleting multiple people, counting and more.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


Please make sure to update tests as appropriate.
