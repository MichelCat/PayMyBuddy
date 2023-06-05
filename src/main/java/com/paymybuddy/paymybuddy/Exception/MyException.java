package com.paymybuddy.paymybuddy.Exception;

public class MyException extends Exception {
  private final String messsage;
  private final Object[] parameters;
  
  public MyException(String messsage, Object... parameters) {
    this.messsage = messsage;
    this.parameters = parameters;
  }
  
  @Override
  public String getMessage() {
    return MessagePropertieFormat.getMessage(messsage, parameters);
  }
}
