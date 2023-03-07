package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * BankAccountEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "bank_account")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
//@EqualsAndHashCode(of= {"bankCode", "bankBranchCode", "bankAccountNumber", "bankRibKey", "bankIban", "bankBic", "idBankAccount", "bankIdPerson", "bankName"})
@EqualsAndHashCode(of= {"bankCode", "bankBranchCode", "bankAccountNumber", "bankRibKey", "bankIban", "bankBic", "idBankAccount", "bankName"})
@ToString(callSuper = true)
public class BankAccountEntity {
  /**
   * Bank account ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer idBankAccount;
  /**
   * Account User ID
   */
//  Integer bankIdPerson;
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
  String bankBranchCode;
  /**
   * Account number
   */
  String bankAccountNumber;
  /**
   * Bank key
   */
  String bankRibKey;
  /**
   * IBAN
   */
  String bankIban;
  /**
   * BIC code
   */
  String bankBic;
  
  @ManyToOne
  @JoinColumn(name="bankIdPerson", nullable=false)
  private PersonEntity personEntity;
}
