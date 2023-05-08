package com.paymybuddy.paymybuddy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.BankAccount;
import com.paymybuddy.paymybuddy.controller.model.User;
import com.paymybuddy.paymybuddy.controller.utils.BankAccountUtils;
import com.paymybuddy.paymybuddy.controller.utils.UserUtils;
import com.paymybuddy.paymybuddy.dao.db.BankAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BankAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;

/**
 * ProfileBussiness is the profile page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class ProfileBussiness {
  
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private UserUtils userUtils;
  @Autowired
  private BankAccountDao bankAccountDao;
  @Autowired
  private BankAccountUtils bankAccountUtils;

  /**
   * Find customer information
   * 
   * @param id User ID
   * @return Customer information
   */
  public User getUserById(final Integer id) {
    CustomerEntity customerEntity = customerDao.findById(id).get();
    return userUtils.fromCustomerEntityToUser(customerEntity);
  }
  
  /**
   * Find bank account information
   * 
   * @param id User ID
   * @return Bank account information
   */
  public BankAccount getBankAccountById(final Integer id) {
    BankAccountEntity bankAccountEntity = bankAccountDao.findById(id).get();
    return bankAccountUtils.fromBankAccountEntityToBankAccount(bankAccountEntity);
  }
  
  /**
   * Update an existing user
   * 
   * @param user The user object updated
   * @return New modified customer record
   */
  @Transactional(rollbackFor = Exception.class)
  public User updateUser(User user) throws MyException {
    // User does not exist
    if (customerDao.existsById(user.getId()) == false) {
      throw new MyException("throw.UserNotExist");
    }
    
    CustomerEntity customerEntity = userUtils.fromUserToCustomerEntity(user);
    return userUtils.fromCustomerEntityToUser(customerDao.save(customerEntity));
  }
  
  /**
   * Update an existing bank account
   * 
   * @param bankAccount The bank account object updated
   * @return New modified bank account record
   */
  @Transactional(rollbackFor = Exception.class)
  public BankAccount updateBankAccount(BankAccount bankAccount) throws MyException {
    CustomerEntity customerEntity = customerDao.findById(bankAccount.getIdCustomer())
        .orElseThrow(() -> new MyException("throw.UserNotExist"));
    
    BankAccountEntity bankAccountEntity = bankAccountUtils.fromBankAccountToBankAccountEntity(
                                            bankAccount, customerEntity);
    bankAccountEntity = bankAccountDao.save(bankAccountEntity);
    return bankAccountUtils.fromBankAccountEntityToBankAccount(bankAccountEntity);
  }
}
