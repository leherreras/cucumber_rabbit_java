Feature: Customer Existence in the System

  Scenario: Customer exists in the system
    Given a customer with name "JohnDoe" exists in the system
    And the customer "JohnDoe" is active
    And the customer "JohnDoe" has the phishing detection service active
    When a message is sent to the input queue with customer name "JohnDoe"
    Then the response in the output queue should be "available"

  Scenario: Customer does not exist in the system
    Given a customer with name "UnknownCustomer" does not exist in the system
    When a message is sent to the input queue with customer name "UnknownCustomer"
    Then the response in the output queue should be "not available"