# Customer Service

The objective of this service is to validate if a client exists and is active, to determine if it has contracted phishing detection to indicate that it is available.
The solution is based in request reply using rabbitMQ:

![image](https://github.com/user-attachments/assets/8b83cf7b-0d9b-4efd-aaf5-04286e479e8d)






## Getting Started

### Dependencies

- Java 17
- Maven 3.9.+
- Docker & Docker Componse

### Run Locally

- Execute the docker compose command for start up the components
    - The path base is: /.devcontainer/
        ```
        > docker compose up postgres rabbitmq -d
        ```

- The docker-compose should create two containers:
    - A postgres container with a testing DB with customers table
    - A rabbitmq container with a testing exchange with two queues.

- Start manually or via command the Application
    ```
    > mvn clean package
    > java -jar target/customer-0.0.1-SNAPSHOT.jar
    ```

### Run with DevContainer

#### Visual Studio Code
1. Install the "Dev Containers" extension in VS Code
2. Open the project folder in VS Code
3. Click the green button in the bottom-left corner or press Ctrl/Cmd + Shift + P and select "Dev Containers: Reopen in Container"
4. Wait for the container to build and start
5. The project will be ready to run inside the container

#### IntelliJ IDEA
1. Install the "Remote Development" plugin
2. Go to File > Open
3. Navigate to and select the project folder
4. Click "Trust Project"
5. Select "Dev Container" from the popup that appears
6. Wait for the container to build and start
7. The project will be ready to run inside the container

The devcontainer will automatically:
- Set up Java 17
- Install Maven
- Configure required environment variables
- Start PostgreSQL and RabbitMQ containers

### Test

For test the service, go to http://localhost:15672/#/queues, select the *testing.customer.query.is-customer-available* queue, and publish the follow message:
```
{
    "customer": "CUSTOMER1"
}
```

This message should be processed for the service, and produce a new event message to *testing.customer.response.is-customer-available* queue:
```
{
    "customer": "CUSTOMER1",
    "isAvailable": true
}
```
for consult the customer data, connect to postgres DB using the credentials in *customer/src/main/resources/application.properties* and execute:

```
    select * from customers;
```
result:

```
    +--+---------+------+------------------+
    |id|name     |status|phishing_detection|
    +--+---------+------+------------------+
    | 1|CUSTOMER1|true  |true              |
    | 2|CUSTOMER2|true  |false             |
    | 3|CUSTOMER3|false |true              |
    | 4|CUSTOMER4|false |false             |
    +--+---------+------+------------------+
```


## Deliverables

### Test Documentation
- Test Plan: `test_plan.md`
- Test Report & Results: `target/cucumber-reports/cucumber.html`

### Test Implementation
- Gherkin Scenarios: `src/test/resources/features/customer`
- Test Automation Project: `src/test/java/com/appgate/customer/cucumber/steps/CustomerE2ESteps.java`
- Test Environment Setup: This file

## Extras
### CI/CD
- Automated Test Pipeline
- Pipeline Documentation & Evidence: `pipeline.md`
