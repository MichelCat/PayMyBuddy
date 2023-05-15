package com.paymybuddy.paymybuddy.Exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyException extends Exception {
//  private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.ENGLISH);
  private final String messsage;
  private Object[] parameters;
  
  public MyException(String messsage, Object... parameters) {
    this.messsage = messsage;
    this.parameters = parameters;
  }
  
  public String getMessage() {
//    try {
//      String message = resourceBundle.getString(messsage);
//      return MessageFormat.format(message, parameters);
//    } catch (Exception e) {
//      return messsage;
//    }
    return MessagePropertieFormat.getMessage(messsage, parameters);
  }
}
