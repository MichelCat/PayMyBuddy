package com.paymybuddy.paymybuddy.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.CustomerMessage;
import com.paymybuddy.paymybuddy.controller.utils.CustomerMessageUtils;
import com.paymybuddy.paymybuddy.dao.db.CustomerMessageDao;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerMessageEntity;
import com.paymybuddy.paymybuddy.dao.user.AppUserDao;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

/**
 * TransferBussiness is the transfer page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class ContactBussiness {
  
  @Autowired
  private CustomerMessageDao customerMessageDao;
  @Autowired
  private CustomerMessageUtils customerMessageUtils;
  @Autowired
  private AppUserDao appUserDao;
  
  /**
   * Search list of customer messages
   * 
   * @param id User ID
   * @return List of customer messages
   */
  public List<CustomerMessage> getCustomerMessageById(final String username) throws MyException {
    // User does not exist
    AppUserEntity appUserEntity = appUserDao.findByUsername(username)
         .orElseThrow(() -> new MyException("throw.UserNotFound", username));
    
    List<CustomerMessageEntity> customerMessageEntities = customerMessageDao.getCustomerMessageById(appUserEntity.getId());
    
    List<CustomerMessage> customerMessages = new ArrayList<>();
    for (CustomerMessageEntity customerMessageEntity : customerMessageEntities) {
      CustomerMessage customerMessage = customerMessageUtils.fromCustomerMessageEntityToCustomerMessage(customerMessageEntity);
      
      Optional<AppUserEntity> optUserSender = appUserDao.findById(customerMessage.getIdUserSender());
      customerMessage.setEmailSender(optUserSender.get().getUsername());
      
      Optional<AppUserEntity> optUserRecipient = appUserDao.findById(customerMessage.getIdUserRecipient());
      customerMessage.setEmailRecipient(optUserRecipient.get().getUsername());
      
      customerMessages.add(customerMessage);
    }
    return customerMessages;
  }
  
  /**
   * Adding the new customer message
   * 
   * @param customerMessage New customer message to add
   * @return New customer message added
   */
  @Transactional(rollbackFor = Exception.class)
  public CustomerMessage addCustomerMessage(CustomerMessage customerMessage, String username) throws MyException {
    // Sender does not exist
    AppUserEntity userSender = appUserDao.findByUsername(username)
        .orElseThrow(() -> new MyException("throw.CustomerNotExist", username));
    // Contact email does not exist
    AppUserEntity userContactEmail = appUserDao.findByContactEmail()
        .orElseThrow(() -> new MyException("throw.ContactEmailNotExist"));
    
    // Transaction date
    long currentTimeMillis = System.currentTimeMillis();
    Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
    
    CustomerMessageEntity customerMessageEntity = customerMessageUtils.fromCustomerMessageToCustomerMessageEntity(customerMessage);
    customerMessageEntity.setAppUserEntitySender(userSender);
    customerMessageEntity.setAppUserEntityRecipient(userContactEmail);
    customerMessageEntity.setMessageDate(currentTimestamp);
    customerMessageEntity.setSubject(customerMessage.getSubject());
    customerMessageEntity.setDetail(customerMessage.getDetail());
    return customerMessageUtils.fromCustomerMessageEntityToCustomerMessage(customerMessageDao.save(customerMessageEntity));
  }
}
