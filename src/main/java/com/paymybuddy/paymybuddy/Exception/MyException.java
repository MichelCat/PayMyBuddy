package com.paymybuddy.paymybuddy.Exception;

public class MyException extends Exception {
  private final String messsage;
  private Object[] parameters;
  
  public MyException(String messsage, Object... parameters) {
    this.messsage = messsage;
    this.parameters = parameters;
  }
  
  public String getMessage() {
    return MessagePropertieFormat.getMessage(messsage, parameters);
  }
}
