Feature: Send bad format to queue

  Scenario: Create the customer but is sent bad format in message to queue
    Given a customer with name "JohnDoe" exists in the system
    When I send a malformed message to the queue
    Then the message should be rejected by the queue

  Scenario: Send empty message to queue
    Given a customer with name "JaneDoe" exists in the system  
    When I send an empty message to the queue
    Then the message should be rejected by the queue

  Scenario: Send message with missing required fields
    Given a customer with name "BobSmith" exists in the system
    When I send a message missing required fields to the queue
    Then the message should be rejected by the queue
