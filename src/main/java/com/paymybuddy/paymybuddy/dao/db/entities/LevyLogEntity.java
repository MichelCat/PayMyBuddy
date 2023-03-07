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
@EqualsAndHashCode(of= {"levyIdPerson", "levyDate", "idLevy", "levyDescription", "levyAmount"})
@ToString(callSuper = true)
public class LevyLogEntity {
  /**
   * Levy log ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer idLevy;
  /**
   * Levy user ID
   */
  Integer levyIdPerson;
  /**
   * Levy date
   */
  Timestamp levyDate;
  /**
   * Levy description
   */
  String levyDescription;
  /**
   * Levy amount
   */
  Float levyAmount;
  
  @ManyToOne
  @JoinColumn(name="levyIdPerson", nullable=false)
  private PersonEntity personEntity;
}
