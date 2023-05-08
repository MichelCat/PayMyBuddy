package com.paymybuddy.paymybuddy.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;
import com.paymybuddy.paymybuddy.controller.model.Transaction;
import com.paymybuddy.paymybuddy.controller.model.TransactionParameter;
import com.paymybuddy.paymybuddy.controller.utils.BuddyUtils;
import com.paymybuddy.paymybuddy.controller.utils.CustomerAccountUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionParameterUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionUtils;
import com.paymybuddy.paymybuddy.dao.db.BankTransactionDao;
import com.paymybuddy.paymybuddy.dao.db.BuddyDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.TransactionParameterDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;

/**
 * HomeBussiness is the home page processing service
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class HomeBussiness {

  @Autowired
  private TransactionParameterDao transactionParameterDao;
  @Autowired
  private TransactionParameterUtils transactionParameterUtils;
  @Autowired
  private CustomerAccountDao customerAccountDao;
  @Autowired
  private CustomerAccountUtils customerAccountUtils;
  @Autowired
  private BankTransactionDao bankTransactionDao;
  @Autowired
  private TransactionUtils transactionUtils;
  @Autowired
  private CustomerDao customerDao;
  @Autowired
  private BuddyDao buddyDao;
  @Autowired
  private BuddyUtils buddyUtils;

  /**
   * Find the list of transaction parameters
   * 
   * @return List of transaction parameters sorted by descending effective date
   */
  public List<TransactionParameter> getTransactionParameters() {
    List<TransactionParameterEntity> transactionParameterEntities = transactionParameterDao.findAllByOrderByEffectiveDateDesc();
    return transactionParameterUtils.fromListTransactionParameterEntityToListTransactionParameter(transactionParameterEntities);
  }
  
  /**
   * Find customer account information
   * 
   * @param id User ID
   * @return Customer account information
   */
  public CustomerAccount getCustomerAccountById(final Integer id) {
    CustomerAccountEntity customerAccountEntity = customerAccountDao.findById(id).get();
    return customerAccountUtils.fromCustomerAccountEntityToCustomerAccount(customerAccountEntity);
  }
  
  /**
   * Last transaction
   * 
   * @param id User ID
   * @return Last transaction
   */
  public Transaction getLastTransactionById(final Integer id) {
    CustomerEntity customerEntity = customerDao.findById(id).get();
    BankTransactionEntity bankTransactionEntity = bankTransactionDao.findFirstByCustomerDebitOrCustomerCreditOrderByTransactionDateDesc(
                                                    customerEntity, customerEntity);
    return transactionUtils.fromBankTransactionEntityToTransaction(bankTransactionEntity);
  }
  
  /**
   * Search user's buddy list
   * 
   * @param id User ID
   * @return List of user's buddies
   */
  public List<Buddy> getBuddiesById(Integer id) {
    
    // Buddy list
    Optional<CustomerEntity> customerEntity = customerDao.findById(id);
    List<BuddyEntity> buddyEntities = buddyDao.findByCustomerUser(customerEntity);
    List<Buddy> buddies = buddyUtils.fromListBuddyEntityToListBuddy(buddyEntities);
    
    // Add connection name
    buddies.forEach(b -> {
      Optional<CustomerEntity> customerBuddy = customerDao.findById(b.getIdBuddy());
      b.setEmail(customerBuddy.get().getEmail());
    });
    return buddies;
  }
}
