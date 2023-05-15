package com.paymybuddy.paymybuddy.controller.model;

import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Register is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Register {

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
    var register = (Register) o;
    return
    Objects.equals(this.email, register.email)
      && Objects.equals(this.password, register.password)
      && Objects.equals(this.firstName, register.firstName)
      && Objects.equals(this.lastName, register.lastName);
  }
 
  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(email, password, firstName, lastName);
  }
  
  public Register() {
    email = "";
    password = "";
    firstName = "";
    lastName = "";
  }
}
