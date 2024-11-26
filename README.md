# TODO MANAGEMENT SYSTEM

## Overview
This project is a simple implementation of a `RESTful` API using **Spring Boot**. It is designed to serve as a foundational template for building Java-based RESTful web services. You can customize it according to your needs and extend the functionality by adding new endpoints, services, or integrations.

## 1. Introduction
This repository provides a template for a Spring Boot application configured with Maven. It includes some basic API endpoints, health-check capabilities, and this README file to help you get started.

The project is primarily for learning purposes and can be easily adapted to various use cases by extending the service layer or adding new routes.

## 2. Installation
To get started with the project:

1. **Clone the repository** to your local machine.
   ```bash
   git clone https://github.com/dembasowfr/todo-springboot.git
   cd todo-springboot
   ```

2. **Build the project** using Maven:
   ```bash
   ./mvnw clean install
   ```

3. **Start the application** with Spring Boot:
   ```bash
   ./mvnw spring-boot:run
   ```
   This will start the application on port `8080` by default.

## 3. Usage
Once the application is up and running, you can interact with it through various endpoints:

## 4. Testing with Postman

To test these endpoints using Postman:

1. Open Postman.
2. Create a new request and set the HTTP method (GET, POST, PUT, DELETE) as required.
3. For POST and PUT requests, provide the necessary JSON data in the "Body" tab.
4. Click **Send** to execute the request and view the response.

By following these steps, you can interact with the API and test all the available endpoints for `Company`, `User`, and `Task`.

![Postman API Endpoints Architecture](../todo/src/images/postman-api-endpoints.png)


#### 4.1. Company API Endpoints

- **Get All Companies**

  - **URL**: `http://localhost:8080/api/companies`
  - **Method**: `GET`
  - **Description**: Fetches all companies from the database.
  - **Response Example**:
    ```json
    [
        {
            "id": 1,
            "name": "Boom Incorporation.",
            "address": "Basak street 10",
            "email": "info@boom.com",
            "phoneNumber": "+53545545464",
            "website": "www.boom.com",
            "users": [
                {
                    "id": 1,
                    "name": "Don",
                    "surname": "Weasley",
                    "username": "ron",
                    "role": "STANDARD",
                    "email": "ron@example.com",
                    "tasks": [
                        {
                            "id": 1,
                            "title": "Swim",
                            "description": "Swim for 2 hours",
                            "completed": false
                        }
                    ]
                }
            ]
        },
        {
            "id": 2,
            "name": "Moom Inc.",
            "address": "123 Main Street",
            "email": "info@destech.com",
            "phoneNumber": "+924892347293",
            "website": "www.destech.com",
            "users": []
        }
    ]
    ```

- **Get Company By ID**

  - **URL**: `http://localhost:8080/api/companies/{id}`
  - **Method**: `GET`
  - **Description**: Fetch a company by its ID.
  - **Response Example**:
    ```json
    {
        "id": 1,
        "name": "Moom Inc.",
        "address": "123 Main Street",
        "email": "info@destech.com",
        "phoneNumber": "+924892347293",
        "website": "www.destech.com"
    }
    ```

- **Add a New Company**

  - **URL**: `http://localhost:8080/api/companies`
  - **Method**: `POST`
  - **Description**: Creates a new company.
  - **Request Body Example**:
    ```json
    {
        "name": "Moom Inc.",
        "address": "123 Main Street",
        "email": "info@destech.com",
        "phoneNumber": "+924892347293",
        "website": "www.destech.com"
    }
    ```


- **Update Company By ID**

  - **URL**: `http://localhost:8080/api/companies/{id}`
  - **Method**: `PUT`
  - **Description**: Updates an existing company by its ID.
  - **Request Body Example**:
    ```json
    {

        "name": "Boom Incorporation.",
        "address": "Basak street 10",
        "email": "info@boom.com",
        "phoneNumber": "+53545545464",
        "website": "www.boom.com"
    }
    ```
  - **Response Example**:
    ```json
    {
        "id": 1,
        "name": "Boom Incorporation.",
        "address": "Basak street 10",
        "email": "info@boom.com",
        "phoneNumber": "+53545545464",
        "website": "www.boom.com",
        "users": []
    }
    ```

- **Delete Company By ID**

  - **URL**: `http://localhost:8080/api/companies/{id}`
  - **Method**: `DELETE`
  - **Description**: Deletes a company by its ID.
  - **Response Example**:
    ```json
    {
      "message": "Company with ID 1 has been deleted."
    }
    ```


#### 4.2. User API Endpoints

- **Get All Users**

  - **URL**: `http://localhost:8080/api/users`
  - **Method**: `GET`
  - **Description**: Fetches all users.
  - **Response Example**:
    ```json
    [
        {
            "id": 1,
            "name": "Don",
            "surname": "Weasley",
            "username": "ron",
            "role": "STANDARD",
            "email": "ron@example.com",
            "tasks": [
                {
                    "id": 1,
                    "title": "Swim",
                    "description": "Swim for 2 hours",
                    "completed": false
                }
            ]
        },
        {
            "id": 2,
            "name": "Harry",
            "surname": "Potter",
            "username": "ron",
            "role": "STANDARD",
            "email": "harry@example.com",
            "tasks": []
        }
    ]
    ```

