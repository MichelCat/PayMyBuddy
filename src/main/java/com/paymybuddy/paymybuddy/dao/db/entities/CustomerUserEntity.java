package com.paymybuddy.paymybuddy.dao.db.entities;

import java.util.Date;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * CustomerUserEntity is Entity model. Link the AppUserEntity model with CustomerEntity
 * 
 * @author MC
 * @version 1.0
 */
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"idUser", "username", "password", "firstName", "lastName", "appUserRole", "expired", "locked", "credentialsExpired", "enabled", "emailValidationKey", "validEmailEndDate"})
@ToString
public class CustomerUserEntity {
  
  /**
   * User ID
   */
  @NotNull(message = "User ID cannot be null")
  Integer idUser;
  /**
   * Email used to authenticate the user
   */
  @NotBlank(message = "Email used to authenticate the user is required")
  @Email(message = "Email should be valid")
  @Size(max = 254)
  String username;
  /**
   * Password used to authenticate the user
   */
  @ToString.Exclude
  @NotBlank(message = "Customer password is required")
  @Size(max = 100)
  String password;
  /**
   * User role
   */
  @NotBlank(message = "User role is required")
  @Size(max = 20)
  String appUserRole;
  /**
   * User account expired
   */
  @NotNull(message = "User account expired cannot be null")
  Boolean expired;
  /**
   * User locked
   */
  @NotNull(message = "User locked cannot be null")
  Boolean locked;
  /**
   * User credentials (password) expired
   */
  @NotNull(message = "User credentials expired cannot be null")
  Boolean credentialsExpired;
  /**
   * Activated user
   */
  @NotNull(message = "Activated user cannot be null")
  Boolean enabled;
  /**
   * Email validation key for customers
   */
  @NotBlank(message = "Email validation key is required")
  @Size(max = 36)
  String emailValidationKey;
  /**
   * Valid email end date for customers
   */
  @NotNull(message = "Valid email end date cannot be null")
  Date validEmailEndDate;
  
  /**
  * Customer first name
  */
  @NotBlank(message = "Customer first name is required")
  @Size(max = 50)
  String firstName;
  /**
  * Customer last name
  */
  @NotBlank(message = "Customer last name is required")
  @Size(max = 50)
  String lastName;
}
