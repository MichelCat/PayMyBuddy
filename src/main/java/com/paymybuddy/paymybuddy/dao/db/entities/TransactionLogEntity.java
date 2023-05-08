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
 * TransactionLogEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "transaction_log")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class TransactionLogEntity {
  /**
   * Log ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_log")
  Integer id;
  /**
   * Log debit customer ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="logIdDebit", nullable=false)
  private CustomerEntity customerDebit;
  /**
   * Log credit customer ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="logIdCredit", nullable=false)
  private CustomerEntity customerCredit;
  /**
   * Log date
   */
  @NotNull(message = "Log date cannot be null")
  @PastOrPresent
  @Column(name = "log_date")
  Timestamp logDate;
  /**
   * Log description
   */
  @NotBlank(message = "Log description is required")
  @Column(name = "log_description")
  @Size(max = 50)
  String description;
  /**
   * Log amount
   */
  @Column(name = "log_amount")
  Float amount;
  /**
   * Log levy
   */
  @Column(name = "log_levy")
  Float levy;
}
