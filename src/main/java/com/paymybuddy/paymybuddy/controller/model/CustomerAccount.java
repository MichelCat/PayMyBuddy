package com.paymybuddy.paymybuddy.controller.model;

import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * CustomerAccount is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"idCustomer", "balance"})
@ToString
public class CustomerAccount {

  /**
   * Customer account ID
   */
  Integer idCustomer;
  
  /**
   * Account balance
   */
  Float balance;
}
