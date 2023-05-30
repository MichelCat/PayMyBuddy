package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * CustomerBuddyEntity is Entity model. Link the BuddyEntity model with CustomerEntity
 * 
 * @author MC
 * @version 1.0
 */
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"idUser", "idBuddy", "connection", "email"})
@ToString
public class CustomerBuddyEntity {
  /**
   * User ID
   */
  @NotNull(message = "User ID cannot be null")
  Integer idUser;
  /**
   * Friend ID
   */
  @NotNull(message = "Friend ID cannot be null")
  Integer idBuddy;
  /**
   * User connection
   */
  @NotBlank(message = "User connection is required")
  @Size(max = 50)
  String connection;
  /**
   * User email
   */
  @NotBlank(message = "User email is required")
  @Size(max = 254)
  String email;
}