- **Get User By ID**

  - **URL**: `http://localhost:8080/api/users/{id}`
  - **Method**: `GET`
  - **Description**: Fetch a user by their ID.
  - **Response Example**:
    ```json
    {
        "id": 2,
        "name": "Rooon",
        "surname": "Weasley",
        "username": "ron",
        "role": "STANDARD",
        "email": "ron@example.com",
        "tasks": []
    }
    ```

- **Add a New User**

  - **URL**: `http://localhost:8080/api/users`
  - **Method**: `POST`
  - **Description**: Creates a new user.
  - **Request Body Example**:
    ```json
   {

        "name": "Rooon",
        "surname": "Weasley",
        "username": "ron",
        "role": "STANDARD",
        "email": "ron@example.com",
        "company": {
            "id": 1
        }
    }
    ```
  - **Response Example**:
    ```json
    {
        "id": 2,
        "name": "Rooon",
        "surname": "Weasley",
        "username": "ron",
        "role": "STANDARD",
        "email": "ron@example.com",
        "tasks": null
    }
    ```

- **Update User By ID**

  - **URL**: `http://localhost:8080/api/users/{id}`
  - **Method**: `PUT`
  - **Description**: Updates an existing user by their ID.
  - **Request Body Example**:
    ```json
    {
        "name": "Harry",
        "surname": "Potter",
        "username": "harry",
        "role": "STANDARD",
        "email": "harry@example.com"
    }
    ```
  - **Response Example**:
    ```json
    {
        "id": 2,
        "name": "Harry",
        "surname": "Potter",
        "username": "ron",
        "role": "STANDARD",
        "email": "harry@example.com",
        "tasks": []
    }
    ```

- **Delete User By ID**

  - **URL**: `http://localhost:8080/api/users/{id}`
  - **Method**: `DELETE`
  - **Description**: Deletes a user by their ID.
  - **Response Example**:
    ```json
    {
      "message": "User with ID 1 has been deleted."
    }
    ```

---

#### 4.3. Task API Endpoints

- **Get All Tasks**

  - **URL**: `http://localhost:8080/api/tasks`
  - **Method**: `GET`
  - **Description**: Fetches all tasks.
  - **Response Example**:
    ```json
    [
        {
            "id": 1,
            "title": "Swim",
            "description": "Swim for 2 hours",
            "completed": false
        },
        {
            "id": 2,
            "title": "Run",
            "description": "Run for 2 hours",
            "completed": true
        }
    ]
    ```

- **Get Task By ID**

  - **URL**: `http://localhost:8080/api/tasks/{id}`
  - **Method**: `GET`
  - **Description**: Fetch a task by its ID.
  - **Response Example**:
    ```json
    {
        "id": 1,
        "title": "Swim",
        "description": "Swim for 2 hours",
        "completed": false
    }
    ```

- **Add a New Task**

  - **URL**: `http://localhost:8080/api/tasks`
  - **Method**: `POST`
  - **Description**: Creates a new task.
  - **Request Body Example**:
    ```json
    {
        "title": "Run",
        "description": "Run for 2 hours",
        "completed": true,
        "user": {
            "id": 1
        }
    }

    ```
  - **Response Example**:
    ```json
    {
        "id": 2,
        "title": "Run",
        "description": "Run for 2 hours",
        "completed": true
    }
    ```

- **Update Task By ID**

  - **URL**: `http://localhost:8080/api/tasks/{id}`
  - **Method**: `PUT`
  - **Description**: Updates an existing task by its ID.
  - **Request Body Example**:
    ```json
    {
      "title": "Updated Task A",
      "description": "Updated description of Task A",
      "status": "completed"
    }
    ```
  - **Response Example**:
    ```json
    {
      "id": 1,
      "title": "Updated Task A",
      "description": "Updated description of Task A",
      "status": "completed"
    }
    ```

- **Delete Task By ID**

  - **URL**: `http://localhost:8080/api/tasks/{id}`
  - **Method**: `DELETE`
  - **Description**: Deletes a task by its ID.
  - **Response Example**:
    ```json
    {
      "message": "Task with ID 1 has been deleted."
    }
    ```


## 5. Contributing
We welcome contributions from the community! If you’d like to contribute to this project:

1. **Fork the repository** and clone it to your local machine.
2. **Create a new branch** for your changes.
3. **Make the necessary modifications** (add new endpoints, fix bugs, improve documentation, etc.).
4. **Submit a pull request** with a detailed description of what you’ve changed.

For major changes, it’s a good idea to **open an issue** first to discuss the proposed changes with the maintainers.

## 6. License
This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for more information.
