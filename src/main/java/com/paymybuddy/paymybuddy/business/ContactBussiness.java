package com.paymybuddy.paymybuddy.business;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.CustomerMessage;
import com.paymybuddy.paymybuddy.controller.utils.CustomerMessageUtils;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerMessageDao;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerMessageEntity;

/**
 * TransferBussiness is the transfer page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class ContactBussiness {
  
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private CustomerMessageDao customerMessageDao;
  @Autowired
  private CustomerMessageUtils customerMessageUtils;
  
  
  /**
   * Adding the new customer message
   * 
   * @param customerMessage New customer message to add
   * @return New customer message added
   */
  @Transactional(rollbackFor = Exception.class)
  public CustomerMessage addCustomerMessage(CustomerMessage customerMessage) throws MyException {
    // User does not exist
    CustomerEntity customerEntity = customerDao.findById(customerMessage.getIdCustomer())
        .orElseThrow(() -> new MyException("throw.CustomerNotExist", customerMessage.getIdCustomer()));
    
    // Transaction date
    long currentTimeMillis = System.currentTimeMillis();
    Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
    
    CustomerMessageEntity customerMessageEntity = customerMessageUtils.fromCustomerMessageToCustomerMessageEntity(customerMessage);
    customerMessageEntity.setCustomer(customerEntity);
    customerMessageEntity.setMessageDate(currentTimestamp);
    customerMessageEntity.setSubject(customerMessage.getSubject());
    customerMessageEntity.setDetail(customerMessage.getDetail());
    return customerMessageUtils.fromCustomerMessageEntityToCustomerMessage(customerMessageDao.save(customerMessageEntity));
  }
}
