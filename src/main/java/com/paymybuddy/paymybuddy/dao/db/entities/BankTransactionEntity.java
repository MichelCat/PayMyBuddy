package com.paymybuddy.paymybuddy.dao.db.entities;

import java.sql.Timestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
@EqualsAndHashCode(of = {"id"})
@ToString
public class BankTransactionEntity {
  /**
   * Bank transaction ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_bank_transaction")
  Integer id;
  /**
   * Debit customer ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="transactionIdDebit", nullable=false)
  private CustomerEntity customerDebit;
  /**
   * Credit customer ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="transactionIdCredit", nullable=false)
  private CustomerEntity customerCredit;
  /**
   * Transaction date
   */
  @NotNull(message = "Transaction date cannot be null")
  @PastOrPresent
  @Column(name = "transaction_date")
  Timestamp transactionDate;
  /**
   * Transaction description
   */
  @NotBlank(message = "Transaction description is required")
  @Column(name = "transaction_description")
  @Size(max = 50)
  String description;
  /**
   * Transaction amount
   */
  @Column(name = "transaction_amount")
  Float amount;
  /**
   * Transaction levy
   */
  @Column(name = "transaction_levy")
  Float levy;
}
