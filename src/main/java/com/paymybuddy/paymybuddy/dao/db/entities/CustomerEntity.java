package com.paymybuddy.paymybuddy.dao.db.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
//@NoArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName", "address1", "address2", "zipCode", "city", "emailValidationKey", "validEmailEndDate"})
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
   * User ID. AppUserEntity [1..1] to CustomerEntity [0..1]
   */
  @OneToOne 
  @JoinColumn( name="customerIdUser", nullable=false )
  private AppUserEntity appUserEntity;
  
  /**
  * Customer first name
  */
  @NotBlank(message = "Customer first name is required")
  @Column(name = "customer_first_name")
  @Size(max = 50)
  String firstName;
  /**
  * Customer last name
  */
  @NotBlank(message = "Customer last name is required")
  @Column(name = "customer_last_name")
  @Size(max = 50)
  String lastName;
  /**
  * Address 1 customer
  */
  @NotNull(message = "Address 1 customer may not be null")
  @Column(name = "customer_address_1")
  @Size(max = 100)
  String address1;
  /**
  * Address 2 customer
  */
  @NotNull(message = "Address 2 customer may not be null")
  @Column(name = "customer_address_2")
  @Size(max = 100)
  String address2;
  /**
  * Customer zip code
  */
  @NotNull(message = "Customer zip code may not be null")
  @Column(name = "customer_zip_code")
  @Size(max = 50)
  String zipCode;
  /**
  * Customer city
  */
  @NotNull(message = "Customer city may not be null")
  @Column(name = "customer_city")
  @Size(max = 50)
  String city;
  
  /**
   * Email validation key for customers
   */
//  @Column(name = "customer_email_validation_key", unique=true)
  @Column(name = "customer_email_validation_key")
  @Size(max = 36)
  String emailValidationKey;
  /**
   * Valid email end date for customers
   */
  @Column(name = "customer_valid_email_end_date")
  Date validEmailEndDate;
  
  
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
   * CustomerEntity [1..1] to CustomerAccountEntity [0..1]
   */
  @OneToMany( targetEntity=CustomerAccountEntity.class, mappedBy="customer"
      , cascade = CascadeType.ALL )
  private List<CustomerAccountEntity> customerAccountEntities = new ArrayList<>();
  
  /**
   * CustomerEntity [1..1] to BuddyEntity [0..n], customer relationship
   */
  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="customerUser"
          , cascade = CascadeType.ALL )
  private List<BuddyEntity> buddyEntityUsers = new ArrayList<>();
  /**
   * CustomerEntity [1..1] to BuddyEntity [0..n], buddy relationship
   */
  @OneToMany( targetEntity=BuddyEntity.class, mappedBy="customerBuddy"
          , cascade = CascadeType.ALL )
  private List<BuddyEntity> buddyEntityBuddies = new ArrayList<>();
  
  public CustomerEntity() {
    id = 0;
    appUserEntity = null;
    firstName = "";
    lastName = "";
    address1 = "";
    address2 = "";
    zipCode = "";
    city = "";
    emailValidationKey = "";
    validEmailEndDate = null;
  }
  
  /**
   * Test valid email key for customers
   * 
   * @param testKey Key to validate
   * @return Boolean Validated key
   */
  public boolean isValidEmailKey(String testKey) {
    return this.emailValidationKey.equals(testKey);
  }

  /**
   * Test valid email date for customers
   * 
   * @return Boolean Validated key
   */
  public boolean isValidEmailEndDate() {
    Date currentDate = new Date();
    return currentDate.before(validEmailEndDate);    
  }
  
  /**
   * Create valid email key for customers
   */
  public void createValidEmailKey() {
    this.emailValidationKey = UUID.randomUUID().toString();
  }
  
  /**
   * Create valid email end date for customers
   */
  public void createValidEndDate() {
    // Today's date plus 24 hours
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, 24);
    this.validEmailEndDate = calendar.getTime();
  }
}
