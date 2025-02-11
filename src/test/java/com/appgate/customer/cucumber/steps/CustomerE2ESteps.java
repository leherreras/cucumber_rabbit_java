package com.appgate.customer.cucumber.steps;

import com.appgate.customer.Application;
import com.appgate.customer.cucumber.actions.CustomerActions;
import com.appgate.customer.dto.CustomerRequest;
import com.appgate.customer.dto.CustomerResponse;
import com.appgate.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

import org.junit.jupiter.api.Assertions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {Application.class})
@CucumberContextConfiguration
public class CustomerE2ESteps {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.in.customer.query.queue}")
    private String inputQueue;

    @Value("${app.out.customer.exchange}")
    private String exchange;

    @Value("${app.out.customer.response.routing-key}")
    private String responseRoutingKey;

    private CustomerActions customerActions;
    private String customerName;
    private CustomerResponse receivedResponse;

    @Before
    public void setup() {
        // Clean the database
        customerRepository.deleteAll();
        
        // Purge the queues
        rabbitTemplate.execute(channel -> {
            channel.queuePurge(inputQueue);
            channel.queuePurge(exchange + "." + responseRoutingKey);
            return null;
        });

        customerActions = new CustomerActions(customerRepository);
    }

    @Given("a customer with name {string} does not exist in the system")
    public void a_customer_does_not_exist_in_the_system(String name) {
        this.customerName = name;
       System.out.println("Customer " + name + " does not exist in the system (database is empty).");
    }

    @Given("a customer with name {string} exists in the system")
    public void a_customer_with_name_exists_in_the_system(String name) {
        this.customerName = name;
        customerActions.createCustomer(name, false, false);
    }

    @Given("the customer {string} is active")
    public void the_customer_is_active(String name) {
        customerActions.updateCustomer(name, true, null);
    }

    @Given("the customer {string} is not active")
    public void the_customer_is_not_active(String name) {
        customerActions.updateCustomer(name, false, null);
    }

    @Given("the customer {string} has the phishing detection service active")
    public void the_customer_has_the_phishing_detection_service_active(String name) {
        customerActions.updateCustomer(name, null, true);
    }

    @Given("the customer {string} does not have the phishing detection service active")
    public void the_customer_does_not_have_the_phishing_detection_service_active(String name) {
        customerActions.updateCustomer(name, null, false);
    }

    @When("a message is sent to the input queue with customer name {string}")
    public void sendRequestToRabbitMQ(String name) {
        CustomerRequest request = new CustomerRequest(name);
        rabbitTemplate.convertAndSend(inputQueue, request);
    }

    @When("I send a malformed message to the queue")
    public void sendRequestMalformedToRabbitMQ() {
        String request = "malformed";
        rabbitTemplate.convertAndSend(inputQueue, request);
    }

    @When("I send an empty message to the queue")
    public void sendRequestEmptyToRabbitMQ() {
        String request = "";
        rabbitTemplate.convertAndSend(inputQueue, request);
    }

    @When("I send a message missing required fields to the queue")
    public void sendRequestMissingFieldsToRabbitMQ() {
        String request = "{}";
        rabbitTemplate.convertAndSend(inputQueue, request);
    }

    @Then("the message should be rejected by the queue")
    public void verifyMessageRejected() throws Exception {
        // Wait for response message (retry for up to 5 seconds)
        for (int i = 0; i < 5; i++) {
            Object message = rabbitTemplate.receiveAndConvert(exchange + "." + responseRoutingKey);
            if (message != null) {
                receivedResponse = objectMapper.readValue(message.toString(), CustomerResponse.class);
                break;
            }
            TimeUnit.SECONDS.sleep(1);
        }

        // Validate the response
        Assertions.assertNull(receivedResponse, "No response received from RabbitMQ");
    }

    @Then("the response in the output queue should be {string}")
    public void verifyResponseMessage(String expectedResponse) throws Exception {
        // Wait for response message (retry for up to 5 seconds)
        for (int i = 0; i < 5; i++) {
            Object message = rabbitTemplate.receiveAndConvert(exchange + "." + responseRoutingKey);
            if (message != null) {
                receivedResponse = objectMapper.readValue(message.toString(), CustomerResponse.class);
                break;
            }
            TimeUnit.SECONDS.sleep(1);
        }

        // Validate the response
        boolean expectedAvailability = expectedResponse.equals("available");

        Assertions.assertNotNull(receivedResponse, "No response received from RabbitMQ");
        Assertions.assertEquals(expectedAvailability, receivedResponse.isAvailable(), "Customer available mismatch");
        Assertions.assertEquals(customerName, receivedResponse.customer(), "Customer name mismatch");
    }

}
