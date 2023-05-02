package com.paymybuddy.paymybuddy.controller.model;

import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.Getter;
import lombok.Setter;

/**
 * BankAccount is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Getter
@Setter
public class BankAccount {
  /**
   * Bank account ID
   */
  Integer id;
  /**
   * Account customer ID
   */
  Integer idCustomer;
  /**
   * Name of the bank
   */
  String bankName;
  /**
   * Bank code
   */
  String bankCode;
  /**
   * Branch code
   */
  String branchCode;
  /**
   * Account number
   */
  String accountNumber;
  /**
   * Bank key
   */
  String rib;
  /**
   * IBAN
   */
  String iban;
  /**
   * BIC code
   */
  String bic;
  
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
    var bankAccount = (BankAccount) o;
    return
    // Objects.equals(this.id, bankAccount.id) &&
    Objects.equals(this.idCustomer, bankAccount.idCustomer)
        && Objects.equals(this.bankName, bankAccount.bankName)
        && Objects.equals(this.bankCode, bankAccount.bankCode)
        && Objects.equals(this.branchCode, bankAccount.branchCode)
        && Objects.equals(this.accountNumber, bankAccount.accountNumber)
        && Objects.equals(this.rib, bankAccount.rib)
        && Objects.equals(this.iban, bankAccount.iban)
        && Objects.equals(this.bic, bankAccount.bic);
  }

  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, idCustomer, bankName, bankCode, branchCode, accountNumber, rib, iban, bic);
  }
}
