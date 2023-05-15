package com.paymybuddy.paymybuddy.Exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessagePropertieFormat {
  private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.ENGLISH);

  public static String getMessage(String messsage, Object... parameters) {
    try {
      String message = resourceBundle.getString(messsage);
      return MessageFormat.format(message, parameters);
   } catch (Exception e) {
      return messsage;
    }    
  }
}
