package com.appgate.customer.cucumber.actions;

import com.appgate.customer.repository.CustomerEntity;
import com.appgate.customer.repository.CustomerRepository;

public class CustomerActions {
    private final CustomerRepository customerRepository;

    public CustomerActions(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(String name, boolean isActive, boolean isPhishingDetection) {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName(name);
        customer.setActive(isActive);
        customer.setPhishingDetection(isPhishingDetection);
        customerRepository.save(customer);
    }

    public void updateCustomer(String name, Boolean isActive, Boolean isPhishingDetection) {
        CustomerEntity customer = customerRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Customer not found with name: " + name));

        if (isActive != null) {
            customer.setActive(isActive);
        }
        if (isPhishingDetection != null) {
            customer.setPhishingDetection(isPhishingDetection);
        }
        customerRepository.save(customer);
    }
}
