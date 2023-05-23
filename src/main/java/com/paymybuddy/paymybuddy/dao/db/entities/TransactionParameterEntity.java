package com.paymybuddy.paymybuddy.dao.db.entities;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * TransactionParameterEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "transaction_parameter")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "levyRate", "effectiveDate"})
@ToString
public class TransactionParameterEntity {
  /**
   * Transaction parameter ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_parameter")
  Integer id;
  
  /**
   * Levy rate
   */
  @Column(name = "levy_rate")
  Float levyRate;
  /**
   * Effective date
   */
  @NotNull(message = "Effective date cannot be null")
  @Column(name = "effective_date", unique=true)
  Date effectiveDate;
  /**
   * Contact email
   */
  @NotBlank(message = "Contact email is required")
  @Column(name = "contact_email")
  @Size(max = 254)
  String contactEmail;
}
