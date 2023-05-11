package com.paymybuddy.paymybuddy.business;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;
import com.paymybuddy.paymybuddy.controller.model.Transaction;
import com.paymybuddy.paymybuddy.controller.model.TransactionParameter;
import com.paymybuddy.paymybuddy.controller.utils.CustomerAccountUtils;
import com.paymybuddy.paymybuddy.controller.utils.CustomerBuddyUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionParameterUtils;
import com.paymybuddy.paymybuddy.controller.utils.TransactionUtils;
import com.paymybuddy.paymybuddy.dao.db.BankTransactionDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerAccountDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerDao;
import com.paymybuddy.paymybuddy.dao.db.TransactionParameterDao;
import com.paymybuddy.paymybuddy.dao.db.CustomerBuddyDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BankTransactionEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerBuddyEntity;

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
  private CustomerBuddyDao customerBuddyDao;
  @Autowired
  private CustomerBuddyUtils customerBuddyUtils;

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
    Optional<CustomerAccountEntity> optCustomerAccountEntity = customerAccountDao.findById(id);
    return customerAccountUtils.fromCustomerAccountEntityToCustomerAccount(optCustomerAccountEntity.get());
  }
  
  /**
   * Last transaction
   * 
   * @param id User ID
   * @return Last transaction
   */
  public Transaction getLastTransactionById(final Integer id) {
    Optional<CustomerEntity> optCustomerEntity = customerDao.findById(id);
    
    Optional<BankTransactionEntity> optBankTransactionEntity
                              = bankTransactionDao.findFirstByCustomerDebitOrCustomerCreditOrderByTransactionDateDesc(
                                   optCustomerEntity, optCustomerEntity);
    return transactionUtils.fromBankTransactionEntityToTransaction(optBankTransactionEntity.get());
  }
  
  /**
   * Search user's buddy list
   * 
   * @param id User ID
   * @return List of user's buddies
   */
  public List<Buddy> getBuddiesById(Integer id) {
    List<CustomerBuddyEntity> customerBuddyEntities = customerBuddyDao.findCustomerBuddyById(id);
    return customerBuddyUtils.fromListCustomerBuddyEntityToListBuddy(customerBuddyEntities);
  }
}
