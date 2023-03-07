package com.paymybuddy.paymybuddy.dao.db.entities;

import java.sql.Timestamp;
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
@EqualsAndHashCode(of= {"transactionIdDebtor", "transactionIdCredit", "transactionDate", "idBankTransaction", "transactionDescription", "transactionAmount"})
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
  Integer transactionIdDebtor;
  /**
   * Credit user ID
   */
  Integer transactionIdCredit;
  /**
   * Transaction date
   */
  Timestamp transactionDate;
  /**
   * Transaction description
   */
  String transactionDescription;
  /**
   * Transaction amount
   */
  Float transactionAmount;
  
  @ManyToOne
  @JoinColumn(name="transactionIdDebtor", nullable=false)
  private PersonEntity personEntityDebtor;
  
  
  @ManyToOne
  @JoinColumn(name="transactionIdCredit", nullable=false)
  private PersonEntity personEntityCredit;
}
