package com.paymybuddy.paymybuddy.dao.user.entities;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "user")
public class PaymybuddyAppUser implements UserDetails {
  
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
  private PaymybuddyAppUserRole paymybuddyAppUserRole;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(paymybuddyAppUserRole.name());
    return Collections.singleton(authority);
  }

  @Override
  public boolean isAccountNonExpired() { return !expired; }

  @Override
  public boolean isAccountNonLocked() { return !locked; }

  @Override
  public boolean isCredentialsNonExpired() { return !credentiaExpired; }

  @Override
  public boolean isEnabled() { return !enabled; }
  
//  /**
//   * PaymybuddyAppUser [1..1] to CustomerEntity [0..1], PaymybuddyAppUser relationship
//   */
//  @OneToOne( mappedBy = "paymybuddyAppUser" )
//  private CustomerEntity customerEntity;
  
  public PaymybuddyAppUser() {
    id = 0;
    username = "";
    password = "";
    paymybuddyAppUserRole = PaymybuddyAppUserRole.USER_ROLE;
    expired = false;
    locked = false;
    credentiaExpired = false;
    enabled = false;
  }
}
