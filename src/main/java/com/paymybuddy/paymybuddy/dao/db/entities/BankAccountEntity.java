package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "bank_account"
      , uniqueConstraints = {
        @UniqueConstraint(columnNames = { "bank_code", "bank_branch_code", "bank_account_number", "bank_rib_key" })
        , @UniqueConstraint(columnNames = { "bank_iban", "bank_bic" })
      })
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"bankName", "bankCode", "branchCode", "accountNumber", "rib", "iban", "bic"})
@ToString
public class BankAccountEntity {
  /**
   * Bank account ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_bank_account")
  Integer id;
  /**
   * Account customer ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="bankIdCustomer", nullable=false)
  private CustomerEntity customer;
  /**
   * Name of the bank
   */
  @NotBlank(message = "Name of the bank is required")
  @Column(name = "bank_name")
  @Size(max = 50)
  String bankName;
  /**
   * Bank code
   */
  @NotBlank(message = "Bank code is required")
  @Column(name = "bank_code")
  @Size(max = 5)
  String bankCode;
  /**
   * Branch code
   */
  @NotBlank(message = "Branch code is required")
  @Column(name = "bank_branch_code")
  @Size(max = 5)
  String branchCode;
  /**
   * Account number
   */
  @NotBlank(message = "Account number is required")
  @Column(name = "bank_account_number")
  @Size(max = 11)
  String accountNumber;
  /**
   * Bank key
   */
  @NotBlank(message = "Bank key is required")
  @Column(name = "bank_rib_key")
  @Size(max = 2)
  String rib;
  /**
   * IBAN
   */
  @NotBlank(message = "IBAN is required")
  @Column(name = "bank_iban")
  @Size(max = 34)
  String iban;
  /**
   * BIC code
   */
  @NotBlank(message = "BIC code is required")
  @Column(name = "bank_bic")
  @Size(max = 11)
  String bic;
}
