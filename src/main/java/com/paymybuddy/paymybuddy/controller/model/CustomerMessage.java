package com.paymybuddy.paymybuddy.controller.model;

import java.sql.Timestamp;
import org.springframework.validation.annotation.Validated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
@EqualsAndHashCode(of = {"id", "emailSender", "emailRecipient", "idUserSender", "idUserRecipient", "messageDate", "subject", "detail"})
@ToString
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
