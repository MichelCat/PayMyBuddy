package com.paymybuddy.paymybuddy.dao.user.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerMessageEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "app_user")
public class AppUserEntity implements UserDetails {
  
  /**
   * User ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_user")
  private long id;
  /**
   * Email used to authenticate the user
   */
  @Email(message = "Email should be valid")
  @Column(name = "user_email", unique=true)
  @Size(max = 254)
  private String username;
  /**
   * Password used to authenticate the user
   */
  @ToString.Exclude
  @NotBlank(message = "Customer password is required")
  @Column(name = "user_password")
  @Size(max = 100)
  private String password;
  /**
   * User role
   */
  @Column(name = "user_role")
  private String appUserRole;
  /**
   * User account expired
   */
  @Column(name = "user_expired")
  private Boolean expired;
  /**
   * User locked
   */
  @Column(name = "user_locked")
  private Boolean locked;
  /**
   * User credentials (password) expired
   */
  @Column(name = "user_credentia_expired")
  private Boolean credentiaExpired;
  /**
   * Activated user
   */
  @Column(name = "user_enabled")
  private Boolean enabled;
  /**
   * Email validation key
   */
  @Column(name = "user_email_validation_key", unique=true)
  @Size(max = 36)
  private String emailValidationKey;
  /**
   * Valid email end date
   */
  @Column(name = "user_valid_email_end_date")
  private Date validEmailEndDate;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole);
    return Collections.singleton(authority);
  }

  @Override
  public boolean isAccountNonExpired() { return !expired; }

  @Override
  public boolean isAccountNonLocked() { return !locked; }

  @Override
  public boolean isCredentialsNonExpired() { return !credentiaExpired; }

  @Override
  public boolean isEnabled() { return enabled; }
  
//  /**
//   * AppUserEntity [1..1] to CustomerEntity [0..1], appUserEntity relationship
//   */
//  @OneToOne( mappedBy = "appUserEntity" )
//  private CustomerEntity customerEntity;
  
  /**
   * AppUserEntity [1..1] to CustomerMessageEntity [0..n], sender relationship
   */
  @OneToMany( targetEntity=CustomerMessageEntity.class, mappedBy="appUserEntitySender"
            , cascade = CascadeType.ALL )
  private List<CustomerMessageEntity> customerMessageEntitySenders = new ArrayList<>();
  /**
   * AppUserEntity [1..1] to CustomerMessageEntity [0..n], recipient relationship
   */
  @OneToMany( targetEntity=CustomerMessageEntity.class, mappedBy="appUserEntityRecipient"
            , cascade = CascadeType.ALL )
  private List<CustomerMessageEntity> customerMessageEntityRecipients = new ArrayList<>();
  
  
  public AppUserEntity() {
    id = 0;
    username = "";
    password = "";
    appUserRole = "";
    expired = false;
    locked = false;
    credentiaExpired = false;
    enabled = false;
    emailValidationKey = null;
    validEmailEndDate = null;
  }
  
  /**
   * Test valid email key
   * 
   * @param testKey Key to validate
   * @return Boolean Validated key
   */
  public boolean isValidEmailKey(String testKey) {
    return this.emailValidationKey.equals(testKey);
  }
  
  /**
   * Test valid email date
   * 
   * @return Boolean Validated key
   */
  public boolean isValidEmailEndDate() {
    Date currentDate = new Date();
    return currentDate.before(validEmailEndDate);    
  }
  
  /**
   * Create valid email key
   */
  public void createValidEmailKey() {
    this.emailValidationKey = UUID.randomUUID().toString();
  }
  
  /**
   * Create valid email end date
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
