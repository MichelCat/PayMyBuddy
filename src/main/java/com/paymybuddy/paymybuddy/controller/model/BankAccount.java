package com.paymybuddy.paymybuddy.controller.model;

import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * BankAccount is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "idCustomer", "bankName", "bankCode", "branchCode", "accountNumber", "rib", "iban", "bic"})
@ToString
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
  
  public BankAccount() {
    id = 0;
    idCustomer = 0;
    bankName = "";
    bankCode = "";
    branchCode = "";
    accountNumber = "";
    rib= "";
    iban = "";
    bic = "";
  }
}
