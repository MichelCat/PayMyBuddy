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
 * LevyLogEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "levy_log")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"idLevy"})
@ToString(callSuper = true)
public class LevyLogEntity {
  /**
   * Levy log ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer idLevy;
  /**
   * Levy debit user ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="levyIdDebit", nullable=false)
  private PersonEntity personEntityDebit;
  /**
   * Levy credit user ID
   */
  @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
  @JoinColumn(name="levyIdCredit", nullable=false)
  private PersonEntity personEntityCredit;
  /**
   * Levy date
   */
  @NotNull(message = "Levy date cannot be null")
  @PastOrPresent
  Timestamp levyDate;
  /**
   * Levy description
   */
  @NotBlank(message = "Levy description is required")
  String levyDescription;
  /**
   * Levy amount
   */
  @Positive(message = "Levy amount should be a positive number")
  @Digits(integer=9, fraction=2)
  Float levyAmount;
}
