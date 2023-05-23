package com.paymybuddy.paymybuddy.business;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paymybuddy.paymybuddy.Exception.MyException;
import com.paymybuddy.paymybuddy.controller.model.BankAccount;
import com.paymybuddy.paymybuddy.controller.model.Customer;
import com.paymybuddy.paymybuddy.controller.utils.BankAccountUtils;
import com.paymybuddy.paymybuddy.controller.utils.CustomerUtils;
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
  private CustomerUtils customerUtils;
  @Autowired
  private BankAccountDao bankAccountDao;
  @Autowired
  private BankAccountUtils bankAccountUtils;

  /**
   * Find customer information
   * 
   * @param username Email
   * @return Customer information
   */
  public Customer getCustomerByUsername(final String username) {
    Optional<CustomerEntity> optCustomerEntity = customerDao.findByUsername(username);
    return customerUtils.fromCustomerEntityToUser(optCustomerEntity.get());
  }
  
  /**
   * Find bank account information
   * 
   * @param id User ID
   * @return Bank account information
   */
  public BankAccount getBankAccountById(final Integer id) {
    Optional<CustomerEntity> optCustomerEntity = customerDao.findById(id);
        
    Optional<BankAccountEntity> optBankAccountEntity = bankAccountDao.findByCustomer(optCustomerEntity);
    if (optBankAccountEntity.isEmpty()) {
      BankAccount bankAccount = new BankAccount();
      bankAccount.setIdCustomer(id);
      return bankAccount;
    }
    return bankAccountUtils.fromBankAccountEntityToBankAccount(optBankAccountEntity.get());
  }
  
  /**
   * Update an existing user
   * 
   * @param customer The user object updated
   * @return New modified customer record
   */
  @Transactional(rollbackFor = Exception.class)
  public Customer updateUser(Customer customer, String username) throws MyException {
    // User does not exist
    CustomerEntity customerEntity = customerDao.findByUsername(username)
        .orElseThrow(() -> new MyException("throw.CustomerNotExist", username));
    
    customerEntity.setFirstName(customer.getFirstName());
    customerEntity.setLastName(customer.getLastName());
    customerEntity.setAddress1(customer.getAddress1());
    customerEntity.setAddress2(customer.getAddress2());
    customerEntity.setZipCode(customer.getZipCode());
    customerEntity.setCity(customer.getCity());
    return customerUtils.fromCustomerEntityToUser(customerDao.save(customerEntity));
  }
  
  /**
   * Add/Update a bank account
   * 
   * @param bankAccount The bank account object updated
   * @return New modified bank account record
   */
  @Transactional(rollbackFor = Exception.class)
  public BankAccount saveBankAccount(BankAccount bankAccount, String username) throws MyException {
    BankAccountEntity bankAccountEntity = null;
        
    Optional<BankAccountEntity> optBankAccountEntity = bankAccountDao.findById(bankAccount.getId());
    if (optBankAccountEntity.isEmpty()) {
      // Bank account non-existent. Creation of the bank account.
     
      // User does not exist
      CustomerEntity customerEntity = customerDao.findByUsername(username)
          .orElseThrow(() -> new MyException("throw.CustomerNotExist", username));
      
      bankAccountEntity = new BankAccountEntity();
      bankAccountEntity.setCustomer(customerEntity);
    } else {
      // Bank account existent
      bankAccountEntity = optBankAccountEntity.get();
    }
    
    bankAccountEntity.setBankName(bankAccount.getBankName());
    bankAccountEntity.setBankCode(bankAccount.getBankCode());
    bankAccountEntity.setBranchCode(bankAccount.getBranchCode());
    bankAccountEntity.setAccountNumber(bankAccount.getAccountNumber());
    bankAccountEntity.setRib(bankAccount.getRib());
    bankAccountEntity.setIban(bankAccount.getIban());
    bankAccountEntity.setBic(bankAccount.getBic());
    bankAccountEntity = bankAccountDao.save(bankAccountEntity);
    return bankAccountUtils.fromBankAccountEntityToBankAccount(bankAccountEntity);
  }
}
