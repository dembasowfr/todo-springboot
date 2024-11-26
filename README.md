### TODO 

#### Overview
- [ ] 1. Introduction
- [ ] 2. Installation
- [ ] 3. Usage
- [ ] 4. Contributing
- [ ] 5. License

Tutorial on how to create a RESTful API using Spring Boot is available at [Introduction to Maven and its Lifecycle | Spring boot Maven project](https://www.youtube.com/watch?v=1e4jNP8iKLo)

#### 1. Introduction
This project is a simple implementation of a `RESTful` API using Spring Boot. It is intended to be used as a starting point for new projects.

#### 2. Installation
- Clone the repository
- Run `mvn clean install` to build the project
- Run `mvn spring-boot:run` to start the application

#### 3. Usage
- The application will start on port `8080`
- The API documentation can be found at `http://localhost:8080/swagger-ui.html`
- Check the health of the application at `http://localhost:8080/actuator/health`
- Check the metrics of the application at `http://localhost:8080/actuator/metrics`
- Check the info of the application at `http://localhost:8080/actuator/info`
- Invoke shutdown of the application at `http://localhost:8080/actuator/shutdown`


#### 4. Contributing
- Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

#### 5. License

- [MIT](https://choosealicense.com/licenses/mit/)


