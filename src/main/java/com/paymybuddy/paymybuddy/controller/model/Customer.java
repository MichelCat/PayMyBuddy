package com.paymybuddy.paymybuddy.controller.model;

import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * User is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "connection", "firstName", "lastName", "address1", "address2", "zipCode", "city"})
@ToString
public class Customer {
  /**
   * Customer ID
   */
  Integer id;

  /**
   * User connection
   */
  String connection;
  /**
  * Customer first name
  */
  String firstName;
  
  /**
  * Customer last name
  */
  String lastName;
  
  /**
  * Address 1 customer
  */
  String address1;
  
  /**
  * Address 2 customer
  */
  String address2;
  
  /**
  * Customer zip code
  */
  String zipCode;
  
  /**
  * Customer city
  */
  String city;
  
  public Customer() {
    id = 0;
    connection = "";
    firstName = "";
    lastName = "";
    address1 = "";
    address2 = "";
    zipCode = "";
    city = "";
  }
}
