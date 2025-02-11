Feature: Combined Customer Scenarios

  Scenario: Customer exists, is active, and has phishing detection active
    Given a customer with name "JohnDoe" exists in the system
    And the customer "JohnDoe" is active
    And the customer "JohnDoe" has the phishing detection service active
    When a message is sent to the input queue with customer name "JohnDoe"
    Then the response in the output queue should be "available"

  Scenario: Customer exists but is not active
    Given a customer with name "JohnDoe" exists in the system
    And the customer "JohnDoe" is not active
    When a message is sent to the input queue with customer name "JohnDoe"
    Then the response in the output queue should be "not available"

  Scenario: Customer exists but does not have phishing detection active
    Given a customer with name "JohnDoe" exists in the system
    And the customer "JohnDoe" does not have the phishing detection service active
    When a message is sent to the input queue with customer name "JohnDoe"
    Then the response in the output queue should be "not available"