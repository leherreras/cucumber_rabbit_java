# Test Plan for Customer Project

## Objective
Validate that the system meets the following conditions:
1. The customer must exist in the system.
2. The customer must be in an active state.
3. The customer must have the phishing detection service active.

---

## Conditions and Test Cases

### 1. The Customer Must Exist in the System

#### Positive Case: Customer Exists in the System
- **Description**: Verify that the system correctly recognizes a customer that exists in the database.
- **Preconditions**:
  - The customer "JohnDoe" is registered in the database.
- **Steps**:
  1. Query the availability of the customer "JohnDoe".
- **Expected Result**:
  - The system should respond that the customer is "available".

#### Negative Case: Customer Does Not Exist in the System
- **Description**: Verify that the system correctly handles the query for a customer that does not exist.
- **Preconditions**:
  - The customer "UnknownCustomer" is not registered in the database.
- **Steps**:
  1. Query the availability of the customer "UnknownCustomer".
- **Expected Result**:
  - The system should respond that the customer is "not available".

---

### 2. The Customer Must Be in an Active State

#### Positive Case: Customer Is Active
- **Description**: Verify that the system correctly recognizes a customer that is active.
- **Preconditions**:
  - The customer "JohnDoe" is registered in the database.
  - The customer "JohnDoe" is in an active state.
- **Steps**:
  1. Query the availability of the customer "JohnDoe".
- **Expected Result**:
  - The system should respond that the customer is "available".

#### Negative Case: Customer Is Not Active
- **Description**: Verify that the system correctly handles the query for a customer that is not active.
- **Preconditions**:
  - The customer "JohnDoe" is registered in the database.
  - The customer "JohnDoe" is not active.
- **Steps**:
  1. Query the availability of the customer "JohnDoe".
- **Expected Result**:
  - The system should respond that the customer is "not available".

---

### 3. The Customer Must Have the Phishing Detection Service Active

#### Positive Case: Customer Has Phishing Detection Active
- **Description**: Verify that the system correctly recognizes a customer that has the phishing detection service active.
- **Preconditions**:
  - The customer "JohnDoe" is registered in the database.
  - The customer "JohnDoe" has the phishing detection service active.
- **Steps**:
  1. Query the availability of the customer "JohnDoe".
- **Expected Result**:
  - The system should respond that the customer is "available".

#### Negative Case: Customer Does Not Have Phishing Detection Active
- **Description**: Verify that the system correctly handles the query for a customer that does not have the phishing detection service active.
- **Preconditions**:
  - The customer "JohnDoe" is registered in the database.
  - The customer "JohnDoe" does not have the phishing detection service active.
- **Steps**:
  1. Query the availability of the customer "JohnDoe".
- **Expected Result**:
  - The system should respond that the customer is "not available".

### 4. Message Format Validation for Queue

#### Negative Case: Malformed Message
- **Description**: Verify that the system correctly handles malformed messages.
- **Preconditions**:
  - The customer "JohnDoe" is registered in the database.
- **Steps**:
  1. Send a malformed message to the queue.
- **Expected Result**:
  - The message should be rejected by the queue.

#### Negative Case: Empty Message
- **Description**: Verify that the system correctly handles empty messages.
- **Preconditions**:
  - The customer "JaneDoe" is registered in the database.
- **Steps**:
  1. Send an empty message to the queue.
- **Expected Result**:
  - The message should be rejected by the queue.

#### Negative Case: Missing Required Fields
- **Description**: Verify that the system correctly handles messages with missing required fields.
- **Preconditions**:
  - The customer "JaneDoe" is registered in the database.
  - Required fields are defined in the message schema.
- **Steps**:
  1. Send a message with missing required fields to the queue.
- **Expected Result**:
  - The message should be rejected by the queue.

---

## Additional Considerations

1. **Data Cleanup**:
   - Before each test, the database should be cleaned to avoid conflicts between scenarios.

2. **Test Database**:
   - Use an in-memory database (such as H2) for testing, configured in the `test` profile.

3. **Reports**:
   - Generate test reports in HTML and JSON format to facilitate result review.

4. **Test Independence**:
   - Each scenario should be independent and not depend on the state of other scenarios.

---

## Test Structure

### Required Classes and Methods

1. **Class `CustomerStepDefinitions`**:
   - Contains the methods that implement the test steps.
   - Responsible for interacting with the service and the database.

2. **Class `CucumberTestRunner`**:
   - Entry point for running tests with Cucumber.

3. **Class `CustomerService`**:
   - Contains the business logic to verify customer availability.

4. **Class `CustomerRepository`**:
   - Provides methods to interact with the database.

---

## Test Execution

1. **Command to Run Tests**:
   - Use `mvn test` to execute all tests.

2. **Generated Reports**:
   - Reports are generated in the `target/cucumber-reports` folder.

---

## Final Notes

This test plan is designed to be clear and detailed, allowing the development team to implement the required tests efficiently. Any questions or necessary adjustments can be discussed and refined in collaboration with the QA team.