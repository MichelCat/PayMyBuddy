package com.paymybuddy.paymybuddy.dao.db.entities;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * CustomerBuddyEntity is Entity model. Link the BuddyEntity model with CustomerEntity
 * 
 * @author MC
 * @version 1.0
 */
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class CustomerBuddyEntity {
  /**
   * User ID
   */
  Integer idUser;
  /**
   * Friend ID
   */
  Integer idBuddy;
  /**
   * User connection
   */
  String connection;
  /**
   * User email
   */
  String email;

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
    var customerBuddyEntity = (CustomerBuddyEntity) o;
    return
    Objects.equals(this.idUser, customerBuddyEntity.idUser)
    && Objects.equals(this.idBuddy, customerBuddyEntity.idBuddy)
    && Objects.equals(this.connection, customerBuddyEntity.connection)
    && Objects.equals(this.email, customerBuddyEntity.email);
  }
 
  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(idUser, idBuddy, connection, email);
  }
}
