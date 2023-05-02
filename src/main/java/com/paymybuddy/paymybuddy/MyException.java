package com.paymybuddy.paymybuddy;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

public class MyException extends Exception {
  private ResourceBundleMessageSource resourceBundleMessageSource;
  private final String messsage;
  
  
  public MyException(ResourceBundleMessageSource resourceBundleMessageSource, String messsage) {
    this.resourceBundleMessageSource = resourceBundleMessageSource;
    this.messsage = messsage;
  }
  
  public String getMessage() {
    // en-US
    // 
//    return messageSource.getMessage(codeErreur, null, Locale.getDefault());
//    return messageSource.getMessage(codeErreur, null, Locale.ENGLISH);
    
    return resourceBundleMessageSource.getMessage(messsage, null, Locale.ENGLISH);
  }
}
