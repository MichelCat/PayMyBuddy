package com.paymybuddy.paymybuddy.controller.model;

import java.sql.Date;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * TransactionParameter is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class TransactionParameter {
  /**
   * Transaction parameter ID
   */
  Integer id;
  
  /**
   * Levy rate
   */
  Float levyRate;
  /**
   * Effective date
   */
  Date effectiveDate;
  /**
   * Contact email
   */
  String contactEmail;
  
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
    var transactionParameter = (TransactionParameter) o;
    return
    // Objects.equals(this.id, transactionParameter.id) &&
    Objects.equals(this.levyRate, transactionParameter.levyRate)
      && Objects.equals(this.effectiveDate, transactionParameter.effectiveDate)
      && Objects.equals(this.contactEmail, transactionParameter.contactEmail);
  }

  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, levyRate, effectiveDate, contactEmail);
  }
}
