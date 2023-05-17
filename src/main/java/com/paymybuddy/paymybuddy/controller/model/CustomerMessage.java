package com.paymybuddy.paymybuddy.controller.model;

import java.sql.Timestamp;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * CustomerMessage is business model
 * 
 * @author MC
 * @version 1.0
 */
@Validated
@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
public class CustomerMessage {

  /**
   * Message ID
   */
  Integer id;
  /**
   * Message customer ID
   */
  Integer idCustomer;
  /**
   * Message date
   */
  Timestamp messageDate;
  /**
   * Subject
   */
  String subject;
  /**
   * Detail
   */
  String detail;
  
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
    var customerMessage = (CustomerMessage) o;
    return
    Objects.equals(this.id, customerMessage.id)
      && Objects.equals(this.idCustomer, customerMessage.idCustomer)
      && Objects.equals(this.messageDate, customerMessage.messageDate)
      && Objects.equals(this.subject, customerMessage.subject)
      && Objects.equals(this.detail, customerMessage.detail);
  }

  /**
   * Get the hash code for the object of class Method
   * 
   * @return Hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, idCustomer, messageDate, subject, detail);
  }
  
  public CustomerMessage() {
    id = 0;
    idCustomer = 0;
    messageDate = null;
    subject = "";
    detail = "";
  }
}
