# Rest API Service Using Spring-boot and DynamoDB

### Intro:

    This is a almost production ready practice project using spring boot and DynamoDB. 
    Anyone can use this with little modification based on their requirment.

### Technology used:

- Java
- Spring boot
- Reactive programming or Web-flux
- DynamoDB
- Docker
- AWS SDK + AWS CLI
- Shell Script
- Web-flux spring security (JWT authentication)

### Pre-requisites:

- JDK 17
- IDE (intellij recommended)
- Docker version 24+
- API testing client (postman recommended)

### Build instructions:

- Clone the repo
- Open in IDE and load the `pom.xml` file dependency
- run `docker-compose up --build` command at the project root directory

### Testing or run process:

- run `docker-compose up --build` command at the project root directory
  (you can skip this step if you already run this command)
    - You can use both `localstack` or `dynamoDb` docker image present in `docker-compose`
      file. Just comment-in and comment-out what you like.
    - Please use the dynamoDb endpoint property `aws.dynamoDb.endpoint` based on your image choice
- set active profile for application run
- run the project from the IDE or cli

### Auth

- Sign up a user using sign-up api and one of the role `USER` or `ADMIN`
- Now login using credentials and collect access token

### Functionality:

- Login
- Registration
- Refresh token
- Todo
    - Add todo
    - Get todo by id
    - Get all todo by email
    - Update todo
    - Delete todo
    - Get todo by status