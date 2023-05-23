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
  * Email sender
  */
  String emailSender;
        /**
        * Email recipient
        */
        String emailRecipient;
  
        /**
         * Email sender ID
         */
        long idUserSender;
        /**
         * Email recipient ID
         */
        long idUserRecipient;
        
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
      && Objects.equals(this.emailSender, customerMessage.emailSender)
      && Objects.equals(this.emailRecipient, customerMessage.emailRecipient)
      && Objects.equals(this.idUserSender, customerMessage.idUserSender)
      && Objects.equals(this.idUserRecipient, customerMessage.idUserRecipient)
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
    return Objects.hash(id, emailSender, emailRecipient, idUserSender, idUserRecipient, messageDate, subject, detail);
  }
  
  public CustomerMessage() {
    id = 0;
    emailSender = "";
    emailRecipient = "";
    idUserSender = 0;
    idUserRecipient = 0;
    messageDate = null;
    subject = "";
    detail = "";
  }
}
