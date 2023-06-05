package com.paymybuddy.paymybuddy.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ContactBusiness {
  
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
   * @param pageNumber Current page
   * @param pageSize Page size
   * @return List of customer messages
   */
  public Page<CustomerMessage> getCustomerMessageById(final String username
                                                      , final int pageNumber
                                                      , final int pageSize
                                                      ) throws MyException {
    // User does not exist
    Optional<AppUserEntity> optAppUserEntity = appUserDao.findByUsername(username);
    if (optAppUserEntity.isEmpty() == true) {
      throw new MyException("throw.UserNotFound", username);
    }
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
    
    Page<CustomerMessageEntity> customerMessageEntities
                                  = customerMessageDao.findByAppUserEntitySenderOrAppUserEntityRecipientOrderByMessageDateDesc(
                                        optAppUserEntity, optAppUserEntity, pageable);
    
    List<CustomerMessage> customerMessages = new ArrayList<>();
    for (CustomerMessageEntity customerMessageEntity : customerMessageEntities) {
      CustomerMessage customerMessage = customerMessageUtils.fromCustomerMessageEntityToCustomerMessage(customerMessageEntity);
      
      Optional<AppUserEntity> optUserSender = appUserDao.findById(customerMessage.getIdUserSender());
      if (optUserSender.isEmpty()) {
        throw new MyException("throw.SenderUnknown");
      }
      customerMessage.setEmailSender(optUserSender.get().getUsername());
      
      Optional<AppUserEntity> optUserRecipient = appUserDao.findById(customerMessage.getIdUserRecipient());
      if (optUserRecipient.isEmpty()) {
        throw new MyException("throw.RecipientUnknown");
      }
      customerMessage.setEmailRecipient(optUserRecipient.get().getUsername());
      
      customerMessages.add(customerMessage);
    }
    return new PageImpl<>(customerMessages, pageable, customerMessageEntities.getTotalElements());
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
