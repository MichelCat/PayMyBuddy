package com.paymybuddy.paymybuddy.controller.utils;

import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.CustomerMessage;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerMessageEntity;

/**
 * CustomerMessageUtils is an CustomerMessage object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class CustomerMessageUtils {

  /**
   * Conversion CustomerMessageEntity to CustomerMessage
   * 
   * @param customerMessageEntity CustomerMessageEntity object
   * @return CustomerMessage
   */
  public CustomerMessage fromCustomerMessageEntityToCustomerMessage(CustomerMessageEntity customerMessageEntity) {
    CustomerMessage customerMessage = new CustomerMessage();
    customerMessage.setId(customerMessageEntity.getId());
    customerMessage.setIdCustomer(null);
    customerMessage.setMessageDate(customerMessageEntity.getMessageDate());
    customerMessage.setSubject(customerMessageEntity.getSubject());
    customerMessage.setDetail(customerMessageEntity.getDetail());
    return customerMessage;
  }
  
  /**
   * Conversion CustomerMessage to CustomerMessageEntity
   * 
   * @param customerMessage CustomerMessage object
   * @return CustomerMessageEntity
   */
  public CustomerMessageEntity fromCustomerMessageToCustomerMessageEntity(CustomerMessage customerMessage) {
    CustomerMessageEntity customerMessageEntity = new CustomerMessageEntity();
    customerMessageEntity.setId(customerMessage.getId());
    customerMessageEntity.setCustomer(null);
    customerMessageEntity.setMessageDate(customerMessage.getMessageDate());
    customerMessageEntity.setSubject(customerMessage.getSubject());
    customerMessageEntity.setDetail(customerMessage.getDetail());
    return customerMessageEntity;
  }
}
