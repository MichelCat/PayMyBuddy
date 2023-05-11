package com.paymybuddy.paymybuddy.controller.model;

import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
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
public class CustomerAccount {

  /**
   * Customer account ID
   */
  Integer idCustomer;
  
  /**
   * Account balance
   */
  Float balance;
  
  
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
    var customerAccount = (CustomerAccount) o;
    return
    Objects.equals(this.idCustomer, customerAccount.idCustomer)
      && Objects.equals(this.balance, customerAccount.balance);
  }

  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(idCustomer, balance);
  }
}
