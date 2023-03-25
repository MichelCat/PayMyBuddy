package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@EqualsAndHashCode(of = {"bankName", "bankCode", "bankBranchCode", "bankAccountNumber", "bankRibKey", "bankIban", "bankBic"})
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
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="bankIdPerson", nullable=false)
  private PersonEntity personEntity;
  /**
   * Name of the bank
   */
  @NotBlank(message = "Name of the bank is required")
  String bankName;
  /**
   * Bank code
   */
  @NotBlank(message = "Bank code is required")
  String bankCode;
  /**
   * Branch code
   */
  @NotBlank(message = "Branch code is required")
  String bankBranchCode;
  /**
   * Account number
   */
  @NotBlank(message = "Account number is required")
  String bankAccountNumber;
  /**
   * Bank key
   */
  @NotBlank(message = "Bank key is required")
  String bankRibKey;
  /**
   * IBAN
   */
  @NotBlank(message = "IBAN is required")
  String bankIban;
  /**
   * BIC code
   */
  @NotBlank(message = "BIC code is required")
  String bankBic;
}
