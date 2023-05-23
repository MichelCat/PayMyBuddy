package com.paymybuddy.paymybuddy.controller.utils;

import java.util.ArrayList;
import java.util.List;
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
   * Conversion CustomerMessageEntity list to CustomerMessage list
   * 
   * @param customerMessageEntities CustomerMessageEntity list
   * @return CustomerMessage list
   */
  public List<CustomerMessage> fromListCustomerMessageEntityToListCustomerMessage(List<CustomerMessageEntity> customerMessageEntities) {
    List<CustomerMessage> customerMessages = new ArrayList<>();
    for (CustomerMessageEntity customerMessageEntity : customerMessageEntities) {
      customerMessages.add(fromCustomerMessageEntityToCustomerMessage(customerMessageEntity));
    }    
    return customerMessages;
  }

  /**
   * Conversion CustomerMessageEntity to CustomerMessage
   * 
   * @param customerMessageEntity CustomerMessageEntity object
   * @return CustomerMessage
   */
  public CustomerMessage fromCustomerMessageEntityToCustomerMessage(CustomerMessageEntity customerMessageEntity) {
    CustomerMessage customerMessage = new CustomerMessage();
    customerMessage.setId(customerMessageEntity.getId());
    customerMessage.setEmailSender(null);
    customerMessage.setEmailRecipient(null);
    customerMessage.setIdUserSender(customerMessageEntity.getAppUserEntitySender().getId());
    customerMessage.setIdUserRecipient(customerMessageEntity.getAppUserEntityRecipient().getId());
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
    customerMessageEntity.setAppUserEntitySender(null);
    customerMessageEntity.setAppUserEntityRecipient(null);
    customerMessageEntity.setMessageDate(customerMessage.getMessageDate());
    customerMessageEntity.setSubject(customerMessage.getSubject());
    customerMessageEntity.setDetail(customerMessage.getDetail());
    return customerMessageEntity;
  }
}
