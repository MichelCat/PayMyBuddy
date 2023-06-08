package com.paymybuddy.paymybuddy.dao.user.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "app_user")
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "username", "password", "appUserRole", "expired", "locked", "credentialsExpired", "enabled"})
@ToString
public class AppUserEntity implements UserDetails {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * User ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_user")
  Long id;
  /**
   * Email used to authenticate the user
   */
  @NotBlank(message = "Email used to authenticate the user is required")
  @Email(message = "Email should be valid")
  @Column(name = "user_email", unique=true)
  @Size(max = 254)
  String username;
  /**
   * Password used to authenticate the user
   */
  @ToString.Exclude
  @NotBlank(message = "Customer password is required")
  @Column(name = "user_password")
  @Size(max = 100)
  String password;
  /**
   * User role
   */
  @NotBlank(message = "User role is required")
  @Column(name = "user_role")
  @Size(max = 20)
  String appUserRole;
  /**
   * User account expired
   */
  @NotNull(message = "User account expired cannot be null")
  @Column(name = "user_expired")
  Boolean expired;
  /**
   * User locked
   */
  @NotNull(message = "User locked cannot be null")
  @Column(name = "user_locked")
  Boolean locked;
  /**
   * User credentials (password) expired
   */
  @NotNull(message = "User credentials expired cannot be null")
  @Column(name = "user_credentials_expired")
  Boolean credentialsExpired;
  /**
   * Activated user
   */
  @NotNull(message = "Activated user cannot be null")
  @Column(name = "user_enabled")
  Boolean enabled;

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
  public boolean isCredentialsNonExpired() { return !credentialsExpired; }

  @Override
  public boolean isEnabled() { return enabled; }
  
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
    id = 0L;
    username = "";
    password = "";
    appUserRole = "";
    expired = false;
    locked = false;
    credentialsExpired = false;
    enabled = false;
  }
  
  /**
   * Test Contains a role
   * 
   * @param role Role to search
   * @return Boolean Role belongs to user
   */
  public boolean isContainsRole(String role) {
    return appUserRole.equals(role);
  }
}
