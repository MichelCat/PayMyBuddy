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
 * PersonEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "person")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of = {"personEmail"})
@ToString(callSuper = true)
public class PersonEntity {
  /**
   * User ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer idPerson;
  /**
   * User connection
   */
  @NotBlank(message = "User connection is required")
  String personConnection;
  /**
   * User email
   */
  @Column(unique=true)
  @Email(message = "Email should be valid")
  String personEmail;
  /**
   * User password
   */
  @ToString.Exclude
  @NotBlank(message = "User password is required")
  String personPassword;
  
  /**
   * PersonEntity [1..1] to LevyLogEntity [0..n], debit relationship
   */
  @OneToMany( targetEntity=LevyLogEntity.class, mappedBy="personEntityDebit"
            , cascade = CascadeType.ALL )
  private List<LevyLogEntity> levyLogEntityDebits = new ArrayList<>();
  /**
   * PersonEntity [1..1] to LevyLogEntity [0..n], credit relationship
   */
  @OneToMany( targetEntity=LevyLogEntity.class, mappedBy="personEntityCredit"
            , cascade = CascadeType.ALL )
  private List<LevyLogEntity> levyLogEntityCredits = new ArrayList<>();
  
  /**
   * PersonEntity [1..1] to BankAccountEntity [1..n]
   */
  @OneToMany( targetEntity=BankAccountEntity.class, mappedBy="personEntity"
            , cascade = CascadeType.ALL )
  private List<BankAccountEntity> bankAccountEntities = new ArrayList<>();
  
  /**
   * PersonEntity [1..1] to BankTransactionEntity [0..n], debit relationship
   */
  @OneToMany( targetEntity=BankTransactionEntity.class, mappedBy="personEntityDebit"
          , cascade = CascadeType.ALL )
  private List<BankTransactionEntity> bankTransactionEntityDebits = new ArrayList<>();
  /**
   * PersonEntity [1..1] to BankTransactionEntity [0..n], credit relationship
   */
  @OneToMany( targetEntity=BankTransactionEntity.class, mappedBy="personEntityCredit"
          , cascade = CascadeType.ALL )
  private List<BankTransactionEntity> bankTransactionEntityCredits = new ArrayList<>();
  
  /**
   * PersonEntity [1..1] to UserAccountEntity [1..1]
   */
  @OneToOne( cascade = CascadeType.ALL ) 
  @JoinColumn( name="accountIdPerson" )
  private UserAccountEntity userAccountEntity;
  
  /**
   * PersonEntity [1..1] to BuddyEntity [0..n], user relationship
   */
  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="personEntityUser"
          , cascade = CascadeType.ALL )
  private List<BuddyEntity> BuddyEntityUsers = new ArrayList<>();
  /**
   * PersonEntity [1..1] to BuddyEntity [0..n], buddy relationship
   */
  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="personEntityBuddy"
          , cascade = CascadeType.ALL )
  private List<BuddyEntity> BuddyEntityBuddy = new ArrayList<>();
}
