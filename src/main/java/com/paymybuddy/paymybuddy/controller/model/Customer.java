package com.paymybuddy.paymybuddy.controller.model;

import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
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
    var user = (Customer) o;
    return
    Objects.equals(this.id, user.id)
      && Objects.equals(this.connection, user.connection)
      && Objects.equals(this.firstName, user.firstName)
      && Objects.equals(this.lastName, user.lastName)
      && Objects.equals(this.address1, user.address1)
      && Objects.equals(this.address2, user.address2)
      && Objects.equals(this.zipCode, user.zipCode)
      && Objects.equals(this.city, user.city);
  }
 
  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, connection, firstName, lastName, address1, address2, zipCode, city);
  }
  
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
