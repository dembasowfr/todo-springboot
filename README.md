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


<!--- Here I will show the Postman architecture image --->

**Note:** You can also import the Postman collection by clicking the button below:
[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/19275856-91cd934f-a0ad-42a5-92fa-4ab2d976a8be?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D19275856-91cd934f-a0ad-42a5-92fa-4ab2d976a8be%26entityType%3Dcollection%26workspaceId%3Dd547e4b6-cfea-4a69-9aaa-ba0ed8658b77)


#### 4.1. Company API Endpoints

- **Get All Companies**

  - **URL**: `http://localhost:8080/api/companies?user_id={id}`
  - **Method**: `GET`
  - **Description**: 
  Gets the `user_role` from the `user_id`
  Fetches all companies from the database in case of `SUPER_USER`
  Fetches only the company associated with a specific user in case of `ADMIN_USER` or `STANDARD_USER`
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

  - **URL**: `http://localhost:8080/api/companies/{id}?user_id={id}`
  - **Method**: `GET`
  - **Description**: 
 
  Fetch any company by its ID in case of `SUPER_USER`
  Fetches the company associated with a specific user in case of `ADMIN_USER` or `STANDARD_USER`
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

  - **URL**: `http://localhost:8080/api/companies?user_id={id}`
  - **Method**: `POST`
  - **Description**: 
  Creates a new company in case of `SUPER_USER`
  Others users are not allowed to create a new company

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

  - **URL**: `http://localhost:8080/api/companies/{id}?user_id={id}`
  - **Method**: `PUT`
  - **Description**: 
  Updates any existing company by its ID in case of `SUPER_USER`
  Updates the company associated with a specific user in case of `ADMIN_USER` 
  `STANDARD_USER` can not update any company

  - **Request Body Example**:
    ```json
    {

        "name": "Boom Incorporation Updated.",
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
        "name": "Boom Incorporation Updated.",
        "address": "Basak street 10",
        "email": "info@boom.com",
        "phoneNumber": "+53545545464",
        "website": "www.boom.com",
        "users": []
    }
    ```

- **Delete Company By ID**

  - **URL**: `http://localhost:8080/api/companies/{id}?user_id={id}`
  - **Method**: `DELETE`
  - **Description**: 
  Only `SUPER_USER` can delete a company
  `ADMIN_USER` and `STANDARD_USER` can not delete any company
  If the company is deleted, all the users associated with the company will be deleted as well as well all the tasks associated with the users

  - **Response Example**:
    ```json
    {
      "message": "Company with ID 1 has been deleted."
    }
    ```


#### 4.2. User API Endpoints

- **Get All Users**

  - **URL**: `http://localhost:8080/api/users?user_id={id}`
  - **Method**: `GET`
  - **Description**: 
  `SUPER_USER` can fetches all users.
  `ADMIN_USER` can fetches all the users associated with their company including themselves and other ADMIN_USERs and STANDARD_USERs.

  `STANDARD_USER` can only view their own profile.

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

  - **URL**: `http://localhost:8080/api/users/{id}?user_id={id}`
  - **Method**: `GET`
  - **Description**: 
  `SUPER_USER` can view any user by their ID.
  `ADMIN_USER` can view any user associated with their company by their ID.
  `STANDARD_USER` can only view their own profile.

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

  - **URL**: `http://localhost:8080/api/users?user_id={id}`
  - **Method**: `POST`
  - **Description**: 
  `SUPER_USER` can create a new user within any company with any role.
  `ADMIN_USER` can create a new user within their company with any role except `SUPER_USER`.	
  `STANDARD_USER` can not create a new user.
  
  - **Request Body Example**:

    ```json
    {

        "name": "Rooon",
        "surname": "Weasley",
        "username": "ron",
        "role": "STANDARD_USER",
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
        "role": "STANDARD_USER",
        "email": "ron@example.com",
        "tasks": null
    }
    ```

- **Update User By ID**

  - **URL**: `http://localhost:8080/api/users/{id}?user_id={id}`
  - **Method**: `PUT`
  - **Description**: 
  `SUPER_USER` can updates an existing user by their ID.
  `ADMIN_USER` can updates an existing user associated with their company by their ID.
  `STANDARD_USER` can only update their own profile.

  - **Request Body Example**:
    ```json
    {
        "name": "Harry",
        "surname": "Potter",
        "username": "harry",
        "role": "STANDARD_USER",
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
        "role": "STANDARD_USER",
        "email": "harry@example.com",
        "tasks": []
    }
    ```

- **Delete User By ID**

  - **URL**: `http://localhost:8080/api/users/{id}`
  - **Method**: `DELETE`
  - **Description**: 
  `SUPER_USER` can delete any user by their ID.
  `ADMIN_USER` can delete any user associated with their company by their ID.
  `STANDARD_USER` can only delete their own profile.

  - **Response Example**:
    ```json
    {
      "message": "User with ID 1 has been deleted."
    }
    ```

---

#### 4.3. Task API Endpoints

- **Get All Tasks**

  - **URL**: `http://localhost:8080/api/tasks?user_id={id}`
  - **Method**: `GET`
  - **Description**: 
  `SUPER_USER` can fetches all tasks.
  `ADMIN_USER` can fetches all the tasks associated with their company including themselves and other ADMIN_USERs and STANDARD_USERs.
  `STANDARD_USER` can only view their own tasks.

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

  - **URL**: `http://localhost:8080/api/tasks/{id}?user_id={id}`
  - **Method**: `GET`
  - **Description**: 

  `SUPER_USER` can view any task by its ID.
  `ADMIN_USER` can view any task associated with their company by its ID.
  `STANDARD_USER` can only view their own tasks.


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

  - **URL**: `http://localhost:8080/api/tasks?user_id={id}`
  - **Method**: `POST`
  - **Description**: 
  `SUPER_USER` can assign a task to any user including themselves and other super users, admin users, and standard users.
  `ADMIN_USER` can assign a task to any user within their company including themselves and other admin users and standard users.
  `STANDARD_USER` can only assign a task to themselves.


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

  - **URL**: `http://localhost:8080/api/tasks/{id}?user_id={id}`
  - **Method**: `PUT`
  - **Description**: 
  `SUPER_USER` can update any task by its ID.
  `ADMIN_USER` can update any task associated with their company by its ID.
  `STANDARD_USER` can only update their own tasks.

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

  - **URL**: `http://localhost:8080/api/tasks/{id}?user_id={id}`
  - **Method**: `DELETE`
  - **Description**: 
  `SUPER_USER` can delete any task by its ID.
  `ADMIN_USER` can delete any task associated with their company by its ID.
  `STANDARD_USER` can only delete their own tasks.

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


This project follows the **standard GitHub flow**. The code can be optimized and refactored, and new features can be added.
For major changes, it’s a good idea to **open an issue** first to discuss the proposed changes with the maintainers.

## 6. License
This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for more information.
