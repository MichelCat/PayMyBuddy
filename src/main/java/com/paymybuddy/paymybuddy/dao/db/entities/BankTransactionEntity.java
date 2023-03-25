package com.paymybuddy.paymybuddy.dao.db.entities;

import java.sql.Timestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * BankTransactionEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "bank_transaction")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"idBankTransaction"})
@ToString(callSuper = true)
public class BankTransactionEntity {
  /**
   * Bank transaction ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer idBankTransaction;
  /**
   * Debit User ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="transactionIdDebit", nullable=false)
  private PersonEntity personEntityDebit;
  /**
   * Credit user ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="transactionIdCredit", nullable=false)
  private PersonEntity personEntityCredit;
  /**
   * Transaction date
   */
  @NotNull(message = "Transaction date cannot be null")
  @PastOrPresent
  Timestamp transactionDate;
  /**
   * Transaction description
   */
  @NotBlank(message = "Transaction description is required")
  String transactionDescription;
  /**
   * Transaction amount
   */
  @Positive(message = "Transaction amount should be a positive number")
  @Digits(integer=9, fraction=2)
  Float transactionAmount;
}
