package com.paymybuddy.paymybuddy.dao.db.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@EqualsAndHashCode(of= {"personConnection", "idPerson", "personEmail", "personPassword"})
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
  String personConnection;
  /**
   * User email
   */
  String personEmail;
  /**
   * User password
   */
  @ToString.Exclude
  String personPassword;
  
  @OneToMany( targetEntity=LevyLogEntity.class, mappedBy="personEntity"
            , cascade = CascadeType.ALL )
  private List<LevyLogEntity> levyLogEntities = new ArrayList<>();
  
  
  @OneToMany( targetEntity=BankAccountEntity.class, mappedBy="personEntity"
            , cascade = CascadeType.ALL )
  private List<BankAccountEntity> bankAccountEntities = new ArrayList<>();

  
  @OneToMany( targetEntity=BankTransactionEntity.class, mappedBy="personEntityDebtor"
      , cascade = CascadeType.ALL )
  private List<BankTransactionEntity> bankTransactionEntityDebtors = new ArrayList<>();

  @OneToMany( targetEntity=BankTransactionEntity.class, mappedBy="personEntityCredit"
      , cascade = CascadeType.ALL )
  private List<BankTransactionEntity> bankTransactionEntityCredits = new ArrayList<>();
  
  
  @OneToOne( cascade = CascadeType.ALL ) 
  @JoinColumn( name="accountIdPerson" )
  private UserAccountEntity userAccountEntity;
  
  
  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="personEntityUser"
      , cascade = CascadeType.ALL )
  private List<BuddyEntity> BuddyEntitydUsers = new ArrayList<>();

  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="personEntityBuddy"
      , cascade = CascadeType.ALL )
  private List<BuddyEntity> BuddyEntityBuddy = new ArrayList<>();
  
}
