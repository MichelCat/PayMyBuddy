package com.paymybuddy.paymybuddy.controller.model;

import java.sql.Timestamp;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.Getter;
import lombok.Setter;

/**
 * Transaction is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Getter
@Setter
public class Transaction {
  /**
   * Bank transaction ID
   */
  Integer id;
  /**
   * Debit customer ID
   */
  Integer idDebit;
  /**
   * Credit customer ID
   */
  Integer idCredit;
  /**
   * Connection name
   */
  String connection;
  /**
   * Transaction date
   */
  Timestamp transactionDate;
  /**
   * Transaction description
   */
  String description;
  /**
   * Transaction amount
   */
  Float amount;
  /**
   * Transaction levy
   */
  Float levy;
  
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
    var transaction = (Transaction) o;
    return
    Objects.equals(this.id, transaction.id)
      && Objects.equals(this.idDebit, transaction.idDebit)
      && Objects.equals(this.idCredit, transaction.idCredit)
      && Objects.equals(this.transactionDate, transaction.transactionDate)
      && Objects.equals(this.description, transaction.description)
      && Objects.equals(this.amount, transaction.amount)
      && Objects.equals(this.levy, transaction.levy);
  }

  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, idDebit, idCredit, transactionDate, description, amount, levy);
  }

}
