package com.appgate.customer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerRequest {

  private String customer;

  @JsonCreator
  public CustomerRequest(@JsonProperty("customer") String customer)  {
    this.customer = customer;
  }
}
