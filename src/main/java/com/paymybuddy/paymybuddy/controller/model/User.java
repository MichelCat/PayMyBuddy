package com.paymybuddy.paymybuddy.controller.model;

import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Getter
@Setter
public class User {
  /**
   * Customer ID
   */
  Integer id;

  /**
   * User connection
   */
  String connection;
  
  /**
   * User email
   */
  String email;
  
  /**
   * User password
   */
  @ToString.Exclude
  String password;
  
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
    var user = (User) o;
    return
    // Objects.equals(this.id, user.id) &&
    Objects.equals(this.connection, user.connection)
      && Objects.equals(this.email, user.email)
      && Objects.equals(this.password, user.password)
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
    return Objects.hash(id, connection, email, password, firstName, lastName, address1, address2, zipCode, city);
  }
}
