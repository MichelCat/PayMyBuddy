package com.paymybuddy.paymybuddy.controller.model;

import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
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
  
  /**
   * Compare two objects
   * 
   * @param o Object to compare
   * @return True if the objects are equal, and false if not.
   */
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var buddy = (Buddy) o;
    return
    // Objects.equals(this.id, buddy.id) &&
    Objects.equals(this.idUser, buddy.idUser)
      && Objects.equals(this.idBuddy, buddy.idBuddy)
      && Objects.equals(this.email, buddy.email)
      && Objects.equals(this.connection, buddy.connection);
  }

  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, idUser, idBuddy, email, connection);
  }
}
