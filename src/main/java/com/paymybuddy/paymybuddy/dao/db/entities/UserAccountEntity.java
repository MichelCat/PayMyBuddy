package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * UserAccountEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "user_account")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of= {"accountIdPerson"})
@ToString(callSuper = true)
public class UserAccountEntity {
  /**
   * User account ID
   */
  @Id
  Integer accountIdPerson;
  /**
   * Account balance
   */
  @PositiveOrZero(message = "Account balance must be a positive number or zero")
  @Digits(integer=9, fraction=2)
  Float accountBalance;
}
