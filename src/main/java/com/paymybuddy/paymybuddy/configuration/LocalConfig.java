package com.paymybuddy.paymybuddy.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocalConfig {

//  @Bean
//  public MessageSource messageSource() {
//    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//    messageSource.setBasename("lang/messages");
//    messageSource.setDefaultEncoding("UTF-8");
//    return messageSource;
//  }
  
  @Bean
  public ResourceBundleMessageSource messageSource() {
      final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
      source.setBasename("lang");
      return source;
  }
  
  
}
