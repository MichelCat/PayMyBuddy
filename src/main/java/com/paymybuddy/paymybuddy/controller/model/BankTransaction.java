package com.paymybuddy.paymybuddy.controller.model;

import java.sql.Timestamp;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * BankTransaction is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "idDebit", "idCredit", "connection", "transactionDate", "description", "amount", "levy"})
@ToString
public class BankTransaction {
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
}
