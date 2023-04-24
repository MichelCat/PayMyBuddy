package com.paymybuddy.paymybuddy.dao.db.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * CustomerEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "customer")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"email"})
@ToString
public class CustomerEntity {
  /**
   * Customer ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_customer")
  Integer id;
  /**
   * Customer email
   */
  @Email(message = "Email should be valid")
  @Column(name = "customer_email", unique=true)
  String email;
  /**
   * Customer password
   */
  @ToString.Exclude
  @NotBlank(message = "Customer password is required")
  @Column(name = "customer_password")
  String password;
  
  /**
   * CustomerEntity [1..1] to TransactionLogEntity [0..n], debit relationship
   */
  @OneToMany( targetEntity=TransactionLogEntity.class, mappedBy="customerDebit"
            , cascade = CascadeType.ALL )
  private List<TransactionLogEntity> transactionLogEntityDebits = new ArrayList<>();
  /**
   * CustomerEntity [1..1] to TransactionLogEntity [0..n], credit relationship
   */
  @OneToMany( targetEntity=TransactionLogEntity.class, mappedBy="customerCredit"
            , cascade = CascadeType.ALL )
  private List<TransactionLogEntity> transactionLogEntityCredits = new ArrayList<>();
  
  /**
   * CustomerEntity [1..1] to BankAccountEntity [1..n]
   */
  @OneToMany( targetEntity=BankAccountEntity.class, mappedBy="customer"
            , cascade = CascadeType.ALL )
  private List<BankAccountEntity> bankAccountEntities = new ArrayList<>();
  
  /**
   * CustomerEntity [1..1] to BankTransactionEntity [0..n], debit relationship
   */
  @OneToMany( targetEntity=BankTransactionEntity.class, mappedBy="customerDebit"
          , cascade = CascadeType.ALL )
  private List<BankTransactionEntity> bankTransactionEntityDebits = new ArrayList<>();
  /**
   * CustomerEntity [1..1] to BankTransactionEntity [0..n], credit relationship
   */
  @OneToMany( targetEntity=BankTransactionEntity.class, mappedBy="customerCredit"
          , cascade = CascadeType.ALL )
  private List<BankTransactionEntity> bankTransactionEntityCredits = new ArrayList<>();
  
  /**
   * CustomerEntity [1..1] to CustomerAccountEntity [1..1]
   */
  @OneToOne( cascade = CascadeType.ALL ) 
  @JoinColumn( name="idCustomer" )
  private CustomerAccountEntity customerAccountEntity;
  
  /**
   * CustomerEntity [1..1] to BuddyEntity [0..n], customer relationship
   */
  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="customerUser"
          , cascade = CascadeType.ALL )
  private List<BuddyEntity> BuddyEntityUsers = new ArrayList<>();
  /**
   * CustomerEntity [1..1] to BuddyEntity [0..n], buddy relationship
   */
  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="customerBuddy"
          , cascade = CascadeType.ALL )
  private List<BuddyEntity> BuddyEntityBuddy = new ArrayList<>();
}