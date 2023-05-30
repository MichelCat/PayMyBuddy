package com.paymybuddy.paymybuddy.controller.model;

import java.sql.Date;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * TransactionParameter is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "levyRate", "effectiveDate", "contactEmail"})
@ToString
public class TransactionParameter {
  /**
   * Transaction parameter ID
   */
  Integer id;
  
  /**
   * Levy rate
   */
  Float levyRate;
  /**
   * Effective date
   */
  Date effectiveDate;
  /**
   * Contact email
   */
  String contactEmail;
}
