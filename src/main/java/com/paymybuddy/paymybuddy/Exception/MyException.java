package com.paymybuddy.paymybuddy.Exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class MyException extends Exception {
  private final String messsage;
  
  public MyException(String messsage) {
    this.messsage = messsage;
  }
  
  public String getMessage() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("lang/messages", Locale.ENGLISH);
    return resourceBundle.getString(messsage);    
  }
}
