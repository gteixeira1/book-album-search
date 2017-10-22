# Book/Album Search App

### Application Overview
This App perform a web search of books, using Google Books API, and albums, using iTunes API, by a searchKey and returns a single result to the user.
It was created a REST service which receive this searchKey, provided by the user, as URI parameter and then perform the request on each API to get the result.

### Application Configuration
  - Java 1.8_71
  - Maven 3.3.9
  - SpringBoot 1.5.8.RELEASE

### Business Rules
  - The result is limited on 5 items per each API;
  - Each API request is independent and cannot be impacted by the process of the other API;
  - The application has a response time limit of 1 minute.
  - The result is ordered by Title

### Application Build / Start
This application was built using Spring Boot framework and it was used Maven as a project library manager.
To build the project using Maven, just run the maven command inside the project home directory:
```sh
mvn clean install
```
To start the application, you just need to run the spring-boot::run with maven command:
```sh
mvn spring-boot::run
```
You can also use the JAR file created by build command and start the application using a Java command.
After the build has successfully executed, a new JAR (*book-album-search-0.0.1-SNAPSHOT.jar*) file will be created inside *'target'*.
Then, to start the application using Java command, you just need to run the command:
```sh
java -jar target/book-album-search-0.0.1-SNAPSHOT.jar
```

### Application Endpoints
##### Search Book/Album
- Endpoint which perform the search of books and albums
- URL: http://localhost:8080/book-album-search/api/v1/book-album?searchKey=jones
- Method: GET
- URI Parameters: searchKey (required)

##### Search Books
- Endpoint which perform the only search books (only available on DEV environment)
- URL: http://localhost:8080/book-album-search/api/v1/books?searchKey=jones
- Method: GET
- URI Parameters: searchKey (required)

##### Search Book/Album
- Endpoint which perform the only search albums (only available on DEV environment)
- URL: http://localhost:8080/book-album-search/api/v1/albums?searchKey=jones
- Method: GET
- URI Parameters: searchKey (required)

### Sample Endpoint Tests
* http://localhost:8080/book-album-search/api/v1/book-album?searchKey=black
* http://localhost:8080/book-album-search/api/v1/book-album?searchKey=black%20sabbath
* http://localhost:8080/book-album-search/api/v1/book-album?searchKey=black%20sabbath%20iron
* http://localhost:8080/book-album-search/api/v1/book-album?searchKey=pink%20floyd%20mother
* http://localhost:8080/book-album-search/api/v1/book-album?searchKey=1qaz2wsx3edc4rfv
* http://localhost:8080/book-album-search/api/v1/albums?searchKey=andrea
* http://localhost:8080/book-album-search/api/v1/albums?searchKey=andrea%20bocelli
* http://localhost:8080/book-album-search/api/v1/books?searchKey=Hitchhiker%27s%20Guide
* http://localhost:8080/book-album-search/api/v1/books?searchKey=Uncharted

### Justification of Technology Choice
It was decided to use (*Spring Boot*) framework for this application because it's very simple to use and implement REST service.
Spring Boot has a embedded container which we can easily start a HTTP endpoint.
Also, I'm already working with Spring Boot and for this reason I' very familiar with this framework.

