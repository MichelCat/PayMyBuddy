package com.paymybuddy.paymybuddy.controller.model;

import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Buddy is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id", "idUser", "idBuddy", "email", "connection"})
@ToString
public class Buddy {
  /**
   * Buddy ID
   */
  Integer id;
  
  /**
   * User ID
   */
  Integer idUser;
  
  /**
   * Friend ID
   */
  Integer idBuddy;
  
  /**
   * Customer email
   */
  String email;
  
  /**
   * Connection name
   */
  String connection;
}
