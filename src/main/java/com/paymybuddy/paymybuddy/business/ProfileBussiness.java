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
    CustomerEntity customerEntity = customerDao.findById(user.getId())
        .orElseThrow(() -> new MyException("throw.UserNotExist"));
    
    // Customer first name
    if(user.getFirstName() != null) {
      customerEntity.setFirstName(user.getFirstName());
    }

    // Customer last name
    if(user.getLastName() != null) {
      customerEntity.setLastName(user.getLastName());
    }

    // Address 1 customer
    if(user.getAddress1() != null) {
      customerEntity.setAddress1(user.getAddress1());
    }

    // Address 2 customer
    if(user.getAddress2() != null) {
      customerEntity.setAddress2(user.getAddress2());
    }

    // Customer zip code
    if(user.getZipCode() != null) {
      customerEntity.setZipCode(user.getZipCode());
    }

    // Customer city
    if(user.getCity() != null) {
      customerEntity.setCity(user.getCity());
    }
    
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
    // The bank account does not exist
    BankAccountEntity bankAccountEntity = bankAccountDao.findById(bankAccount.getIdCustomer())
        .orElseThrow(() -> new MyException("throw.BankAccountNotExist"));
    
    // Name of the bank
    if(bankAccount.getBankName() != null) {
      bankAccountEntity.setBankName(bankAccount.getBankName());
    }

    // Bank code
    if(bankAccount.getBankCode() != null) {
      bankAccountEntity.setBankCode(bankAccount.getBankCode());
    }

    // Branch code
    if(bankAccount.getBranchCode() != null) {
      bankAccountEntity.setBranchCode(bankAccount.getBranchCode());
    }

    // Account number
    if(bankAccount.getAccountNumber() != null) {
      bankAccountEntity.setAccountNumber(bankAccount.getAccountNumber());
    }

    // Bank key
    if(bankAccount.getRib() != null) {
      bankAccountEntity.setRib(bankAccount.getRib());
    }

    // IBAN
    if(bankAccount.getIban() != null) {
      bankAccountEntity.setIban(bankAccount.getIban());
    }

    // BIC code
    if(bankAccount.getBic() != null) {
      bankAccountEntity.setBic(bankAccount.getBic());
    }
    
    bankAccountEntity = bankAccountDao.save(bankAccountEntity);
    return bankAccountUtils.fromBankAccountEntityToBankAccount(bankAccountEntity);
  }
}
