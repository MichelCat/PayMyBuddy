package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * CustomerAccountEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "customer_account")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of= {"idCustomer"})
@ToString
public class CustomerAccountEntity {
  /**
   * Customer account ID
   * CustomerEntity [1..1] to CustomerAccountEntity [1..1]
   */
  @Id
  @Column(name = "account_id_customer")
  Integer idCustomer;
  
  /**
   * Account balance
   */
  @PositiveOrZero(message = "Account balance must be a positive number or zero")
  @Column(name = "account_balance")
  Float balance;
}
