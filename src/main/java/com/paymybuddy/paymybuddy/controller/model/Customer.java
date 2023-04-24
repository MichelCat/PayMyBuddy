package com.paymybuddy.paymybuddy.controller.model;

import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Customer is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Getter
@Setter
public class Customer {
  /**
   * Customer ID
   */
  Integer id;

  /**
   * Customer connection
   */
  String connection;
  
  /**
   * Customer email
   */
  String email;
  
  /**
   * Customer password
   */
  @ToString.Exclude
  String password;
  
  /**
   * Compare two objects
   * 
   * @param o Object to compare
   * @return True if the objects are equal, and false if not.
   */
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var customer = (Customer) o;
    return
    // Objects.equals(this.id, customer.id) &&
    Objects.equals(this.connection, customer.connection)
      && Objects.equals(this.email, customer.email)
      && Objects.equals(this.password, customer.password);
  }

  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, connection, email, password);
  }
}
